/**
 * Created by Matija on 18.8.2016..
 */
(function () {
    angular.module('interceptor.services', [])
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('unauthenticatedInterceptor');
        })
        .factory('unauthenticatedInterceptor', function ($q, $injector,store) {
            return {
                'request': function (config) {
                    var token = store.get('jwt');
                    if (token) {
                        config.headers['Authorization'] = token;
                    }
                    return config;
                },
                'response': function (response) {
                    var token = response.headers("Authorization");
                    if(token!==null){
                        store.set('jwt',token);
                    }
                    return response;
                },
                'responseError': function (rejection) {
                    if(rejection.status == 401) {
                        store.remove('jwt');
                        $injector.get('$state').transitionTo('index');
                    }
                    return $q.reject(rejection);
                }
            };
        });
})();