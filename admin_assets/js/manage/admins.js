var admins_tabel;
var admins_buttons = [];
 $(document).ready(function() {
 	
 	 if($.inArray(EXPORT_ADMINS, permissions) != -1){
		  admins_buttons.push( 
		  	    {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Admins Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                }
	 	  );
 	  }
 	
 	var admins_TableButtons = function() {
           admins_tabel = $("#admins_table").DataTable({
            "oLanguage": {
				  	"sProcessing":   lang_array['sProcessing'],
					"sLengthMenu":   lang_array['sLengthMenu'],
					"sZeroRecords":  lang_array['sZeroRecords'],
					"sInfo":         lang_array['sInfo'],
					"sInfoEmpty":    lang_array['sInfoEmpty'],
					"sInfoFiltered": lang_array['sInfoFiltered'],
					"sInfoPostFix":  "",
					"sSearch":       lang_array['sSearch'],
					"sUrl":          "",
					"oPaginate": {
						"sFirst":    lang_array['sFirst'],
						"sPrevious": lang_array['sPrevious'],
						"sNext":     lang_array['sNext'],
						"sLast":     lang_array['sLast']
				   }
			 },
             "bServerSide": false,
             aaSorting : [[0, 'desc']],
             "sAjaxSource": base_url + '/admin/users_manage/get_admins/format/json',
             "columnDefs": [
		         {
                    "targets": -1, // permissions
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_permissions(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
		         {
                    "targets": -2, // edit
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_admin_info(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons:admins_buttons,
            });
        };
        admins_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              admins_TableButtons();
            }
          };
        }();

       admins_TableManageButtons.init();  
 });
 
 function show_admin_info (id) {
 	$('#admin_id_input').val(id);
 	if(id != 0){ // edit
 		$('#delete_admin').css('display' , 'inline');
 		$.ajax({
        url: base_url + '/admin/users_manage/get_admin_info/format/json?admin_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#admin_name').val(response.data['name']);
           $('#admin_username').val(response.data['username']);
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
            });
        }
      });
 	}
    $('.admin_manage_modal').modal('show');
 }
 
 
 $('.admin_manage_modal').on('hidden.bs.modal', function () {
        $('#admin_name').val('');
        $('#admin_username').val('');
        $('#admin_password').val('');
        $('#delete_admin').css('display' , 'none');
 });
 
 function save_admin () {
   var admin_id = $('#admin_id_input').val();
   var data = { 
   	 'admin_id' : admin_id, 
   	 'name' : $('#admin_name').val(),
   	 'username' : $('#admin_username').val(),
   };
   password = $('#admin_password').val();
   if(password != ''){
   	 data['password'] = password;
   }
   console.log(data);
    $.ajax({
        url: base_url + '/admin/users_manage/save_admin/format/json',
        type: "post",
        dataType: "json",
        data : data,
        success: function(response) {
             new PNotify({
	                  title:  lang_array['success'],
	                  text: lang_array['admin_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	          });
	        admins_tabel.ajax.url( base_url + '/admin/users_manage/get_admins/format/json').load();
	        $('.admin_manage_modal').modal('hide');
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
            });
        }
     });
 }
 
function delete_admin (id) {
 	var admin_id = $('#admin_id_input').val();
 	data = { 'admin_id' : admin_id};
    $.ajax({
        url: base_url + '/admin/users_manage/delete_admin/format/json',
        type: "post",
        dataType: "json",
        data : data,
        success: function(response) {
             new PNotify({
	                  title:  lang_array['success'],
	                  text: lang_array['admin_deleted'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	          });
	        admins_tabel.ajax.url( base_url + '/admin/users_manage/get_admins/format/json').load();
	        $('.admin_manage_modal').modal('hide');
	        $('.admin-delete-modal').modal('hide');
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
            });
        }
     });
 }
 
 function show_permissions (admin_id) {
 	$('#user_id_perm').val(admin_id);
 	$.ajax({
        url: base_url + '/admin/users_manage/get_admin_permissions/format/json?admin_id='+admin_id,
        type: "get",
        dataType: "json",
        success: function(response) {
          // console.log(response.data);
           $.each(response.data, function(index, permission) { 
               var  check = $('.permissions_modal').find('#'+permission);
               check.prop("checked", true);
               check.parent("div").addClass("checked");
          });
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
            });
        }
      });
    $('.permissions_modal').modal('show');
 }
 
 $('.permissions_modal').on('hidden.bs.modal', function () {
  	  $('.permission_check').prop("checked", false);
      $('.permission_check').parent("div").removeClass("checked");
});
 
 //save user permissions 
 $("#permissions_form").submit(function(event) {
   var data_string = $( "#permissions_form" ).serializeArray();
   var url =  base_url + '/admin/users_manage/save_admin_permissions/format/json';
     $.ajax(
           {
            url: url,
            type: "post",
            dataType: "json",
            data: data_string,
            success: function(response) {
              new PNotify({
	                  title:  lang_array['success'],
	                  text: '',
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	          });
              //admins_tabel.ajax.url( base_url + '/admin/users_manage/get_admins/format/json').load();
              window.location.reload(true);
             //  $(".permissions_modal").modal("hide");
            },error: function(xhr, status, error){
            	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
              });
              //admins_tabel.ajax.url( base_url + '/admin/users_manage/get_admins/format/json').load();
            }
          });
     event.preventDefault(); 
  });
 
 
 
 
 
 
 
