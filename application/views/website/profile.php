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
						<div class="location"><span class="location-lbl"><?php echo $this->lang->line('location'); ?>: </span><span class="location-val">{{city_name}}</span></div>
						<div class="phone"><span class="phone-lbl"><?php echo $this->lang->line('phone'); ?>: </span><span class="phone-val">{{phone}}</span></div>
						{{#email}}
						<div class="email"><span class="email-lbl"><?php echo $this->lang->line('email'); ?>: </span><span class="email-val">{{email}}</span></div>{{/email}}
					</div>
					<div class="col-md-2 mt-2">
						<button class="btn button2 edit-user-info"><?php echo $this->lang->line('edit_info'); ?></button>
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
				<li class='tab'><a href="#user-ads"><?php echo $this->lang->line('my_ads'); ?></a></li>
				<li class='tab'><a href="#favorites"><?php echo $this->lang->line('favorites'); ?></a></li>
				<li class='tab'><a href="#chats" class="chat-tab-link"><?php echo $this->lang->line('chats'); ?><div class="new-msg d-none"><i class="fas fa-envelope"></i></div></a>

				</li>
				<li class='tab'><a href="#bookmarks"><?php echo $this->lang->line('saved_searches'); ?> </a></li>
			</ul>
			<div id="user-ads" class="user-ads">
				<section class="products">
					<div class="container main">
						<div class="row no-gutters first">
							<script id="user-ads-template" type="text/template">
								<div class="col-12">
									<div class="card mb-4" data-ad-id="{{ad.ad_id}}" data-template-id="{{ad.tamplate_id}}" data-status-id="{{ad.status}}" data-category-id="{{ad.category_id}}">
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
														<!--<div class="details mb-2">{{ad.description}}</div>-->
														<div class="category"><span class="category-lbl"><?php echo $this->lang->line('category'); ?>: </span><span class="category-val">{{ad.category_name}}</span></div>
														<div class="location"><span class="location-lbl"><?php echo $this->lang->line('location'); ?>: </span><span class="location-val">{{ad.city_name}}- {{ad.location_name}}</span></div>

														{{#publish}}
														<div class="date"><span class="date-lbl"><?php echo $this->lang->line('publish_date'); ?>: </span><span class="date-val">{{publish}}</span></div>{{/publish}} {{#expiry}}
														<div class="date"><span class="date-lbl"><?php echo $this->lang->line('expiry_date'); ?>: </span><span class="date-val">{{expiry}}</span></div>{{/expiry}} {{#reject_note}}
														<div class="reject"><span class="reject-lbl"><?php echo $this->lang->line('rejection_reason'); ?>: </span><span class="reject-val">{{reject_note}}</span></div>{{/reject_note}}
														<div class="btn button2 delete-ad mt-2 mr-2"><span><?php echo $this->lang->line('delete'); ?> </span><i class="far fa-trash-alt fa-lg"></i></div>
														<div class="btn button2 edit-ad mt-2"><span><?php echo $this->lang->line('edit'); ?> </span><i class="fas fa-pencil-alt fa-lg"></i></div>
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
															<div class="text"><i class="fas fa-info-circle"></i>
																<?php echo $this->lang->line('view_details'); ?>
															</div>
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
														<div class="location"><span class="location-lbl"><?php echo $this->lang->line('location'); ?>: </span><span class="location-val">{{ad.city_name}}- {{ad.location_name}}</span></div>

														<div class="date"><span class="date-lbl"><?php echo $this->lang->line('publish_date'); ?>: </span><span class="date-val">{{date}}</span></div>
														<div class="negotiable"><span class="negotiable-lbl"><?php echo $this->lang->line('price'); ?>: </span><span class="negotiable-val">{{negotiable}}</span></div>
														<div class="seller"><span class="seller-lbl"><?php echo $this->lang->line('seller_name'); ?>: </span><span class="seller-val">Jhon Doe</span></div>
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
					<ul class="sessions">

						<script id="chat-sessions-template" type="text/template">

							<li class="session" data-username="{{username}}" data-adname="{{details.ad_title}}" data-session-id="{{details.chat_session_id}}" data-ad-id="{{details.ad_id}}" data-seller-id="{{details.seller_id}}">
								<div class="row">
									<div class="col-2">
										<div class="chat-img text-center">
											<img src="<?php echo base_url('{{image}}'); ?>" width="50px" alt="">
										</div>
									</div>
									<div class="col-3">
										<div class="username">{{username}}</div>
									</div>
									<div class="col-4">
										<div class="chat-name">{{details.ad_title}}</div>
									</div>
								</div>
							</li>
							<hr>

						</script>

					</ul>
				</div>
			</div>

			<div id="bookmarks" class="bookmarks">
				<ul class="bookmarks-list">
					<script id="saved-bookmarks-template" type="text/template">

						<li class="bookmark" data-bookmark-id="{{user_bookmark_id}}" data-filter="{{filter}}">
							<div class="row">
								<div class="col-sm-8 details">
									{{# query.search}}
									<div class="search"><span class="search-lbl"><?php echo $this->lang->line('search'); ?>: </span><span class="search-val">{{query.search}}</span></div>{{/query.search}} {{#query.category_name}}
									<div class="category"><span class="category-lbl"><?php echo $this->lang->line('category'); ?>: </span><span class="query.category-val">{{query.category_name}}</span></div>{{/query.category_name}} {{#query.city_name}}
									<div class="city"><span class="city-lbl"><?php echo $this->lang->line('city'); ?>: </span><span class="city-val">{{query.city_name}}</span></div>{{/query.city_name}} {{#query.location_name}}
									<div class="location"><span class="location-lbl"><?php echo $this->lang->line('location'); ?>: </span><span class="location-val">{{query.location_name}}</span></div>{{/query.location_name}} {{#query.price_min}}
									<div class="price"><span class="price-lbl"><?php echo $this->lang->line('price'); ?>: </span><span class="price-val"><?php echo $this->lang->line('from'); ?>: {{query.price_min}}<?php echo $this->lang->line('sp'); ?> <?php echo $this->lang->line('to'); ?>: {{query.price_max}}<?php echo $this->lang->line('sp'); ?></span></div>{{/query.price_min}} {{#query.type_name}}
									<div class="type "><span class="type-lbl"><?php echo $this->lang->line('type'); ?>: </span><span class="type-val">{{query.type_name}}</span></div>{{/query.type_name}} {{#query.type_model_name}}
									<div class="model "><span class="model-lbl"><?php echo $this->lang->line('type_model'); ?>: </span><span class="model-val">{{query.type_model_name}}</span></div>{{/query.type_model_name}} {{#query.years_name}}
									<div class="manufacture_date"><span class="manufacture_date-lbl"><?php echo $this->lang->line('manufacture_date'); ?>: </span><span class="manufacture_date-val">{{query.years_name}}</span></div>{{/query.years_name}} {{#query.automatic_name}}
									<div class="is_automatic"><span class="is_automatic-lbl"><?php echo $this->lang->line('motion'); ?>: </span><span class="is_automatic-val">{{query.automatic_name}}</span></div>{{/query.automatic_name}} {{#query.state_name}}
									<div class="is_new"><span class="is_new-lbl"></span>
										<?php echo $this->lang->line('item_status'); ?>: <span class="is_new-val">{{query.state_name}}</span></div>{{/query.state_name}} {{#query.kilometer_min}}
									<div class="kilometer"><span class="kilometer-lbl"><?php echo $this->lang->line('kilometrage'); ?>: </span><span class="kilometer-val">from: {{query.kilometer_min}} to: {{query.kilometer_max}}</span></div>{{/query.kilometer_min}} {{#query.space_min}}

									<div class="space"><span class="space-lbl"><?php echo $this->lang->line('space'); ?>: </span><span class="space-val"><?php echo $this->lang->line('from'); ?>: {{query.space_min}} <?php echo $this->lang->line('to'); ?>: {{query.space_max}}</span></div>{{/query.space_min}} {{#query.rooms_num_min}}
									<div class="rooms_num"><span class="rooms_num-lbl"><?php echo $this->lang->line('rooms_num'); ?>:</span><span class="rooms_num-val"><?php echo $this->lang->line('from'); ?>: {{query.rooms_num_min}} <?php echo $this->lang->line('to'); ?>: {{query.rooms_num_max}}</span></div>{{/query.rooms_num_min}} {{#query.floor_min}}
									<div class="floor"><span class="floor-lbl"><?php echo $this->lang->line('floor'); ?>: </span><span class="floor-val">from: {{query.floor_min}} to: {{query.floor_max}}</span></div>{{/query.floor_min}} {{#query.furniture_name}}
									<div class="with_furniture"><span class="with_furniture-lbl"><?php echo $this->lang->line('with_furniture'); ?>: </span><span class="with_furniture-val">{{query.furniture_name}}</span></div>{{/query.furniture_name}} {{#query.size_min}}

									<div class="size"><span class="size-lbl"><?php echo $this->lang->line('size'); ?>: </span><span class="size-val"><?php echo $this->lang->line('from'); ?>: {{query.size_min}} <?php echo $this->lang->line('to'); ?>: {{query.size_max}}</span></div>{{/query.size_min}} {{#query.schedule_name}}

									<div class="schedule schedule_name"><span class="schedule-lbl"><?php echo $this->lang->line('schedule'); ?>:</span><span class="schedule-val">{{query.schedule_name}}</span></div>{{/query.schedule_name}} {{#query.education_name}}
									<div class="education education_name"><span class="education-lbl"><?php echo $this->lang->line('education'); ?>: </span><span class="education-val">{{query.education_name}}</span></div>{{/query.education_name}} {{#query.salary_min}}

									<div class="salary"><span class="salary-lbl"><?php echo $this->lang->line('salary'); ?>: </span><span class="salary-val"><?php echo $this->lang->line('from'); ?>: {{query.salary_min}} <?php echo $this->lang->line('to'); ?>: {{query.salary_max}}</span></div>{{/query.salary_min}}
								</div>
								<div class="col-6 col-sm-2 text-center">
									<div class="show">
										<button class="btn button2 show"><?php echo $this->lang->line('show'); ?></button>
									</div>
								</div>
								<div class="col-6 col-sm-2 text-center">
									<div class="remove"><button class="btn button2 remove"><?php echo $this->lang->line('delete'); ?></button></div>
								</div>
							</div>
						</li>
						<hr>
					</script>
				</ul>
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

								<!--
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
-->
								<div class="form-group">
									<select name="city_id" class="city-select" required>
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_city'); ?></option>
								</select>
								</div>

								<div class="form-group">
									<select name="location_id" class="location-select">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('item_location'); ?></option>
								</select>
								</div>

								<div class="form-group">
									<select name="show_period" class="period-select" required>
							<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('show_period'); ?></option>
<!--
							<option value="1"><?php echo $this->lang->line('week'); ?></option>
							<option value="2"><?php echo $this->lang->line('10_days'); ?></option>
							<option value="3"><?php echo $this->lang->line('month'); ?></option>
-->
						</select>
								</div>

								<div class="form-group">
									<!--todo display currency/ take only numbers-->
									<input type="number" class="form-control" name="price" placeholder="<?php echo $this->lang->line('item_price'); ?>" required>
								</div>

								<div class="form-group">
									<label class="">
									<input type='hidden' value='0' name='is_negotiable'>
							<input type="checkbox" name="is_negotiable" value="1"><span class=""> <?php echo $this->lang->line('negotiable'); ?></span>
						</label>
								</div>

								<div class="form-group">
									<textarea class="form-control" name="description" rows="4" placeholder="<?php echo $this->lang->line('add_description'); ?>"></textarea>
								</div>

							</div>
							<div class="col-sm-6">
								<div class="form-group d-none field type_name">
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

									<div class="form-group field manufacture_date">
										<input type="text" class="form-control" name="manufacture_date" placeholder="<?php echo $this->lang->line('manufacture_date'); ?>" data-toggle="datepicker">
									</div>
									<div class="form-group field kilometer">
										<input type="number" class="form-control" name="kilometer" placeholder="<?php echo $this->lang->line('kilometers'); ?>">
									</div>
									<!--						<div class="form-group field">-->
									<label class="field is_automatic">
									<input type='hidden' value='0' name='is_automatic'>
								<input type="checkbox" name="is_automatic" value="1"><span class=""> <?php echo $this->lang->line('automatic'); ?></span>
							</label>
									<!--						</div>-->
									<br>
									<div class="form-group field is_new">
										<label class="">
										<input type='hidden' value='0' name='is_new'>
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>

								</div>
								<!--properties template-->
								<div class="template-properties template d-none" data-template-id="2">
									<div class="form-group field" space>
										<input type="text" class="form-control" name="space" placeholder="<?php echo $this->lang->line('space'); ?>">
									</div>

									<div class="form-group field rooms_num">
										<input type="number" class="form-control" name="rooms_num" placeholder="<?php echo $this->lang->line('rooms'); ?>">
									</div>

									<div class="form-group field floor">
										<input type="number" class="form-control" name="floor" placeholder="<?php echo $this->lang->line('floor'); ?>">
									</div>

									<div class="form-group field state">
										<input type="text" class="form-control" name="state" placeholder="<?php echo $this->lang->line('state'); ?>">
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
										<label class="">
										<input type='hidden' value='0' name='is_new'>
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--electronics template-->
								<!--type id-->
								<div class="template-electronics template d-none" data-template-id="4">

									<div class="form-group field size">
										<input type="text" class="form-control" name="size" placeholder="<?php echo $this->lang->line('size'); ?>">
									</div>

									<div class="form-group field is_new">
										<label class="">
										<input type='hidden' value='0' name='is_new'>
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--fashion template-->
								<div class="template-fashion template d-none" data-template-id="5">
									<div class="form-group field is_new">
										<label class="">
										<input type='hidden' value='0' name='is_new'>
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--kids template-->
								<div class="template-kids template d-none" data-template-id="6">
									<div class="form-group field is_new">
										<label class="">
										<input type='hidden' value='0' name='is_new'>
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--sports template-->
								<div class="template-sports template d-none" data-template-id="7">
									<div class="form-group field is_new">
										<label class="">
										<input type='hidden' value='0' name='is_new'>
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--job positions template-->
								<!--schedule id/experience id/education id-->
								<div class="template-job template d-none" data-template-id="8">
									<div class="form-group field schedule_name">
										<select name="schedule_id" class="schedules-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('schedule'); ?></option>
									</select>
									</div>

									<div class="form-group field education_name">
										<select name="education_id" class="educations-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('education'); ?></option>
									</select>
									</div>

									<div class="form-group field experience">
										<input type="text" class="form-control" name="experience" placeholder="<?php echo $this->lang->line('experience'); ?>">
									</div>

									<div class="form-group field salary">
										<input type="text" class="form-control" name="salary" placeholder="<?php echo $this->lang->line('salary'); ?>">
									</div>
								</div>

								<!--industries template-->
								<div class="template-industries template d-none" data-template-id="9">
									<div class="form-group field is_new">
										<label class="">
										<input type='hidden' value='0' name='is_new'>
								<input type="checkbox" name="is_new" value="1"><span class=""> <?php echo $this->lang->line('new'); ?></span>
							</label>
									</div>
								</div>
								<!--services template-->
								<div class="template-services template d-none" data-template-id="10"></div>

								<!--basic template-->
								<div class="template-basic template d-none" data-template-id="11"></div>

								<div class="ad-images">

								</div>

								<script id="ad-edit-images-template" type="text/template">

									<div class="main-img">
										<div class="">Main image:</div>
										<div class="image-wrapper"  data-url="{{main_image}}">
											<img src="<?php echo base_url('{{main_image}}'); ?>" alt="" width="100px" height="100px">
											<button class="btn btn-danger delete" type="button">Delete</button>
										</div>
									</div>
									<div class="">Other images:</div>
									<div class="secondary-imgs">
										{{#images}}
										<div class="image-wrapper" data-url="{{image}}">
											<img src="<?php echo base_url('{{image}}'); ?>" alt="" width="100px" height="100px">
											<button class="btn btn-danger delete" type="button">Delete</button>
										</div>
										{{/images}}
									</div>
								</script>
								<!--							<div id="fileuploader-ad">Upload</div>-->
								<div id="fileuploader-edit-ad-main">Upload</div>
								<div id="fileuploader-edit-ad">Upload</div>
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

	<!--edit user info modal-->
	<div id="edit-user-info-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="error-message d-none text-left"></div>
					<form id="edit-user-info-form">
						<input type="hidden" name="location_id" class="location-id">
						<div id="fileuploader-register">Upload</div>
						<div class="form-group">
							<select name="city_id" class="city-select">
								<option value="" class="placeholder d-none" selected><?php echo $this->lang->line('select_location'); ?></option>
							</select>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="name" placeholder="<?php echo $this->lang->line('username'); ?>">
						</div>
						<div class="form-group">
							<input type="email" class="form-control" name="email" placeholder="<?php echo $this->lang->line('email'); ?>">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="birthday" data-toggle="birthdate" placeholder="<?php echo $this->lang->line('birthdate'); ?>">
						</div>

						<div class="form-group">
							<select name="gender" class="gender-select">
							<option value="" class="placeholder d-none" selected><?php echo $this->lang->line('gender'); ?></option>
							<option value="-1"><?php echo $this->lang->line('not_set'); ?></option>
							<option value="1"><?php echo $this->lang->line('male'); ?></option>
							<option value="2"><?php echo $this->lang->line('female'); ?></option>
						</select>
						</div>

						<div class="form-group">
							<input type="number" class="form-control whatsup" name="whatsup_number" placeholder="<?php echo $this->lang->line('whatsapp_number'); ?>">
						</div>
						<!--
						<div class="form-group">
							<input type="text" class="form-control" name="phone" placeholder="<?php //echo $this->lang->line('phone'); ?>">
						</div>
-->
						<div class="modal-footer">
							<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('update'); ?></button>
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
					<h6 class="text">
						<?php echo $this->lang->line('delete_ad_confirm'); ?>
					</h6>
					<form id="delete-ad-form">
						<input type="hidden" class="ad-id" name="ad_id">
						<input type="hidden" class="status-id" name="status">
						<div class="modal-footer">
							<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('yes'); ?></button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!--<script src="<?php echo base_url('assets/js/profile.js'); ?>"></script>-->
