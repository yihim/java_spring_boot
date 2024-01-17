package com.example.order_info_micro.database;

import com.example.order_info_micro.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, UUID> {
    List<OrderInfo> findOrderInfoByWizardId(UUID id);
}

