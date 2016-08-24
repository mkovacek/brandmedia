'use strict';

angular.module('brandmedia', [
  'ngResource',
  'ui.router',
  'angularUtils.directives.dirPagination',
  'angular-storage',
  'gridshore.c3js.chart',
  'angularMoment',
  'interceptor.services',
  'home.controller',
  'settings.services',
  'settings.controller',
  'keywords.services',
  'keywords.controller',
  'mentions.services',
  'mentions.controller',
  'analytics.services',
  'analytics.controller'
])

.constant('SERVER_ADDRESS', 'api') //change this

.config(['$urlRouterProvider','$stateProvider','$locationProvider',function($urlRouterProvider, $stateProvider,$locationProvider) {
  $urlRouterProvider.otherwise('/home');

  $stateProvider
      .state('index', {
          url: '/',
          templateUrl: "/",
          module:'public'
      })

      .state('indexLogIn', {
          url: '/index',
          templateUrl: "/index",
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
                templateUrl: "/home/content"
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
                  templateUrl: "/home/settings",
                  controller: "SettingsCtrl",
                  controllerAs: "sc"
              }
          },
          module:'private'
      })

      $locationProvider.html5Mode({
          enabled: true,
          requireBase: false
      });
}])

.run(function($rootScope, $location, $timeout, store,$state) {
    var mdlUpgradeDom = false;
    setInterval(function() {
        if (mdlUpgradeDom) {
            componentHandler.upgradeDom();
            mdlUpgradeDom = false;
        }
    }, 200);

    var observer = new MutationObserver(function () {
        mdlUpgradeDom = true;
    });
    observer.observe(document.body, {
        childList: true,
        subtree: true
    });

    $rootScope.$on('$locationChangeStart', function (event, newUrl, oldUrl) {
        var token = store.get('jwt');
        var newpath = newUrl.split("/")[3];
        if(token === null && (newpath !== "signin" && newpath !== "signup" && newpath !== "")){
            event.preventDefault();
            $state.go('index');
        }

    });

});


