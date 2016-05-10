package com.dataLayer.DAO;

import com.model.Entity.OrderItem;

import java.util.List;

public interface OrderItemDAO {
    void saveOrderItem(OrderItem orderItem);

    OrderItem getOrderItem(int userId, int itemId, int orderId);

    List<OrderItem> getOrderListByOrderIdAndUserId(int userId, int orderId);

    void updateAmount(OrderItem orderItem, int amount);
}
