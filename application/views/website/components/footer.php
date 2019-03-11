</div><!--main wrapper close-->

<footer class="page-footer">
	<script id="footer-template" type="text/template">
		<div class="container">
			<div class="row">
				<div class="col-md-7">
					<div class="section about">
						<h3>
							<?php echo $this->lang->line('about'); ?>
						</h3>
						<!-- <div class="">{{about_us}}</div> -->
						<div class="">{{&about_us}}</div>
					</div>
				</div>
				<div class="col-md-5">
					<div class="section contact">
						<h3>
							<?php echo $this->lang->line('contact_us'); ?>
						</h3>
						<div class="social">
							{{#facebook_link}}<span class="icon facebook"><a href="{{facebook_link}}"><i class="fab fa-facebook-square fa-2x"></i></a></span>{{/facebook_link}} {{#youtube_link}}
							<span class="icon youtube"><a href="{{youtube_link}}"><i class="fab fa-youtube-square fa-2x"></i></a></span>{{/youtube_link}} {{#twiter_link}}
							<span class="icon twitter"><a href="{{twiter_link}}"><i class="fab fa-twitter-square fa-2x"></i></a></span>{{/twiter_link}}
							{{#instagram_link}}
							<span class="icon instagram"><a href="{{instagram_link}}"><i class="fab fa-instagram fa-2x"></i></a></span>{{/instagram_link}}
							
							{{#linkedin_link}}
							<span class="icon linkedin"><a href="{{linkedin_link}}"><i class="fab fa-linkedin fa-2x"></i></a></span>{{/linkedin_link}}
						</div>
						<ul class="info">
							<li><i class="fas fa-envelope"></i> {{email}}</li>
							<li style="direction:  ltr;"><i class="fas fa-phone" data-fa-transform="flip-h"></i> {{&phone}}</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</script>

</footer>

<!--  jQuery library  -->
<script src="<?php echo base_url('assets/js/jquery-3.2.1.min.js'); ?>"></script>
<!--  bootstrap  -->
<script src="<?php echo base_url('assets/js/popper.min.js'); ?>"></script>
<script src="<?php echo base_url('assets/js/bootstrap.min.js'); ?>"></script>
<!--  slick slider  -->
<script src="<?php echo base_url('assets/js/slick.min.js'); ?>"></script>
<script src="<?php echo base_url('assets/js/slick-lightbox.min.js'); ?>"></script>
<script>
//	$(document).ready(function() {
		$('.ads-slider').slick({
			infinite: true,
			slidesToShow: 1,
			mobileFirst: true,
			swipeToSlide: true,
			dots: true,
			adaptiveHeight: false,
			autoplay: true,
			autoplaySpeed: 2000,
			arrows: false,
			speed: 500,
			fade: true,
			cssEase: 'linear',
			pauseOnHover: false
		});

		$('.category-slider').slick({
			infinite: false,
			slidesToShow: 3,
			mobileFirst: true,
			swipeToSlide: true,
			touchThreshold: 20,
			responsive: [{
					breakpoint: 1024,
					settings: {
						slidesToShow: 10,
						swipeToSlide: true
					}
				},
				{
					breakpoint: 600,
					settings: {
						slidesToShow: 6,
						swipeToSlide: true
					}
				},
				{
					breakpoint: 480,
					settings: {
						slidesToShow: 4,
						swipeToSlide: true
					}
				}
			]
		});
	
	$(".loading-overlay .spinner").fadeOut(500, function () {
		$(this).parent().fadeOut(500, function () {
			$("body").removeAttr('style');
			$(this).remove();
		});
	});
//	});
</script>
<!--  mustache  -->
<script src="<?php echo base_url('assets/js/mustache.min.js'); ?>"></script>
<!--  sumoselect  -->
<script src="<?php echo base_url('assets/js/jquery.sumoselect.min.js'); ?>"></script>
<script>
	$(document).ready(function() {
		$("select").SumoSelect({});
	});
</script>
<!--easy tabs-->
<script src="<?php echo base_url('assets/js/jquery.easytabs.min.js'); ?>"></script>
<script>
	$(function() {
		$('#profile-tabs').easytabs({
			tabs: "> ul li"
		});
	});

</script>
<!-- notify sound -->
   <script src="<?php echo base_url() ?>admin_assets/js/jquery.playSound.js"></script>

<!--  multi level dropdown  -->
<script src="<?php echo base_url('assets/js/bootstrap-4-navbar.js'); ?>"></script>

<!--  mixit up  -->
<script src="<?php echo base_url('assets/js/mixitup.min.js'); ?>"></script>

<!--  file upload  -->
<script src="<?php echo base_url('assets/js/jquery.form.js'); ?>"></script>
<script src="<?php echo base_url('assets/js/jquery.uploadfile.min.js'); ?>"></script>

<!--convert url text to hyperlink-->
<script src="<?php echo base_url('assets/js/linkify.min.js'); ?>"></script>  
<script src="<?php echo base_url('assets/js/linkify-string.min.js'); ?>"></script>  

<!-- main js file -->
<script src="<?php echo base_url('assets/js/script.js'); ?>"></script>
<script src="<?php echo base_url('assets/js/profile.js'); ?>"></script>
<!--  datepicker  -->
<script src="<?php echo base_url('assets/js/datepicker.min.js'); ?>"></script>
<script>
	$(document).ready(function() {
		$('[data-toggle="datepicker"]').datepicker({
			format: 'yyyy',
			autoHide: true,
			zIndex: 2000
		});

		$('[data-toggle="birthdate"]').datepicker({
			format: 'yyyy-mm-dd',
			autoHide: true,
			zIndex: 2000
		});
	});
</script>
<!-- WOW -->
<script src="<?php echo base_url('assets/js/wow.min.js'); ?>"></script>
<script>
	new WOW().init();
</script> 
<!-- emoji -->
<script src="<?php echo base_url('assets/js/emojione.min.js'); ?>"></script>
<!--  fontawesome  -->
<script src="<?php echo base_url('assets/js/fontawesome-all.min.js'); ?>" defer></script>

</body>

</html>
