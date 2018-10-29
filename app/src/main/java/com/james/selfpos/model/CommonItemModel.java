package com.james.selfpos.model;

import java.io.Serializable;

/**
 * Created by James on 2018/10/14.
 */
public class CommonItemModel implements Serializable{

    private String itemName;
    private String itemCode;
    private String picUrl;
    /*
    * 单位 eg：毫升
    * */
    private String unit;
    /*
    * 库存单位 eg：瓶
    * */
    private String inventoryUnit;
    private int spec;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInventoryUnit() {
        return inventoryUnit;
    }

    public void setInventoryUnit(String inventoryUnit) {
        this.inventoryUnit = inventoryUnit;
    }

    public int getSpec() {
        return spec;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }
}
