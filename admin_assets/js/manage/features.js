var features_table;
var areas_table;
var feature_buttons = [];
 $(document).ready(function() {

 	if($.inArray(EXPORT_DATA, permissions) != -1){
		  feature_buttons.push(
		  	    {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Features Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2]
                  }
                }
		 );
 	}

 	var features_TableButtons = function() {
           features_table = $("#features_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/data_manage/get_all_features/format/json',
             "columnDefs": [
             //     {
             //        "targets": -1, // locations
             //        "data": null,
             //        "visible" : can_show_edit_modal,
             //        "mRender": function(date, type, full) {
             //           	 return '<button id="" onclick="show_features_modal(\'' + full[0] + '\');"  type="button" class="btn btn-primary" >'+lang_array['view']+'</li></button>';
		         //     }
		         // },
		         {
                    "targets": -1, // edit
                    "data": null,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_features_manage_modal(\'' + full[0] + '\');"  type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: feature_buttons,
            });
        };
        features_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              features_TableButtons();
            }
          };
        }();

      features_TableManageButtons.init();
 });

function show_features_manage_modal (feature_id) {
  $('#manage_feature_id').val(feature_id);
 // $('#city_delete_btn').css('display' , 'none');
  $('#feature_id').val(feature_id);
  show_delete_data_btns(feature_id);
  show_save_edits_data_btns(feature_id);
  if(feature_id != 0){ // edit
  	  //$('#city_delete_btn').css('display' , 'inline');
  	  $.ajax({
        url: base_url + '/api/data_control/get_feature_info/format/json?feature_id='+feature_id,
        type: "get",
        dataType: "json",
        success: function(response) {
           $('#feature_en_name').val(response.data['name_en']);
           $('#feature_ar_name').val(response.data['name_ar']);
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
   $('.features_manage_modal').modal('show');
}

 $('.features_manage_modal').on('hidden.bs.modal', function () {
  	    $('#city_en_name').val('');
        $('#city_ar_name').val('');
        $('.data_delete_btn').css('display' , 'inline');
        $('.data_update_btn').css('display' , 'inline');
 });


function save_feature () {
   var feature_id = $('#manage_feature_id').val();
   var en_name = $('#feature_en_name').val();
   var ar_name = $('#feature_ar_name').val();

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
       'en_name' : $('#feature_en_name').val(),
       'ar_name' : $('#feature_ar_name').val(),
       'feature_id' : feature_id
      };
	  console.log(data);
	    $.ajax({
	        url:  base_url + '/admin/data_manage/save_features/format/json',
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
	                  text: lang_array['city_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 features_table.ajax.url(base_url + '/admin/data_manage/get_all_features/format/json').load();
			         $('.features_manage_modal').modal('hide');
	             }
	        },error: function(xhr, status, error){
              console.log(xhr);
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


function delete_city () {
   var feature_id =  $('#manage_feature_id').val();
   data = {'feature_id' : feature_id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_city/format/json',
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
                  text: lang_array['city_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                 features_table.ajax.url(base_url + '/admin/data_manage/get_all_features/format/json').load();
			     $('.features_manage_modal').modal('hide');
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


function show_locations_modal (feature_id) {
	$('#feature_id').val(feature_id);
    var areas_TableButtons = function() {
     if ($("#areas_table")) {
      areas_table = $("#areas_table").DataTable({
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
         "sAjaxSource": base_url + '/admin/data_manage/get_city_areas/format/json?feature_id='+feature_id,
         "columnDefs": [
             {
                "targets": -1,
                "data": null,
                "visible" : can_show_edit_modal,
                "mRender": function(date, type, full) {
                    return '<button id="" onclick="show_manage_areas_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';

	             }
	         },
          ],
          dom: "Bfrtip",
          buttons: [
          ],
        });
     }
    };
    areas_TableManageButtons = function() {
      "use strict";
      return {
        init: function() {
          areas_TableButtons();
        }
      };
    }();

    areas_TableManageButtons.init();
    $('.locations_modal').modal('show');

}

 $('.locations_modal').on('hidden.bs.modal', function () {
  	 areas_table.destroy();
 });


 function show_manage_areas_modal (location_id) {
 	$('#location_id').val(location_id);
 	//$('#location_delete_btn').css('display' , 'none');
 	 show_delete_data_btns(location_id);
     show_save_edits_data_btns(location_id);
     if(location_id != 0){ // edit
     	  //$('#location_delete_btn').css('display' , 'inline');
	  	  $.ajax({
	        url: base_url + '/api/data_control/get_location_info/format/json?location_id='+location_id,
	        type: "get",
	        dataType: "json",
	        success: function(response) {
	           $('#location_en_name').val(response.data['en_name']);
	           $('#location_ar_name').val(response.data['ar_name']);
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
    $('.locations_manage_modal').modal('show');
 }

 $('.locations_manage_modal').on('hidden.bs.modal', function () {
  	    $('#location_en_name').val('');
	    $('#location_ar_name').val('');
	    $('.data_delete_btn').css('display' , 'inline');
        $('.data_update_btn').css('display' , 'inline');
	    $("body").addClass("modal-open");
 });

 function save_location () {
   var feature_id = $('#feature_id').val();
   var en_name = $('#location_en_name').val();
   var ar_name = $('#location_ar_name').val();
   var location_id = $('#location_id').val();
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
       'en_name' : $('#location_en_name').val(),
       'ar_name' : $('#location_ar_name').val(),
       'feature_id' : feature_id,
       'location_id' : location_id
      };
	  // console.log(data);
	    $.ajax({
	        url:  base_url + '/admin/data_manage/save_location/format/json',
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
	                  text: lang_array['location_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 areas_table.ajax.url(base_url + '/admin/data_manage/get_city_areas/format/json?feature_id='+feature_id).load();
			         $('.locations_manage_modal').modal('hide');
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

function delete_location () {
   var location_id =  $('#location_id').val();
   var feature_id = $('#feature_id').val();
   data = {'location_id' : location_id};
    $.ajax({
        url:  base_url + '/admin/data_manage/delete_location/format/json',
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
                  text: lang_array['location_deleted'],
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                areas_table.ajax.url(base_url + '/admin/data_manage/get_city_areas/format/json?feature_id='+feature_id).load();
			    $('.locations_manage_modal').modal('hide');
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
