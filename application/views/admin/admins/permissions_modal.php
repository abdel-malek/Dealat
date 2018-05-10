  <div class="modal fade permissions_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm" style="width: 460px !important;">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel2"><?php echo $this->lang->line('admin_permissions') ?></h4>
        </div>
        <div class="modal-body">
           <form id="permissions_form" acrion="post"  class="form-horizontal form-label-left">
   	          <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::ADS_MANAGE; ?>" id="<?php echo PERMISSION::ADS_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('ads_manage') ?>
	                </label>
                </div>
                <div class="checkbox col-md-6"">
	                <label>
	                  <input value="<?php echo PERMISSION::EXPORT_ADS; ?>" id="<?php echo PERMISSION::EXPORT_ADS; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('export_ads') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::REPORTS_MANAGE; ?>" id="<?php echo PERMISSION::REPORTS_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('reports_manage');?>
	                </label>
                </div>
                <div class="checkbox col-md-6"">
	                <label>
	                  <input value="<?php echo PERMISSION::EXPORT_REPORTS; ?>" id="<?php echo PERMISSION::EXPORT_REPORTS; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('export_reports') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::USERS_MANAGE; ?>" id="<?php echo PERMISSION::USERS_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('users_manage');?>
	                </label>
                </div>
                <div class="checkbox col-md-6"">
	                <label>
	                  <input value="<?php echo PERMISSION::EXPORT_USERS; ?>" id="<?php echo PERMISSION::EXPORT_USERS; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('export_users') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::COMMERCIALS_MANAGE; ?>" id="<?php echo PERMISSION::COMMERCIALS_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('commercials_manage');?>
	                </label>
                </div>
                <div class="checkbox col-md-6"">
	                <label>
	                  <input value="<?php echo PERMISSION::EXPORT_COMMERCIALS; ?>" id="<?php echo PERMISSION::EXPORT_COMMERCIALS; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('export_commercials') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::CATEGORIES_MANAGE; ?>" id="<?php echo PERMISSION::CATEGORIES_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('categories_manage');?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::DATA_MANAGE; ?>" id="<?php echo PERMISSION::DATA_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('data_manage');?>
	                </label>
                </div>
                <div class="checkbox col-md-6"">
	                <label>
	                  <input value="<?php echo PERMISSION::EXPORT_DATA; ?>" id="<?php echo PERMISSION::EXPORT_DATA; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('export_data') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::NOTIFICATION_MANAGE; ?>" id="<?php echo PERMISSION::NOTIFICATION_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('notification_manage');?>
	                </label>
                </div>
                <div class="checkbox col-md-6"">
	                <label>
	                  <input value="<?php echo PERMISSION::EXPORT_NOTIFICATIONS; ?>" id="<?php echo PERMISSION::EXPORT_NOTIFICATIONS; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('export_notification') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::ADMINS_MANAGE; ?>" id="<?php echo PERMISSION::ADMINS_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('admins_manage');?>
	                </label>
                </div>
                <div class="checkbox col-md-6"">
	                <label>
	                  <input value="<?php echo PERMISSION::EXPORT_ADMINS; ?>" id="<?php echo PERMISSION::EXPORT_ADMINS; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('admins_export') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-md-6">
	                <label>
	                  <input value="<?php echo PERMISSION::ABOUT_MANAGE; ?>" id="<?php echo PERMISSION::ABOUT_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('about_manage');?>
	                </label>
                </div>
	         </div>
        </div>
        <input type="hidden" id="user_id_perm" name="admin_id"/>
        <div class="modal-footer">
          <button  type="submit" class="btn btn-success" ><?php echo $this->lang->line('save') ?></button>
        </div>
      </form>
      </div>
    </div>
  </div>