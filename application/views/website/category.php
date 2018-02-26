<body class="category-page">
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

	<section class="products">
		<div class="categories">
			<div class="category-slider slick-slider">

				<?php if($main_categories != null): foreach ($main_categories as $category): ?>
				<div class="category" data-category-id="<?php echo $category->category_id; ?>">
					<img src="<?php echo base_url($category->web_image); ?>" width="60px" alt="<?php echo $category->category_name ?>">
					<h6 class="name">
						<?php echo $category->category_name ?>
					</h6>
				</div>
				<?php endforeach; ?>
				<?php endif; ?>

			</div>
		</div>
		
		<div class="container-fluid main">
<div class="sub-categories d-none">
				<div class="container">
					<div class="row">
						
					</div>
				</div>
			</div>
			<div class="row no-gutters">
				<div class="col-md-10 left-col">
					<div class="row mb-4">
						<div class="col-md-2">
							<div class="category-name">
								<?php echo $category_name ?>:</div>
						</div>
						<div class="col-md-6">
							<div class="search-wrapper">
								<input type="search" class="form-control" placeholder="<?php echo $this->lang->line('search'); ?>">
								<span class="icon"><i class="fas fa-search"></i></span>
							</div>
						</div>
						<div class="col-md-3 offset-md-1"><button class="btn button2 w-75 filter"><?php echo $this->lang->line('filter'); ?></button></div>
					</div>
					
					<div class="controls">
						<div class="nav-wrapper">
							<button class="nav-scroller prev d-none">
								<i class="fas fa-angle-left fa-lg"></i>
							</button>
							<button class="nav-scroller next d-none">
								<i class="fas fa-angle-right fa-lg"></i>
							</button>
							<ul>
								<li class="selected" data-filter="all">
									<?php echo $this->lang->line('all'); ?>
								</li>
								<?php if($subcategories!= null && count($subcategories) > 1):?>
								<?php foreach ($subcategories as $key => $category): ?>
								<li data-filter=".<?php echo $category->category_name ?>">
									<?php echo $category->category_name ?>
								</li>
								<?php endforeach; ?>
								<?php else:?>
								<?php endif;?>
							</ul>
						</div>
					</div>
					
					<div class="row ">
						<?php if($ads != null): foreach ($ads as $ad):?>
						<div class="col-sm-6 col-lg-4 mix <?php echo $ad->category_name ?>">
							<div class="card mb-4" data-ad-id="<?php echo $ad->ad_id ?>" data-template-id="<?php echo $ad->tamplate_id ?>">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i>
										<?php echo $this->lang->line('view_details'); ?>
									</div>
								</div>

								<?php if($ad->is_featured != 0): ?>
								<div class="feat"><img src="<?php echo base_url('assets/images/featured/featured-ads-new.png'); ?>" alt=""></div>
								<?php endif; ?>

								<div class="card-img-top" style="background-image: url('<?php echo base_url($ad->main_image); ?>')">
								</div>
								
								<div class="card-body">
									<div class="row">
										<div class="col-7 mt-2">
											<div class="card-title">
												<?php echo $ad->title ?>
											</div>
											<div class="location"><span class="location-lbl"></span><span class="location-val"><?php echo $ad->city_name .'-'.$ad->location_name ?></span></div>
										</div>
										<div class="col-5 mt-2">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<div class="clearfix"></div>
											<div class="date"><span class="date-lbl"></span><span class="date-val"><?php $timestamp = strtotime($ad->publish_date); echo date('d-m-Y',$timestamp); ?></span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">
											<?php echo $ad->price; ?>
										</div>
									</div>
									<div class="fav">
										<!--										<span class="text">Add to favorites</span>-->
										<!--								<span class="icon" data-added="0" title="Add to favorites"><i class="far fa-star fa-2x"></i></span>-->
										<span class="icon" data-added="0" title="Add to favorites"><i class="far fa-heart fa-2x"></i></span>
									</div>
								</div>
							</div>
						</div>
						<?php endforeach ?>
						<?php endif;?>
					</div>
				</div>
				<div class="col-md-2 right-col order-first order-md-last">
					<button class="btn button2 place-ad animated infinite pulse "><i class="fas fa-plus"></i> <?php echo $this->lang->line('place_ad'); ?></button>
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