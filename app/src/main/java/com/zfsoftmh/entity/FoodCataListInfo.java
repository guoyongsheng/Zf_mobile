package com.zfsoftmh.entity;

import java.util.List;

/**
 * Created by ljq
 * on 2017/7/28.
 * 某个食物类别以及该类别下的所有食物
 */

public class FoodCataListInfo {
    String foodcataId;
    String foodcataName;
    String isactive;

    List<FoodInfo> foodlist;


    public String getFoodcataId() {
        return foodcataId;
    }

    public void setFoodcataId(String foodcataId) {
        this.foodcataId = foodcataId;
    }

    public String getFoodcataName() {
        return foodcataName;
    }

    public void setFoodcataName(String foodcataName) {
        this.foodcataName = foodcataName;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public List<FoodInfo> getFoodlist() {
        return foodlist;
    }

    public void setFoodlist(List<FoodInfo> foodlist) {
        this.foodlist = foodlist;
    }
}
