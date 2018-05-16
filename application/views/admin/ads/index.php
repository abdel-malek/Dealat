 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('users_ads_list') ?></b></h3>  
	              </div>
	        
	              	 <!-- filter form -->
		             <div class="row" id="filter_panel">
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
		                        	<?php $status = STATUS::get_list($this->session->userdata('language'));?>
		                         	<?php if($status!= null): foreach ($status as $key => $value): ?>
		                         		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
		                            <?php  endforeach; ?>
		                            <?php endif; ?> 
		                          </select>
		                        </div>
		                        
		                        <div class="col-md-4" id='edit_status_selecter_div'>
		                      	<label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('edit_status') ?></label>
		                         <select class="form-control select2_single" id="edit_status_select" tabindex="-1">
		                           <option value ='0'><?php echo $this->lang->line('all') ?></option>
		                        	<?php $status = EDIT_STATUS::get_list($this->session->userdata('language'));?>
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
		              </div>
		            </div>
		             <!-- /filter form -->
	              
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                  	<h2>
	                  		<span id="status_count_label"> </span>
	                  		<span class="countIcon"><span class="badge bg-green pending_count"></span></span>
	                    </h2>
	                    <table id="ads_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('created_at') ?></th>
	                          <th><?php echo $this->lang->line('tamplate_name') ?></th>
	                          <th><?php echo $this->lang->line('category') ?></th>
	                          <th><?php echo $this->lang->line('title') ?></th>
	                          <th><?php echo $this->lang->line('publish_date') ?></th>
	                          <th><?php echo $this->lang->line('price') ?></th>
	                          <th><?php echo $this->lang->line('location') ?></th>
	                          <th><?php echo $this->lang->line('status') ?></th>
	                          <th><?php echo $this->lang->line('edit_status') ?></th>
	                          <th><?php echo $this->lang->line('details') ?></th>
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
	       <!-- </div>
	      </div> -->
        
        <!-- /page content -->