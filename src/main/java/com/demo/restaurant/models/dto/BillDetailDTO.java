package com.demo.restaurant.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailDTO {
    private UUID id;

    private MenuDTO menuItem;

    private int quantities;

    private double price;
}
