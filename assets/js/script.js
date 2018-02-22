/*jslint browser: true*/
/*global $, alert, Mustache*/

$(function () {
	//    Loading screen
	$(window).on('load', function () {
		$(".loading-overlay .spinner").fadeOut(1000, function () {
			$(this).parent().fadeOut(500, function () {
				$("body").removeAttr('style');
				$(this).remove();
			});
		});
	});

	$(".controls li").click(function () {
		$(this).siblings("li").removeClass("selected");
		$(this).addClass("selected");
	});

	//get categories and subcategories
	$.ajax({
		type: "get",
		url: base_url + '/api/categories_control/get_nested_categories',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {
			console.log(data);
			alert("error status false");
		} else {
			var i, template, rendered;
			var catData = {
				categories: data.data
			};
			template = $('#ad-modal-categories-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, catData);
			$("#ad-modal .categories-nav .main-categories").append(rendered);
		}
	}).fail(function (response) {
		alert("fail");
	});

	//get cities and areas - types
	$.ajax({
		type: "get",
		url: base_url + '/api/ads_control/get_data_lists',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {
			console.log(data);
			alert("error status false");
		} else {
			console.log(data);
			var i, template, rendered, locData, typesData;
			//locations
			locData = {
				cities: data.data.nested_locations
			};
			template = $('#ad-modal-cities-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, locData);
			$("#ad-modal .locations-nav .cities").append(rendered);

			//types
			typesData = {
				types: data.data.types
			};
			//			console.log(typesData.types);
			template = $('#ad-modal-types-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, typesData);
			$("#ad-modal .types-nav .types").append(rendered);

			for (i in data.data.educations) {
				$('#ad-modal .educations-select').append($('<option>', {
					value: data.data.educations[i].education_id,
					text: data.data.educations[i].name
				}));
			}
			$('select.educations-select')[0].sumo.reload();

			for (i in data.data.schedules) {
				$('#ad-modal .schedules-select').append($('<option>', {
					value: data.data.schedules[i].education_id,
					text: data.data.schedules[i].name
				}));
			}
			$('select.schedules-select')[0].sumo.reload();
		}
	}).fail(function (response) {
		alert("fail");
	});

	$(".home-page .card .card-img-top,.home-page .card .overlay,.category-page .card .card-img-top,.category-page .card .overlay,.profile-page .favorites .card-left, .search-page .card-left").click(function () {
		$("#card-modal").modal("show");
		$(".card-img-slider").slick("refresh");
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

	//view template fields when change category in place ad modal
	$("#ad-modal .categories-nav").on("click", ".last-subcategory", function () {
		var templateId, parentId;
		templateId = $(this).data("templateId");
		parentId = $(this).data("parentId");
		//		$("#ad-modal .template").addClass("d-none");
		$("#ad-modal .template").each(function () {
			$(this).addClass("d-none");

			if ($(this).data("templateId") === templateId) {
				$(this).removeClass("d-none");
			}
		});
		$("#place-ad-form .category-id").val(parentId);
	});

	$("#ad-modal .locations-nav").on("click", ".location", function () {
		var locationId;
		locationId = $(this).data("locationId");
		$("#place-ad-form .location-id").val(locationId);
	});

	$("button.place-ad").click(function () {
		$("button.ad").addClass("invisible");
		$("#ad-modal").modal("show");
	});

	$("#place-ad-form").submit(function (evnt) {
		evnt.preventDefault();
		evnt.stopImmediatePropagation();

		console.log($(this).serializeArray());
		$.ajax({
			type: "post",
			url: base_url + '/api/ads_control/post_new_ad',
			dataType: "json",
			data: $(this).serialize()
			//			{
			//				category_id: ,
			//				location_id: ,
			//				show_period: ,
			//				title: ,
			//				description: ,
			//				price: ,
			//				main_image:
			//				//ad template parameters
			//			}
		}).done(function (data) {
			if (data.status === false) {
				console.log(data);
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						console.log(errorMessage[node].innerHTML);
						wholeMessage += errorMessage[node].innerHTML;
					} else {
						wholeMessage += '<br>';
					}
				}
				$('#ad-modal .error-message').html(wholeMessage);
			} else {
				//				if ($("#ad-modal .featured input").is(':checked')) {
				//					$("#ad-modal").modal("hide");
				//					setTimeout(function () {
				//						$("#pay-modal").modal("show");
				//					}, 500);
				//				} else {
				//					$("#ad-modal").modal("hide");
				//					setTimeout(function () {
				//						$("#success-modal").modal("show");
				//					}, 500);
				//				}
				//				$("#place-ad-form").trigger("reset");
				//				return false;
				console.log(data);
				alert("success status true");
			}
		}).fail(function (response) {
			console.log(response);
			alert("fail");
		});

	});

	$("#ad-modal .featured").click(function () {
		if ($(this).find("input").is(':checked')) {
			$(this).find(".warning").removeClass("d-none");
		} else {
			$(this).find(".warning").addClass("d-none");
		}
	});


	$(window).scroll(function () {
		if ($(this).scrollTop() > $(".products").offset().top && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
			//			$(".categories").addClass("slider-fixed");	
			$(".categories").css({
				position: "fixed",
				top: 0,
				"z-index": 10
			});
			$(".category-slider img").css("width", "30%");
			if (lang === "ar") {
				$(".category-slider h6").css("font-size", ".7rem");
			} else {
				$(".category-slider h6").css("font-size", ".6rem");
			}

		} else {
			//			$(".categories").removeClass("slider-fixed");	
			//			setTimeout(function () {
			$(".categories").css({
				position: "absolute"
			});
			$(".category-slider img").css("width", "60%");
			if (lang === "ar") {
				$(".category-slider h6").css("font-size", ".8rem");
			} else {
				$(".category-slider h6").css("font-size", ".7rem");
			}
		}

	});

	//remove aside ads
	$("aside.banners .close").click(function () {
		$(this).parents(".banner").fadeOut();
	});

	//display rating in card modal
	var userRateValue = 2,
		i;
	$("#card-modal").on("show.bs.modal", function () {
		$(".rating .rate-group").each(function () {
			if ($(this).data("value") <= userRateValue) {
				$(this).children("label").css("color", "#FFCC36");
			} else {
				$(this).children("label").css("color", "#ddd");
			}
		});
	});

	$(".profile-page .rating .rate-group").each(function () {
		if ($(this).data("value") <= userRateValue) {
			$(this).children("label").css("color", "#FFCC36");
		} else {
			$(this).children("label").css("color", "#ddd");
		}
	});

	//add item to favorite
	$(".card .fav .icon").mouseenter(function () {
		$(this).css("color", "pink");
	});
	$(".card .fav .icon").mouseleave(function () {
		if ($(this).data("added") === 0) {
			$(this).css("color", "#999");
		} else if ($(this).data("added") === 1) {
			$(this).css("color", "pink");
		}
	});

	$(".card .fav .icon").click(function () {
		if ($(this).data("added") === 0) {
			$(this).html('<i class="fas fa-heart fa-2x"></i>');
			$(this).css("color", "pink");
			$(this).data("added", 1);
			//			$(this).siblings(".text").text("Add to favorites");
			$(this).attr("title", "Remove from favorites");
		} else if ($(this).data("added") === 1) {
			$(this).html('<i class="far fa-heart fa-2x"></i>');
			$(this).css("color", "#999");
			$(this).data("added", 0);
			//			$(this).siblings(".text").text("Remove from favorites");
			$(this).attr("title", "Add to favorites");
		}

	});

	//menu nav left and right arrows
	if ($(".controls ul").width() > $(".controls").width()) {
		if (lang === "ar") {
			$(".nav-scroller.prev").removeClass("d-none");
		} else {
			$(".nav-scroller.next").removeClass("d-none");
		}
	}

	$(".nav-scroller.next").click(function () {
		$(".nav-scroller.prev").removeClass("d-none");
		if (($(".controls ul").offset().left + $(".controls ul").width()) <= ($(".controls").offset().left + $(".controls").width())) {
			$(this).addClass("d-none");
			return;
		}
		if (lang === "ar") {
			$(".controls ul").css("margin-right","+=100px");
		} else {
			$(".controls ul").css("margin-left","-=100px");
		}
		if (($(".controls ul").offset().left + $(".controls ul").width()) <= ($(".controls").offset().left + $(".controls").width() + 100)) {
			$(this).addClass("d-none");
		}
	});

	$(".nav-scroller.prev").click(function () {
		console.log("1");
		$(".nav-scroller.next").removeClass("d-none");
		if ($(".controls ul").offset().left >= $(".controls").offset().left) {
			console.log("2");
			$(this).addClass("d-none");
			$(".controls ul").css({
				"margin-left": "0px"
			});
			return;
		}
		console.log("3");
		if (lang === "ar") {
			$(".controls ul").css("margin-right","-=100px");
		} else {
			$(".controls ul").css("margin-left","+=100px");
		}

		if ($(".controls ul").offset().left >= ($(".controls").offset().left - 100)) {
			$(this).addClass("d-none");
			$(".controls ul").css({
				"margin-left": "0px"
			});
		}
	});

	//show/hide social media sidebar
	$(".show-social").click(function () {
		if ($(".social-fixed .icons").data("show") === 0) {
			$(".social-fixed .icons").css("left", "0");
			$(".social-fixed .icons").data("show", 1);
			$(".social-fixed svg").removeClass("fa-angle-right");
			$(".social-fixed svg").addClass("fa-angle-left");
		} else {
			$(".social-fixed .icons").css("left", "-30px");
			$(".social-fixed .icons").data("show", 0);
			$(".social-fixed svg").removeClass("fa-angle-left");
			$(".social-fixed svg").addClass("fa-angle-right");
		}
	});

	$(".profile-page .my-ads .delete-ad").click(function () {
		$("#delete-modal").modal("show");
	});
	$(".profile-page .my-ads .edit-ad").click(function () {
		$("#ad-modal").modal("show");
	});

	$(".category").click(function () {
		var id, name;
		id = $(this).data("categoryId");
		name = $(this).find(".name").text().trim();
		$.ajax({
			type: "get",
			url: base_url + '/api/categories_control/get_subcategories?category_id=' + id,
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {
				console.log("error status false");
			} else {
				$(".home-page .sub-categories, .category-page .sub-categories").remove();
				if (data.data.length > 1) {
					var i, template, rendered, string, sub = [],
						all;
					if (lang === "ar") {
						all = "الكل";
					} else {
						all = 'All';
					}
					console.log(data);
					for (i in data.data) {
						sub.push({
							id: data.data[i].category_id,
							name: data.data[i].category_name
						});
					}
					template = $('#sub-categories-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, sub);
					string = '<div class="sub-categories">' + '<div class="container">' + '<div class="row">' +
						'<div class="col-sm-3">' + '<div class="sub">' + '<li><span class="name all" data-id=' + id + '>' + all +
						'</span></li>' + '</div></div></div></div></div>';
					$(".home-page .main, .category-page .main").prepend(string);
					$(".home-page .sub-categories .row, .category-page .sub-categories .row").append(rendered);
					//	smooth scroll
					$("html, body").animate({
						scrollTop: $(".main").offset().top
					}, 700);
				} else {
					window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + id + "&category_name=" + name;
				}
			}
		}).fail(function (response) {
			alert("fail");
		});

	});

	$(".home-page, .category-page").on("click", ".sub-categories .name", function () {
		var id, name, is_all;
		id = $(this).data("id");
		name = $(this).text().trim();
		window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + id + "&category_name=" + name;
		if ($(this).hasClass("all")) {
			is_all = 1;
			$(".controls").hide();
		}
	});

	//search
	$('input[type="search"]').keypress(function (e) {
		var key, data, query = 0;
		key = e.which;
		query = $(this).val();
		if (key === 13) {
			data = {
				query: $(this).val()
			};
			window.location = base_url + '/search_control?query=' + query;
			//			$.ajax({
			//				type: "get",
			//				//				url: base_url + '/api/ads_control/search?query=' + query,
			//				url: base_url + '/api/ads_control/search',
			//				dataType: "json",
			//				data: data
			//			}).done(function (data) {
			//				if (data.status === false) {
			//					//					console.log("error status false");
			//					alert("error status false");
			//				} else {
			//					alert("success status true");
			//				}
			//			}).fail(function (response) {
			//				alert("fail");
			//			});
		}
	});

	//chat with seller
	$("#card-modal button.chat").click(function () {
		$("#card-modal").modal("hide");
		setTimeout(function () {
			$("#chat-modal").modal("show");
		}, 500);
	});

	//language
	//	$(".language-switch span").click(function (e) {
	////		e.preventDefault();
	//		var language = "en";
	//		if ($(this).hasClass("english")) {
	//			language = "en";
	//		} else if ($(this).hasClass("arabic")) {
	//			language = "ar";
	//		}
	//		$.ajax({
	//			type: "get",
	//			url: base_url + '/users_control_web/change_language?language=' + language,
	//			dataType: "json"
	//		}).done(function (data) {
	//			if (data.status === false) {
	//				location.reload();
	//				console.log(data);
	////				return false;
	//			} else {
	//			}
	//		}).fail(function (response) {
	//			alert("fail");
	//		});
	//	});


});
