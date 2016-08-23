/**
 * Created by Matija on 23.8.2016..
 */
angular.module('mentions.services', []).factory('MentionsServices', MentionsServices);

MentionsServices.$inject = ['$q', '$resource', 'SERVER_ADDRESS'];

function MentionsServices($q, $resource, SERVER_ADDRESS) {

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
        fetchMentions: fetchMentions
    };
    
    function fetchMentions(data) {
        var q = $q.defer();
        postAction.save({
            type : 'mentions',
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