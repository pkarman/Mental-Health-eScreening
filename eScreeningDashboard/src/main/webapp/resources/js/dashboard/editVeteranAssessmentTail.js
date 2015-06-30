//$(document).ready(function () {

    var module_values = [];
    var reset_check = false;

    $(".module_list").find(':checked').each(function () {
        module_values.push($(this).val());
    });

    $(".clear_all").on("click", function (e) {
        e.preventDefault();
        clearAllSelectins();
    });

    $(".clear_all_modules").on("click", function (e) {
        e.preventDefault();
        clearAllModulesSelectins();
    });

    $(".reset").on("click", function (e) {
        e.preventDefault();
        $('input:checkbox').removeAttr('checked');
        $("tr").removeClass("highlight");
        for (var i in module_values) {
            $("input:checkbox[value=" + module_values[i] + "]").prop('checked', true);
        }
        reset_check = false;
    });

    $(".battery_list li span").on("click", function (e) {
        e.preventDefault();
        if (this.className.indexOf("highlight") > -1) {
            $("tr").removeClass("highlight");
            var classes = $(this).attr('class').split(' ');

            for (var i = 0; i < classes.length; i++) {
                $("." + classes[i]).closest("tr").addClass("highlight");
            }
        } else {
            //$('input:checkbox').removeAttr('checked');
            $("." + $(this).attr('class')).prop('checked', true);
            $("tr").removeClass("highlight");
            var classes = $(this).attr('class').split(' ');

            for (var i = 0; i < classes.length; i++) {
                $("." + classes[i]).closest("tr").addClass("highlight");
            }
        }
    });


    $(".battery_list input").on("click", function (e) {
        $(".module_list tr input").prop('checked', false);

        if (reset_check == false) {
            for (var i in module_values) {
                $("input:checkbox[value=" + module_values[i] + "]").prop('checked', true);
            }
        }

        $(".module_list  ." + $(this).attr('class')).prop('checked', true);
        $(".module_list tr").removeClass("highlight");

        var classes = $(this).attr('class').split(' ');


        for (var i = 0; i < classes.length; i++) {
            $(".module_list ." + classes[i]).closest("tr").addClass("highlight");
        }
    });


    var selectedProgramId = $("#selectedProgramId").val();
    $li.hide().filter(".program_" + selectedProgramId).show();
    if ((selectedProgramId == "") || (typeof selectedProgramId == "undefined" )) {
        $li.show();
    }
//})