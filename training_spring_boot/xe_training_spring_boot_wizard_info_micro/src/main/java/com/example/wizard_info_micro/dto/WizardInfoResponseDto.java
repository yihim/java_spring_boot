package com.example.wizard_info_micro.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WizardInfoResponseDto {
    private UUID id;
    private String name;
    private int age;
    private boolean active;
}
