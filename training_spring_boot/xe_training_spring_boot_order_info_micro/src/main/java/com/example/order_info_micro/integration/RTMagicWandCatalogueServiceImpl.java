package com.example.order_info_micro.integration;

import com.example.order_info_micro.common.ApiUrl;
import com.example.order_info_micro.dto.ApiDto.MagicWandCatalogueDto;
import com.example.order_info_micro.exception.client.ClientErrorException;
import com.example.order_info_micro.exception.client.MagicWandCatalogue.MagicWandCatalogueNotExistException;
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
public class RTMagicWandCatalogueServiceImpl implements RTMagicWandCatalogueService {

    private static final Logger logger = LoggerFactory.getLogger(RTMagicWandCatalogueServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<MagicWandCatalogueDto> getAllMagicWandCatalogue() throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTMagicWandCatalogueService.getAllMagicWandCatalogue");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity entity = new HttpEntity<>(null, headers);
            ResponseEntity<List<MagicWandCatalogueDto>> response = restTemplate.exchange(ApiUrl.MAGIC_WAND_CATALOGUE_GET_ALL_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<List<MagicWandCatalogueDto>>() {
            });
            List<MagicWandCatalogueDto> allMagicWandCatalogueDto = response.getBody();
            return allMagicWandCatalogueDto;
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }

    @Override
    public MagicWandCatalogueDto getMagicWandCatalogueById(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTMagicWandCatalogueService.getMagicWandCatalogueById");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<MagicWandCatalogueDto> response = restTemplate.exchange(ApiUrl.MAGIC_WAND_CATALOGUE_GET_BY_ID_URL + id, HttpMethod.GET, entity, MagicWandCatalogueDto.class);
            MagicWandCatalogueDto magicWandCatalogueDto = response.getBody();
            if (magicWandCatalogueDto.getId() == null) {
                throw new MagicWandCatalogueNotExistException("Magic wand catalogue Id does not exist.", HttpStatus.NOT_FOUND.value());
            }
            return magicWandCatalogueDto;
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }

    @Override
    public void updateMagicWandCatalogueById(String id, MagicWandCatalogueDto magicWandCatalogueDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Client RTMagicWandCatalogueService.updateMagicWandCatalogueById");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<MagicWandCatalogueDto> requestUpdate = new HttpEntity<MagicWandCatalogueDto>(magicWandCatalogueDto, headers);
            restTemplate.exchange(ApiUrl.MAGIC_WAND_CATALOGUE_UPDATE_STOCK_BY_ID_URL + id, HttpMethod.PUT, requestUpdate, void.class);
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }
}
