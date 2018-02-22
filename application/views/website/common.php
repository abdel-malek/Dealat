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
			<div class="col-6 col-sm-3 col-lg-2  offset-sm-0 offset-md-1">
				<span class="logo"><a href="<?php echo base_url() ?>"><img class="" src="<?php echo base_url("assets/images/Dealat%20logo%20Red%20background-lined.png"); ?>" width="150px" alt=""></a></span>
			</div>
			<div class="col-5 col-sm-3 col-md-2 offset-1 offset-sm-0">
				<div class="language-wrapper">
					<div class="language-switch">
						<?php if( $this->session->userdata("language")  == "en" ) $en_lng ="selected"; else $en_lng="";  ?>
						<?php if( $this->session->userdata("language")  == "ar" ) $ar_lng ="selected"; else $ar_lng="";  ?>
						<a class="english <?php echo $en_lng; ?>" href="<?php echo site_url('/users_control_web/change_language?language=en')?>" data-locale="en">en</a>
						<a class="arabic <?php echo $ar_lng; ?>" href="<?php echo site_url('/users_control_web/change_language?language=ar') ?>" data-locale="ar">ar</a>
					</div>
				</div>
			</div>
			<div class="col-6 col-sm-3 col-lg-2 offset-md-0 offset-lg-3 mt-2 mb-2">
				<!--					<button class="btn button1 login">Sign In</button>-->
				<button class="btn button2 login"><?php echo $this->lang->line('sign_in'); ?></button>
			</div>
			<div class="col-6 col-sm-3 col-lg-2 mt-2 mb-2">
				<!--					<button class="btn button1 register">Register</button>-->
				<button class="btn button2 register"><?php echo $this->lang->line('register'); ?></button>
			</div>
		</div>
	</div>
</header>

<!--card modal-->
<div id="card-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body">
				<div class="card">

					<div class="card-img-slider slick-slider">
						<!--
							<div class="card-img-top"><img src="images/bike1.jpg" alt=""></div>
							<div class="card-img-top"><img src="images/bike1.jpg" alt=""></div>
							<div class="card-img-top"><img src="images/bike1.jpg" alt=""></div>
-->
						<div class="card-img-top" style="background-image: url('<?php echo base_url('assets/images/car1.png'); ?>')"></div>
						<div class="card-img-top" style="background-image: url('<?php echo base_url('assets/images/car2.jpg'); ?>')"></div>
						<div class="card-img-top" style="background-image: url('<?php echo base_url('assets/images/car3.png'); ?>')"></div>
						<div class="card-img-top" style="background-image: url('<?php echo base_url('assets/images/bike1.jpg'); ?>')"></div>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-12 mb-4 text-center">
								<div class="card-title">Product Title</div>
								<div class="details">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam dolorum assumenda</div>
							</div>
							<div class="col-7 info-col">

								<div class="seller"><span class="seller-lbl"><?php echo $this->lang->line('seller_name'); ?>: </span><span class="seller-val">Jhon Doe</span></div>
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
									<div class="phone"><span class="phone-lbl"><i class="fas fa-home fa-fw"></i></span> <span class="phone-val">123456789</span></div>
									<div class="mobile"><span class="mobile-lbl"><i class="fas fa-mobile-alt fa-fw"></i> </span><span class="mobile-val"><a href="tel:09123456789">09123456789</a></span></div>
									<div class="whatsapp"><span class="whatsapp-lbl"><i class="fab fa-whatsapp fa-fw"></i> </span><span class="whatsapp-val"><a href="tel:09123456789">09123456789</a></span></div>
								</details>

							</div>
							<div class="col-5 info-col">
								<div class="location"><span class="location-lbl"></span><span class="location-val">Syria, Damascus</span></div>
								<div class="status"><span class="status-lbl"></span><span class="status-val">New</span></div>
								<div class="negotiable"><span class="negotiable-lbl"><?php echo $this->lang->line('price'); ?>: </span><span class="negotiable-val">None negotiable</span></div>
								<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
								<!--									<div class="likes"><span class="likes-lbl">Likes </span><span class="likes-val">350 </span></div>-->
								<!--									<div class="rating">stars</div>-->
								<div class="date"><span class="date-lbl"></span><span class="date-val">12/12/2015</span></div>
							</div>

						</div>
						<div class="price">
							<div class="price-val">3000$</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn button2 chat"><?php echo $this->lang->line('chat_seller'); ?></button>
			</div>
		</div>
	</div>
</div>

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
				<!--
					<button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Sign up with Facebook</button>
					<button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Sign up with Google</button>
-->
				<div class="title lines">
					<span class="text"><?php echo $this->lang->line('create_account1'); ?></span>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-sm-6"><button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Facebook</button></div>
						<div class="col-sm-6"><button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Google</button></div>
					</div>
				</div>
				<div class="title lines">
					<span class="text"><?php echo $this->lang->line('create_account2'); ?></span>
				</div>
				<form action="">
					<div id="fileuploader-register">Upload</div>
					<div class="form-group">
						<input type="text" class="form-control" name="user_name" placeholder="<?php echo $this->lang->line('username'); ?>">
					</div>
					<div class="form-group">
						<input type="email" class="form-control" name="email" placeholder="<?php echo $this->lang->line('email'); ?>">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" name="phone" placeholder="<?php echo $this->lang->line('phone'); ?>">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="password" placeholder="<?php echo $this->lang->line('password'); ?>">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="confirm_password" placeholder="<?php echo $this->lang->line('repassword'); ?>">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('register'); ?></button>
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
					<h5><?php echo $this->lang->line('log_into_account'); ?></h5>
				</div>
				<!--
					<div class="title lines">
						<span class="text">Sign in with:</span>
					</div>
-->
				<form action="">
					<div class="form-group">
						<input type="text" class="form-control" name="user_name" placeholder="<?php echo $this->lang->line('username'); ?>">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="password" placeholder="<?php echo $this->lang->line('password'); ?>">
					</div>
				</form>
				<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('sign_in'); ?></button>

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
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
			</div>
			<div class="modal-body">
				<div class="error-message full modal-error-message d-none"></div>
				<form id="place-ad-form">

					<input type="hidden" name="category_id" class="category-id">
					<input type="hidden" name="location_id" class="location-id">
					<div class="form-group">
						<input type="text" class="form-control" name="title" placeholder="<?php echo $this->lang->line('item_name'); ?>" required>
					</div>
					<div class="form-group">
						<nav class="navbar navbar-expand-md navbar-light bg-light categories-nav">
							<ul class="navbar-nav">
								<li class="nav-item dropdown">
									<a class="nav-link dropdown-toggle select" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $this->lang->line('select_category'); ?></a>
									<ul class="dropdown-menu main-categories">
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
									<ul class="dropdown-menu cities">

									</ul>
								</li>
							</ul>
						</nav>
					</div>


					<div class="form-group">
						<select name="show_period" class="period-select" placeholder="<?php echo $this->lang->line('show_period'); ?>" required>
							<option disabled selected value="foo" >
								<option value="1">a week</option>
								<option value="2">10 days</option>
								<option value="3">a month</option>
						</select>
					</div>

					<div class="form-group">
						<!--todo display currency/ take only numbers-->
						<input type="text" class="form-control" name="price" placeholder="<?php echo $this->lang->line('item_price'); ?>" required>
					</div>

					<div class="form-group">
						<textarea class="form-control" name="description" rows="4" placeholder="<?php echo $this->lang->line('add_description'); ?>"></textarea>
					</div>

					<!--vehicles template-->
					<!--type id/ type model id-->
					<div class="template-vehicles template d-none" data-template-id="1">

						<div class="form-group">
							<nav class="navbar navbar-expand-md navbar-light bg-light types-nav">
								<ul class="navbar-nav">
									<li class="nav-item dropdown">
										<a class="nav-link dropdown-toggle select" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $this->lang->line('select_type'); ?></a>
										<ul class="dropdown-menu types">
										</ul>
									</li>
								</ul>
							</nav>
						</div>

						<!--
					<script id="ad-modal-types-template" type="text/template">
						{{ #types }}
						<li><a class="dropdown-item dropdown-toggle type" href="" data-type-id={{type_id}}>{{name}}</a>
							<ul class="dropdown-menu">
								{{ #models }}
								<li><a class="dropdown-item model" href="" data-type-id={{type_id}} data-type-model-id={{type_model_id}}>{{name}}</a></li>
								{{ /models }}
							</ul>
						</li>
						{{ /types }}
					</script>
-->
						<script id="ad-modal-types-template" type="text/template">
							{{ #types }}
							<li><a class="dropdown-item type" href="" data-type-id={{type_id}}>{{name}}</a>

							</li>
							{{ /types }}
						</script>


						<div class="form-group">
							<input type="text" class="form-control" name="manufacture_date" placeholder="<?php echo $this->lang->line('manufacture_date'); ?>" data-toggle="datepicker">
						</div>

						<label class="">
							<input type="checkbox" name="is_automatic" value="false"><span class=""> Automatic</span>
						</label>

						<label class="">
							<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
						</label>

						<div class="form-group">
							<input type="text" class="form-control" name="kilometer" placeholder="<?php echo $this->lang->line('kilometers'); ?>">
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

						<label class="">
							<input type="checkbox" name="with_furniture" value="false"><span class=""> <?php echo $this->lang->line('with_furniture'); ?></span>
						</label>
					</div>
					<!--mobiles template-->
					<!--type id-->
					<div class="template-mobiles template d-none" data-template-id="3">
						<label class="">
							<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
						</label>
					</div>
					<!--electronics template-->
					<!--type id-->
					<div class="template-electronics template d-none" data-template-id="4">
						<div class="form-group">
							<input type="text" class="form-control" name="size" placeholder="<?php echo $this->lang->line('size'); ?>">
						</div>

						<label class="">
							<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
						</label>
					</div>
					<!--fashion template-->
					<div class="template-fashion template d-none" data-template-id="5">
						<label class="">
							<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
						</label>
					</div>
					<!--kids template-->
					<div class="template-kids template d-none" data-template-id="6">
						<label class="">
							<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
						</label>
					</div>
					<!--sports template-->
					<div class="template-sports template d-none" data-template-id="7">
						<label class="">
							<input type="checkbox" name="is_new" value="false"><span class=""> New Equipments</span>
						</label>
					</div>
					<!--job positions template-->
					<!--schedule id/experience id/education id-->
					<div class="template-job template d-none" data-template-id="8">
						<div class="form-group">
							<select name="schedule_id" class="schedules-select" placeholder="<?php echo $this->lang->line('schedule'); ?>" required>
								<option disabled selected value="foo" >
							</select>
						</div>

						<div class="form-group">
							<select name="education_id" class="educations-select" placeholder="<?php echo $this->lang->line('education'); ?>" required>
								<option disabled selected value="foo" >
							</select>
						</div>

						<div class="form-group">
							<input type="text" class="form-control" name="salary" placeholder="<?php echo $this->lang->line('salary'); ?>">
						</div>
					</div>

					<!--industries template-->
					<div class="template-industries template d-none" data-template-id="9">
						<label class="">
							<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
						</label>
					</div>
					<!--services template-->
					<div class="template-services template d-none" data-template-id="10"></div>

					<!--services template-->
					<div class="template-services template d-none" data-template-id="11"></div>

					<div id="fileuploader-ad">Upload</div>
					<!--
					<div class="">
						<input id="terms-agree" type="checkbox" name="featured" class="featured" value="false">
						<label for="terms-agree" class="">
							<span class="">Set as featured advertisement</span>
							<span class="d-none text-danger"> This will cost you some money</span>
						</label>
					</div>
					-->
					<label class="featured">
						<input id="featured-ad" type="checkbox" name="featured_ad" value="false"><span class=""> <?php echo $this->lang->line('set_as_featured'); ?></span>
						<span class="warning d-none text-warning"> <?php echo $this->lang->line('featured_cost'); ?></span>
					</label>
					<div class="">
						<input id="terms-agree" type="checkbox" name="terms_agree" class="" required value="false">
						<label for="terms-agree" class="">
							<span class=""><?php echo $this->lang->line('agree_policy'); ?> <a href="" target="_blank"><?php echo $this->lang->line('terms'); ?></a></span>
							<span class="d-none text-danger">(required) <i class="fas fa-exclamation"></i></span>
						</label>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('submit_ad'); ?></button>
					</div>
				</form>

			</div>
			<!--
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit">Submit Ad</button>
			</div>
-->
		</div>
	</div>
</div>

<!--filter modal-->
<div id="filter-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
			</div>
			<div class="modal-body">
				<form action="">
					<div class="form-group">
						<input type="text" class="form-control" name="user_name" placeholder="Search">
					</div>
					<div class="form-group">
						<select name="" class="location-select" placeholder="Choose lacation">
								<option disabled selected value="foo" >
								<option value="1">lacation1</option>
								<option value="2">lacation2</option>
								<option value="3">lacation3</option>
							</select>
					</div>

					<div class="form-group">
						<input type="range" class="form-control" min="1" max="10" value="5" name="user_name" placeholder="Search">
					</div>

					<div class="form-group">
						<select name="" class="category-select" placeholder="Select Category">
								<option disabled selected value="foo" >
								<option value="art-music">Art and music</option>
								<option value="clothes">Clothes</option>
								<option value="electronics">Electronics</option>
								<option value="furniture">Furniture</option>
								<option value="jobs">Jobs</option>
								<option value="kids">Kids</option>
								<option value="mobile">Mobile</option>
								<option value="pets">Pets</option>
								<option value="estate">Real Estate</option>
								<option value="sports">Sports</option>
								<option value="vehicles">Vehicles</option>
							</select>
					</div>

					<div class="status form-group">
						<label class="text-center title">Status</label>
						<label class="radio-inline new">
                            <input type="radio" name="status" id="status-new" value="male"> New</label>
						<label class="radio-inline old">
                            <input type="radio" name="status" id="status-old" value="female"> Old</label>
					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn button2 submit">Done</button>
				<!--					<button type="button" class="btn button1" data-dismiss="modal">Cancel</button>-->
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
				<h6>Advertisement Added Successfully</h6>
			</div>
		</div>
	</div>
</div>

<!--
<a href="#" class="link" >
				<li><span class="name" data-id={{id}}>{{name}}</span></li>
			</a>
-->
<script id="sub-categories-template" type="text/template">
	{{ #. }}
	<div class="col-sm-3">
		<div class="sub">
			<li><span class="name" data-id={{id}}>{{name}}</span></li>
		</div>
	</div>
	{{ /. }}
</script>

<script id="ad-modal-categories-template" type="text/template">
	{{ #categories }}
	<li><a class="dropdown-item dropdown-toggle" href="" data-category-id={{category_id}}>{{category_name}}</a>
		<ul class="dropdown-menu">
			{{ #children }}
			<li><a class="dropdown-item last-subcategory" href="" data-template-id={{tamplate_id}} data-parent-id={{parent_id}}>{{category_name}}</a></li>
			{{ /children }}
		</ul>
	</li>
	{{ /categories }}
</script>

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
