// public/js/app.js

myapp = angular.module('sampleApp', 
	[
		'ngRoute',
		'ngCookies',
		'Routes',
		//LOCALIZATION MODULE
		'pascalprecht.translate',
		//CONTROLLERS
		'MainControllerModule', 
		'LoginControllerModule',
		'PageControllerModule', 
		'BearControllerModule',
		'LocaleControllerModule',
		//SERVICES
		'UtilsServiceModule'
	]
);

myapp.config(['$translateProvider', function ($translateProvider) {

    $translateProvider.translations('en', locale_en);
    $translateProvider.translations('es', locale_es);
     
    $translateProvider.preferredLanguage('en');

    $translateProvider.useCookieStorage();
}]);

/*
myapp.directive('header', function () {
	return {
		restrict: 'A', //This menas that it will be used as an attribute and NOT as an element. I don't like creating custom HTML elements
		replace: true,
		//scope: {user: '='}, // This is one of the cool things :). Will be explained in post.
		templateUrl: "header.html",
		controller: ['$scope', '$filter', function ($scope, $filter) {
		// Your behaviour goes here :)
			$scope.user = $rootScope.user;
		}]
	}
});
*/
