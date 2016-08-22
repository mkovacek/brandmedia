/**
 * Created by Matija on 22.8.2016..
 */

(function () {

    angular.module('mentions.controller', []).controller('MentionsCtrl', MentionsCtrl);

    MentionsCtrl.$inject = ['KeywordsServices'];

    function MentionsCtrl(KeywordsServices){
        this.activeKeywordList = {};
        this._KeywordsServices = KeywordsServices;
        this.show = false;
        this.getActiveKeywordList();
    }

    MentionsCtrl.prototype.getActiveKeywordList = function () {
        var that = this;
        this._KeywordsServices.getActiveKeywords().then(function (response) {
            that.activeKeywordList = response;
        },function(error){
            console.log("error: "+error);
        })
    }

    MentionsCtrl.prototype.getMentions = function (keywordId) {
        console.log(keywordId);
        this.show = true;
    }

})();
