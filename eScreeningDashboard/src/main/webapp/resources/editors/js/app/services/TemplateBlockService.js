angular.module('EscreeningDashboardApp.services.templateBlock', [])
    .factory('TemplateBlockService', function(){
        "use strict";
        var variableSeed = 0;
        var variablePrefix = "%var%";
        var parseContentReg = new RegExp("<img[^>]+ta-insert-variable=\"(" + variablePrefix + "[^\"]+)\"[^>]+>", "g");
        var replaceVarReg = new RegExp(variablePrefix, "g");
        var variableHash = {};
        
        function newAVElement(variable){
        	var name = variable.getName();
        	var hashCode = variablePrefix + this.addVariableToHash(variable);
            var idValue = hashCode + '_' + variableSeed++;
            
            return '<img ' +
              'class="ta-insert-variable text-info" ' +
              'id="'+ idValue + '" ' +
              'src="" ' +
              'ta-insert-variable="' + hashCode + '" ' +
              'alt="' + name + '" ' +
              'title="' + name + '" ' +
              'contenteditable="false" ' +
              '/>';
        }

        function removeDuplicateBlockContent(target, container){
            var varChildren = target.find(".ta-insert-variable");
            if (varChildren.size() > 0){
                console.log("Item inserted contains variables. Checking for duplicates");
                var changed = false;
                varChildren.each(function(i, child){
                    changed |= removeDuplicateBlockContent($(child), container);
                });
                return changed;
            }
            else if(target.prop("tagName") === 'IMG'
                 && target.hasClass('ta-insert-variable') 
                 && angular.isDefined(target.attr("src")) 
                 && target.attr("src") != "" 
                 && angular.isDefined(target.attr("id"))){
                    
                    console.log("Checking for duplicate after drag");
                    var changed = false;
                    container
                     .find("[id='"+ target.attr("id") + "']")
                     .each(function(){
                        if($(this).attr("src") != ""){
                            $(this).attr("src", "");
                        }
                        else{
                            console.log("Remvoing old duplicate variable.");
                             $(this).remove();
                             changed = true;
                        }
                     });
                    return changed;
             }
        }
        
        /**
         * Calculates a unique hash code for the give variable and its transformations
         */
        function hashVariable(variable) {
        	if(!variable || angular.isUndefined(variable.id)){
        		throw "Null variable cannot be hashed";
        	}
        	var stringRep  = "" + variable.id; 
        	if(variable.transformations){
        		variable.transformations.forEach(function(transformation){
        			stringRep += transformation.name;
        			if(transformation.params){
        				transformation.params.forEach(function(param){stringRep += param; });
        			}
        		});
        	}
        	//encode string into base-64 encoded ASCII string 
        	return window.btoa(unescape(encodeURIComponent(stringRep)));
		}
        
		function getVariableFromHash(hashCode){
			return variableHash[hashCode];
		}
        
        return {
            /**
             * This is the preferred way of creating instances of TemplateBlock
             */
            newBlock: function(jsonConfig, parent){
                return new EScreeningDashboardApp.models.TemplateBlock(jsonConfig, parent);
            },
            
            parseIntoContents: function(textContent){
                var contents = [];
                
                if(angular.isString(textContent)){
                	var fragments = textContent.split(parseContentReg);

                    fragments.forEach(function(frag){
                        var content = null;
                        if(frag.indexOf(variablePrefix) === 0){
                        	var varHash = frag.replace(replaceVarReg, "");
                        	var avObject = getVariableFromHash(varHash);
							
                            if(angular.isDefined(avObject)){
                                content = {
                                    type: "var",
                                    content : avObject
                                };
                            }
                        }
                        if(content === null){
                            content = {
                                type : "text",
                                content: frag
                            };
                        }

                        contents.push(content);
                    });
                }
                //remove extra line break
                if(contents.length > 0){
                    var lastContent = contents[contents.length-1];
                    if(lastContent.type == "text" && lastContent.content.search(/<p><br\/*><\/p>\w*$/) === -1){
                        lastContent.content = lastContent.content.replace(/(>[^<]+)<\w*br\w*\/*>([^<]*<[^>]+>\w*)$/, "$1$2");
                    }
                }
                return contents;
            },
            
            joinContents: function(contents){
                var joinedString = "";
                if(angular.isArray(contents)){
                    contents.forEach(function(textElement){
                        if(textElement.type === "text"){
                            joinedString += textElement.content;
                        }
                        else if(textElement.type === "var"){
                            joinedString += newAVElement(textElement.content);
                        }
                    }); 
                }
                return joinedString;
            },
            
            createAVElement : newAVElement,
            
            /**
             * Cleans up the given container after a drag and drop operation was carried out
             * @param containerElement - the DOM element which represents the editor which contains assessment variables
             * @param insertEvent - the event object for the dragged operation
             * @return true if something was changed
             */
            avDragHandler : function(containerElement, event){
                if(event.originalTarget){
                    var target = $(event.originalTarget);
                    var container = $(containerElement);
                    
                    return removeDuplicateBlockContent(target, container);
                }
                return false;
            },
			
            /**
             * Adds the given variable to the service's variable registry and returns a unique ID for the given variable
             */
            addVariableToHash: function(variable){
            	//create hashcode and set variable for that entry
            	var hashCode = hashVariable(variable);
            	variableHash[hashCode] = variable;
            	return hashCode;
            },
			
            /**
             * Initializes the a variable hash to have all variable found in the template
             */
			resetVariableHash: function(template){ 
				var self = this;
				variableHash = {}; 
				if(template && template.blocks){
					template.blocks.forEach(function(block){
						block.getVariables().forEach(self.addVariableToHash);
					});
				}
			}
            
        };
    });
