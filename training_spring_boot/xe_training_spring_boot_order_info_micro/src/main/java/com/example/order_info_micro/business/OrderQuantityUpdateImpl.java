package com.example.order_info_micro.business;

import com.example.order_info_micro.dto.ApiDto.MagicWandCatalogueDto;
import com.example.order_info_micro.entity.OrderInfo;
import com.example.order_info_micro.exception.client.MagicWandCatalogue.MagicWandCatalogueNotValidException;
import com.example.order_info_micro.integration.RTMagicWandCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
public class OrderQuantityUpdateImpl implements OrderQuantityUpdate {

    private static final Logger logger = LoggerFactory.getLogger(OrderQuantityUpdateImpl.class);

    @Autowired
    RTMagicWandCatalogueService rtMagicWandCatalogueService;

    @Override
    public MagicWandCatalogueDto updateMagicWandCatalogueStockOnUpdateOrderInfo(String magicWandCatalogueId, int updatedQuantity, OrderInfo currentOrderInfo) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderQuantityUpdate.updateMagicWandCatalogueStockOnUpdateOrderInfo");
        MagicWandCatalogueDto currentMagicWandCatalogueDto = rtMagicWandCatalogueService.getMagicWandCatalogueById(magicWandCatalogueId);
        int currentMagicWandCatalogueStock = currentMagicWandCatalogueDto.getStock();
        int currentOrderInfoQuantity = currentOrderInfo.getQuantity();
        if (updatedQuantity > currentOrderInfoQuantity) {
            int stockDecrement = updatedQuantity - currentOrderInfoQuantity;
            int updatedStock = currentMagicWandCatalogueStock - stockDecrement;
            if (updatedStock < 0) {
                throw new MagicWandCatalogueNotValidException("Magic wand catalogue's stock is not sufficient. " + "(Current: " + currentOrderInfoQuantity + ", Available: " + currentMagicWandCatalogueStock + ")", HttpStatus.BAD_REQUEST.value());
            }
            currentMagicWandCatalogueDto.setStock(updatedStock);
            return currentMagicWandCatalogueDto;
        } else {
            int stockIncrement = currentOrderInfoQuantity - updatedQuantity;
            int updatedStock = currentMagicWandCatalogueStock + stockIncrement;
            currentMagicWandCatalogueDto.setStock(updatedStock);
            return currentMagicWandCatalogueDto;
        }
    }

    @Override
    public MagicWandCatalogueDto updateMagicWandCatalogueStockOnSaveOrderInfo(String magicWandCatalogueId, int savedQuantity) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderQuantityUpdate.updateMagicWandCatalogueStockOnSaveOrderInfo");
        MagicWandCatalogueDto currentMagicWandCatalogueDto = rtMagicWandCatalogueService.getMagicWandCatalogueById(magicWandCatalogueId);
        int currentMagicWandCatalogueStock = currentMagicWandCatalogueDto.getStock();
        if (savedQuantity > currentMagicWandCatalogueStock) {
            throw new MagicWandCatalogueNotValidException("Magic wand catalogue's stock is not sufficient. " + "(Selected: " + savedQuantity + ", Available: " + currentMagicWandCatalogueStock + ")", HttpStatus.BAD_REQUEST.value());
        } else {
            int updatedStock = currentMagicWandCatalogueStock - savedQuantity;
            currentMagicWandCatalogueDto.setStock(updatedStock);
            logger.info(String.valueOf(currentMagicWandCatalogueDto));
            return currentMagicWandCatalogueDto;
        }
    }

    @Override
    public MagicWandCatalogueDto updateMagicWandCatalogueStockOnDeleteOrderInfo(String magicWandCatalogueId, int currentOrderQuantity) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderQuantityUpdate.updateMagicWandCatalogueStockOnDeleteOrderInfo");
        MagicWandCatalogueDto currentMagicWandCatalogueDto = rtMagicWandCatalogueService.getMagicWandCatalogueById(magicWandCatalogueId);
        if (currentMagicWandCatalogueDto != null) {
            int currentMagicWandCatalogueStock = currentMagicWandCatalogueDto.getStock();
            int updateMagicWandCatalogueStock = currentMagicWandCatalogueStock + currentOrderQuantity;
            currentMagicWandCatalogueDto.setStock(updateMagicWandCatalogueStock);
            return currentMagicWandCatalogueDto;
        }
        return null;
    }
}
