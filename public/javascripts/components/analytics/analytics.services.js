/**
 * Created by Matija on 24.8.2016..
 */
angular.module('analytics.services', []).factory('AnalyticsServices', AnalyticsServices);

AnalyticsServices.$inject = ['$q', '$resource', 'SERVER_ADDRESS'];

function AnalyticsServices($q, $resource, SERVER_ADDRESS) {

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
        fetchAnalytics: fetchAnalytics
    };

    function fetchAnalytics(data) {
        var q = $q.defer();
        postAction.save({
            type : 'statistics',
            action: 'fetch',
            data: data
        }, function(response) {
            q.resolve(response);
        }, function(response) {
            q.reject(response);
        });
        return q.promise;
    };


}