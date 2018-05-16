var certificates_table;
 $(document).ready(function() {
 	var certificates_TableButtons = function() {
           certificates_table = $("#certificates_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/data_manage/get_all_certificates/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // edit
                    "data": null,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_certificate_manage_modal(\'' + full[0] + '\');"  type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: [
                 {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Certificates Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2]
                  }
                },
              ],
            });
        };
        certificates_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              certificates_TableButtons();
            }
          };
        }();

       certificates_TableManageButtons.init();  
 });
 
function show_certificate_manage_modal (id) {
  $('#certificate_id').val(id);
  if(id != 0){ 
  	  $('#certificate_delete_btn').css('display' , 'inline');
	  $.ajax({
        url: base_url + '/api/data_control/get_certificate_info/format/json?certificate_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#certificate_en_name').val(response.data['en_name']);
           $('#certificate_ar_name').val(response.data['ar_name']);
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
   $('.certificate_manage_modal').modal('show');
 }
 
$('.certificate_manage_modal').on('hidden.bs.modal', function () {
  	    $('#certificate_en_name').val('');
        $('#certificate_ar_name').val('');
        $('#certificate_delete_btn').css('display' , 'none');
});

function save_certificate () {
  var id = $('#certificate_id').val();
  var en_name = $('#certificate_en_name').val();
  var ar_name = $('#certificate_ar_name').val();
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
			'certificate_id' : id
		};
	    $.ajax({
	        url: base_url + '/admin/data_manage/save_certificate/format/json',
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
	                  text: lang_array['certificate_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 certificates_table.ajax.url( base_url + '/admin/data_manage/get_all_certificates/format/json').load();
			         $('.certificate_manage_modal').modal('hide');
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

function delete_certificate() {
  var id = $('#certificate_id').val();
   data = {'certificate_id' : id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_certificate/format/json',
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
                  text: lang_array['certificate_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                certificates_table.ajax.url( base_url + '/admin/data_manage/get_all_certificates/format/json').load();
			    $('.certificate_manage_modal').modal('hide');
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