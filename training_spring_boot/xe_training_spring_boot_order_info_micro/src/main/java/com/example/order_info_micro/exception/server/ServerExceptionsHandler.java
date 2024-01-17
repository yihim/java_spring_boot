package com.example.order_info_micro.exception.server;

import brave.Span;
import brave.Tracer;
import com.example.order_info_micro.exception.ExceptionFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(ServerErrorException.class)
    public Map<String, Object> handleServerErrorException(ServerErrorException ex) {
        Map<String, Object> message = new HashMap<>();
        String serverErrorExceptionTraceId = getTraceId();
        message.put("code", ex.getHttpStatus());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), serverErrorExceptionTraceId, message);
        logger.info("ServerErrorExceptionTraceId: {}", serverErrorExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(InvalidOrderInfoDetailsException.class)
    public Map<String, Object> handleInvalidOrderInfoDetailsException(InvalidOrderInfoDetailsException ex) {
        Map<String, Object> message = new HashMap<>();
        String invalidOrderInfoDetailsExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.BAD_REQUEST.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), invalidOrderInfoDetailsExceptionTraceId, message);
        logger.info("InvalidOrderInfoDetailsExceptionTraceId: {}", invalidOrderInfoDetailsExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(OrderInfoExistException.class)
    public Map<String, Object> handleOrderInfoExistException(OrderInfoExistException ex) {
        Map<String, Object> message = new HashMap<>();
        String orderInfoExistExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.CONFLICT.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), orderInfoExistExceptionTraceId, message);
        logger.info("OrderInfoExistExceptionTraceId: {}", orderInfoExistExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(NoOrderInfoFoundException.class)
    public Map<String, Object> handleNoWizardInfoFoundException(NoOrderInfoFoundException ex) {
        Map<String, Object> message = new HashMap<>();
        String noOrderInfoFoundExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.NO_CONTENT.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), noOrderInfoFoundExceptionTraceId, message);
        logger.info("NoMagicWandCatalogueFoundExceptionTraceId: {}", noOrderInfoFoundExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(OrderInfoIdNotFoundException.class)
    public Map<String, Object> handleWizardIdNotFoundException(OrderInfoIdNotFoundException ex) {
        Map<String, Object> message = new HashMap<>();
        String orderInfoIdNotFoundExceptionTraceId = getTraceId();
        message.put("code", HttpStatus.NOT_FOUND.value());
        message.put("message", ex.getLocalizedMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), orderInfoIdNotFoundExceptionTraceId, message);
        logger.info("OrderInfoIdNotFoundExceptionTraceId: {}", orderInfoIdNotFoundExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }
}
