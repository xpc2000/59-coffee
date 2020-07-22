package com.code59.caffemall.controller.Order;

import com.alibaba.fastjson.JSON;
import com.code59.caffemall.bean.*;
import com.code59.caffemall.controller.Discount.DiscountController;
import com.code59.caffemall.controller.Order.ClientAndShop.OrderTemp;
import com.code59.caffemall.controller.Order.staticVar.OrderFinal;
import com.code59.caffemall.controller.Order.staticVar.OrdersAll;
import com.code59.caffemall.controller.Order.tempVar.NameAndId;
import com.code59.caffemall.controller.Order.tempVar.OrderSingle;
import com.code59.caffemall.dao.CartDao;
import com.code59.caffemall.dao.ShopDao;
import com.code59.caffemall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

class Temp{
    private Cart itemm;
    private String user;

    public Cart getItemm() {
        return itemm;
    }

    public void setItemm(Cart itemm) {
        this.itemm = itemm;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
@RestController
public class ClientClient {
    //前期准备：提取出所有门店
    @Autowired
    OrderServices orderServices;
    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;
    @Autowired
    BranchMenuService branchMenuService;
    @Autowired
    StockMenuService stockMenuService;
    @Autowired
    ShopDao shopDao;
    @Autowired
    CartDao cartDao;
    @RequestMapping("/showOrder")///////废弃
    public String showorder(String guestId)
    {
        return JSON.toJSONString(OrdersAll.getOrderByGuestId(guestId).getCartsAll());
    }
//    @RequestMapping("/addOrder")//提交购物车////废弃
//    public String addorder(@RequestBody OrderFinal order)
//    {
//        String guestId= order.getGuestId();
//        Guest gtemp=userService.get(guestId);
//        orderServices.add(order.getCartsAll(),order.getShopId(),gtemp,DiscountController.discount);
//        OrdersAll.deleteOrder(order.getGuestId());
//        return "ok";
//    }
    //SerchFood功能在superAdmin中已经有了
    @RequestMapping("/addOrderFood")//下单（给某个餐品下单）/////废弃
    public String addorderfood(@RequestBody OrderSingle temp)//Temp包含Food变量和String(guest_id)变量
    {
        OrdersAll.addOrder(temp);
        return "ok";
    }
    @RequestMapping("/SearchDrink")
    public String searchdrink(@RequestBody NameAndId nameAndId)
    {
       List<StockOnSell>list= branchMenuService.getFoodByTypeNameShopid("drink%"
               , nameAndId.getName(),nameAndId.getShopid());
       System.out.println(nameAndId.getShopid());
       List<Stock>stocks=stockMenuService.convertFromStockOnsellToStock(list);
       return JSON.toJSONString(stocks);
    }
    @RequestMapping("/SearchSnack")
    public String searchSnack(@RequestBody NameAndId nameAndId)
    {
        List<StockOnSell>list= branchMenuService.getFoodByTypeNameShopid("snack%"
                , nameAndId.getName(),nameAndId.getShopid());
        System.out.println(nameAndId.getShopid());
        List<Stock>stocks=stockMenuService.convertFromStockOnsellToStock(list);
        return JSON.toJSONString(stocks);
    }

    @RequestMapping("/SelectStores")
    public String SelectStores()
    {
        List<Shop>shopList=shopDao.selectList(null);
        List<TempShop>tempShopList=new ArrayList<>();
        shopList.forEach(s->{
            TempShop ts=new TempShop(s.getId(),s.getName());
            tempShopList.add(ts);
        });
        return JSON.toJSONString(tempShopList);
    }
    @RequestMapping("/SearchShopOrderClient")
    public String SearchShopOrderClient(@RequestBody String guestid)
    {
        guestid=guestid.substring(0,guestid.length()-1);
        List<Order_shop>order_shops=orderServices.showOrderShopsClient(guestid);
        List<OrderTemp>orderTemps=convertToOrderTemp(order_shops);
        return JSON.toJSONString(orderTemps);
    }
    @RequestMapping("/Receipt")
    public String Receipt(@RequestBody String orderId)
    {
        orderId = orderId.substring(0, orderId.length() - 1);
        Order_shop orderShopTemp = orderServices.get(orderId);
        if (orderShopTemp != null) {
            orderShopTemp.setBeOver("y");
        }
        if (orderServices.update(orderShopTemp) == 1)
            return "ok";
        else
            return "fail";
    }
    public List<OrderTemp> convertToOrderTemp(List<Order_shop> orderdetails) {
        List<OrderTemp> orderTemps = new ArrayList<>();
        orderdetails.forEach(order_shop -> {
            String guestName = userService.get(order_shop.getIdGuest()).getName();
            OrderTemp ot = new OrderTemp(order_shop.getId(), order_shop.getTime(), guestName, order_shop.getDeliverAddress(), order_shop.getPhone(),
                    order_shop.getTotalPrice(), order_shop.getBeDeliver(), order_shop.getBeOver());
            orderTemps.add(ot);
        });
        return orderTemps;
    }
}
class TempShop{
    private String id;
    private String name;

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

    public TempShop(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
class TempAddtoCart{
    private String username;
    private Stock item;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Stock getItem() {
        return item;
    }

    public void setItem(Stock item) {
        this.item = item;
    }
}
