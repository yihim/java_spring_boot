package com.example.magic_wand_catalogue_micro.exception.server;

import brave.Span;
import brave.Tracer;
import com.example.magic_wand_catalogue_micro.exception.ExceptionFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ServerExceptionsHandler {

    @Autowired
    private Tracer tracer;

    private static final Logger logger = LoggerFactory.getLogger(ServerExceptionsHandler.class);

    public String getTraceId() {
        String traceId = null;
        Span span = tracer.currentSpan();
        if (span != null) {
            traceId = span.context().traceIdString();
        }
        return traceId;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> message = new HashMap<>();
        String httpRequestMethodNotSupportedExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.METHOD_NOT_ALLOWED.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), httpRequestMethodNotSupportedExceptionTraceId, message);
        logger.info("HttpRequestMethodNotSupportedExceptionTraceId: {}", httpRequestMethodNotSupportedExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(ServerErrorException.class)
    public Map<String, Object> handleServerErrorException(ServerErrorException ex) {
        Map<String, Object> message = new HashMap<>();
        String serverErrorExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), serverErrorExceptionTraceId, message);
        logger.info("ServerErrorExceptionTraceId: {}", serverErrorExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(InvalidMagicWandCatalogueDetailsException.class)
    public Map<String, Object> handleInvalidMagicWandCatalogueDetailsException(InvalidMagicWandCatalogueDetailsException ex) {
        Map<String, Object> message = new HashMap<>();
        String invalidMagicWandCatalogueDetailsExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.BAD_REQUEST.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), invalidMagicWandCatalogueDetailsExceptionTraceId, message);
        logger.info("InvalidMagicWandCatalogueDetailsExceptionTraceId: {}", invalidMagicWandCatalogueDetailsExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(MagicWandCatalogueExistException.class)
    public Map<String, Object> handleMagicWandCatalogueExistException(MagicWandCatalogueExistException ex) {
        Map<String, Object> message = new HashMap<>();
        String magicWandCatalogueExistExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.CONFLICT.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), magicWandCatalogueExistExceptionTraceId, message);
        logger.info("MagicWandCatalogueExistExceptionTraceId: {}", magicWandCatalogueExistExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(NoMagicWandCatalogueFoundException.class)
    public Map<String, Object> handleMagicWandCatalogueFoundException(NoMagicWandCatalogueFoundException ex) {
        Map<String, Object> message = new HashMap<>();
        String noMagicWandCatalogueFoundExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.NO_CONTENT.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), noMagicWandCatalogueFoundExceptionTraceId, message);
        logger.info("NoMagicWandCatalogueFoundExceptionTraceId: {}", noMagicWandCatalogueFoundExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(MagicWandCatalogueIdNotFoundException.class)
    public Map<String, Object> handleWizardIdNotFoundException(MagicWandCatalogueIdNotFoundException ex) {
        Map<String, Object> message = new HashMap<>();
        String magicWandCatalogueIdNotFoundExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.NOT_FOUND.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), magicWandCatalogueIdNotFoundExceptionTraceId, message);
        logger.info("MagicWandCatalogueIdNotFoundExceptionTraceId: {}", magicWandCatalogueIdNotFoundExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }
}
