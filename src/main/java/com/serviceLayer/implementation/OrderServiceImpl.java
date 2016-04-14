package com.serviceLayer.implementation;

import com.dataLayer.DAO.EventDAO;
import com.dataLayer.DAO.MenuDAO;
import com.dataLayer.DAO.OrderDAO;
import com.dataLayer.DAO.UserDAO;
import com.model.*;
import com.serviceLayer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    MenuDAO menuDAO;
    @Autowired
    UserDAO userDAO;

    @Autowired
    EventDAO eventDAO;

    public void save(Order order) {
        orderDAO.save(order);
    }

    public void saveByRequest(HttpServletRequest req, HttpSession session) {
        int event_id = Integer.parseInt(req.getParameter("event_id"));
        Event event = eventDAO.getEventById(event_id);
        int item_id = Integer.parseInt(req.getParameter("item_id"));
        Item item = menuDAO.getItemById(item_id);
        int user_id = (int) session.getAttribute("userId");
        User user = userDAO.getUser(user_id);

        Order order = new Order(user,item,event);

        orderDAO.save(order);
    }

    @Override
    public List<Order> orderListOfUserByEvent(int userId, int eventId) {
        return orderDAO.orderListOfUserByEvent(userId,eventId);
    }

    @Override
    public List<Order> orderListOfEvent(int eventId) {
        return orderDAO.orderListOfEvent(eventId);
    }
}
