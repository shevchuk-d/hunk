var app = angular.module('dialogDemo1', ['ngMaterial']);

app.controller('AppCtrl', function($scope, $http, $mdDialog) {
        $scope.status = '  ';
        $scope.customFullscreen = false;

        $scope.showConfirm = function(ev, locker, hunk, reserved) {
            var title = getTitle(locker, reserved);
            var msg = getPopupContent(locker);
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title(title)
                .textContent(msg)
                .ariaLabel('Assignment')
                .targetEvent(ev)
                .ok(reserved ? 'Finish' : 'Assign')
                .cancel('Chose another');

            $mdDialog.show(confirm).then(function() {
                if (!reserved) $scope.addVisit(hunk, locker);
                if (reserved) $scope.finishVisit(locker);
            }, function() {
                $scope.status = 'Chose another';
            });
        };

        $scope.addVisit = function(hunk, locker) {
            var newVisit = JSON.stringify({
                "start": new Date(),
                "locker": locker.lockerId,
                "client": hunk.clientId
            });
            $http.post("http://localhost:8080/hunk/visit/", newVisit);
        };
        
        function getTitle(locker, reserved) {
            var visitZ = {};
            var hunk = {};
            var msg = "Oops";
            $http.get("http://localhost:8080/hunk/" + locker.lockerId + "/visit/active").then(
                function (response) {
                    visitZ = response.data;
                    return visitZ;
                }).then(function(visitZ){
                    $http.get("http://localhost:8080/hunk/client/" + visitZ.client).then(function(response) {
                        console.log(JSON.stringify(hunk));
                        msg = reserved
                            ? 'Would you like to finish visit for ' + hunk.name + '?'
                            : 'Would you like to assign this locker for ' + hunk.name + '?';
                    });
            });
            return msg
        }

        function getPopupContent(locker) {
            var visitZ = {};
            var hunk = {};
            $http.get("http://localhost:8080/hunk/" + locker.lockerId + "/visit/active").then(
                function (response) {
                    visitZ = response.data;
                    return visitZ;
                }).then(function(visit){
                    visitZ = visit;
                    $http.get("http://localhost:8080/hunk/client/" + visitZ.client).then(function(response) {
                        hunk = response.data;
                    });
                });
            return hunk.name == undefined
                ? 'Please, search person you want to assign this locker?'
                : 'The locker ' + locker.lockerId + ' is assigned to' +
                    '\nName: ' + hunk.name +
                    '\nStarted: ' + visitZ.start;
        }

        $scope.finishVisit = function(locker) {
            var visit = {};
            $http.get("http://localhost:8080/hunk/" + locker.lockerId + "/visit/active").then(
                function (response) {
                    visit = response.data;
                    visit.finish = new Date();
                    visit.locker = null;
                    visit.client = null;
                    return visit;
            }).then(function(visit){
                $http.put("http://localhost:8080/hunk/visit/" + visit.visitId, visit).
                success(function(data, status) {
                    console.log(":)");
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