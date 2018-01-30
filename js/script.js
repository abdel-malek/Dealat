$(function () {
	$(".controls li").click(function () {
		$(this).siblings("li").removeClass("selected");
		$(this).addClass("selected");
	});

	$(".card").click(function () {
		$(".products .row .ad").addClass("invisible");
		$("#card-modal").modal("show");
	});

	$("button.register").click(function () {
		$("#register-modal").modal("show");
	});

	$("button.login").click(function () {
		$("#login-modal").modal("show");
	});

	$("button.filter").click(function () {
		$("#filter-modal").modal("show");
	});

	$("button.ad,button.ad1").click(function () {
		$("button.ad").addClass("invisible");
		$("#ad-modal").modal("show");
	});

	$("#ad-modal .submit").click(function () {
		$("#ad-modal").modal("hide");
		setTimeout(function(){$("#pay-modal").modal("show");},500);
	});

	$(window).scroll(function () {
		if ($(this).scrollTop() > $(".products .row").offset().top + 100 && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
			$(".products .row .ad").removeClass("invisible");
		} else {
			$(".products .row .ad").addClass("invisible");
		}

		if ($(this).scrollTop() > $(".products").offset().top && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
			//			$(".categories").addClass("slider-fixed");	
			$(".categories").css({
				position: "fixed",
				top: 0,
				"z-index": 10
			});
			$(".slider img").css("width", "30%");
			$(".slider h6").css("font-size", ".6rem");
		} else {
			//			$(".categories").removeClass("slider-fixed");	
			//			setTimeout(function () {
			$(".categories").css({
				position: "absolute"
			});
			$(".slider img").css("width", "60%");
			$(".slider h6").css("font-size", ".7rem");
		}

	});
});
