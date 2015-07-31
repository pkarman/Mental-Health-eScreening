(function() {

    "use strict";

    angular.module('common.print', [])

        .directive('print', ['$timeout', function($timeout) {
            return {
                link: function(scope, element, attrs) {
                    function getHtml() {
                        return $(attrs.print).html();
                    }

                    element.click(function() {
                        var data = getHtml();
                        var mywindow = window.open('', attrs.printTitle, 'height=400,width=800');

                        mywindow.document.write('<html><head><title>' + attrs.printTitle + '</title>');
                        mywindow.document.write('<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css" type="text/css" />');
                        mywindow.document.write('</head><body >' + data + '</body></html>');
                        mywindow.print();

                        $timeout(function() {
                            mywindow.close();
                        }, 10);

                        return true;
                    });
                }
            };
        }]);

})();