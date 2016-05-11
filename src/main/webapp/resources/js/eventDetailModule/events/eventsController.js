var eventController = angular.module('eventController', []);

eventController.controller('eventListController', function ($rootScope, Event) {
    $rootScope.events = Event.query();
});

controllers.controller("createEventController", function ($scope, $rootScope, CreateEventService, DateTimePicker) {
    $scope.createEvent = function (name, date) {
        CreateEventService.saveEvent(name, date).then(function (response) {
            $rootScope.events = response.data;
            $scope.date = '';
            $scope.name = '';
        });
    };
    $scope.dateTimePickerConfig = DateTimePicker.getConfig();
});