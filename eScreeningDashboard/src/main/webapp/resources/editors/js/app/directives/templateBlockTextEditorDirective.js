(function(angular) {
    "use strict";

    Editors.directive('templateBlockTextEditor', ['textAngularManager', 'TemplateBlockService', 
                                                  function(textAngularManager, TemplateBlockService) {
        
        function addAvDeleteButton(editorScope, $element){
            //We had to do it this way because adding an ng-click gets stripped in the wysiwyg because of santaize
            $element.on('click', function(event){
                editorScope.displayElements.popover.css('width', '62px');
                
                var container = editorScope.displayElements.popoverContainer;
        
                container.empty();
                container.css('line-height', '28px');
        
                var buttonGroup = angular.element('<div class="btn-group">');
        
                var unLinkButton = angular.element('<button type="button" class="btn btn-default btn-sm btn-small" tabindex="-1" unselectable="on"><i class="fa fa-trash-o icon-trash-o"></i></button>');
                // directly before this click event is fired a digest is fired off whereby the reference to $element is orphaned off
                unLinkButton.on('click', function (event) {
                    event.preventDefault();
                    $element.remove();
                    editorScope.updateTaBindtaTextElement();
                    editorScope.hidePopover();
                });
                buttonGroup.append(unLinkButton);
                container.append(buttonGroup);
                
                editorScope.showPopover($(event.target));   
            });
        }
        
        function addDeleteButtonToAll(editorScope, $editorEle){
            $editorEle
                .find("img.ta-insert-variable")
                .each(function(i, ele){ addAvDeleteButton(editorScope, $(ele));  });
        }
        
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
                    var target = $(e.target);
                    if(target.prop("tagName") === 'IMG' 
                        && target.hasClass('ta-insert-variable')){
                        addAvDeleteButton($scope, target);
                    }
                    
                });
                
                $element.mouseover(function(){
                    if(!$element.data("init")){
                        $element.data("init", true);
                        addDeleteButtonToAll($scope, $element);
                    }
                });
            }
        };
        
    }]);
})(angular);