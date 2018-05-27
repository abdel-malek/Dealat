 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('categories_management') ?></b></h3>  
	              </div>
	              
	             
		        </div>
	           </div>
	          <div class="clearfix"></div>
	            <div class="row">
                     <div class="x_panel">
	                  <div class="x_title">
	                    <!-- <h2><i class="fa fa-bars"></i> <?php echo $this->lang->line('commercial_ads_by_category') ?></h2> -->
	                    <div class="clearfix"></div>
	                  </div>
	                  <div class="x_content">
	                    <div class="col-xs-3">
	                      <!-- required for floating -->
	                      <!-- Nav tabs -->
	                      <ul class="nav nav-tabs tabs-left">
	                      	<!-- main add -->
	                      	<li>
	                      	  <?php if(PERMISSION::Check_permission(PERMISSION::ADD_MAIN_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
	                      		<button id="main_cat_add_btn" onclick="show_manage_cat_modal(0 , 1 , 0 , 0);" type="button" class="btn btn-primary" ><li class="fa fa-plus"></li><?php echo $this->lang->line('add_main_category') ?></button>
	                      	  <?php endif; ?>
	                      		<button id="main_cat_add_btn" onclick="show_sort_modal(0);" type="button" class="btn btn-primary" ><li class="fa fa-sort-amount-asc"></li>  <?php echo $this->lang->line('sort') ?></button>
	                      	</li>
	                        <?php if(isset($main_categories) && $main_categories!= null): ?>
	                        	<?php foreach ($main_categories as $row):?>
	                        	   <li class="li-row">
	                        	    <!-- <ul> -->
	                        	    	<div class="row">
	                        	    		<div class="col-sm-10"><a  class="cat_tap" href="#<?php echo $row->category_id ?> " data-toggle="tab"><span id="cat<?php echo $row->category_id ?>"><?php echo $row->category_name ?> </span></a></div>
	                        	    	    <?php if(PERMISSION::Check_permission(PERMISSION::UPDATE_MAIN_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN')) ||
	                        	    	             PERMISSION::Check_permission(PERMISSION::DELETE_MAIN_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN')) ||
													 PERMISSION::Check_permission(PERMISSION::HIDE_MAIN_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
	                        	    		   <div class="col-sm-2"><span onclick="show_manage_cat_modal(<?php echo $row->category_id  ?> , 1 ,0 , <?php echo $row->tamplate_id  ?> );" class="icon-edit"><i class="fa fa-edit"></i></span></div>
	                        	    		<?php endif; ?>
	                        	    	</div>
	                        	    <!-- <a  class="" href="#<?php echo $row->category_id ?> " data-toggle="tab"><?php echo $row->category_name ?></a> -->
	                        	    <!-- </ul> -->
	                               </li>
	                        	<?php endforeach;?>
	                        <?php endif; ?>
	                      </ul>
	                    </div>
	
	                    <div class="col-xs-9">
	                      <!-- Tab panes -->
	                      <div class="tab-content">
	                       <?php if(isset($main_categories) && $main_categories!= null): ?>
	                          <?php foreach ($main_categories as $row):?>
	                              <?php if($row->category_id  == "1"): ?>
	                              	 <div class="tab-pane active" id="<?php echo $row->category_id ?>">
	                              <?php else: ?>
	                              	 <div class="tab-pane" id="<?php echo $row->category_id ?>">
	                              <?php endif; ?>
	                                  <!-- sub add -->
	                                  <?php if(PERMISSION::Check_permission(PERMISSION::ADD_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
	                                    <button id="main_cat_add_btn" onclick="show_manage_cat_modal(0 ,0 , <?php echo $row->category_id ?> , <?php echo $row->tamplate_id ?>  );" type="button" class="btn btn-primary" ><li class="fa fa-plus"></li><?php echo $this->lang->line('add_subcategory_for') ?>
	                                       <span id="for_cat<?php echo $row->category_id?>"><?php echo $row->category_name ?></span>
	                                    </button>
	                                   <?php endif; ?>
	                                    <button id="main_cat_add_btn" onclick="show_sort_modal(<?php echo $row->category_id ?> );" type="button" class="btn btn-primary" ><li class="fa fa-sort-amount-asc"></li>  <?php echo $this->lang->line('sort') ?></button>
			                             <!-- start accordion -->
						                    <div class="accordion" id="accordion" role="tablist" aria-multiselectable="true">
						                      <?php $sub_cats =  get_sub_cats($row->category_id , $this->session->userdata('language'))?>
						                      <?php if(isset($sub_cats) && $sub_cats!= null): ?>
						                      	  <?php foreach ($sub_cats as $sub_row):?>
						                      	  	  <div class="panel">
						                      	  	    <div class="row">
						                      	  	      <a class="panel-heading col-md-10" role="tab" id="headingOne" data-toggle="collapse" data-parent="#accordion" href="#<?php echo $sub_row->category_id ?>" aria-expanded="true" aria-controls="collapseOne">
								                            <h4 class="panel-title">
								                            	<span id="cat<?php echo $sub_row->category_id?>"><?php echo $sub_row->category_name ?></span>
								                            	<?php if(PERMISSION::Check_permission(PERMISSION::UPDATE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN')) ||
						                        	    	             PERMISSION::Check_permission(PERMISSION::DELETE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN')) ||
																		 PERMISSION::Check_permission(PERMISSION::HIDE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
									                            	<?php if($this->session->userdata('language') == 'en'): ?>
									                            	   <span class='pull-right' onclick="show_manage_cat_modal(<?php echo $sub_row->category_id  ?> , 0,<?php echo $row->category_id ?> , <?php echo $row->tamplate_id ?>);" ><li class="fa fa-edit"></li></span>
									                            	<?php else: ?>
									                            	   <span class='pull-left' onclick="show_manage_cat_modal(<?php echo $sub_row->category_id  ?> , 0,<?php echo $row->category_id ?> , <?php echo $row->tamplate_id ?>);" ><li class="fa fa-edit"></li></span>
									                            	<?php endif; ?>
									                            <?php endif; ?>
								                            </h4>
								                          </a>
								                          <!-- <span class='col-md-2' onclick="show_manage_cat_modal(<?php echo $sub_row->category_id  ?> , 0,<?php echo $row->category_id ?> , <?php echo $row->tamplate_id ?>);" ><li class="fa fa-edit"></li></span> -->
						                      	  	    </div>
								                        <div id="<?php echo $sub_row->category_id ?>" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
								                          <div class="panel-body">
								                          	<!-- sub sub add -->
								                            <?php if(PERMISSION::Check_permission(PERMISSION::ADD_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
									                          	<button id="main_cat_add_btn" onclick="show_manage_cat_modal(0, 0 , <?php echo $sub_row->category_id ?>  , <?php echo $sub_row->tamplate_id ?> );" type="button" class="btn btn-primary" ><li class="fa fa-plus"></li><?php echo $this->lang->line('add_subcategory_for') ?>
									                          	   <span id='for_cat<?php echo $sub_row->category_id?>'><?php echo $sub_row->category_name ?></span>
									                          	</button>
									                        <?php endif; ?>
								                          	<button id="main_cat_add_btn" onclick="show_sort_modal(<?php echo $sub_row->category_id ?>);" type="button" class="btn btn-primary" ><li class="fa fa-sort-amount-asc"></li>  <?php echo $this->lang->line('sort') ?></button>
								                            <table id='sub_sub_cats<?php echo $sub_row->category_id ?>' class="table table-striped table-bordered">
								                              <thead>
								                                <tr>
								                                  <th>#</th>
								                                  <th><?php echo $this->lang->line('category_name') ?></th>
								                                  <?php if(PERMISSION::Check_permission(PERMISSION::UPDATE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))||
						                        	    	               PERMISSION::Check_permission(PERMISSION::DELETE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN')) ||
																		   PERMISSION::Check_permission(PERMISSION::HIDE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
								                                    <th><?php echo $this->lang->line('edit') ?></th>
								                                  <?php endif; ?>
								                                </tr>
								                              </thead>
								                              <tbody>
								                              	<?php $sub_sub_cats = get_sub_cats($sub_row->category_id , $this->session->userdata('language')); ?>
								                              	<?php if(isset($sub_sub_cats) && $sub_sub_cats!= null): ?>
								                              		<?php foreach ($sub_sub_cats as $sub_sub_row):?>
								                              		   <tr>
										                                  <th scope="row"><?php echo $sub_sub_row->category_id ?></th>
										                                  <td><span id='cat<?php echo  $sub_sub_row->category_id?>'><?php echo $sub_sub_row->category_name ?></span></td>
										                                  <?php if(PERMISSION::Check_permission(PERMISSION::UPDATE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))||
						                        	    	                       PERMISSION::Check_permission(PERMISSION::DELETE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN')) ||
																		           PERMISSION::Check_permission(PERMISSION::HIDE_SUB_CAT , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
										                                     <td><button id="" onclick="show_manage_cat_modal(<?php echo $sub_sub_row->category_id ?>  , 0 , <?php echo $sub_row->category_id ?> ,<?php echo $sub_row->tamplate_id ?>  );" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button></td>
										                                  <?php endif; ?>
										                               </tr>
										                            <?php endforeach; ?>
								                              	<?php else:  ?>
								                              	    <!-- empty note --> 
								                              	<?php endif;?>
								                              </tbody>
								                            </table>
								                          </div>
								                        </div>
								                       </div> 
						                      	  <?php endforeach; ?>
						                      <?php else: ?>
						                      	  <!-- empty note -->
						                      <?php endif; ?>	
						                    </div>
					                    <!-- end of accordion -->
			                        </div>
	                          <?php endforeach;?>
	                        <?php endif; ?>
	                      </div>
	                    </div>
	
	                    <div class="clearfix"></div>
	
	                  </div>
	                </div>   
	            </div>
	          </div>
	        </div>
	       <!-- </div>
	      </div> -->
         <?php $this->load->view('admin/categories/manage_category_modal'); ?>
         <?php $this->load->view('admin/categories/sorting_modal'); ?>
        <!-- /page content -->