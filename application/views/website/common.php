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
							<div class="col-7">

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
								<details>
									<summary><span class="mb-1 mt-2">Contact Information</span></summary>
									<div class="phone"><span class="phone-lbl"><i class="fas fa-home fa-fw"></i></span> <span class="phone-val">123456789</span></div>
									<div class="mobile"><span class="mobile-lbl"><i class="fas fa-mobile-alt fa-fw"></i> </span><span class="mobile-val"><a href="tel:09123456789">09123456789</a></span></div>
									<div class="whatsapp"><span class="whatsapp-lbl"><i class="fab fa-whatsapp fa-fw"></i> </span><span class="whatsapp-val"><a href="tel:09123456789">09123456789</a></span></div>
								</details>

							</div>
							<div class="col-5">
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
					<span class="text">Create an account using</span>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-sm-6"><button class="btn facebook"><i class="fab fa-facebook-f fa-lg"></i> Facebook</button></div>
						<div class="col-sm-6"><button class="btn google"><i class="fab fa-google-plus-g fa-lg"></i> Google</button></div>
					</div>
				</div>
				<div class="title lines">
					<span class="text">or create a new one here</span>
				</div>
				<form action="">
					<div id="fileuploader-register">Upload</div>
					<div class="form-group">
						<input type="text" class="form-control" name="user_name" placeholder="User Name">
					</div>
					<div class="form-group">
						<input type="email" class="form-control" name="email" placeholder="Email">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" name="phone" placeholder="Phone number">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="password" placeholder="Password">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="confirm_password" placeholder="Confirm Password">
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
					<h5>Log Into Your Account</h5>
				</div>
				<!--
					<div class="title lines">
						<span class="text">Sign in with:</span>
					</div>
-->
				<form action="">
					<div class="form-group">
						<input type="text" class="form-control" name="user_name" placeholder="User Name">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="password" placeholder="Password">
					</div>
				</form>
				<button type="submit" class="btn button2 submit"><?php echo $this->lang->line('sign_in'); ?></button>

				<div class="title lines">
					<span class="text">or sign in with:</span>
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
				<form id="place-ad-form">

					<div class="form-group">
						<input type="text" class="form-control" name="title" placeholder="Item's name" required>
					</div>

					<div class="form-group">
						<select name="category" class="category-select" placeholder="Select Category">
							<option disabled selected value="foo" >
							<option value="art-music">Art and music</option>
						</select>
					</div>
					
					<nav class="navbar navbar-expand-md navbar-light bg-light categories-nav">
						<ul class="navbar-nav">
							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Select Category</a>
								<ul class="dropdown-menu main-categories" aria-labelledby="navbarDropdownMenuLink">
<!--
									<li><a class="dropdown-item dropdown-toggle" href="#">damascus</a>
										<ul class="dropdown-menu">
											<li><a class="dropdown-item" href="#">muhagreen</a></li>
											<li><a class="dropdown-item" href="#">mazzeh</a></li>
										</ul>
									</li>
-->
								</ul>
							</li>
						</ul>
					</nav>
					
					<div class="form-group">
						<select name="location" class="location-select" placeholder="Item's location">
							<option disabled selected value="foo" >
								<option value="1">lacation1</option>
								<option value="2">lacation2</option>
								<option value="3">lacation3</option>
						</select>
					</div>

					<!--
	<nav class="navbar navbar-expand-md navbar-light bg-light">
		<ul class="navbar-nav">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Location</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<li><a class="dropdown-item" href="#">Action</a></li>
					<li><a class="dropdown-item" href="#">Another action</a></li>
					<li><a class="dropdown-item dropdown-toggle" href="#">Submenu</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="#">Submenu action</a></li>
							<li><a class="dropdown-item" href="#">Another submenu action</a></li>
						</ul>
					</li>
					<li><a class="dropdown-item dropdown-toggle" href="#">Submenu 2</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="#">Submenu action 2</a></li>
							<li><a class="dropdown-item" href="#">Another submenu action 2</a></li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
	</nav>
-->
					<nav class="navbar navbar-expand-md navbar-light bg-light location-nav">
						<ul class="navbar-nav">
							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Location</a>
								<ul class="dropdown-menu cities" aria-labelledby="navbarDropdownMenuLink">
<!--
									<li><a class="dropdown-item dropdown-toggle" href="#">damascus</a>
										<ul class="dropdown-menu">
											<li><a class="dropdown-item" href="#">muhagreen</a></li>
											<li><a class="dropdown-item" href="#">mazzeh</a></li>
										</ul>
									</li>
-->
								</ul>
							</li>
						</ul>
					</nav>
					
					<div class="form-group">
						<select name="show_period" class="period-select" placeholder="Keep ad for...">
							<option disabled selected value="foo" >
								<option value="week">a week</option>
								<option value="10_days">10 days</option>
								<option value="month">a month</option>
						</select>
					</div>
					<div class="form-group">
						<!--todo display currency/ take only numbers-->

						<input type="text" class="form-control" name="price" placeholder="Item's price" required>
					</div>

					<!--
					<div class="form-group">
						<input type="text" class="form-control" name="location" placeholder="Item's location">
					</div>
-->

					<div class="form-group">
						<textarea class="form-control" name="description" rows="4" placeholder="Add description"></textarea>
					</div>

					<!--properties template-->
					<div class="">
						<div class="form-group">
							<input type="text" class="form-control" name="space" placeholder="Space">
						</div>
						<div class="form-group">
							<input type="number" class="form-control" name="rooms_num" placeholder="Rooms">
						</div>
						<div class="form-group">
							<input type="number" class="form-control" name="floor" placeholder="Floor">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="state" placeholder="State">
						</div>
						<label class="">
						<input type="checkbox" name="with_furniture" value="false"><span class=""> With furniture</span>
					</label>
					</div>
					<!--sports template-->
					<div class="template-sports">
						<label class="">
						<input type="checkbox" name="is_new" value="false"><span class=""> New Equipments</span>
					</label>
					</div>
					<!--vehicles template-->
					<!--type id/ type model id-->
					<div class="template-vehicles">
						<div class="form-group">
							<input type="date" class="form-control" name="manufacture_date" placeholder="Manufacturing date">
						</div>
						<label class="">
						<input type="checkbox" name="is_automatic" value="false"><span class=""> Automatic</span>
					</label>
						<label class="">
						<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
					</label>
						<div class="form-group">
							<input type="text" class="form-control" name="kilometer" placeholder="Kilometers">
						</div>
					</div>
					<!--electronics template-->
					<!--type id-->
					<div class="template-electronics">
						<div class="form-group">
							<input type="text" class="form-control" name="size" placeholder="Size">
						</div>

						<label class="">
						<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
					</label>
					</div>
					<!--mobiles template-->
					<!--type id-->
					<div class="template-mobiles">
						<label class="">
						<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
					</label>
					</div>
					<!--fashion template-->
					<div class="template-fashion">
						<label class="">
						<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
					</label>
					</div>
					<!--services template-->
					<div class="template-services"></div>
					<!--kids template-->
					<div class="template-kids">
						<label class="">
						<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
					</label>
					</div>
					<!--industries template-->
					<div class="template-industries">
						<label class="">
						<input type="checkbox" name="is_new" value="false"><span class=""> New</span>
					</label>
					</div>
					<!--job positions template-->
					<!--schedule id/experience id/education id-->
					<div class="template-job">
						<div class="form-group">
							<input type="text" class="form-control" name="salary" placeholder="Salary">
						</div>
					</div>

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
						<input id="featured-ad" type="checkbox" name="featured_ad" value="false"><span class=""> Set as featured advertisement</span>
						<span class="warning d-none text-warning"> This will cost you some money</span>
					</label>
					<div class="">
						<input id="terms-agree" type="checkbox" name="terms_agree" class="" required value="false">
						<label for="terms-agree" class="">
							<span class=""><?php echo $this->lang->line('agree_policy'); ?> <a href="" target="_blank"><?php echo $this->lang->line('terms'); ?></a></span>
							<span class="d-none text-danger">(required) <i class="fas fa-exclamation"></i></span>
						</label>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn button2 submit">Submit Ad</button>
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
	{{ #. }}
		<li><a class="dropdown-item dropdown-toggle" href="#">{{category_name}}</a></li>
	{{ /. }}
</script>
<script id="ad-modal-locations-template" type="text/template">
	{{ #. }}
		<li><a class="dropdown-item dropdown-toggle" href="#">{{city}}</a></li>
	{{ /. }}
</script>

