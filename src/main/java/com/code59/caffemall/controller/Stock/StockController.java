package com.code59.caffemall.controller.Stock;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.DataSourceDTO;
import com.code59.caffemall.bean.ShopStock;
import com.code59.caffemall.bean.Stock;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.controller.Order.staticVar.Var;
import com.code59.caffemall.dao.ShopStockDao;
import com.code59.caffemall.dao.StockOnSellDao;
import com.code59.caffemall.service.StockMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@DS("coffee_shop")
public class StockController {
    @Autowired
    DataSource dataSource;

    @Autowired
    DataSourceCreator dataSourceCreator;
    @Autowired
    StockMenuService stockMenuService;
    @Autowired
    ShopStockDao shopStockDao;
    @Autowired
    StockOnSellDao stockOnSellDao;
    public String testda()
    {

        return "ok";
    }
    @RequestMapping("/SearchStockg1")
    public String Searchstock(@RequestBody String name)
    {

//        DataSourceDTO dto = new DataSourceDTO();
//        String driverName = "com.mysql.cj.jdbc.Driver";
//        String baseUrl = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
//        String userName="root";
//        String password="46210707067asdf";
//        String poolName= "t1";
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try(Connection connection = DriverManager.getConnection(baseUrl,userName,password); Statement statement = connection.createStatement();) {
//            String sql = String.format("CREATE DATABASE %s",poolName);
//            statement.executeUpdate(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        dto.setDriverClassName(driverName);
//        dto.setPassword("46210707067asdf");//输入你的数据库的密码
//        dto.setPollName(poolName);//输入你想要的数据源名字，为shop1,shop2...
//        String url = String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=UTC",poolName);
//        dto.setUrl(url);//配置数据源URL
//        dto.setUsername("root");//输入你的用户名
//        DataSourceProperty dataSourceProperty = new DataSourceProperty();
//        BeanUtils.copyProperties(dto, dataSourceProperty);
//        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
//        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
//        ds.addDataSource(dto.getPollName(), dataSource);
//        System.out.println(ds.getCurrentDataSources().keySet());



        name=name.substring(0,name.length()-1);
        if(name.equals("null"))
        {
            System.out.println("y\ny\ny\ny\n");

            return JSON.toJSONString(stockMenuService.showOnSellStock(null));
        }

        System.out.println("n\nn\nn\n");
        List<Stock>stocks= stockMenuService.showOnSellStock(name);
        return JSON.toJSONString(stocks);
    }
    @RequestMapping("/foodEditShopg1")
    //public String foodEdit(@RequestBody Stock stock)
    public String foodEdit(@RequestBody String idfood)
    {
//        System.out.println(stock);
//        QueryWrapper wrapper=new QueryWrapper();
//        wrapper.eq("id_food",stock.getIdFood());
//        ShopStock temp=new ShopStock();
//        temp.setIdfood(stock.getIdFood());
//        temp.setNum(stock.getNum());
//        int num=shopStockDao.update(temp,wrapper);
//        if(num==1)return "ok";
//        return "fail";
        return "ok";
    }
    @RequestMapping("/StockDownShelf")
    public String stockdownshelf(@RequestBody String idfood)
    {
        idfood=idfood.substring(0,idfood.length()-1);
        stockMenuService.undercarrage(idfood);
        System.out.println("测试idfood"+idfood);
        return "ok";
    }
    @RequestMapping("SearchDownShelfStock")
    public String SearchDownshelfstock(@RequestBody String name)
    {
        name=name.substring(0,name.length()-1);
        return JSON.toJSONString(stockMenuService.showUnderSellStock(name));
    }
}
