package com.example.order_info_micro.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderInfoDto {
    private UUID id;
    private UUID wizardId;
    private String wizardName;
    private UUID magicWandCatalogueId;
    private String magicWandCatalogueName;
    private int quantity;
}
