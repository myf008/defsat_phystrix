

function ShowPhySwitchCtrl($scope, $http, $modal, $routeParams, PhySwitchService) {
    
	initScope();

    /***********************functions***********************/
    function initScope() {
        initParameters();
        initFunctions();
    }

    function initParameters() {
        //提示信息
        $scope.data = {
            type: '',
            msg: '',
            isShow: false
        };
        
        
        var getResult = PhySwitchService.getPhySwitchByID({'action':$routeParams.appId});
        
        getResult.$promise.then(function (data) {
        	$scope.isLoading = false;
        	if (data.success) {           		
        		$scope.phySwitch = data.result;
        	}else{
        		showAlert('warning', data.messages);
        	}
        });
    
    }
    
    function initFunctions() {
    	
    	$scope.closePhySwitch = function(){
    		 window.location = "/phystrix-admin/#/app";
    	};
        
        $scope.closeAlert = function () {
        	$scope.data.isShow = false;
        };
        
        $scope.updatePhySwitch = function (phySwitch) {
        	phySwitch.switchStatus = $scope.phySwitch.switchStatus;
            var updateResult = PhySwitchService.updatePhySwitch({'phySwitch':phySwitch});
            
            updateResult.$promise.then(function (data) {
            	$scope.isLoading = false;
            	if (data.success) {           		
            		showAlert('info', 'OK'); 
            	}else{
            		showAlert('warning', data.messages);
            	}
            });
        }
    }
    
    //显示提示信息
    function showAlert(type, msg) {
        $scope.data.msg = msg;
        $scope.data.type = type;
        $scope.data.isShow = true;
    }
}