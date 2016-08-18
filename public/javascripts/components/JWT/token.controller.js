/**
 * Created by Matija on 18.8.2016..
 */

(function () {

    angular.module('token.controller', []).controller('TokenCtrl', TokenCtrl);

    TokenCtrl.$inject = ['$window','store'];

    function TokenCtrl($window,store){
        if($window.token!=="none") store.set("jwt",$window.token);
        console.log("token: "+$window.token);
    }

})();
