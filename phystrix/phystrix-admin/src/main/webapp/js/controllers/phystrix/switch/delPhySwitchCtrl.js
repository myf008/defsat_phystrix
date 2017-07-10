
function DelPhySwitchCtrl($scope, $modalInstance, phySwitch, PhySwitchService){
	
    $scope.name = '业务模块 : '+phySwitch.appId;
    $scope.phySwitch = phySwitch;

    $scope.alert = {
        type: '',
        msg: '',
        isShow: false
    };
    
    $scope.del = function(){
    	var delResult = PhySwitchService.delPhySwitchByID({'appId':phySwitch.appId});
    	
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