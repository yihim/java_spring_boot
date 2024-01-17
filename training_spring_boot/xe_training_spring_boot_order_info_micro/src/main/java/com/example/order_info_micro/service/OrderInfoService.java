package com.example.order_info_micro.service;

import com.example.order_info_micro.entity.OrderInfo;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

public interface OrderInfoService {

    OrderInfo saveOrderInfo(OrderInfo orderInfo) throws HttpRequestMethodNotSupportedException;

    List<OrderInfo> getAllOrderInfo() throws HttpRequestMethodNotSupportedException;

    OrderInfo getOrderInfoById(String id) throws HttpRequestMethodNotSupportedException;

    OrderInfo updateOrderInfoById(String id, OrderInfo orderInfo) throws HttpRequestMethodNotSupportedException;

    String deleteOrderInfoById(String id) throws HttpRequestMethodNotSupportedException;
}
