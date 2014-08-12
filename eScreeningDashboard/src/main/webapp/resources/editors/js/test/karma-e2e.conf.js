/**
 * Configuration File for Karma Test-Runner, dedicated to End-to-End Testing VA WYSIWYG.
 * 
 * @author Bryan Henderson
 * @date   07/15/2014
 */
module.exports = function(config){
	config.set({
		basePath:'..',
		frameworks:['ng-scenario'],
		files:['test/e2e/**/*.js'],
		exclude:[],
		port:8080,
		logLevel:config.LOG_INFO,
		autoWatch:false,
		browsers:['Chrome','ChromeCanary','Firefox','Safari','Opera','PhantomJS'],
		singleRun:false,
		urlRoot:'/_karma_/',
		proxies:{
			'/':'http://localhost:9000/'
		}
	});
};