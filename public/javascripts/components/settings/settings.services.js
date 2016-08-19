/**
 * Created by Matija on 18.8.2016..
 */

angular.module('settings.services', []).factory('SettingsServices', SettingsServices);

SettingsServices.$inject = ['$q', '$resource', 'SERVER_ADDRESS'];

function SettingsServices($q, $resource, SERVER_ADDRESS) {

    var postAction = $resource('/:url/:type/:action', {
        url: SERVER_ADDRESS,
        type: '@type',
        action: '@action'
    }, {
        save: {
            method: 'POST'
        }
    });

    return {
        logout: logout
    };


    function logout() {
        var q = $q.defer();
        postAction.save({
            type : 'authenticate',
            action: 'logout'
        }, function(response) {
            q.resolve(response);
        }, function(response) {
            q.reject(response);
        });
        return q.promise;
    };
}
