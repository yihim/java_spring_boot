package com.example.order_info_micro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID wizardId;
    private String wizardName;
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID magicWandCatalogueId;
    private String magicWandCatalogueName;
    private int quantity;
}
