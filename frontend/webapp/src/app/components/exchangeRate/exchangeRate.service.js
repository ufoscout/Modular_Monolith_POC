(function() {
  'use strict';

  angular
    .module('webapp')
    .factory('exchangeRateService', exchangeRateService);

  /** @ngInject */
  function exchangeRateService($http) {

    var service = {
      findExchangeRates : findExchangeRates
    };

    return service;

    function findExchangeRates(currencyList) {
      return $http({
        url: "/rest/exchangeRate/rates",
        method: "GET",
        params: {currencies: currencyList.join(",")}
      });
    }

  }

})();
