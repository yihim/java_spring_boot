package com.example.order_info_micro.integration;

import com.example.order_info_micro.dto.ApiDto.WizardInfoDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

public interface RTWizardInfoService {

    List<WizardInfoDto> getAllWizardInfo() throws HttpRequestMethodNotSupportedException;

    WizardInfoDto getWizardInfoById(String id) throws HttpRequestMethodNotSupportedException;
}
