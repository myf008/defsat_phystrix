/**
 * create by feiyongjun 
 */
'use strict';

var module = angular.module('portal', ['ngRoute',
				'ngResource',
				'phySwitchService',
				'phyConfigService',
				'baseTableService',
				'ui.bootstrap']);
				
	module.config(['$routeProvider',
    function ($routeProvider) {

        $routeProvider
        	.when("/app",{
            	templateUrl: '/phystrix-admin/partials/phystrix/switch/phySwitch.html',
            	reloadOnSearch: false	
            }).when("/app/:appId",{
            	templateUrl: '/phystrix-admin/partials/phystrix/switch/showPhySwitch.html',
            	reloadOnSearch: false	
            }).when("/para",{
            	templateUrl: '/phystrix-admin/partials/phystrix/config/phyConfig.html',
            	reloadOnSearch: false	
            }).when("/para/:appId/:commandKey",{
            	templateUrl: '/phystrix-admin/partials/phystrix/config/showPhyConfig.html',
            	reloadOnSearch: false	
            }).otherwise({
                templateUrl: '/phystrix-admin/partials/phystrix/switch/phySwitch.html',
                reloadOnSearch: false
            });
    }]);
	 