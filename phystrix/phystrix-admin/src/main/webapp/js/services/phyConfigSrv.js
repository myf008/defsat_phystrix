angular.module("phyConfigService", ['ngResource'])
    .factory("PhyConfigService", ['$resource', '$routeParams',
        function ($resource, $routeParams) {
            return $resource("/phystrix-admin/phystrix/config/:action",
                {},
                {
                	getAllPhyConfigs: {
                		method: 'GET',
                		params: {
                            action: 'phyConfigList'
                		}
                	},    
                	getByAppId: {
                		method: 'GET',
                		params: {
                			action: '@appId'
                		}
                	},    
                    getPhyConfigByID: {
                        method: 'POST',
                        params: {
                            action: 'getByCommandKey',
                            appId: '@appId',
                            commandKey:'@commandKey'
                        }
                    }, 
                    delPhyConfigByID: {
                        method: 'POST',
                        params: {
                            action: 'delOne',
                            appId: '@appId',
                            commandKey:'@commandKey'
                        }
                    }, 
                    updatePhyConfig:{
                    	method:'POST',
                        params: {
                            action: 'update',
                            phyConfig: '@phyConfig'
                        }
                       
                    },
                    addPhyConfig:{
                    	method:'POST',
                        params: {
                            action: 'add',
                            phyConfig: '@phyConfig'
                        }
                    }
                }
            );
        }
]);