/**
 * Created by Matija on 18.8.2016..
 */
(function () {
    angular.module('jwtInterceptor.services',[])
        .config(function ($httpProvider,jwtInterceptorProvider) {
            jwtInterceptorProvider.tokenGetter =['store', function(store) {
                return store.get('jwt');
            }];
            $httpProvider.interceptors.push('jwtInterceptor');
        })
})();

