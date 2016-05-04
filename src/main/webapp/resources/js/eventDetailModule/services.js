var services = angular.module('eventApp.services', []);

services.factory("OrderListService", function ($http, $rootScope) {
    var orderListService = {};

    orderListService.addToOrder = function (eventId, ItemId) {
        $http({
            method: 'POST',
            url: '/add_to_order',
            params: {
                event_id: eventId,
                item_id: ItemId
            }
        }).then(function () {
            orderListService.updateOrderList();
        });
    };

    orderListService.removeFromOrder = function (eventId, itemId) {
        $http({
            method: 'GET',
            url: "/remote_from_order" + itemId + "_" + eventId
        }).then(function () {
            orderListService.updateOrderList();
        });
    };

    orderListService.removeOneItemFromOrder = function (eventId, itemId) {
        $http({
            method: 'GET',
            url: '/remote_one_item_from_order' + itemId + "_" + eventId
        }).then(function () {
            orderListService.updateOrderList();
        });
    };

    orderListService.updateOrderList = function () {
        $http.get("/CommonOrderJson_" + $rootScope.eventId+"/"+$rootScope.currentRestaurant).success(function (data) {
            $rootScope.myOrders = data;
        })
    };

    orderListService.getTotal = function () {
        var total = 0;
        if ($rootScope.myOrders) {
            for (var i = 0; i < $rootScope.myOrders.myOrderList.length; i++) {
                total += $rootScope.myOrders.myOrderList[i].count * $rootScope.myOrders.myOrderList[i].item.price;
            }
        } else return 0;
        return total;
    };

    orderListService.removeNumberItemFromOrder = function (itemId,eventId, number) {
        $http({
            method: 'POST',
            url: '/remote_number_item_from_order',
            params: {
                event_id: eventId,
                item_id: itemId,
                number: number
            }
        }).finally(function () {
            orderListService.updateOrderList();
        });
    };

    orderListService.addNumberItemToOrder = function (itemId,eventId, number) {
            $http({
                method: 'POST',
                url: '/add_number_item_to_order',
                params: {
                    event_id: eventId,
                    item_id: itemId,
                    number: number
                }
            }).finally(function () {
                orderListService.updateOrderList();
            });
    };

    orderListService.changeItemNumber = function (newCount, oldCount, eventId, itemId) {
        if (newCount > oldCount) {
            orderListService.addNumberItemToOrder(itemId, eventId, newCount - oldCount);
        } else {
            orderListService.removeNumberItemFromOrder(itemId, eventId, oldCount - newCount)
        }
    };
    
    return orderListService;
});

services.factory("ItemService", function ($http) {
    var itemService = {};
    itemService.addNewItem = function (dataForRequest) {
        return $http.post('/new_item', dataForRequest)
    };
    return itemService;
});

services.factory("RestaurantService", function ($http) {
    var restaurantService = {};
    return restaurantService;
});

services.factory("EventService", function ($http) {
   var eventService = {};

    eventService.getEventById = function (id) {
       return $http.get("/event_" + id);
    };

    return eventService;
});