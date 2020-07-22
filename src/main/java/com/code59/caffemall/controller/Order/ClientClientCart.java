package com.code59.caffemall.controller.Order;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.controller.Discount.DiscountController;
import com.code59.caffemall.controller.Order.tempVar.FoodlistForRank;
import com.code59.caffemall.controller.Order.tempVar.OrderGenerating;
import com.code59.caffemall.dao.CartDao;
import com.code59.caffemall.dao.FoodDao;
import com.code59.caffemall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@DS("coffee_guest")
@RestController
public class ClientClientCart {
    @Autowired
    CartDao cartDao;
    @Autowired
    FoodDao foodDao;
    @Autowired
    CartService cartService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrderServices orderServices;
    @Autowired
    UserService userService;
    @Autowired
    MoneyService moneyService;

    @RequestMapping("/AddShoppingCart")
    public String AddShoppingCart(@RequestBody TempAddtoCart tac)
    {
        System.out.println("测试成功");
        System.out.println(tac.getUsername());
        System.out.println(tac.getItem());


        Cart cart=new Cart(tac.getUsername(),tac.getItem().getIdFood(),tac.getItem().getName()
                ,tac.getItem().getType(),tac.getItem().getNum()
                ,tac.getItem().getPrice()*tac.getItem().getDiscount());
        if(!cartService.get(tac.getUsername(),tac.getItem().getIdFood()).isEmpty())return "already";
        cartDao.insert(cart);
        return "ok";
    }
    @RequestMapping("/addShopingCartIndex")
    public String addShopingCartIndex(@RequestBody GuestAndFoodId gaf)
    {
        Food temp=menuService.get(gaf.getFoodid());
        if(temp==null)return "fail";
        Cart cart=new Cart(gaf.getUsername(),gaf.getFoodid(),temp.getName(),temp.getType(),1
        ,temp.getDiscount()*temp.getPrice());
        if(!cartService.get(gaf.getUsername(),gaf.getFoodid()).isEmpty())return "already";
        cartDao.insert(cart);
        return "ok";
    }
    @RequestMapping("/getShoppingCart")
    public String getShoppingCart(@RequestBody String idGuest)
    {

        idGuest=idGuest.substring(0,idGuest.length()-1);
        List<Cart> cartList=cartService.get(idGuest);
        List<TempCart>tempCarts=convertfromCartToTempCart(cartList);
        tempCarts.forEach(e->{
            System.out.println(e);
        });
        return JSON.toJSONString(convertfromCartToTempCart(cartList));
    }
    @RequestMapping("/GetLeadBoard")
    public String GetLeadBoard()
    {
        List<FoodlistForRank>foodList=moneyService.ranking();
        return JSON.toJSONString(foodList);
    }

    public List<TempCart> convertfromCartToTempCart(List<Cart>cartList)
    {
        List<TempCart>tempCarts=new ArrayList<>();

        //if(cartList==null)return tempCarts;
        for(int i=0;i<cartList.size();i++)
        {
            Cart e=cartList.get(i);
            Food ftemp =menuService.get(e.getIdFood());
            String urltemp=ftemp.getUrl();
            TempCart tc=new TempCart(e.getIdFood(),e.getName(),e.getType(),e.getPriceAfterDiscount(),urltemp
            ,1,false);
            tempCarts.add(tc);
        }
        System.out.println("e");
        return tempCarts;
    }
    @RequestMapping("/DelShoppingCart")
    public String DelShoppingCart(@RequestBody CartIdAndId cai)
    {
        if(cartService.delete(cai.getUsername(),cai.getFoodid())==1)return "ok";
        return "fail";
    }
    @RequestMapping("/SubmitOrder")
    public String SubmitOrder(@RequestBody TempOrderMessage tom)
    {
        List<Cart> carts=cartService.get(tom.getUsername());
        Guest guest=userService.get(tom.getUsername());
        List<String> result=orderServices.add(carts,guest,tom.getItem(), DiscountController.discount);
        result.forEach(r->{
            System.out.println("rrr"+r);

        });
        if(result.get(0).equals("Fail"))
        {
            StringBuffer sb=new StringBuffer();
            for(int i=1;i<result.size();i+=2)
            {
                if(i!=1)sb.append("，");
                sb.append(result.get(i)+"库存不够，");
                sb.append("还缺"+result.get(i+1)+"个");
            }
            return sb.toString();
        }
        cartService.delete(tom.getUsername());
        return "ok";
    }
    @RequestMapping("/addnum")
    public String addnum(@RequestBody CartIdAndIdAndNum car)
    {
        cartService.updatebyGuestFoodNum(car.getUsername(),car.getFoodid(),car.getNum());
        return "ok";
    }
}
class CartIdAndIdAndNum{
    private String username;
    private String foodid;
    private int num;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
class CartIdAndId{
    private String username;
    private String foodid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }
}
class TempOrderMessage{
    private String username;
    private OrderGenerating item;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OrderGenerating getItem() {
        return item;
    }

    public void setItem(OrderGenerating item) {
        this.item = item;
    }
}
class GuestAndFoodId{
    private String username;
    private String foodid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }
}

class TempCart{
    private String id;
    private String name;
    private String type;
    private double price;
    private String url;
    private int num;
    private boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public TempCart(String id, String name, String type, double price, String url, int num, boolean checked) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.url = url;
        this.num = num;
        this.checked = checked;
    }
}
