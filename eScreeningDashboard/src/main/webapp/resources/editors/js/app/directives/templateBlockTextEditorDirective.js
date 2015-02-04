(function(angular) {
    "use strict";

    Editors.directive('templateBlockTextEditor', ['textAngularManager', 'TemplateBlockService', 'eventBus',
                                                  function(textAngularManager, TemplateBlockService, eventBus) {
 
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
        
        function rmTableEditing($editorScope, $editorEle, skipResizeDestroy){
        	
        	if(!skipResizeDestroy){
        		$editorEle.find("td.ui-resizable, th.ui-resizable").resizable().resizable("destroy");
        	}
        	
        	$editorEle.find(".ui-resizable-handle").remove();
        	$editorEle.find(".ui-resizable").removeClass("ui-resizable");
        	$editorEle.find(".tablewrap table")
        		.removeClass("editing")
        		.unwrap();
        	
        	//this doesn't work in all cases
        	$editorScope.updateTaBindtaTextElement();        	
        }
        
        function addResizeWithPercentage(container, elementsToResize, handles){
        	var sibTotalWidth;
        	elementsToResize
	  			.resizable({
				  'handles': handles,  
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
        }
        
        function initTableEditing(editorScope, $editorEle){
        	console.debug("init table editing");
        	
        	function updateEditing(){
        		console.debug("updating tables as editable");
	        	$editorEle
	        	.find("table:not(.editing)")
	        	.each(function(i, table){
	        		var $table = $(table);
	        		console.debug("Setting table as editable:", $table);
	        		
	        		$table.addClass("editing");
	        		
	        		$table.find("tr:first")
	        				.find("td:not(:last-child), th:not(:last-child)")
	        				.each(function(i, element){
			        			if(i == 0){
			        				addResizeWithPercentage($table, $(this), "s, e, se");
			        			}
			        			else{
			        				addResizeWithPercentage($table, $(this), "e"); 
			        			}
			        		});
	  		
	        		$table.find("tr:not(:first)")
		    				.find("td:first, th:first")
							.resizable({handles: "s"});			
	        				
	        		var wrapper = $("<div/>").addClass("tablewrap").hover( deleteElementHandler(editorScope) );
	        		
	        		$table.wrap(wrapper);
	            });
        	}
        	
        	//This starts editing of tables we are loading from db
        	rmTableEditing(editorScope, $editorEle, true);
        	updateEditing();
        	
        	//Setup the initialization of new tables inserted
        	$editorEle.focus(function(){
        		updateEditing();
        	});
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
                        initTableEditing($scope, $element);
                    }                   
                });
                
                //The following is to remove the editor atifacts when the block is being saved. 
                
                //This works for IE and Chrome but not firefox
                $element.blur(function(){
                	rmTableEditing($scope, $element);
                });
                  
                //TODO: this doesn't work in chrome or IE because in rmTableEditing the call to $editorScope.updateTaBindtaTextElement(); does not actually cause an update 
                //subscribe to onModalClose event
                eventBus.onModalClose($scope, 
                		'modules.templates.edit.controller:resources/editors/views/templates/templateblockmodal.html', 
                		function(){ 
                	 		
                			rmTableEditing($scope, $element);
                			
                	 		//console.debug("Cleaned editor is: ", $element.html());
                		}, true);
            }
        };
        
    }]);
})(angular);