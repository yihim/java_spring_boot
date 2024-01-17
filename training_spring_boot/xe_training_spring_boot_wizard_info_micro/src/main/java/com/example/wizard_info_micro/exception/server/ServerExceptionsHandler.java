package com.example.wizard_info_micro.exception.server;

import brave.Span;
import brave.Tracer;
import com.example.wizard_info_micro.exception.ExceptionFormat;
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

    @ExceptionHandler(InvalidWizardInfoDetailsException.class)
    public Map<String, Object> handleInvalidWizardInfoDetailsException(InvalidWizardInfoDetailsException ex) {
        Map<String, Object> message = new HashMap<>();
        String invalidWizardInfoDetailsExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.BAD_REQUEST.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), invalidWizardInfoDetailsExceptionTraceId, message);
        logger.info("InvalidWizardInfoDetailsExceptionTraceId: {}", invalidWizardInfoDetailsExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(WizardInfoExistException.class)
    public Map<String, Object> handleWizardInfoExistException(WizardInfoExistException ex) {
        Map<String, Object> message = new HashMap<>();
        String wizardInfoExistExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.CONFLICT.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), wizardInfoExistExceptionTraceId, message);
        logger.info("WizardInfoExistExceptionTraceId: {}", wizardInfoExistExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(NoWizardInfoFoundException.class)
    public Map<String, Object> handleNoWizardInfoFoundException(NoWizardInfoFoundException ex) {
        Map<String, Object> message = new HashMap<>();
        String noWizardInfoFoundExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.NO_CONTENT.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), noWizardInfoFoundExceptionTraceId, message);
        logger.info("NoWizardInfoFoundExceptionTraceId: {}", noWizardInfoFoundExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(WizardIdNotFoundException.class)
    public Map<String, Object> handleWizardIdNotFoundException(WizardIdNotFoundException ex) {
        Map<String, Object> message = new HashMap<>();
        String wizardIdNotFoundExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.NOT_FOUND.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), wizardIdNotFoundExceptionTraceId, message);
        logger.info("WizardIdNotFoundExceptionTraceId: {}", wizardIdNotFoundExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }
}
