/*jslint browser: true*/
/*global $, alert,console,lang, Mustache*/

$(function () {
	//global variables
	var mixer = mixitup('.products');
	var category_id, category_name;
	category_id = $(".category-page").data("categoryId");
	category_name = $(".category-page").data("categoryName");
	//    Loading screen
	$(window).on('load', function () {
		$(".loading-overlay .spinner").fadeOut(500, function () {
			$(this).parent().fadeOut(500, function () {
				$("body").removeAttr('style');
				$(this).remove();
			});
		});
	});

	$("button.register").click(function () {
		$("#register-modal").modal("show");
	});

	$("button.login").click(function () {
		$("#login-modal").modal("show");
	});

	$("button.filter").click(function () {
		//		$("#filter-modal").modal("show");
	});

	function resetPostAd() {
		$("#place-ad-form").trigger("reset");
		if (lang === "ar") {
			$("#ad-modal .categories-nav .select").text("اختر فئة");
			$("#ad-modal .locations-nav .select").text("مكان القطعة");
			$("#ad-modal .types-nav .select").text("اختر النوع");
		} else {
			$("#ad-modal .categories-nav .select").text("Select Category");
			$("#ad-modal .locations-nav .select").text("Item's Location");
			$("#ad-modal .types-nav .select").text("Select type");
		}
		$("#ad-modal .ajax-file-upload-container").empty();
		$("#ad-modal .types-nav").parent(".form-group").addClass("d-none");
		adImgs = [];
		$("#ad-modal .featured .warning").addClass("d-none");
		$('#ad-modal .error-message').addClass("d-none");
	}
	$("#ad-modal").on("show.bs.modal", function () {
		mixer.destroy();
	});

	$("#ad-modal").on("hide.bs.modal", function () {
		resetPostAd();
		mixer = mixitup('.products');
	});

	//change selected in controls
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
			var i, template, rendered, catData;
			catData = {
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

	//get cities and areas - types - educations - schedules
	$.ajax({
		type: "get",
		url: base_url + '/api/ads_control/get_data_lists',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {
			console.log(data);
			alert("error status false");
		} else {
			//			console.log(data);
			var i, j, template, rendered, locData, arr1 = [],
				typesData,
				arr2 = [];
			//locations
			locData = {
				cities: data.data.nested_locations
			};
			template = $('#ad-modal-cities-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, locData);
			$("#ad-modal .locations-nav .cities").append(rendered);

			//types
			for (i in data.data.types) {
				arr1.push(data.data.types[i]);
			}
			for (i in arr1) {
				for (j in arr1[i]) {
					arr2.push(arr1[i][j]);
				}
			}
			typesData = {
				types: arr2
			};
			//			console.log(typesData.types);
			template = $('#ad-modal-types-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, typesData);
			$("#ad-modal .types-nav .types").append(rendered);

			//if type with no models don't open a menu and remove arrow 
			$("#ad-modal .types-nav ul.dropdown-menu").each(function () {
				if ($(this).html().trim() === "") {
					$(this).siblings(".dropdown-item").removeClass("dropdown-toggle");
					$(this).remove();
				}
			});

			//educations
			for (i in data.data.educations) {
				$('#ad-modal .educations-select').append($('<option>', {
					value: data.data.educations[i].education_id,
					text: data.data.educations[i].name
				}));
			}
			$('select.educations-select')[0].sumo.reload();

			//schedules
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

	//card modal
	$(".home-page .card .card-img-top,.home-page .card .overlay,.category-page .card .card-img-top,.category-page .card .overlay,.profile-page .favorites .card-left, .search-page .card-left").click(function () {
		$("#card-modal .card").remove();
		$.ajax({
			type: "get",
			url: base_url + '/api/ads_control/get_ad_details',
			dataType: "json",
			data: {
				ad_id: $(this).parents(".card").data("adId"),
				template_id: $(this).parents(".card").data("templateId")
			}
		}).done(function (data) {
			if (data.status === false) {
				console.log(data);
				alert("error status false");
			} else {
				//				console.log(data);
				var adData, negotiable, automatic, status, furniture, type, i, template, rendered, templateId;
				if (data.data.is_negotiable === "0") {
					if (lang === "ar") {
						negotiable = "غير قابل للتفاوض";
					} else {
						negotiable = "Not negotiable";
					}

				} else {
					if (lang === "ar") {
						negotiable = "قابل للتفاوض";
					} else {
						negotiable = "Negotiable";
					}
				}
				if (data.data.is_automatic === "0") {
					if (lang === "ar") {
						automatic = "لا";
					} else {
						automatic = "No";
					}

				} else {
					if (lang === "ar") {
						automatic = "نعم";
					} else {
						automatic = "Yes";
					}
				}
				if (data.data.with_furniture === "0") {
					if (lang === "ar") {
						furniture = "لا";
					} else {
						furniture = "No";
					}

				} else {
					if (lang === "ar") {
						furniture = "نعم";
					} else {
						furniture = "Yes";
					}
				}
				if (data.data.is_new === "0") {
					if (lang === "ar") {
						status = "مستعمل";
					} else {
						status = "Used";
					}

				} else {
					if (lang === "ar") {
						status = "جديد";
					} else {
						status = "New";
					}
				}

				adData = {
					ad: data.data,
					date: data.data.publish_date.split(' ')[0],
					negotiable: negotiable,
					automatic: automatic,
					status: status,
					furniture: furniture
				};

				template = $('#ad-details-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, adData);
				$("#card-modal .modal-body").append(rendered);
				$('#card-modal .card-img-slider').slick({
					infinite: true,
					slidesToShow: 1,
					mobileFirst: true,
					swipeToSlide: true
				});

				$("#card-modal .templates [class*='-val']").each(function () {
					if ($(this).text() === " ") {
						$(this).text(" -");
					}
				});

				templateId = parseInt(data.data.tamplate_id, 10);
				$("#card-modal .template").each(function () {
					if ($(this).data("templateId") === templateId) {

						$(this).removeClass("d-none");
					}
				});

				$("#card-modal").modal("show");
				setTimeout(function () {
					$(".card-img-slider").slick("refresh");
				}, 200);

			}
		}).fail(function (response) {
			alert("fail");
		});
	});

	//post ad modal
	$("button.place-ad").click(function () {
		$("button.ad").addClass("invisible");
		$("#ad-modal").modal("show");
	});

	//view template fields when change category in place ad modal
	$("#ad-modal .categories-nav").on("click", ".last-subcategory", function () {
		var templateId, subId, has_types = 0;
		templateId = $(this).data("templateId");
		subId = $(this).data("categoryId");
		//		$("#ad-modal .template").addClass("d-none");
		$("#ad-modal .template").each(function () {
			$(this).addClass("d-none");

			if ($(this).data("templateId") === templateId) {
				$(this).removeClass("d-none");
			}
		});
		//only display selected template types
		$("#ad-modal .types-nav .type-item").each(function () {
			$(this).addClass("d-none");
			if ($(this).data("templateId") === templateId) {
				has_types = 1;
				$(this).removeClass("d-none");
			}
		});
		if (has_types !== 0) {
			$("#ad-modal .types-nav").parent(".form-group").removeClass("d-none");
		} else {
			$("#ad-modal .types-nav").parent(".form-group").addClass("d-none");
		}
		if (lang === "ar") {
			$("#ad-modal .types-nav .select").text("اختر النوع");
		} else {
			$("#ad-modal .types-nav .select").text("Select type");
		}
		$("#place-ad-form .type-model-id").val("");
		$("#place-ad-form .type-id").val("");
		$("#place-ad-form .category-id").val(subId);

		//change select placeholder
		$("#ad-modal .categories-nav .select").text($(this).text());
	});

	//locations select
	$("#ad-modal .locations-nav").on("click", ".location", function () {
		var locationId;
		locationId = $(this).data("locationId");
		$("#place-ad-form .location-id").val(locationId);
		//change select placeholder
		$("#ad-modal .locations-nav .select").text($(this).text());
	});

	//types select
	$("#ad-modal .types-nav").on("click", ".type-item", function () {
		if ($(this).find("ul").length > 0) {
			//has children models
		} else {
			//has no children models
			var typeId;
			typeId = $(this).data("typeId");
			$("#place-ad-form .type-model-id").val("");
			$("#place-ad-form .type-id").val(typeId);
		}
		//		console.log("type: " + $("#place-ad-form .type-id").val());
		//		console.log("model: " + $("#place-ad-form .type-model-id").val());
		//change select placeholder
		$("#ad-modal .types-nav .select").text($(this).text());
	});

	//model select
	$("#ad-modal .types-nav").on("click", ".model", function () {
		var typeId, typeModelId;
		typeId = $(this).data("typeId");
		typeModelId = $(this).data("typeModelId");
		$("#place-ad-form .type-id").val(typeId);
		$("#place-ad-form .type-model-id").val(typeModelId);
	});

	//featured ad
	$("#ad-modal .featured").click(function () {
		if ($(this).find("input").is(':checked')) {
			$(this).find(".warning").removeClass("d-none");
		} else {
			$(this).find(".warning").addClass("d-none");
		}
	});

	//upload register image
	$("#fileuploader-register").uploadFile({
		//				url: base_url + '/api/ads_control/ad_images_upload',
		multiple: false,
		dragDrop: false,
		fileName: "image",
		acceptFiles: "image/*",
		maxFileSize: 10000 * 1024,
		showDelete: true,
		showPreview: true,
		previewHeight: "100px",
		previewWidth: "100px",
		uploadStr: "Upload Image"
	});

	var adImgs = [];
	//upload ad main image
	$("#fileuploader-ad").uploadFile({
		url: base_url + '/api/ads_control/ad_images_upload',
		multiple: true,
		dragDrop: true,
		fileName: "image",
		acceptFiles: "image/*",
		maxFileSize: 10000 * 1024,
		//see docs for localization(lang)
		showDelete: true,
		//				statusBarWidth:600,
		dragdropWidth: "100%",
		showPreview: true,
		previewHeight: "100px",
		previewWidth: "100px",
		uploadStr: "Upload Images",
		returnType: "json",
		onSuccess: function (files, data, xhr, pd) {
			//			console.log(data);
			//			adImgs.push(data.data.slice(12));
			adImgs.push(data.data);
			//			console.log(adImgs);
			//			$("#ad-modal .main-image").val(data.data.slice(12));
		},
		onError: function (files, status, errMsg, pd) {
			console.log("upload failed");
		},
		deleteCallback: function (data, pd) {
			//			console.log(data.data);

			var arr;
			arr = [data.data];
			//				for (var i = 0; i < data.data.length; i++) {
			$.post(base_url + '/api/ads_control/delete_images', {
					images: arr
				},
				function (resp, textStatus, jqXHR) {
					//Show Message    
					//					alert("File Deleted");
					var i, deleted;
					//					deleted = data.data.slice(12);
					deleted = data.data;
					for (i in adImgs) {
						if (adImgs[i] === deleted) {
							adImgs.splice(i, 1);
						}
					}
					//					console.log(adImgs);
				});
			//				}
		}
	});

	//place ad form submit
	$("#place-ad-form").submit(function (evnt) {
		evnt.preventDefault();
		evnt.stopImmediatePropagation();
		//		console.log(adImgs);
		var i, upladed_imgs = [],
			main_img = "",
			secondary_imgs = [];
		//copy adimgs into uploaded_imgs
		for (i in adImgs) {
			upladed_imgs.push(adImgs[i]);
		}
		main_img = upladed_imgs[0];
		upladed_imgs.splice(0, 1);
		secondary_imgs = upladed_imgs;
		secondary_imgs = JSON.stringify(secondary_imgs);
		var data = $(this).serializeArray(); // convert form to array
		data.push({
			name: "main_image",
			value: main_img
		}, {
			name: "images",
			value: secondary_imgs
		});
		//		console.log($(this).serializeArray());
		//		console.log(data);
		//		console.log($.param(data));
		$.ajax({
			type: "post",
			url: base_url + '/api/ads_control/post_new_ad',
			dataType: "json",
			data: $.param(data)
			//				$(this).serialize()
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
				$('#ad-modal .error-message').removeClass("d-none");
				$("#ad-modal").animate({
					scrollTop: $("body").offset().top
				}, 500);
			} else {
				//				console.log(data);
				//				if ($("#ad-modal .featured input").is(':checked')) {
				//					$("#ad-modal").modal("hide");
				//					setTimeout(function () {
				//						$("#pay-modal").modal("show");
				//					}, 500);
				//				} else {
				$("#ad-modal").modal("hide");
				setTimeout(function () {
					$("#success-modal").modal("show");
				}, 500);
				setTimeout(function () {
					$("#success-modal").modal("hide");
				}, 2000);
				//				}
				//reset
				resetPostAd();
			}
		}).fail(function (response) {
			console.log(response);
			alert("fail");
		});
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
		$(this).css("color", "#FF87A0");
	});
	$(".card .fav .icon").mouseleave(function () {
		if ($(this).data("added") === 0) {
			$(this).css("color", "#999");
		} else if ($(this).data("added") === 1) {
			$(this).css("color", "#FF87A0");
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
			$(".controls ul").css("margin-right", "+=100px");
		} else {
			$(".controls ul").css("margin-left", "-=100px");
		}
		if (($(".controls ul").offset().left + $(".controls ul").width()) <= ($(".controls").offset().left + $(".controls").width() + 100)) {
			$(this).addClass("d-none");
		}
	});

	$(".nav-scroller.prev").click(function () {
		$(".nav-scroller.next").removeClass("d-none");
		if ($(".controls ul").offset().left >= $(".controls").offset().left) {
			$(this).addClass("d-none");
			$(".controls ul").css({
				"margin-left": "0px"
			});
			return;
		}
		if (lang === "ar") {
			$(".controls ul").css("margin-right", "-=100px");
		} else {
			$(".controls ul").css("margin-left", "+=100px");
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

	//view subcategories when click on category slider
	$(".category").click(function () {

		category_id = $(this).data("categoryId");
		category_name = $(this).find(".name").text().trim();
		$.ajax({
			type: "get",
			url: base_url + '/api/categories_control/get_subcategories?category_id=' + category_id,
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {
				console.log("error status false");
			} else {
				var subData;
				$(".main .sub-categories .row").empty();
				if (data.data.length > 1) {
					var i, template, rendered, string, sub = [],
						all;
					if (lang === "ar") {
						all = "الكل";
					} else {
						all = 'All';
					}
					subData = {
						sub: data.data,
						catId: category_id
					};
					template = $('#sub-categories-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, subData);
					$(".main .sub-categories .row").append(rendered);
					$(".main .sub-categories").removeClass("d-none");
					//	smooth scroll
					$("html, body").animate({
						scrollTop: $(".main").offset().top
					}, 700);
					$(".main .sub-categories").css("background-color", "lightyellow");
					setTimeout(function () {
						$(".main .sub-categories").removeAttr("style");
					}, 1100);
				} else {
					window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + category_id + "&category_name=" + category_name;
				}
			}
		}).fail(function (response) {
			alert("fail");
		});

	});

	//click on subcategory
	$(".home-page, .category-page").on("click", ".sub-categories .name", function () {
		var id, name;
		id = $(this).data("id");
		name = $(this).text().trim();

		if ($(this).hasClass("all")) {
			window.location = base_url + "/home_control/load_ads_by_category_page_all?category_id=" + id + "&category_name=" + name + "&parent_id=" + category_id + "&parent_name=" + category_name;
		} else {
			window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + id + "&category_name=" + name + "&parent_id=" + category_id + "&parent_name=" + category_name;
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
//			if ($(this).parents(".category-page").length > 0) {
//				window.location = base_url + '/search_control?query=' + query + '&category_id=' + category_id ;
//			} else {
//				window.location = base_url + '/search_control?query=' + query;
//			}
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
		//		$("#card-modal").modal("hide");
		//		setTimeout(function () {
		//			$("#chat-modal").modal("show");
		//		}, 500);
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
