'use strict';

//展示信息的dialog的controller
function AddPhyConfigCtrl($scope, $modalInstance, data, PhyConfigService) {
	
    /***********************执行开始***********************/
    initScope();
    /***********************执行结束***********************/

    /***********************functions***********************/
    function initScope() {
        initParameters();
        initFunctions();
    }
    
    function initParameters() {
    	 $scope.data = data;
    	 $scope.phyConfig = {
    			 appId:'',
    			 commandKey:'',
    			 commandGroup:'',
    			 fallback:'',
    			 isolationStgy:'SEMAPHORE',
    			 maxRequest:'500',
    			 timeout:'1000',
    			 threadPoolSize:'10',
    			 requestThreshold:'20',
    			 errorThreshold:'30',
    			 circuitBreakTime:'10000'
    	 }
    	 
    }
	
    
    function initFunctions() {
    	    	
        //不显示提示信息
        $scope.closeAlert = function () {
            $scope.data.isShow = false;
            
        };  
    	
    }
    
    $scope.addPhyConfig = function(){
    	var result = PhyConfigService.addPhyConfig({
            'phyConfig': $scope.phyConfig
          });
    	processData(result);
    }
    
	function processData(result) {
        result.$promise.then(
            function (data) {
            	console.log(data);
                $scope.isLoading = false;
                if (data.success) {
                   $modalInstance.close('ok');    
 
                } else {
                    showAlert('warning', data.messages);
                }
         });
    } 
	
    
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel');
    };
    
    
    
    //显示提示信息
    function showAlert(type, msg) {
        $scope.data.msg = msg;
        $scope.data.type = type;
        $scope.data.isShow = true;
    }
}