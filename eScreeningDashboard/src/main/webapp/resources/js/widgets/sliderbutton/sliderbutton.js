/**
 * sliderbutton.js - The JavaScript (non-JQuery reliant) slider button widget. Built to
 * duplicate the behavior and basic look-and-feel of iPhone slider button widgets. Originally 
 * conceived as a JavaScript-only JQuery component, this has become largely CSS, so instructions
 * on usage reside here in place of code, for the time being.
 * @author Bryan Henderson
 * @date 11/20/2013
 */

/**
 * USAGE:
 * 
 * HTML (use link element or @import to bring in /js/widgets/sliderbutton/sliderbutton.css:
 * <div class="slider-frame">
 *  <span class="slider-button">Off</span>
 * </div>
 * 
 * In JQuery code blocks (i.e., $(document).ready), use this to set behavior:
 * 
 * $('.slider-button').toggle(function(){
 *  $(this).addClass('on').html('On');
 * },function(){
 *  $(this).removeClass('on').html('Off');
 * });
 */

// HAPPY WIDGET ABUSE!!