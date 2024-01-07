package com.demo.restaurant.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BillDetails")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bill_detail_id", updatable = false, nullable = false)
    private UUID billDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private Menu menuItem;

    @Column(name = "quantities", nullable = false)
    private int quantities;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    public BillDetail(Menu menuItem, int quantities) {
        this.menuItem = menuItem;
        this.quantities = quantities;
    }

    public BillDetail(Menu menuItem, int quantities, Bill bill) {
        this.menuItem = menuItem;
        this.quantities = quantities;
        this.bill = bill;
    }
}

