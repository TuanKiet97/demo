package com.demo.restaurant.controller;

import com.demo.restaurant.constant.Constant;
import com.demo.restaurant.models.dto.BillDTO;
import com.demo.restaurant.models.dto.OrderDTO;
import com.demo.restaurant.responses.ResponseObject;
import com.demo.restaurant.services.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bills")
public class BillController {

    @Autowired
    private BillServiceImpl billService;

    @PostMapping
    public ResponseEntity<ResponseObject> createBill(@RequestBody List<OrderDTO> orderDTOList) {
	BillDTO bill = billService.createBill(orderDTOList);
	String message = "Create the bill successfully";
	return ResponseEntity
		.status(HttpStatus.CREATED)
		.body(new ResponseObject(201, message, bill));
    }



    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBillDetailsFromBill(@PathVariable UUID id,
								    @RequestBody Set<UUID> billDetailIdsList) {
	BillDTO billUpdated = billService.updateBillDetailsFromBill(id, billDetailIdsList);
	boolean hasValue = Objects.nonNull(billUpdated);
	String message = hasValue ? "Update bill successfully" : "This bill deleted because bill detail list is empty";
	Object data = hasValue ? billUpdated : Constant.EMPTY;

	return ResponseEntity
		.status(HttpStatus.OK)
		.body(new ResponseObject(200, message, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> confirmBillPaymentById(@PathVariable UUID id) {
	BillDTO billDTO = billService.confirmBillPaymentById(id);
	String message = "Bill payment confirm successfully";

	return ResponseEntity
		.status(HttpStatus.OK)
		.body(new ResponseObject(200, message, billDTO));
    }















    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllBillsOrderByOrderedTimeDesc(@RequestParam(name = "sort", required = false, defaultValue = "orderedTime") String sort,
									    @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction,
									    @RequestParam(name = "size", required = false, defaultValue = "4") int size,
									    @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
	Set<BillDTO> bills = billService.getAllBillsOrderByOrderedTimeDesc(sort, direction, size, page);
	String message = "Get all bills successfully";

	return ResponseEntity
		.status(HttpStatus.OK)
		.body(new ResponseObject(200, message, bills));
    }




}
