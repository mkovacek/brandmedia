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
        this.selectedKeyword='';
        this.columns = {
            users : [],
            countries : []
        };
        this.columnsData = {
            users : [],
            countries : []
        };
    }

    AnalyticsCtrl.prototype.getActiveKeywordList = function () {
        var that = this;
        this._KeywordsServices.getActiveKeywords().then(function (response) {
            that.activeKeywordList = response;
        })
    };

    AnalyticsCtrl.prototype.getAnalytics = function (keywordId) {
        this.columns = {
            users : [],
            countries : []
        };
        this.columnsData = {
            users : [],
            countries : []
        };
        this.selectedKeyword = keywordId;
        var that = this;
        var data = {
            keywordId: keywordId,
            size:5
        };
        this._AnalyticsServices.fetchAnalytics(data).then(function(response){
            angular.forEach(response.users,function(user){
                that.columns.users.push(user.name);
                that.columnsData.users.push(user.value);
            });

            angular.forEach(response.countries,function(countrie){
                that.columns.countries.push(countrie.name);
                that.columnsData.countries.push(countrie.value);
            });
            that.show = true;
        })
    };

})();
