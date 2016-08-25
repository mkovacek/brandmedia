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
    }

    KeywordsCtrl.prototype.getKeywordList = function () {
        var that = this;
        this._KeywordsServices.getAllKeywords().then(function (response) {
            that.keywordList = response;
        })
    };


    KeywordsCtrl.prototype.addKeyword = function () {
        if(this.keyword.trim()!==""){
            var that = this;
            this._KeywordsServices.addKeywords(this.keyword).then(function (response) {
                that.keyword = '';
                that.keywordList = response;
            })
        }
    };

    KeywordsCtrl.prototype.changeStatus = function (keyword) {
        var data = {
            keyword : keyword.keyword,
            keywordId : keyword.keywordId,
            active : keyword.active
        };
        keyword.active === 1 ? this._KeywordsServices.activateKeywordAndStartStream(data) : this._KeywordsServices.deactivateKeywordAndStopStream(data);
    };

})();
