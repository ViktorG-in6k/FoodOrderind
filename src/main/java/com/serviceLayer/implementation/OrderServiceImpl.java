package com.serviceLayer.implementation;

import com.dataLayer.DAO.Interfaces.OrderDAO;
import com.dataLayer.entity.DTO.orderDTO.OrderPlacementStatus;
import com.dataLayer.entity.DTO.orderItemDTO.OrderItemDTO;
import com.dataLayer.entity.DTO.orderItemDTO.OrderItemRequest;
import com.dataLayer.entity.base.*;
import com.dataLayer.entity.external.SplitBillApi;
import com.dataLayer.splitBillDTO.product.ProductRequestJSON;
import com.serviceLayer.googleAuthentication.CurrentUserDetails;
import com.serviceLayer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    EventService eventService;
    @Autowired
    ItemService itemService;
    @Autowired
    RestaurantService restaurantService;

    @Override
    public void save(Order order) {
        orderDAO.saveOrder(order);
    }

    @Override
    public void save(OrderItemRequest itemRequest) {
        orderDAO.saveOrder(new Order());
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderDAO.getOrderByOrderId(orderId);
    }

    @Override
    public List<Order> getOrdersByEventIdAndRestaurantId(int eventId, int restaurantId) {
        return orderDAO.getOrdersByEventIdAndRestaurantId(eventId, restaurantId);
    }

    @Override
    public List<Order> getOrdersByEventIdAndRestaurantIdAndStatus(int eventId, int restaurantId, Status status) {
        return orderDAO.getOrderByEventIdAndRestaurantIdAndStatus(eventId, restaurantId, status);
    }

    @Override
    public void setPayerById(int orderId, int payerId) {
        orderDAO.setPayer(orderId, payerId);
    }

    @Override
    public void removePayer(int orderId) {
        orderDAO.removePayer(orderId);
    }

    @Transactional
    @Override
    public OrderPlacementStatus getOrderPlacementStatus(Order order, Authentication authentication) {
        Set<User> participants = new HashSet<>();
        if (order != null) {
            List<OrderItemDTO> orderItems = orderItemService.getOrderItemListDTOByOrderId(order.getId());

            for (OrderItemDTO itemDTO : orderItems) {
                participants.add(userService.getUser(itemDTO.getUser().getId()));
            }

            int participantsAmount = participants.size();
            return new OrderPlacementStatus(order, participantsAmount, isMineOrder(order, authentication));
        }
        return null;
    }

    @Override
    public OrderPlacementStatus getOrderPlacementStatusByOrderId(Order order, Authentication authentication){
        Set<User> participants = new HashSet<>();

            List<OrderItemDTO> orderItems = orderItemService.getOrderItemListDTOByOrderId(order.getId());

            for (OrderItemDTO itemDTO : orderItems) {
                participants.add(userService.getUser(itemDTO.getUser().getId()));
            }

            int participantsAmount = participants.size();
            return new OrderPlacementStatus(order, participantsAmount, isMineOrder(order, authentication));
    }


    @Override
    public boolean isMineOrder(Order order, Authentication authentication) {
        int currentUserId = ((CurrentUserDetails) authentication.getPrincipal()).getUser().getId();

        for (OrderItem item : order.getOrderItems()) {
            if (item.getUser().getId() == currentUserId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void changeOrderStatus(int orderId, Status status, int splitBillId) throws IOException {
        orderDAO.changeOrderStatus(orderId, status);
        if (status.equals(Status.SPLIT_BILL)) {
            sendOrderToSplitBill(orderId, splitBillId);
        }
    }

    @Override
    public Order createOrder(int eventId, int restaurantId) {
        Event event = eventService.getEventById(eventId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Order order = new Order(restaurant, event, Status.PENDING);
        save(order);
        return order;
    }

    @Override
    public void sendOrderToSplitBill(int orderId, int splitBillId) throws IOException {
        SplitBillApi splitBillApi = new SplitBillApi();
        Order order = getOrderById(orderId);

        List<OrderItemDTO> orders = orderItemService.getOrderItemListDTOByOrderId(orderId);
        List<OrderItemDTO> commonOrders = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : orders) {
            if (commonOrders.contains(orderItemDTO)) {
                for (OrderItemDTO orderItem : commonOrders) {
                    if (orderItem.getItem().getId() == orderItemDTO.getItem().getId()) {
                        orderItem.setItemAmount(orderItem.getItemAmount() + orderItemDTO.getItemAmount());
                    }
                }
            } else commonOrders.add(orderItemDTO);
        }

        splitBillApi.login(order.getPayer().getEmail());

        splitBillApi.newBill(splitBillId);
        orderDAO.setSplitBillId(orderId, splitBillApi.getBillId());

        for (OrderItemDTO orderItem : commonOrders) {
            ProductRequestJSON productRequestJSON = new ProductRequestJSON(
                    orderItem.getItemAmount(),
                    orderItem.getItem().getName(),
                    orderItem.getItem().getPrice()
            );
            splitBillApi.newProduct(productRequestJSON);
        }
    }

    @Override
    public List<OrderPlacementStatus> getOrderPlacementStatusByEventIdAndRestaurantId(int eventId, int restaurantId, Authentication authentication) {
        List<Order> orders = orderDAO.getOrdersByEventIdAndRestaurantId(eventId, restaurantId);
        return  orders.stream().map(order -> getOrderPlacementStatus(order, authentication)).collect(Collectors.toList());
    }

    @Override
    public void updatePercentageDiscount(int orderId, double percentageDiscount) {
        Order order = orderDAO.getOrderByOrderId(orderId);
        order.setPercentageDiscount(new BigDecimal(percentageDiscount));
        orderDAO.updateOrder(order);
    }
}

