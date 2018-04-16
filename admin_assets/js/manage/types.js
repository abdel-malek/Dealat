var types_table;
var type_models_table;
var template = $('#filter_type_template_select').val();
 $(document).ready(function() {

 	var types_TableButtons = function() {
           types_table = $("#types_table").DataTable({
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
             aaSorting : [[3, 'desc']],
             "sAjaxSource": base_url + '/admin/data_manage/get_all_types/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // models
                    "data": null,
                 //   "visible" : false,
                    "mRender": function(date, type, full) {
                     //  if(full[3] == 'vehicles' || full[3] == 'مركبات' ){
                       	 return '<button id="" onclick="show_models_modal(\'' + full[0] + '\');" type_id=\'' + full[0] + '\' type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
                    //   }else{
                    //   	   return '';
                    //   }
                      
		             }
		         },
		         {
                    "targets": -2, // details
                    "data": null,
                 //   "visible" : false,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_manage_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         } 
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
            });
        };
        types_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              types_TableButtons();
            }
          };
        }();

       types_TableManageButtons.init();  
       
       
     // filter by template
	 $('#filter_type_template_select').change(function(event) {
	    template = $("#filter_type_template_select").val();
	    if(template == 0){
	      //  types_table.column(4).visible(false);
	    	types_table
			 .search( '' )
			 .columns().search( '' )
			 .draw();
	    }else{
	    	// if(template == 1){ // vehicles
	    	    // types_table.column(4).visible(true);	
	    	// }else{
	    	    // types_table.column(4).visible(false);	
	    	// }
	        template_name = $(this).find("option:selected").text();
	    	types_table.search( template_name ).draw();
	    }
     });
       
 });
 
 
function show_models_modal (type_id) {
	$('#model_type_id').val(type_id);
    var type_models_TableButtons = function() {
     if ($("#models_table")) {
      type_models_table = $("#models_table").DataTable({
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
         "sAjaxSource": base_url + '/admin/data_manage/get_types_models/format/json?type_id='+type_id,
         "columnDefs": [
             {
                "targets": -1, // models
                "data": null,
             //   "visible" : false,
                "mRender": function(date, type, full) {
                    return '<button id="" onclick="show_manage_models_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
                  
	             }
	         },
          ],
          dom: "Bfrtip",
          buttons: [
          ],
        });
     } 
    };
    type_models_TableManageButtons = function() {
      "use strict";
      return {
        init: function() {
          type_models_TableButtons();
        }
      };
    }();

    type_models_TableManageButtons.init();  
    $('.models_modal').modal('show');
 }
 
 
 $('.models_modal').on('hidden.bs.modal', function () {
  	 type_models_table.destroy();
 });
  
function show_manage_modal (type_id) {
   $('#type_id').val(type_id);
   if(type_id == 0){ // add
   	   $('#template_select_div').css('display' , 'inline');
   	   $('#type_delete_btn').css('display' , 'none');
   }else{ // edit
       $.ajax({
        url: base_url + '/api/data_control/get_type_info/format/json?type_id='+type_id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#type_template_label').html(response.data['template_name']);
           $('#template_label_div').css('display' , 'inline');     
           $('#type_en_name').val(response.data['en_name']);
           $('#type_ar_name').val(response.data['ar_name']);
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
	
   $('.types_manage_modal').modal('show');
}

 $('.types_manage_modal').on('hidden.bs.modal', function () {
	   $('#template_label_div').css('display' , 'none');
	   $('#template_select_div').css('display' , 'none');
	   $('#type_delete_btn').css('display' , 'inline');
	   $('#type_en_name').val('');
       $('#type_ar_name').val('');
 });
 

function save_type () {
   var type_id = $('#type_id').val();
   var en_name = $('#type_en_name').val();
   var ar_name = $('#type_ar_name').val();
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
	   var data = {
       'en_name' : $('#type_en_name').val(),
       'ar_name' : $('#type_ar_name').val(),
      };
	   var url;
	   if(type_id == 0){ // add
	   	   console.log($('#type_template_select').val());
	       data['tamplate_id'] = $('#type_template_select').val();
	       url = base_url + '/admin/data_manage/add_type/format/json';
	   }else{
	   	  data['type_id'] = type_id;
	      url = base_url + '/admin/data_manage/edit_type/format/json';
	   }
	  // console.log(data);
	    $.ajax({
	        url: url,
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
	                  text: lang_array['type_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 types_table.ajax.url( base_url + '/admin/data_manage/get_all_types/format/json').load();
			         $('.types_manage_modal').modal('hide');
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

function delete_type () {
   var type_id = $('#type_id').val();
   data = {'type_id' : type_id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_type/format/json',
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
                  text: lang_array['type_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                 types_table.ajax.url( base_url + '/admin/data_manage/get_all_types/format/json').load();
		         $('.types_manage_modal').modal('hide');
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


function show_manage_models_modal (type_model_id) {
	$('#type_model_id').val(type_model_id);
	var type_id = $('#model_type_id').val();
	if(type_model_id != 0){ // edit
	  $('#type_model_delete_btn').css('display' , 'inline');
	  $.ajax({
        url: base_url + '/api/data_control/get_type_model_info/format/json?type_model_id='+type_model_id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#type_model_en_name').val(response.data['en_name']);
           $('#type_model_ar_name').val(response.data['ar_name']);
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
	$('.type_model_manage_modal').modal('show');
	//console.log(type_id);
}

$('.type_model_manage_modal').on('hidden.bs.modal', function () {
	 $('#type_model_en_name').val('');
     $('#type_model_ar_name').val('');
     $("body").addClass("modal-open");
 });
 

function save_type_model(){
	var type_model_id = $('#type_model_id').val();
	var type_id = $('#model_type_id').val();
	var en_name = $('#type_model_en_name').val();
	var ar_name = $('#type_model_ar_name').val();
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
	   var data = {
	   	'en_name' : en_name , 
	   	'ar_name' : ar_name , 
	   	'type_id' : type_id , 
	   	'type_model_id' : type_model_id
	   };
  	    $.ajax({
        url: base_url + '/admin/data_manage/save_type_model/format/json',
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
                  text: lang_array['model_saved'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                 type_models_table.ajax.url(base_url + '/admin/data_manage/get_types_models/format/json?type_id='+type_id).load();
		         $('.type_model_manage_modal').modal('hide');
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


function delete_type_model() {
  var id = $('#type_model_id').val();
  var type_id = $('#model_type_id').val();
   data = {'type_model_id' : id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_type_model/format/json',
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
                  text: lang_array['model_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
               type_models_table.ajax.url(base_url + '/admin/data_manage/get_types_models/format/json?type_id='+type_id).load();
		       $('.type_model_manage_modal').modal('hide');
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
