package com.cx.mapper;

import com.cx.pojo.Orders;
import com.cx.pojo.OrdersCustom;
import com.cx.pojo.User;

import java.util.List;

public interface UserMapperOrders {
    public List<OrdersCustom> findOrdersUser() throws Exception;
    //查询订单，关联查询用户信息,使用resultMap
    public List<Orders> findOrdersUserResultMap() throws Exception;

    //查询订单（关联用户）及订单明细
    public List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;
    //查询用户购买商品信息
    public List<User> findUserAndItemsResultMap() throws Exception;

    //查询订单，关联用户查询，用户查询用的是延迟加载
    public List<Orders> findOrdersUserLazyLoading() throws Exception;
}
