/**
 * Created by Bryan Henderson on 4/16/2014.
 */
var Editors = angular.module("Editors",
    [
        'ngResource',
        'ui.router',
        'ui.bootstrap',
        'ngTable',
        'xeditable',
        'ui.tree',
        'ui.sortable',
        'ngAnimate',
        'textAngular',
        'restangular',
        'angularUtils.directives.uiBreadcrumbs',
        'EscreeningDashboardApp.services.battery',
        'EscreeningDashboardApp.services.survey',
        'EscreeningDashboardApp.services.surveypage',
        'EscreeningDashboardApp.services.surveysection',
        'EscreeningDashboardApp.services.question',
        'EscreeningDashboardApp.services.template',
        'EscreeningDashboardApp.services.templateType',
        'EscreeningDashboardApp.services.assessmentVariable',
        'EscreeningDashboardApp.services.question',
        'EscreeningDashboardApp.filters.messages',
        'EscreeningDashboardApp.filters.freemarkerWhiteSpace',
        'EscreeningDashboardApp.filters.limitToWithEllipsis'
    ]);

/**
 * A generic confirmation for risky actions.
 * Usage: Add attributes: ng-really-message="Are you sure"? ng-really-click="takeAction()" function
 */
Editors.directive('ngReallyClick', [function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function() {
                var message = attrs.ngReallyMessage;
                var shouldSkip = Object.isDefined(attrs.ngReallySkipWhen) 
                    && attrs.ngReallySkipWhen.length > 0 
                    && scope.$apply(attrs.ngReallySkipWhen);
                    
                if (shouldSkip || (message && confirm(message))) {
                    scope.$apply(attrs.ngReallyClick);
                }
            });
        }
    };
}]);

Editors.config(function(RestangularProvider, $provide) {

    $provide.decorator('taOptions', ['taRegisterTool', 'taCustomRenderers', 'taSelectableElements', '$delegate', '$modal', function(taRegisterTool, taCustomRenderers, taSelectableElements, $delegate, $modal){

	    // Add <code /> as an selectable element
	    taSelectableElements.push('code');

	    // Add custom renderer for transforming the insert variable HTML
	    taCustomRenderers.push({
		    selector: 'img',
		    customAttribute: 'ta-insert-variable',
		    renderLogic: function(element){
			    var img = angular.element('<img/>');
			    var attributes = element.prop("attributes");
			    // loop through element attributes and apply them on img
			    angular.forEach(attributes, function(attr) {
				    img.attr(attr.name, attr.value);
			    });
			    img.attr('alt', img.attr('title'));
			    element.replaceWith(img);
		    }
	    });

		// Register the custom addVariable tool with textAngular
	    // $delegate is the taOptions we are decorating
		taRegisterTool('insertVariable', {
			display: '<button title="Add Variable" class="btn btn-default"><i class="fa fa-plus"></i> Add Variable</button>',
			tooltiptext: 'Insert Assessment Variable',
			action: function(deferred) {

				/*
					Credit:
				    http://jsfiddle.net/timdown/cCAWC/3/
				    http://stackoverflow.com/questions/4687808/contenteditable-selected-text-save-and-restore
				 */
				function saveSelection() {
					var sel;
					if (window.getSelection) {
						sel = window.getSelection();
						if (sel.getRangeAt && sel.rangeCount) {
							return sel.getRangeAt(0);
						}
					} else if (document.selection && document.selection.createRange) {
						return document.selection.createRange();
					}
					return null;
				}

				/*
					Note:
					This is continued from above. Alternative approach would be to use Rangy:
				    https://code.google.com/p/rangy/wiki/SelectionSaveRestoreModule
				 */
				function restoreSelection(range) {
					if (range) {
						if (window.getSelection) {
							sel = window.getSelection();
							sel.removeAllRanges();
							sel.addRange(range);
						} else if (document.selection && range.select) {
							range.select();
						}
					}
				}

				/* Credit:
					http://stackoverflow.com/questions/4233265/contenteditable-set-caret-at-the-end-of-the-text-cross-browser
				 */
				function placeCaretAtEnd(el) {
					el.focus();
					if (typeof window.getSelection != "undefined"
						&& typeof document.createRange != "undefined") {
						var range = document.createRange();
						range.selectNodeContents(el);
						range.collapse(false);
						var sel = window.getSelection();
						sel.removeAllRanges();
						sel.addRange(range);
					} else if (typeof document.body.createTextRange != "undefined") {
						var textRange = document.body.createTextRange();
						textRange.moveToElementText(el);
						textRange.collapse(false);
						textRange.select();
					}
				}

				/*
					Credit:
				    http://jsfiddle.net/timdown/vXnCM/
				    http://stackoverflow.com/questions/6249095/how-to-set-caretcursor-position-in-contenteditable-element-div
				 */
				function setCaret(el, pos) {
					var range = document.createRange();
					var sel = window.getSelection();

					try {

						range.setStart(el.childNodes[0].childNodes[0], pos);
						range.collapse(true);
						sel.removeAllRanges();
						sel.addRange(range);
						el.focus();
					} catch(e) {
						placeCaretAtEnd(el);
					}
				}

				/*
					Credit:
					http://jsfiddle.net/timdown/JPb75/1/
				*/
				function insertHtmlAfterSelection(html) {
					var sel, range, expandedSelRange, node;
					if (window.getSelection) {
						sel = window.getSelection();
						if (sel.getRangeAt && sel.rangeCount) {
							range = window.getSelection().getRangeAt(0);
							expandedSelRange = range.cloneRange();
							range.collapse(false);

							// Range.createContextualFragment() would be useful here but is
							// non-standard and not supported in all browsers (IE9, for one)
							var el = document.createElement("div");
							el.innerHTML = html;
							var frag = document.createDocumentFragment(), node, lastNode;
							while ((node = el.firstChild)) {
								lastNode = frag.appendChild(node);
							}
							range.insertNode(frag);

							// Preserve the selection
							if (lastNode) {
								expandedSelRange.setEndAfter(lastNode);
								sel.removeAllRanges();
								sel.addRange(expandedSelRange);
							}
						}
					} else if (document.selection && document.selection.createRange) {
						range = document.selection.createRange();
						expandedSelRange = range.duplicate();
						range.collapse(false);
						range.pasteHTML(html);
						expandedSelRange.setEndPoint("EndToEnd", range);
						expandedSelRange.select();
					}
				}

				/*
					Credit:
				    http://jsfiddle.net/TjXEG/1/
				    http://stackoverflow.com/questions/16736680/get-caret-position-in-contenteditable-div-including-tags
				 */
				function getCaretCharacterOffsetWithin(element) {
					var caretOffset = 0;
					if (typeof window.getSelection != "undefined") {
						var range = window.getSelection().getRangeAt(0);
						var preCaretRange = range.cloneRange();
						preCaretRange.selectNodeContents(element);
						preCaretRange.setEnd(range.endContainer, range.endOffset);
						caretOffset = preCaretRange.toString().length;
					} else if (typeof document.selection != "undefined" && document.selection.type != "Control") {
						var textRange = document.selection.createRange();
						var preCaretTextRange = document.body.createTextRange();
						preCaretTextRange.moveToElementText(element);
						preCaretTextRange.setEndPoint("EndToEnd", textRange);
						caretOffset = preCaretTextRange.text.length;
					}
					return caretOffset;
				}

				var addVariableTool = this;

				var el = $("div[id^='taTextElement']").get(0);

				var elCaret = getCaretCharacterOffsetWithin(el);

				var selRange = saveSelection();

				var modalInstance = $modal.open({
					templateUrl: 'resources/editors/views/templates/assessmentvariablemodal.html',
					controller: ['$scope', '$modalInstance', 'AssessmentVariableService', function($scope, $modalInstance, AssessmentVariableService) {
                        $scope.assessmentVariables = AssessmentVariableService.getLastCachedResults();

						$scope.assessmentVariable = {};

						$scope.$watch('assessmentVariable.id', function(newValue, oldValue) {

							var embed, embedAlt, embedCode;

							if (newValue !== oldValue && $scope.assessmentVariable && $scope.assessmentVariable.id) {

								embed =
									'<img ' +
										'class="ta-insert-variable text-info" ' +
										'src="" ' +
										'ta-insert-variable="' + $scope.assessmentVariable.id + '" ' +
										'title="(' + $scope.assessmentVariable.getName() + ')"' +
										'contenteditable="false" ' +
									'/>';

								// This version shows text in image for FF but if dragged, it will be duplicated
								embedAlt =
									'<img ' +
										'class="ta-insert-variable text-info" ' +
										'src="" ' +
										'ta-insert-variable="' + $scope.assessmentVariable.id + '" ' +
										'alt="(' + $scope.assessmentVariable.getName() +
										')" ' +
										'title="(' + $scope.assessmentVariable.getName() + ')" ' +
										'contenteditable="false" ' +
									'/>';

								// This is the code version and requires onElementSelect.element (below)
								// to be set to 'code' for editing/deleting element
								embedCode =
									'<code + ' +
										'class="ta-insert-variable text-info" +' +
										'ta-insert-variable="' + $scope.assessmentVariable.id + '" +' +
										'title="(' + $scope.assessmentVariable.getName() + ')">' +
											$scope.assessmentVariable.getName()+
									'</code>';

								$modalInstance.close(embed);
	                        }

                        }, true);

						$scope.cancel = function() {
							$modalInstance.dismiss();
						};

					}]
				});

				modalInstance.result.then(function(embed) {

					/*
						Credit:
					    http://jsfiddle.net/aaronroberson/6pz5gjmo/1/
					 */

					restoreSelection(selRange);

					setCaret(el, elCaret);

					insertHtmlAfterSelection(embed);

					deferred.resolve(addVariableTool.$editor().updateTaBindtaHtmlElement());

				});

				return false;

			},
			onElementSelect: {
				element: 'img',
				onlyWithAttrs: ['ta-insert-variable'],
				action: function (event, $element, editorScope) {
					// Setup the editor toolbar
					// Edit bar logic based upon http://hackerwins.github.io/summernote
					event.preventDefault();

					editorScope.displayElements.popover.css('width', '62px');

					var container = editorScope.displayElements.popoverContainer;

					container.empty();
					container.css('line-height', '28px');

					var buttonGroup = angular.element('<div class="btn-group">');

					var unLinkButton = angular.element('<button type="button" class="btn btn-default btn-sm btn-small" tabindex="-1" unselectable="on"><i class="fa fa-trash-o icon-trash-o"></i></button>');
					// directly before this click event is fired a digest is fired off whereby the reference to $element is orphaned off
					unLinkButton.on('click', function (event) {
						event.preventDefault();
						$element.replaceWith($element.contents());
						editorScope.updateTaBindtaTextElement();
						editorScope.hidePopover();
					});
					buttonGroup.append(unLinkButton);
					container.append(buttonGroup);
					editorScope.showPopover($element);
				}
			}
		});
		// DO NOT add the button to the default toolbar definition, but if you did, this is how you would:
		//$delegate.toolbar[$delegate.toolbar.length] = ['insertVariable'];
		return $delegate;
	}]);
});


Editors.run(function(editableOptions) {
    editableOptions.theme = 'bs3';
});

Editors.run(['$rootScope', '$state', '$stateParams', '$modal', 'Restangular', function ($rootScope,   $state,   $stateParams,  $modal, Restangular) {
    Restangular.setErrorInterceptor(function(response, deferred, responseHandler) {

        if(Object.isDefined(response) && Object.isDefined(response.data)){
            if(Object.isArray(response.data.errorMessages)){
                response.data.errorMessages.forEach(function (errorMessage) {
                    $rootScope.addMessage(new BytePushers.models.Message({type: BytePushers.models.Message.ERROR, value: errorMessage.description}));
                });
            }
        }

        return true; // error not handled
    });
    // It's very handy to add references to $state and $stateParams to the $rootScope
    // so that you can access them from any scope within your applications.For example,
    // <li ng-class="{ active: $state.includes('assessments.list') }"> will set the <li>
    // to active whenever 'assessments.list' or one of its descendents is active.
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;

}]);
Editors.value('MessageHandler', new BytePushers.models.MessageHandler());
Editors.value('Template', new EScreeningDashboardApp.models.Template());
Editors.value('TemplateType', new EScreeningDashboardApp.models.TemplateType());
Editors.value('TemplateVariableContent', new EScreeningDashboardApp.models.TemplateVariableContent());
