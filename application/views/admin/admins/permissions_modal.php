  <div class="modal fade permissions_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 460px !important;">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel2"><?php echo $this->lang->line('admin_permissions') ?></h4>
        </div>
        <div class="modal-body">
           <form id="permissions_form" acrion="post"  class="form-horizontal form-label-left">
  	          <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                  <input class='permission_check flat' value="<?php echo PERMISSION::ADS_MANAGE; ?>" id="<?php echo PERMISSION::ADS_MANAGE; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('ads_manage') ?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('always_accept') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							   <input class='permission_check flat' value="<?php echo PERMISSION::EXPORT_ADS; ?>" id="<?php echo PERMISSION::EXPORT_ADS; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('export_ads') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('always_reject') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('reject_after_accept') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('always_hide') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('hide_after_accept') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('always_delete') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('delete_after_accept') ?>
							</label>
						</div>
					</div>
                </div>
  	          </div>
  	          <hr>
  	          
  	          
  	          <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                  <input class='permission_check flat' value="<?php echo PERMISSION::REPORTS_MANAGE; ?>" id="<?php echo PERMISSION::REPORTS_MANAGE; ?>" type="checkbox" name="permissions[]">  <?php echo $this->lang->line('reports_manage');?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							   <input class='permission_check flat' value="<?php echo PERMISSION::EXPORT_REPORTS; ?>" id="<?php echo PERMISSION::EXPORT_REPORTS; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('export') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('hide') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('reject') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('delete') ?>
							</label>
						</div>
					</div>
					
                </div>
  	          </div>
  	          <hr>
  	          
  	          <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                  <input class='permission_check flat' value="<?php echo PERMISSION::EXPORT_USERS; ?>" id="<?php echo PERMISSION::EXPORT_USERS; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('export_users') ?>
	                </label>
                </div>
                
                <div class="row sub hidden">
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('block') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('send_msg') ?>
							</label>
						</div>
					</div>
					
                </div>
  	          </div>
  	          <hr>
  	          
  	           <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('main_commercial_ads_manage');?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('export') ?>
							</label>
						</div>
					</div>
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('add') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('show') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('delete') ?>
							</label>
						</div>
					</div>
					
                </div>
  	          </div>
  	          <hr>
  	          
  	          <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('other_commercial_ads_manage');?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('export') ?>
							</label>
						</div>
					</div>
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('add') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('show') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('delete') ?>
							</label>
						</div>
					</div>
					
                </div>
  	          </div>
  	          <hr>
  	          
  	           <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                   <input class='permission_check flat' value="<?php echo PERMISSION::CATEGORIES_MANAGE; ?>" id="<?php echo PERMISSION::CATEGORIES_MANAGE; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('categories_manage');?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('add_main_category') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('add_sub_category') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('edit_main_category') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('edit_sub_category') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('hide_main_category') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('hide_sub_category') ?>
							</label>
						</div>
					</div>
					 <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('delete_main_category') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('delete_sub_category') ?>
							</label>
						</div>
					</div>
                </div>
  	          </div>
  	          <hr>
  	          
  	          <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                   <input class='permission_check flat' value="<?php echo PERMISSION::DATA_MANAGE; ?>" id="<?php echo PERMISSION::DATA_MANAGE; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('data_manage');?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							   <input class='permission_check flat' value="<?php echo PERMISSION::EXPORT_DATA; ?>" id="<?php echo PERMISSION::EXPORT_DATA; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('export') ?>
							</label>
						</div>
					</div>
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('add') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('edit') ?>
							</label>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('delete') ?>
							</label>
						</div>
					</div>
					
                </div>
  	          </div>
  	          <hr>
  	          
  	     
  	     <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                   <input class='permission_check flat' value="<?php echo PERMISSION::NOTIFICATION_MANAGE; ?>" id="<?php echo PERMISSION::NOTIFICATION_MANAGE; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('notification_manage');?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							    <input class='permission_check flat' value="<?php echo PERMISSION::EXPORT_NOTIFICATIONS; ?>" id="<?php echo PERMISSION::EXPORT_NOTIFICATIONS; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('export') ?>
							</label>
						</div>
					</div>
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('send') ?>
							</label>
						</div>
					</div>
					
                </div>
  	          </div>
  	          <hr>
  	          
  	          <div class="form-group">
  	          	<div class="checkbox main">
	                <label>
	                    <input class='permission_check flat' value="<?php echo PERMISSION::ABOUT_MANAGE; ?>" id="<?php echo PERMISSION::ABOUT_MANAGE; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('about_manage');?>
	                </label>
                </div>
                
                <div class="row sub hidden">
                <div class="col-xs-12 col-sm-6">
						<div class="checkbox">
							<label>
							  <input class='permission_check flat' value="" id="" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('edit') ?>
							</label>
						</div>
					</div>
					
                </div>
  	          </div>
  	          <hr>
  	    
	         <div class="form-group row">
        	    <div class="checkbox col-xs-12 col-sm-6">
	                <label>
	                  <input class='permission_check flat' value="<?php echo PERMISSION::ADMINS_MANAGE; ?>" id="<?php echo PERMISSION::ADMINS_MANAGE; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('admins_manage');?>
	                </label>
                </div>
                <div class="checkbox col-xs-12 col-sm-6">
	                <label> 
	                  <input class='permission_check flat' value="<?php echo PERMISSION::EXPORT_ADMINS; ?>" id="<?php echo PERMISSION::EXPORT_ADMINS; ?>" type="checkbox"  name="permissions[]">  <?php echo $this->lang->line('admins_export') ?>
	                </label>
                </div>
	         </div>
	         
	         <div class="form-group row">
        	    <div class="checkbox col-xs-12 col-sm-6">
	                <label>
	                  <input class='permission_check flat' value="<?php echo PERMISSION::VIEW_ADMINS_ACTIONS; ?>" id="<?php echo PERMISSION::VIEW_ADMINS_ACTIONS; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('view_actions_log');?>
	                </label>
                </div>
                <div class="checkbox col-xs-12 col-sm-6">
	                <label>
	                  <input class='permission_check flat' value="<?php echo PERMISSION::EXPORT_ACTIONS_LOG; ?>" id="<?php echo PERMISSION::EXPORT_ACTIONS_LOG; ?>" type="checkbox" class="flat" name="permissions[]">  <?php echo $this->lang->line('export_actions') ?>
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