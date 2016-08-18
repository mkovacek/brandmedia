'use strict';

angular.module('brandmedia', [
  'ngResource',
  'ui.router',
  'angularUtils.directives.dirPagination',
  'angular-storage',
  'angular-jwt',
  'gridshore.c3js.chart',
  'token.controller',
  'jwtInterceptor.services',
  'interceptor.services',
  'home.controller'
])

.constant('SERVER_ADDRESS', 'localhost:9000')

.config(['$urlRouterProvider','$stateProvider','$locationProvider',function($urlRouterProvider, $stateProvider,$locationProvider) {
  $urlRouterProvider.otherwise('/home');

  $stateProvider
      .state('index', {
          url: '/',
          templateUrl: "/",
          module:'public'
      })

      .state('signin', {
          url: '/signin',
          templateUrl: "/signin",
          module:'public'
      })

      .state('signup', {
          url: '/signup',
          templateUrl: "/signup",
          module:'public'
      })

      .state('home', {
        url: '/home',
        views: {
            "":{
                templateUrl: "/home/content",
                controller: "HomeCtrl",
                controllerAs:"hc"
            },
              "content@home": {
                  templateUrl: "/home/keywords"

              }
        },
        module:'private'
      })

      .state('home.keywords', {
          url: '/keywords/',
          views: {
              "content": {
                  templateUrl: "/home/keywords"
              }
          },
          module:'private'
      })

      .state('home.mentions', {
          url: '/mentions/',
          module:'private',
          views: {
              "content": {
                  templateUrl: "/home/mentions"
              }
          }
      })

      .state('home.analytics', {
          url: '/analytics/',
          views: {
              "content@home": {
                  templateUrl: "/home/analytics"
              }
          },
          module:'private'
      })

      .state('home.settings', {
          url: '/settings/',
          views: {
              "content@home": {
                  templateUrl: "/home/settings"
              }
          },
          module:'private'
      })


      $locationProvider.html5Mode({
          enabled: true,
          requireBase: false
      });
}])

.run(function($rootScope, $location, $timeout) {
    $rootScope.$on('$viewContentLoaded', function() {
        $timeout(function() {
            componentHandler.upgradeAllRegistered();
        });
    });
})

.run(['$rootScope','jwtHelper','store','$state',function($rootScope,jwtHelper,store,$state){
    $rootScope.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams) {
       /* var token = store.get('jwt');
        if (token) {
            if (jwtHelper.isTokenExpired(token)){
                store.remove('jwt');
                e.preventDefault();
                $state.go('index');
            }
        }else{
            if(toState.module === 'private'){
                e.preventDefault();
                $state.go('index');
            }
        }*/
    })
}]);
