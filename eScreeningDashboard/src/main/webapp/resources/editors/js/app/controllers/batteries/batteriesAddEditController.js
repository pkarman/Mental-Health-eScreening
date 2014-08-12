Editors.controller('batteryAddEditController',['$rootScope','$scope','$state','$stateParams','battery','sections','BatteryService',function($rootScope,$scope,$state,$stateParams,battery,sections,BatteryService){
	$scope.totalSections = 0;
	$scope.totalModules = 0;
	$scope.isDirty = false;
    $scope.selectedSurveyModulesIsDirty = false;
	
	$scope.availSections = [];
	$scope.batterySections = [];
	$scope.sections = sections;
	
	$scope.totalSurveysLen = 0;
	
	$scope.currentlySelectedBatteryUIObject = {};
	$scope.currentlySelectedBattery = battery;
	
	
	$scope.$watch('sections',function(newVal, oldVal){
		$scope.sections.forEach(function(section){
			var sec = section.toUIObject();
			sec.visible = false;
			
			for (var i=0;i<sec.surveys.length;i++)
			{
				$scope.totalSurveysLen++;
			}
			
			sec.surveys.forEach(function(survey){
				survey.visible = false;
			});
			$scope.availSections.push(sec);
		});
		//alert('Total Surveys Length::' + $scope.totalSurveysLen);
		// Now, make a batterySections copy, otherwise we will be databound to availSections.
		angular.copy($scope.availSections, $scope.batterySections);
	});
	
	$scope.$watch('battery',function(newVal,oldVal){
		$scope.currentlySelectedBatteryUIObject = $scope.currentlySelectedBattery.toUIObject();

        $scope.batterySections.forEach(function(batterySectionUIObject){
            batterySectionUIObject.visible = false;
            batterySectionUIObject.surveys.forEach(function(batterySurveyUIObject) {
                $scope.currentlySelectedBatteryUIObject.surveys.forEach(function(surveyUIObject){
                    if(batterySectionUIObject.id === surveyUIObject.surveySection.id){
                        batterySectionUIObject.visible = true;
                        surveyUIObject.visible = true;
                    }
                    if(batterySurveyUIObject.id === surveyUIObject.id) {
                        batterySurveyUIObject.visible = true;
                    }
                });
            });
        });
		
		console.log('BATTERYSECTIONS:: ' + JSON.stringify($scope.batterySections));
		
		// Second to Lastly, set visibility of availSections surveys on the basis of what is present on batterySections
		/*var secIdx = 0;
		$scope.batterySections.forEach(function(section){
            if (section.id == $scope.availSections[secIdx].id){
                var survIdx = 0;
                section.surveys.forEach(function(survey){
                    if (survey.id == $scope.availSections[secIdx].surveys[survIdx].id){
                        $scope.availSections[secIdx].surveys[survIdx].visible = !survey.visible;
                    }
                    survIdx++;
                });
            }
            secIdx++;
		});*/


        $scope.batterySections.forEach(function(batterySectionUIObject) {
            $scope.availSections.forEach(function(availableSectionUIObject){
                if(batterySectionUIObject.id === availableSectionUIObject.id) {
                    batterySectionUIObject.surveys.forEach(function(batterySurveyUIObject) {
                        availableSectionUIObject.surveys.forEach(function(availableSurveyUIObject) {
                            if(batterySurveyUIObject.id === availableSurveyUIObject.id) {
                                availableSurveyUIObject.visible = !batterySurveyUIObject.visible;
                            }
                        });
                    });
                }
            });
        });

        $scope.availSections.forEach(function(availableSectionUIObject){
            availableSectionUIObject.visible = availableSectionUIObject.surveys.some(function(availableSurveyUIObject){
                if(availableSurveyUIObject.visible){
                    return true;
                }
            });
        });
		
		// Lastly, disable an availSection if all of its Surveys are not Visible.
		/*$scope.availSections.forEach(function(section){
			var secvisible = false;
			section.surveys.forEach(function(survey){
				if (survey.visible){secvisible = true;}
			});
			section.visible = secvisible;
		});*/
	});
	
	$scope.setEditSurveySectionsVisibility = function(survey, section){
		if (survey.surveySection.id == section.id  && survey.visible)
			section.visible = true;
		else
			section.visible = false;
		
		for (var i=0;i<section.surveys.length;i++){
			if (survey.id == section.surveys[i].id){
				section.surveys[i].visible = true;
				section.visible = true;
			}
		}
		
		return section;
	};

    var performDirtyCheckOfSelectedModules = function (addNewlySelectedSurveys){
        // check to see if the battToSave.surveys has the same survey collection. If it is the same, do not mark the form as dirty. Otherwise mark the form as dirty.
        if(Object.isArray($scope.currentlySelectedBattery.getSurveys()) && Object.isArray($scope.batterySections)){
            var selectedSurveySectionDomainObjects = EScreeningDashboardApp.models.Battery.convertToSurveySectionDomainObjects($scope.batterySections);
            var selectedVisibleSurveysDomainObjects = EScreeningDashboardApp.models.Battery.findVisibleSurveys(selectedSurveySectionDomainObjects);

            if(isSelectedSurveyListDirty($scope.currentlySelectedBattery.getSurveys(), selectedVisibleSurveysDomainObjects, addNewlySelectedSurveys)) {
                $scope.selectedSurveyModulesIsDirty = true;
            } else {
                $scope.selectedSurveyModulesIsDirty = false;
            }
        }
    };

    var isSelectedSurveyListDirty = function(previouslyPersistedSurveys, modifiedSelectedSurveys, addNewlySelectedSurveys){
        var selectedSurveyDirty = false,
            saveNewlyAddedSelectedSurveys = (Object.isDefined(addNewlySelectedSurveys) && Object.isBoolean(addNewlySelectedSurveys))? true: false,
            newlyAddedSelectedSurveys = [];

        // Determine if any new surveys been added to the currently selected survey list that have not been previously persisted.
        // If so, set the selected survey list dirty status to true and save new surveys in array to later be added to the previously
        // persisted surveys, if the addNewlySelectedSurveys boolean is true.
        modifiedSelectedSurveys.forEach(function(modifiedSelectedSurvey){
            var modifiedSelectedSurveyFound = previouslyPersistedSurveys.some(function(previouslyPersistedSurvey){
                if(modifiedSelectedSurvey.getId() === previouslyPersistedSurvey.getId()){
                    return true;
                }
            });

            if(modifiedSelectedSurveyFound == false) {
                if (selectedSurveyDirty === false) {
                    selectedSurveyDirty = true
                }

                if(saveNewlyAddedSelectedSurveys){
                    newlyAddedSelectedSurveys.push(modifiedSelectedSurvey);
                }
            }
        });


        // Determine if previously persisted surveys have been removed from selection.
        // If so, mark for deletion.
        previouslyPersistedSurveys.forEach(function(previouslySelectedSurvey){
            var previouslyPersistedSurveyFound = modifiedSelectedSurveys.some(function(modifiedSelectedSurvey){
                if(previouslySelectedSurvey.getId() === modifiedSelectedSurvey.getId()) {
                    return true;
                }
            });

            if(previouslyPersistedSurveyFound == false){
                previouslySelectedSurvey.markedForDeletion();
                previouslyPersistedSurveys.remove(previouslySelectedSurvey);

                if (selectedSurveyDirty === false) {
                    selectedSurveyDirty = true;
                }
            }
        });

        if (saveNewlyAddedSelectedSurveys) {
            newlyAddedSelectedSurveys.forEach(function(newlyAddedSelectedSurvey){
                previouslyPersistedSurveys.push(newlyAddedSelectedSurvey);
            });
        }

        return selectedSurveyDirty;
    };
	
    $scope.createSection = function(){
    	$scope.totalSections++;
        return {surveySectionId:null,name:"",displayOrder:$scope.totalSections};
    };

    $scope.removeSection = function(sec, ind){
           // Remove the item from battery.sections
           $scope.currentlySelectedBattery.surveys.splice(ind,1);
           $scope.currentlySelectedBattery.surveys.filter(function(e){return e;});
           
           // Push section into available sections
           $scope.sections.push(sec);
    };
    
    $scope.selectForBattery = function(survey){
        // First, make non-visible in availSections.
        survey.visible = false;
        $scope.selectedSurveyModulesIsDirty = $scope.isDirty = true;

        var survId = survey.id;
        var secId = survey.surveySection.id;
        // Check the parent Survey Section, and if all surveys are not visible, set visible to false.
        $scope.availSections.forEach(function(section){
            var howManyVisible = 0;
            if (secId == section.id){
                section.surveys.forEach(function(survey){
                    if (survey.visible){
                        howManyVisible++;
                    }
                });
                section.visible = section.isExpanded = howManyVisible > 0 ? true : false;
            }
        });

        // Set visibility on the Battery Sections.
        $scope.batterySections.forEach(function(section){
            if (secId == section.id){
                section.visible = true;
                // Now find the Survey match and make it visible.
                section.surveys.forEach(function(survey){
                    if (survId == survey.id){
                        survey.visible = true;
                    }
                });
            }
        });

        performDirtyCheckOfSelectedModules();
    };

    $scope.removeFromSections = function (survey) {
        // Lastly, remove the module from the parent section in the Available column.
    	
        /*var secInd = 0;
        for (var i=0;i<$scope.sections.length;i++){
            if ($scope.sections[i].name == module.surveySectionItem.name){
                secInd = i;
            }
        }
        if ($scope.sections[secInd].surveys.length == 1){
            $scope.sections[secInd].surveys = [];
            return;
        } else {
            var tempMods = $scope.sections[secInd].surveys;
            for (var j=0;j<$scope.sections[secInd].surveys.length;j++){
                if ($scope.sections[secInd].surveys[j].name == module.name){
                    
                   tempMods = $scope.sections[secInd].surveys.splice(j,1);
                }
            }

        }*/
    };



    $scope.removeFromBattery = function(survey){
    	// First, make non-visible in batterySections.
		survey.visible = false;
		$scope.selectedSurveyModulesIsDirty = $scope.isDirty = true;
		var survId = survey.id;
		var secId = survey.surveySection.id;
		// Check the parent Survey Section, and if all surveys are not visible, set visible to false.
		$scope.batterySections.forEach(function(section){
			var howManyVisible = 0;
			if (secId == section.id){
				section.surveys.forEach(function(survey){
					if (survey.visible){
						howManyVisible++;
					}
				});
				section.visible = section.isExpanded = howManyVisible > 0 ? true : false;
			}
		});
		
		// Set visibility on the Available Sections.
		$scope.availSections.forEach(function(section){
			if (secId == section.id){
				section.visible = true;
				// Now find the Survey match and make it visible.
				section.surveys.forEach(function(survey){
					if (survId == survey.id){
						survey.visible = true;
					}
				});
			}
		});

        performDirtyCheckOfSelectedModules();
        // First, copy over the Module to it's parent Section.
    	/*survey.visible = false;
    	
    	var survId = survey.id;
		var secId = survey.surveySectionId;
		// Check the parent Survey Section, and if all surveys are not visible, set visible to false.
		$scope.batterySections.forEach(function(section){
			var howManyVisible = 0;
			if (secId == section.id){
				section.surveys.forEach(function(survey){
					if (survey.visible){
						howManyVisible++;
					}
				});
				section.visible = section.isExpanded = howManyVisible > 0 ? true : false;
			}
		});
		
		// Set visibility on the Available Sections.
		$scope.availSections.forEach(function(section){
			if (secId == section.id){
				section.visible = true;
				// Now find the Survey match and make it visible.
				section.surveys.forEach(function(survey){
					if (survId == survey.id){
						survey.visible = true;
					}
				});
			}
		});*/
    	
    	// Grab 
    	
        /*for (var i=0;i<$scope.sections.length;i++){
            if ($scope.sections[i].name == module.surveySectionItem.name) {
                
                $scope.sections[i].surveys.push(module);
            }
        }

        // Now remove the Module from it's parent Section in battery.sections
        var par = {};
        for (i=0;i<$scope.batterySections.length;i++){
            if (module.surveySectionItem.name == $scope.batterySections[i].name){
                par = $scope.batterySections[i];
            }
        }
        if (par && par.name){
            if (par.name == module.surveySectionItem.name){
                for (i=0;i<par.surveys.length;i++){
                    if (par.surveys[i].name == module.name){
                        par.surveys.splice(i,1);
                    }
                }
                // If the section has no Modules, remove it, too.
                if (par.surveys.length <1){
                    for (i=0;i<$scope.batterySections.length;i++){
                        if (module.surveySectionItem.name == $scope.batterySections[i].name){
                            $scope.batterySections.splice(i,1);
                        }
                    }
                }
            }
        }*/
    };

    $scope.sortingLog = [];

    $scope.sortableOpts = {
	    update: function(e, ui) {
	    	console.log('Sortable Update:: ');
	    	$scope.isDirty = true;
	      },
	      stop: function(e, ui) {
	        // this callback has the changed model
	        console.log('Sortable Stop:: ');
	        $scope.isDirty = true;
	      }
    };
    
    // Button actions
    $scope.saveBattery = function(){
    	performDirtyCheckOfSelectedModules(true);
        $scope.currentlySelectedBatteryUIObject.surveys = $scope.currentlySelectedBattery.getSurveysAsSurveyUIObjects();
    	var currentlySelectedUpdatedBattery = new EScreeningDashboardApp.models.Battery($scope.currentlySelectedBatteryUIObject);
    	if (currentlySelectedUpdatedBattery.getId() === -1){
    		// Create.
    		BatteryService.create(BatteryService.setCreateBatteryRequestParameter(currentlySelectedUpdatedBattery)).then(
                function(battery){
                    $state.go('home');
                }
            );
    	} else {
    		// Update.
    		BatteryService.update(BatteryService.setUpdateBatteryRequestParameter(currentlySelectedUpdatedBattery)).then(
                function(battery){
                    $state.go('home');
                }
            );
    	}
    };
    
    $scope.cancelBattery = function(){$state.go('home');};
}]);