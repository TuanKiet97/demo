package com.demo.restaurant.services;

import com.demo.restaurant.models.dto.BillDTO;
import com.demo.restaurant.models.dto.OrderDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BillService {
    Set<BillDTO> getAllBillsOrderByOrderedTimeDesc(String sort, String direction, int size, int page);

    BillDTO createBill(List<OrderDTO> orderDTOsList);

    BillDTO updateBillDetailsFromBill(UUID billId, Set<UUID> billDetailIdsList);

    BillDTO confirmBillPaymentById(UUID billId);
}
