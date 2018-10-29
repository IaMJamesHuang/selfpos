package com.james.selfpos;

import com.james.selfpos.model.CommonItemModel;

import java.util.HashMap;

/**
 * Created by James on 2018/10/30.
 */
public class ItemCenter {

    private static HashMap<String, CommonItemModel> dataMap = new HashMap<>();

    static {
        dataMap.put("1234567890", createCGNN());
        dataMap.put("1234567891", createNRM());
        dataMap.put("1234567892", createSP());
        dataMap.put("1234567893", createKL());
        dataMap.put("1234567894", createXB());
    }

    public static CommonItemModel queryItem(String item) {
        return dataMap.get(item);
    }

    private static CommonItemModel createCGNN() {
        CommonItemModel model = new CommonItemModel();
        model.setItemCode("1234567890");
        model.setItemName("晨光牛奶");
        model.setInventoryUnit("瓶");
        model.setUnit("ml");
        model.setSpec(300);
        model.setPrice(2);
        model.setPicUrl("drawable://" + R.drawable.meal);
        return model;
    }

    private static CommonItemModel createNRM() {
        CommonItemModel model = new CommonItemModel();
        model.setItemCode("1234567891");
        model.setItemName("康师傅牛肉面");
        model.setInventoryUnit("包");
        model.setUnit("g");
        model.setSpec(300);
        model.setPrice(3);
        model.setPicUrl("drawable://" + R.drawable.niuroumian);
        return model;
    }

    private static CommonItemModel createSP() {
        CommonItemModel model = new CommonItemModel();
        model.setItemCode("1234567892");
        model.setItemName("乐事薯片");
        model.setInventoryUnit("包");
        model.setUnit("g");
        model.setSpec(200);
        model.setPrice(3);
        model.setPicUrl("drawable://" + R.drawable.shupian);
        return model;
    }

    private static CommonItemModel createKL() {
        CommonItemModel model = new CommonItemModel();
        model.setItemCode("1234567893");
        model.setItemName("百事可乐");
        model.setInventoryUnit("瓶");
        model.setUnit("ml");
        model.setSpec(250);
        model.setPrice(2);
        model.setPicUrl("drawable://" + R.drawable.kele);
        return model;
    }

    private static CommonItemModel createXB() {
        CommonItemModel model = new CommonItemModel();
        model.setItemCode("1234567894");
        model.setItemName("旺旺雪饼");
        model.setInventoryUnit("袋");
        model.setUnit("g");
        model.setSpec(300);
        model.setPrice(3);
        model.setPicUrl("drawable://" + R.drawable.wangwang);
        return model;
    }

}
