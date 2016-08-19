/**
 * Created by Matija on 18.8.2016..
 */

(function () {

    angular.module('home.controller', []).controller('HomeCtrl', HomeCtrl);

    HomeCtrl.$inject = ['$window','store'];

    function HomeCtrl($window,store){
        if($window.token!=="none"){
            store.set("jwt",$window.token);
            this.token = $window.token
        }
    }

})();
