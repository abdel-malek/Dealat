<!--  loading -->
<!--
	<section class="loading-overlay">
		<div class="logo">
			<img src="images/icons/LOGO.png" alt="Aura">
		</div>

		<div class="spinner">
			<div class="dot1"></div>
			<div class="dot2"></div>
		</div>
	</section>
-->
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
<!--end loading-->

<div class="social-fixed">
	<span class="show-social"><i class="fas fa-angle-left"></i></span>
	<div class="icons" data-show="1">
		<span class="icon facebook"><a href=""><img src="<?php echo base_url("assets/images/facebook.png"); ?>" alt=""></a></span>
		<span class="icon youtube"><a href=""><img src="<?php echo base_url("assets/images/youtube.png"); ?>" alt=""></a></span>
		<span class="icon twitter"><a href=""><img src="<?php echo base_url("assets/images/twitter.png"); ?>" alt=""></a></span>
		<span class="icon instagram"><a href=""><img src="<?php echo base_url("assets/images/instagram.png"); ?>" alt=""></a></span>

	</div>
</div>

<header class="home">
	<div class="container">
		<div class="row align-items-center">
			<!--			<div class="col-6 col-sm-3 col-lg-2 offset-sm-0 offset-md-1">-->
			<!--
		<div class="col-lg-1">
			<span class="home-icon">
				<a href="<?php echo base_url() ?>" style="color: #fff">Home</a>
			</span>
		</div>
-->
			<div class="col-6 col-sm-3 col-lg-2 offset-sm-0 offset-md-1 offset-lg-0">
				<span class="logo"><a href="<?php echo base_url() ?>"><img class="" src="<?php echo base_url("assets/images/Dealat%20logo%20Red%20background-lined.png"); ?>" width="150px" alt=""></a></span>
			</div>
			<div class="col-5 col-sm-3 col-md-2 col-lg-3 offset-1 offset-sm-0">
				<span class="home-icon" style="padding-right: 10px">
					<a href="<?php echo base_url() ?>" style="color: #fff">Home</a>
				</span>

				<span class="language-wrapper">
					<span class="language-switch">
						<?php if( $this->session->userdata("language")  == "en" ) $en_lng ="selected"; else $en_lng="";  ?>
						<?php if( $this->session->userdata("language")  == "ar" ) $ar_lng ="selected"; else $ar_lng="";  ?>
						<a class="english <?php echo $en_lng; ?>" href="<?php echo site_url('/users_control_web/change_language?language=en')?>" data-locale="en">en</a>
						<a class="arabic <?php echo $ar_lng; ?>" href="<?php echo site_url('/users_control_web/change_language?language=ar') ?>" data-locale="ar">ar</a>
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
<!--				<button class="btn button2 logged"><?php echo $this->session->userdata('USERNAME'); ?></button>-->
				<div class="header-account-logged">
					<div class="header-account-dropdown dropdown">
						<a class="header-account-open account-link" id="dLabel" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><button class="btn button2 logged">
							<span class="login-label"><?php echo $this->session->userdata('USERNAME'); ?></span></button>
						</a>
						<ul class="dropdown-menu arrow" role="menu" aria-labelledby="dLabel">
							<li>
								<a href="<?php echo base_url("index.php/users_control_web/load_profile") ?>" class="link">Profile</a>
							</li>
							<li>
								<a href="<?php echo base_url("index.php/users_control_web/logout") ?>" data-no-turbolink="true" class="link logout-link">Logout</a>
							</li>
							</ul>
					</div>			
				</div>
			</div>
			<?php } ?>
<!--
		<div class="col-12">
		<div class="user-wrapper">
			<div class="account header__account">
		<?php if( !($this->session->userdata('PHP_AUTH_USER'))){ ?>
				
               		<div>not logged in</div>
                <?php  }else{?>
				<div class="header-account-logged">
					<div class="header-account-dropdown dropdown">
						<a class="header-account-open account-link" id="dLabel" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="login-label"><?php echo $this->session->userdata('USERNAME'); ?></span>
						</a>
						<ul class="dropdown-menu arrow" role="menu" aria-labelledby="dLabel">
							<li>
								<a href="<?php echo base_url("index.php/users_control_web/load_profile") ?>" class="link">Profile</a>
							</li>
							<li>
								<a href="<?php echo base_url("index.php/users_control_web/logout") ?>" data-no-turbolink="true" class="link logout-link">Logout</a>
							</li>
						</ul>
					</div>			
				</div>
                <?php } ?>
			</div>
		</div>
		</div>
-->
		
		
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
			</div>
		</div>
	</div>
</div>

<script id="ad-details-template" type="text/template">
	<div class="card">
		<div class="card-img-slider slick-slider">
			<div class="card-img-top" style="background-image: url('<?php echo base_url('{{ad.main_image}}'); ?>')"></div>
			{{#ad.images}}
			<div class="card-img-top" style="background-image: url('<?php echo base_url('{{image}}'); ?>')"></div>
			{{/ad.images}}
		</div>
		<div class="card-body">
			<div class="row">
				<div class="col-12 mb-4 text-center">
					<div class="card-title">{{ad.title}}</div>
					<div class="details">{{ad.description}}</div>
				</div>

				<div class="col-4 info-col">
					<div class="seller"><span class="seller-lbl"><?php echo $this->lang->line('seller_name'); ?>: </span><span class="seller-val">{{ad.seller_name}}</span></div>
					<div class="seller"><span class="rating-lbl"><?php echo $this->lang->line('seller_rating'); ?>: </span>
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
					</div>
					<details open>
						<summary><span class="mb-1 mt-2"><?php echo $this->lang->line('contact_info'); ?></span></summary>
						<div class="phone"><span class="phone-lbl"><i class="fas fa-home fa-fw"></i></span> <span class="phone-val">{{ad.seller_phone}}</span></div>
						<div class="mobile"><span class="mobile-lbl"><i class="fas fa-mobile-alt fa-fw"></i> </span><span class="mobile-val"><a href="tel:09123456789">09123456789</a></span></div>
						<div class="whatsapp"><span class="whatsapp-lbl"><i class="fab fa-whatsapp fa-fw"></i> </span><span class="whatsapp-val"><a href="tel:09123456789">09123456789</a></span></div>
					</details>

				</div>

				<div class="col-4 info-col">
					<div class="location"><span class="location-lbl"></span><span class="location-val">{{ad.city_name}}-  {{ad.location_name}}</span></div>
					<div class="negotiable"><span class="negotiable-lbl"><?php echo $this->lang->line('price'); ?>: </span><span class="negotiable-val">{{negotiable}}</span></div>
					<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
					<div class="date"><span class="date-lbl"></span><span class="date-val">{{date}}</span></div>
				</div>

				<div class="col-4 info-col templates">
					<!--vehicles template-->
					<div class="template-vehicles template d-none" data-template-id="1">
						<div class="type"><span class="type-lbl">Type:</span><span class="type-val"> {{ad.type_name}}</span></div>
						<div class="model"><span class="model-lbl">Model:</span><span class="model-val"> {{ad.type_model_name}}</span></div>
						<div class="manufacture_date"><span class="manufacture_date-lbl">Manufacture date:</span><span class="manufacture_date-val"> {{ad.manufacture_date}}</span></div>
						<div class="is_automatic"><span class="is_automatic-lbl">Automatic:</span><span class="is_automatic-val"> {{automatic}}</span></div>
						<div class="is_new"><span class="is_new-lbl"></span>Status:<span class="is_new-val"> {{status}}</span></div>
						<div class="kilometer"><span class="kilometer-lbl">Kilometer:</span><span class="kilometer-val"> {{ad.kilometer}}</span></div>
					</div>

					<!--properties template-->
					<div class="template-properties template d-none" data-template-id="2">
						<div class="space"><span class="space-lbl">Space:</span><span class="space-val"> {{ad.space}}</span></div>
						<div class="rooms_num"><span class="rooms_num-lbl">Rooms:</span><span class="rooms_num-val"> {{ad.rooms_num}}</span></div>
						<div class="floor"><span class="floor-lbl">Floor:</span><span class="floor-val"> {{ad.floor}}</span></div>
						<div class="state"><span class="state-lbl">State:</span><span class="state-val"> {{ad.state}}</span></div>
						<div class="with_furniture"><span class="with_furniture-lbl">Furniture:</span><span class="with_furniture-val"> {{furniture}}</span></div>
					</div>

					<!--mobiles template-->
					<!--type id-->
					<div class="template-mobiles template d-none" data-template-id="3">
						<div class="type"><span class="type-lbl">Type:</span><span class="type-val"> {{ad.type_name}}</span></div>
						<div class="model"><span class="model-lbl">Model:</span><span class="model-val"> {{ad.type_model_name}}</span></div>
						<div class="is_new"><span class="is_new-lbl">Status:</span><span class="is_new-val"> {{status}}</span></div>
					</div>

					<!--electronics template-->
					<!--type id-->
					<div class="template-electronics template d-none" data-template-id="4">
						<div class="type"><span class="type-lbl">Type:</span><span class="type-val"> {{ad.type_name}}</span></div>
						<div class="model"><span class="model-lbl">Model:</span><span class="model-val"> {{ad.type_model_name}}</span></div>
						<div class="size"><span class="size-lbl">Size:</span><span class="size-val"> {{ad.size}}</span></div>
						<div class="is_new"><span class="is_new-lbl">Status:</span><span class="is_new-val"> {{status}}</span></div>
					</div>

					<!--fashion template-->
					<div class="template-fashion template d-none" data-template-id="5">
						<div class="is_new"><span class="is_new-lbl">Status:</span><span class="is_new-val"> {{status}}</span></div>
					</div>

					<!--kids template-->
					<div class="template-kids template d-none" data-template-id="6">
						<div class="is_new"><span class="is_new-lbl">Status:</span><span class="is_new-val"> {{status}}</span></div>
					</div>

					<!--sports template-->
					<div class="template-sports template d-none" data-template-id="7">
						<div class="is_new"><span class="is_new-lbl">Status:</span><span class="is_new-val"> {{status}}</span></div>
					</div>

					<!--job positions template-->
					<!--schedule id/experience id/education id-->
					<div class="template-job template d-none" data-template-id="8">
						<div class="schedule"><span class="schedule-lbl">Schedule:</span><span class="schedule-val"> {{ad.schedule}}</span></div>
						<div class="education"><span class="education-lbl">Education:</span><span class="education-val"> {{ad.education}}</span></div>
						<div class="salary"><span class="salary-lbl">Salary:</span><span class="salary-val"> {{ad.salary}}</span></div>
					</div>

					<!--industries template-->
					<div class="template-industries template d-none" data-template-id="9">
						<div class="is_new"><span class="is_new-lbl">Status:</span><span class="is_new-val"> {{status}}</span></div>
					</div>

					<!--services template-->
					<div class="template-services template d-none" data-template-id="10"></div>

					<!--basic template-->
					<div class="template-basic template d-none" data-template-id="11"></div>
				</div>

			</div>
			<div class="price">
				<div class="price-val">{{ad.price}}<?php echo $this->lang->line('sp'); ?></div>
			</div>
		</div>
	</div>
</script>


<!--register modal-->
<div id="register-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body">
				<div class="error-message full d-none"></div>
				<!--
					<button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Sign up with Facebook</button>
					<button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Sign up with Google</button>
-->
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
				<form id="register-form">
					<div id="fileuploader-register">Upload</div>
					<input type="hidden" name="lang" class="lang">
					<div class="form-group">
						<input type="text" class="user_name form-control" name="name" placeholder="<?php echo $this->lang->line('username'); ?>" required>
					</div>
					<div class="form-group">
						<input type="email" class="email form-control" name="email" placeholder="<?php echo $this->lang->line('email'); ?>">
					</div>
					<div class="form-group">
						<input type="text" class="phone form-control" name="phone" placeholder="<?php echo $this->lang->line('phone'); ?>" required>
					</div>
					<div class="form-group">
						<input type="password" class="password form-control" name="password" placeholder="<?php echo $this->lang->line('password'); ?>" required>
					</div>
					<div class="form-group">
						<input type="password" class="confirm_password form-control" name="confirm_password" placeholder="<?php echo $this->lang->line('repassword'); ?>">
					</div>
					<div class="form-group">
						<select name="city_id" class="city-select">
							<option value="" class="placeholder d-none" selected>select location</option>
						</select>
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
				<form id="login-form">
					<div class="form-group">
						<input type="text" class="form-control" name="phone" placeholder="<?php echo $this->lang->line('phone'); ?>" required>
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="password" placeholder="<?php echo $this->lang->line('password'); ?>" required>
					</div>
					<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('sign_in'); ?></button>
				</form>
				

				<div class="title lines">
					<span class="text"><?php echo $this->lang->line('signin_with'); ?></span>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-sm-6"><button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Facebook</button></div>
						<div class="col-sm-6"><button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Google</button></div>
					</div>
				</div>
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
							<input type="hidden" name="location_id" class="location-id">
							<input type="hidden" name="type_id" class="type-id">
							<input type="hidden" name="type_model_id" class="type-model-id">
							<!--					<input type="hidden" name="main_image" class="main-image">-->

							<div class="form-group">
								<input type="text" class="form-control" name="title" placeholder="<?php echo $this->lang->line('item_name'); ?>" required>
							</div>

							<div class="form-group">
								<nav class="navbar navbar-expand-md navbar-light bg-light categories-nav">
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
										<li><a class="dropdown-item last-subcategory" href="" data-template-id={{tamplate_id}} data-category-id={{category_id}}>{{category_name}}</a></li>
										{{ /children }}
									</ul>
								</li>
								{{ /categories }}
							</script>

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

							<script id="ad-modal-cities-template" type="text/template">
								{{ #cities }}
								<li><a class="dropdown-item dropdown-toggle" href="" data-city-id={{city_id}}>{{city_name}}</a>
									<ul class="dropdown-menu">
										{{ #locations }}
										<li><a class="dropdown-item location" href="" data-location-id={{location_id}}>{{location_name}}</a></li>
										{{ /locations }}
									</ul>
								</li>
								{{ /cities }}
							</script>

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

							<!--
							<label class="featured">
								<input id="featured-ad" type="checkbox" name="is_featured" value="1"><span class=""> <?php echo $this->lang->line('set_as_featured'); ?></span>
								<span class="warning d-none text-warning"> <?php echo $this->lang->line('featured_cost'); ?></span>
							</label>
							<div class="">
								<input id="terms-agree" type="checkbox" name="terms_agree" class="" value="1" required>
								<label for="terms-agree" class="">
									<span class=""><?php echo $this->lang->line('agree_policy'); ?> <a href="" target="_blank"><?php echo $this->lang->line('terms'); ?></a></span>
									<span class="d-none text-danger">(required) <i class="fas fa-exclamation"></i></span>
								</label>
							</div>
-->
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

							<div id="fileuploader-ad">Upload</div>
							<label class="featured">
								<input id="featured-ad" type="checkbox" name="is_featured" value="1"><span class=""> <?php echo $this->lang->line('set_as_featured'); ?></span>
								<span class="warning d-none text-warning"> <?php echo $this->lang->line('featured_cost'); ?></span>
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
							<input type="hidden" name="category_id" class="category-id">
							<input type="hidden" name="location_id" class="location-id">

							<div class="form-group">
								<input type="text" class="search form-control" name="search" placeholder="<?php echo $this->lang->line('search'); ?>">
							</div>

							<div class="form-group">
								<nav class="navbar navbar-expand-md navbar-light bg-light categories-nav">
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
								<!--todo display currency/ take only numbers-->
								<label for="">Price:</label>
								<div class="row">
									<div class="col-sm-6">
										<input type="number" class="form-control" name="price_min" placeholder="min" min="0">
									</div>
									<div class="col-sm-6">
										<input type="number" class="form-control" name="price_max" placeholder="max" min="0">
									</div>
								</div>
							</div>

						</div>

						<div class="col-sm-6">
							<div class="form-group d-none">
								<select name="type_id" class="type-select">
									<option selected value="" class="placeholder d-none"><?php echo $this->lang->line('select_type'); ?></option>
								</select>
							</div>

							<div class="form-group d-none">
								<select multiple name="" class="model-select" placeholder="select model">

								</select>
							</div>
							<!--vehicles template-->
							<div class="template-vehicles template d-none" data-template-id="1">

								<div class="form-group">
									<select multiple name="" class="manufacture-date-select" placeholder="<?php echo $this->lang->line('manufacture_date'); ?>">
							</select>

								</div>
								<div class="form-group">
									<label for=""><?php echo $this->lang->line('kilometers'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="kilometers_min" placeholder="min" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="kilometers_max" placeholder="max" min="0">
										</div>
									</div>
								</div>
								<div class="form-group">
									<select name="is_automatic" class="automatic-select">
										<option selected value="" class="placeholder d-none">select change</option>
										<option value="">All</option>
										<option value="1">Automatic</option>
										<option value="0">Manual</option>
									</select>
								</div>
								<div class="form-group">
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
								<div class="form-group">
									<label for=""><?php echo $this->lang->line('space'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="space_min" placeholder="min" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="space_max" placeholder="max" min="0">
										</div>
									</div>
								</div>

								<div class="form-group">
									<label for=""><?php echo $this->lang->line('rooms'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="rooms_num_min" placeholder="min" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="rooms_num_max" placeholder="max" min="0">
										</div>
									</div>
								</div>

								<div class="form-group">
									<label for=""><?php echo $this->lang->line('floor'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="floor_min" placeholder="min" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="floor_max" placeholder="max" min="0">
										</div>
									</div>
								</div>

								<div class="form-group">
									<input type="text" class="form-control" name="state" placeholder="<?php echo $this->lang->line('state'); ?>">
								</div>

								<select name="with_furniture" class="status-select">
									<option selected value="" class="placeholder d-none">select status</option>
									<option value="">All</option>
									<option value="1">With furniture</option>
									<option value="0">Without furniture</option>
								</select>
							</div>
							<!--mobiles template-->
							<div class="template-mobiles template d-none" data-template-id="3">

								<div class="form-group">
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

								<div class="form-group">
									<input type="text" class="form-control" name="size" placeholder="<?php echo $this->lang->line('size'); ?>">
								</div>

								<div class="form-group">
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
								<div class="form-group">
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
								<div class="form-group">
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
								<div class="form-group">
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
								<div class="form-group">
									<select multiple name="" class="schedules-select" placeholder="<?php echo $this->lang->line('schedule'); ?>">
							</select>
								</div>

								<div class="form-group">
									<select multiple name="" class="educations-select" placeholder="<?php echo $this->lang->line('education'); ?>">
							</select>
								</div>

								<div class="form-group">
									<label for=""><?php echo $this->lang->line('salary'); ?>:</label>
									<div class="row">
										<div class="col-sm-6">
											<input type="number" class="form-control" name="salary_min" placeholder="min" min="0">
										</div>
										<div class="col-sm-6">
											<input type="number" class="form-control" name="salary_max" placeholder="max" min="0">
										</div>
									</div>
								</div>
							</div>

							<!--industries template-->
							<div class="template-industries template d-none" data-template-id="9">
								<div class="form-group">
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
						<button type="submit" class="btn button2 submit">Filter</button>
						<!--					<button type="button" class="btn button1" data-dismiss="modal">Cancel</button>-->
					</div>
				</form>
			</div>

		</div>
	</div>
</div>

<!--pay modal-->
<div id="pay-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body">
				<p>Choose your payment method</p>
				<form action="">

					<div class="form-group">
						<input type="text" class="form-control" name="bank_name" placeholder="Bank Name">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" name="account_number" placeholder="Account Number">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" name="routing_number" placeholder="Routing Number">
					</div>

					<div class="form-group">
						<select name="" class="category-select" placeholder="Account Type">
							<option disabled selected value="foo" >
							<option value="1">type1</option>
							<option value="2">type2</option>
							<option value="3">type3</option>
						</select>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit">Authorize</button>
				<!--					<button type="button" class="btn button1" data-dismiss="modal">Cancel</button>-->
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
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit">Yes</button>
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
				<button type="submit" class="btn button2 submit">Ok</button>
			</div>
		</div>
	</div>
</div>

<!--chat modal-->
<div id="chat-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body text-center">
				<input type="text" class="form-control form-control-sm mt-2" placeholder="Send a message to the seller">
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit">Send</button>
			</div>
		</div>
	</div>
</div>

<!--success modal-->
<div class="modal fade" id="success-modal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-sm">
		<div class="modal-content">
			<div class="modal-body text-center">
				<p>Advertisement Added Successfully</p>
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
				<div class="modal-verify-sms__text">
					<?php echo $this->lang->line('verification_text'); ?>
					<div class="phone-num-sec"></div>
				</div>

				<div class="error-message full d-none">
					<?php echo $this->lang->line('verification_error'); ?>
				</div>
				<form id="verify-form">
					<input type="hidden" class="phone" name="phone" />
						<input type="text" class="" name="verification_code" placeholder="<?php echo $this->lang->line('enter_code'); ?>" required />
					<div class="modal-footer">
						<button class="btn button2 submit" type="submit"><?php echo $this->lang->line('verify'); ?></button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>


<script id="sub-categories-template" type="text/template">
	<div class="col-sm-3">
		<div class="sub">
			<li><span class="name all" data-id={{catId}}><?php echo $this->lang->line('all'); ?></span></li>
		</div>
	</div>
	{{ #sub }}
	<div class="col-sm-3">
		<div class="sub">
			<li><span class="name" data-id={{category_id}}>{{category_name}}</span></li>
		</div>
	</div>
	{{ /sub }}
</script>
