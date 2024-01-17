package com.example.order_info_micro.controller;

import com.example.order_info_micro.dto.ApiDto.WizardInfoDto;
import com.example.order_info_micro.integration.RTWizardInfoService;
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
@RequestMapping("/wizard-info")
public class RTWizardInfoController {

    private static final Logger logger = LoggerFactory.getLogger(RTMagicWandCatalogueController.class);

    @Autowired
    private RTWizardInfoService rtWizardInfoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("find-all")
    public List<WizardInfoDto> findAllWizardInfo() throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTWizardInfoController.findAllWizardInfo");
        return rtWizardInfoService.getAllWizardInfo()
                .stream()
                .map(wizardInfoModel -> modelMapper.map(wizardInfoModel, WizardInfoDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("find-id/{id}")
    public ResponseEntity<WizardInfoDto> findWizardInfoById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTWizardInfoController.findWizardInfoById");

        WizardInfoDto wizardInfoDto = rtWizardInfoService.getWizardInfoById(id);

        WizardInfoDto wizardInfoResponse = modelMapper.map(wizardInfoDto, WizardInfoDto.class);

        return ResponseEntity.ok().body(wizardInfoResponse);
    }

}