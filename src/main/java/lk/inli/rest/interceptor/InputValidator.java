package lk.inli.rest.interceptor;

import lk.inli.rest.model.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Provider
@Priority(value = 2)
public class InputValidator implements ContainerRequestFilter {
    private static final Logger log = LogManager.getLogger(InputValidator.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.debug("Reading path and query parameters");
        String path = requestContext.getUriInfo().getPath();
        Optional<MultivaluedMap<String, String>> optionalQueryParams = Optional.ofNullable(requestContext.getUriInfo().getQueryParameters());
        Optional<MultivaluedMap<String, String>> optionalPathParams = Optional.ofNullable(requestContext.getUriInfo().getPathParameters());

        if (!path.endsWith("ping")) {
            log.debug("Path does not contain heartbeat request");

            if (!optionalPathParams.isPresent() && !optionalQueryParams.isPresent()) {
                log.debug("Neither path nor query params available");
                requestContext.abortWith(this.createInputNotFoundErrorResponse());
                return;
            }

            if (optionalPathParams.isPresent() && null != optionalPathParams.get() && optionalPathParams.get().size() > 0) {
                optionalPathParams.ifPresent(pathParams -> {
                    //Number is given as path parameter
                    log.debug("Reading input from path param {}", optionalPathParams.get());
                    Optional<String> optional = Optional.ofNullable(pathParams.getFirst("input"));

                    this.hasNumberInParam(optional, requestContext);
                });
            } else if (optionalQueryParams.isPresent() && null != optionalQueryParams.get() && optionalQueryParams.get().size() > 0)  {
                optionalQueryParams.ifPresent(queryParams -> {
                    log.debug("Reading input range from query string {}", optionalQueryParams.get());
                    Optional<String> optionalStart = Optional.ofNullable(queryParams.getFirst("start"));
                    Optional<String> optionalEnd = Optional.ofNullable(queryParams.getFirst("end"));

                    this.hasNumberInParam(optionalStart, requestContext);
                    this.hasNumberInParam(optionalEnd, requestContext);
                });
            } else {
                log.debug("Neither path nor query params valid");
                requestContext.abortWith(this.createInputNotFoundErrorResponse());
            }
        } else {
            log.info("Heartbeat request. Just proceeding ...");
        }
    }

    private void hasNumberInParam(Optional<String> optional, ContainerRequestContext requestContext) {
        if (!optional.isPresent()) {
            log.info("Input number not found!");
            requestContext.abortWith(createInputNotFoundErrorResponse());
        }

        optional.ifPresent(input -> {
            log.info("Input param: {}", input);

            try {
                this.isNumber(input);
            } catch (NumberFormatException nfe) {
                requestContext.abortWith(createNumberFormatErrorResponse());
            }
        });
    }

    private void isNumber(String input) throws NumberFormatException {
        try {
            Pattern pattern = Pattern.compile("^[\\d]*$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                log.debug("Valid number in param");
            } else {
                throw new NumberFormatException("Not a number");
            }
        } catch (Exception e) {
            throw new NumberFormatException("Not a number");
        }
    }

    private Response createInputNotFoundErrorResponse() {
        // Send a generic 400 bad request - array of numbers not found
        Response.ResponseBuilder responseBuilder = null;
        ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), "Input number not found.");

        responseBuilder = Response.status(Response.Status.BAD_REQUEST);
        responseBuilder = responseBuilder.type(MediaType.APPLICATION_JSON);
        Response response = responseBuilder.entity(errorResponse).build();
        return response;
    }

    private Response createNumberFormatErrorResponse() {
        // Send a generic 400 bad request - invalid numbers found in the input
        Response.ResponseBuilder responseBuilder = null;
        ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), "Input number is not valid.");

        responseBuilder = Response.status(Response.Status.BAD_REQUEST);
        responseBuilder = responseBuilder.type(MediaType.APPLICATION_JSON);
        Response response = responseBuilder.entity(errorResponse).build();
        return response;
    }
}
