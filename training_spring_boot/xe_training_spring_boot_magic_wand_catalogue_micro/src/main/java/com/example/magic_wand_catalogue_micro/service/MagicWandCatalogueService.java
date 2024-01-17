package com.example.magic_wand_catalogue_micro.service;

import com.example.magic_wand_catalogue_micro.entity.MagicWandCatalogue;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

public interface MagicWandCatalogueService {

    MagicWandCatalogue saveMagicWandCatalogue(MagicWandCatalogue magicWandCatalogue) throws HttpRequestMethodNotSupportedException;

    List<MagicWandCatalogue> getAllMagicWandCatalogue() throws HttpRequestMethodNotSupportedException;

    MagicWandCatalogue getMagicWandCatalogueById(String id) throws HttpRequestMethodNotSupportedException;

    MagicWandCatalogue updateMagicWandCatalogueById(String id, MagicWandCatalogue magicWandCatalogue) throws HttpRequestMethodNotSupportedException;

    MagicWandCatalogue updateMagicWandCatalogueStockById(String id, MagicWandCatalogue magicWandCatalogue) throws HttpRequestMethodNotSupportedException;

    String deleteMagicWandCatalogueById(String id) throws HttpRequestMethodNotSupportedException;
}
