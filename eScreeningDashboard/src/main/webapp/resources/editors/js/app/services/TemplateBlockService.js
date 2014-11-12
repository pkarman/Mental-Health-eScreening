angular.module('EscreeningDashboardApp.services.templateBlock', [])
    .factory('TemplateBlockService', function(){
        "use strict";
        var variableSeed = 0;
        var parseContentReg = /<img[^>]+id="(var\d+)_\d+"[^>]+>/;
        var replaceVarReg = /var/;
        
        function newAVElement(id, name){
            var idValue = "var" + id + '_' + variableSeed++;
            return '<img ' +
              'class="ta-insert-variable text-info" ' +
              'id="'+ idValue + '" ' +
              'src="" ' +
              'ta-insert-variable="' + id + '" ' +
              'alt="' + name + '" ' +
              'title="' + name + '" ' +
              'contenteditable="false" ' +
              '/>';  
        }
        
        function removeDuplicateBlockContent(target, container){
            var varChildren = target.find(".ta-insert-variable");
            if (varChildren.size() > 0){
                console.log("Item inserted contains variables. Checking for duplicates");
                varChildren.each(function(i, child){
                    removeDuplicateBlockContent($(child), container); 
                });
            }
            else if(target.prop("tagName") === 'IMG'
                 && Object.isDefined(target.attr("src")) 
                 && target.attr("src") != "" 
                 && Object.isDefined(target.attr("id"))){
                    
                    console.log("Checking for duplicate after drag");
                    
                    container
                     .find("[id="+ target.attr("id") + "]")
                     .each(function(){
                        if($(this).attr("src") != ""){
                            $(this).attr("src", "");
                        }
                        else{
                            console.log("Remvoing old duplicate variable.");
                             $(this).remove();
                        }
                     });
             }
        }
        
        return {
            /**
             * This is the preferred way of creating instances of TemplateBlock
             */
            newBlock: function(jsonConfig, parent){
                return new EScreeningDashboardApp.models.TemplateBlock(jsonConfig, parent);
            },
            
            parseIntoContents: function(textContent, variableHash){
                var contents = [];
                
                if(angular.isString(textContent)){
                    var fragments = textContent.split(parseContentReg);

                    fragments.forEach(function(frag){
                        var content = null;
                        if(frag.indexOf("var") == 0){
                            var avId = parseInt(frag.replace(replaceVarReg, ""));
                            if(!isNaN(avId)){
                                var avObject = variableHash[avId];
                                if(avObject){
                                    content = {
                                        type: "var",
                                        content : avObject
                                    };
                                }
                            }
                        }
                        if(content == null){
                            content = {
                                type : "text",
                                content: frag
                            };
                        }

                        contents.push(content);
                    });
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
                            joinedString += newAVElement(textElement.content.id, textElement.content.getName());
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
             */
            avDragHandler : function(containerElement, event){
                if(event.originalTarget){
                    var target = $(event.originalTarget);
                    var container = $(containerElement);
                    
                    removeDuplicateBlockContent(target, container);
                   
                }
            }
        }
    });