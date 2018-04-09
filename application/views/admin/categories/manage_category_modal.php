   <div class="modal fade manage_cat" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('category_details') ?></h4>
        </div>
        <div class="modal-body">
                <form id="manage_cat" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('arabic_name') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <input  class="form-control col-md-7 col-xs-12" id="cat_arabic_name"></input>
	      	         </div>
	               </div> 
	               
	                
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('english_name') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <input  class="form-control col-md-7 col-xs-12" id="cat_english_name"></input>
	      	         </div>
	               </div> 
	               
	               <div class="form-group choose_tamplate_div" style="display: none">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('templates') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12 fit_select_div">
	      	               <select class="form-control select2_single" id="cat_tamplate" tabindex="-1">
      	               	        <?php $tamplates = TAMPLATES::get_template_list($this->session->userdata('language'));?>
	                         	<?php if($tamplates!= null): foreach ($tamplates as $key => $value): ?>
	                         		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
	                            <?php  endforeach; ?>
	                            <?php endif; ?> 
		                   </select>
	      	         </div>
	               </div>
	               
	               <div style="display: none" class="form-group hidden_fields_manage_div">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('fields_status') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <label style="display: none" for="cat_is_new" class='col-md-6 col-xs-12 template_info_cat is_new_cat'>
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_is_new"></input>
	      	            	<?php echo $this->lang->line('is_new') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_type_name" class="col-md-6 col-xs-12 template_info_cat 1_info_cat 3_info_cat 4_info_cat">
	      	            	<input checked="" type="checkbox" class="hide_check_box" id="cat_type_name"></input>
	      	            	<?php echo $this->lang->line('type') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_type_model_name " class="col-md-6 col-xs-12 template_info_cat 1_info_cat">
	      	            	<input checked=""  type="checkbox" class="template_info_cat 1_info_cat" id="cat_type_model_name"></input>
	      	                <?php echo $this->lang->line('type_model') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_manufacture_date" class="col-md-6 col-xs-12 template_info_cat 1_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_manufacture_date"></input>
	      	            	<?php echo $this->lang->line('manufacture_date') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_kilometer" class="col-md-6 col-xs-12 template_info_cat 1_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_kilometer"></input>
	      	            	<?php echo $this->lang->line('kilometrage') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_is_automatic" class="col-md-6 col-xs-12 template_info_cat 1_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_is_automatic"></input>
	      	            	<?php echo $this->lang->line('motion') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_with_furniture" class="col-md-6 col-xs-12 template_info_cat 2_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_with_furniture"></input>
	      	                <?php echo $this->lang->line('with_furniture') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_space" class="col-md-6 col-xs-12 template_info_cat 2_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_space"></input>
	      	            	<?php echo $this->lang->line('space') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_rooms_num" class="col-md-6 col-xs-12 template_info_cat 2_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_rooms_num"></input>
	      	            	<?php echo $this->lang->line('rooms_num') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_floor" class="col-md-6 col-xs-12 template_info_cat 2_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_floor"></input>
	      	            	<?php echo $this->lang->line('floor') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_state" class="col-md-6 col-xs-12 template_info_cat 2_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_state"></input>
	      	            	<?php echo $this->lang->line('state') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_size" class="col-md-6 col-xs-12 template_info_cat 4_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_size"></input>
	      	            	<?php echo $this->lang->line('size') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_education_name" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	                <input checked=""  type="checkbox" class="hide_check_box" id="cat_education_name"></input>
	      	                <?php echo $this->lang->line('education') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_schedule_name" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_schedule_name"></input>
	      	            	<?php echo $this->lang->line('schedule') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_experience" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_experience"></input>
	      	            	<?php echo $this->lang->line('experience') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_salary" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_salary"></input>
	      	            	<?php echo $this->lang->line('salary') ?>
	      	            </label>
	      	         </div>
	               </div> 
	               
                </form>
              <input type='hidden' id='cat_id_input' parent_id template_id />
          </div>
        <div class="modal-footer">
          <button   onclick="save_category()"  id="" type="button" class="btn btn-success"><?php echo $this->lang->line('save') ?></button>
          <button   onclick="" id=""  type="button" class="btn btn-danger"><?php echo $this->lang->line('delete') ?></button>
          <!-- <div id="delete-modal" class="modal fade delete_modal" tabindex="-1" role="dialog" aria-hidden="true">
          	 data-toggle="modal" data-target=".delete_modal"
		    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		         <span aria-hidden="true">&times;</span>
		       </button>
		            </div>
		            <div class="modal-body text-center">
		                <h6>Are you sure you want to delete this ad?</h6>
		            </div>
		            <div class="modal-footer">
		                <button type="submit" class="btn button2 submit"  onclick="delete_comm()">Yes</button>
		            </div>
		        </div>
		    </div>
         </div> -->
        </div>
      </div>
    </div>
  </div>