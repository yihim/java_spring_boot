package com.example.order_info_micro.controller;

import com.example.order_info_micro.dto.OrderInfoDto;
import com.example.order_info_micro.entity.OrderInfo;
import com.example.order_info_micro.service.OrderInfoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/order-info")
public class OrderInfoController {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("add")
    public ResponseEntity<OrderInfoDto> addOrderInfo(@RequestBody OrderInfoDto orderInfoDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoController.addOrderInfo");

        OrderInfo orderInfoRequest = modelMapper.map(orderInfoDto, OrderInfo.class);

        OrderInfo orderInfo = orderInfoService.saveOrderInfo(orderInfoRequest);

        OrderInfoDto orderInfoResponse = modelMapper.map(orderInfo, OrderInfoDto.class);

        return new ResponseEntity<OrderInfoDto>(orderInfoResponse, HttpStatus.CREATED);
    }

    @GetMapping("find-all")
    public List<OrderInfoDto> findAllOrderInfo() throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoController.findAllOrderInfo");

        return orderInfoService.getAllOrderInfo()
                .stream()
                .map(orderInfo -> modelMapper.map(orderInfo, OrderInfoDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("find-id/{id}")
    public ResponseEntity<OrderInfoDto> findOrderInfoById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoController.findOrderInfoById");

        OrderInfo orderInfo = orderInfoService.getOrderInfoById(id);

        OrderInfoDto orderInfoResponse = modelMapper.map(orderInfo, OrderInfoDto.class);
        return ResponseEntity.ok().body(orderInfoResponse);
    }

    @PutMapping("update-id/{id}")
    public ResponseEntity<OrderInfoDto> changeOrderInfoById(@PathVariable String id, @RequestBody OrderInfoDto orderInfoDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoController.changeOrderInfoById");

        OrderInfo orderInfoRequest = modelMapper.map(orderInfoDto, OrderInfo.class);

        OrderInfo orderInfo = orderInfoService.updateOrderInfoById(id, orderInfoRequest);

        OrderInfoDto orderInfoResponse = modelMapper.map(orderInfo, OrderInfoDto.class);

        return ResponseEntity.ok().body(orderInfoResponse);
    }

    @DeleteMapping("delete-id/{id}")
    public String removeOrderInfoById(@PathVariable String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server OrderInfoController.removeOrderInfoById");
        return orderInfoService.deleteOrderInfoById(id);
    }
}
