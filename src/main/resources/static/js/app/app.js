var app = angular.module('ngrepeatApp', ['dialogDemo1', 'progressCircularDemo1']);


app.controller('customersCtrl', function($scope, $http) {

    $scope.loader = false;

    $scope.getLockers = function(){
        console.log($scope.loader);
        $scope.loader = true;
        $http.get("http://localhost:8080/hunk/lockers/reserved")
            .then(function(response) {
                $scope.reservedLockers = response.data;
                console.log($scope.loader);
                return response.data;
            }).then(function (data) {
            $scope.reservedLockers = data;
            $http.get("http://localhost:8080/hunk/lockers/reserved/neighbors").then(function(response) {
                $scope.reservedLockersNeighbors = response.data;
                console.log($scope.loader);
                return response.data;
            });
            }).then(function (data) {
            $scope.loader = false;
            console.log($scope.loader);
        });
    };

    $scope.getLockers();

    $http.get("http://localhost:8080/hunk/lockers/all").then(function(response) {
        $scope.lockers = response.data;
    });

    // $http.get("http://localhost:8080/hunk/lockers/reserved").then(function(response) {
    //     $scope.reservedLockers = response.data;
    // });



    $scope.arrayContains = function (array, obj) {
        return angular.toJson(array).indexOf(angular.toJson(obj)) >= 0;
    };

    $scope.hunk = {
        "clientId": "",
        "name": "",
        "sex": ""
    };



    $scope.getHunk = function(id) {
        $http.get("http://localhost:8080/hunk/client/" + id).then(function(response) {
            $scope.hunk = response.data;
        });
    };

    $scope.refreshLockers = function () {
        $http.get("http://localhost:8080/hunk/lockers/reserved").then(function(response) {
            $scope.reservedLockers = response.data;
        });
    };



});

