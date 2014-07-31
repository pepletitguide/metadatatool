angular.module('LoginControllerModule', []).controller('LoginController', function($http, $scope, $rootScope, $location) {
	
	$scope.tagline = $rootScope.message;	
	$rootScope.message = '';
	$scope.loginForm = {};

	$scope.login = function(){
		$http.post('auth/login', {
			username: $scope.loginForm.username,
			password: $scope.loginForm.password,
		})
		.success(function(user){
			// No error: authentication OK
			$scope.tagline = 'Authentication successful!';
			$location.url('/bears');
		})
		.error(function(){
			// Error: authentication failed
			$scope.tagline = 'Authentication failed.';
			$location.url('/login');
		});
		$scope.loginForm = {};
	};
}).controller('LogoutController', function($rootScope, $window) {
	$rootScope.logout();
	$window.history.back();
});