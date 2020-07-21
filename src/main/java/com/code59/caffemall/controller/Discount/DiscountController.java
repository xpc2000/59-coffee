package com.code59.caffemall.controller.Discount;

import com.code59.caffemall.bean.Discount;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountController {
    static public Discount discount=new Discount();
    static{
        discount.setPayments_1(90);
        discount.setPayments_2(180);
        discount.setPayments_3(300);
        discount.setMinus_1(20);
        discount.setMinus_2(50);
        discount.setMinus_3(100);
    }
    @RequestMapping("/showdiscount")
    public Discount showdiscount()
    {
        return discount;
    }
    @RequestMapping("/editdiscount")
    public String Editdiscount(@RequestBody Discount disc)
    {
        discount=disc;
        return "ok";
    }

}
