var app = angular.module('dialogDemo1', ['ngMaterial']);

app.controller('AppCtrl', function($scope, $http, $mdDialog) {
    $scope.status = '  ';
    $scope.customFullscreen = false;

    $scope.showConfirm = function(ev, locker, reserved) {
        $scope.ev = ev;
        $scope.reserved = reserved;
        $scope.lockerForPopup = locker;
        if ($scope.reserved){
            $http.get($scope.HOST + "/hunk/" + locker.lockerId + "/visit/active")
                .then(function (response) {
                    $scope.visitForCurrentLocker = response.data;
                    console.log('1. ' + JSON.stringify($scope.visitForCurrentLocker));
                    return response.data;
                }).then(function(visit){
                $http.get($scope.HOST + "/hunk/client/" + visit.client)
                    .then(function(response) {
                        var hunkForCurrentLocker = response.data;
                        $scope.buildPopup(hunkForCurrentLocker, locker);
                    })
            })
        } else {
            $scope.buildPopup($scope.hunk, locker);
        }
    };


    $scope.buildPopup = function (hunkForCurrentLocker, locker) {
        var assigneeExists = hunkForCurrentLocker.clientId != 0;
        $scope.alert = $mdDialog.alert()
            .clickOutsideToClose(true)
            .title("Oops!")
            .textContent("Please search the client you want to assign this locker for!")
            .ariaLabel()
            .targetEvent($scope.ev)
            .ok('Chose another');

        $scope.confirm = $mdDialog.confirm()
            .title($scope.reserved ? 'Finish workout' : 'Start workout')
            .textContent($scope.reserved
                ? "Do you want to finish workout for " + hunkForCurrentLocker.name + "?"
                : "Do you want to assign " + $scope.lockerForPopup.number
                                                + " for " + hunkForCurrentLocker.name + "?"
            )
            .ariaLabel('Assignment')
            .targetEvent($scope.ev)
            .ok($scope.reserved ? 'Finish' : 'Assign')
            .cancel('Chose another');

        if (assigneeExists){
            $mdDialog.show($scope.confirm).then(function() {
                if (!$scope.reserved) $scope.addVisit(hunkForCurrentLocker, locker);
                if ($scope.reserved) $scope.finishVisit(locker);
            })
        }else {
            $mdDialog.show($scope.alert)
        }

    };

    $scope.addVisit = function(hunk, locker) {
        var newVisit = JSON.stringify({
            "start": new Date(),
            "locker": locker.lockerId,
            "client": hunk.clientId
        });
        $http.post($scope.HOST + "/hunk/visit/", newVisit).
        success(function(data, status) {
            $scope.getLockers()
        });
    };

    $scope.finishVisit = function(locker) {
        var visit = {};
        $http.get($scope.HOST + "/hunk/" + locker.lockerId + "/visit/active").then(
            function (response) {
                visit = response.data;
                visit.finish = new Date();
                visit.locker = null;
                visit.client = null;
                return visit;
        }).then(function(visit){
            $http.put($scope.HOST + "/hunk/visit/" + visit.visitId, visit).
            success(function(data, status) {
                $scope.getLockers()
            });
        });
    };

    function DialogController($scope, $mdDialog) {
        $scope.hide = function() {
            $mdDialog.hide();
        };

        $scope.cancel = function() {
            $mdDialog.cancel();
        };

        $scope.answer = function(answer) {
            $mdDialog.hide(answer);
        };
    }

});