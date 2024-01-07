package com.demo.restaurant.services;

import com.demo.restaurant.common.enums.BillStatus;
import com.demo.restaurant.exceptions.ResponseBadRequest;
import com.demo.restaurant.exceptions.ResponseNotImplement;
import com.demo.restaurant.models.dto.BillDTO;
import com.demo.restaurant.models.dto.OrderDTO;
import com.demo.restaurant.models.entities.Bill;
import com.demo.restaurant.models.entities.BillDetail;
import com.demo.restaurant.models.mapper.BillMapper;
import com.demo.restaurant.repositories.BillRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static com.demo.restaurant.common.enums.BillStatus.PAID;
import static com.demo.restaurant.common.enums.BillStatus.UNPAID;

@Service
public class BillServiceImpl implements BillService {
    private static final Logger LOGGER = LogManager.getLogger(BillServiceImpl.class);

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private BillMapper billMapper;


    @Override
    public BillDTO createBill(List<OrderDTO> orderDTOsList) {
	Bill bill;
	Set<BillDetail> billDetails = new LinkedHashSet<>();
	LocalDateTime orderTime = LocalDateTime.now();
	if(!orderDTOsList.isEmpty()) {
	    bill = new Bill(billDetails , orderTime);
	    billDetails = billDetailService.addBillDetailsFromBill(bill,orderDTOsList);
	    bill.setBillDetails(billDetails);
	    billRepository.save(bill);
	    return billMapper.convertEntityToDTO(bill);
	} else {
	    LOGGER.warn("Orders list is empty");
	    throw new ResponseNotImplement("Orders list is empty. Please try again");
	}
    }

    @Override
    public BillDTO updateBillDetailsFromBill(UUID billId, Set<UUID> billDetailIdsList) {
	Bill bill = billRepository.findById(billId)
		.orElseThrow(() -> {
		    LOGGER.warn("This bill does not exist with id: {}", billId);
		    return new ResponseBadRequest("This bill does not exist with id: " + billId);
		});

	Set<BillDetail> updatedBillDetails = billDetailService.deleteBillDetailsFromBill(bill, billDetailIdsList);

	if (bill.getStatus().equals(UNPAID.getStatus())) {
	    if (updatedBillDetails.isEmpty()) {
		billRepository.deleteById(billId);
	    } else {
		bill.setBillDetails(updatedBillDetails);
	    }

	    return !updatedBillDetails.isEmpty() ? billMapper.convertEntityToDTO(bill) : null;
	} else {
	    LOGGER.warn("Bill with id '{}' has already been paid", billId);
	    throw new ResponseNotImplement("This bill has already been paid");
	}
    }


    @Override
    public BillDTO confirmBillPaymentById(UUID id) {
	Bill bill = billRepository.findById(id)
		.orElseThrow(()->{
		LOGGER.warn("This bill does not exist with id: {}", id);
		return new ResponseBadRequest("This bill does not exist with id: " + id);
		});
	if(bill.getStatus().equals(UNPAID.getStatus())) {
	    bill.setStatus(PAID.getStatus());
	    billRepository.save(bill);
	    deleteBillWhenPaidAndTimedOut(id);
	    return billMapper.convertEntityToDTO(bill);
	} else {
	    LOGGER.warn("Bill with id '{}' has already been paid", id);
	    throw new ResponseNotImplement("This bill has already been paid");
	}
    }


    private void deleteBillWhenPaidAndTimedOut(UUID billId) {
	Bill bill = billRepository.findById(billId)
		.orElseThrow(() -> {
		    LOGGER.warn("This bill does not exist with id: {}", billId);
		    return new ResponseBadRequest("This bill does not exist with id: " + billId);
		});

	if (bill.getStatus().equals(PAID.getStatus())) {
	    Timer timer = new Timer();
	    long timeToDelete = 1000L * 90L;
	    timer.schedule(new TimerTask() {
		@Override
		public void run() {
		    Set<BillDetail> billDetails = billDetailService.getBillDetailsByBillId(billId);
		    for (BillDetail billDetail : billDetails) {
			billDetailService.deleteBillDetailById(billDetail.getBillDetailId());
		    }
		    billRepository.deleteById(billId);
		}
	    }, timeToDelete);
	} else {
	    LOGGER.warn("Bill with id '{}' has already been unpaid", billId);
	    throw new ResponseBadRequest("This bill has already been unpaid");
	}
    }






    @Override
    public Set<BillDTO> getAllBillsOrderByOrderedTimeDesc(String sort, String direction, int size, int page) {
	return null;
    }






}
