package com.demo.restaurant.models.mapper;

import com.demo.restaurant.models.dto.BillDetailDTO;
import com.demo.restaurant.models.entities.BillDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillDetailMapper {
    @Autowired
    private MenuMapper menuMapper;

    public BillDetailDTO convertEntityToDTO(BillDetail billDetail) {
	BillDetailDTO billDetailDTO = new BillDetailDTO();
	double price = billDetail.getMenuItem().getPrice() * billDetail.getQuantities();

	billDetailDTO.setId(billDetail.getBillDetailId());
	billDetailDTO.setMenuItem(menuMapper.convertEntityToDTO(billDetail.getMenuItem()));
	billDetailDTO.setQuantities(billDetail.getQuantities());
	billDetailDTO.setPrice(price);

	return billDetailDTO;
    }

    public BillDetail convertDTOToEntity(BillDetailDTO billDetailDTO) {
	BillDetail billDetail = new BillDetail();

	billDetail.setBillDetailId(billDetailDTO.getId());
	billDetail.setMenuItem(menuMapper.convertDTOToEntity(billDetailDTO.getMenuItem()));
	billDetail.setQuantities(billDetailDTO.getQuantities());

	return billDetail;
    }
}
