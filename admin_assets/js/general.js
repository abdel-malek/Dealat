    // loader
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

   
   // select
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

 
 
  
   var permissions;
   $(document).ready(function() {
   	  //datepicker
      $('[data-toggle="datepicker"]').datepicker({
        format: 'yyyy-mm-dd',
        autoHide: true,
        zIndex: 2000
      });
      
     //get user permissions
      var admin_id = $('#admin_id').val();
      $.ajax({
        url: base_url + '/admin/users_manage/get_admin_permissions/format/json?admin_id='+admin_id,
        type: "get",
        dataType: "json",
        async : false,
        success: function(response) {
        	permissions = response.data;
           // console.log(permissions);
        },error: function(xhr, status, error){
        }
     });
      
   });
  


  
  
  