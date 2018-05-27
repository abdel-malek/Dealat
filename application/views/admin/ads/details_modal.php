   <div class="modal fade ads_details" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="ad_deatils_title"><?php echo $this->lang->line('ad_details') ?></h4>
        </div>
        <div class="modal-body">
        	
            <div class="" role="tabpanel" data-example-id="togglable-tabs">
              <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true"><?php echo $this->lang->line('basic_info') ?></a>
                </li>
                <li role="presentation" class=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false"><?php echo $this->lang->line('detailed_info') ?></a>
                </li>
                <li role="presentation" class=""><a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false"><?php echo $this->lang->line('seller_info') ?></a>
                </li>
              </ul>
              <div id="myTabContent" class="tab-content">
                <div role="tabpanel" class="tab-pane fade active in slider_div" id="tab_content1" aria-labelledby="home-tab">
                    <form id="basic_info_ad" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
                       <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('title') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_title'>ad title</label>
		      	         </div>
		               </div> 
		               
		               <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('description') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <p class="form-control"  id='ad_description'><?php echo $this->lang->line('not_set') ?></p>
		      	         </div>
		               </div> 
                    </form>
	               	<!-- <div class="images-slider slick-slider">
					</div> -->
                </div>
                <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">
                   <form id="detailed_info_ad" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
                   	 <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('ad_number') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_id'>1</label>
		      	         </div>
		             </div>
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('status') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_status'> pending</label>
		      	         </div>
		             </div>
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('edit_status') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_edit_status'> pending</label>
		      	         </div>
		             </div>
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('created_at') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_creation_date'> 2-19-2017</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group" style="display: none" id='publish_date_div'>
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('published_at') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_publish_date'></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group" style="display: none" id='expiry_date_div'>
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('expire_at') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_expire_date'></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('category') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_category'></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('location') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_location'> syria</label>
		      	         </div>
		             </div>
		             
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('price') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_price'> 10000$</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('is_negotiable') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_negotiable'>No</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group featured_div">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('is_featured') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_featured'>No</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group featured_select_div" style="display: none">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('is_featured') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <select id="select_featured" class='form-control' style="margin-bottom: 10px;">
		      	           	  <option value="1">Yes</option>
		      	           	  <option value="0">No</option>
		      	           </select>
		      	         </div>
		             </div>
		             
		             <!-- tamplates info  -->
		             
		             <div class="form-group template_info is_new">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('is_new') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_is_new'>No</label>
		      	         </div>
		             </div>
		             
		             <!-- vehicles -->
		             
		             <div class="form-group template_info 1_info 3_info 4_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('type') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_type_name'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 1_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('type_model') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_type_model_name'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 1_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('manufacture_date') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_manufacture_date'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 1_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('kilometrage') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_kilometer'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 1_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('motion') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_is_automatic'><?php echo $this->lang->line('automatic') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 1_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('engine_capacity') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_engine_capacity'><?php echo $this->lang->line('engine_capacity') ?></label>
		      	         </div>
		             </div>
		             
		             <!-- propertis --> 
		             
		             <div class="form-group template_info 2_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('with_furniture') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_with_furniture'>No</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 2_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('space') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_space'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 2_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('rooms_num') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_rooms_num'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 2_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('floor') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_floor'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 2_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('floors_number') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_floors_number'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		            <div class="form-group template_info 2_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('state') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_state'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             
		             <!-- electronics -->
		             
		              <div class="form-group template_info 4_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('size') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_size'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <!-- job postions -->
		             
		             <div class="form-group template_info 8_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('education') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_education_name'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 8_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('certificate') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_certificate_name'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		              <div class="form-group template_info 8_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('schedule') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_schedule_name'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 8_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('experience') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_experience'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		             <div class="form-group template_info 8_info">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('salary') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_salary'><?php echo $this->lang->line('not_set') ?></label>
		      	         </div>
		             </div>
		             
		           </form>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">
                   <form id="seller_info_ad" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
                   	   <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('seller_name') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_seller_name'></label>
		      	         </div>
		              </div>
		              <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('seller_phone') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_seller_phone'></label>
		      	         </div>
		              </div>
                   </form>
                </div>
              </div>
            </div>
        </div>
        <input type="hidden"  id="post_id"/>
        <div class="modal-footer">
          <div class="pull-left">
          	 <!-- <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
          </div>
          <label  style="display: none" id="reject_note_label"></label>
          <label  style="display: none" id="expiry_edit_label"><?php echo $this->lang->line('expiry_edit_note') ?></label>
          <button style="display: none" template_id id="delete_ad_btn" type="button" class="btn btn-danger" data-toggle="modal" data-target=".ad-delete-modal"><span class="fa fa-trash"></span></button>
          <button style="display: none" onclick="perform_action('hide')" id="hide_btn" type="button" class="btn btn-warning"><?php echo $this->lang->line('hide_ad') ?></button>
          <button style="display: none" onclick="perform_action('show')" id="show_btn" type="button" class="btn btn-warning"><?php echo $this->lang->line('show_ad') ?></button>
          <button style="display: none" id="reject_btn"  type="button" class="btn btn-danger" data-toggle="modal" data-target=".reject_model"><?php echo $this->lang->line('reject_ad') ?></button>
          <button style="display: none"  onclick="perform_action('accept')"  id="accept_btn" type="button" class="btn btn-success"><?php echo $this->lang->line('accept_ad') ?></button>
          
        </div>
      </div>
    </div>
  </div>
  <?php $this->load->view('admin/ads/reject_ad_modal') ?>
  
   <!-- confirmation modal   -->
  <div id="ad-delete-modal" class="modal fade ad-delete-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
            	<?php echo $this->lang->line('confirmation') ?>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-center">
                <h6><?php echo $this->lang->line('delete_ad_confirmation') ?></h6>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn button2 submit" onclick="perform_action('delete')"><?php echo $this->lang->line('yes') ?></button>
            </div>
        </div>
    </div>
  </div>