package com.example.order_info_micro.exception;

import brave.Span;
import brave.Tracer;
import com.example.order_info_micro.exception.server.ServerExceptionsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionsHandler {
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, Object> message = new HashMap<>();
        String httpMessageNotReadableExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.BAD_REQUEST.value());
        message.put("message", "Both Ids must be in UUID format.");
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), httpMessageNotReadableExceptionTraceId, message);
        logger.info("HttpMessageNotReadableExceptionTraceId: {}", httpMessageNotReadableExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }
}
