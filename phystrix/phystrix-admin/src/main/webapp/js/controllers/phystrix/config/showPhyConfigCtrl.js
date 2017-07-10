

function ShowPhyConfigCtrl($scope, $http, $modal, $routeParams, PhyConfigService) {
    
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
        
        
        var getResult = PhyConfigService.getPhyConfigByID({'appId':$routeParams.appId,'commandKey':$routeParams.commandKey});
        
        getResult.$promise.then(function (data) {
        	$scope.isLoading = false;
        	if (data.success) {  
        		$scope.phyConfig = data.result;
        	}else{
        		showAlert('warning', data.messages);
        	}
        });
    
    }
    
    function initFunctions() {
    	
    	$scope.closePhyConfig = function(){
    		 window.location = "/phystrix-admin/#/para";
    	};
        
        $scope.closeAlert = function () {
        	$scope.data.isShow = false;
        };
        
        $scope.updatePhyConfig = function (phyConfig) {
        	phyConfig.commandKey = $scope.phyConfig.commandKey;
        	phyConfig.commandGroup = $scope.phyConfig.commandGroup;
        	phyConfig.fallback = $scope.phyConfig.fallback;
        	phyConfig.isolationStgy = $scope.phyConfig.isolationStgy;
        	phyConfig.maxRequest = $scope.phyConfig.maxRequest;
        	phyConfig.timeout = $scope.phyConfig.timeout;
        	phyConfig.threadPoolSize = $scope.phyConfig.threadPoolSize;
        	phyConfig.requestThreshold = $scope.phyConfig.requestThreshold;
        	phyConfig.errorThreshold = $scope.phyConfig.errorThreshold;
        	phyConfig.circuitBreakTime = $scope.phyConfig.circuitBreakTime;
            var updateResult = PhyConfigService.updatePhyConfig({'phyConfig':phyConfig});
            
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