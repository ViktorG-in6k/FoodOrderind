package com.controllers;

import com.model.Event;
import com.model.Menu;
import com.model.Restaurant;
import com.serviceLayer.service.EventService;
import com.serviceLayer.service.MenuService;
import com.serviceLayer.service.RestaurantService;
import com.serviceLayer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    MenuService menuService;

    @RequestMapping(value = "/")
    public String getMain(Model model) {
        return "main";
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public String events(HttpServletRequest req, HttpSession session) {
        String email = req.getParameter("email");
        session.setAttribute("backPage","/");
        userService.saveUser(email);

        session.setAttribute("userMail", email);
        session.setAttribute("allEvents", eventService.getListOfAllEvents());

        return "events";
    }

    @RequestMapping(value = "/events")
    public String events(HttpSession session) {
        session.setAttribute("allEvents", eventService.getListOfAllEvents());
        session.setAttribute("backPage","/events");

        return "events";
    }

    @RequestMapping(value = "/sing_out")
    public String sing_out(HttpSession session) {
        session.setAttribute("userMail", null);
        return "main";
    }

    @RequestMapping(value = "/new_item", method = RequestMethod.POST)
    public String new_item(HttpServletRequest req,HttpSession session) {

        String name = req.getParameter("name");
        String description = req.getParameter("discript");
        String URLimage = req.getParameter("image");
        BigDecimal price = new BigDecimal(req.getParameter("price"));

        Restaurant restaurant =(Restaurant)session.getAttribute("restaurant");
        System.out.println(restaurant.getName());
        Menu item = new Menu(restaurant, name, description, URLimage, price);

        menuService.save(item);
        //session.setAttribute("menu", restaurantService.getRestaurantById(restaurant.getId()).getMenu());
        String ref = req.getHeader("Referer");
        return "redirect:" + ref;
    }

    @RequestMapping(value = "/new_event", method = RequestMethod.POST)
    public String new_Event(HttpServletRequest req) {

        String name = req.getParameter("name");
        String description = req.getParameter("discript");
        String URLimage = req.getParameter("image");
        LocalDateTime date = LocalDateTime.parse(req.getParameter("date"));
        Event event = new Event(name, description, URLimage, date);
        eventService.save(event);
        String ref = req.getHeader("Referer");
        return "redirect:" + ref;
    }

    @RequestMapping(value = "/new_restaurant", method = RequestMethod.POST)
    public String new_restaurant(HttpServletRequest req) {

        String name = req.getParameter("name");
        String description = req.getParameter("discript");
        String URLimage = req.getParameter("image");
        Restaurant restaurant = new Restaurant(name, description, URLimage);
        restaurantService.save(restaurant);
        String ref = req.getHeader("Referer");
        return "redirect:" + ref;
    }

    @RequestMapping(value = "/events/event_{id}")
    public String get_restaurants(HttpSession session, @PathVariable("id") String id) {
        session.setAttribute("eventId", id);
        session.setAttribute("allRestaurants", restaurantService.getListOfAllRestaurant());
        session.setAttribute("backPage","/events");
        return "restaurants";
    }

    @RequestMapping(value = "/events/event_{event}/restaurant_{id}")
    public String get_menu(HttpSession session,@PathVariable("event") int event, @PathVariable("id") int id) {
        session.setAttribute("restaurant",restaurantService.getRestaurantById(id));
        System.out.println(restaurantService.getRestaurantById(id).getName());
        System.out.println(restaurantService.getRestaurantById(id).getMenu().size());
        for (Menu item: restaurantService.getRestaurantById(id).getMenu()) {
            System.out.println(item.getName());
            System.out.println(item.getPrice());
        }
        session.setAttribute("Menu", restaurantService.getRestaurantById(id).getMenu());
        session.setAttribute("backPage","/events/event_"+event);
        return "menu";
    }
}
