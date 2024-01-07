package com.demo.restaurant.models.mapper;

import com.demo.restaurant.models.dto.BillDTO;
import com.demo.restaurant.models.dto.BillDetailDTO;
import com.demo.restaurant.models.entities.Bill;
import com.demo.restaurant.models.entities.BillDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BillMapper {
    @Autowired
    private BillDetailMapper billDetailMapper;

    public Bill convertDTOToEntity(BillDTO billDTO) {
	Bill bill = new Bill();
	Set<BillDetail> billDetails = billDTO
		.getBillDetails()
		.stream()
		.map(billDetailDTO -> billDetailMapper.convertDTOToEntity(billDetailDTO))
		.collect(Collectors.toCollection(LinkedHashSet::new));

	bill.setBillId(billDTO.getId());
	bill.setBillDetails(billDetails);
	bill.setOrderedTime(billDTO.getOrderedTime());
	bill.setStatus(billDTO.getStatus());

	return bill;
    }

    public BillDTO convertEntityToDTO(Bill bill) {
	BillDTO billDTO = new BillDTO();
	int totalQuantities = 0;
	double totalPrice = 0;
	Set<BillDetailDTO> billDetails = bill.getBillDetails()
		.stream()
		.map(billDetail -> billDetailMapper.convertEntityToDTO(billDetail))
		.collect(Collectors.toCollection(LinkedHashSet::new));

	for (BillDetail menuItem : bill.getBillDetails()) {
	    totalQuantities += menuItem.getQuantities();
	    totalPrice += menuItem.getMenuItem().getPrice() * menuItem.getQuantities();
	}

	billDTO.setId(bill.getBillId());
	billDTO.setBillDetails(billDetails);
	billDTO.setTotalQuantities(totalQuantities);
	billDTO.setOrderedTime(bill.getOrderedTime());
	billDTO.setTotalPrice(totalPrice);
	billDTO.setStatus(bill.getStatus());

	return billDTO;
    }
}
