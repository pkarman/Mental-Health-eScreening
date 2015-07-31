/**
 * Angular filter to pull custom white space tags out of freemarker template content.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.filters.freemarkerWhiteSpace', [])
  .filter('removeSpaceTags', function(){
    'use strict';
    var tagRegex = /\$\{((LINE_BREAK)|(NBSP))\}/g;
    return function (content) {
        if(Object.isDefined(content)){
            return content.replace(tagRegex, " ");
        }
        return content;
        
    };
});