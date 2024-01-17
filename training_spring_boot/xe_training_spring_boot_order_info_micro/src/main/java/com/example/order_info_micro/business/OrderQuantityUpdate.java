package com.example.order_info_micro.business;

import com.example.order_info_micro.dto.ApiDto.MagicWandCatalogueDto;
import com.example.order_info_micro.entity.OrderInfo;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface OrderQuantityUpdate {
    MagicWandCatalogueDto updateMagicWandCatalogueStockOnUpdateOrderInfo(String magicWandCatalogueId, int updatedQuantity, OrderInfo currentOrderInfo) throws HttpRequestMethodNotSupportedException;

    MagicWandCatalogueDto updateMagicWandCatalogueStockOnSaveOrderInfo(String magicWandCatalogueId, int savedQuantity) throws HttpRequestMethodNotSupportedException;

    MagicWandCatalogueDto updateMagicWandCatalogueStockOnDeleteOrderInfo(String magicWandCatalogueId, int currentOrderQuantity) throws HttpRequestMethodNotSupportedException;
}
