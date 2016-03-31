/**
 * Created by piotr on 31.03.16.
 */


angular.module('springGame.services', [])
    .factory('GameSocket', ['$rootScope', function($rootScope) {

            var socket = SockJS("/game");
                console.log("Clicked");
                socket.onopen = function(){console.log("onopen");}
                socket.onclose = function(){console.log("onclose");}
                socket.onmessage = function(){console.log("onmessage");}

            return socket;
        }
    ]);