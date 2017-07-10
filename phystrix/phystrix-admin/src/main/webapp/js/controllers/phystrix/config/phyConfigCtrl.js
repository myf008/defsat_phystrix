

function PhyConfigListCtrl($scope,$rootScope,PhyConfigService,BaseTableService,$modal,$filter){

	

	initScope();
	  
/***********************functions***********************/
	function initScope() {
	      initParameters();
	      initFunctions();
	}
	   
	function initParameters() {
		refreshData();
	}
	
	function refreshData(){
		var getResult = PhyConfigService.getAllPhyConfigs();
    	getResult.$promise.then(
                function (data) {
                    $scope.isLoading = false;
                    if (data.success) {
	                    $scope.dataList = data.result;    
	                    $scope.table = BaseTableService.getCustomizedTable($scope, $filter);  
	                    $scope.hideTable=false;
                    }else{
                    	displayMsgDialog('warning', data.messages);
                    }
                }
         );
	}
		
    function initFunctions() {
    	
	    $scope.addPhyConfig = function () {
	           $scope.message = {
	                title: 'Add Phystrix Config',
	                msg: '',
	                type:'',
	                isShow:false
	            };
	            var modalInstance = $modal.open({
	                templateUrl: '/phystrix-admin/partials/phystrix/config/addPhyConfig.html',
	                controller: 'AddPhyConfigCtrl',
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
	    
	    $scope.deletePhyConfig = function(phyConfig){
	        var modalInstance = $modal.open({
	 
	            templateUrl: '/phystrix-admin/partials/common/deleteConfirm.html',
	            controller: 'DelPhyConfigCtrl',
	            resolve: {
	            	phyConfig: function () {
	                    return phyConfig;
	                }
	            }
	        });
	       

	        modalInstance.result.then(function () {
	            refreshData();
	        }, function () {
	        });
	    }
	    
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