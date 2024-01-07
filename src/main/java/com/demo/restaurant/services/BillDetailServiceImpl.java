package com.demo.restaurant.services;

import com.demo.restaurant.exceptions.ResponseBadRequest;
import com.demo.restaurant.exceptions.ResponseNotImplement;
import com.demo.restaurant.models.dto.BillDTO;
import com.demo.restaurant.models.dto.OrderDTO;
import com.demo.restaurant.models.entities.Bill;
import com.demo.restaurant.models.entities.BillDetail;
import com.demo.restaurant.models.entities.Menu;
import com.demo.restaurant.models.mapper.BillDetailMapper;
import com.demo.restaurant.repositories.BillDetailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BillDetailServiceImpl implements BillDetailService {
    private static final Logger LOGGER = LogManager.getLogger(BillDetailServiceImpl.class);

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private MenuServices menuServices;

    @Autowired
    private BillDetailMapper billDetailMapper;

    @Override
    public Set<BillDetail> getBillDetailsByBillId(UUID billId) {
	boolean existBill = billDetailRepository.existsByBillBillId(billId);

	if (existBill) {
	    return billDetailRepository.findByBillBillId(billId);
	} else {
	    LOGGER.warn("Bill with id '{}' does not exist or deleted", billId);
	    throw new ResponseBadRequest("This bill does not exist or deleted");
	}
    }

    @Override
    public Set<BillDetail> addBillDetailsFromBill(Bill bill, List<OrderDTO> orderedList) {
	if (!orderedList.isEmpty()) {
	    return convertOrderedDTOsListToBillDetailsSet(bill, orderedList)
		    .stream()
		    .map(billDetail -> billDetailRepository.save(billDetail))
		    .collect(Collectors.toCollection(LinkedHashSet::new));
	} else {
	    UUID billId = bill.getBillId();
	    LOGGER.warn("Order list of bill with id '{}' is empty", billId);
	    throw new ResponseNotImplement("Can't take action because order list is empty");
	}
    }

    @Override
    public Set<BillDetail> deleteBillDetailsFromBill(Bill bill, Set<UUID> billDetailIds) {
	Set<BillDetail> billDetails = bill.getBillDetails();

	for (UUID billDetailId : billDetailIds) {
	    billDetails.removeIf(billDetail -> billDetail.getBillDetailId().equals(billDetailId));
	    billDetailRepository.deleteById(billDetailId);
	}

	return !billDetails.isEmpty() ? billDetails : Collections.emptySet();
    }

    @Override
    public void deleteBillDetailById(UUID billDetailId) {
	if (billDetailRepository.existsById(billDetailId)) {
	    billDetailRepository.deleteById(billDetailId);
	} else {
	    LOGGER.warn("This bill detail does not exits with id: {}", billDetailId);
	    throw new ResponseBadRequest("This bill detail does not exits with id: " + billDetailId);
	}
    }

    @Override
    public int countBillDetailByMenuItemId(UUID menuItemId) {
	return billDetailRepository.countByMenuItemMenuItemId(menuItemId);
    }

    private Set<BillDetail> convertOrderedDTOsListToBillDetailsSet(Bill bill, List<OrderDTO> orderDTOsList) {
	Map<UUID, Integer> ordersMap = new LinkedHashMap<>();
	orderDTOsList.forEach(orderDTO -> {
	    Integer sum = ordersMap.getOrDefault(orderDTO.getMenuId(), 0);
	    ordersMap.put(orderDTO.getMenuId(), orderDTO.getQuantities() + sum);
	});

	return ordersMap.entrySet().stream()
		.map(order -> {
		    Menu menu = menuServices.getOneProductById(order.getKey());
		    int quantities = order.getValue();

		    return new BillDetail(menu, quantities, bill);
		}).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
