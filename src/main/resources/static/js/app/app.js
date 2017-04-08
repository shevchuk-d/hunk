var app = angular.module('ngrepeatApp', ['dialogDemo1', 'progressCircularDemo1']);


app.controller('customersCtrl', function($scope, $http) {
    $scope.HOST = "localhost:8080/";


    $scope.loader = false;

    $scope.getLockers = function(){
        console.log("0. " + $scope.loader);
        $scope.loader = true;
        $http.get($scope.HOST + "/hunk/lockers/reserved")
            .then(function(response) {
                $scope.reservedLockers = response.data;
                console.log("1. " + $scope.loader);
                return response.data;
                }).then(function (data) {
                    $scope.reservedLockers = data;
                    console.log("2. " + $scope.loader);
                    $http.get($scope.HOST + "/hunk/lockers/reserved/neighbors")
                        .then(function(response) {
                            $scope.reservedLockersNeighbors = response.data;
                            console.log("3. " + $scope.loader);
                            $scope.loader = false;
                            console.log("5. " + $scope.loader);
                            return response.data;
                        });
            });
    };

    $scope.getLockers();

    $http.get($scope.HOST + "/hunk/lockers/all").then(function(response) {
        $scope.lockers = response.data;
    });

    $scope.arrayContains = function (array, obj) {
        return angular.toJson(array).indexOf(angular.toJson(obj)) >= 0;
    };

    $scope.hunk = {
        "clientId": "",
        "name": "",
        "sex": ""
    };

    $scope.getHunk = function(id) {
        $http.get($scope.HOST + "/hunk/client/" + id).then(function(response) {
            $scope.hunk = response.data;
        });
    };

    $scope.refreshLockers = function () {
        $http.get($scope.HOST + "/hunk/lockers/reserved").then(function(response) {
            $scope.reservedLockers = response.data;
        });
    };

});

