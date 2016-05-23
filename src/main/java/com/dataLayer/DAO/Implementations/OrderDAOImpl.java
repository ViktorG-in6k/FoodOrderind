package com.dataLayer.DAO.Implementations;

import com.dataLayer.DAO.Interfaces.OrderDAO;
import com.dataLayer.entity.base.Order;
import com.dataLayer.entity.base.Status;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveOrder(Order order) {
        Session session = sessionFactory.getCurrentSession();
        session.save(order);
    }

    @Override
    public Order getOrderByEventIdAndRestaurantIdAndPayerId(int eventId, int restaurantId, int payerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from order_info where event_id = :eventId and restaurant_id = :restaurantId and payer_id = :payerId");
        return (Order) query
                .setInteger("eventId", eventId)
                .setInteger("restaurantId", restaurantId)
                .setInteger("payerId", payerId)
                .uniqueResult();
    }


    @Override
    public List<Order> getOrdersByEventIdAndRestaurantId(int eventId, int restaurantId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from order_info where event_id = :eventId and restaurant_id = :restaurantId");
        return  query
                .setInteger("eventId", eventId)
                .setInteger("restaurantId", restaurantId)
                .list();
    }

    @Override
    public Order getOrderByOrderId(int orderId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from order_info where id = :orderId");
        return (Order) query
                .setInteger("orderId", orderId)
                .uniqueResult();
    }

    @Override
    public List<Order> getOrdersByEventId(int eventId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from order_info where event_id = :eventId");
        return (List<Order>) query
                .setInteger("eventId", eventId)
                .list();
    }

    @Override
    public Order getOrderByEventId(int eventId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from order_info where event_id = :eventId");
        return (Order) query
                .setInteger("eventId", eventId)
                .uniqueResult();
    }

    @Override
    public List<Order> getOrdersByRestaurantId(int restaurantId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from order_info where restaurant_id = :restaurantId");
        return (List<Order>) query
                .setInteger("restaurantId", restaurantId)
                .list();
    }

    @Override
    public void setPayer(int orderId, int payerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update order_info set payer_id = :payerId where id = :orderId");
        query
                .setInteger("orderId", orderId)
                .setInteger("payerId", payerId);
        query.executeUpdate();
    }

    @Override
    public void removePayer(int orderId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update order_info set payer_id = :payerId where id = :orderId");
        query
                .setInteger("orderId", orderId)
                .setString("payerId", null);
        query.executeUpdate();
    }

    @Override
    public void setPayer(int eventId, int restaurantId, int payerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update order_info set payer_id = :payerId where event_id = :eventId and restaurant_id = :restaurantId");
        query
                .setInteger("restaurantId", restaurantId)
                .setInteger("eventId", eventId)
                .setInteger("payerId", payerId);
        query.executeUpdate();
    }

    @Override
    public void setSplitBillId(int orderId, int billId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update order_info set bill_id = :billId where id = :orderId");
        query
                .setInteger("orderId", orderId)
                .setInteger("billId", billId);
        query.executeUpdate();
    }

    @Override
    public void changeOrderStatus(int orderId, Status status) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, orderId);
        order.setStatus(status);
        session.update(order);
    }

    @Override
    public List<Order> getOrderByEventIdAndRestaurantIdAndStatus(int eventId, int restaurantId, Status status) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from order_info where event_id = :eventId and restaurant_id = :restaurantId and status = :status");
        return  query
                .setInteger("eventId", eventId)
                .setInteger("restaurantId", restaurantId)
                .setString("status", status.toString())
                .list();
    }

    @Override
    public void updateOrder(Order order) {
        Session session = sessionFactory.getCurrentSession();
        session.update(order);
    }
}


