 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('all_cities_list') ?></b></h3>  
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
		                      	<label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('template') ?></label>
		                         <select class="form-control select2_single" id="filter_type_template_select" tabindex="-1">
		                           <option value ='0'><?php echo $this->lang->line('all') ?></option>
		                        	<?php $templates = TAMPLATES::get_templates_with_types($this->session->userdata('language'));?>
		                         	<?php if($templates!= null): foreach ($templates as $key => $value): ?>
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
	                  <?php if(PERMISSION::Check_permission(PERMISSION::ADD_DATA , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                  	    <div class="pull-left">
                           <button onclick="show_cities_manage_modal(0);" type="button" class="btn btn-primary"><li class="fa fa-plus"></li> <?php echo $this->lang->line('add_city') ?></button>
                        </div>
                      <?php endif; ?>
	                    <table id="cities_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('english_name') ?></th>
	                          <th><?php echo $this->lang->line('arabic_name') ?></th>
	                          <th><?php echo $this->lang->line('edit') ?></th>
	                          <th><?php echo $this->lang->line('locations') ?></th>
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
	        <?php $this->load->view('admin/cities/cities_manage_modal') ?>
	        <?php $this->load->view('admin/cities/locations_modal') ?>
