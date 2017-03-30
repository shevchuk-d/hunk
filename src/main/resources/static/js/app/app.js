var app = angular.module("ngrepeatApp", []);


app.controller('customersCtrl', function($scope, $http) {
    $http.get("http://localhost:8080/hunk/lockers/all").then(function(response) {
        $scope.lockers = response.data;
    });

    $http.get("http://localhost:8080/hunk/lockers/reserved").then(function(response) {
        $scope.reservedLockers = response.data;
    });

    $http.get("http://localhost:8080/hunk/lockers/reserved/neighbors").then(function(response) {
        $scope.reservedLockersNeighbors = response.data;
    });

    $scope.arrayContains = function (array, obj) {
        // console.log(JSON.stringify(array).indexOf(JSON.stringify(obj)));
        // console.log(JSON.stringify(array));
        // console.log(JSON.stringify(obj));
        return angular.toJson(array).indexOf(angular.toJson(obj)) >= 0;
    }
});

