package com.example.order_info_micro.dto.ApiDto;

import lombok.Data;

import java.util.UUID;

@Data
public class WizardInfoDto {
    private UUID id;
    private String name;
    private int age;
    private boolean active;
}
