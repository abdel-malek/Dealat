<?php $this->load->view('header'); ?>

<body class="profile-page">
	<?php $this->load->view('common'); ?>

	<section class="user-details mt-4 mb-2">
		<div class="container">
			<div class="row ">
				<div class="col-md-2">
					<div class="default-img m-auto" style="background-color: #fff">
						<div class="image" style="background-image: url('<?php echo base_url("assets/images/Dealat%20logo%20red.png"); ?>')">
						</div>
					</div>

				</div>
				<div class="col-md-2 text-center mt-2">
					<div class="name">User Name</div>
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
				</div>
				<div class="col-md-7 offset-md-1 mt-2">
					<div class="phone"><span class="phone-lbl">Phone: </span><span class="phone-val">123456789</span></div>
					<div class="mobile"><span class="mobile-lbl">Mobile: </span><span class="mobile-val">09456123787</span></div>
					<div class="email"><span class="email-lbl">Email: </span><span class="email-val">name@gmail.com</span></div>
				</div>
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
				<li class='tab'><a href="#my-ads">My Ads</a></li>
				<li class='tab'><a href="#favorites">Favorites</a></li>
				<li class='tab'><a href="#chats">Chats</a></li>
			</ul>
			<div id="my-ads" class="my-ads">
				<section class="products">
					<div class="container main">
						<div class="row no-gutters">
							<div class="col-12">
								<div class="card mb-4">
									<div class="container">
										<div class="overlay">
											<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
										</div>
										<div class="row no-gutters">
											<div class="col-md-6">
												<div class="card-left">
													<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/motor1.jpg"); ?>')"></div>

													<div class="price">
														<div class="price-val">3000$</div>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="card-body">
													<div class="card-title mb-1">Product Title</div>
													<div class="details mb-2">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Lorem ipsum dolor sit amet,</div>
													<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
<!--													<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>-->

													<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
													<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
													<div class="date"><span class="date-lbl">Expires </span><span class="date-val">01/01/2016</span></div>
													<div class="negotiable"><span class="negotiable-lbl">Price: </span><span class="negotiable-val">None negotiable</span></div>
													<div class="btn button2 delete-ad mt-2 mr-2"><span>Delete </span><i class="far fa-trash-alt fa-lg"></i></div>
									<div class="btn button2 edit-ad mt-2"><span>Edit </span><i class="fas fa-pencil-alt fa-lg"></i></div>
												</div>
<div class="status d-none"><span class="status-val">Accepted </span><span><i class="far fa-check-circle"></i></span></div>
<div class="status d-none"><span class="status-val">Rejected </span><span><i class="fas fa-ban"></i></span></div>
<div class="status"><span class="status-val">Expired </span><span><i class="fas fa-history"></i></span></div>
										
											</div>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>

				</section>
			</div>
			<div id="favorites" class="favorites">
				<section class="products">

					<div class="container-fluid main">
						<div class="row">

							<div class="col-md-6">
								<div class="card mb-4">
									<div class="container">
										
										<div class="row no-gutters">
											<div class="col-md-6">
												<div class="card-left">
												<div class="overlay">
											<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
										</div>
													<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/motor1.jpg"); ?>')"></div>

													<div class="price">
														<div class="price-val">3000$</div>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="card-body">
													<div class="card-title mb-1">Product Title</div>
													<div class="details mb-2">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Lorem ipsum dolor sit amet,</div>
													<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
													<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>

													<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
													<!--									<div class="rating">stars</div>-->


													<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
													<div class="negotiable"><span class="negotiable-lbl">Price: </span><span class="negotiable-val">None negotiable</span></div>
													<div class="seller"><span class="seller-lbl">Seller: </span><span class="seller-val">Jhon Doe</span></div>
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="col-md-6">
								<div class="card mb-4">
									<div class="container">
										<div class="overlay">
											<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
										</div>
										<div class="row no-gutters">
											<div class="col-md-6">
												<div class="card-left">
													<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/bike1.jpg"); ?>')"></div>

													<div class="price">
														<div class="price-val">3000$</div>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="card-body">
													<div class="card-title mb-1">Product Title</div>
													<div class="details mb-2">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Lorem ipsum dolor sit amet,</div>
													<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
													<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>

													<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
													<!--									<div class="rating">stars</div>-->
													<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
													<div class="negotiable"><span class="negotiable-lbl">Price: </span><span class="negotiable-val">None negotiable</span></div>
													<div class="seller"><span class="seller-lbl">Seller: </span><span class="seller-val">Jhon Doe</span></div>
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
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
										<img src="<?php echo base_url("assets/images/Dealat%20logo%20white%20background.png"); ?>" width="50px" alt="">
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
						<li>
							<div class="row">
								<div class="col-2">
									<div class="chat-img">
										<img src="<?php echo base_url("assets/images/Dealat%20logo%20white%20background.png"); ?>" width="50px" alt="">
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
<?php $this->load->view('common'); ?>
	<?php $this->load->view('footer'); ?>
