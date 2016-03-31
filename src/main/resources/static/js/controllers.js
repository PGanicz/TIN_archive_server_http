/**
 * Created by piotr on 31.03.16.
 */
angular.module('springGame.controllers', [])
    .controller('GameController', ['$scope','GameSocket', function($scope,gameSocket) {

        $scope.sendMessage = function(){
            gameSocket.send('hello world');
        }
    }]);
		  