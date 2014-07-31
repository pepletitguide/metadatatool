angular.module('LocaleControllerModule', []).controller('LocaleController', function($http, $translate, $route, $window) {
		console.log($route.current.params.locale);
		$translate.use($route.current.params.locale);
		$window.history.back();
});