package com.code59.caffemall.controller.Order;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.dao.CartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@DS("coffee_guest")
@RestController
public class ClientClientCart {
    @Autowired
    CartDao cartDao;

    @RequestMapping("/AddShoppingCart")
    public String AddShoppingCart(@RequestBody TempAddtoCart tac)
    {
        System.out.println("测试成功");
        System.out.println(tac.getUsername());
        System.out.println(tac.getItem());


        Cart cart=new Cart(tac.getUsername(),tac.getItem().getIdFood(),tac.getItem().getName()
                ,tac.getItem().getType(),tac.getItem().getNum()
                ,tac.getItem().getPrice()*tac.getItem().getDiscount());
        cartDao.insert(cart);
        return "ok";
    }
}
