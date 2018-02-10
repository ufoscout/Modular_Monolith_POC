(function() {
  'use strict';

   angular
    .module('webapp')
    .directive('main', mainDirective);

  /** @ngInject */
  function mainDirective() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/main/main.html',
      controller: MainController,
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function MainController() {
      //var vm = this;

    }
  }

})();
