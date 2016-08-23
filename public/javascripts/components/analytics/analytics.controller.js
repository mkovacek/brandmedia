/**
 * Created by Matija on 22.8.2016..
 */

(function () {

    angular.module('analytics.controller', []).controller('AnalyticsCtrl', AnalyticsCtrl);

    AnalyticsCtrl.$inject = ['KeywordsServices'];

    function AnalyticsCtrl(KeywordsServices){
        this.activeKeywordList = {};
        this._KeywordsServices = KeywordsServices;
        this.show = false;
        this.getActiveKeywordList();
    }

    AnalyticsCtrl.prototype.getActiveKeywordList = function () {
        var that = this;
        this._KeywordsServices.getActiveKeywords().then(function (response) {
            that.activeKeywordList = response;
        })
    }

    AnalyticsCtrl.prototype.getAnalytics = function (keywordId) {
        this.show = true;
    }

})();
