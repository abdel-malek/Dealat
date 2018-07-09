 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b id=""><?php echo $this->lang->line('users_list') ?></b></h3>  
	              </div>
	        
	              	 <!-- filter form -->
		             <!-- <div class="row" id="filter_panel">
		              <div class="col-md-12 col-sm-12 col-xs-12">
		                <div class="x_panel">
		                  <div class="x_content" >
		                    </br>
		                     </br>
		                     <div class='row'>
			                   <div class="col-md-4">
		                      	<label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('status') ?></label>
		                         <select class="form-control select2_single" id="status_select" tabindex="-1">
		                           <option value ='0'><?php echo $this->lang->line('all') ?></option>
		                        	<?php $status = STATUS::get_list();?>
		                         	<?php if($status!= null): foreach ($status as $key => $value): ?>
		                         		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
		                            <?php  endforeach; ?>
		                            <?php endif; ?> 
		                          </select>
		                        </div>
		                     </div>
		                    </div>
		                  </div>
		                </div>
		              </div> -->
		             <!-- /filter form -->
	             </div> 
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                    <table id="users_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('name') ?></th>
	                          <th><?php echo $this->lang->line('phone') ?></th>
	                          <th><?php echo $this->lang->line('email') ?></th>
	                          <th><?php echo $this->lang->line('city') ?></th>
	                          <th><?php echo $this->lang->line('ads_num') ?></th>
	                          <th><?php echo $this->lang->line('user_status') ?></th>
	                          <th><?php echo $this->lang->line('admin_status') ?></th>
	                          <th><?php echo $this->lang->line('details') ?></th>
	                          <th><?php echo $this->lang->line('chats') ?></th>
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
	        <?php $this->load->view('admin/users/user_chats_modal') ?>
	        <?php $this->load->view('admin/users/details_modal') ?>
        <!-- /page content -->