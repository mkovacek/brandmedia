'use strict';

angular.module('brandmedia', [
  'ngResource',
  'ui.router',
  'angularUtils.directives.dirPagination',
  'angular-storage',
  'angular-jwt',
  'gridshore.c3js.chart'
/*  'mdl'*/
])

.config(['$urlRouterProvider','$stateProvider','$locationProvider',function($urlRouterProvider, $stateProvider,$locationProvider) {
  $urlRouterProvider.otherwise('/home');

  $stateProvider
      .state('home', {
        url: '/home',
        views: {
              "content@": {
                  templateUrl: "/home/keywords"
              }
        },
        module:'private'
      })

      .state('keywords', {
          url: '/home/keywords/',
          views: {
              "content@": {
                  templateUrl: "/home/keywords"
              }
          },
          module:'private'
      })

      .state('mentions', {
          url: '/home/mentions/',
          module:'private',
          views: {
              "content@": {
                  templateUrl: "/home/mentions"
              }
          }
      })

      .state('analytics', {
          url: '/home/analytics/',
          views: {
              "content@": {
                  templateUrl: "/home/analytics"
              }
          },
          module:'private'
      })

      .state('settings', {
          url: '/home/settings/',
          views: {
              "content@": {
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
});

/*
.run(['$rootScope','jwtHelper','store','$state',function($rootScope,jwtHelper,store,$state){
    $rootScope.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams) {
        var token = store.get('jwt');
        if (token) {
            if (jwtHelper.isTokenExpired(token)){
                store.remove('jwt');
                e.preventDefault();
                $state.go('landing');
            }
        }else{
            if(toState.module ==='private'){
                e.preventDefault();
                $state.go('landing');
            }
        }
    })
}]);*/
