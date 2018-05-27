var schedules_table;
var sche_buttons = [];
 $(document).ready(function() {
 	
 	if($.inArray(EXPORT_DATA, permissions) != -1){
		  sche_buttons.push( 
		  	    {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Schedules Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2]
                  }
                }
		 );
 	}
 	
 	var schedules_TableButtons = function() {
           schedules_table = $("#schedules_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/data_manage/get_all_schedules/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // edit
                    "data": null,
                    "visible" : can_show_edit_modal,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_schedule_manage_modal(\'' + full[0] + '\');"  type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: sche_buttons,
            });
        };
        schedules_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              schedules_TableButtons();
            }
          };
        }();

      schedules_TableManageButtons.init();  
 });
 

 
function show_schedule_manage_modal (id) {
  $('#schedule_id').val(id);
  show_delete_data_btns(id);
  show_save_edits_data_btns(id);
  if(id != 0){ 
  	 // $('#schedule_delete_btn').css('display' , 'inline');
	  $.ajax({
        url: base_url + '/api/data_control/get_schedule_info/format/json?schedule_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#schedule_en_name').val(response.data['en_name']);
           $('#schedule_ar_name').val(response.data['ar_name']);
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
   $('.schedules_manage_modal').modal('show');
 }
 
$('.schedules_manage_modal').on('hidden.bs.modal', function () {
  	    $('#schedule_en_name').val('');
        $('#schedule_ar_name').val('');
        $('.data_delete_btn').css('display' , 'inline');
        $('.data_update_btn').css('display' , 'inline');
       // $('#schedule_delete_btn').css('display' , 'none');
});
 
function save_schedule() {
  var id = $('#schedule_id').val();
  var en_name = $('#schedule_en_name').val();
  var ar_name = $('#schedule_ar_name').val();
	if(en_name =='' || ar_name == ''){
		new PNotify({
              title: lang_array['attention'],
              text: lang_array['names_validation'],
              type: 'error',
              styling: 'bootstrap3',
              buttons: {
			        sticker: false
			}
        });
	}else{
		var data  ={
			'en_name' : en_name , 
			'ar_name' : ar_name , 
			'schedule_id' : id
		};
	    $.ajax({
	        url: base_url + '/admin/data_manage/save_schedule/format/json',
	        type: "post",
	        dataType: "json",
	        data: data,
	        success: function(response) {
	            if(response.status == false){
	           	  new PNotify({
		                  title: lang_array['attention'],
		                  text: response.message,
		                  type: 'error',
		                  styling: 'bootstrap3',
		                  buttons: {
						        sticker: false
						}
		          });
	            }else{
	                new PNotify({
	                  title:  lang_array['success'],
	                  text: lang_array['schedule_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 schedules_table.ajax.url( base_url + '/admin/data_manage/get_all_schedules/format/json').load();
			         $('.schedules_manage_modal').modal('hide');
	             }
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
}

function delete_schedule() {
  var id = $('#schedule_id').val();
   data = {'schedule_id' : id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_schedule/format/json',
        type: "post",
        dataType: "json",
        data: data,
        success: function(response) {
            if(response.status == false){
           	  new PNotify({
	                  title: lang_array['attention'],
	                  text: response.message,
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	          });
            }else{
                new PNotify({
                  title:  lang_array['success'],
                  text: lang_array['schedule_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                 schedules_table.ajax.url( base_url + '/admin/data_manage/get_all_schedules/format/json').load();
			     $('.schedules_manage_modal').modal('hide');
             }
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