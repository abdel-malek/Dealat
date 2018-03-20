<body class="profile-page">
	<?php $this->load->view('website/common'); ?>

	<section class="user-details mt-4 mb-2">
		<div class="container">
			<div class="row first">
				<script id="user-info-template" type="text/template">
					<div class="col-md-2">
						<div class="default-img m-auto" style="background-color: #fff">
							<div class="image" style="background-image: url('<?php echo base_url('{{personal_image}}'); ?>')">
							</div>
						</div>

					</div>
					<div class="col-md-2 text-center mt-2">
						<div class="name">{{name}}</div>
						<!--<fieldset class="rating">
							<span class="rate-group" data-value="5">
							<input type="radio" id="star5" name="rating" value="5" />
							<label class="full" for="star5" title="Awesome - 5 stars"></label>
						</span>

							<div class="rate-group" data-value="4">
								<input type="radio" id="star4" name="rating" value="4" />
								<label class="full" for="star4" title="Pretty good - 4 stars"></label>
							</div>

							<div class="rate-group" data-value="3">
								<input type="radio" id="star3" name="rating" value="3" />
								<label class="full" for="star3" title="Meh - 3 stars"></label>
							</div>

							<div class="rate-group" data-value="2">
								<input type="radio" id="star2" name="rating" value="2" />
								<label class="full" for="star2" title="Kinda bad - 2 stars"></label>
							</div>

							<div class="rate-group" data-value="1">
								<input type="radio" id="star1" name="rating" value="1" /><label class="full" for="star1" title="Sucks big time - 1 star"></label>
							</div>

						</fieldset>
						<div class="clearfix"></div>-->
					</div>
					<div class="col-md-5 offset-md-1 mt-2">
						<div class="location"><span class="location-lbl">Location: </span><span class="location-val">{{city_name}}</span></div>
						<div class="phone"><span class="phone-lbl">Phone: </span><span class="phone-val">{{phone}}</span></div>
						<div class="email"><span class="email-lbl">Email: </span><span class="email-val">{{email}}</span></div>
					</div>
					<div class="col-md-2 mt-2">
						<button class="btn button2 edit-user-info">Edit Info</button>
					</div>
				</script>
			</div>
		</div>
	</section>
	<div class="container">
		<div id="profile-tabs" class="profile-tabs">
			<ul class='etabs'>
				<!--
			<div class="row">
<div class="col">
	<li class='tab'><a href="#my-ads">My Ads</a></li>
</div>
<div class="col">
	<li class='tab'><a href="#favorites">Favorites</a></li>
</div>
<div class="col">
	<li class='tab'><a href="#chats">Chats</a></li>
</div>
</div>
-->
				<li class='tab'><a href="#user-ads">My Ads</a></li>
				<li class='tab'><a href="#favorites">Favorites</a></li>
				<li class='tab'><a href="#chats">Chats</a></li>
			</ul>
			<div id="user-ads" class="user-ads">
				<section class="products">
					<div class="container main">
						<div class="row no-gutters first">
							<script id="user-ads-template" type="text/template">
								<div class="col-12">
									<div class="card mb-4" data-ad-id="{{ad.ad_id}}" data-template-id="{{ad.tamplate_id}}" data-status-id="{{ad.status}}">
										<div class="container">
											<div class="overlay">
												<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
											</div>
											<div class="row no-gutters">
												<div class="col-md-6">
													<div class="card-left">
														<div class="card-img-top" style="background-image: url('<?php echo base_url('{{ad.main_image}}'); ?>')"></div>

														<div class="price">
															<div class="price-val">{{ad.price}}
																<?php echo $this->lang->line('sp'); ?>
															</div>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="card-body">
														<div class="card-title mb-1">{{ad.title}}</div>
														<div class="details mb-2">{{ad.description}}</div>
														<div class="location"><span class="location-lbl">Location: </span><span class="location-val">{{ad.city_name}}- {{ad.location_name}}</span></div>

														<div class="date"><span class="date-lbl">Published </span><span class="date-val">{{date}}</span></div>
														<div class="date"><span class="date-lbl">Expires </span><span class="date-val">---</span></div>
														<div class="negotiable"><span class="negotiable-lbl">Price: </span><span class="negotiable-val">{{negotiable}}</span></div>
														<div class="btn button2 delete-ad mt-2 mr-2"><span>Delete </span><i class="far fa-trash-alt fa-lg"></i></div>
														<div class="btn button2 edit-ad mt-2"><span>Edit </span><i class="fas fa-pencil-alt fa-lg"></i></div>
													</div>
													<div class="status"><span class="status-val">{{status}} </span>
														<span class="status-icon d-none" data-status-id="1"><i class="far fa-pause-circle"></i></span>
														<span class="status-icon d-none" data-status-id="2"><i class="far fa-check-circle"></i></span>
														<span class="status-icon d-none" data-status-id="3"><i class="fas fa-history"></i></span>
														<span class="status-icon d-none" data-status-id="4"><i class="far fa-eye-slash"></i></span>
														<span class="status-icon d-none" data-status-id="5"><i class="fas fa-ban"></i></span>
														<span class="status-icon d-none" data-status-id="6"><i class="far fa-trash-alt"></i></span>

													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</script>
						</div>
					</div>
				</section>
			</div>

			<div id="favorites" class="favorites">
				<section class="products">

					<div class="container-fluid main">
						<div class="row first">
							<script id="user-fav-template" type="text/template">
								<div class="col-md-6">
									<div class="card mb-4" data-ad-id="{{ad.ad_id}}" data-template-id="{{ad.tamplate_id}}">
										<div class="container">

											<div class="row no-gutters">
												<div class="col-md-6">
													<div class="card-left">
														<div class="overlay">
															<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
														</div>
														<div class="card-img-top" style="background-image: url('<?php echo base_url('{{ad.main_image}}'); ?>')"></div>

														<div class="price">
															<div class="price-val">{{ad.price}}
																<?php echo $this->lang->line('sp'); ?>
															</div>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="card-body">
														<div class="card-title mb-1">{{ad.title}}</div>
														<div class="details mb-2">{{ad.description}}</div>
														<div class="location"><span class="location-lbl">Location: </span><span class="location-val">{{ad.city_name}}- {{ad.location_name}}</span></div>

														<div class="date"><span class="date-lbl">Published: </span><span class="date-val">{{date}}</span></div>
														<div class="negotiable"><span class="negotiable-lbl">Price: </span><span class="negotiable-val">{{negotiable}}</span></div>
														<div class="seller"><span class="seller-lbl">Seller: </span><span class="seller-val">Jhon Doe</span></div>
													</div>

												</div>
											</div>
										</div>
									</div>
								</div>
							</script>
						</div>
					</div>
				</section>
			</div>

			<div id="chats" class="chats">
				<div class="container">
					<ul>
						<li>
							<div class="row">
								<div class="col-2">
									<div class="chat-img">
										<img src="<?php echo base_url('assets/images/Dealat%20logo%20white%20background.png'); ?>" width="50px" alt="">
									</div>
								</div>
								<div class="col-8">
									<div class="chat-name">John Doe</div>
								</div>
								<div class="col-2">
									<div class="chat-time">08:30 pm</div>
								</div>
							</div>
						</li>
						<hr>
					</ul>
				</div>
			</div>
		</div>
	</div>

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
							<div class="col-sm-6">
								<input type="hidden" name="ad_id" class="ad-id">
								<input type="hidden" name="location_id" class="location-id">
								<input type="hidden" name="type_id" class="type-id">
								<input type="hidden" name="type_model_id" class="type-model-id">

								<div class="form-group">
									<input type="text" class="form-control" name="title" placeholder="<?php echo $this->lang->line('item_name'); ?>" required>
								</div>

								<div class="form-group">
									<nav class="navbar navbar-expand-md navbar-light bg-light locations-nav">
										<ul class="navbar-nav">
											<li class="nav-item dropdown">
												<a class="nav-link dropdown-toggle select" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $this->lang->line('item_location'); ?></a>
												<ul class="dropdown-menu cities main-dropdown">

												</ul>
											</li>
										</ul>
									</nav>
								</div>

								<div class="form-group">
									<select name="show_period" class="period-select" required>
							<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('show_period'); ?></option>
							<option value="1"><?php echo $this->lang->line('week'); ?></option>
							<option value="2"><?php echo $this->lang->line('10_days'); ?></option>
							<option value="3"><?php echo $this->lang->line('month'); ?></option>
						</select>
								</div>

								<div class="form-group">
									<!--todo display currency/ take only numbers-->
									<input type="number" class="form-control" name="price" placeholder="<?php echo $this->lang->line('item_price'); ?>" required>
								</div>

								<div class="form-group">
									<label class="">
							<input type="checkbox" name="is_negotiable" value="1"><span class=""> <?php echo $this->lang->line('negotiable'); ?></span>
						</label>
								</div>

								<div class="form-group">
									<textarea class="form-control" name="description" rows="4" placeholder="<?php echo $this->lang->line('add_description'); ?>"></textarea>
								</div>

							</div>
							<div class="col-sm-6">
								<div class="form-group d-none">
									<nav class="navbar navbar-expand-md navbar-light bg-light types-nav">
										<ul class="navbar-nav">
											<li class="nav-item dropdown">
												<a class="nav-link dropdown-toggle select" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $this->lang->line('select_type'); ?></a>
												<ul class="dropdown-menu types main-dropdown">
												</ul>
											</li>
										</ul>
									</nav>
								</div>

								<!--vehicles template-->
								<!--type id/ type model id-->
								<div class="template-vehicles template d-none" data-template-id="1">

									<div class="form-group">
										<input type="text" class="form-control" name="manufacture_date" placeholder="<?php echo $this->lang->line('manufacture_date'); ?>" data-toggle="datepicker">
									</div>
									<div class="form-group">
										<input type="number" class="form-control" name="kilometer" placeholder="<?php echo $this->lang->line('kilometers'); ?>">
									</div>
									<!--						<div class="form-group">-->
									<label class="">
								<input type="checkbox" name="is_automatic" value="1"><span class=""> <?php echo $this->lang->line('automatic'); ?></span>
							</label>
									<!--						</div>-->
									<br>
									<div class="form-group">
										<label class="">
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>

								</div>
								<!--properties template-->
								<div class="template-properties template d-none" data-template-id="2">
									<div class="form-group">
										<input type="text" class="form-control" name="space" placeholder="<?php echo $this->lang->line('space'); ?>">
									</div>

									<div class="form-group">
										<input type="number" class="form-control" name="rooms_num" placeholder="<?php echo $this->lang->line('rooms'); ?>">
									</div>

									<div class="form-group">
										<input type="number" class="form-control" name="floor" placeholder="<?php echo $this->lang->line('floor'); ?>">
									</div>

									<div class="form-group">
										<input type="text" class="form-control" name="state" placeholder="<?php echo $this->lang->line('state'); ?>">
									</div>

									<div class="form-group">
										<label class="">
											<input type='hidden' value='0' name='with_furniture'>
											<input type="checkbox" name="with_furniture" value="1"><span class=""> <?php echo $this->lang->line('with_furniture'); ?></span>
										</label>
									</div>
								</div>
								<!--mobiles template-->
								<!--type id-->
								<div class="template-mobiles template d-none" data-template-id="3">

									<div class="form-group">
										<label class="">
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--electronics template-->
								<!--type id-->
								<div class="template-electronics template d-none" data-template-id="4">

									<div class="form-group">
										<input type="text" class="form-control" name="size" placeholder="<?php echo $this->lang->line('size'); ?>">
									</div>

									<div class="form-group">
										<label class="">
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--fashion template-->
								<div class="template-fashion template d-none" data-template-id="5">
									<div class="form-group">
										<label class="">
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--kids template-->
								<div class="template-kids template d-none" data-template-id="6">
									<div class="form-group">
										<label class="">
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--sports template-->
								<div class="template-sports template d-none" data-template-id="7">
									<div class="form-group">
										<label class="">
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--job positions template-->
								<!--schedule id/experience id/education id-->
								<div class="template-job template d-none" data-template-id="8">
									<div class="form-group">
										<select name="schedule_id" class="schedules-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('schedule'); ?></option>
									</select>
									</div>

									<div class="form-group">
										<select name="education_id" class="educations-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('education'); ?></option>
									</select>
									</div>

									<div class="form-group">
										<input type="text" class="form-control" name="experience" placeholder="<?php echo $this->lang->line('experience'); ?>">
									</div>

									<div class="form-group">
										<input type="text" class="form-control" name="salary" placeholder="<?php echo $this->lang->line('salary'); ?>">
									</div>
								</div>

								<!--industries template-->
								<div class="template-industries template d-none" data-template-id="9">
									<div class="form-group">
										<label class="">
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--services template-->
								<div class="template-services template d-none" data-template-id="10"></div>

								<!--basic template-->
								<div class="template-basic template d-none" data-template-id="11"></div>

								<!--							<div id="fileuploader-ad">Upload</div>-->

							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('submit_ad'); ?></button>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>

	<!--edit user info modal-->
	<div id="edit-user-info-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="edit-user-info-form">
						<input type="hidden" name="location_id" class="location-id">
						<div class="form-group">
							<select name="city_id" class="city-select">
								<option value="" class="placeholder d-none" selected>select location</option>
							</select>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="name" placeholder="<?php echo $this->lang->line('name'); ?>">
						</div>
						<div class="form-group">
							<input type="email" class="form-control" name="email" placeholder="<?php echo $this->lang->line('email'); ?>">
						</div>
						<!--
						<div class="form-group">
							<input type="text" class="form-control" name="phone" placeholder="<?php //echo $this->lang->line('phone'); ?>">
						</div>
-->
						<div class="modal-footer">
							<button type="submit" class="btn button2 submit">Update</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!--delete modal-->
	<div id="delete-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body text-center">
					<h6>Are you sure you want to delete this ad?</h6>
					<form id="delete-ad-form">
						<input type="hidden" class="ad-id" name="ad_id">
						<input type="hidden" class="status-id" name="status">
						<div class="modal-footer">
							<button type="submit" class="btn button2 submit">Yes</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!--<script src="<?php echo base_url('assets/js/profile.js'); ?>"></script>-->
