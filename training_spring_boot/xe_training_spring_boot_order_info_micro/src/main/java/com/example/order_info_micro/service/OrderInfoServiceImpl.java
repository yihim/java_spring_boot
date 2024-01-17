package com.example.order_info_micro.service;

import com.example.order_info_micro.business.DetailsValidation;
import com.example.order_info_micro.business.NameUpdate;
import com.example.order_info_micro.business.OrderQuantityUpdate;
import com.example.order_info_micro.database.OrderInfoRepository;
import com.example.order_info_micro.dto.ApiDto.MagicWandCatalogueDto;
import com.example.order_info_micro.entity.OrderInfo;
import com.example.order_info_micro.exception.client.ClientErrorException;
import com.example.order_info_micro.exception.client.MagicWandCatalogue.MagicWandCatalogueNotExistException;
import com.example.order_info_micro.exception.client.MagicWandCatalogue.MagicWandCatalogueNotValidException;
import com.example.order_info_micro.exception.client.WizardInfo.WizardInfoNotValidException;
import com.example.order_info_micro.exception.server.*;
import com.example.order_info_micro.integration.RTMagicWandCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.Integer.parseInt;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Autowired
    private DetailsValidation detailsValidation;

    @Autowired
    private OrderQuantityUpdate orderQuantityUpdate;

    @Autowired
    private RTMagicWandCatalogueService rtMagicWandCatalogueService;

    @Autowired
    private NameUpdate nameUpdate;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Override
    public OrderInfo saveOrderInfo(OrderInfo orderInfo) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoService.saveOrderInfo");
        try {
            OrderInfo validatedOrderInfo = detailsValidation.orderInfoDetailsValidation(orderInfo);
            // To validate wizard info
            Map<String, String> validWizardInfo = detailsValidation.wizardInfoDetailsValidation(orderInfo.getWizardId().toString().trim(), orderInfo.getWizardName().trim());
            // To validate magic wand catalogue
            Map<String, String> validMagicWandCatalogue = detailsValidation.magicWandCatalogueDetailsValidation(orderInfo.getMagicWandCatalogueId().toString().trim(), orderInfo.getMagicWandCatalogueName().trim(), orderInfo.getWizardId().toString().trim());
            if (validWizardInfo.get("Result").equalsIgnoreCase("Wizard details are valid.")) {
                if (validMagicWandCatalogue.get("Result").equalsIgnoreCase("Magic wand catalogue details are valid and wizard's age is applicable.")) {
                    // If both validations passed, proceed to check duplicated data
                    List<OrderInfo> existOrderInfo = orderInfoRepository.findOrderInfoByWizardId(orderInfo.getWizardId());
                    // Check the list of data whether a data is consisting both Ids
                    for (OrderInfo info : existOrderInfo) {
                        if (info.getWizardId().equals(orderInfo.getWizardId()) && info.getMagicWandCatalogueId().equals(orderInfo.getMagicWandCatalogueId())) {
                            throw new OrderInfoExistException("Order info exist.");
                        }
                    }
                    // If not duplicated, then proceed to normal saving
                    // When saving, update the magic wand catalogue stock as well
                    MagicWandCatalogueDto updateMagicWandCatalogueDtoStock = orderQuantityUpdate.updateMagicWandCatalogueStockOnSaveOrderInfo(orderInfo.getMagicWandCatalogueId().toString().trim(), orderInfo.getQuantity());
                    rtMagicWandCatalogueService.updateMagicWandCatalogueById(orderInfo.getMagicWandCatalogueId().toString().trim(), updateMagicWandCatalogueDtoStock);
                    orderInfo.setWizardId(UUID.fromString(validatedOrderInfo.getWizardId().toString().trim()));
                    orderInfo.setWizardName(validatedOrderInfo.getWizardName().trim());
                    orderInfo.setMagicWandCatalogueId(UUID.fromString(validatedOrderInfo.getMagicWandCatalogueId().toString().trim()));
                    orderInfo.setMagicWandCatalogueName(validatedOrderInfo.getMagicWandCatalogueName().trim());
                    return orderInfoRepository.save(orderInfo);
                } else {
                    throw new MagicWandCatalogueNotValidException(validMagicWandCatalogue.get("Result"), parseInt(validMagicWandCatalogue.get("HttpStatus")));
                }
            } else {
                throw new WizardInfoNotValidException(validWizardInfo.get("Result"), parseInt(validWizardInfo.get("HttpStatus")));
            }
        } catch (NullPointerException e) {
            throw new InvalidOrderInfoDetailsException("All fields must not be null.");
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }

    }

    @Override
    public List<OrderInfo> getAllOrderInfo() throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoService.getAllOrderInfo");
        try {
            if (orderInfoRepository.findAll().isEmpty()) {
                throw new NoOrderInfoFoundException("There is no order info in the database.");
            }
            return orderInfoRepository.findAll();
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }

    @Override
    public OrderInfo getOrderInfoById(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoService.getOrderInfoById");
        try {
            return orderInfoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new OrderInfoIdNotFoundException("Order info does not exist."));
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }

    @Override
    public OrderInfo updateOrderInfoById(String id, OrderInfo orderInfo) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoService.updateOrderInfoById");
        try {
            OrderInfo currentOderInfo = orderInfoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new OrderInfoIdNotFoundException("Order info does not exist."));
            OrderInfo validatedOrderInfo = detailsValidation.orderInfoDetailsValidation(orderInfo);
            // To validate wizard info
            Map<String, String> validWizardInfo = detailsValidation.wizardInfoDetailsValidation(orderInfo.getWizardId().toString().trim(), orderInfo.getWizardName().trim());
            // To validate magic wand catalogue
            Map<String, String> validMagicWandCatalogue = detailsValidation.magicWandCatalogueDetailsValidationOnUpdate(orderInfo.getMagicWandCatalogueId().toString().trim(), orderInfo.getMagicWandCatalogueName().trim(), orderInfo.getWizardId().toString().trim());
            if (validWizardInfo.get("Result").equalsIgnoreCase("Wizard details are valid.")) {
                if (validMagicWandCatalogue.get("Result").equalsIgnoreCase("Magic wand catalogue details are valid and wizard's age is applicable.")) {
                    // If both validations passed, proceed to update order info
                    if (currentOderInfo.getWizardId().equals(orderInfo.getWizardId()) && currentOderInfo.getMagicWandCatalogueId().equals(orderInfo.getMagicWandCatalogueId())) {
                        // If both wizard and magic wand Ids are matched with the current order info, proceed to update magic wand stock, both names and update order info
                        MagicWandCatalogueDto updatedMagicWandCatalogueDtoStock = orderQuantityUpdate.updateMagicWandCatalogueStockOnUpdateOrderInfo(orderInfo.getMagicWandCatalogueId().toString().trim(), orderInfo.getQuantity(), currentOderInfo);
                        rtMagicWandCatalogueService.updateMagicWandCatalogueById(orderInfo.getMagicWandCatalogueId().toString().trim(), updatedMagicWandCatalogueDtoStock);
                        List<OrderInfo> currentAllOrderInfo = getAllOrderInfo();
                        nameUpdate.updateOrderInfoWizardAndMagicWandName(currentAllOrderInfo, currentOderInfo, orderInfo);
                        currentOderInfo.setQuantity(validatedOrderInfo.getQuantity());
                        return orderInfoRepository.save(currentOderInfo);
                    } else {
                        // Else, proceed to suggesting user to update exist order info that contains both Ids
                        List<OrderInfo> existOrderInfo = orderInfoRepository.findOrderInfoByWizardId(orderInfo.getWizardId());
                        // Check the list of data whether a data is consisting both Ids
                        for (OrderInfo info : existOrderInfo) {
                            if (info.getWizardId().equals(orderInfo.getWizardId()) && info.getMagicWandCatalogueId().equals(orderInfo.getMagicWandCatalogueId())) {
                                throw new OrderInfoExistException("Order info exist.");
                            }
                        }
                    }
                    // If both Ids are not found in the order info table, suggest user to add a new one
                    throw new NoOrderInfoFoundException("Consider adding a new order info with the respective data.");
                } else {
                    throw new MagicWandCatalogueNotValidException(validMagicWandCatalogue.get("Result"), parseInt(validMagicWandCatalogue.get("HttpStatus")));
                }
            } else {
                throw new WizardInfoNotValidException(validWizardInfo.get("Result"), parseInt(validWizardInfo.get("HttpStatus")));
            }
        } catch (NullPointerException e) {
            throw new InvalidOrderInfoDetailsException("All fields must not be null.");
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }

    @Override
    public String deleteOrderInfoById(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoService.deleteOrderInfoById");
        try {
            OrderInfo currentOrderInfo = orderInfoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new OrderInfoIdNotFoundException("Order info does not exist."));
            try {
                // If the magic wand catalogue has not been deleted, then proceed to update the stock before deleting
                MagicWandCatalogueDto updatedMagicWandCatalogueDtoStock = orderQuantityUpdate.updateMagicWandCatalogueStockOnDeleteOrderInfo(currentOrderInfo.getMagicWandCatalogueId().toString().trim(), currentOrderInfo.getQuantity());
                if (updatedMagicWandCatalogueDtoStock != null) {
                    rtMagicWandCatalogueService.updateMagicWandCatalogueById(currentOrderInfo.getMagicWandCatalogueId().toString().trim(), updatedMagicWandCatalogueDtoStock);
                    orderInfoRepository.deleteById(UUID.fromString(id));
                }
            } catch (MagicWandCatalogueNotExistException e) {
                // If the magic wand catalogue has been deleted, proceed to normal deletion
                orderInfoRepository.deleteById(UUID.fromString(id));
            }
            return "Order info has been deleted successfully !\tId: " + id;
        } catch (HttpClientErrorException e) {
            throw new ClientErrorException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e.getStatusCode().value());
        }
    }
}
