(function() {
  'use strict';

  angular
    .module('webapp')
    .directive('currencyConverter', currencyConverter);

  /** @ngInject */
  function currencyConverter() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/converter/converter.html',
      controller: CurrencyConverterController,
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function CurrencyConverterController($location, currencyService, exchangeRateService) {
      var vm = this;
      vm.actions = {
        refresh : refresh,
        convert : convert
      };

      vm.data = {
        currencyFrom: "",
        amountFrom: 1,
        amountTo: "-",
        currencyTo: "",
        currencyList: [],
        currencyFromExchangeRates: []
      };

      init();

      function init() {
        currencyService.findCurrencies()
          .then(function successCallback(response) {
            vm.data.currencyList = response.data;
            vm.data.currencyFrom=getCurrencyById($location.search().from);
            refresh();
          }, function errorCallback() {
          });
      }

      function refresh() {
        if (vm.data.currencyFrom.length === 0) {
          return;
        }
        exchangeRateService.findExchangeRates([vm.data.currencyFrom.id])
          .then(function successCallback(response) {
            vm.data.currencyFromExchangeRates = response.data;
            vm.data.currencyTo = getExchangeRateByToId($location.search().to);
            convert();
          }, function errorCallback() {
          });
      }

      function convert() {
        if (!isNaN(vm.data.amountFrom) && !isNaN(vm.data.currencyTo.exchangeRate)) {
          vm.data.amountTo = vm.data.amountFrom * vm.data.currencyTo.exchangeRate;
        } else {
          vm.data.amountTo = "-";
        }
      }

      function getCurrencyById(currencyId) {
        var result = vm.data.currencyList[0];
        for (var i in vm.data.currencyList) {
          if (currencyId === vm.data.currencyList[i].id) {
            result = vm.data.currencyList[i];
          }
        }
        return result;
      }

      function getExchangeRateByToId(currencyId) {
        var result = vm.data.currencyFromExchangeRates[0];
        for (var i in vm.data.currencyFromExchangeRates) {
          if (currencyId === vm.data.currencyFromExchangeRates[i].to) {
            result = vm.data.currencyFromExchangeRates[i];
          }
        }
        return result;
      }
    }
  }

})();
