'use strict';

angular.module('brandmedia', [
  'ngResource',
  'ui.router',
  'angularUtils.directives.dirPagination',
  'angular-storage',
  'angular-jwt'
])

.config(['$urlRouterProvider','$stateProvider','$locationProvider',function($urlRouterProvider, $stateProvider,$locationProvider) {
  $urlRouterProvider.otherwise('/home');

  $stateProvider
      .state('home', {
        url: '/home',
        templateUrl: '/home/keywords',
        module:'private'
      })

      .state('keywords', {
          url: '/keywords',
          templateUrl: '/home/keywords',
          module:'private'
      })

      .state('mentions', {
          url: '/mentions',
          templateUrl: '/home/mentions',
          module:'private'
      })

      .state('analytics', {
          url: '/analytics',
          templateUrl: '/home/analytics',
          module:'private'
      })

      .state('settings', {
          url: '/settings',
          templateUrl: '/home/settings',
          module:'private'
      })


      $locationProvider.html5Mode({
          enabled: true,
          requireBase: false
      });
}]);

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
