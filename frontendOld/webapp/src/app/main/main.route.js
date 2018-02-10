(function() {
  'use strict';

  angular
    .module('webapp')
    .config(routeConfig);

  function routeConfig($routeProvider) {
    $routeProvider
      .when('/', {
        template: '<currencies-list></currencies-list>'
      })
      .when('/currencies', {
        template: '<currencies-list></currencies-list>'
      })
      .when('/converter', {
        template: '<currency-converter></currency-converter>'
      })
      .otherwise({
        redirectTo: '/'
      });
  }

})();
