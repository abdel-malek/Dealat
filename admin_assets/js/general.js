       $( window ).load(function() {
       	 $('.loader').fadeOut(800, function () {
            $('.dim-overlay').fadeOut(300, function () {
                // $("body").css("overflow", "visible");
            });
         });
        // Run code
       });
       
       
      $( document ).ajaxStart(function() {
        // console.log('show');
         $('.dim-overlay').css('display','block');
         $('.loader').css('display','block');
      });
      $( document ).ajaxStop(function() {
      	  //console.log('hide');
         $('.loader').fadeOut(500, function () {
            $('.dim-overlay').fadeOut(100, function () {
            });
         });
     });
 // <!-- Select2 -->

      $(document).ready(function() {
        $(".select2_single").select2({
          placeholder: "اختر",
          allowClear: true,
          width: 229
        });
        $(".select2_group").select2({});
        $(".select2_multiple").select2({
          maximumSelectionLength: 6,
          placeholder: "Select Multiple choices",
          allowClear: true,
        });
      });
 //   <!-- /Select2 -->
 
 
   //datepicker

    $(document).ready(function() {
    $('[data-toggle="datepicker"]').datepicker({
        format: 'yyyy-mm-dd',
        autoHide: true,
        zIndex: 2000
      });
    });

    
 //  <!-- bootstrap-daterangepicker -->
      // $(document).ready(function() {
        // var cb = function(start, end, label) {
          // console.log(start.toISOString(), end.toISOString(), label);
          // $('#reportrange_right span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        // };
// 
        // var optionSet1 = {
          // startDate: moment().subtract(29, 'days'),
          // endDate: moment(),
          // minDate: '09/01/2017',
          // maxDate: '10/30/2017',
          // dateLimit: {
            // days: 60
          // },
          // showDropdowns: true,
          // showWeekNumbers: true,
          // timePicker: false,
          // timePickerIncrement: 1,
          // timePicker12Hour: true,
          // ranges: {
            // 'Today': [moment(), moment()],
            // 'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            // 'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            // 'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            // 'This Month': [moment().startOf('month'), moment().endOf('month')],
            // 'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
          // },
          // opens: 'right',
          // buttonClasses: ['btn btn-default'],
          // applyClass: 'btn-small btn-primary',
          // cancelClass: 'btn-small',
          // format: 'MM/DD/YYYY',
          // separator: ' to ',
          // locale: {
            // applyLabel: 'Submit',
            // cancelLabel: 'Clear',
            // fromLabel: 'From',
            // toLabel: 'To',
            // customRangeLabel: 'Custom',
            // daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            // monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            // firstDay: 1
          // }
        // };
// 
        // $('#reportrange_right span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
// 
        // $('#reportrange_right').daterangepicker(optionSet1, cb);
// 
        // $('#reportrange_right').on('show.daterangepicker', function() {
          // console.log("show event fired");
        // });
        // $('#reportrange_right').on('hide.daterangepicker', function() {
          // console.log("hide event fired");
        // });
        // $('#reportrange_right').on('apply.daterangepicker', function(ev, picker) {
          // console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
        // });
        // $('#reportrange_right').on('cancel.daterangepicker', function(ev, picker) {
          // console.log("cancel event fired");
        // });
// 
        // $('#options1').click(function() {
          // $('#reportrange_right').data('daterangepicker').setOptions(optionSet1, cb);
        // });
// 
        // $('#options2').click(function() {
          // $('#reportrange_right').data('daterangepicker').setOptions(optionSet2, cb);
        // });
// 
        // $('#destroy').click(function() {
          // $('#reportrange_right').data('daterangepicker').remove();
        // });
// 
      // });
      // $(document).ready(function() {
        // var cb = function(start, end, label) {
          // console.log(start.toISOString(), end.toISOString(), label);
          // $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        // };
// 
        // var optionSet1 = {
          // startDate: moment().subtract(29, 'days'),
          // endDate: moment(),
          // minDate: '10/01/2017',
          // maxDate: '10/30/2017',
          // dateLimit: {
            // days: 60
          // },
          // showDropdowns: true,
          // showWeekNumbers: true,
          // timePicker: false,
          // timePickerIncrement: 1,
          // timePicker12Hour: true,
          // ranges: {
            // 'Today': [moment(), moment()],
            // 'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            // 'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            // 'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            // 'This Month': [moment().startOf('month'), moment().endOf('month')],
            // 'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
          // },
          // opens: 'left',
          // buttonClasses: ['btn btn-default'],
          // applyClass: 'btn-small btn-primary',
          // cancelClass: 'btn-small',
          // format: 'MM/DD/YYYY',
          // separator: ' to ',
          // locale: {
            // applyLabel: 'Submit',
            // cancelLabel: 'Clear',
            // fromLabel: 'From',
            // toLabel: 'To',
            // customRangeLabel: 'Custom',
            // daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            // monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            // firstDay: 1
          // }
        // };
        // $('#reportrange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
        // $('#reportrange').daterangepicker(optionSet1, cb);
        // $('#reportrange').on('show.daterangepicker', function() {
          // console.log("show event fired");
        // });
        // $('#reportrange').on('hide.daterangepicker', function() {
          // console.log("hide event fired");
        // });
        // $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
          // console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
        // });
        // $('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
          // console.log("cancel event fired");
        // });
        // $('#options1').click(function() {
          // $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
        // });
        // $('#options2').click(function() {
          // $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
        // });
        // $('#destroy').click(function() {
          // $('#reportrange').data('daterangepicker').remove();
        // });
      // });
      // $(document).ready(function() {
        // $('#single_cal1').daterangepicker({
          // singleDatePicker: true,
          // calender_style: "picker_1"
        // }, function(start, end, label) {
          // console.log(start.toISOString(), end.toISOString(), label);
        // });
        // $('#single_cal2').daterangepicker({
          // singleDatePicker: true,
          // calender_style: "picker_2"
        // }, function(start, end, label) {
          // console.log(start.toISOString(), end.toISOString(), label);
        // });
        // $('#single_cal3').daterangepicker({
          // singleDatePicker: true,
          // calender_style: "picker_3"
        // }, function(start, end, label) {
          // console.log(start.toISOString(), end.toISOString(), label);
        // });
        // $('#single_cal4').daterangepicker({
          // singleDatePicker: true,
          // calender_style: "picker_4"
        // }, function(start, end, label) {
          // console.log(start.toISOString(), end.toISOString(), label);
        // });
      // });
      // $(document).ready(function() {
        // $('#reservation').daterangepicker(null, function(start, end, label) {
          // console.log(start.toISOString(), end.toISOString(), label);
        // });
      // });
 
  //  <!-- /bootstrap-daterangepicker -->