  <div class="modal fade admin_manage_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm" style="width: 460px !important;">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel2"><?php echo $this->lang->line('admin_info') ?></h4>
        </div>
        <div class="modal-body">
           <form  class="form-horizontal form-label-left">
   	          <div class="form-group">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('admin_name') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	           <input type="text" class="form-control"  id='admin_name'></input>
      	         </div>
              </div> 
              
              <div class="form-group">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('admin_username') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	           <input type="text" class="form-control"  id='admin_username'></input>
      	         </div>
              </div>
              
              <div class="form-group">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('password') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	           <input type="password" class="form-control"  id='admin_password'></input>
      	         </div>
              </div>
            </form>
        </div>
        <input type="hidden" id="admin_id_input" />
        <div class="modal-footer">
          <!-- <button type="button" class="btn btn-default" data-dismiss="modal"><?php echo $this->lang->line('cancel') ?></button> -->
          <button  style="display: none" id="delete_admin" type="button" class="btn btn-danger" data-toggle="modal" data-target=".admin-delete-modal"><span class='fa fa-trash'></span></button>
          <button  onclick="save_admin()" type="button" class="btn btn-success" ><?php echo $this->lang->line('save') ?></button>
        </div>
      </form>
      </div>
    </div>
  </div>
  
   <!-- confirmation modal   -->
  <div id="admin-delete-modal" class="modal fade admin-delete-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
            	<?php echo $this->lang->line('confirmation') ?>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-center">
                <h6><?php echo $this->lang->line('delete_admin_confirmation') ?></h6>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn button2 submit" onclick="delete_admin()""><?php echo $this->lang->line('yes') ?></button>
            </div>
        </div>
    </div>
  </div>