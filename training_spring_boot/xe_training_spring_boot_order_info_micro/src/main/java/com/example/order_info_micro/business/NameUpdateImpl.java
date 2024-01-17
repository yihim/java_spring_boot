package com.example.order_info_micro.business;

import com.example.order_info_micro.entity.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NameUpdateImpl implements NameUpdate {

    private static final Logger logger = LoggerFactory.getLogger(NameUpdateImpl.class);

    @Override
    public void updateOrderInfoWizardAndMagicWandName(List<OrderInfo> currentAllOrderInfo, OrderInfo currentOrderInfo, OrderInfo updatedOrderInfo) {
        logger.info("Server NameUpdate.updateOrderInfoWizardAndMagicWandName");
        String currentWizardName = currentOrderInfo.getWizardName();
        String currentMagicWandName = currentOrderInfo.getMagicWandCatalogueName();
        String updatedWizardName = updatedOrderInfo.getWizardName().trim();
        String updatedMagicWandName = updatedOrderInfo.getMagicWandCatalogueName().trim();
        for (OrderInfo info : currentAllOrderInfo) {
            if (currentWizardName.equalsIgnoreCase(info.getWizardName())) {
                info.setWizardName(updatedWizardName);
            }
            if (currentMagicWandName.equalsIgnoreCase(info.getMagicWandCatalogueName())) {
                info.setMagicWandCatalogueName(updatedMagicWandName);
            }
        }
    }
}
