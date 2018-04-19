 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('set_about_info') ?></b></h3>  
	              </div>
	              	 <!-- filter form -->
		             <div class="row" id="filter_panel">
		              </div>
		             <!-- /filter form -->
	             </div> 
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
			             <form  data-parsley-validate class="form-horizontal form-label-left">
			               <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('about_ar') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	            <textarea  class="form-control col-md-7 col-xs-12" name="ar_about_us" id="about_ar"><?php echo $about_info->ar_about_us ?></textarea>
			      	         </div>
			               </div> 
			               <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('about_en') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	            <textarea  class="form-control col-md-7 col-xs-12"  name="en_about_us" id="about_en"><?php echo $about_info->en_about_us ?></textarea>
			      	         </div>
			               </div> 
			               <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('phone') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control" name="phone" id='about_phone' value ="<?php echo $about_info->phone ?>"></input>
			      	         </div>
				           </div> 
				           <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('email') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control" name="email"  id='about_email' value="<?php echo $about_info->email ?>"></input>
			      	         </div>
				           </div> 
			               <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('facebook_link') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control" name="facebook_link" id='facebook_link' value="<?php echo $about_info->facebook_link ?>"></input>
			      	         </div>
				           </div> 
				           <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('twiter_link') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control" name="twiter_link"  id='twiter_link' value="<?php echo $about_info->twiter_link ?>"></input>
			      	         </div>
				           </div> 
				           <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('youtube_link') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control"  name="youtube_link"  id='youtube_link' value="<?php echo $about_info->youtube_link ?>"></input>
			      	         </div>
				           </div> 
				           <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('instagram_link') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control"  name="instagram_link"  id='instagram_link' value="<?php echo $about_info->instagram_link ?>"></input>
			      	         </div>
				           </div>
				           <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('linkedin_link') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control" name="linkedin_link"   id='linkedin_link' value="<?php echo $about_info->linkedin_link ?>"></input>
			      	         </div>
				           </div> 
			               <div class='pull-right'>
			               	  <button id="" onclick="save_about()" type="button" class="btn btn-primary"><?php echo $this->lang->line('save') ?></button>
			               </div>
		                </form>
	                  </div>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>
        <!-- /page content -->