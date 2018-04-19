        <!-- footer content -->
        <footer>
          <div class="pull-right">
            <!-- Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a> -->
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>
    <?php $this->load->view('admin/ads/details_modal') ?>
    <?php $this->load->view('admin/commercial_ads/details_modal') ?>
    <!-- jQuery -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/jquery/dist/jquery.min.js"></script>
    <!-- <script src="<?php echo base_url() ?>Files/vendors/jquery/jquery_new.js"></script> -->
    <!-- Bootstrap -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    
    <!-- FastClick -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/nprogress/nprogress.js"></script>
    
    <!-- bootstrap confirmation -->
    <script src="<?php echo base_url() ?>admin_assets/js/bootstrap-confirmation.js"></script>
    <!-- FastClick -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/nprogress/nprogress.js"></script>
     <!-- iCheck -->
     <script src="<?php echo base_url() ?>admin_assets/vendors/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/jszip/dist/jszip.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatable_fixedColumns/dataTables.fixedColumns.min.js"></script>
    
    
     <!-- Switchery -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/switchery/dist/switchery.min.js"></script>
     <!-- Select2 -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/select2/dist/js/select2.full.min.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="<?php echo base_url() ?>admin_assets/build/js/custom.min.js"></script>
    <!-- Datatables -->
    <script src="<?php echo base_url() ?>admin_assets/build/js/moment.min.js"></script>
    <!-- /Datatables -->
    
    
    <!-- bootstrap-daterangepicker -->
    <!-- <script src="<?php echo base_url() ?>Files/js/datepicker/daterangepicker.js"></script>    -->
    
    <!-- echarts -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/echarts/dist/echarts.min.js"></script>
    
   <!-- PNotify -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.buttons.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.nonblock.js"></script>
    
    <!-- datepicker -->
    <!-- <link href="<?php echo base_url() ?>Files/datepicker/datepicker.min.js" rel="stylesheet"> -->
    <script src="<?php echo base_url() ?>admin_assets/datepicker/datepicker.min.js"></script>
    
    <!-- vars --> 

   <!--  slick slider  -->
   <script src="<?php echo base_url('assets/js/slick.min.js'); ?>"></script>
   
    <!-- jquery uploader -->
    <script src="<?php echo base_url('assets/js/jquery.uploadfile.min.js'); ?>"></script>
    
    <script>
       var lang_array=[];
       if(lang == 'en'){
       	 lang_array['attention'] = 'Oh no..' ;
       	 lang_array['something_wrong'] = 'Some thing went wrong , please refresh.';
       	 lang_array['view'] = 'View' ;
       	 lang_array['success'] = 'Success' ;
       	 lang_array['ad_accepted'] = 'Ad is Accepted' ;
       	 lang_array['ad_rejected'] = 'Ad is Rejected' ;
       	 lang_array['ad_hidden'] = 'Ad is Hidden' ;
       	 lang_array['ad_show'] = 'Ad is Shown' ;
       	 lang_array['ad_deleted'] = 'Ad is Deleted' ;
       	 lang_array['ad_saved'] = 'Ad is saved' ;
       	 lang_array['type_saved'] = 'Brand is saved' ;
       	 lang_array['type_deleted'] = 'Brand is Deleted' ;
         lang_array['model_saved'] = 'Model is saved' ;
         lang_array['model_deleted'] = 'Model is Deleted' ;
         lang_array['schedule_saved'] = 'Work Schedule is saved' ;
       	 lang_array['schedule_deleted'] = 'Work Schedule is deleted' ;
       	 lang_array['show_status_changed'] = 'Ad show status has been changed' ;
       	 lang_array['names_validation'] = 'English and arabic names ara requierd.' ;
       	 lang_array['note'] = 'Please Note' ;
       	 lang_array['new_pending'] = 'A new pending ad is added' ;
       	 lang_array['education_saved'] = 'Education is saved';
       	 lang_array['education_deleted'] = 'Education is Deleted';
       	 lang_array['user_status_changed'] = 'User activation status has been changed';
       	 lang_array['city_saved'] = 'City is saved' ;
       	 lang_array['city_deleted'] = 'City is Deleted' ;
       	 lang_array['location_saved'] = 'Area is saved' ;
       	 lang_array['location_deleted'] = 'Area is Deleted' ;
       	 lang_array['notification_sent'] = 'Notification sent successfully'
       	 //datatable
       	 lang_array['sProcessing'] = 'Processing...' ;
       	 lang_array['sLengthMenu'] = 'Show _MENU_ entries' ;
       	 lang_array['sZeroRecords'] = 'No matching records found' ;
       	 lang_array['sInfo'] = 'Showing _START_ to _END_ of _TOTAL_ entries' ;
       	 lang_array['sInfoEmpty'] = 'Showing 0 to 0 of 0 entries' ;
       	 lang_array['sInfoFiltered'] = '(filtered from _MAX_ total entries)' ;
       	 lang_array['sSearch'] = 'Search:' ;
       	 lang_array['sFirst'] = 'First' ;
       	 lang_array['sPrevious'] = 'Last' ;
       	 lang_array['sNext'] = "Next" ;
       	 lang_array['sLast'] = 'Previous' ;
       }else if (lang == 'ar'){
       	 lang_array['attention'] = 'الرجاء الانتباه' ;
       	 lang_array['something_wrong'] = 'حدث خطأ ما، الرجاء تحديث الصفحة';
       	 lang_array['view'] = 'عرض' ;
       	 lang_array['success'] = 'نجاح' ;
       	 lang_array['ad_accepted'] = 'تم قبول الإعلان ' ;
       	 lang_array['ad_rejected'] = 'تم رفض الإعلان' ;
       	 lang_array['ad_hidden'] = 'تم إخفاء الإعلان' ;
       	 lang_array['ad_show'] = 'تم إظهار الإعلان' ;
       	 lang_array['ad_deleted'] = 'تم حذف الإعلان' ;
       	 lang_array['ad_saved'] = 'تم حفظ الإعلان' ;
       	 lang_array['type_saved'] = 'تم حفظ الماركة' ;
       	 lang_array['model_saved'] = 'تم حفظ الموديل' ;
       	 lang_array['model_deleted'] = 'تم حذف الموديل' ;
       	 lang_array['type_deleted'] = 'تم حذف الماركة' ;
       	 lang_array['schedule_saved'] = 'تم حفظ دوام العمل' ;
       	 lang_array['schedule_deleted'] = 'تم حذف دوام العمل' ;
         lang_array['show_status_changed'] = 'تم تغيير حالة ظهور الإعلان بنجاح' ;
       	 lang_array['names_validation'] = 'يجب إدخال الاسم العربي والاسم الانغليزي' ;
       	 lang_array['note'] = 'ملاحظة' ;
       	 lang_array['new_pending'] = "يوجد لديك إعلان قيد الانتظار جديد" ;
       	 lang_array['education_saved'] = 'تم حفظ المستوى التعليمي ';
       	 lang_array['education_deleted'] = 'تم حذف المستوى التعليمي ';
       	 lang_array['user_status_changed'] = 'تم تغيير حالة فعالية المستخدم';
          lang_array['city_saved'] = 'تم حفظ المحافظة' ;
       	 lang_array['city_deleted'] = 'تم حذف المحافظة' ;
       	 lang_array['location_saved'] = 'تم حفظ المنطقة' ;
       	 lang_array['location_deleted'] = 'تم حذف المنطقة'
       	 lang_array['notification_sent'] = 'تم إرسال الإشعار بنجاح'
       	 //datatable
       	 lang_array['sProcessing'] = "جارٍ التحميل..." ;
       	 lang_array['sLengthMenu'] = "أظهر _MENU_ مدخلات" ;
       	 lang_array['sZeroRecords'] = "لم يعثر على أية سجلات" ;
       	 lang_array['sInfo'] = "إظهار _START_ إلى _END_ من أصل _TOTAL_ مدخل" ;
       	 lang_array['sInfoEmpty'] = "يعرض 0 إلى 0 من أصل 0 سجل" ;
       	 lang_array['sInfoFiltered'] = "(منتقاة من مجموع _MAX_ مُدخل)";
       	 lang_array['sSearch'] = "ابحث:" ;
       	 lang_array['sFirst'] =  "الأول" ;
       	 lang_array['sPrevious'] = "السابق" ;
       	 lang_array['sNext'] = "التالي" ;
       	 lang_array['sLast'] = "الأخير" ;
       }
    </script>
    
    <!-- my js -->
    <script src="<?php echo base_url() ?>admin_assets/js/general.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/ad.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/commercial_ads.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/users.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/main_commercials.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/category.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/types.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/reported_ads.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/educations.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/schedules.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/cities.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/notifications.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/about.js"></script>
   
  </body>
</html>