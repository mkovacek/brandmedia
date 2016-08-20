/**
 * Created by Matija on 18.8.2016..
 */

(function () {

    angular.module('settings.controller', []).controller('SettingsCtrl', SettingsCtrl);

    SettingsCtrl.$inject = ['SettingsServices', 'store', '$state'];

    function SettingsCtrl(SettingsServices,store,$state){
        this._SettingsServices = SettingsServices;
        this._store = store;
        this._$state = $state;
/*        this.user = {
            uuid : null,
            firstname : null,
            lastname : null,
            email : null,
            oldPassword : null,
            newPassword : null
        }*/
    }

    SettingsCtrl.prototype.logout = function () {
        var that = this;
        this._SettingsServices.logout().then(function (response) {
            that._store.remove('jwt');
            that._$state.go('index');
        },function(error){
            that._store.remove('jwt');
            that._$state.go('index');
        })
    }

/*    SettingsCtrl.prototype.settings = function(valid) {
        if(valid){
            console.log("dsadas");
            var that=this;
            console.log(that.user)
        }
    }*/

})();
