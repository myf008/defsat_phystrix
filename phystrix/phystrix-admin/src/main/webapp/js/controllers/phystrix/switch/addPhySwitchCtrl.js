'use strict';

//展示信息的dialog的controller
function AddPhySwitchCtrl($scope, $modalInstance, data, PhySwitchService) {
	
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
    	 $scope.phySwitch = {
    			 appId:'',
    			 switchStatus:'1',
    	 }
    	 
    }
    
	
    function initFunctions() {
    	    	
        //不显示提示信息
        $scope.closeAlert = function () {
            $scope.data.isShow = false;
            
        };  
    	
    }
    
    $scope.addPhySwitch = function(){
    	var result = PhySwitchService.addPhySwitch({
            'phySwitch': $scope.phySwitch
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