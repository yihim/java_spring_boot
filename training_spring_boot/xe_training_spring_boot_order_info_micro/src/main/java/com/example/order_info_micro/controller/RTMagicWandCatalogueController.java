package com.example.order_info_micro.controller;

import com.example.order_info_micro.dto.ApiDto.MagicWandCatalogueDto;
import com.example.order_info_micro.integration.RTMagicWandCatalogueService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/magic-wand-catalogue")
public class RTMagicWandCatalogueController {

    private static final Logger logger = LoggerFactory.getLogger(RTMagicWandCatalogueController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RTMagicWandCatalogueService rtMagicWandCatalogueService;

    @GetMapping("find-all")
    public List<MagicWandCatalogueDto> findAllMagicWandCatalogue() throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTMagicWandCatalogueController.findAllMagicWandCatalogue");
        return rtMagicWandCatalogueService.getAllMagicWandCatalogue()
                .stream()
                .map(magicWandCatalogue -> modelMapper.map(magicWandCatalogue, MagicWandCatalogueDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("find-id/{id}")
    public ResponseEntity<MagicWandCatalogueDto> findMagicWandCatalogueById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTMagicWandCatalogueController.findMagicWandCatalogueById");

        MagicWandCatalogueDto magicWandCatalogueDto = rtMagicWandCatalogueService.getMagicWandCatalogueById(id);

        MagicWandCatalogueDto magicWandCatalogueResponse = modelMapper.map(magicWandCatalogueDto, MagicWandCatalogueDto.class);

        return ResponseEntity.ok().body(magicWandCatalogueResponse);
    }
}