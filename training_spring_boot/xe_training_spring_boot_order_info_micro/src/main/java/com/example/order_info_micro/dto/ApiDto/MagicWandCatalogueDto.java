package com.example.order_info_micro.dto.ApiDto;

import lombok.Data;

import java.util.UUID;

@Data
public class MagicWandCatalogueDto {
    private UUID id;
    private String name;
    private int ageLimit;
    private int stock;
}
