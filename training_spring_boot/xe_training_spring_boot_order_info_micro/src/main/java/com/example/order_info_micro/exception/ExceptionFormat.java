package com.example.order_info_micro.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionFormat {
    private String type;
    private int code;
    private LocalDateTime timestamp;
    private String traceId;
    private Map<String, Object> errors;
    
    public Map<String, Object> toFormat() {
        Map<String, Object> status = new HashMap<>();
        Map<String, Object> details = new HashMap<>();
        details.put("type", getType());
        details.put("code", getCode());
        details.put("timestamp", getTimestamp());
        details.put("traceId", getTraceId());
        details.put("errors", getErrors());
        status.put("status", details);
        return details;
    }
}
