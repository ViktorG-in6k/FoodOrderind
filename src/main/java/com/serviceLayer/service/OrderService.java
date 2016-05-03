package com.serviceLayer.service;

import com.DTOLayer.DTOEntity.orderDTO.OrderDTOList;
import com.DTOLayer.DTOEntity.orderDTO.OrderDTOListOfEachUser;
import com.model.Entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {
    public void save(Order order);

    public void saveByRequest(HttpServletRequest req, HttpSession session);

    public OrderDTOList orderListOfUserByEvent(int userId, int eventId);

    public void setResponsibleUser(int userId, int eventId, int restaurantId);

    public OrderDTOList orderListOfEvent(int eventId);

    OrderDTOList orderListOfUserByRestaurant(int eventId, int restaurantId);

    public void deleteItemFromOrder(int userId, int eventId, int itemId);

    public void deleteOneItemFromOrder(int userId, int eventId, int itemId);

    public void updateOrderedOfOrder(boolean ordered, int eventId, int itemId);

    public List<OrderDTOListOfEachUser> orderDTOListOfEachUser(int eventId);
}

