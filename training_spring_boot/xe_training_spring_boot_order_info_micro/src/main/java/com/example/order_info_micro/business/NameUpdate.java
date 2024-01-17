package com.example.order_info_micro.business;

import com.example.order_info_micro.entity.OrderInfo;

import java.util.List;

public interface NameUpdate {
    void updateOrderInfoWizardAndMagicWandName(List<OrderInfo> currentAllOrderInfo, OrderInfo currentOrderInfo, OrderInfo updatedOrderInfo);
}
