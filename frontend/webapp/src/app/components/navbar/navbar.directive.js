(function() {
  'use strict';

  angular
    .module('webapp')
    .directive('exchageRateNavbar', exchageRateNavbar);

  /** @ngInject */
  function exchageRateNavbar() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/navbar/navbar.html',
      controller: NavbarController,
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function NavbarController() {
      var vm = this;

      vm.isCollapsed = true;

    }
  }

})();
