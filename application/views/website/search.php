<?php $this->load->view('header'); ?>

<body class="search-page">
	<?php $this->load->view('common'); ?>

	<section class="search">
		<div class="container">
			<header>
				<div class="row align-items-center text-center">
					<div class="col-sm-3"><span class="logo"><img src="<?php echo base_url("assets/images/Dealat%20logo%20white%20background.png"); ?>" width="60px" alt=""></span></div>
					<div class="col-md-3"><button class="btn button2 w-75 filter">Filter</button></div>
					<div class="col-md-5 "><input type="search" class="form-control" placeholder="Search"></div>
				</div>
				<!--				<button class="btn button2 ad">Place an Ad</button>-->
			</header>
		</div>
	</section>

	<section class="products">

		<div class="container-fluid main">
			<div class="row no-gutters">
				<div class="col-md-10 left-col">
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
						
						<div class="col-md-6">
							<div class="card mb-4">
								<div class="container">
									<div class="overlay">
										<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
									</div>
									<div class="row no-gutters">
										<div class="col-md-6">
											<div class="card-left">
												<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/car1.png"); ?>')"></div>

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
				<div class="col-md-2 right-col">
					<aside class="banners">
						<div class="banner">
							<a href=""><img src="images/af-coinbase-2.jpg" class="img-fluid" alt=""></a>
						</div>
						<div class="banner">
							<a href=""><img src="images/af-coinbase-2.jpg" class="img-fluid" alt=""></a>
						</div>
					</aside>
				</div>
			</div>
		</div>
	</section>
	<?php $this->load->view('common'); ?>
	<?php $this->load->view('footer'); ?>