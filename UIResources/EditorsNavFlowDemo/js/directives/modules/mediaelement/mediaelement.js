(function() {

    "use strict";

    angular.module('common.mediaelement', [])

        .directive('mediaelement', ['$timeout', function($timeout) {
            return {
                restrict: 'A',
                link: function(scope, element, attrs) {
                    // Adding this $timeout allows iPads and iPhones to wait until one digest cycle has gone by and then load the video.
                    $timeout(function() {
                        attrs.$observe('src', function() {
                            element.mediaelementplayer({
                                // if set, overrides <video width>
                                videoWidth: -1,
                                // if set, overrides <video height>
                                videoHeight: -1,
                                // width of audio player
                                audioWidth: 400,
                                // height of audio player
                                audioHeight: 50,
                                // initial volume when the player starts
                                startVolume: 0.5,
                                // useful for <audio> player loops
                                loop: false,
                                // enables Flash and Silverlight to resize to content size
                                enableAutosize: true,
                                // the order of controls you want on the control bar (and other plugins below)
                                features: ['playpause','progress','current','duration','tracks','volume','fullscreen'],
                                // Hide controls when playing and mouse is not over the video
                                alwaysShowControls: false,
                                // force iPad's native controls
                                iPadUseNativeControls: false,
                                // force iPhone's native controls
                                iPhoneUseNativeControls: false,
                                // force Android's native controls
                                AndroidUseNativeControls: false,
                                // forces the hour marker (##:00:00)
                                alwaysShowHours: false,
                                // show framecount in timecode (##:00:00:00)
                                showTimecodeFrameCount: false,
                                // used when showTimecodeFrameCount is set to true
                                framesPerSecond: 25,
                                // turns keyboard support on and off for this instance
                                enableKeyboard: true,
                                // when this player starts, it will pause other players
                                pauseOtherPlayers: true,
                                // array of keyboard commands
                                keyActions: []
                            });
                        });
                    });
                }
            };
        }]);

})();
