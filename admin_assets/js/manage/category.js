 var sub_sub_cats_tabel;
 $(document).ready(function() {
 	var sub_sub_cats_TableButtons = function() {
           sub_sub_cats_tabel = $(".sub_sub_cats_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/categories_manage/get_sub_cats/format/json?category_id='+ 25,
             "columnDefs": [
                 {
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_edit_cat_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		            }
		         },
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
              initComplete: function(nRow, settings, json){
	          	 activated_number =0;
	           },
            });
        };
        sub_sub_cats_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              sub_sub_cats_TableButtons();
            }
          };
        }();
       sub_sub_cats_TableManageButtons.init();
	       
   }); 
   
function show_manage_cat_modal (id , is_main) {
   if(is_main){ // manage main cat
   	  if(id == 0){ //add
   	  	$('.choose_tamplate_div').css('display' , 'inline'); 
   	  }else{ // edit
   	  	 	$.ajax({
	        url: base_url + '/api/categories_control/get_info/format/json?category_id='+id,
	        type: "get",
	        dataType: "json",
	        success: function(response) {
	            $('#cat_arabic_name').val(response.data['ar_name']);
	            $('#cat_english_name').val(response.data['en_name']);
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
   }else{ // manage sub cat
   	  if(id == 0){ //add
   	  	 
   	  }else{ // edit
   	  	  $.ajax({
	        url: base_url + '/api/categories_control/get_info/format/json?category_id='+id,
	        type: "get",
	        dataType: "json",
	        success: function(response) {
	            $('#cat_arabic_name').val(response.data['ar_name']);
	            $('#cat_english_name').val(response.data['en_name']);
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
   $('.manage_cat').modal('show');
}

 $('.manage_cat').on('hidden.bs.modal', function () {
	  $('#cat_arabic_name').val('');
	  $('#cat_english_name').val('');
	  $('.choose_tamplate_div').css('display' , 'none'); 
 });
 
function save_category(id){
	
}
  
  
  