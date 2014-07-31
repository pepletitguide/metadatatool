// public/js/controllers/GeekCtrl.js
angular.module('BearControllerModule', []).controller('BearController', ['$scope','$http', '$location', function($scope, $http, $location) {

	function getBears() {
        
		$http.get('/api/bears')
		    .success(function(data) {
		        $scope.bears = data;
		    })
		    .error(function(data) {
		        
		    });
	}

	getBears();

    $scope.createBear = function(){
        $http.post('/api/bears', $scope.form)
            .success(function(data) {
                getBears();
            })
            .error(function(data) {
                
            });
    };
    $scope.deleteBear = function(id){
        $http.delete('/api/bears/'+id)
            .success(function(data) {
                getBears();
            })
            .error(function(data) {
                
            });
    };
}
]);

