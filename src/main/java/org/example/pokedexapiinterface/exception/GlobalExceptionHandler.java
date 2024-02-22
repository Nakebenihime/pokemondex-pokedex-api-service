package org.example.pokedexapiinterface.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.viewmodel.ErrorResponseDTO;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This method extracts the path and query parameters from the given WebRequest object
     * and constructs a path string with query parameters.
     *
     * @param request The WebRequest object containing the path and query parameters.
     * @return A string representing the URI with query parameters.
     * @throws NullPointerException if the 'request' parameter is null.
     */
    private static String getPathParameters(WebRequest request) {
        String uri = request.getDescription(false).substring(4);
        Map<String, String[]> parameters = request.getParameterMap();

        StringBuilder sb = new StringBuilder(uri);
        sb.append("?");

        parameters.forEach((name, values) -> {
            for (String value : values) {
                sb.append(name).append("=").append(value).append("&");
            }
        });

        if (sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /*------------------------------------------------------- 404 ----------------------------------------------------*/

    /**
     * Handles the exception thrown when a Pokémon is not found during a search.
     * Logs the exception information using log4j and returns an error response in the form of {@link ErrorResponseDTO}.
     *
     * @param ex      The exception instance of {@link HttpMediaTypeNotSupportedException}
     * @param request The web request
     * @return A {@link ResponseEntity} object with the error response and the appropriate HTTP status code.
     */
    @ExceptionHandler(value = {PokemonNotFoundException.class})
    protected ResponseEntity<Object> handlePokemonNotFoundException(final PokemonNotFoundException ex, WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final ErrorResponseDTO errorResponse =
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpStatus.NOT_FOUND.value())
                        .message("The specified search parameters did not produce any results")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .path(getPathParameters(request))
                        .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    /**
     * Handles the exception thrown when an Ability is not found during a search.
     * Logs the exception information using log4j and returns an error response in the form of {@link ErrorResponseDTO}.
     *
     * @param ex      The exception instance of {@link HttpMediaTypeNotSupportedException}
     * @param request The web request
     * @return A {@link ResponseEntity} object with the error response and the appropriate HTTP status code.
     */
    @ExceptionHandler(value = {AbilityNotFoundException.class})
    protected ResponseEntity<Object> handlePokemonNotFoundException(final AbilityNotFoundException ex, WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final ErrorResponseDTO errorResponse =
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpStatus.NOT_FOUND.value())
                        .message("The specified search parameters did not produce any results")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .path(getPathParameters(request))
                        .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    /*------------------------------------------------------- 405 ----------------------------------------------------*/

    /**
     * Override method from {@link ResponseEntityExceptionHandler} to handle exceptions raised when a requested HTTP method is not supported.
     * Logs the exception information using log4j and returns an error response in the form of {@link ErrorResponseDTO}.
     *
     * @param ex      The exception instance of {@link HttpRequestMethodNotSupportedException}
     * @param headers HTTP headers
     * @param status  HTTP status code
     * @param request The web request instance
     * @return A {@link ResponseEntity} object with the error response and the appropriate HTTP status code.
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final String unsupported = ex.getLocalizedMessage();
        final String supported = "Supported methods : " + ex.getSupportedHttpMethods();

        final ErrorResponseDTO errorResponse =
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.METHOD_NOT_ALLOWED)
                        .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message("Oops! It seems there's an issue. Your request can't be processed because the server doesn't support the requested method. Please check your request and make sure you're using a valid method the server can handle.")
                        .errors(Collections.singletonList(unsupported + " ; " + supported))
                        .path(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }
    /*------------------------------------------------------- 406 ----------------------------------------------------*/

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);
        final String supported = "'Accept' supported methods : " + ex.getSupportedMediaTypes();

        final ErrorResponseDTO errorResponse =
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Oops! It seems there's an issue. Your request can't be processed because the server doesn't support the requested method. Please check your request and make sure you're using a valid method the server can handle.")
                        .errors(Collections.singletonList(supported))
                        .path(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    /*------------------------------------------------------- 415 ----------------------------------------------------*/

    /**
     * Override method from {@link HttpMediaTypeNotSupportedException} to handle exceptions raised when a requested MediaType is not supported.
     * Logs the exception information using log4j and returns an error response in the form of {@link ErrorResponseDTO}.
     *
     * @param ex      The exception instance of {@link HttpMediaTypeNotSupportedException}
     * @param headers HTTP headers
     * @param status  HTTP status code
     * @param request The web request
     * @return A {@link ResponseEntity} object with the error response and the appropriate HTTP status code.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final String supported = "Content-Type supported MediaTypes : " + MediaType.toString(ex.getSupportedMediaTypes());

        final ErrorResponseDTO errorResponse =
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Oops! It seems there's an issue. Your request can't be processed because the server doesn't support the provided media type or format. Please check your request headers and make sure they match a format the server can handle.")
                        .errors(Collections.singletonList(supported))
                        .path(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    /*------------------------------------------------------- 500 ----------------------------------------------------*/

    /**
     * Handles any type of exception by logging the error and creating a custom error response in the form of {@link ErrorResponseDTO} object.
     *
     * @param ex      The exception to be handled
     * @param request The web request
     * @return A {@link ResponseEntity} object with the error response and the appropriate HTTP status code.
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(final Exception ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);
        final ErrorResponseDTO errorResponse =
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("The server experienced an unexpected error.")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .path(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }
}
