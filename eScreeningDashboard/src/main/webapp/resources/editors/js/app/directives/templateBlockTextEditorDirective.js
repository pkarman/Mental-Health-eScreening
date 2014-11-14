(function(angular) {
    "use strict";

    Editors.directive('templateBlockTextEditor', ['textAngularManager', 'TemplateBlockService', 
                                                  function(textAngularManager, TemplateBlockService) {
        
        return {
            restrict: 'A',
            scope: false,
            link: function ($scope, $element, attrs) {
                var el = $element.get(0);
                el.addEventListener("DOMNodeInserted",function(e) {
                    //console.log("editor changed");
                    
                    if(TemplateBlockService.avDragHandler(el, e)){
                        console.log("updating editor because of change.");
                        textAngularManager.refreshEditor('text-block-editor');
                    }
                });
            }
        };
        
    }]);
})(angular);