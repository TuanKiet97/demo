package com.demo.restaurant.services;

import com.demo.restaurant.models.dto.OrderDTO;
import com.demo.restaurant.models.entities.Bill;
import com.demo.restaurant.models.entities.BillDetail;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BillDetailService {
    Set<BillDetail> getBillDetailsByBillId(UUID billId);

    Set<BillDetail> addBillDetailsFromBill(Bill bill, List<OrderDTO> orderedList);

    Set<BillDetail> deleteBillDetailsFromBill(Bill bill, Set<UUID> billDetailIds);

    void deleteBillDetailById(UUID billDetailId);

    int countBillDetailByMenuItemId(UUID menuItemId);
}
