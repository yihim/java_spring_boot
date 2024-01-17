package com.example.magic_wand_catalogue_micro.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MagicWandCatalogueDto {
    private UUID id;
    private String name;
    private int ageLimit;
    private int stock;
}
