/**
 * Created by Matija on 22.8.2016..
 */

(function () {

    angular.module('keywords.controller', []).controller('KeywordsCtrl', KeywordsCtrl);

    KeywordsCtrl.$inject = ['KeywordsServices'];

    function KeywordsCtrl(KeywordsServices){
        this.keyword = '';
        this.keywordList = {};
        this._KeywordsServices = KeywordsServices;
        this.getKeywordList();
        /*var upgrade = window.localStorage.getItem('upgrade-keyword');
        console.log(upgrade);
        componentHandler.upgradeAllRegistered();
        if(!upgrade){
            console.log("jej");
            window.localStorage.setItem('upgrade-keyword',true);

        }*/
    }

    KeywordsCtrl.prototype.getKeywordList = function () {
        var that = this;
        this._KeywordsServices.getAllKeywords().then(function (response) {
            that.keywordList = response;
        },function(error){
            console.log("error: "+error);
        })
    }


    KeywordsCtrl.prototype.addKeyword = function () {
        if(this.keyword.trim()!==""){
            var that = this;
            this._KeywordsServices.addKeywords(this.keyword).then(function (response) {
                that.keyword = '';
                that.keywordList = response;
            },function(error){
                console.log("error: "+error);
            })
        }
    }

})();
