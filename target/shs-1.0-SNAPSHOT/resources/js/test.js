var app = angular.module('pathApp', ["ngRoute"], function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/Book/Edit', {
            templateUrl: 'partials/Edit',
            controller: function ($scope) {
                $scope.classname = "edit"
            }
        })
        .when('/Book/Delete', {
            template: '<div class="box" ng-class="classname">Delete</div>',
            controller: function ($scope) {
                $scope.classname = "delete"
            }
        })
        .when('/Book/Show', {
            template: '<div class="box" ng-class="classname">Show</div>',
            controller: function ($scope) {
                $scope.classname = "show"
            }
        })
        .when('/Book/Add', {
            template: '<div class="box" ng-class="classname">Add</div>',
            controller: function ($scope) {
                $scope.classname = "add"
            }
        })
        .when('/Book/Error', {
            template: '<div class="box" ng-class="classname">Error Path</div>',
            controller: function ($scope) {
                $scope.classname = "error"
            }
        })
        .otherwise({redirectTo: '/Book/Error'});

});

app.controller('MainCtrl', function ($scope) {
    $scope.test = '123';
});

app.controller('MainCtrl', function ($scope){
    $scope.test = "123";
});