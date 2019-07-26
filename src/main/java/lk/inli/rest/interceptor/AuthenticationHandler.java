package lk.inli.rest.interceptor;

import lk.inli.rest.model.ErrorResponse;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
@Priority(value = 1)
public class AuthenticationHandler implements ContainerRequestFilter {
    private final static String USERNAME = "REST_USER";
    private final static String PASSWORD = "REST_PASSWORD";

    private static final Logger log = LogManager.getLogger(AuthenticationHandler.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Optional<String> optional = Optional.ofNullable(requestContext.getHeaderString("Authorization"));

        if (!optional.isPresent()) {
            log.fatal("Authentication details missing!");
            requestContext.abortWith(createFaultResponse());
        }

        optional.ifPresent(authorization -> {
            log.info("Authentication headers found {}", authorization);
            String[] parts = authorization.split(" ");

            if (parts.length != 2 || !"Basic".equals(parts[0])) {
                requestContext.abortWith(createFaultResponse());
                return;
            }

            String decodedAuthValue = null;
            try {
                decodedAuthValue = new String(Base64Utility.decode(parts[1]));
            } catch (Base64Exception ex) {
                requestContext.abortWith(createFaultResponse());
                return;
            }

            String[] authValues = decodedAuthValue.split(":");
            if (isAuthenticated(authValues[0], authValues[1])) {
                // let request to continue
            } else {
                // authentication failed
                requestContext.abortWith(createFaultResponse());
            }
        });
    }

    private boolean isAuthenticated(String username, String password) {
        return USERNAME.equalsIgnoreCase(username) && PASSWORD.equalsIgnoreCase(password);
    }

    private Response createFaultResponse() {
        // Send a generic 401 Unauthorized error
        Response.ResponseBuilder responseBuilder = null;
        ErrorResponse errorResponse = new ErrorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "Invalid credentials");

        responseBuilder = Response.status(Response.Status.UNAUTHORIZED);
        responseBuilder = responseBuilder.type(MediaType.APPLICATION_JSON);
        Response response = responseBuilder.entity(errorResponse).build();

        return response;
        //return Response.status(401).header("WWW-Authenticate", "Basic").build();
    }
}