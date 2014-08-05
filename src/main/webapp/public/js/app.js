//App initialization
myapp = angular.module('sampleApp', 
	[
		'ngRoute',
		'ngCookies',
		'Routes',
		//LOCALIZATION MODULE
		'pascalprecht.translate',
		//CONTROLLERS
		'MainControllerModule',
		'MenuControllerModule', 
		
		//LOGIN CONTROLLERS
		'LoginControllerModule',
		'ForgotPasswordControllerModule',
		'ResetPasswordControllerModule',
		
		'PageControllerModule', 
		'LocaleControllerModule',
		//SERVICES
		'UtilsServiceModule'
	]
);
//Locales initialization
myapp.config(['$translateProvider', function ($translateProvider) {

    $translateProvider.translations('en', locale_en);
    $translateProvider.translations('es', locale_es);
     
    $translateProvider.preferredLanguage('en');

    $translateProvider.useCookieStorage();
}]);
