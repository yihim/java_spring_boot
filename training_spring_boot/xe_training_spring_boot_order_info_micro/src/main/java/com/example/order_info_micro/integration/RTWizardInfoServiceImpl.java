package com.example.order_info_micro.integration;

import com.example.order_info_micro.common.ApiUrl;
import com.example.order_info_micro.dto.ApiDto.WizardInfoDto;
import com.example.order_info_micro.exception.client.ClientErrorException;
import com.example.order_info_micro.exception.client.WizardInfo.WizardInfoNotExistException;
import com.example.order_info_micro.exception.server.ServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RTWizardInfoServiceImpl implements RTWizardInfoService {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RTMagicWandCatalogueServiceImpl.class);

    @Override
    public List<WizardInfoDto> getAllWizardInfo() throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTWizardInfoService.getAllWizardInfo");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity entity = new HttpEntity<>(null, headers);
            ResponseEntity<List<WizardInfoDto>> response = restTemplate.exchange(ApiUrl.WIZARD_INFO_GET_ALL_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<List<WizardInfoDto>>() {
            });
            List<WizardInfoDto> allWizardInfoDto = response.getBody();
            return allWizardInfoDto;
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }

    @Override
    public WizardInfoDto getWizardInfoById(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTWizardInfoService.getWizardInfoById");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<WizardInfoDto> response = restTemplate.exchange(ApiUrl.WIZARD_INFO_GET_BY_ID_URL + id, HttpMethod.GET, entity, WizardInfoDto.class);
            WizardInfoDto wizardInfoDto = response.getBody();
            if (wizardInfoDto.getId() == null) {
                throw new WizardInfoNotExistException("Wizard info Id does not exist.", HttpStatus.NOT_FOUND.value());
            }
            return wizardInfoDto;
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }
};