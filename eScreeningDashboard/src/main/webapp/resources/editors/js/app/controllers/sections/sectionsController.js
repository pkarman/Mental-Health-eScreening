Editors.controller('sectionsController', ['$rootScope','$scope','$state', '$resource','$location', '$window', '$filter', 'SurveySectionService','sections',function($rootScope,$scope,$state,$resource,$location,$window,$filter,SurveySectionService,sections){
            $scope.sections = sections;
            $scope.editSections = [];
            $scope.inError = false;
            $scope.errors = [];
            $scope.errors = [];
            $scope.battery = $rootScope.selectedBattery;
            $scope.isDirty = false;
            $scope.predicate = "-displayOrder";
            $scope.reverse = false;
            
            $scope.setDirty = function(){
            	$scope.isDirty = true;
            };
            
            $scope.$watch('sections',function(newVal, oldVal){
            	if ($scope.sections && $scope.sections.length > 0){
            		
            		$scope.editSections = [];
            		for (var i=0;i<$scope.sections.length;i++){
            			$scope.editSections.push($scope.returnEditSection($scope.sections[i]));
            		}
            	}else{
            		$scope.editSections = [];
            	}
            });
            
            $scope.$watch('editSections', function(newVal, oldVal){
                if ($scope.editSections && $scope.editSections.length > 0){
                    $scope.editSections.forEach(function(section){
                        if (section.surveys && section.surveys.length == 0){
                            section.isExpanded = true;
                        }else{
                            section.isExpanded = false;
                            section.visible = true;
                            section.surveys.forEach(function(surveyUI){
                                surveyUI.visible = true;
                            });
                        }
                    });
                }
            });
            
            $scope.isAllExpanded = false;
            
            $scope.secRemoveCopy = {};
            
            $scope.deleteCheck = function(section){
            	if (section.surveys.length >0){
            		alert('This Section contains Modules. Please remove the Modules to other Section(s) before attempting to delete.');
            	} else {
            		var rem = confirm("This will delete the Section, and it will not be available in the future. Click 'OK' to permanently delete it.");
            		if (rem == true)
            			$scope.removeSection(section);
            		else
            			return;
            	}
            };
            
            /* @REFACTOR - Pending new JSON call structure. - JBH (7/8/2014) */
            /* @REFACTOR - Changing to a visible/invisible with flagging for removal, per new Save scheme. - JBH (7/30/2014) */
            $scope.removeSection = function(editorSurveySection){
                editorSurveySection.markedForDeletion = true;
                editorSurveySection.visible = false;
        		$scope.isDirty = true;
            	var surveySection = new EScreeningDashboardApp.models.SurveySection(editorSurveySection);
            	//sect.visible = editorSurveySection.visible;
            	//sect.markedForDeletion = editorSurveySection.markedForDeletion;
            	var ID = surveySection.getId();
            	if (surveySection.getId()<0){
            		// Section hasn't been saved, and this one doesn't create Sections, so return.
            		return;
            	}
            	
            };

            $scope.expandAll = function(){
            	console.log('Expand All');
                for (var i=0;i<$scope.editSections.length;i++){
                    $scope.editSections[i].isExpanded=true;
                }
                $scope.isAllExpanded = true;
                $scope.$apply();
            };
            
            $scope.collapseAll = function(){
            	console.log('Collapse All');
            	for (var i=0;i<$scope.editSections.length;i++){
            		$scope.editSections[i].isExpanded = false;
            	}
            	$scope.isAllExpanded = false;
            	$scope.$apply();
            };
            
            /* @REFACTORED - Pending new JSON call structure. - JBH (7/11/2014) */
            $scope.saveSection = function(editorSurveySection){
            	console.log('Save Section:: ' + JSON.stringify(editorSurveySection));
                var surveySection = new EScreeningDashboardApp.models.SurveySection(editorSurveySection);
                //sect.visible = editorSurveySection.visible;
                //sect.markedForDeletion = editorSurveySection.markedForDeletion;

            	if (surveySection.getId()<0){
            		// Create.
            		SurveySectionService.create(SurveySectionService.setCreateSurveySectionRequestParameter(surveySection)).then(
                        function(section){
                            var editSection = $scope.returnEditSection(section);
                            for (var i=0;i<$scope.sections.length;i++){
                                if ($scope.sections[i].getId() == section.getId()){
                                    // Perform inserts.
                                    $scope.sections[i] = section;
                                    $scope.editSections[i] = editSection;
                                }
                            }
                        });
            	} else {
            		// Update.
            		if (surveySection.isMarkedForDeletion()){
            			//alert('marked for deletion');
            			SurveySectionService.remove(SurveySectionService.setRemoveSurveySectionRequestParameter(surveySection.getId())).then(
                        function(section){
                            SurveySectionService.query(SurveySectionService.setQuerySurveySectionSearchCriteria(null)).then(function (existingSections){
                                $scope.sections = existingSections;
                                console.log('Sections:: ' + existingSections);

	                            }, function(responseError) {
	                                $rootScope.errors.push(responseError.getMessage());
	                                console.log('Sections Query Error:: ' + JSON.stringify($rootScope.errors));
	
	                            });
	                        }
	                    );
            		}else{
            			//alert('marked for update');
            			//delete sect.markedForDeletion;
            			//delete sect.visible;
            			SurveySectionService.update(SurveySectionService.setUpdateSurveySectionRequestParameter(surveySection)).then(
                            function(section){
                                var editSection = $scope.returnEditSection(section);
                                $scope.sections.forEach(function (surveySection, index, array) {
                                    if(surveySection.getId() == section.getId()) {
                                        array[index] = section;
                                    }
                                });

                                $scope.editSections.forEach(function(editSurveySection, index, array) {
                                    if(editSurveySection.id == editSection.id){
                                        array[index] = editSection;
                                    }
                                });
                            }
                        );
            		}
            	}
            };

            /* @REFACTORED - New JSON call structure. - JBH (7/10/2014) */
            $scope.save = function(){
            	console.log('Sections:: Save');
            	if ($scope.isDirty){
                    EScreeningDashboardApp.getInstance().sort($scope.editSections, "surveyArrayHasIncreased", "+");
                    EScreeningDashboardApp.getInstance().sort($scope.editSections, "markedForDeletion", "-");
            		for (var i=0; i<$scope.editSections.length;i++){
                        $scope.saveSection($scope.editSections[i]);
            		}
            	}
                $state.go('home');
            };
            
            $scope.returnEditSection = function(domainSection){
            	var secIsExpanded = false;
            	if (domainSection.getSurveys() === undefined)
            		secIsExpanded = true;
            	else
            		secIsExpanded = false;
            	return {
            			id:domainSection.getId(),
            			name:domainSection.getName(),
            			description:domainSection.getDescription(),
            			createdDate:domainSection.getCreatedDate(),
            			displayOrder:domainSection.getDisplayOrder(),
            			isExpanded: secIsExpanded,
            			surveys:$scope.returnEditSurveys(domainSection.getSurveys()),
                        surveyArrayHasIncreased: domainSection.surveyArrayHasIncreased,
                        visible: domainSection.isVisible(),
                        markedForDeletion: domainSection.isMarkedForDeletion()
            		};
            };
            
            $scope.returnEditSurveys = function(domainSurveys){
            	var surveys = [];
            	for (var i=0;i<domainSurveys.length;i++){
            		var item = {
            			id:domainSurveys[i].getId(),
            			name:domainSurveys[i].getName(),
            			description:domainSurveys[i].getDescription(),
            			version:domainSurveys[i].getVersion(),
            			displayOrder:domainSurveys[i].getDisplayOrder(),
            			mha:domainSurveys[i].isMHA(),
                        visible: domainSurveys[i].isVisible(),
                        markedForDeletion: domainSurveys[i].isMarkedForDeletion(),
            			createdDate:domainSurveys[i].getCreatedDate(),
                        surveySection: {
                            id: domainSurveys[i].getSurveySection().getId(),
                            name: domainSurveys[i].getSurveySection().getName(),
                            description: domainSurveys[i].getSurveySection().getDescription(),
                            createdDate: domainSurveys[i].getSurveySection().getCreatedDate(),
                            markedForDeletion: domainSurveys[i].getSurveySection().isMarkedForDeletion(),
                            visible: domainSurveys[i].getSurveySection().isVisible()
                        }
            		};
            		surveys.push(item);
            	}
            	
            	return surveys;
            };

            $scope.cancel = function(){
            	if (!$scope.isDirty)
            		$state.go('home');
            	else {
            		var cancelConf = confirm('Discard changes and return to Editor Menu?');
            		if (cancelConf == true)
            			$state.go('home');
            		else {
            			return;
            		}
            	}
            };

            $scope.addSection = function() {
                var secToAdd = $rootScope.createSection();
                secToAdd.index = $scope.sections.length + 2;
                $scope.sections.unshift($rootScope.createSection());
            };

            $scope.sortableOptions = {
                connectWith: ".connected-mods-container",
            	update: function(e, ui) {
                    $scope.isDirty = true;
                    $scope.expandAll();
            	},
        	    stop: function(e, ui) {
        	    	var itemUI = ui.item.scope();
        	    	console.log('Item:: ' + itemUI);
        	    	var toIndex = ui.item.sortable.dropindex;
        	    	console.log('Drop Index:: ' + toIndex);
        	    	var output = '';
        	    	for (var property in itemUI) {
        	    	  output += property + ': ' + itemUI[property]+'; ';
        	    	}
        	    	console.log(output);
                    EScreeningDashboardApp.getInstance().checkWhetherSurveySectionSurveyArrayHasIncreased($scope.sections, $scope.editSections);
                    for (var i=0;i<$scope.editSections.length;i++){
                        var item = $scope.editSections[i];
                        if (item.surveys.length == 0){
                            item.isExpanded = true;
                        }

                        item.displayOrder = i+1;
                        for (var j=0;j<item.surveys.length;j++){

                            var surv = item.surveys[j];
                            if (j==toIndex){
                                item.isExpanded = true;
                            }
                            surv.displayOrder = j+1;
                        }
                    }
                    $scope.isDirty = true;
                    $scope.collapseAll();
                    $scope.expandAll();
        	    }
            };
            
            
            $scope.sortableSecOptions = {
                connectWith: '.connected-sec-container',
                update:function(e, ui){

                },
                stop:function(e, ui){

                    for (var i=0;i<$scope.editSections.length;i++){
                        var item = $scope.editSections[i];
                        if (!item.surveys || item.surveys.length <= 0)
                            item.isExpanded = true;
                        item.displayOrder = i+1;
                        for (var j=0;j<item.surveys.length;j++){
                            var surv = item.surveys[j];
                            surv.displayOrder = j+1;
                        }

                    }

                    $scope.isDirty = true;
                    $scope.collapseAll();
                    $scope.expandAll();
                }
            };
        }]);