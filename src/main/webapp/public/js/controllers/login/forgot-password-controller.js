angular.module('ForgotPasswordControllerModule', []).controller('ForgotPasswordController', function($http, $scope, $rootScope, $location, $window) {
	$scope.forgotPassword = function(){
		var userMail = $scope.forgotPasswordForm.email;
		$http.post(Constants.REST_PATH + 'auth/forgotPassword', {
			email: $scope.forgotPasswordForm.email
		})
		.success(function(user){
			// No error: reset e-mail sent
			$scope.message = 'Password reset e-mail was sent to ' + userMail;
			//$location.url('/bears');
		})
		.error(function(){
			// Error: request failed
			$scope.message = 'Something went wrong. Please try again';
		});
		$scope.forgotPasswordForm = {};
	}
})