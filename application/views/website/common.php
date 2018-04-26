<div class="main-wrapper">
	<!--  loading -->
	<!--
<section class="loading-overlay">
	<div class="spinner">
		<div class="sk-circle">
			<div class="sk-circle1 sk-child"></div>
			<div class="sk-circle2 sk-child"></div>
			<div class="sk-circle3 sk-child"></div>
			<div class="sk-circle4 sk-child"></div>
			<div class="sk-circle5 sk-child"></div>
			<div class="sk-circle6 sk-child"></div>
			<div class="sk-circle7 sk-child"></div>
			<div class="sk-circle8 sk-child"></div>
			<div class="sk-circle9 sk-child"></div>
			<div class="sk-circle10 sk-child"></div>
			<div class="sk-circle11 sk-child"></div>
			<div class="sk-circle12 sk-child"></div>
		</div>
	</div>
</section>
-->
	<section class="loading-overlay1">
		<div class="spinner">
			<div class="sk-circle">
				<div class="sk-circle1 sk-child"></div>
				<div class="sk-circle2 sk-child"></div>
				<div class="sk-circle3 sk-child"></div>
				<div class="sk-circle4 sk-child"></div>
				<div class="sk-circle5 sk-child"></div>
				<div class="sk-circle6 sk-child"></div>
				<div class="sk-circle7 sk-child"></div>
				<div class="sk-circle8 sk-child"></div>
				<div class="sk-circle9 sk-child"></div>
				<div class="sk-circle10 sk-child"></div>
				<div class="sk-circle11 sk-child"></div>
				<div class="sk-circle12 sk-child"></div>
			</div>
		</div>
	</section>
	<!--end loading-->

	<div class="social-fixed">
		<span class="show-social"><i class="fas fa-angle-left"></i></span>
		<div class="icons" data-show="1">

			<script id="social-fixed-template" type="text/template">
				{{#facebook_link}}<span class="icon facebook"><a href="{{facebook_link}}"><img src="<?php echo base_url("assets/images/facebook.png"); ?>" alt=""></a></span>{{/facebook_link}} {{#youtube_link}}
				<span class="icon youtube"><a href="{{youtube_link}}"><img src="<?php echo base_url("assets/images/youtube.png"); ?>" alt=""></a></span>{{/youtube_link}} {{#twiter_link}}
				<span class="icon twitter"><a href="{{twiter_link}}"><img src="<?php echo base_url("assets/images/twitter.png"); ?>" alt=""></a></span>{{/twiter_link}}
				<!--		<span class="icon instagram"><a href=""><img src="<?php echo base_url("assets/images/instagram.png"); ?>" alt=""></a></span>-->
			</script>
		</div>
	</div>

	<header class="home">
		<div class="container">
			<div class="row align-items-center">

				<div class="col-6 col-sm-3 col-lg-2 offset-sm-0 offset-md-1 offset-lg-0">
					<span class="logo"><a href="<?php echo base_url() ?>"><img class="" src="<?php echo base_url("assets/images/Dealat%20logo%20Red%20background-lined.png"); ?>" width="150px" alt=""></a></span>
				</div>
				<div class="col-5 col-sm-3 col-md-2 col-lg-3 offset-1 offset-sm-0">
					<span class="home-icon" style="padding-right: 10px">
					<a href="<?php echo base_url() ?>" style="color: #fff"><?php echo $this->lang->line('home'); ?></a>
				</span>

					<span class="language-wrapper">
					<span class="language-switch">
						<?php if( $this->session->userdata("language")  == "en" ) $en_lng ="selected"; else $en_lng="";  ?>
						<?php if( $this->session->userdata("language")  == "ar" ) $ar_lng ="selected"; else $ar_lng="";  ?>
				<span class="english <?php echo $en_lng; ?>"  data-locale="en"><?php echo $this->lang->line('english'); ?></span>
					<span class="arabic <?php echo $ar_lng; ?>" data-locale="ar"><?php echo $this->lang->line('arabic'); ?></span>
					</span>
					</span>

				</div>
				<?php if( !($this->session->userdata('PHP_AUTH_USER'))){ ?>
				<!--if not logged-->
				<div class="col-6 col-sm-3 col-lg-2 offset-md-0 offset-lg-3 mt-2 mb-2">
					<button class="btn button2 login"><?php echo $this->lang->line('sign_in'); ?></button>
				</div>
				<div class="col-6 col-sm-3 col-lg-2 mt-2 mb-2">
					<button class="btn button2 register"><?php echo $this->lang->line('register'); ?></button>
				</div>
				<?php  }else{?>
				<!--if logged-->
				<div class="col-6 col-sm-3 col-lg-2 offset-md-0 offset-lg-3 mt-2 mb-2">

				</div>
				<div class="col-6 col-sm-3 col-lg-2 mt-2 mb-2">

					<div class="header-account-logged">
						<div class="header-account-dropdown dropdown">
							<a class="header-account-open account-link" id="dLabel" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><button class="btn button2 logged">
							<span class="login-label"><?php echo $this->session->userdata('USERNAME'); ?></span></button>
						</a>
							<ul class="dropdown-menu arrow" role="menu" aria-labelledby="dLabel">
								<li>
									<a href="<?php echo base_url('index.php/users_control_web/load_profile') ?>" class="link"><?php echo $this->lang->line('profile'); ?></a>
								</li>
								<li>
									<a href="<?php echo base_url('index.php/users_control_web/logout') ?>" data-no-turbolink="true" class="link logout-link"><?php echo $this->lang->line('logout'); ?></a>
								</li>
							</ul>
						</div>
						<div class="new-msg d-none"><a href="<?php echo base_url('index.php/users_control_web/load_profile#chats') ?>"><i class="fas fa-envelope"></i></a></div>
					</div>
				</div>
				<?php } ?>
			</div>
		</div>
	</header>

	<!--card modal-->
	<div id="card-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
				</div>
				<div class="modal-body">

				</div>
				<div class="modal-footer">
					<button type="button" class="btn button2 chat"><?php echo $this->lang->line('chat_seller'); ?></button>
					<button type="button" class="btn button2 report" title="<?php echo $this->lang->line('report_ad'); ?>"><i class="far fa-flag"></i></button>
				</div>
			</div>
		</div>
	</div>

	<script id="ad-details-template" type="text/template">
		<div class="card" data-ad-id="{{ad.ad_id}}" data-category-id="{{ad.category_id}}">
			<div class="card-img-slider slick-slider">
				<div class="card-img-top"><img src="<?php echo base_url('{{ad.main_image}}'); ?>"></div>
				{{#ad.images}}
				<div class="card-img-top"><img src="<?php echo base_url('{{image}}'); ?>"></div>
				{{/ad.images}} {{#ad.main_video}}
				<div class="card-img-top"><video height="200" controls>
  <source src="<?php echo base_url('{{ad.main_video}}'); ?>" type="video/mp4">
</video></div>
				{{/ad.main_video}}
			</div>
			<div class="card-body">
				<div class="fav">
					<span class="icon" data-added="{{ad.is_favorite}}" title="<?php echo $this->lang->line('add_fav'); ?>"><i class="far fa-heart fa-2x"></i></span>
				</div>
				<div class="row">
					<div class="col-12 mb-4 text-center">
						<div class="card-title">{{ad.title}}</div>
						<div class="details">{{ad.description}}</div>
					</div>

					<div class="col-4 info-col">
						<div class="number"><span class="number-lbl"><?php echo $this->lang->line('ad_number'); ?>: </span><span class="number-val">{{ad.ad_id}}</span></div>
						<div class="category"><span class="category-lbl"><?php echo $this->lang->line('category'); ?>: </span><span class="category-val">{{ad.parent_category_name}} - {{ad.category_name}}</span></div>
						<div class="seller"><span class="seller-lbl"><?php echo $this->lang->line('seller_name'); ?>: </span><span class="seller-val">{{ad.seller_name}}</span></div>
						<!--<div class="seller"><span class="rating-lbl"><?php echo $this->lang->line('seller_rating'); ?>: </span>
						<span class="rating-val">
							<fieldset class="rating">
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
						<div class="clearfix"></div>
						</span>
					</div>-->
						<details open>
							<summary><span class="mb-1 mt-2"><?php echo $this->lang->line('contact_info'); ?></span></summary>
							<div class="mobile"><span class="mobile-lbl"><i class="fas fa-mobile-alt fa-fw"></i> </span><span class="mobile-val"><a href="tel:{{ad.seller_phone}}">{{ad.seller_phone}}</a></span></div>
							{{#ad.whatsup_number}}
							<div class="whatsapp"><span class="whatsapp-lbl"><i class="fab fa-whatsapp fa-fw"></i> </span><span class="whatsapp-val"><a href="tel:{{ad.whatsup_number}}">{{ad.whatsup_number}}</a></span></div>{{/ad.whatsup_number}}
						</details>

					</div>

					<div class="col-4 info-col">
						<div class="location"><span class="location-lbl"></span><span class="location-val">{{ad.city_name}}{{#ad.location_name}} - {{/ad.location_name}}{{ad.location_name}}</span></div>
						<div class="negotiable"><span class="negotiable-lbl"><?php echo $this->lang->line('price'); ?>: </span><span class="negotiable-val">{{negotiable}}</span></div>
						<div class="date"><span class="date-lbl"></span><span class="date-val">{{date}}</span></div>
					</div>

					<div class="col-4 info-col templates">
						<!--vehicles template-->
						<div class="template-vehicles template d-none" data-template-id="1">
							<div class="type field type_name"><span class="type-lbl"><?php echo $this->lang->line('type'); ?>:</span><span class="type-val"> {{ad.type_name}}</span></div>
							<div class="model field type_model_name"><span class="model-lbl"><?php echo $this->lang->line('type_model'); ?>:</span><span class="model-val"> {{ad.type_model_name}}</span></div>
							<div class="manufacture_date field"><span class="manufacture_date-lbl"><?php echo $this->lang->line('manufacture_date'); ?>:</span><span class="manufacture_date-val"> {{ad.manufacture_date}}</span></div>
							<div class="is_automatic field"><span class="is_automatic-lbl"><?php echo $this->lang->line('motion'); ?>:</span><span class="is_automatic-val"> {{automatic}}</span></div>
							<div class="is_new field"><span class="is_new-lbl"></span>
								<?php echo $this->lang->line('item_status'); ?>:<span class="is_new-val"> {{status}}</span></div>
							<div class="kilometer field"><span class="kilometer-lbl"><?php echo $this->lang->line('kilometrage'); ?>:</span><span class="kilometer-val"> {{ad.kilometer}}</span></div>
						</div>

						<!--properties template-->
						<div class="template-properties template d-none" data-template-id="2">
							<div class="space field"><span class="space-lbl"><?php echo $this->lang->line('space'); ?>:</span><span class="space-val"> {{ad.space}}</span></div>
							<div class="rooms_num field"><span class="rooms_num-lbl"><?php echo $this->lang->line('rooms_num'); ?>:</span><span class="rooms_num-val"> {{ad.rooms_num}}</span></div>
							<div class="floor field"><span class="floor-lbl"><?php echo $this->lang->line('floor'); ?>:</span><span class="floor-val"> {{ad.floor}}</span></div>
							<div class="state field"><span class="state-lbl"><?php echo $this->lang->line('state'); ?>:</span><span class="state-val"> {{ad.state}}</span></div>
							<div class="with_furniture field"><span class="with_furniture-lbl"><?php echo $this->lang->line('with_furniture'); ?>:</span><span class="with_furniture-val"> {{furniture}}</span></div>
						</div>

						<!--mobiles template-->
						<!--type id-->
						<div class="template-mobiles template d-none" data-template-id="3">
							<div class="type field type_name"><span class="type-lbl"><?php echo $this->lang->line('type'); ?>:</span><span class="type-val"> {{ad.type_name}}</span></div>
							<div class="model field type_model_name"><span class="model-lbl"><?php echo $this->lang->line('type_model'); ?>:</span><span class="model-val"> {{ad.type_model_name}}</span></div>
							<div class="is_new field"><span class="is_new-lbl"><?php echo $this->lang->line('item_status'); ?>:</span><span class="is_new-val"> {{status}}</span></div>
						</div>

						<!--electronics template-->
						<!--type id-->
						<div class="template-electronics template d-none" data-template-id="4">
							<div class="type field type_name"><span class="type-lbl"><?php echo $this->lang->line('type'); ?>:</span><span class="type-val"> {{ad.type_name}}</span></div>
							<div class="model field type_model_name"><span class="model-lbl"><?php echo $this->lang->line('type_model'); ?>:</span><span class="model-val"> {{ad.type_model_name}}</span></div>
							<div class="size field"><span class="size-lbl"><?php echo $this->lang->line('size'); ?>:</span><span class="size-val"> {{ad.size}}</span></div>
							<div class="is_new field"><span class="is_new-lbl"><?php echo $this->lang->line('item_status'); ?>:</span><span class="is_new-val"> {{status}}</span></div>
						</div>

						<!--fashion template-->
						<div class="template-fashion template d-none" data-template-id="5">
							<div class="is_new field"><span class="is_new-lbl"><?php echo $this->lang->line('item_status'); ?>:</span><span class="is_new-val"> {{status}}</span></div>
						</div>

						<!--kids template-->
						<div class="template-kids template d-none" data-template-id="6">
							<div class="is_new field"><span class="is_new-lbl"><?php echo $this->lang->line('item_status'); ?>:</span><span class="is_new-val"> {{status}}</span></div>
						</div>

						<!--sports template-->
						<div class="template-sports template d-none" data-template-id="7">
							<div class="is_new field"><span class="is_new-lbl"><?php echo $this->lang->line('item_status'); ?>:</span><span class="is_new-val"> {{status}}</span></div>
						</div>

						<!--job positions template-->
						<!--schedule id/experience id/education id-->
						<div class="template-job template d-none" data-template-id="8">
							<div class="schedule field schedule_name"><span class="schedule-lbl"><?php echo $this->lang->line('schedule'); ?>:</span><span class="schedule-val"> {{ad.schedule}}</span></div>
							<div class="education field education_name"><span class="education-lbl"><?php echo $this->lang->line('education'); ?>:</span><span class="education-val"> {{ad.education}}</span></div>
							<div class="experience field experience"><span class="experience-lbl"><?php echo $this->lang->line('experience'); ?>:</span><span class="experience-val"> {{ad.experience}}</span></div>
							<div class="salary field"><span class="salary-lbl"><?php echo $this->lang->line('salary'); ?>:</span><span class="salary-val"> {{ad.salary}}</span></div>
						</div>

						<!--industries template-->
						<div class="template-industries template d-none" data-template-id="9">
							<div class="is_new field"><span class="is_new-lbl"><?php echo $this->lang->line('item_status'); ?>:</span><span class="is_new-val"> {{status}}</span></div>
						</div>

						<!--services template-->
						<div class="template-services template d-none" data-template-id="10"></div>

						<!--basic template-->
						<div class="template-basic template d-none" data-template-id="11"></div>
					</div>

				</div>
				<div class="price">
					<div class="price-val">{{ad.price}}
						<?php echo $this->lang->line('sp'); ?>
					</div>
				</div>
			</div>
		</div>
	</script>


	<!--register modal-->
	<div id="register-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
				</div>
				<div class="modal-body">
					<div class="error-message d-none"></div>
					<!--
					<button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Sign up with Facebook</button>
					<button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Sign up with Google</button>
-->
					<!--
				<div class="title lines">
					<span class="text"><?php echo $this->lang->line('create_account1'); ?></span>
				</div>
				<div class="container social">
					<div class="row">
						<div class="col-sm-6"><button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Facebook</button></div>
						<div class="col-sm-6"><button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Google</button></div>
					</div>
				</div>
				<div class="title lines">
					<span class="text"><?php echo $this->lang->line('create_account2'); ?></span>
				</div>
-->
					<form id="register-form">
						<!--					<div id="fileuploader-register">Upload</div>-->
						<input type="hidden" name="lang" class="lang">
						<div class="row">
							<div class="col-sm-6 border-middle">
								<div class="form-group">
									<select name="city_id" class="city-select" required>
							<option value="" class="placeholder d-none" selected><?php echo $this->lang->line('select_city'); ?></option>
						</select>
								</div>
								<div class="form-group">
									<input type="text" class="user_name form-control" name="name" placeholder="<?php echo $this->lang->line('username'); ?>" required>
								</div>
								<div class="form-group phone-wrapper">
									<div class="row no-gutters">
										<div class="col-2 text-center"><span style="font-weight: bold;position: relative;top: 30%;">+963</span></div>
										<div class="col-10">
											<input type="number" class="phone form-control" name="phone" placeholder="<?php echo $this->lang->line('phone'); ?>" required>
										</div>
									</div>

								</div>
								<div class="form-group">
									<input type="password" class="password form-control" name="password" placeholder="<?php echo $this->lang->line('password'); ?>" required>
								</div>
								<div class="form-group">
									<input type="password" class="confirm_password form-control" name="confirm_password" placeholder="<?php echo $this->lang->line('repassword'); ?>">
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<input type="email" class="email form-control" name="email" placeholder="<?php echo $this->lang->line('email'); ?>">
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
							</div>
						</div>

						<div class="modal-footer">
							<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('register'); ?></button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!--login modal-->
	<div id="login-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">

					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>

				</div>
				<div class="modal-body">
					<div class="modal-title">
						<h5>
							<?php echo $this->lang->line('log_into_account'); ?>
						</h5>
					</div>
					<!--
					<div class="title lines">
						<span class="text">Sign in with:</span>
					</div>
-->
					<div class="error-message d-none text-left"></div>
					<form id="login-form">
						<div class="form-group">
							<input type="number" class="form-control phone" name="phone" placeholder="<?php echo $this->lang->line('phone'); ?>" required>
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="password" placeholder="<?php echo $this->lang->line('password'); ?>" required>
						</div>
						<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('sign_in'); ?></button>
					</form>
					<!--
				<div class="title lines">
					<span class="text"><?php echo $this->lang->line('signin_with'); ?></span>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-sm-6"><button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Facebook</button></div>
						<div class="col-sm-6"><button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Google</button></div>
					</div>
				</div>
-->
				</div>
				<div class="modal-footer">
				</div>
			</div>
		</div>
	</div>

	<!--ad modal-->
	<div id="ad-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="error-message full d-none"></div>
					<form id="place-ad-form">
						<div class="row">
							<div class="col-sm-6">
								<input type="hidden" name="category_id" class="category-id">
								<input type="hidden" name="type_id" class="type-id">
								<input type="hidden" name="type_model_id" class="type-model-id">

								<div class="form-group">
									<input type="text" class="form-control" name="title" placeholder="<?php echo $this->lang->line('item_name'); ?>" required>
								</div>

								<div class="form-group">
									<nav class="navbar navbar-expand-md navbar-light categories-nav">
										<ul class="navbar-nav">
											<li class="nav-item dropdown">
												<a class="nav-link dropdown-toggle select" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $this->lang->line('select_category'); ?></a>
												<ul class="dropdown-menu main-categories main-dropdown">
												</ul>
											</li>
										</ul>
									</nav>
								</div>

								<script id="ad-modal-categories-template" type="text/template">
									{{ #categories }}
									<li><a class="dropdown-item dropdown-toggle" href="" data-category-id={{category_id}}>{{category_name}}</a>
										<ul class="dropdown-menu">
											{{ #children }}
											<li>
												<a class="dropdown-item subcategory last-subcategory" href="" data-template-id={{tamplate_id}} data-category-id={{category_id}}>{{category_name}}</a>
											</li>
											{{ /children }}
										</ul>
									</li>
									{{ /categories }}
								</script>

								<script id="ad-modal-subcategories-template" type="text/template">
									<ul class="dropdown-menu">
										{{ #. }}
										<li>
											<a class="dropdown-item last-subcategory" href="" data-template-id="{{tamplate_id}}" data-category-id="{{category_id}}" ">{{category_name}}</a>
									</li>
									{{ /. }}
								</ul>
							</script>

							<div class="form-group">
								<select name="city_id" class="city-select" required>
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_city'); ?></option>
								</select>
							</div>

							<div class="form-group">
								<select name="location_id" class="location-select">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_location'); ?></option>
								</select>
							</div>

							<div class="form-group">
								<select name="show_period" class="period-select" required>
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('show_period'); ?></option>
								</select>
							</div>

							<div class="form-group">
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
							<div class="form-group d-none field type_name">
								<nav class="navbar navbar-expand-md navbar-light types-nav">
									<ul class="navbar-nav">
										<li class="nav-item dropdown">
											<a class="nav-link dropdown-toggle select" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $this->lang->line('select_type'); ?></a>
											<ul class="dropdown-menu types main-dropdown">
											</ul>
										</li>
									</ul>
								</nav>
							</div>

							<script id="ad-modal-types-template" type="text/template">
								{{ #types }}
								<li class="type-item" data-type-id={{type_id}} data-template-id={{tamplate_id}}><a class="dropdown-item dropdown-toggle type" href="" data-type-id={{type_id}}>{{name}}</a>
									<ul class="dropdown-menu">
										{{ #models }}
										<li><a class="dropdown-item model" href="" data-type-id={{type_id}} data-type-model-id={{type_model_id}}>{{name}}</a></li>
										{{ /models }}
									</ul>
								</li>
								{{ /types }}
							</script>

							<!--vehicles template-->
							<!--type id/ type model id-->
							<div class="template-vehicles template d-none" data-template-id="1">

								<div class="form-group field manufacture_date">
									<input type="text" class="form-control" name="manufacture_date" placeholder="<?php echo $this->lang->line('manufacture_date'); ?>" data-toggle="datepicker">
								</div>
								<div class="form-group field kilometer">
									<input type="number" class="form-control" name="kilometer" placeholder="<?php echo $this->lang->line('kilometers'); ?>">
								</div>

								<div class="form-group field is_automatic">
									<select name="is_automatic" class="automatic-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_motion'); ?></option>
										<option value="1"><?php echo $this->lang->line('automatic'); ?></option>
										<option value="0"><?php echo $this->lang->line('manual'); ?></option>
									</select>
								</div>

								<div class="form-group field is_new">
									<select name="is_new" class="status-select ">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>

							</div>
							<!--properties template-->
							<div class="template-properties template d-none" data-template-id="2">
								<div class="form-group field space">
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
								<input type="checkbox" name="with_furniture" value="1"><span class=""> <?php echo $this->lang->line('with_furniture'); ?></span>
							</label>
								</div>
							</div>
							<!--mobiles template-->
							<!--type id-->
							<div class="template-mobiles template d-none" data-template-id="3">

								<div class="form-group field is_new">
									<select name="is_new" class="status-select ">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--electronics template-->
							<!--type id-->
							<div class="template-electronics template d-none" data-template-id="4">

								<div class="form-group field size">
									<input type="number" class="form-control" name="size" placeholder="<?php echo $this->lang->line('size'); ?>">
								</div>

								<div class="form-group field is_new">
									<select name="is_new" class="status-select ">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--fashion template-->
							<div class="template-fashion template d-none" data-template-id="5">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select ">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--kids template-->
							<div class="template-kids template d-none" data-template-id="6">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select ">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--sports template-->
							<div class="template-sports template d-none" data-template-id="7">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select ">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
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
									<input type="number" class="form-control" name="salary" placeholder="<?php echo $this->lang->line('salary'); ?>">
								</div>
							</div>

							<!--industries template-->
							<div class="template-industries template d-none" data-template-id="9">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select ">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--services template-->
							<div class="template-services template d-none" data-template-id="10"></div>

							<!--basic template-->
							<div class="template-basic template d-none" data-template-id="11"></div>

							<div id="fileuploader-ad-main">Upload</div>
							<div id="fileuploader-ad">Upload</div>
							<div id="fileuploader-ad-video" class="d-none">Upload</div>
							<label class="featured">
								<input id="featured-ad" type="checkbox" name="is_featured" value="1"><span class=""> <?php echo $this->lang->line('set_as_featured'); ?></span>
								<div class="warning d-none text-warning featured-note"> <?php echo $this->lang->line('featured_cost'); ?></div>
							</label>
							<div class="">
								<input id="terms-agree" type="checkbox" name="terms_agree" class="" value="1" required>
								<label for="terms-agree" class="">
									<span class=""><?php echo $this->lang->line('agree_policy'); ?> <a href="" target="_blank"><?php echo $this->lang->line('terms'); ?></a></span>
									<span class="d-none text-danger">(required) <i class="fas fa-exclamation"></i></span>
								</label>
							</div>
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

<!--filter modal-->
<div id="filter-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body">
				<form id="filter-form">

					<div class="row">
						<div class="col-sm-6">
							<input type="hidden" class="template-id">
							<input type="hidden" name="category_id" class="category-id">
							<input type="hidden" name="category_name">

							<div class="form-group">
								<input type="text" class="search form-control" name="search" placeholder="<?php echo $this->lang->line('search'); ?>">
							</div>

							<div class="form-group">
								<nav class="navbar navbar-expand-md navbar-light categories-nav">
									<ul class="navbar-nav">
										<li class="nav-item dropdown">
											<a class="nav-link dropdown-toggle select" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $this->lang->line('select_category'); ?></a>
											<ul class="dropdown-menu main-categories main-dropdown">
											</ul>
										</li>
									</ul>
								</nav>
							</div>

							<div class="form-group">
								<select name="city_id" class="city-select">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_city'); ?></option>
								</select>
							</div>

							<div class="form-group">
								<select name="location_id" class="location-select">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_location'); ?></option>
								</select>
							</div>

							<div class="form-group">
								<!--todo display currency/ take only numbers-->
								<label for=""><?php echo $this->lang->line('price'); ?>:</label>
								<div class="row">
									<div class="col-sm-6">
										<input type="number" class="form-control" name="price_min" placeholder="<?php echo $this->lang->line('from'); ?>" min="0">
									</div>
									<div class="col-sm-6">
										<input type="number" class="form-control" name="price_max" placeholder="<?php echo $this->lang->line('to'); ?>" min="0">
									</div>
								</div>
							</div>

						</div>

						<div class="col-sm-6">
							<div class="form-group d-none field type_name">
								<select name="type_id" class="type-select">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_type'); ?></option>
								</select>
							</div>

							<div class="form-group d-none field type_model_name">
								<select multiple name="" class="model-select multiple" placeholder="<?php echo $this->lang->line('select_model'); ?>">

								</select>
							</div>
							<!--vehicles template-->
							<div class="template-vehicles template d-none" data-template-id="1">

								<div class="form-group field manufacture_date">
									<select multiple name="" class="manufacture-date-select multiple" placeholder="<?php echo $this->lang->line('manufacture_date'); ?>">
							</select>

								</div>
								<div class="form-group field kilometer">
									<label for=""><?php echo $this->lang->line('kilometers'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="kilometers_min" placeholder="<?php echo $this->lang->line('from'); ?>" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="kilometers_max" placeholder="<?php echo $this->lang->line('to'); ?>" min="0">
										</div>
									</div>
								</div>
								<div class="form-group field is_automatic">
									<select name="is_automatic" class="automatic-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_motion'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('automatic'); ?></option>
										<option value="0"><?php echo $this->lang->line('manual'); ?></option>
									</select>
								</div>
								<div class="form-group field is_new">
									<select name="is_new" class="status-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--properties template-->
							<div class="template-properties template d-none" data-template-id="2">
								<div class="form-group field space">
									<label for=""><?php echo $this->lang->line('space'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="space_min" placeholder="<?php echo $this->lang->line('from'); ?>" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="space_max" placeholder="<?php echo $this->lang->line('to'); ?>" min="0">
										</div>
									</div>
								</div>

								<div class="form-group field rooms_num">
									<label for=""><?php echo $this->lang->line('rooms'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="rooms_num_min" placeholder="<?php echo $this->lang->line('from'); ?>" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="rooms_num_max" placeholder="<?php echo $this->lang->line('to'); ?>" min="0">
										</div>
									</div>
								</div>

								<div class="form-group field floor">
									<label for=""><?php echo $this->lang->line('floor'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="floor_min" placeholder="<?php echo $this->lang->line('from'); ?>" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="floor_max" placeholder="<?php echo $this->lang->line('to'); ?>" min="0">
										</div>
									</div>
								</div>

								<select name="with_furniture" class="status-select field with_furniture">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
									<option value=""><?php echo $this->lang->line('all'); ?></option>
									<option value="1"><?php echo $this->lang->line('with_furniture'); ?></option>
									<option value="0"><?php echo $this->lang->line('without_furniture'); ?></option>
								</select>
							</div>
							<!--mobiles template-->
							<div class="template-mobiles template d-none" data-template-id="3">

								<div class="form-group field is_new">
									<select name="is_new" class="status-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--electronics template-->
							<div class="template-electronics template d-none" data-template-id="4">

								<div class="form-group field size">
									<label for=""><?php echo $this->lang->line('size'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="size_min" placeholder="<?php echo $this->lang->line('from'); ?>" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="size_max" placeholder="<?php echo $this->lang->line('to'); ?>" min="0">
										</div>
									</div>
								</div>

								<div class="form-group field is_new">
									<select name="is_new" class="status-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--fashion template-->
							<div class="template-fashion template d-none" data-template-id="5">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--kids template-->
							<div class="template-kids template d-none" data-template-id="6">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--sports template-->
							<div class="template-sports template d-none" data-template-id="7">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--job positions template-->
							<!--schedule id/experience id/education id-->
							<div class="template-job template d-none" data-template-id="8">
								<div class="form-group field schedule_name">
									<select multiple name="" class="schedules-select multiple" placeholder="<?php echo $this->lang->line('schedule'); ?>">
							</select>
								</div>

								<div class="form-group field education_name">
									<select multiple name="" class="educations-select multiple" placeholder="<?php echo $this->lang->line('education'); ?>">
							</select>
								</div>

								<div class="form-group field salary">
									<label for=""><?php echo $this->lang->line('salary'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="salary_min" placeholder="<?php echo $this->lang->line('from'); ?>" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="salary_max" placeholder="<?php echo $this->lang->line('to'); ?>" min="0">
										</div>
									</div>
								</div>
							</div>

							<!--industries template-->
							<div class="template-industries template d-none" data-template-id="9">
								<div class="form-group field is_new">
									<select name="is_new" class="status-select">
										<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_status'); ?></option>
										<option value=""><?php echo $this->lang->line('all'); ?></option>
										<option value="1"><?php echo $this->lang->line('new'); ?></option>
										<option value="0"><?php echo $this->lang->line('old'); ?></option>
									</select>
								</div>
							</div>
							<!--services template-->
							<div class="template-services template d-none" data-template-id="10"></div>

							<!--basic template-->
							<div class="template-basic template d-none" data-template-id="11"></div>

						</div>
					</div>

					<!--
					<div class="status form-group">
						<label class="text-center title">Status</label>
						<label class="radio-inline new">
                            <input type="radio" name="status" id="status-new" value="male"> New</label>
						<label class="radio-inline old">
                            <input type="radio" name="status" id="status-old" value="female"> Old</label>
					</div>
-->
					<div class="modal-footer">
						<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('search'); ?></button>
					</div>
				</form>
			</div>

		</div>
	</div>
</div>

<!--notification modal-->
<div id="notification-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body text-center">
				<h6 class="text"></h6>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('ok'); ?></button>
			</div>
		</div>
	</div>
</div>

<!--report modal-->
<div id="report-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body">
				<form id="report-form" class="text-center">
					<input type="hidden" name="ad_id" class="ad-id">
					<select name="report_message_id" class="report-select" required placeholder="<?php echo $this->lang->line('why_report_ad'); ?>">
						<option disabled selected value="" class="d-none" >
					</select>
				</form>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit" form="report-form"><?php echo $this->lang->line('report'); ?></button>
			</div>
		</div>
	</div>
</div>

<!--chat modal-->
<div id="chat-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-md" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
				<div class="chat-header">
					<div class="ad-name"></div>
					<div class="user-name"></div>
				</div>
			</div>
			<div class="modal-body">
				<ol class="chat">

				</ol>

				<script id="chat-self-template" type="text/template">

					<li class="self" data-msg-id="{{message_id}}">
						<div class="msg">
							<p>{{text}}</p>
							<time>{{time}}</time>
						</div>
					</li>
				</script>

				<script id="chat-other-template" type="text/template">
					<li class="other" data-msg-id="{{message_id}}">
						<div class="msg">
							<p>{{text}}</p>
							<time>{{time}}</time>
						</div>
					</li>
				</script>

				<form id="chat-form">
					<input type="hidden" class="ad-id" name="ad_id">
					<input type="hidden" class="is-seller">
					<input type="hidden" class="chat-session-id" name="chat_session_id">
					<div class="send-wrapper">
						<input type="text" class="form-control form-control-sm mt-2" name="msg" placeholder="<?php echo $this->lang->line('write_message'); ?>">
						<button type="submit" class="btn button2 submit telegram" form="chat-form"><i class="fab fa-telegram-plane"></i></button>
					</div>
				</form>
			</div>
			<div class="modal-footer">
			</div>
		</div>
	</div>
</div>

<!--success modal-->
<div class="modal fade" id="success-modal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-sm">
		<div class="modal-content">
			<div class="modal-body text-center">
				<p class="text"></p>
			</div>
		</div>
	</div>
</div>

<!--success modal with ok btn-->
<div id="success-btn-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body text-center">
				<h6 class="text"></h6>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('ok'); ?></button>
			</div>
		</div>
	</div>
</div>

<!--verify registration modal-->
<div class="modal fade" id="verify-modal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body text-center">
				<h6 class="text">
					<?php echo $this->lang->line('verification_text'); ?>
					<div class="phone-num-sec"></div>
				</h6>

				<div class="error-message full d-none">
					<?php echo $this->lang->line('verification_error'); ?>
				</div>
				<form id="verify-form">
					<input type="hidden" class="phone" name="phone" />
					<input type="hidden" class="password" />
					<input type="text" class="form-control code" name="verification_code" placeholder="<?php echo $this->lang->line('enter_code'); ?>" required />
					<div class="modal-footer">
						<button class="btn button2 submit" type="submit"><?php echo $this->lang->line('verify'); ?></button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script id="main-commercial-ads-template" type="text/template">
	{{#.}}
	<div class="slide">
		<a href="{{ad_url}}">
				<div class="ad-image" title="{{title}}" data-commercial_ad_id="{{commercial_ad_id}}" style="background-image: url('<?php echo base_url("{{image}}"); ?>')"></div>
			</a>
	</div>
	{{/.}}
</script>

<script id="side-commercial-ads-template" type="text/template">
	{{#.}}
	<div class="banner">
		<button type="button" class="close">
			  <span>&times;</span>
			</button>
		<a href="{{ad_url}}"><img src="<?php echo base_url("{{image}}"); ?>" class="img-fluid" alt="{{title}}" title="{{title}}"></a>
	</div>
	{{/.}}
</script>

<script id="sub-categories-template" type="text/template">
	<li class="name all" data-id={{catId}}>
		<?php echo $this->lang->line('all'); ?>
	</li>
	{{ #sub }}
	<li class="name" data-id={{category_id}}>{{category_name}}</li>
	{{ /sub }}
</script>
