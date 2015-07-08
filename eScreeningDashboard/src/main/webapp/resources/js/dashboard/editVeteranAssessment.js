$(document).ready(function () {
    $(function () {
        $('#selectedProgramId').change(function () {
            $.ajax({
                url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/clinics',
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    $('#selectedClinicId').empty(); // clear the current elements in select box
                    $('#selectedClinicId').append($('<option></option>').attr('value', '').text('Please Select a Clinic'));
                    for (row in data) {
                        $('#selectedClinicId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    //alert(errorThrown);
                }
            });

            $.ajax({
                url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/noteTitles',
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    $('#selectedNoteTitleId').empty();
                    $('#selectedNoteTitleId').append($('<option></option>').attr('value', '').text('Please Select a Note Title'));
                    for (row in data) {
                        $('#selectedNoteTitleId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    //alert(errorThrown);
                }
            });

            $.ajax({
                url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/clinicians',
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    $('#selectedClinicianId').empty();
                    $('#selectedClinicianId').append($('<option></option>').attr('value', '').text('Please Select a Clinician'));
                    for (row in data) {
                        $('#selectedClinicianId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    //alert(errorThrown);
                }
            });

            // Filter
            $(".program_1").addClass("hide2");
        });
    });


    $(function () {
        $('#selectedClinicianId').change(function () {
            if ($('#selectedClinicianId').val() != '') {
                // make an ajax call to editVeteranAssessment which will return surveys and also clinical reminders for the selected clinician id
                $.ajax({
                    url: 'editVeteranAssessment',
                    type: 'GET',
                    data: 'vid='+ $('#veteranId').val() + '&clinicianId=' + $('#selectedClinicianId').val() + '&programId=' + $('#selectedProgramId').val() + '&clinicId=' + $('#selectedClinicId').val() + '&noteTitleId=' + $('#selectedNoteTitleId').val(),
                    dataType: 'html',
                    async:true,
                    success: function (data) {
                        //console.log(data);
                        $('#output').html(data);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert(errorThrown);
                    }
                });
            } else {
                $('#output').html('');
            }
        });
    });
})