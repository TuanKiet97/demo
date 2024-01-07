package com.demo.restaurant.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    public static final String PERCENT = "%";
    public static final String EMPTY = "";

    //    Menu item properties
    public static final String MENU_ITEM_NAME_PROPERTY = "name";
    public static final String MENU_ITEM_DESCRIPTION_PROPERTY = "description";
    public static final String MENU_ITEM_TYPE_PROPERTY = "type";
    public static final String MENU_ITEM_STATUS_PROPERTY = "status";
}
