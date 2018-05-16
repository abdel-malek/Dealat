var education_table;
var edu_buttons =[];
 $(document).ready(function() {
 	
 	if($.inArray(EXPORT_DATA, permissions) != -1){
		  edu_buttons.push( 
		  	    {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Educations Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2]
                  }
                }
		 );
 	}
 	
 	var educations_TableButtons = function() {
           education_table = $("#educations_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/data_manage/get_all_educations/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // edit
                    "data": null,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_education_manage_modal(\'' + full[0] + '\');"  type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: edu_buttons,
            });
        };
        educations_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              educations_TableButtons();
            }
          };
        }();

       educations_TableManageButtons.init();  
 });
 

 
function show_education_manage_modal (id) {
  $('#education_id').val(id);
  if(id != 0){ 
  	  $('#education_delete_btn').css('display' , 'inline');
	  $.ajax({
        url: base_url + '/api/data_control/get_education_info/format/json?education_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#education_en_name').val(response.data['en_name']);
           $('#education_ar_name').val(response.data['ar_name']);
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
   $('.education_manage_modal').modal('show');
}

$('.education_manage_modal').on('hidden.bs.modal', function () {
  	    $('#education_en_name').val('');
        $('#education_ar_name').val('');
        $('#education_delete_btn').css('display' , 'none');
});
 
function save_education () {
  var id = $('#education_id').val();
  var en_name = $('#education_en_name').val();
  var ar_name = $('#education_ar_name').val();
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
			'education_id' : id
		};
	    $.ajax({
	        url: base_url + '/admin/data_manage/save_education/format/json',
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
	                  text: lang_array['education_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 education_table.ajax.url( base_url + '/admin/data_manage/get_all_educations/format/json').load();
			         $('.education_manage_modal').modal('hide');
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

function delete_education() {
  var id = $('#education_id').val();
   data = {'education_id' : id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_education/format/json',
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
                  text: lang_array['education_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                education_table.ajax.url( base_url + '/admin/data_manage/get_all_educations/format/json').load();
			    $('.education_manage_modal').modal('hide');
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