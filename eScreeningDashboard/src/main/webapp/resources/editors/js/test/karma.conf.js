/**
 * Configuration File for Karma Test-Runner, dedicated to Unit Testing VA WYSIWYG.
 * 
 * @author Bryan Henderson
 * @date   07/15/2014
 */
module.exports = function(config){
	config.set({
		basePath:'..',
		frameworks:['jasmine'],
		files:[
		       '../../../../vendor-libs/angularjs/angular.min.js',
		       '../../../../vendor-libs/angularjs/angular-route.min.js',
		       '../../../../vendor-libs/angularjs/angular-sanitize.min.js',
		       '../../../../vendor-libs/angularjs/angular-resource.min.js',
		       '../../../../vendor-libs/angularjs/angular-animate.min.js',
		       'test/lib/angular-mocks.js',
		       'test/unit/**/*.js'
		       ],
		exclude:[],
		port:8080,
		logLevel:config.LOG_INFO,
		autoWatch:true,
		browsers:['Safari','Chrome', 'ChromeCanary','Firefox','Opera','PhantomJS'],
		singleRun:false
	});
};