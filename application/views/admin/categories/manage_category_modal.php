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
	      	            <label style="display: none" for="cat_engine_capacity" class="col-md-6 col-xs-12 template_info_cat 1_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_engine_capacity"></input>
	      	            	<?php echo $this->lang->line('engine_capacity') ?>
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
	      	            <label style="display: none" for="cat_floors_number" class="col-md-6 col-xs-12 template_info_cat 2_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_floors_number"></input>
	      	            	<?php echo $this->lang->line('floors_number') ?>
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
	      	            <label style="display: none" for="cat_certificate_name" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	                <input checked=""  type="checkbox" class="hide_check_box" id="cat_certificate_name"></input>
	      	                <?php echo $this->lang->line('certificate') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_schedule_name" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_schedule_name"></input>
	      	            	<?php echo $this->lang->line('schedule') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_experience" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_experience"></input>
	      	            	<?php echo $this->lang->line('experience') ?>
	      	            </label>
	      	            <label style="display: none" for="cat_gender" class="col-md-6 col-xs-12 template_info_cat 8_info_cat">
	      	            	<input checked=""  type="checkbox" class="hide_check_box" id="cat_gender"></input>
	      	            	<?php echo $this->lang->line('gender') ?>
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
          <label style="display: none" id='has_ads_note'><?php echo $this->lang->line('has_ads_note') ?></label>
          <button  onclick="save_category()"  id="" type="button" class="btn btn-success"><?php echo $this->lang->line('save') ?></button>
          <button  style="display: none" id="final_delete_category_btn"  type="button" class="btn btn-danger"  data-toggle="modal" data-target=".cat-delete-modal"><?php echo $this->lang->line('delete') ?></button>
          <button  style="display: none; background-color: #bdc3c7; border-color: #aaafb3;"  onclick="deactivate_cat()" id="delete_category_btn"  type="button" class="btn btn-danger"><?php echo $this->lang->line('diactivate') ?></button>
          <button  style="display: none"  onclick="activate_cat()" id="activate_category_btn"  type="button" class="btn btn-primary"><?php echo $this->lang->line('activate') ?></button>
        </div>
      </div>
    </div>
  </div>
 
 <!-- confirmation modal   -->
  <div id="cat-delete-modal" class="modal fade cat-delete-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
            	<?php echo $this->lang->line('confirmation') ?>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-center">
                <h6><?php echo $this->lang->line('delete_cat_confirmation') ?></h6>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn button2 submit"  onclick="delete_cat()"><?php echo $this->lang->line('yes') ?></button>
            </div>
        </div>
    </div>
 </div>