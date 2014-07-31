// public/js/controllers/MainCtrl.js
angular.module('MainControllerModule', []).controller('MainController', function($http, $scope, $rootScope, $location) {

	$scope.tagline = $rootScope.message;	
	$rootScope.message = '';

	$scope.register = function(){
		console.log(angular.toJson($scope.loginForm));
		$scope.loginForm = {};
	}
});


angular.module('MainControllerModule').factory('api', function ($http, $cookies) {
  return {
      init: function (token) {
          $http.defaults.headers.common['X-Access-Token'] = token || $cookies.token;
      }
  };
});
