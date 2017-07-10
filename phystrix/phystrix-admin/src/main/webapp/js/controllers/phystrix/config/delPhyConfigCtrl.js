
function DelPhyConfigCtrl($scope, $modalInstance, phyConfig, PhyConfigService){
	
    $scope.name = '业务模块 : '+phyConfig.appId + ', commandKey: '+phyConfig.commandKey +'的Hystrix配置';
    $scope.phyConfig = phyConfig;

    $scope.alert = {
        type: '',
        msg: '',
        isShow: false
    };
    
    $scope.del = function(){
    	var delResult = PhyConfigService.delPhyConfigByID({'appId':phyConfig.appId,'commandKey':phyConfig.commandKey});
    	
        delResult.$promise.then(function (data) {
        	$scope.isLoading = false;
        	if (data.success) {           		
        		$modalInstance.close('ok');
        	}else{
        		$scope.alert = {
					type: 'alert-danger',
					msg: data.messages,
					isShow: true
    			};  
        	}
        });
    };
    
    $scope.cancel = function(){
        $modalInstance.close();
    };
    
}