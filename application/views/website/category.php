<body class="home-page">
	<?php $this->load->view('website/common'); ?>

	<section>
		<div class="container-fluid">
			<div class="ads-slider">
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
					<div class="col-6 col-sm-6 col-md-3 col-lg-2">
						<div class="download android text-center"><a href=""><img src="<?php echo base_url("assets/images/google-play-badge.png"); ?>" height="45px" alt=""></a></div>
					</div>
					<div class="col-6 col-sm-6 col-md-3 col-lg-2">
						<div class="download ios  text-center"><a href=""><img src="<?php echo base_url("assets/images/ios%20en%20black.png"); ?>" height="45px" alt=""></a></div>
					</div>
					<div class="col-sm-12 col-md-4 col-lg-5 offset-lg-1 offset-xl-0 mt-2">
						<div class="search-wrapper">
							<input type="search" class="form-control" placeholder="Search">
							<span class="icon"><i class="fas fa-search"></i></span>
						</div>
					</div>

				</div>

			</header>
		</div>
	</section>

	<section class="products">
		<div class="categories">
			<div class="category-slider slick-slider">
			
			  <?php if($main_categories != null): foreach ($main_categories as $category): ?>
				<div class="category">
					<img src="<?php echo base_url($category->web_image); ?>" width="60px" alt="<?php echo $category->category_name ?>">
					<h6><?php echo $category->category_name ?></h6>
				</div>
			  <?php endforeach; ?>
			  <?php endif; ?>
				
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
