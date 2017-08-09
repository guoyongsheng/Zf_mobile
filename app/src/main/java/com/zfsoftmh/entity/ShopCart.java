package com.zfsoftmh.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljq
 * on 2017/7/28.
 * 购物车
 */

public class ShopCart {
    int account; //总计
    double totalPrice; //总价
    Map<FoodInfo,Integer> foodInfoMap;//食物的信息和食物的数量
    List<FoodInfo> list_food;

    ArrayList<FoodInfo> foodList;


    public ShopCart(){
        account=0;
        totalPrice=0;
        foodInfoMap=new HashMap<>();
        foodList=new ArrayList<>();
        }

    public int getAccount() {
        return account;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public HashMap<FoodInfo, Integer> getFoodInfoMap() {
        return (HashMap<FoodInfo, Integer>) foodInfoMap;
    }

    public boolean AddFood(FoodInfo foodInfo){
        //if 此处应该判断食物的数量
        int storage=foodInfo.getStorage();
        if(storage<=0){
            return false;
        }
        int num=0;
        if(foodInfoMap.containsKey(foodInfo)){
            num=foodInfoMap.get(foodInfo);
        }
        num+=1;
        foodInfoMap.put(foodInfo,num);

        totalPrice+=foodInfo.getPrice();
        account++;
        if(num<=0){
            //出错
            // retrun false;
        }

          return true;
    }

    public boolean RemoveFood(FoodInfo foodInfo){
        int num=0;
        if(foodInfoMap.containsKey(foodInfo)){
            num=foodInfoMap.get(foodInfo);
        }
        if(num<=0){
            return false;
        }
        num--;
        foodInfoMap.put(foodInfo,num);

        if(num==0){
            foodInfoMap.remove(foodInfo);
        }

        totalPrice-=foodInfo.getPrice();
        account--;
        return true;

    }


    public void clear(){
        this.account=0;
        this.totalPrice=0;
        this.foodInfoMap.clear();
    }



    public ArrayList<FoodInfo> getFoodList(){
        foodList.clear();
       foodList.addAll(foodInfoMap.keySet());
        int count=0;
       for(int i=0;i<foodList.size();i++){
           FoodInfo foodInfo=foodList.get(i);
           if(foodInfoMap.containsKey(foodInfo)){
               count=foodInfoMap.get(foodInfo);
           }
           foodList.get(i).setCount(count);
       }
        return foodList;

    }




}
