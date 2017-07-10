angular.module("phySwitchService", ['ngResource'])
    .factory("PhySwitchService", ['$resource', '$routeParams',
        function ($resource, $routeParams) {
            return $resource("/phystrix-admin/phystrix/switch/:action",
                {},
                {
                	getAllPhySwitchs: {
                		method: 'GET',
                		params: {
                            action: 'phySwitchList'
                		}
                	},    
                    getPhySwitchByID: {
                        method: 'GET',
                        params: {
                            action: '@appId'
                        }
                    }, 
                    delPhySwitchByID: {
                        method: 'POST',
                        params: {
                            action: 'delete',
                            appId: '@appId'
                        }
                    }, 
                    updatePhySwitch:{
                    	method:'POST',
                        params: {
                            action: 'update',
                            phySwitch: '@phySwitch'
                        }
                       
                    },
                    addPhySwitch:{
                    	method:'POST',
                        params: {
                            action: 'add',
                            phySwitch: '@phySwitch'
                        }
                    }
                }
            );
        }
]);