package com.demo.restaurant.common.enums;

public enum MenuStatus {
    ON_SELL("on sell"),
    STOP_SELL("stop sell");

    private final String status;

    MenuStatus(String status) {
	this.status = status;
    }

    public String getStatus() {
	return status;
    }
}
