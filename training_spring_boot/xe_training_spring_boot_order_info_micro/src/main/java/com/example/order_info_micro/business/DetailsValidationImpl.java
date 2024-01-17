package com.example.order_info_micro.business;

import com.example.order_info_micro.dto.ApiDto.MagicWandCatalogueDto;
import com.example.order_info_micro.dto.ApiDto.WizardInfoDto;
import com.example.order_info_micro.entity.OrderInfo;
import com.example.order_info_micro.exception.server.InvalidOrderInfoDetailsException;
import com.example.order_info_micro.integration.RTMagicWandCatalogueService;
import com.example.order_info_micro.integration.RTWizardInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DetailsValidationImpl implements DetailsValidation {

    private static final Logger logger = LoggerFactory.getLogger(DetailsValidationImpl.class);

    @Autowired
    private RTMagicWandCatalogueService rtMagicWandCatalogueService;

    @Autowired
    private RTWizardInfoService rtWizardInfoService;

    @Override
    public Map<String, String> wizardInfoDetailsValidation(String id, String name) throws HttpRequestMethodNotSupportedException {
        logger.info("Server DetailsValidation.wizardInfoDetailsValidation");
        Map<String, String> wizardInfoDetailsResult = new HashMap<>();
        WizardInfoDto existWizardInfoDto = rtWizardInfoService.getWizardInfoById(id);
        String existWizardInfoId = existWizardInfoDto.getId().toString().trim();
        String existWizardInfoName = existWizardInfoDto.getName();
        boolean existWizardInfoActive = existWizardInfoDto.isActive();
        if (id.equals(existWizardInfoId)) {
            if (name.equalsIgnoreCase(existWizardInfoName)) {
                if (existWizardInfoActive) {
                    wizardInfoDetailsResult.put("Result", "Wizard details are valid.");
                    wizardInfoDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.OK.value()));
                    return wizardInfoDetailsResult;
                } else {
                    wizardInfoDetailsResult.put("Result", "Wizard status is not active.");
                    wizardInfoDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.OK.value()));
                    return wizardInfoDetailsResult;
                }
            } else {
                wizardInfoDetailsResult.put("Result", "Wizard name is not match, try check wizard name whether has changed to a new name.");
                wizardInfoDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.NOT_FOUND.value()));
                return wizardInfoDetailsResult;
            }
        } else {
            wizardInfoDetailsResult.put("Result", "Wizard Id is not match.");
            wizardInfoDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.NOT_FOUND.value()));
            return wizardInfoDetailsResult;
        }
    }

    @Override
    public Map<String, String> magicWandCatalogueDetailsValidation(String id, String name, String wizardInfoId) throws HttpRequestMethodNotSupportedException {
        logger.info("Server DetailsValidation.magicWandCatalogueDetailsValidation");
        Map<String, String> magicWandCatalogueDetailsResult = new HashMap<>();
        MagicWandCatalogueDto existMagicWandCatalogueDto = rtMagicWandCatalogueService.getMagicWandCatalogueById(id);
        WizardInfoDto existWizardInfoDto = rtWizardInfoService.getWizardInfoById(wizardInfoId);
        String existMagicWandCatalogueId = existMagicWandCatalogueDto.getId().toString().trim();
        String existMagicWandCatalogueName = existMagicWandCatalogueDto.getName();
        int existMagicWandCatalogueStock = existMagicWandCatalogueDto.getStock();
        int existMagicWandCatalogueAgeLimit = existMagicWandCatalogueDto.getAgeLimit();
        int existWizardInfoAge = existWizardInfoDto.getAge();
        if (id.equals(existMagicWandCatalogueId)) {
            if (name.equalsIgnoreCase(existMagicWandCatalogueName)) {
                if (existWizardInfoAge <= existMagicWandCatalogueAgeLimit) {
                    if (existMagicWandCatalogueStock > 0) {
                        magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue details are valid and wizard's age is applicable.");
                        magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.OK.value()));
                        return magicWandCatalogueDetailsResult;
                    } else {
                        magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue details are valid and wizard's age is applicable but magic wand catalogue is out of stock.");
                        magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.OK.value()));
                        return magicWandCatalogueDetailsResult;
                    }
                } else {
                    magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue details are valid but wizard's age is not applicable.");
                    magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.OK.value()));
                    return magicWandCatalogueDetailsResult;
                }
            } else {
                magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue name is not match, try check magic wand catalogue name whether has changed to a new name.");
                magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.NOT_FOUND.value()));
                return magicWandCatalogueDetailsResult;
            }
        } else {
            magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue Id is not match.");
            magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.NOT_FOUND.value()));
            return magicWandCatalogueDetailsResult;
        }
    }

    @Override
    public Map<String, String> magicWandCatalogueDetailsValidationOnUpdate(String id, String name, String wizardInfoId) throws HttpRequestMethodNotSupportedException {
        logger.info("Server DetailsValidation.magicWandCatalogueDetailsValidationOnUpdate");
        Map<String, String> magicWandCatalogueDetailsResult = new HashMap<>();
        MagicWandCatalogueDto existMagicWandCatalogueDto = rtMagicWandCatalogueService.getMagicWandCatalogueById(id);
        WizardInfoDto existWizardInfoDto = rtWizardInfoService.getWizardInfoById(wizardInfoId);
        String existMagicWandCatalogueId = existMagicWandCatalogueDto.getId().toString().trim();
        String existMagicWandCatalogueName = existMagicWandCatalogueDto.getName();
        int existMagicWandCatalogueStock = existMagicWandCatalogueDto.getStock();
        int existMagicWandCatalogueAgeLimit = existMagicWandCatalogueDto.getAgeLimit();
        int existWizardInfoAge = existWizardInfoDto.getAge();
        if (id.equals(existMagicWandCatalogueId)) {
            if (name.equalsIgnoreCase(existMagicWandCatalogueName)) {
                if (existWizardInfoAge <= existMagicWandCatalogueAgeLimit) {
                    magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue details are valid and wizard's age is applicable.");
                    magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.OK.value()));
                    return magicWandCatalogueDetailsResult;
                } else {
                    magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue details are valid but wizard's age is not applicable.");
                    magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.OK.value()));
                    return magicWandCatalogueDetailsResult;
                }
            } else {
                magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue name is not match, try check magic wand catalogue name whether has changed to a new name.");
                magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.NOT_FOUND.value()));
                return magicWandCatalogueDetailsResult;
            }
        } else {
            magicWandCatalogueDetailsResult.put("Result", "Magic wand catalogue Id is not match.");
            magicWandCatalogueDetailsResult.put("HttpStatus", String.valueOf(HttpStatus.NOT_FOUND.value()));
            return magicWandCatalogueDetailsResult;
        }
    }

    @Override
    public OrderInfo orderInfoDetailsValidation(OrderInfo orderInfo) {
        logger.info("Server DetailsValidation.orderInfoDetailsValidation");
        if (orderInfo.getWizardName().trim().equals("") ||
                orderInfo.getMagicWandCatalogueName().trim().equals("")) {
            throw new InvalidOrderInfoDetailsException("Name fields must not be empty.");
        }

        Pattern digit = Pattern.compile("[0-9]");
        Pattern specialOnName = Pattern.compile("[!@#^$%&*(),.+=|<>?{}\\[\\]~-]");

        Matcher wizardNameHasDigit = digit.matcher(orderInfo.getWizardName().trim());
        Matcher wizardNameHasSpecial = specialOnName.matcher(orderInfo.getWizardName().trim());
        Matcher magicWandNameHasDigit = digit.matcher(orderInfo.getMagicWandCatalogueName().trim());
        Matcher magicWandNameHasSpecial = specialOnName.matcher(orderInfo.getMagicWandCatalogueName().trim());

        if (wizardNameHasDigit.find() || wizardNameHasSpecial.find() || magicWandNameHasDigit.find() || magicWandNameHasSpecial.find()) {
            throw new InvalidOrderInfoDetailsException("Please ensure both names do not contain any special characters and numbers.");
        }

        if (orderInfo.getQuantity() < 0) {
            throw new InvalidOrderInfoDetailsException("Order quantity must not be having negative numbers.");
        }

        return orderInfo;
    }
}
