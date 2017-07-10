

function PhySwitchListCtrl($scope,$rootScope,PhySwitchService,BaseTableService,$modal,$filter){

	

	initScope();
	  
/***********************functions***********************/
	function initScope() {
	      initParameters();
	      initFunctions();
	}
	   
	function initParameters() {
		refreshData();
	}
	
	function refreshData() {
    	
    	var getResult = PhySwitchService.getAllPhySwitchs();
    	
    	getResult.$promise.then(
                function (data) {
                    $scope.isLoading = false;
                    if (data.success) {
	                    $scope.dataList = data.result;    
	                    $scope.table = BaseTableService.getCustomizedTable($scope, $filter);  
	                    
                    }else{
                    	displayMsgDialog('warning', data.messages);
                    }
                }
         );
    	
    }
    	
    function initFunctions() {
    	
	    $scope.addPhySwitch = function () {
	           $scope.message = {
	                title: 'Add App',
	                msg: '',
	                type:'',
	                isShow:false
	            };
	            var modalInstance = $modal.open({
	                templateUrl: '/phystrix-admin/partials/phystrix/switch/addPhySwitch.html',
	                controller: 'AddPhySwitchCtrl',
	                resolve: {
	                    data: function () {
	                        return $scope.message;
	                    }
	                }
	            });
	            modalInstance.result.then(function (data) {
					refreshData();
	            }, function () {
	            });
	    };   
	    
	    $scope.deletePhySwitch = function(phySwitch){
	        var modalInstance = $modal.open({
	 
	            templateUrl: '/phystrix-admin/partials/common/deleteConfirm.html',
	            controller: 'DelPhySwitchCtrl',
	            resolve: {
	            	phySwitch: function () {
	                    return phySwitch;
	                }
	            }
	        });
	       

	        modalInstance.result.then(function () {
	            refreshData();
	        }, function () {
	        });
	    };
	    
    }
    
    
    
    function displayMsgDialog(header,body){
		var modalInstance = $modal.open({
				            templateUrl: '/phystrix-admin/partials/common/message.html',
				            controller: 'MessageCtrl',
				            resolve: {
				                msg: function () {
				                    return {
												headerText:header,
												bodyText:body
											};
				                }
				            }
		});
	}
}