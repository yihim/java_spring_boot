package com.example.order_info_micro.integration;

import com.example.order_info_micro.dto.ApiDto.MagicWandCatalogueDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

public interface RTMagicWandCatalogueService {

    List<MagicWandCatalogueDto> getAllMagicWandCatalogue() throws HttpRequestMethodNotSupportedException;

    MagicWandCatalogueDto getMagicWandCatalogueById(String id) throws HttpRequestMethodNotSupportedException;

    void updateMagicWandCatalogueById(String id, MagicWandCatalogueDto magicWandCatalogueDto) throws HttpRequestMethodNotSupportedException;
}
