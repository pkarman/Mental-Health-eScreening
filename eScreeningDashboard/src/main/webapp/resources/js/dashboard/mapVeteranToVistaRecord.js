$(document).ready(function() {

    // Select Tab
    tabsLoad("createBattery");

    // JH - 508 Set the other page elements hide while modal show to help AT tools
    var modalBlock    = '.modal';
    var outerPageDiv  = '#outerPageDiv';
    
    $(modalBlock).on('shown.bs.modal', function (e) {
        $(outerPageDiv).attr('aria-hidden', 'true');
    });
    $(modalBlock).on('hidden.bs.modal', function (e) {
        $(outerPageDiv).attr('aria-hidden', 'false');
    });
});