// public/js/appRoutes.js
angular.module('Routes', [])

.config(['$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {

	//================================================
    // Check if the user is connected
    //================================================
    var checkLoggedin = function($q, $timeout, $http, $location, $rootScope){
      // Initialize a new promise
      var deferred = $q.defer();

      // Make an AJAX call to check if the user is logged in
      $http.get(Constants.REST_PATH + 'auth/logged').success(function(user){
        // Authenticated
        if (user.logged == true) {
          //$rootScope.user = user;
          $timeout(deferred.resolve, 0);
        // Not Authenticated
        } else {
          $rootScope.message = 'You need to log in.';
          $timeout(function(){deferred.reject();}, 0);
          $location.url('/login');
        }
      });

      return deferred.promise;
    };
    //================================================
    
    //================================================
    // Add an interceptor for AJAX errors
    //================================================
    $httpProvider.responseInterceptors.push(function($q, $location) {
      return function(promise) {
        return promise.then(
          // Success: just return the response
          function(response){
            return response;
          },
          // Error: check the error status to get only the 401
          function(response) {
            if (response.status === 401)
              $location.url('/login');
            return $q.reject(response);
          }
        );
      }
    });
    //================================================

    // ================ ROUTING ======================
    $locationProvider.html5Mode(true);
    var noTemplate = " ";
	$routeProvider

		// home page
		.when('/', {
			templateUrl: 'views/home.html',
			controller: 'MainController'
		})

		.when('/login', {
			templateUrl: 'views/login.html',
			controller: 'LoginController'
		})
		.when('/logout', {
			template: noTemplate,
			controller: 'LogoutController'
		})
		.when('/page', {
			templateUrl: 'views/page.html',
			controller: 'PageController'
		})
		.when('/bears', {
			templateUrl: 'views/bear.html',
			controller: 'BearController',
			resolve: { loggedin: checkLoggedin }
		})
		.when('/401', {
			templateUrl: '401.html',
		})
		.when('/locale/:locale', {
			template: noTemplate,
			controller: 'LocaleController'
		})
		.otherwise({
			templateUrl: '404.html'
		})
		
		;

}])
.run(function($rootScope, $http){
    $rootScope.message = '';
    // Logout function is available in any pages
    $rootScope.logout = function(){
      $rootScope.message = 'Logged out.';
      $http.post(Constants.REST_PATH + 'auth/logout');
    };
});

