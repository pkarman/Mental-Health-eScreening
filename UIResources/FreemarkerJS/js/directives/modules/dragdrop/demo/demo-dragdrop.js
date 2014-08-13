(function() {
    
    "use strict";
    
    angular.module('demo.dragdrop', [
        'common.dragdrop'
    ])

    .controller('DemoDragDropCtrl', ['$scope', 'DragDropHandler', function($scope, DragDropHandler) {
        $scope.items = [
            {
                id: 3,
                name:'Item 1'
            },
            {
                id: 4,
                name:'Item 2'
            },
            {
                id: 5,
                name:'Item 3'
            },
            {
                id: 6,
                name:'Item 4'
            }
        ];
              
        $scope.objects = [
            {
                id: 1,
                name: 'New Item 1'
            },
            {
                id: 2,
                name: 'New Item 2'
            }
        ];

        $scope.codeExample = "<ul>\n" +
            "   <li\n" +
            "       ng-repeat='object in objects'\n" +
            "       draggable='object'\n" +
            "       draggable-target='#sortable'>\n" +
            "       {{ object.name }}\n" +
            "   </li>\n" +
            "</ul>\n\n" +

            "<ul\n" +
            "   droppable='items'\n" +
            "   ng-update='updateObjects(id, from, to)'\n" +
            "   ng-create='createObject(object, to)'\n" +
            "   id='sortable'>\n" +
            "   <li ng-repeat='item in items track by item.id'>\n"+
            "       {{ item.name }}\n" +
            "   </li>\n" +
        "</ul>";
        
        $scope.updateObjects = function(from, to) {
            var itemIds = _.pluck($scope.items, 'id');
            console.log(itemIds);
        };

        $scope.createObject = function(object, to) {
            var newItem = angular.copy(object);
            newItem.id = Math.ceil(Math.random() * 1000);
            DragDropHandler.addObject(newItem, $scope.items, to);
        };
        
        $scope.deleteItem = function(itemId) {
            $scope.items = _.reject($scope.items, function(item) {
                return item.id == itemId; 
            });
        };
    }])
    
;})();