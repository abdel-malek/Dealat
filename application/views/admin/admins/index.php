 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b id=""><?php echo $this->lang->line('admins_list') ?></b></h3>  
	              </div>
	             </div> 
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                  	<div class="pull-right">
                           <button onclick="show_admin_info(0);" type="button" class="btn btn-primary"><li class="fa fa-plus"></li> <?php echo $this->lang->line('add_admin') ?></button>
                        </div>
	                    <table id="admins_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('created_at') ?></th>
	                          <th><?php echo $this->lang->line('admin_name') ?></th>
	                          <th><?php echo $this->lang->line('admin_username') ?></th>
	                          <th><?php echo $this->lang->line('edit') ?></th>
	                          <th><?php echo $this->lang->line('permissions') ?></th>
	                        </tr>
	                      </thead>
	                      <tbody>
	                     </tbody>
	                    </table>
	                  </div>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>
	        <?php $this->load->view('admin/admins/admin_manage_modal') ?>
	        <?php $this->load->view('admin/admins/permissions_modal') ?>
        <!-- /page content -->