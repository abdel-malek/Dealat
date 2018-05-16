var periods_table;
var period_buttons =[];
 $(document).ready(function() {
 	
 	if($.inArray(EXPORT_DATA, permissions) != -1){
		  period_buttons.push( 
		  	    {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Show period Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2,3]
                  }
                }
		 );
 	}
 	
 	var periods_TableButtons = function() {
           periods_table = $("#periods_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/data_manage/get_show_periods/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // edit
                    "data": null,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_periods_manage_modal(\'' + full[0] + '\');"  type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: period_buttons,
            });
        };
        periods_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              periods_TableButtons();
            }
          };
        }();

       periods_TableManageButtons.init();  
 });
 

 
function show_periods_manage_modal (id) {
  $('#period_id').val(id);
  if(id != 0){ 
  	  $('#period_delete_btn').css('display' , 'inline');
	  $.ajax({
        url: base_url + '/api/data_control/get_period_info/format/json?period_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#period_en_name').val(response.data['en_name']);
           $('#period_ar_name').val(response.data['ar_name']);
           $('#period_days').val(response.data['days']);
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
   $('.manage_period_modal').modal('show');
}

$('.manage_period_modal').on('hidden.bs.modal', function () {
  	    $('#period_en_name').val('');
        $('#period_ar_name').val('');
        $('#period_days').val('');
        $('#period_delete_btn').css('display' , 'none');
});
 
function save_period () {
  var id = $('#period_id').val();
  var en_name = $('#period_en_name').val();
  var ar_name = $('#period_ar_name').val();
  var days = $('#period_days').val();
	if(en_name =='' || ar_name == '' || days == ''){
		new PNotify({
              title: lang_array['attention'],
              text: lang_array['names_days_validation'],
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
			'period_id' : id,
			'days' : days
		};
	    $.ajax({
	        url: base_url + '/admin/data_manage/save_period/format/json',
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
	                  text: lang_array['period_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 periods_table.ajax.url( base_url + '/admin/data_manage/get_show_periods/format/json').load();
			         $('.manage_period_modal').modal('hide');
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
// 
function delete_period() {
  var id = $('#period_id').val();
   data = {'period_id' : id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_period/format/json',
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
                  text: lang_array['period_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                periods_table.ajax.url( base_url + '/admin/data_manage/get_show_periods/format/json').load();
			    $('.manage_period_modal').modal('hide');
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