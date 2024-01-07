package com.demo.restaurant.common.enums;

public enum BillStatus {
    UNPAID("unpaid"),
    PAID("paid");

    private final String status;

    BillStatus(String status) {
	this.status = status;
    }

    public String getStatus() {
	return status;
    }
}
