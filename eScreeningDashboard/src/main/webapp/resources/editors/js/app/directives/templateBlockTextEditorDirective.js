(function(angular) {
    "use strict";

    Editors.directive('templateBlockTextEditor', ['textAngularManager', 'TemplateBlockService', 
                                                  function(textAngularManager, TemplateBlockService) {
        var editing = false;
        function addAvDeleteButton(editorScope, $element){
            //We had to do it this way because adding an ng-click gets stripped in the wysiwyg because of santaize
            $element.on('click', deleteElementHandler(editorScope));
        }

        function deleteElementHandler(editorScope){
        	return function(event){
	            editorScope.displayElements.popover.css('width', '62px');
	            
	            var container = editorScope.displayElements.popoverContainer;
	    
	            container
	            	.empty()
	            	.css('line-height', '28px')
	            	.append(createDeleteGroup(editorScope, $(this)));
	            
	            editorScope.showPopover($(this));  
        	};
        }
        
        function createDeleteGroup(editorScope, $element){
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
            return buttonGroup;
        }
        
        function addDeleteButtonToAll(editorScope, $editorEle){
            $editorEle
                .find("img.ta-insert-variable")
                .each(function(i, ele){ addAvDeleteButton(editorScope, $(ele));  });
        }
        
        function initTables(editorScope, $editorEle){
        	initTableEditing(editorScope, $editorEle);
        	$editorEle
        	.focusin(function(){
        		initTableEditing(editorScope, $editorEle);
        	})
        	.focusout(function(){
        		rmTableEditing($editorEle);
        	});
        	
        	//this disables the resizing of the table by firefox
        	document.execCommand('enableObjectResizing', false, 'false');
        }
        
        function rmTableEditing($editorEle){
        	editing = false;
        	$editorEle.find(".isResizable")
        		.resizable("destroy")
        		.removeClass(".isResizable");
        	$editorEle.find(".ui-resizable-handle").remove();
        	$editorEle.find(".tablewrap table")
        		.removeClass("editing")
        		.unwrap();
        	textAngularManager.refreshEditor('text-block-editor');
        }
        
        function initTableEditing(editorScope, $editorEle){
        	if(!editing){
        		editing = true;
	        	$editorEle
	        	.find("table")
	        	.each(function(i, table){
	        		var container = $(table);
	        		var sibTotalWidth;
	        		$(table)
	        			.addClass("editing")
	            		.find("tr:first td, tr:first th")
	            		  .addClass("isResizable")
	            		  .resizable({
	            			  handles: "e",  
	            			  start: function(event, ui){
	            		            sibTotalWidth = ui.originalSize.width + ui.originalElement.next().outerWidth();
	            		        },
	            		        stop: function(event, ui){
	            		            var cellPercentWidth=100 * ui.originalElement.outerWidth()/ container.innerWidth();
	            		            ui.originalElement.css('width', cellPercentWidth + '%');  
	            		            var nextCell = ui.originalElement.next();
	            		            var nextPercentWidth=100 * nextCell.outerWidth()/container.innerWidth();
	            		            nextCell.css('width', nextPercentWidth + '%');
	            		            textAngularManager.refreshEditor('text-block-editor');
	            		        },
	            		        resize: function(event, ui){ 
	            		            ui.originalElement.next().width(sibTotalWidth - ui.size.width); 
	            		        }
	            		  });
	        		      		
	        		$(table).find("tr")
	        			.addClass("isResizable")
	        			.resizable({handles:"s"});
	        		
	        		$(table).wrap($("<div/>").addClass("tablewrap"));
	        		
	            });
	        	
	        	$(".tablewrap").hover( deleteElementHandler(editorScope) );  
	        }
        }
        
        
        return {
            restrict: 'A',
            scope: false,
            link: function ($scope, $element, attrs) {
                var el = $element.get(0);
                el.addEventListener("DOMNodeInserted",function(e) {
                    
                    if(TemplateBlockService.avDragHandler(el, e)){
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
                        initTables($scope, $element);
                    }
                });
            }
        };
        
    }]);
})(angular);