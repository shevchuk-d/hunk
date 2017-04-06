var app = angular.module('ngrepeatApp', ['dialogDemo1', 'progressCircularDemo1']);


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

    $scope.isHidden = function getElementByXpath(path) {
        return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.offsetParent === null;
    };


});

