package com.example.order_info_micro.exception.client;

import brave.Span;
import brave.Tracer;
import com.example.order_info_micro.exception.ExceptionFormat;
import com.example.order_info_micro.exception.client.MagicWandCatalogue.MagicWandCatalogueNotExistException;
import com.example.order_info_micro.exception.client.MagicWandCatalogue.MagicWandCatalogueNotValidException;
import com.example.order_info_micro.exception.client.WizardInfo.WizardInfoNotExistException;
import com.example.order_info_micro.exception.client.WizardInfo.WizardInfoNotValidException;
import com.example.order_info_micro.exception.server.ServerExceptionsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ClientExceptionsHandler {
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

    @ExceptionHandler(ClientErrorException.class)
    public Map<String, Object> handleClientErrorException(ClientErrorException ex) {
        Map<String, Object> message = new HashMap<>();
        String clientErrorExceptionTraceId = getTraceId();
        message.put("code", ex.getHttpStatus());
        message.put("message", ex.getMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), clientErrorExceptionTraceId, message);
        logger.info("ClientErrorExceptionTraceId: {}", clientErrorExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(WizardInfoNotValidException.class)
    public Map<String, Object> handleWizardInfoNotValidException(WizardInfoNotValidException ex) {
        Map<String, Object> message = new HashMap<>();
        String wizardInfoNotValidExceptionTraceId = getTraceId();
        message.put("code", ex.getHttpStatus());
        message.put("message", ex.getMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), wizardInfoNotValidExceptionTraceId, message);
        logger.info("MagicWandCatalogueNotValidExceptionTraceId: {}", wizardInfoNotValidExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(MagicWandCatalogueNotValidException.class)
    public Map<String, Object> handleMagicWandCatalogueNotValidException(MagicWandCatalogueNotValidException ex) {
        Map<String, Object> message = new HashMap<>();
        String magicWandCatalogueNotValidExceptionTraceId = getTraceId();
        message.put("code", ex.getHttpStatus());
        message.put("message", ex.getMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), magicWandCatalogueNotValidExceptionTraceId, message);
        logger.info("MagicWandCatalogueNotValidExceptionTraceId: {}", magicWandCatalogueNotValidExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(MagicWandCatalogueNotExistException.class)
    public Map<String, Object> handleMagicWandCatalogueNotExistException(MagicWandCatalogueNotExistException ex) {
        Map<String, Object> message = new HashMap<>();
        String magicWandCatalogueNotExistExceptionTraceId = getTraceId();
        message.put("code", ex.getHttpStatus());
        message.put("message", ex.getMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), magicWandCatalogueNotExistExceptionTraceId, message);
        logger.info("MagicWandCatalogueNotExistExceptionTraceId: {}", magicWandCatalogueNotExistExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }

    @ExceptionHandler(WizardInfoNotExistException.class)
    public Map<String, Object> handleWizardInfoNotExistException(WizardInfoNotExistException ex) {
        Map<String, Object> message = new HashMap<>();
        String wizardInfoNotExistExceptionTraceId = getTraceId();
        message.put("code", ex.getHttpStatus());
        message.put("message", ex.getMessage());
        ExceptionFormat exceptionFormat = new ExceptionFormat("NOK", 1, LocalDateTime.now(), wizardInfoNotExistExceptionTraceId, message);
        logger.info("WizardInfoNotExistExceptionTraceId: {}", wizardInfoNotExistExceptionTraceId);
        logger.info(String.valueOf(exceptionFormat.toFormat()));
        return exceptionFormat.toFormat();
    }
}
