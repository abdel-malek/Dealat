<div class="social-fixed">
			<span class="show-social"><i class="fas fa-angle-right"></i></span>
		<div class="icons" data-show="0">
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
					<span class="logo"><a href=""><img class="" src="<?php echo base_url("assets/images/Dealat%20logo%20Red%20background-lined.png"); ?>" width="150px" alt=""></a></span>
				</div>
				<div class="col-5 col-sm-3 col-md-2 offset-1 offset-sm-0">
					<div class="language-wrapper">
						<div class="language-switch">

							<a class="selected" href="" data-locale="en">en</a>

							<a class="" href="" data-locale="ar">ar</a>
						</div>
					</div>
				</div>
				<div class="col-6 col-sm-3 col-lg-2 offset-md-0 offset-lg-3 mt-2 mb-2">
					<!--					<button class="btn button1 login">Sign In</button>-->
					<button class="btn button2 login">Sign In</button>
				</div>
				<div class="col-6 col-sm-3 col-lg-2 mt-2 mb-2">
					<!--					<button class="btn button1 register">Register</button>-->
					<button class="btn button2 register">Register</button>
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
							<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/car1.png"); ?>')"></div>
							<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/car2.png"); ?>')"></div>
							<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/car3.png"); ?>')"></div>
							<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/bike1.png"); ?>')"></div>
						</div>
						<div class="card-body">
							<div class="row">
								<div class="col-12 mb-4 text-center">
									<div class="card-title">Product Title</div>
									<div class="details">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam dolorum assumenda</div>
								</div>
								<div class="col-7">

									<div class="seller"><span class="seller-lbl">Seller Name: </span><span class="seller-val">Jhon Doe</span></div>
									<div class="seller"><span class="rating-lbl">Seller Rating: </span>
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
										<div class="phone"><span class="phone-lbl">Phone: </span><span class="phone-val">123456789</span></div>
										<div class="mobile"><span class="mobile-lbl">Mobile: </span><span class="mobile-val">09123456789</span></div>
										<div class="whatsapp"><span class="whatsapp-lbl">WhatsApp: </span><span class="whatsapp-val">09123456789</span></div>
									</details>

								</div>
								<div class="col-5">
									<div class="location"><span class="location-lbl"></span><span class="location-val">Syria, Damascus</span></div>
									<div class="status"><span class="status-lbl"></span><span class="status-val">New</span></div>
									<div class="negotiable"><span class="negotiable-lbl">Price: </span><span class="negotiable-val">None negotiable</span></div>
									<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
									<!--									<div class="likes"><span class="likes-lbl">Likes </span><span class="likes-val">350 </span></div>-->
									<!--									<div class="rating">stars</div>-->
									<div class="date"><span class="date-lbl"></span><span class="date-val">12/12/2015</span></div>
								</div>
								<input type="text" class="form-control form-control-sm mt-2" placeholder="Send a message to the seller">
							</div>
							<div class="price">
								<div class="price-val">3000$</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn button2">Contact Seller</button>
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
					<button type="submit" class="btn button2 submit">Register</button>
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
					<button type="submit" class="btn button2 submit">Sign In</button>

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
					<form action="">

						<div class="form-group">
							<input type="text" class="form-control" name="name" placeholder="Item's name">
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

						<div class="form-group">
							<input type="text" class="form-control" name="price" placeholder="Item's price">
						</div>

						<div class="form-group">
							<input type="text" class="form-control" name="location" placeholder="Item's location">
						</div>

						<div class="form-group">
							<textarea class="form-control" name="location" rows="4" placeholder="Add description"></textarea>
						</div>

						<div id="fileuploader-ad">Upload</div>

						<div class="">
							<input id="terms-agree" type="checkbox" name="terms_agree" class="" required value="false">
							<label for="terms-agree" class="">
								<span class="">I agree to the dealat <a href="" target="_blank">Terms of Service</a></span>

								<span class="d-none text-danger">(required) <i class="fas fa-exclamation"></i></span>
							</label>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="submit" class="btn button2 submit">Submit Ad</button>
				</div>
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
							<input type="text" class="form-control" name="user_name" placeholder="User Name">
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
