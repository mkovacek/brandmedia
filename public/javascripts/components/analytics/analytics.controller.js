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
        this.userChartObj='';
        this.countrieChartObj='';
    }

    AnalyticsCtrl.prototype.getActiveKeywordList = function () {
        var that = this;
        this._KeywordsServices.getActiveKeywords().then(function (response) {
            that.activeKeywordList = response;
        })
    }

    AnalyticsCtrl.prototype.getAnalytics = function (keywordId) {
        if( this.userChartObj !== '') this.userChartObj.flush();
        if( this.countrieChartObj !== '') this.countrieChartObj.flush();
        this.columns = {
            users : [],
            countries : []
        }
        this.columnsData = {
            users : [],
            countries : []
        }
        this.selectedKeyword = keywordId;
        var that = this;
        var data = {
            keywordId: keywordId,
            size:5
        }
        this._AnalyticsServices.fetchAnalytics(data).then(function(response){
            angular.forEach(response.users,function(user){
                var column = {
                    id : user.name,
                    type: 'pie'
                }
                var columnData = {};
                columnData[user.name] = user.value;
                that.columns.users.push(column)
                that.columnsData.users.push(columnData)
            });

            angular.forEach(response.countries,function(countrie){
                var column = {
                    id : countrie.name,
                    type: 'pie'
                }
                var columnData = {};
                columnData[countrie.name] = countrie.value;
                that.columns.countries.push(column)
                that.columnsData.countries.push(columnData)
            });
            that.show = true;
        })
    }

    AnalyticsCtrl.prototype.userChart = function (chartObj) {
        this.userChartObj = chartObj
    }

    AnalyticsCtrl.prototype.countrieChart = function (chartObj) {
        this.countrieChartObj = chartObj
    }

})();
