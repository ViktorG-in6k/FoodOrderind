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
            url: "/remote_from_order"+itemId+"_"+eventId
        }).then(function () {
            orderListService.updateOrderList();
        });
    };
    
    orderListService.removeOneItemFromOrder = function (eventId, itemId) {
        $http({
            method: 'GET',
            url: '/remote_one_item_from_order'+itemId+"_"+eventId
        }).then(function () {
            orderListService.updateOrderList();
        });
    };

    
    orderListService.updateOrderList = function () {
        $http.get("/MyOrderJson_" + $rootScope.eventId).success(function (data) {
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

    return orderListService;
});

services.factory("RestaurantService", function ($http) {
    var restaurantService = {};
    return restaurantService;
});