angular.module('dialogDemo1', ['ngMaterial'])

    .controller('AppCtrl', function($scope, $http, $mdDialog) {
        $scope.status = '  ';
        $scope.customFullscreen = false;

        $scope.showConfirm = function(ev, locker, hunk, reserved) {
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Would you like to assign this locker for ' + hunk.name + '?')
                .textContent('_TIME_')
                .ariaLabel('Assignment')
                .targetEvent(ev)
                .ok('Assign')
                .cancel('Chose another');

            $mdDialog.show(confirm).then(function() {
                if (!reserved) $scope.addVisit(hunk, locker);
                if (reserved) $scope.finishVisit(hunk, locker);
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

            $http.post("http://localhost:8080/hunk/visit/", newVisit).
            success(function(data, status, headers, config) {
                console.log(status);
            }).
            error(function(data, status, headers, config) {
            })
        };

        $scope.finishVisit = function(hunk, locker) {
            var newVisit = JSON.stringify({
                "start": new Date(),
                "locker": locker.lockerId,
                "client": hunk.clientId
            });

            $http.post("http://localhost:8080/hunk/visit/", newVisit).
            success(function(data, status, headers, config) {
                console.log(status);
            }).
            error(function(data, status, headers, config) {
            })
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
        };

    });