package com.code59.caffemall.controller.Stock;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.bean.ShopStock;
import com.code59.caffemall.bean.Stock;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.service.MenuService;
import com.code59.caffemall.service.StockMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@DS("coffee_shop")
public class AddFoodToShop {
    @Autowired
    StockMenuService stockMenuService;
    @Autowired
    MenuService menuService;

    @RequestMapping("/getFoodNotInShop")
    public String getFoodNotInShop(@RequestBody String shopid) {
        String shopid_1 = shopid.substring(0, shopid.length() - 1);
        List<StockOnSell> list = stockMenuService.list(shopid_1);
        List<String> ids = new ArrayList<>();
        List<Food> foodListAll = menuService.list();
        List<Food> foodNotInShop = new ArrayList<>();
        list.forEach(e -> {
            ids.add(e.getIdfood());
        });

        List<Stock> stocks = new ArrayList<>();
        foodListAll.forEach(e -> {
            if (!ids.contains(e.getId())) {
                Stock stock = new Stock(e.getId(), shopid_1, e.getName(), e.getType(), e.getPrice(), e.getDiscount()
                        , "u", e.getUrl(), 0);
                stocks.add(stock);
            }
        });
        return JSON.toJSONString(stocks);
    }

    @RequestMapping("/addfoodinshop")
    public String addfoodinshop(@RequestBody AddFoodTemp aft) {

        Food food = menuService.get(aft.getIdFood());
        StockOnSell stockOnSell = new StockOnSell();
        stockOnSell.setIdfood(aft.getIdFood());
        stockOnSell.setSellOut("u");
        stockOnSell.setName(food.getName());
        stockOnSell.setType(food.getType());
        stockOnSell.setPrice(food.getPrice());
        stockOnSell.setDiscount(food.getDiscount());
        stockOnSell.setUrl(food.getUrl());
        stockOnSell.setIdShop(aft.getIdShop());
        stockMenuService.add(stockOnSell);
        ShopStock shopStock = new ShopStock();
        shopStock.setIdShop(aft.getIdShop());
        shopStock.setIdfood(aft.getIdFood());
        shopStock.setNum(aft.getNum());
        stockMenuService.add(shopStock);
        return "ok";
    }
}

class AddFoodTemp {
    private String idShop;
    private String idFood;
    private int num;

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}