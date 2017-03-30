var app = angular.module("ngrepeatApp", []);


app.controller('customersCtrl', function($scope, $http) {
    $http.get("http://localhost:8080/hunk/lockers/all").then(function(response) {
        $scope.lockers = response.data;
    });

    $http.get("http://localhost:8080/hunk/lockers/reserved").then(function(response) {
        $scope.reservedLockers = response.data;
    });

});

