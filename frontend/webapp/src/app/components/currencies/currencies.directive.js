(function () {
  'use strict';

  angular
    .module('webapp')
    .directive('currenciesList', currenciesList);

  /** @ngInject */
  function currenciesList() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/currencies/currencies.html',
      controller: CurrenciesController,
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function CurrenciesController(currencyService, exchangeRateService) {
      var vm = this;

      vm.actions = {
        refresh: refresh
      };

      vm.data = {
        currencyFilter: '',
        currencyList: [],
        filteredCurrencyList: [],
        exchangeRates: []
      };

      vm.getExchangeRate = function(currencyFrom, currencyTo) {
        for (var i in vm.data.exchangeRates) {
          var rate = vm.data.exchangeRates[i];
          if ( (currencyFrom === rate.from) && (currencyTo === rate.to) ) {
            return rate.exchangeRate;
          }
        }
        return "-";
      };

      refresh();

      function refresh() {
        currencyService.findCurrencies(vm.data.currencyFilter)
          .then(function successCallback(response) {
            vm.data.filteredCurrencyList = response.data;
            if(vm.data.currencyList.length === 0){
              vm.data.currencyList = vm.data.filteredCurrencyList;
            }
            var currencyIDs = [];
            for (var i in vm.data.filteredCurrencyList) {
              currencyIDs.push(vm.data.filteredCurrencyList[i].id);
            }
            refreshExchangeRates(currencyIDs);
          }, function errorCallback() {
        });
      }

      function refreshExchangeRates(currencyIDs) {
        exchangeRateService.findExchangeRates(currencyIDs)
          .then(function successCallback(response) {
            vm.data.exchangeRates = response.data;
          }, function errorCallback() {
          });
      }
    }
  }
})();
