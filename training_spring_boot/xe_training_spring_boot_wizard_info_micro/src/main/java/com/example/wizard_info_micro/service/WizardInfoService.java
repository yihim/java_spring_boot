package com.example.wizard_info_micro.service;

import com.example.wizard_info_micro.dto.WizardInfoRequestDto;
import com.example.wizard_info_micro.dto.WizardInfoResponseDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

public interface WizardInfoService {

    WizardInfoResponseDto saveWizardInfo(WizardInfoRequestDto wizardInfoRequestDto) throws HttpRequestMethodNotSupportedException;

    List<WizardInfoResponseDto> getAllWizardInfo() throws HttpRequestMethodNotSupportedException;

    WizardInfoResponseDto getWizardInfoById(String id) throws HttpRequestMethodNotSupportedException;

    WizardInfoResponseDto updateWizardInfoById(String id, WizardInfoRequestDto wizardInfoRequestDto) throws HttpRequestMethodNotSupportedException;

    String deleteWizardInfo(String id) throws HttpRequestMethodNotSupportedException;

}
