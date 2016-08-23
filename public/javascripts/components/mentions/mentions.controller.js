/**
 * Created by Matija on 22.8.2016..
 */

(function () {

    angular.module('mentions.controller', []).controller('MentionsCtrl', MentionsCtrl);

    MentionsCtrl.$inject = ['KeywordsServices','MentionsServices','$sce'];

    function MentionsCtrl(KeywordsServices,MentionsServices,$sce){
        this.activeKeywordList = {};
        this.mentionList = {};
        this._KeywordsServices = KeywordsServices;
        this._MentionsServices = MentionsServices;
        this._$sce = $sce;
        this.show = false;
        this.getActiveKeywordList();
        this.highlightKeyword='';
        //pagination
        this.currentPage = 1;
        this.pageSize = 5;
        this.data = {
            keywordId: '',
            offset : 0 ,
            size : 50
        };
    }

    MentionsCtrl.prototype.getActiveKeywordList = function () {
        var that = this;
        this._KeywordsServices.getActiveKeywords().then(function (response) {
            that.activeKeywordList = response;
        })
    };

    MentionsCtrl.prototype.getMentions = function (keyword) {
        var that = this;
        this.show = true;
        this.highlightKeyword = keyword.keyword;
        this.data.keywordId = keyword.id;
        this._MentionsServices.fetchMentions(this.data).then(function(response){
            that.mentionList = response.mentions;
            that.data.offset = that.data.offset + response.meta.offset;
        })
    };

    MentionsCtrl.prototype.highlight = function(status) {
        if(!this.highlightKeyword) {
            return this._$sce.trustAsHtml(status);
        }
        return this._$sce.trustAsHtml(status.replace(new RegExp(this.highlightKeyword, "gi"), function(match) {
            return '<b class="bg-highlight">' + match + '</b>';
        }));
    };

    MentionsCtrl.prototype.pageChange = function(newPageNumber) {
        var that=this;
        var lastPageNumber=Math.ceil(this.mentionList.length/this.pageSize);
        if(lastPageNumber-newPageNumber === 1 || lastPageNumber === newPageNumber){
            console.log("novi fetch");
            this._MentionsServices.fetchMentions(this.data).then(function(response){
                that.mentionList.push.apply(that.mentionList,response.mentions);
                that.data.offset = that.data.offset + response.meta.offset;
            });
        }
    };

})();
