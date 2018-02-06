<?php $this->load->view('header'); ?>

<body class="home-page">
	<?php $this->load->view('common'); ?>

	<section>
		<div class="container-fluid">
			<div class="ads-slider">
				<!--
				<div class="slide ">
					<img class="d-block w-100" src="images/slider1.png" alt="First slide">
				</div>
				<div class="slide ">
					<img class="d-block w-100" src="images/banner-772x250.jpg" alt="Second slide">
				</div>
				<div class="slide">
					<img class="d-block w-100" src="images/slider1.png" alt="Third slide">
				</div>
-->
				<div class="slide">
					<a href="">
						<div class="ad-image" style="background-image: url('<?php echo base_url("assets/images/slider1.png"); ?>')"></div>
					</a>
				</div>
				<div class="slide">
					<a href="">
						<div class="ad-image" style="background-image: url('<?php echo base_url("assets/images/banner-772x250.jpg"); ?>')"></div>
					</a>
				</div>
				<div class="slide">
					<a href="">
						<div class="ad-image" style="background-image: url('<?php echo base_url("assets/images/slider1.png"); ?>')"></div>
					</a>
				</div>
			</div>
		</div>
	</section>

	<section class="search">
		<div class="container">
			<header>
				<div class="row align-items-center">
					<div class="col-0 col-md-2">
						<span class="logo"><img src="<?php echo base_url("assets/images/Dealat%20logo%20white%20background.png"); ?>" width="60px" alt=""></span>
					</div>
					<!--
					<div class="col-md-5 col-md-5">
						<div class="download android"><a href=""><button class="btn button1"><i class="fab fa-android fa-lg"></i> Download Android App</button></a></div>
						<div class="download ios"><a href=""><button class="btn button1"><i class="fab fa-apple fa-lg"></i> Download ios App</button></a></div>
					</div>
-->
					<div class="col-6 col-sm-6 col-md-3 col-lg-2">
						<div class="download android text-center"><a href=""><img src="<?php echo base_url("assets/images/google-play-badge.png"); ?>" height="45px" alt=""></a></div>
					</div>
					<div class="col-6 col-sm-6 col-md-3 col-lg-2">
						<div class="download ios  text-center"><a href=""><img src="<?php echo base_url("assets/images/ios%20en%20black.png"); ?>" height="45px" alt=""></a></div>
					</div>

					<!--
					<div class="col-md-5 col-md-5">
						<div class="download android"><a href=""><img src="images/google-play-badge.png" height="45px" alt=""></a></div>
						<div class="download ios"><a href=""><img src="images/ios%20en%20black.png" height="45px" alt=""></a></div>
					</div>
-->

					<div class="col-sm-12 col-md-4 col-lg-5 offset-lg-1 offset-xl-0 mt-2">
						<div class="search-wrapper">
							<input type="search" class="form-control" placeholder="Search">
							<span class="icon"><i class="fas fa-search"></i></span>
						</div>
					</div>

				</div>

				<!--				<button class="btn button2 ad">Place an Ad</button>-->
			</header>
		</div>
	</section>

	<section class="products">
		<div class="categories">
			<div class="category-slider slick-slider">
				<div class="category">
					<img src="images/Categories/Art%20and%20music.png" width="60px" alt="Art and Music">
					<h6>Art and Music</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/Clothes.png"); ?>" width="60px" alt="Clothes">
					<h6>Clothes</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/electronics.png"); ?>" width="60px" alt="Electronics">
					<h6>Electronics</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/furniture.png"); ?>" width="60px" alt="Furniture">
					<h6>Furniture</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/jobs.png"); ?>" width="60px" alt="Jobs">
					<h6>Jobs</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/kids.png"); ?>" width="60px" alt="Kids">
					<h6>Kids</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/mobile%20and%20mobile%20accessories.png"); ?>" width="60px" alt="Mobile and Mobile Accessories">
					<h6>Mobile</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/pets.png"); ?>" width="60px" alt="Pets">
					<h6>Pets</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/real%20estate.png"); ?>" width="60px" alt="Real Estate">
					<h6>Real Estate</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/sports.png"); ?>" width="60px" alt="Sports">
					<h6>Sports</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/vehicls.png"); ?>" width="60px" alt="Vehicles">
					<h6>Vehicles</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/Clothes.png"); ?>" width="60px" alt="Clothes">
					<h6>Clothes</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/electronics.png"); ?>" width="60px" alt="Electronics">
					<h6>Electronics</h6>
				</div>
				<div class="category"><img src="<?php echo base_url("assets/images/Categories/furniture.png"); ?>" width="60px" alt="Furniture">
					<h6>Furniture</h6>
				</div>
			</div>
		</div>

		<div class="container-fluid main">
			<div class="row no-gutters">
				<div class="col-md-10 left-col">
					<div class="controls">
						<div class="category-name">Vehicles</div>
						<div class="nav-wrapper">

							<button class="nav-scroller prev d-none">
							<i class="fas fa-angle-left fa-lg"></i>
						</button>
							<button class="nav-scroller next d-none">
							<i class="fas fa-angle-right fa-lg"></i>
						</button>
							<ul>
								<li class="selected" data-filter="all">All</li>
								<li data-filter=".cars">Cars</li>
								<li data-filter=".bicycle">Bicycles</li>
								<li data-filter=".motors">Motors</li>
								<li data-filter=".accessories">Cars Accessories</li>
							</ul>
						</div>
					</div>
					<div class="row ">
						<!--						<button class="btn button2 ad invisible">Place an Ad</button>-->
						<div class="col-sm-6 col-lg-4 mix motors">
							<div class="card mb-4">
									<div class="overlay">
										<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
									</div>
									<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/motor1.png"); ?>')">

									</div>
								<div class="card-body">
									<div class="row">
										<div class="col-7 mt-2">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl"></span><span class="location-val">Damascus</span></div>
										</div>
										<div class="col-5 mt-2">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<div class="clearfix"></div>
											<div class="date"><span class="date-lbl"></span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
									<div class="fav">
										<!--										<span class="text">Add to favorites</span>-->
										<span class="icon" data-added="0" title="Add to favorites"><i class="far fa-star fa-2x"></i></span>
									</div>
								</div>
							</div>
						</div>

						<div class="col-sm-6 col-lg-4 mix cars">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
								</div>
								<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/car1.png"); ?>')"></div>
								<div class="card-body">
									<div class="row">
										<div class="col-7">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
											<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>
										</div>
										<div class="col-5">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<!--									<div class="rating">stars</div>-->
											<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
								</div>
							</div>
						</div>

						<div class="col-sm-6 col-lg-4 mix bicycle">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
								</div>
								<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/bike1.jpg"); ?>')"></div>
								<div class="card-body">
									<div class="row">
										<div class="col-7">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
											<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>
										</div>
										<div class="col-5">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<!--									<div class="rating">stars</div>-->
											<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-lg-4 mix cars">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
								</div>
								<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/car2.jpg"); ?>')"></div>
								<div class="card-body">
									<div class="row">
										<div class="col-7">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
											<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>
										</div>
										<div class="col-5">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<!--									<div class="rating">stars</div>-->
											<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
								</div>
							</div>
						</div>


						<div class="col-sm-6 col-lg-4 mix accessories">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
								</div>
								<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/acc2.jpg"); ?>')"></div>
								<div class="card-body">
									<div class="row">
										<div class="col-7">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
											<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>
										</div>
										<div class="col-5">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<!--TODO ask about rating if stars or like heart-->
											<!--									<div class="rating">stars</div>-->
											<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-lg-4 mix cars">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
								</div>
								<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/car3.png"); ?>')"></div>
								<div class="card-body">
									<div class="row">
										<div class="col-7">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
											<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>
										</div>
										<div class="col-5">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<!--TODO ask about rating if stars or like heart-->
											<!--									<div class="rating">stars</div>-->
											<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-lg-4 mix motors">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
								</div>
								<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/motor2.jpg"); ?>')"></div>
								<div class="card-body">
									<div class="row">
										<div class="col-7">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
											<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>
										</div>
										<div class="col-5">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<!--TODO ask about rating if stars or like heart-->
											<!--									<div class="rating">stars</div>-->
											<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-lg-4 mix accessories">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
								</div>
								<div class="card-img-top" style="background-image: url('<?php echo base_url("assets/images/acc1.jpg"); ?>')"></div>
								<div class="card-body">
									<div class="row">
										<div class="col-7">
											<div class="card-title">Product Title</div>
											<div class="location"><span class="location-lbl">Location: </span><span class="location-val">Syria, Damascus</span></div>
											<div class="status"><span class="status-lbl">Status: </span><span class="status-val">New</span></div>
										</div>
										<div class="col-5">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<!--TODO ask about rating if stars or like heart-->
											<!--									<div class="rating">stars</div>-->
											<div class="date"><span class="date-lbl">Published </span><span class="date-val">12/12/2015</span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">3000$</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 right-col order-first order-md-last">
					<button class="btn button2 place-ad animated infinite pulse ">Place an Ad</button>
					<aside class="banners">
						<div class="banner">
							<button type="button" class="close">
							  <span>&times;</span>
							</button>
							<a href=""><img src="<?php echo base_url("assets/images/af-coinbase-2.jpg"); ?>" class="img-fluid" alt=""></a>

						</div>
						<div class="banner">
							<button type="button" class="close">
							  <span>&times;</span>
							</button>
							<a href=""><img src="<?php echo base_url("assets/images/af-coinbase-2.jpg"); ?>" class="img-fluid" alt=""></a>
						</div>
					</aside>
				</div>
			</div>
		</div>
	</section>
	
	<?php $this->load->view('footer'); ?>

	