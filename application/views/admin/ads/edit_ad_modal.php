	<!--edit ad modal-->
	<div id="edit-ad-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="error-message full d-none"></div>
					<form id="edit-ad-form">
						<div class="row">
							<div class="col-sm-6 border-middle">
								<input type="hidden" name="ad_id" class="ad-id">
								<input type="hidden" name="location_id" class="location-id">
								<input type="hidden" class="ad-status">
								<input type="hidden" class="template-id">

								<div class="form-group">
									<input type="text" class="form-control" name="title" placeholder="<?php echo $this->lang->line('item_name'); ?>" required>
								</div>

								<div class="form-group">
									<select name="city_id" class="city-select" required placeholder="<?php echo $this->lang->line('select_city'); ?>">
									  <option disabled selected value="" class="d-none">
								    </select>
								</div>

								<div class="form-group">
									<select name="location_id" class="location-select">
									  <option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_location'); ?></option>
								    </select>
								</div>

								<div class="form-group">
								   <select name="show_period" class="period-select" required placeholder="<?php echo $this->lang->line('show_period'); ?>">
									<option disabled selected value="" class="d-none">
								   </select>
								</div>

								<div class="form-group">
									<input type="text" class="form-control number" name="price" placeholder="<?php echo $this->lang->line('item_price'); ?>" required>
								</div>

								<div class="form-group">
									<label class="">
									<input type='hidden' value='0' name='is_negotiable'>
							<input type="checkbox" name="is_negotiable" value="1"><span class=""> <?php echo $this->lang->line('negotiable'); ?></span>
						</label>
								</div>

								<div class="form-group">
									<textarea class="form-control" name="description" rows="4" placeholder="<?php echo $this->lang->line('add_description'); ?>" required></textarea>
								</div>

							</div>
							<div class="col-sm-6">
							<div class="form-group d-none field type_name">
								<select name="type_id" class="type-select" placeholder="<?php echo $this->lang->line('select_type'); ?>">
									<option disabled selected value="" class="d-none">
								</select>
							</div>
							
							<div class="form-group d-none field type_model_name">
								<select name="type_model_id" class="model-select">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_model'); ?></option>
								</select>
							</div>

								<!--vehicles template-->
								<!--type id/ type model id-->
								<div class="template-vehicles template d-none" data-template-id="1">

									<div class="form-group field engine_capacity">
										<select name="engine_capacity" class="engine-capacity-select" placeholder="<?php echo $this->lang->line('engine_capacity'); ?>">
									<option disabled selected value="" class="d-none">
									</select>
									</div>
									<div class="form-group field is_automatic">
										<select name="is_automatic" class="automatic-select" placeholder="<?php echo $this->lang->line('select_motion'); ?>">
										<option disabled selected value="" class="d-none">
										<option value="1"><?php echo $this->lang->line('automatic'); ?></option>
										<option value="0"><?php echo $this->lang->line('manual'); ?></option>
									</select>
									</div>

									<div class="form-group field is_new">
										<select name="is_new" class="status-select" placeholder="<?php echo $this->lang->line('select_status'); ?>">
											<option disabled selected value="" class="d-none">
											<option value="1"><?php echo $this->lang->line('new'); ?></option>
											<option value="0"><?php echo $this->lang->line('old'); ?></option>
										</select>
									</div>
									
									<div class="form-group field manufacture_date">
										<input type="text" class="form-control" name="manufacture_date" placeholder="<?php echo $this->lang->line('manufacture_date'); ?>" data-toggle="datepicker">
									</div>
									<div class="form-group field kilometer">
										<input type="text" class="form-control number" name="kilometer" placeholder="<?php echo $this->lang->line('kilometers'); ?>">
									</div>

								</div>
								<!--properties template-->
								<div class="template-properties template d-none" data-template-id="2">
									<div class="form-group field space">
										<input type="text" class="form-control number" name="space" placeholder="<?php echo $this->lang->line('space'); ?>">
									</div>

									<div class="form-group field rooms_num">
										<input type="number" class="form-control" name="rooms_num" placeholder="<?php echo $this->lang->line('rooms'); ?>">
									</div>
								
									<div class="form-group field floor">
										<input type="number" class="form-control" name="floor" placeholder="<?php echo $this->lang->line('floor'); ?>">
									</div>
									
									<div class="form-group field floors_number">
										<input type="number" class="form-control" name="floors_number" placeholder="<?php echo $this->lang->line('floors_number'); ?>">
									</div>

									<div class="form-group field property_state_name">
										<select name="property_state_id" class="property-state-select" placeholder="<?php echo $this->lang->line('state'); ?>">
											<option disabled selected value="" class="d-none">
										</select>
									</div>

									<div class="form-group field with_furniture">
										<label class="">
											<input type='hidden' value='0' name='with_furniture'>
											<input type="checkbox" name="with_furniture" value="1"><span class=""> <?php echo $this->lang->line('with_furniture'); ?></span>
										</label>
									</div>
								</div>
								<!--mobiles template-->
								<!--type id-->
								<div class="template-mobiles template d-none" data-template-id="3">

									<div class="form-group field is_new">
										<select name="is_new" class="status-select" placeholder="<?php echo $this->lang->line('select_status'); ?>">
											<option disabled selected value="" class="d-none">
											<option value="1"><?php echo $this->lang->line('new'); ?></option>
											<option value="0"><?php echo $this->lang->line('old'); ?></option>
										</select>
									</div>
								</div>
								<!--electronics template-->
								<!--type id-->
								<div class="template-electronics template d-none" data-template-id="4">

									<div class="form-group field size">
										<input type="number" step="any" class="form-control" name="size" placeholder="<?php echo $this->lang->line('size'); ?>">
									</div>

									<div class="form-group field is_new">
										<select name="is_new" class="status-select" placeholder="<?php echo $this->lang->line('select_status'); ?>">
											<option disabled selected value="" class="d-none">
											<option value="1"><?php echo $this->lang->line('new'); ?></option>
											<option value="0"><?php echo $this->lang->line('old'); ?></option>
										</select>
									</div>
								</div>
								<!--fashion template-->
								<div class="template-fashion template d-none" data-template-id="5">
									<div class="form-group field is_new">
										<select name="is_new" class="status-select" placeholder="<?php echo $this->lang->line('select_status'); ?>">
											<option disabled selected value="" class="d-none">
											<option value="1"><?php echo $this->lang->line('new'); ?></option>
											<option value="0"><?php echo $this->lang->line('old'); ?></option>
										</select>
									</div>
								</div>
								<!--kids template-->
								<div class="template-kids template d-none" data-template-id="6">
									<div class="form-group field is_new">
										<select name="is_new" class="status-select" placeholder="<?php echo $this->lang->line('select_status'); ?>">
											<option disabled selected value="" class="d-none">
											<option value="1"><?php echo $this->lang->line('new'); ?></option>
											<option value="0"><?php echo $this->lang->line('old'); ?></option>
										</select>
									</div>
								</div>
								<!--sports template-->
								<div class="template-sports template d-none" data-template-id="7">
									<div class="form-group field is_new">
										<select name="is_new" class="status-select" placeholder="<?php echo $this->lang->line('select_status'); ?>">
											<option disabled selected value="" class="d-none">
											<option value="1"><?php echo $this->lang->line('new'); ?></option>
											<option value="0"><?php echo $this->lang->line('old'); ?></option>
										</select>
									</div>
								</div>
								<!--job positions template-->
								<!--schedule id/experience id/education id-->
								<div class="template-job template d-none" data-template-id="8">
									<div class="form-group field education_name">
										<select name="education_id" class="educations-select" placeholder="<?php echo $this->lang->line('education'); ?>">
										<option disabled selected value="" class="d-none">
									</select>
									</div>

									<div class="form-group field certificate_name">
										<select name="certificate_id" class="certificates-select" placeholder="<?php echo $this->lang->line('certificate'); ?>">
										<option disabled selected value="" class="d-none">
									</select>
									</div>
									
									<div class="form-group field schedule_name">
										<select name="schedule_id" class="schedules-select" placeholder="<?php echo $this->lang->line('schedule'); ?>">
										<option disabled selected value="" class="d-none">
									</select>
									</div>

								<div class="form-group field gender">
										<select name="gender" class="gender-select" placeholder="<?php echo $this->lang->line('gender'); ?>">
										<option disabled selected value="" class="d-none">
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('male'); ?></option>
										<option value="2"><?php echo $this->lang->line('female'); ?></option>
									</select>
									</div>
									
									<div class="form-group field experience">
										<input type="text" class="form-control" name="experience" placeholder="<?php echo $this->lang->line('experience'); ?>">
									</div>

									<div class="form-group field salary">
										<input type="text" class="form-control number" name="salary" placeholder="<?php echo $this->lang->line('salary'); ?>">
									</div>
								</div>

								<!--industries template-->
								<div class="template-industries template d-none" data-template-id="9">
									<div class="form-group field is_new">
										<select name="is_new" class="status-select" placeholder="<?php echo $this->lang->line('select_status'); ?>">
											<option disabled selected value="" class="d-none">
											<option value="1"><?php echo $this->lang->line('new'); ?></option>
											<option value="0"><?php echo $this->lang->line('old'); ?></option>
										</select>
									</div>
								</div>
								<!--services template-->
								<div class="template-services template d-none" data-template-id="10"></div>

								<!--basic template-->
								<div class="template-basic template d-none" data-template-id="11"></div>

								<div class="ad-images">

								</div>

								<script id="ad-edit-images-template" type="text/template">
									{{#main_image}}
									<div class="main-img">
										<div class="">
											<?php echo $this->lang->line('main_image'); ?>:</div>
										<div class="image-wrapper main-image" data-url="{{main_image}}">
											<img src="<?php echo base_url('{{main_image}}'); ?>" alt="" width="100px" height="100px">
											<button class="btn btn-danger delete" type="button"><?php echo $this->lang->line('delete'); ?></button>
										</div>
									</div>
									{{/main_image}}

									<div class="secondary-imgs">
										<div class="">
											<?php echo $this->lang->line('other_images'); ?>:</div>
										{{#images}}
										<div class="image-wrapper" data-url="{{image}}">
											<img src="<?php echo base_url('{{image}}'); ?>" alt="" width="100px" height="100px">
											<button class="btn btn-danger delete" type="button"><?php echo $this->lang->line('delete'); ?></button>
										</div>
										{{/images}}
									</div>

									{{#main_video}}
									<div class="main-video">
										<div class="">
											<?php echo $this->lang->line('video'); ?>:</div>
										<div class="image-wrapper video" data-url="{{main_video}}">
											<img src="<?php echo base_url('assets/images/default_ad/video.png'); ?>" alt="" width="100px" height="100px">
											<button class="btn btn-danger delete" type="button"><?php echo $this->lang->line('delete'); ?></button>
										</div>
									</div>
									{{/main_video}}
								</script>
								<div id="fileuploader-edit-ad-main"></div>
								<div id="fileuploader-edit-ad"></div>
								<div id="fileuploader-edit-ad-video" class="d-none"></div>

								<div class="">
									<label class="">
									<input type='hidden' value='0' name='ad_visible_phone'>
									<input type="checkbox" name="ad_visible_phone" value="1"><span class=""> <?php echo $this->lang->line('ad_visible_phone'); ?> <?php echo $this->session->userdata('PHP_AUTH_USER')?></span>
									<div class="visible-phone-note"><?php echo $this->lang->line('ad_visible_phone_note'); ?></div>
								</label>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('edit_ad'); ?></button>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>
	<!--confirm edit ad modal modal-->
	<div id="confirm-edit-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body text-center">
					<h6 class="text">
						<?php echo $this->lang->line('confirm_edit'); ?>
					</h6>
					<div class="modal-footer">
						<button class="btn button2 submit"><?php echo $this->lang->line('yes'); ?></button>
					</div>
				</div>

			</div>
		</div>
	</div>