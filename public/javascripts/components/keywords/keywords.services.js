/**
 * Created by Matija on 18.8.2016..
 */

angular.module('keywords.services', []).factory('KeywordsServices', KeywordsServices);

KeywordsServices.$inject = ['$q', '$resource', 'SERVER_ADDRESS'];

function KeywordsServices($q, $resource, SERVER_ADDRESS) {

    var postAction = $resource('/:url/:type/:action', {
        url: SERVER_ADDRESS,
        type: '@type',
        action: '@action'
    }, {
        save: {
            method: 'POST',
            isArray: true
        }
    });

    var getAction = $resource('/:url/:type/:action', {
        url: SERVER_ADDRESS,
        type: '@type',
        action: '@action'
    }, {
        get: {
            method: 'GET',
            isArray: true
        }
    });

    return {
        addKeywords: addKeywords,
        getAllKeywords : getAllKeywords,
        getActiveKeywords : getActiveKeywords
    };


    function addKeywords(keyword) {
        var q = $q.defer();
        postAction.save({
            type : 'keywords',
            action: 'add',
            keyword : keyword
        }, function(response) {
            q.resolve(response);
        }, function(response) {
            q.reject(response);
        });
        return q.promise;
    };

    function getAllKeywords() {
        var q = $q.defer();
        getAction.get({
            type : 'keywords',
            action: 'all'
        }, function(response) {
            q.resolve(response);
        }, function(response) {
            q.reject(response);
        });
        return q.promise;
    };

    function getActiveKeywords() {
        var q = $q.defer();
        getAction.get({
            type : 'keywords',
            action: 'active'
        }, function(response) {
            q.resolve(response);
        }, function(response) {
            q.reject(response);
        });
        return q.promise;
    };
}
