package com.example.order_info_micro.business;

import com.example.order_info_micro.entity.OrderInfo;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Map;

public interface DetailsValidation {
    Map<String, String> wizardInfoDetailsValidation(String id, String name) throws HttpRequestMethodNotSupportedException;

    Map<String, String> magicWandCatalogueDetailsValidation(String id, String name, String wizardInfoId) throws HttpRequestMethodNotSupportedException;

    Map<String, String> magicWandCatalogueDetailsValidationOnUpdate(String id, String name, String wizardInfoId) throws HttpRequestMethodNotSupportedException;

    OrderInfo orderInfoDetailsValidation(OrderInfo orderInfo);
}
