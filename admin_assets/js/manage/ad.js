var ads_table;
var status_val = $("#status_select").val();
var status_name = $("#status_select").find("option:selected").text();
 $(document).ready(function() {
 	var ads_TableButtons = function() {
           ads_table = $("#ads_table").DataTable({
             "oLanguage": {
				  	"sProcessing":   "جارٍ التحميل...",
					"sLengthMenu":   "أظهر _MENU_ مدخلات",
					"sZeroRecords":  "لم يعثر على أية سجلات",
					"sInfo":         "إظهار _START_ إلى _END_ من أصل _TOTAL_ مدخل",
					"sInfoEmpty":    "يعرض 0 إلى 0 من أصل 0 سجل",
					"sInfoFiltered": "(منتقاة من مجموع _MAX_ مُدخل)",
					"sInfoPostFix":  "",
					"sSearch":       "ابحث:",
					"sUrl":          "",
					"oPaginate": {
						"sFirst":    "الأول",
						"sPrevious": "السابق",
						"sNext":     "التالي",
						"sLast":     "الأخير"
				   }
			 },
             "bServerSide": false,
             aaSorting : [[0, 'desc']],
             "sAjaxSource": base_url + '/admin/ads_manage/all_ads/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_ad_details(\'' + full[0] + '\', \'' + full[8] + '\');" type="button" class="btn btn-primary" >View</button>';
		             }
		         } 
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
            });
        };
        ads_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              ads_TableButtons();
            }
          };
        }();

       ads_TableManageButtons.init();  
 	
 });


 // $('#status_select').change(function(event) {
  	 // status_val = $("#status_select").val();
     // ads_table.ajax.url(base_url + '/admin/ads_manage/all_ads/format/json?status='+status_val ).load();
     // console.log(ads_table.ajax.url());                                                                    
 // });
 
// filter by status
 $('#status_select').change(function(event) {
    status_val = $("#status_select").val();
    if(status_val == 0){
    	ads_table
		 .search( '' )
		 .columns().search( '' )
		 .draw();
    }else{
        status_name = $(this).find("option:selected").text();
    	ads_table.search( status_name ).draw();
    }
 });
 
 function show_ad_details (ad_id , tamplate_id) {
 	  console.log(ad_id);
 	  console.log(tamplate_id);
 	  var url =  base_url + '/api/ads_control/get_ad_details/format/json?ad_id='+ad_id+'&template_id='+tamplate_id;
      $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        success: function(response) {
            console.log(response.data);   
            $('.ads_details').modal('show');
        },error: function(xhr, status, error){
        	new PNotify({
                  title: 'الرجاء الانتباه!',
                  text: 'حدث خطأ ما أثناء إحضار تفاصيل الطلب، الرجاء تحديث الصفحة!',
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
          });
        }
      });
 }
 
  
  
  

        
  

