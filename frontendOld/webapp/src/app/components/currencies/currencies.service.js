(function() {
  'use strict';

  angular
    .module('webapp')
    .factory('currencyService', currencyService);

  /** @ngInject */
  function currencyService($http) {

    var service = {
      findCurrencies : findCurrencies
    };

    return service;

    function findCurrencies(filter) {
      return $http({
        url: "/rest/currency/currencies",
        method: "GET",
        params: {filter: filter}
      });
    }

  }

})();
