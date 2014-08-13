/**
 * Created by Bryan on 2/10/14.
 */
var module = angular.module("dd.directives.dragdrop", ['dd.services']);

module.directive('ddDraggable', ['$rootScope', 'uuid', function($rootScope, uuid) {
    return {
        restrict: 'A',
        link: function(scope, el, attrs, controller) {
            console.log("linking draggable element");

            angular.element(el).attr("draggable", "true");
            var id = angular.element(el).attr("id");
            if (!id) {
                id = uuid.new();
                angular.element(el).attr("id", id);
            }

            el.bind("dragstart", function(e) {
                e.dataTransfer.setData('text', id);

                $rootScope.$emit("DD-DRAG-START");
            });

            el.bind("dragend", function(e) {
                $rootScope.$emit("DD-DRAG-END");
            });
        }
    }
}]);

module.directive('ddDropTarget', ['$rootScope', 'uuid', function($rootScope, uuid) {
    return {
        restrict: 'A',
        scope: {
            onDrop: '&'
        },
        link: function(scope, el, attrs, controller) {
            var id = angular.element(el).attr("id");
            if (!id) {
                id = uuid.new();
                angular.element(el).attr("id", id);
            }

            el.bind("dragover", function(e) {
                if (e.preventDefault) {
                    e.preventDefault(); // Necessary. Allows us to drop.
                }

                e.dataTransfer.dropEffect = 'move';  // See the section on the DataTransfer object.
                return false;
            });

            el.bind("dragenter", function(e) {
                // this / e.target is the current hover target.
                angular.element(e.target).addClass('dd-over');
            });

            el.bind("dragleave", function(e) {
                angular.element(e.target).removeClass('dd-over');  // this / e.target is previous target element.
            });

            el.bind("drop", function(e) {
                if (e.preventDefault) {
                    e.preventDefault(); // Necessary. Allows us to drop.
                }

                if (e.stopPropogation) {
                    e.stopPropogation(); // Necessary. Allows us to drop.
                }
                var data = e.dataTransfer.getData("text");
                var dest = document.getElementById(id);
                var src = document.getElementById(data);

                scope.onDrop({dragEl: src, dropEl: dest});
            });

            $rootScope.$on("DD-DRAG-START", function() {
                var el = document.getElementById(id);
                angular.element(el).addClass("dd-target");
            });

            $rootScope.$on("DD-DRAG-END", function() {
                var el = document.getElementById(id);
                angular.element(el).removeClass("dd-target");
                angular.element(el).removeClass("dd-over");
            });
        }
    }
}]);
