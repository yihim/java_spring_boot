package com.example.magic_wand_catalogue_micro.controller;

import com.example.magic_wand_catalogue_micro.dto.MagicWandCatalogueDto;
import com.example.magic_wand_catalogue_micro.entity.MagicWandCatalogue;
import com.example.magic_wand_catalogue_micro.service.MagicWandCatalogueService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/magic-wand-catalogue")
public class MagicWandCatalogueController {

    private static final Logger logger = LoggerFactory.getLogger(MagicWandCatalogueController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MagicWandCatalogueService magicWandCatalogueService;

    @PostMapping("add")
    public ResponseEntity<MagicWandCatalogueDto> addMagicWandCatalogue(@RequestBody MagicWandCatalogueDto magicWandCatalogueDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueController.addMagicWandCatalogue");

        MagicWandCatalogue magicWandCatalogueRequest = modelMapper.map(magicWandCatalogueDto, MagicWandCatalogue.class);

        MagicWandCatalogue magicWandCatalogue = magicWandCatalogueService.saveMagicWandCatalogue(magicWandCatalogueRequest);

        MagicWandCatalogueDto magicWandCatalogueResponse = modelMapper.map(magicWandCatalogue, MagicWandCatalogueDto.class);

        return new ResponseEntity<MagicWandCatalogueDto>(magicWandCatalogueResponse, HttpStatus.CREATED);
    }

    @GetMapping("find-all")
    public List<MagicWandCatalogueDto> findAllMagicWandCatalogue() throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueController.findAllMagicWandCatalogue");
        return magicWandCatalogueService.getAllMagicWandCatalogue()
                .stream()
                .map(magicWandCatalogue -> modelMapper.map(magicWandCatalogue, MagicWandCatalogueDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("find-id/{id}")
    public ResponseEntity<MagicWandCatalogueDto> findMagicWandCatalogueById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueController.findMagicWandCatalogueById");
        MagicWandCatalogue magicWandCatalogue = magicWandCatalogueService.getMagicWandCatalogueById(id);

        MagicWandCatalogueDto magicWandCatalogueResponse = modelMapper.map(magicWandCatalogue, MagicWandCatalogueDto.class);
        return ResponseEntity.ok().body(magicWandCatalogueResponse);
    }

    @PutMapping("update-id/{id}")
    public ResponseEntity<MagicWandCatalogueDto> changeMagicWandCatalogueById(@PathVariable String id, @RequestBody MagicWandCatalogueDto magicWandCatalogueDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueController.changeMagicWandCatalogueById");

        MagicWandCatalogue magicWandCatalogueRequest = modelMapper.map(magicWandCatalogueDto, MagicWandCatalogue.class);

        MagicWandCatalogue magicWandCatalogue = magicWandCatalogueService.updateMagicWandCatalogueById(id, magicWandCatalogueRequest);

        MagicWandCatalogueDto magicWandCatalogueResponse = modelMapper.map(magicWandCatalogue, MagicWandCatalogueDto.class);

        return ResponseEntity.ok().body(magicWandCatalogueResponse);
    }

    @PutMapping("update-stock-id/{id}")
    public ResponseEntity<MagicWandCatalogueDto> changeMagicWandCatalogueStockById(@PathVariable String id, @RequestBody MagicWandCatalogueDto magicWandCatalogueDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueController.changeMagicWandCatalogueStockById");

        MagicWandCatalogue magicWandCatalogueRequest = modelMapper.map(magicWandCatalogueDto, MagicWandCatalogue.class);

        MagicWandCatalogue magicWandCatalogue = magicWandCatalogueService.updateMagicWandCatalogueStockById(id, magicWandCatalogueRequest);

        MagicWandCatalogueDto magicWandCatalogueResponse = modelMapper.map(magicWandCatalogue, MagicWandCatalogueDto.class);

        return ResponseEntity.ok().body(magicWandCatalogueResponse);
    }

    @DeleteMapping("delete-id/{id}")
    public String removeMagicWandCatalogueById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueController.removeMagicWandCatalogueById");
        return magicWandCatalogueService.deleteMagicWandCatalogueById(id);
    }
}
