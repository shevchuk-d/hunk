var app = angular.module('dialogDemo1', ['ngMaterial']);

app.controller('AppCtrl', function($scope, $http, $mdDialog) {
        $scope.status = '  ';
        $scope.customFullscreen = false;

        $scope.showConfirm = function(ev, locker, reserved) {
            $scope.confirm = getPopupContent(locker, reserved, ev);

            console.log('6. ' + JSON.stringify($scope.hunkForCurrentLocker));

            // $scope.confirm = $mdDialog.confirm()
            //     .title(getTitle(locker, reserved) )
            //     .textContent($scope.hunkForCurrentLocker)
            //     .ariaLabel('Assignment')
            //     .targetEvent(ev)
            //     .ok(reserved ? 'Finish' : 'Assign')
            //     .cancel('Chose another');

            $mdDialog.show($scope.confirm).then(function() {
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
            $http.get("http://localhost:8080/hunk/" + locker.lockerId + "/visit/active").then(
                function (response) {
                    $scope.visitZ = response.data;
                    return response.data;
                }).then(function(visitZ){
                    $http.get("http://localhost:8080/hunk/client/" + visitZ.client).then(function(response) {

                    });
            });
        }
        function getPopupContent(locker, reserved, ev) {
                $http.get("http://localhost:8080/hunk/" + locker.lockerId + "/visit/active")
                    .then(function (response) {
                        $scope.visitForCurrentLocker = response.data;
                        console.log('1. ' + JSON.stringify($scope.visitForCurrentLocker));
                        return response.data;
                    }).then(function(visit){
                        $http.get("http://localhost:8080/hunk/client/" + visit.client)
                            .then(function(response) {
                                $scope.hunkForCurrentLocker = response.data;
                                console.log('2. ' + JSON.stringify($scope.hunkForCurrentLocker));
                                return response.data;
                            })
                            .then(function (data) {
                                var confirm = $mdDialog.confirm()
                                    .title(getTitle(locker, reserved) )
                                    .textContent($scope.hunkForCurrentLocker)
                                    .ariaLabel('Assignment')
                                    .targetEvent(ev)
                                    .ok(reserved ? 'Finish' : 'Assign')
                                    .cancel('Chose another');
                                return confirm;
                        });
                    });
                //     .then(function (data) {
                //         $scope.hunkForCurrentLocker = data;
                //         console.log('4. ' + JSON.stringify(data));
                // })
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