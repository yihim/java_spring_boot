package com.example.wizard_info_micro.controller;

import com.example.wizard_info_micro.dto.WizardInfoRequestDto;
import com.example.wizard_info_micro.dto.WizardInfoResponseDto;
import com.example.wizard_info_micro.service.WizardInfoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wizard-info")
public class WizardInfoController {

    private static final Logger logger = LoggerFactory.getLogger(WizardInfoController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WizardInfoService wizardInfoService;

    @PostMapping("add")
    public ResponseEntity<WizardInfoResponseDto> addWizardInfo(@RequestBody WizardInfoRequestDto wizardInfoRequestDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoController.addWizardInfo");

        WizardInfoResponseDto wizardInfoResponse = wizardInfoService.saveWizardInfo(wizardInfoRequestDto);

        return new ResponseEntity<WizardInfoResponseDto>(wizardInfoResponse, HttpStatus.CREATED);
    }

    @GetMapping("find-all")
    public ResponseEntity<List<WizardInfoResponseDto>> findAllWizardInfo() throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoController.findAllWizardInfo");
        return new ResponseEntity<List<WizardInfoResponseDto>>(wizardInfoService.getAllWizardInfo(), HttpStatus.OK);
    }

    @GetMapping("find-id/{id}")
    public ResponseEntity<WizardInfoResponseDto> findWizardInfoById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoController.findWizardInfoById");
        return ResponseEntity.ok().body(wizardInfoService.getWizardInfoById(id));
    }

    @PutMapping("update-id/{id}")
    public ResponseEntity<WizardInfoResponseDto> changeWizardInfoById(@PathVariable String id, @RequestBody WizardInfoRequestDto wizardInfoRequestDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoController.changeWizardInfoById");

        WizardInfoResponseDto wizardInfoResponseDto = wizardInfoService.updateWizardInfoById(id, wizardInfoRequestDto);

        return ResponseEntity.ok().body(wizardInfoResponseDto);
    }

    @DeleteMapping("delete-id/{id}")
    public String removeWizardInfoById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoController.removeWizardInfoById");
        return wizardInfoService.deleteWizardInfo(id);
    }
}
