angular.module('LocaleControllerModule', []).controller('LocaleController', function($http, $translate, $route, $window) {
		$translate.use($route.current.params.locale);
		$window.history.back();
});