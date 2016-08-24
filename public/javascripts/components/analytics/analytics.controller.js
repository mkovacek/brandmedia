/**
 * Created by Matija on 22.8.2016..
 */

(function () {

    angular.module('analytics.controller', []).controller('AnalyticsCtrl', AnalyticsCtrl);

    AnalyticsCtrl.$inject = ['KeywordsServices','AnalyticsServices'];

    function AnalyticsCtrl(KeywordsServices,AnalyticsServices){
        this.activeKeywordList = {};
        this._KeywordsServices = KeywordsServices;
        this._AnalyticsServices = AnalyticsServices;
        this.show = false;
        this.getActiveKeywordList();
        this.statistics = {
            users:"",
            countries:""
        }
        this.selectedKeyword="";
    }

    AnalyticsCtrl.prototype.getActiveKeywordList = function () {
        var that = this;
        this._KeywordsServices.getActiveKeywords().then(function (response) {
            that.activeKeywordList = response;
        })
    }

    AnalyticsCtrl.prototype.getAnalytics = function (keywordId) {
        this.selectedKeyword = keywordId;
        var that = this;
        var data = {
            keywordId: keywordId,
            size:5
        }
        this._AnalyticsServices.fetchAnalytics(data).then(function(response){
            that.statistics.users = response.users;
            that.statistics.countries = response.countries;
            that.show = true;
        })

    }

})();
