/*jslint browser: true*/
/*global $, alert,console,lang, Mustache, base_url, logged, user_id, hiddenFields*/

$(function () {
	//css
	$(".header-account-logged  ul").css("min-width", $(".header-account-logged").width());

	$(window).resize(function () {
		$(".header-account-logged  ul").css("min-width", $(".header-account-logged").width());
		$(".categories .sub-categories").slideUp("fast");
	});

	//global variables
	var typesData, adImgs, mixer, category_id, category_name, i, template, rendered, subcategories = [];
	adImgs = [];
	mixer = mixitup('.products', {
		selectors: {
			control: '[data-mixitup-control]'
		}
	});
	if ($(".profile-page").length > 0) {
		mixer.destroy();
	}
	category_id = $("body").data("categoryId");
	if (!category_id) {
		//home page
		category_id = 0;
	}
	category_name = $("body").data("categoryName");

	//    Loading screen
	//		$(window).on('load', function () {
	//	$(".loading-overlay .spinner").fadeOut(100, function () {
	//		$(this).parent().fadeOut(500, function () {
	//			$("body").removeAttr('style');
	//			$(this).remove();
	//		});
	//	});
	//		});

	//	$.getScript(base_url + 'assets/js/fontawesome-all.min.js');

	$("button.register").click(function () {
		$("#register-modal").modal("show");
	});

	$("button.login").click(function () {
		$("#login-modal").modal("show");
	});

	$("button.filter").click(function () {
		$("#filter-modal").modal("show");
	});

	function resetPostAd() {
		$("#place-ad-form").trigger("reset");
		if (lang === "ar") {
			$("#ad-modal .categories-nav .select").text("اختر صنف");
		} else {
			$("#ad-modal .categories-nav .select").text("Select Category");
		}
		$("#ad-modal .ajax-file-upload-container").empty();
		//		uploadobjMain.reset();
		if (uploadobjOther) {
			uploadobjOther.reset();
		}
		if (uploadobjvideo) {
			uploadobjvideo.reset();
		}
		fileIndex = 0;

		adImgs = [];
		adVideo = [];
		$("#ad-modal .featured .warning").addClass("d-none");
		$('#ad-modal .error-message').addClass("d-none");
		$("#ad-modal .type-select").parents(".form-group").addClass("d-none");
		$("#ad-modal .model-select").parents(".form-group").addClass("d-none");
		$("#ad-modal .template").each(function () {
			$(this).addClass("d-none");
		});
		$('#ad-modal .city-select')[0].sumo.unSelectAll();
		$('#ad-modal .automatic-select')[0].sumo.unSelectAll();
		$('#ad-modal .status-select')[0].sumo.unSelectAll();
		$('#ad-modal .location-select')[0].sumo.unSelectAll();
		$('#ad-modal .location-select')[0].sumo.disable();
		$('#ad-modal .period-select')[0].sumo.unSelectAll();
		$('#ad-modal .schedules-select')[0].sumo.unSelectAll();
		$('#ad-modal .educations-select')[0].sumo.unSelectAll();
		$('#ad-modal .certificates-select')[0].sumo.unSelectAll();
		$('#ad-modal .model-select')[0].sumo.unSelectAll();
		$('#ad-modal .type-select')[0].sumo.unSelectAll();
		$("#ad-modal input[name='price']").closest(".form-group").removeClass("d-none");
		$("#ad-modal input[name='category_id']").val("");
	}

	function resetEditAd() {
		$("#edit-ad-form").trigger("reset");
		$('#edit-ad-modal .error-message').addClass("d-none");
		$("#edit-ad-modal .type-select").parents(".form-group").addClass("d-none");
		$("#edit-ad-modal .model-select").parents(".form-group").addClass("d-none");
		$("#edit-ad-modal .template").each(function () {
			$(this).addClass("d-none");
		});
		$("#edit-ad-modal .ajax-file-upload-container").empty();
		uploadobjEditMain.reset();
		uploadobjEditOther.reset();
		uploadobjEditVideo.reset();
		$('#edit-ad-modal .city-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .location-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .location-select')[0].sumo.disable();
		$('#edit-ad-modal .period-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .schedules-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .educations-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .certificates-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .model-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .type-select')[0].sumo.unSelectAll();
	}

	function resetFilter() {
		$("#filter-form").trigger("reset");
		if (lang === "ar") {
			$("#filter-modal .categories-nav .select").text("اختر صنف");
		} else {
			$("#filter-modal .categories-nav .select").text("Select Category");
		}
		$("#filter-modal .type-select").parents(".form-group").addClass("d-none");
		$("#filter-modal .model-select").parents(".form-group").addClass("d-none");
		$('#filter-modal .error-message').addClass("d-none");
		$("#filter-modal .template").each(function () {
			$(this).addClass("d-none");
		});
		$('#filter-modal .city-select')[0].sumo.unSelectAll();
		$('#filter-modal .location-select')[0].sumo.unSelectAll();
		$('#filter-modal .location-select')[0].sumo.disable();
		$('#filter-modal .manufacture-date-select')[0].sumo.unSelectAll();
		$('#filter-modal .model-select')[0].sumo.unSelectAll();
		$('#filter-modal .type-select')[0].sumo.unSelectAll();
		$('#filter-modal .automatic-select')[0].sumo.unSelectAll();
		$('#filter-modal .status-select')[0].sumo.unSelectAll();
		$('#filter-modal .schedules-select')[0].sumo.unSelectAll();
		$('#filter-modal .educations-select')[0].sumo.unSelectAll();
		$('#filter-modal .certificates-select')[0].sumo.unSelectAll();
	}

	$("#ad-modal, #filter-modal").on("show.bs.modal", function () {
		mixer.destroy();
	});

	$("#ad-modal").on("hide.bs.modal", function () {
		//delete images if user close modal without posting the ad
		if (adImgs.length > 0) {
			var deleteImgs = [];
			for (i in adImgs) {
				deleteImgs.push(adImgs[i]);
			}
			$.ajax({
				type: "post",
				url: base_url + '/api/items_control/delete_images',
				dataType: "json",
				data: {
					images: JSON.stringify(deleteImgs)
				}
			});
		}
		//delete video if user close modal without posting the ad
		if (adVideo.length > 0) {
			var deleteVid = [];
			for (i in adVideo) {
				deleteVid.push(adVideo[i]);
			}
			$.ajax({
				type: "post",
				url: base_url + '/api/items_control/delete_vedios',
				dataType: "json",
				data: {
					videos: JSON.stringify(deleteVid)
				}
			});
		}
		resetPostAd();
		mixer = mixitup('.products', {
			selectors: {
				control: '[data-mixitup-control]'
			}
		});
	});

	$("#filter-modal").on("hide.bs.modal", function () {
		resetFilter();
		mixer = mixitup('.products', {
			selectors: {
				control: '[data-mixitup-control]'
			}
		});
	});

	$("#edit-ad-modal").on("hide.bs.modal", function () {
		resetEditAd();
	});
	$("#register-modal").on("hide.bs.modal", function () {
		$('#register-modal .error-message').addClass("d-none");
	});

	$("#login-modal").on("hide.bs.modal", function () {
		$('#login-modal .error-message').addClass("d-none");
	});

	$("#verify-modal").on("hide.bs.modal", function () {
		$('#verify-modal .error-message').addClass("d-none");
	});

	//change selected in subcategories controls
	$(".controls li").click(function () {
		$(this).siblings("li").removeClass("selected");
		$(this).addClass("selected");
	});

	//get commercial ads
	$.ajax({
		type: "get",
		url: base_url + '/api/commercial_items_control/get_commercial_items',
		dataType: "json",
		data: {
			category_id: category_id,
			from_web: 1
		}
	}).done(function (data) {
		if (data.status === false) {} else {
			var adData, sliderDefaultImg, sliderImgCount = 0,
				sideImgCount = 0,
				sideDefaultImg;

			sliderDefaultImg = {
				image: "assets/images/default_top.jpg"
			};
			sideDefaultImg = {
				image: "assets/images/default-side_ad.jpg"
			};

			if (data.data.length !== 0) {
				for (i in data.data) {
					if (data.data[i].ad_url) {
						var pos = data.data[i].ad_url.search("http");
						if (pos === -1) {
							data.data[i].ad_url = "http://" + data.data[i].ad_url;
						}
					}
					if (data.data[i].position === "2") {
						//top slider ad
						sliderImgCount += 1;
						template = $('#main-commercial-ads-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$(".ads-slider").append(rendered);
						$(".ads-slider").slick("refresh");
					} else if (data.data[i].position === "1") {
						//side ad
						sideImgCount += 1;
						template = $('#side-commercial-ads-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$("aside.banners").append(rendered);
					}
				}
			}
			if (data.data.length === 0 || sliderImgCount === 0) {
				//view default ads in slider
				template = $('#main-commercial-ads-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, sliderDefaultImg);
				$(".ads-slider").append(rendered);
				$(".ads-slider").slick("refresh");
			}
			if (data.data.length === 0 || sideImgCount === 0) {
				//view default ads in and side
				for (i = 0; i < 2; i += 1) {
					template = $('#side-commercial-ads-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, sideDefaultImg);
					$("aside.banners").append(rendered);
				}
			}
		}
	});

	//get categories and subcategories
	$.ajax({
		type: "get",
		url: base_url + '/api/categories_control/get_nested_categories',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {} else {
			var adsCount = [],
				j, k;
			//save subcategories in array for categories slider
			for (i in data.data) {
				subcategories.push({
					categoryId: data.data[i].category_id,
					children: data.data[i].children
				});
				//calculate ads count for each category
				var count = 0;
				count = count + Number(data.data[i].ads_count);
				if (data.data[i].children) {
					for (j in data.data[i].children) {
						count = count + Number(data.data[i].children[j].ads_count);
						if (data.data[i].children[j].children) {
							for (k in data.data[i].children[j].children) {
								count = count + Number(data.data[i].children[j].children[k].ads_count);
							}
						}
					}
				}
				adsCount.push({
					categoryId: data.data[i].category_id,
					count: count
				});
			}

			$(".category-slider .category").each(function () {
				for (i in adsCount) {
					if ($(this).data("categoryId") == adsCount[i].categoryId) {
						$(this).find(".items-count").text(adsCount[i].count);
						break;
					}
				}

			});
			var catData;
			catData = {
				categories: data.data
			};

			//post ad modal categories
			template = $('#ad-modal-categories-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, catData);
			$("#ad-modal .categories-nav .main-categories").append(rendered);

			//filter modal categories
			template = $('#filter-modal-categories-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, catData);
			$("#filter-modal .categories-nav .main-categories").append(rendered);

			for (i in data.data) {
				if (data.data[i].children) {
					for (j in data.data[i].children) {

						if (data.data[i].children[j].children) {
							//save hidden_fields in array
							for (k in data.data[i].children[j].children) {
								if (data.data[i].children[j].children[k].hidden_fields) {
									hiddenFields.push({
										categoryId: data.data[i].children[j].children[k].category_id,
										hidden: $.parseJSON(data.data[i].children[j].children[k].hidden_fields)
									});
								}
							}
							var catId = data.data[i].children[j].category_id;
							$(".categories-nav .subcategory[data-category-id=" + catId + "]").addClass("dropdown-toggle");
							$(".categories-nav .subcategory[data-category-id=" + catId + "]").removeClass("last-subcategory");

							//add third level subcategories
							template = $('#ad-modal-subcategories-template').html();
							Mustache.parse(template);
							rendered = Mustache.render(template, data.data[i].children[j].children);
							$("#ad-modal .categories-nav .subcategory[data-category-id=" + catId + "]").after(rendered);

							var filterSub = {
								parent: data.data[i].children[j],
								children: data.data[i].children[j].children
							};
							template = $('#filter-modal-subcategories-template').html();
							Mustache.parse(template);
							rendered = Mustache.render(template, filterSub);
							$("#filter-modal .categories-nav .subcategory[data-category-id=" + catId + "]").after(rendered);
						} else {
							if (data.data[i].children[j].hidden_fields) {
								hiddenFields.push({
									categoryId: data.data[i].children[j].category_id,
									hidden: $.parseJSON(data.data[i].children[j].hidden_fields)
								});
							}
						}
					}
				}
			}
		}
	});

	function getCatChildrenTypes(catId) {
		var childrenIds = [],
			j, k;
		for (i in subcategories) {
			if (subcategories[i].categoryId == catId) {
				if (subcategories[i].children) {
					for (j in subcategories[i].children) {
						if (subcategories[i].children[j].children) {
							for (k in subcategories[i].children[j].children) {
								//save children ids in array
								childrenIds.push({
									categoryId: subcategories[i].children[j].children[k].category_id
								});
							}
						} else {
							childrenIds.push({
								categoryId: subcategories[i].children[j].category_id
							});
						}
					}
				}
				break;
			} else {
				if (subcategories[i].children) {
					for (j in subcategories[i].children) {
						if (subcategories[i].children[j].category_id == catId) {
							if (subcategories[i].children[j].children) {
								for (k in subcategories[i].children[j].children) {
									//save children ids in array
									childrenIds.push({
										categoryId: subcategories[i].children[j].children[k].category_id
									});
								}
							}
						}
					}
				}
			}
		}
		return childrenIds;
	}

	//view subcategories when click on category slider
	$(".category").click(function () {

		var clickedCat, sub = [],
			subData, all;
		clickedCat = $(this);
		category_id = $(this).data("categoryId");
		category_name = $(this).find(".name").text().trim();

		for (i in subcategories) {
			if (subcategories[i].categoryId == category_id) {
				sub = subcategories[i].children;
				break;
			}
		}

		$(".categories .sub-categories .sub-list").empty();
		if (sub.length > 1) {
			if (lang === "ar") {
				all = "الكل";
			} else {
				all = 'All';
			}
			subData = {
				sub: sub,
				catId: category_id
			};
			template = $('#sub-categories-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, subData);
			$(".categories .sub-categories .sub-list").append(rendered);
			$(".categories .sub-categories").slideDown("fast");

			$("html, body").animate({
				scrollTop: $(".categories").offset().top
			}, 700);

			$(".categories .sub-categories").css({
				top: "35px",
				left: clickedCat.offset().left + 0.5 * clickedCat.width()
			});

		} else {
			window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + category_id + "&category_name=" + category_name;
		}

	});

	//hide subcategories menu
	$(".main").click(function (e) {
		$(".categories .sub-categories").slideUp("fast");
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

	//get cities and areas - types - educations - schedules - certificates - property_state
	$.ajax({
		type: "get",
		url: base_url + '/api/items_control/get_data_lists',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {} else {
			var j, locData, arr1 = [],
				arr2 = [];
			//locations
			locData = {
				cities: data.data.nested_locations
			};

			//register locations
			for (i in locData.cities) {
				$('#register-modal .city-select, #edit-user-info-modal .city-select, #ad-modal .city-select , #edit-ad-modal .city-select, #filter-modal .city-select').append($('<option>', {
					value: locData.cities[i].city_id,
					text: locData.cities[i].city_name
				}));
			}

			$('#register-modal .city-select')[0].sumo.reload();
			$('#ad-modal .city-select')[0].sumo.reload();
			if ($(".profile-page").length > 0) {
				$('#edit-ad-modal .city-select')[0].sumo.reload();
				$('#edit-ad-modal .location-select')[0].sumo.disable();
			}
			$('#filter-modal .city-select')[0].sumo.reload();

			$('#ad-modal .location-select')[0].sumo.disable();

			$('#filter-modal .location-select')[0].sumo.disable();

			$('#ad-modal  , #edit-ad-modal , #filter-modal ').on("change", ".city-select", function () {
				var cityId, i, j;
				cityId = $(this).find('option:selected').val();
				$('#ad-modal .location-select option:not(.placeholder), #edit-ad-modal .location-select option:not(.placeholder), #filter-modal .location-select option:not(.placeholder)').remove();

				for (i in locData.cities) {
					if (locData.cities[i].city_id === cityId) {
						//check if the selected type include models
						if (locData.cities[i].locations !== null) {
							//
							for (j in locData.cities[i].locations) {
								$('#ad-modal .location-select , #edit-ad-modal .location-select, #filter-modal .location-select').append($('<option>', {
									value: locData.cities[i].locations[j].location_id,
									text: locData.cities[i].locations[j].location_name
								}));
							}
							$('#ad-modal .location-select')[0].sumo.reload();
							$('#filter-modal .location-select')[0].sumo.reload();
							if ($(".profile-page").length > 0) {
								$('#edit-ad-modal .location-select')[0].sumo.reload();
							}

							if ($(this).parents("#ad-modal").length > 0) {
								$('#ad-modal .location-select')[0].sumo.enable();
							} else if ($(this).parents("#edit-ad-modal").length > 0) {
								$('#edit-ad-modal .location-select')[0].sumo.enable();
							} else if ($(this).parents("#filter-modal").length > 0) {
								$('#filter-modal .location-select')[0].sumo.enable();
							}

						} else {}
						break;
					}
				}
			});

			//get types
			//convert types into array
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


			// types
			for (i in typesData.types) {
				$('#filter-modal .type-select').append('<option class="d-none type-full-name" value="' + typesData.types[i].type_id + '" data-template-id="' + typesData.types[i].tamplate_id + '" data-category-id="' + typesData.types[i].category_id + '">' + typesData.types[i].full_type_name + '</option> <option class="d-none type-name" value="' + typesData.types[i].type_id + '" data-template-id="' + typesData.types[i].tamplate_id + '" data-category-id="' + typesData.types[i].category_id + '">' + typesData.types[i].name + '</option>');

				$('#ad-modal .type-select, #edit-ad-modal .type-select').append('<option class="d-none" value="' + typesData.types[i].type_id + '" data-template-id="' + typesData.types[i].tamplate_id + '" data-category-id="' + typesData.types[i].category_id + '">' + typesData.types[i].name + '</option>');
			}

			$('#filter-modal select.type-select')[0].sumo.reload();
			$('#ad-modal select.type-select')[0].sumo.reload();

			if ($(".profile-page").length > 0) {
				$('#edit-ad-modal select.type-select')[0].sumo.reload();
			}

			$('.type-select').change(function () {
				var typeId, i, j;
				typeId = $(this).find('option:selected').val();
				$(this).closest(".modal-body").find('.model-select option:not(.placeholder)').remove();
				for (i in typesData.types) {
					if (typesData.types[i].type_id === typeId) {
						//check if the selected type include models
						if (typesData.types[i].models.length > 0) {
							//only display selected template types
							for (j in typesData.types[i].models) {
								$(this).closest(".modal-body").find('.model-select').append($('<option>', {
									value: typesData.types[i].models[j].type_model_id,
									text: typesData.types[i].models[j].name
								}));
							}
							$(this).closest(".modal-body").find('.model-select').closest(".form-group").removeClass("d-none");
							$(this).closest(".modal-body").find('select.model-select')[0].sumo.reload();
						} else {
							$(this).closest(".modal-body").find('.model-select').closest(".form-group").addClass("d-none");
						}
						break;
					}
				}
			});

			//get show periods
			for (i in data.data.show_periods) {
				$('#ad-modal .period-select,#edit-ad-modal .period-select').append($('<option>', {
					value: data.data.show_periods[i].show_period_id,
					text: data.data.show_periods[i].name
				}));
			}
			$('#ad-modal .period-select')[0].sumo.reload();
			if ($(".profile-page").length > 0) {
				$('#edit-ad-modal .period-select')[0].sumo.reload();
			}
			//educations
			for (i in data.data.educations) {
				$('#ad-modal .educations-select,#edit-ad-modal .educations-select, #filter-modal .educations-select').append($('<option>', {
					value: data.data.educations[i].education_id,
					text: data.data.educations[i].name
				}));
			}

			for (i = 0; i < $('select.educations-select').length; i += 1) {
				$('select.educations-select')[i].sumo.reload();
			}

			//certificates
			for (i in data.data.certificates) {
				$('#ad-modal .certificates-select,#edit-ad-modal .certificates-select, #filter-modal .certificates-select').append($('<option>', {
					value: data.data.certificates[i].certificate_id,
					text: data.data.certificates[i].name
				}));
			}

			for (i = 0; i < $('select.certificates-select').length; i += 1) {
				$('select.certificates-select')[i].sumo.reload();
			}

			//schedules
			for (i in data.data.schedules) {
				$('#ad-modal .schedules-select,#edit-ad-modal .schedules-select, #filter-modal .schedules-select').append($('<option>', {
					value: data.data.schedules[i].schedule_id,
					text: data.data.schedules[i].name
				}));
			}

			for (i = 0; i < $('select.schedules-select').length; i += 1) {
				$('select.schedules-select')[i].sumo.reload();
			}

			//property states
			for (i in data.data.states) {
				$('#ad-modal .property-state-select,#edit-ad-modal .property-state-select, #filter-modal .property-state-select').append($('<option>', {
					value: data.data.states[i].property_state_id,
					text: data.data.states[i].name
				}));
			}

			for (i = 0; i < $('select.property-state-select').length; i += 1) {
				$('select.property-state-select')[i].sumo.reload();
			}
		}
	});

	//filter modal manufacture date
	var dteNow = new Date();
	var intYear = dteNow.getFullYear();
	for (i = 1970; i <= intYear; i += 1) {
		$("#filter-modal .manufacture-date-select").append($('<option>', {
			value: i,
			text: i
		}));
	}
	$('select.manufacture-date-select')[0].sumo.reload();

	//ad modal engine_capacity
	for (i = 1100; i <= 5400; i += 100) {
		$("#ad-modal .engine-capacity-select, #edit-ad-modal .engine-capacity-select").append($('<option>', {
			value: i,
			text: i
		}));
	}

	//reload just for the multiple
	$('#ad-modal select.engine-capacity-select')[0].sumo.reload();
	if ($(".profile-page").length > 0) {
		$('#edit-ad-modal select.engine-capacity-select')[0].sumo.reload();
	}

	var ajaxLoadTimeout;
	$(document).ajaxStart(function () {
		ajaxLoadTimeout = setTimeout(function () {
			$(".loading-overlay1").fadeIn("fast");
		}, 600);

	}).ajaxComplete(function () {
		clearTimeout(ajaxLoadTimeout);
		$(".loading-overlay1").fadeOut("fast");
	});

	function openCardModal() {
		$("#card-modal .card").remove();
		$.ajax({
			type: "get",
			url: base_url + '/api/items_control/get_item_details',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: {
				ad_id: $(this).parents(".card").data("adId"),
				template_id: $(this).parents(".card").data("templateId")
			},
			beforeSend: function () {
				ajaxLoadTimeout = setTimeout(function () {
					$(".loading-overlay1").fadeIn("fast");
				}, 100);
			},
			complete: function () {
				clearTimeout(ajaxLoadTimeout);
				$(".loading-overlay1").fadeOut("fast");
			}
		}).done(function (data) {
			if (data.status === false) {} else {
				var adData, negotiable, automatic, status, furniture, type, templateId;

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
						automatic = "يدوي";
					} else {
						automatic = "Manual";
					}

				} else {
					if (lang === "ar") {
						automatic = "اوتوماتيكي";
					} else {
						automatic = "Automatic";
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

				if (data.data.gender === "1") {
					if (lang === "ar") {
						data.data.gender = "ذكر";
					} else {
						data.data.gender = "Male";
					}

				} else if (data.data.gender === "2") {
					if (lang === "ar") {
						data.data.gender = "أنثى";
					} else {
						data.data.gender = "Female";
					}
				}

				if (!data.data.main_image && !data.data.main_video) {
					data.data.main_image = 'assets/images/default_ad/' + data.data.tamplate_id + '.png';
				}

				//add commas to price
				if (data.data.price) {
					data.data.price = new Intl.NumberFormat().format(data.data.price);
				}
				if (data.data.kilometer) {
					data.data.kilometer = new Intl.NumberFormat().format(data.data.kilometer);
				}
				if (data.data.space) {
					data.data.space = new Intl.NumberFormat().format(data.data.space);
				}
				if (data.data.salary) {
					data.data.salary = new Intl.NumberFormat().format(data.data.salary);
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

				$('.card-img-slider').slickLightbox({
					src: 'src',
					itemSelector: '.card-img-top img'
				});

				if (adData.ad.seller_phone) {
					$("#card-modal .seller-phone").val(adData.ad.seller_phone);
				}

//				console.log(data.data);
				if (data.data.seller_id === user_id) {
					$("#card-modal .chat, #card-modal .report, #card-modal .fav").addClass("d-none");
				} else {
					$("#card-modal .chat, #card-modal .report, #card-modal .fav").removeClass("d-none");
					if (data.data.is_admin === 1) {
						$("#card-modal .chat").addClass("d-none");
					} 
				}

				if (data.data.is_admin === 1) {
					$("#card-modal .seller").addClass("d-none");
				} else {
					$("#card-modal .seller").removeClass("d-none");
				}

				//category job then remove price circle
				if (data.data.tamplate_id === "8") {
					$("#card-modal .price").addClass("d-none");
					$("#card-modal .negotiable").addClass("d-none");
				} else {
					$("#card-modal .price").removeClass("d-none");
					$("#card-modal .negotiable").removeClass("d-none");
				}

				if (!logged) {
					$("#card-modal .fav .icon").data("added", 0);
				}

				$("#card-modal .templates [class*='-val']").each(function () {
					if ($(this).text() === " ") {
						$(this).text(" -");
					}
				});

				var subId = data.data.category_id;
				//put all fields as shown
				$("#card-modal .field").each(function () {
					$(this).removeClass("d-none");
				});

				//hide fields from template according to hiddenFields array
				var hideArr = [];
				for (i in hiddenFields) {
					if (hiddenFields[i].categoryId == subId) {
						hideArr = hiddenFields[i].hidden;
						for (j in hideArr) {
							$("#card-modal .field." + hideArr[j]).addClass("d-none");
						}
					}
				}

				templateId = parseInt(data.data.tamplate_id, 10);
				$("#card-modal .template").each(function () {
					if ($(this).data("templateId") === templateId) {

						$(this).removeClass("d-none");
					}
				});

				if ($(".card .fav .icon").data("added") === 0) {
					$(".card .fav .icon").html('<i class="far fa-heart fa-2x"></i>');
					$(".card .fav .icon").css("color", "#bbb");
				} else if ($(".card .fav .icon").data("added") === 1) {
					$(".card .fav .icon").html('<i class="fas fa-heart fa-2x"></i>');
					$(".card .fav .icon").css("color", "#FF87A0");
				}

				$("#card-modal").modal("show");
				setTimeout(function () {
					$(".card-img-slider").slick("refresh");
				}, 200);
			}
		});
	}

	$("#card-modal").on("click", ".show-contact", function () {
		var phone, whatsapp;
		phone = $(this).parents(".card").find(".seller-phone").val();
		$("#card-modal .details .mobile-val a").text("+963 " + phone);
		$("#card-modal .details .mobile-val a").attr("href", "tel:+963" + phone);
		$("#card-modal .details").removeClass("d-none");
	});


	//card modal - view ad details
	$(".home-page .card .card-img-top,.home-page .card .overlay,.category-page .card .card-img-top,.category-page .card .overlay, .search-page .card-left").click(openCardModal);

	$(".profile-page .favorites").on("click", ".card-left", openCardModal);

	//post ad modal
	$("button.place-ad").click(function () {
		if (!logged) {
			$("#ask-register-modal").modal("show");
			return;
		}
		$("#ad-modal").modal("show");
	});


	$("#ad-modal .categories-nav .select").click(function () {
		$("#ad-modal .SumoSelect").each(function (i) {
			if ($(this).hasClass("open")) {
				$(this).removeClass("open");
				return false;
			}
		});
	});
	$("#filter-modal .categories-nav .select").click(function () {
		$("#filter-modal .SumoSelect").each(function (i) {
			if ($(this).hasClass("open")) {
				$(this).removeClass("open");
				return false;
			}
		});
	});

	//view template fields when change category in place ad modal
	$("#ad-modal .categories-nav").on("click", ".last-subcategory", function () {
		var templateId, subId, has_types = 0;
		templateId = $(this).data("templateId");
		subId = $(this).data("categoryId");

		$('#ad-modal .error-message').addClass("d-none");

		//put all fields as shown
		$("#ad-modal .field").each(function () {
			$(this).removeClass("d-none");
		});

		//hide fields from template according to hiddenFields array
		var hideArr = [];
		for (i in hiddenFields) {
			if (hiddenFields[i].categoryId == subId) {
				hideArr = hiddenFields[i].hidden;
				for (j in hideArr) {
					$("#ad-modal .field." + hideArr[j]).addClass("d-none");
				}
			}
		}

		//only display selected template types
		$("#ad-modal .type-select option").each(function () {
			$(this).addClass("d-none");
			if ($(this).data("categoryId") === subId) {
				has_types = 1;
				$(this).removeClass("d-none");
			}
		});

		$('#ad-modal select.type-select')[0].sumo.reload();
		//display types select only if category is vehicles, mobiles or electronics
		if (has_types !== 0) {
			//		if (templateId === 1 || templateId === 3 || templateId===4) {
			$("#ad-modal .type-select").parents(".form-group").removeClass("d-none");
			$("#ad-modal .model-select").parents(".form-group").addClass("d-none");
		} else {
			$("#ad-modal .type-select").parents(".form-group").addClass("d-none");
			$("#ad-modal .model-select").parents(".form-group").addClass("d-none");
		}

		//vehicles
		if (templateId === 1) {
			if ($.inArray("manufacture_date", hideArr) === -1) {
				$("#ad-modal input[name='manufacture_date']").attr("required", true);
			} else {
				$("#ad-modal input[name='manufacture_date']").removeAttr("required");
			}
			if ($.inArray("kilometer", hideArr) === -1) {
				$("#ad-modal input[name='kilometer']").attr("required", true);
			} else {
				$("#ad-modal input[name='kilometer']").removeAttr("required");
			}
			if ($.inArray("is_automatic", hideArr) === -1) {
				$("#ad-modal select[name='is_automatic']").attr("required", true);
			} else {
				$("#ad-modal select[name='is_automatic']").removeAttr("required");
			}
			if ($.inArray("type_name", hideArr) === -1 && has_types !== 0) {
				$("#ad-modal select[name='type_id']").attr("required", true);
			} else {
				$("#ad-modal select[name='type_id']").removeAttr("required");
			}
		} else {
			$("#ad-modal input[name='manufacture_date']").removeAttr("required");
			$("#ad-modal input[name='kilometer']").removeAttr("required");
			$("#ad-modal select[name='is_automatic']").removeAttr("required");
			$("#ad-modal select[name='type_id']").removeAttr("required");
		}

		if (templateId === 1 || templateId === 3 || templateId === 4 || templateId === 5 || templateId === 6 || templateId === 7 || templateId === 9) {
			$("#ad-modal select[name='is_new']").each(function () {
				$(this).removeAttr("required");
			});
			if ($.inArray("is_new", hideArr) === -1) {
				$("#ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").attr("required", true);
			} else {
				$("#ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").removeAttr("required");
			}
		} else {
			$("#ad-modal select[name='is_new']").each(function () {
				$(this).removeAttr("required");
			});
		}

		//only show upload video in properties
		if (templateId === 2) {
			$("#ad-modal #fileuploader-ad-video").removeClass("d-none");

			if ($.inArray("space", hideArr) === -1) {
				$("#ad-modal input[name='space']").attr("required", true);
			} else {
				$("#ad-modal input[name='space']").removeAttr("required");
			}
			if ($.inArray("rooms_num", hideArr) === -1) {
				$("#ad-modal input[name='rooms_num']").attr("required", true);
			} else {
				$("#ad-modal input[name='rooms_num']").removeAttr("required");
			}
			if ($.inArray("floor", hideArr) === -1) {
				$("#ad-modal input[name='floor']").attr("required", true);
			} else {
				$("#ad-modal input[name='floor']").removeAttr("required");
			}
			if ($.inArray("property_state_name", hideArr) === -1) {
				$("#ad-modal select[name='property_state_id']").attr("required", true);
			} else {
				$("#ad-modal select[name='property_state_id']").removeAttr("required");
			}
		} else {
			$("#ad-modal #fileuploader-ad-video").addClass("d-none");

			$("#ad-modal input[name='space']").removeAttr("required");
			$("#ad-modal input[name='rooms_num']").removeAttr("required");
			$("#ad-modal input[name='floor']").removeAttr("required");
			$("#ad-modal select[name='property_state_id']").removeAttr("required");
		}

		//if category is job remove price and negotiable inputs
		if (templateId === 8) {
			$("#ad-modal input[name='price']").closest(".form-group").addClass("d-none");
			$("#ad-modal input[name='price']").removeAttr("required");
			$("#ad-modal input[name='price']").val("0");
			$("#ad-modal input[name='is_negotiable']").closest(".form-group").addClass("d-none");

			if ($.inArray("education_name", hideArr) === -1) {
				$("#ad-modal select[name='education_id']").attr("required", true);
			} else {
				$("#ad-modal select[name='education_id']").removeAttr("required");
			}
			if ($.inArray("gender", hideArr) === -1) {
				$("#ad-modal select[name='gender']").attr("required", true);
			} else {
				$("#ad-modal select[name='gender']").removeAttr("required");
			}
			if ($.inArray("certificate_name", hideArr) === -1) {
				$("#ad-modal select[name='certificate_id']").attr("required", true);
			} else {
				$("#ad-modal select[name='certificate_id']").removeAttr("required");
			}
		} else {
			$("#ad-modal input[name='price']").closest(".form-group").removeClass("d-none");
			$("#ad-modal input[name='price']").attr("required", true);
			$("#ad-modal input[name='price']").val("");
			$("#ad-modal input[name='is_negotiable']").closest(".form-group").removeClass("d-none");

			$("#ad-modal select[name='education_id']").removeAttr("required");
			$("#ad-modal select[name='gender']").removeAttr("required");
			$("#ad-modal select[name='certificate_id']").removeAttr("required");
		}

		//view the selected category template
		$("#ad-modal .template").each(function () {
			$(this).addClass("d-none");

			if ($(this).data("templateId") === templateId) {
				$(this).removeClass("d-none");
			}
		});

		$("#place-ad-form .category-id").val(subId);
		$("#place-ad-form .template-id").val(templateId);


		//change select placeholder
		var catText1, catText2, subText, text;
		catText1 = $(this).closest(".dropdown-menu").siblings("a").text();
		catText2 = $(this).closest(".dropdown-menu").parents(".dropdown-menu");
		subText = $(this).text();
		if (catText2.length > 1) {
			catText2 = catText2.first().siblings("a").text();
			text = catText2 + " - " + catText1 + " - " + subText;
		} else {
			text = catText1 + " - " + subText;
		}
		$(this).closest(".categories-nav").find(".select").text(text);

		//reset select
		$('#place-ad-form select:not(.city-select, .location-select, .period-select)').each(function () {
			$(this).prop('selectedIndex', 0);
			$(this)[0].sumo.reload();
		});

	});

	//view template fields when change category in filter modal
	$("#filter-modal .categories-nav").on("click", ".last-subcategory", function () {
		var templateId, subId, subName, has_types = 0,
			has_class_all = 0;
		if ($(this).hasClass("all")) {
			has_class_all = 1;
		}
		templateId = $(this).data("templateId");
		$("#filter-form input.template-id").val(templateId);
		subId = $(this).data("categoryId");
		subName = $(this).text();

		//put all fields as shown
		$("#filter-modal .field").each(function () {
			$(this).removeClass("d-none");
		});

		//hide fields from template according to hiddenFields array
		var hideArr = [];
		for (i in hiddenFields) {
			if (hiddenFields[i].categoryId == subId) {
				hideArr = hiddenFields[i].hidden;
				for (j in hideArr) {
					$("#filter-modal .field." + hideArr[j]).addClass("d-none");
				}
			}
		}

		$("#filter-modal .template").each(function () {
			$(this).addClass("d-none");

			if ($(this).data("templateId") === templateId) {
				$(this).removeClass("d-none");
			}
		});

		//only display selected template types
		if (has_class_all) {
			//selected category has class all
			var childrenIds = [];
			childrenIds = getCatChildrenTypes(subId);
			$("#filter-modal .type-select option").addClass("d-none");
			$("#filter-modal .type-select option.type-full-name").each(function () {
				for (i in childrenIds) {
					if ($(this).data("categoryId") == childrenIds[i].categoryId) {
						has_types = 1;
						$(this).removeClass("d-none");
						break;
					}
				}
			});
		} else {
			$("#filter-modal .type-select option").addClass("d-none");
			$("#filter-modal .type-select option.type-name").each(function () {
				if ($(this).data("categoryId") === subId) {
					has_types = 1;
					$(this).removeClass("d-none");
				}
			});
		}

		$('#filter-modal select.type-select')[0].sumo.reload();
		$("#filter-modal .model-select")[0].sumo.unSelectAll();
		$("#filter-modal .model-select")[0].sumo.reload();
		//display types select only if category is vehicles, mobiles or electronics
		if (has_types !== 0) {
			$("#filter-modal .type-select").parents(".form-group").removeClass("d-none");
			$("#filter-modal .model-select").parents(".form-group").addClass("d-none");
		} else {
			$("#filter-modal .type-select").parents(".form-group").addClass("d-none");
			$("#filter-modal .model-select").parents(".form-group").addClass("d-none");
		}

		//change select placeholder
		var catText1, catText2, subText, text;
		catText1 = $(this).closest(".dropdown-menu").siblings("a").text();
		catText2 = $(this).closest(".dropdown-menu").parents(".dropdown-menu");
		subText = $(this).text();
		if (catText2.length > 1) {
			catText2 = catText2.first().siblings("a").text();
			text = catText2 + " - " + catText1 + " - " + subText;
		} else {
			text = catText1 + " - " + subText;
		}
		$(this).closest(".categories-nav").find(".select").text(text);

		$("#filter-form .category-id").val(subId);
		$("#filter-form input[name='category_name']").val(text);

		//reset non-multiple select boxes
		$('#filter-form select:not(.city-select, .location-select, .multiple)').each(function () {
			$(this).prop('selectedIndex', 0);
			$(this)[0].sumo.reload();
		});

		//reset multiple select boxes
		$('#filter-form select.multiple').each(function () {
			//			$(this).prop('selectedIndex', 0);
			$(this)[0].sumo.unSelectAll();
			$(this)[0].sumo.reload();
		});

		$('#filter-form input[type="text"]:not(.search, .price),#filter-form input[type="number"]').each(function () {
			$(this).val("");
		});
	});

	//featured ad
	$("#ad-modal .featured").click(function () {
		if ($(this).find("input").is(':checked')) {
			$(this).find(".warning").removeClass("d-none");
		} else {
			$(this).find(".warning").addClass("d-none");
		}
	});

	//upload ad main image
	var upload, deleteString, mainImage, setAsMain, uploadMain, uploadVideo, adMainImg = [],
		adVideo = [];
	if (lang === "ar") {
		upload = "اختر صور";
		uploadVideo = "اختر فيديو";
		deleteString = "حذف";
		mainImage = "الرئيسية";
		setAsMain = "تحديد كرئيسية";
	} else {
		upload = "Upload images";
		uploadVideo = "Upload video";
		deleteString = "Delete";
		mainImage = "Main image";
		setAsMain = "Set as main";
	}

	var uploadobjMain, uploadobjOther, uploadobjvideo;

	var fileIndex = 0;
	uploadobjOther = $("#fileuploader-ad").uploadFile({
		url: base_url + '/api/items_control/item_images_upload',
		multiple: true,
		dragDrop: false,
		fileName: "image",
		acceptFiles: "image/*",
		maxFileSize: 10000 * 1024,
		maxFileCount: 8,
		showDelete: true,
		dragdropWidth: "100%",
		showPreview: true,
		showProgress: true,
		showFileSize: false,
		previewHeight: "100px",
		previewWidth: "100px",
		deleteStr: deleteString,
		uploadStr: upload,
		returnType: "json",
		extraHTML: function () {
			var html;
			html = '<button class="btn set-main">' + setAsMain + '</button>';
			return html;
		},
		customProgressBar: function (obj, s) {
			this.statusbar = $("<div class='ajax-file-upload-statusbar' data-index='" + fileIndex + "'></div>").width(s.statusBarWidth);
			this.preview = $("<img class='ajax-file-upload-preview' />").width(s.previewWidth).height(s.previewHeight).appendTo(this.statusbar).hide();
			this.filename = $("<div class='ajax-file-upload-filename'></div>").appendTo(this.statusbar);
			this.progressDiv = $("<div class='ajax-file-upload-progress'>").appendTo(this.statusbar).hide();
			this.progressbar = $("<div class='ajax-file-upload-bar'></div>").appendTo(this.progressDiv);
			this.abort = $("<div>" + s.abortStr + "</div>").appendTo(this.statusbar).hide();
			this.cancel = $("<div>" + s.cancelStr + "</div>").appendTo(this.statusbar).hide();
			this.done = $("<div>" + s.doneStr + "</div>").appendTo(this.statusbar).hide();
			this.download = $("<div>" + s.downloadStr + "</div>").appendTo(this.statusbar).hide();
			this.del = $("<div>" + s.deleteStr + "</div>").appendTo(this.statusbar).hide();

			this.abort.addClass("ajax-file-upload-red");
			this.done.addClass("ajax-file-upload-green");
			this.download.addClass("ajax-file-upload-green");
			this.cancel.addClass("ajax-file-upload-red");
			this.del.addClass("ajax-file-upload-red");

			fileIndex += 1;
			return this;
		},
		onSuccess: function (files, data, xhr, pd) {
			adImgs.push(data.data);
			//main image
			$("#ad-modal .ajax-file-upload-statusbar").each(function () {
				if ($(this).data("index") === 0) {
					$(this).find(".set-main").text(mainImage);
					$(this).find(".set-main").attr("disabled", true);
				}
			});
			//store image source
			pd.statusbar.data("src", data.data);
		},
		onError: function (files, status, errMsg, pd) {},
		deleteCallback: function (data, pd) {
			var arr;
			arr = [data.data];
			$.post(base_url + '/api/items_control/delete_images', {
					images: JSON.stringify(arr)
				},
				function (resp, textStatus, jqXHR) {
					var i, deleted;
					deleted = data.data;
					for (i in adImgs) {
						if (adImgs[i] === deleted) {
							adImgs.splice(i, 1);
						}
					}

					$("#ad-modal .ajax-file-upload-statusbar").each(function () {
						for (i in adImgs) {
							if ($(this).data("src") === adImgs[i]) {
								$(this).data("index", Number(i));
							}
						}
						if ($(this).data("index") === 0) {
							$(this).find(".set-main").text(mainImage);
							$(this).find(".set-main").attr("disabled", true);
						}
					});
					fileIndex = $("#ad-modal .ajax-file-upload-statusbar").length;
				});
		}
	});

	$("#ad-modal").on("click", ".set-main", function () {
		var imgIndex, temp;
		imgIndex = $(this).closest(".ajax-file-upload-statusbar").data("index");

		if (imgIndex > 0) {
			//not main
			temp = adImgs[imgIndex];
			adImgs.splice(imgIndex, 1);
			adImgs.unshift(temp);
			//change indexes
			$("#ad-modal .ajax-file-upload-statusbar").each(function () {
				$(this).find(".set-main").text(setAsMain);
				$(this).find(".set-main").attr("disabled", false);
				for (i in adImgs) {
					if ($(this).data("src") === adImgs[i]) {
						$(this).data("index", Number(i))
					}
				}
			});
			$(this).text(mainImage);
			$(this).attr("disabled", true);
		}
	});

	//video
	uploadobjvideo = $("#fileuploader-ad-video").uploadFile({
		url: base_url + '/api/items_control/item_video_upload',
		multiple: false,
		dragDrop: false,
		fileName: "video",
		acceptFiles: "video/*",
		maxFileSize: 20000 * 1024,
		maxFileCount: 1,
		showDelete: true,
		dragdropWidth: "100%",
		showPreview: false,
		showProgress: true,
		showFileSize: false,
		previewHeight: "100px",
		previewWidth: "100px",
		deleteStr: deleteString,
		uploadStr: uploadVideo,
		returnType: "json",
		onSuccess: function (files, data, xhr, pd) {
			adVideo.push(data.data);
		},
		onError: function (files, status, errMsg, pd) {},
		deleteCallback: function (data, pd) {
			var arr;
			arr = [data.data];
			$.post(base_url + '/api/items_control/delete_vedios', {
					videos: JSON.stringify(arr)
				},
				function (resp, textStatus, jqXHR) {
					var i, deleted;
					deleted = data.data;
					for (i in adVideo) {
						if (adVideo[i] === deleted) {
							adVideo.splice(i, 1);
						}
					}
				});
		}
	});

	//place ad form submit
	$("#place-ad-form").submit(function (evnt) {
		evnt.preventDefault();
		evnt.stopImmediatePropagation();

		var i, data, uploaded_imgs = [],
			main_img = "",
			secondary_imgs = [];

		data = $(this).serializeArray();

		//copy adimgs into uploaded_imgs so we don't make any operation on the original adImg array
		if (adImgs.length > 0) {
			for (i in adImgs) {
				uploaded_imgs.push(adImgs[i]);
			}

			data.push({
				name: "main_image",
				value: uploaded_imgs[0]
			});
			uploaded_imgs.splice(0, 1);
			secondary_imgs = uploaded_imgs;
			if (secondary_imgs.length > 0) {
				secondary_imgs = JSON.stringify(secondary_imgs);
				data.push({
					name: "images",
					value: secondary_imgs
				});
			}
		} else {
			//in case no images added
			var templateId = $("#place-ad-form .template-id").val();
			if (templateId == 1) {
				if (lang === "ar") {
					$('#ad-modal .error-message').html("الرجاء إرفاق صورة واحدة للإعلان على الأقل ثم حاول مجدداً");
				} else {
					$('#ad-modal .error-message').html("Please upload at least one image and try again");
				}
				$('#ad-modal .error-message').removeClass("d-none");
				$("#ad-modal").animate({
					scrollTop: $("body").offset().top
				}, 500);
				return false;
			}
		}

		if (adVideo.length > 0) {
			data.push({
				name: "main_video",
				value: adVideo[0]
			});
		}

		//remove commas from numbers before sending the request
		var numbersWithComma = ["price", "kilometer", "space", "salary"];
		for (i in data) {
			if (numbersWithComma.indexOf(data[i].name) > -1) {
				data[i].value = data[i].value.replace(/,/g, '');
			}
		}
		//console.log(data);
		$.ajax({
			type: "post",
			url: base_url + '/api/items_control/post_new_item',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: $.param(data),
			beforeSend: function () {
				//				ajaxLoadTimeout = setTimeout(function () {
				$(".loading-overlay1").fadeIn("fast");
				//				}, 100);
			},
			complete: function () {
				//				clearTimeout(ajaxLoadTimeout);
				$(".loading-overlay1").fadeOut("fast");
			}
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
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
				resetPostAd();

				$("#ad-modal").modal("hide");
				setTimeout(function () {
					if (lang === "ar") {
						$("#success-btn-modal .text").html("تم إنشاء إعلانك بنجاح وهو الآن بانتظار الموافقة عليه");
					} else {
						$("#success-btn-modal .text").html("Your ad has been added successfully and it is now waiting for approval");
					}

					$("#success-btn-modal").modal("show");
				}, 500);
			}
		});
	});

	$(window).scroll(function () {
		if ($(this).scrollTop() > $(".products").offset().top) {
			$(".categories").css({
				position: "fixed",
				top: 0,
				"z-index": 10
			});

			$(".category-slider img").css("width", "30%");
			if (lang === "ar") {
				$(".category-slider h6").css("font-size", ".6rem");
			} else {
				$(".category-slider h6").css("font-size", ".5rem");
			}

		} else {
			$(".categories, .category-slider img, .category-slider h6").removeAttr("style");
		}
		if ($(window).innerWidth() > 768) {
			if ($(".right-col").length > 0 && $(this).scrollTop() > ($(".right-col").offset().top - $(".categories").height())) {
				$(".place-ad").css({
					top: $(".categories").height(),
					right: "20px",
					position: "fixed"
				});
			} else {
				$(".place-ad").removeAttr("style");
			}
		} else {
			$(".place-ad").removeAttr("style");
		}
	});

	//display rating in card modal
	var userRateValue = 2;
	$("#card-modal").on("show.bs.modal", function () {
		$(".rating .rate-group").each(function () {
			if ($(this).data("value") <= userRateValue) {
				$(this).children("label").css("color", "#FFCC36");
			} else {
				$(this).children("label").css("color", "#ddd");
			}
		});
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

	//search
	$('input[type="search"]').keypress(function (e) {
		var key, data, query = 0;
		key = e.which;
		query = $(this).val();

		if (key === 13) {
			data = {
				query: $(this).val()
			};

			sessionStorage.removeItem('bookmark');
			if (category_id) {
				sessionStorage.setItem('bookmark', $.param({
					query: query,
					search: query,
					category_id: category_id,
					category_name: category_name
				}));
			} else {
				sessionStorage.setItem('bookmark', $.param({
					query: query,
					search: query
				}));
			}

			if (category_id) {
				window.location = base_url + '/search_control?query=' + query + '&category_id=' + category_id + '&category_name=' + category_name;
			} else {
				window.location = base_url + '/search_control?query=' + query;
			}
		}
	});

	//bookmark search
	$(".search-page .bookmark").click(function () {
		if (!logged) {
			$("#ask-register-modal").modal("show");
			return;
		}
		var data = sessionStorage.getItem('bookmark');

		$.ajax({
			type: "post",
			url: base_url + '/api/users_control/mark_search',
			dataType: "json",
			data: sessionStorage.getItem('bookmark')
		}).done(function (data) {
			if (data.status === false) {} else {
				if (lang === "ar") {
					$("#success-modal .text").html("تم حفظ البحث بنجاح");
				} else {
					$("#success-modal .text").html("Search saved successfully");
				}
				$("#success-modal").modal("show");
				setTimeout(function () {
					$("#success-modal").modal("hide");
				}, 3000);
			}
		});
	});

	//filter
	$('#filter-form').submit(function (e) {
		e.preventDefault();
		e.stopPropagation();
		var data, data1, query = 0,
			typeModelArr = [],
			typeModelNameArr = "",
			schedulesArr = [],
			schedulesNameArr = "",
			educationArr = [],
			educationNameArr = "",
			certificateArr = [],
			certificateNameArr = "",
			manufactureDateArr = [],
			manufactureDateNameArr = "",
			propertStateArr = [],
			propertStateNameArr = "";
		data = $(this).serializeArray();

		if ($('#filter-modal .manufacture-date-select option:selected').length > 0) {
			$('#filter-modal .manufacture-date-select option:selected').each(function (i) {
				manufactureDateArr.push($(this).val());
				manufactureDateNameArr += $(this).text() + ', ';
				$('#filter-modal .manufacture-date-select')[0].sumo.unSelectItem(i);
			});
			manufactureDateArr = JSON.stringify(manufactureDateArr);
			manufactureDateNameArr = manufactureDateNameArr.slice(0, -2);

			data.push({
				name: "manufacture_date",
				value: manufactureDateArr
			}, { //to be written in bookmark search
				name: "years_name",
				value: manufactureDateNameArr
			});
		}

		if ($('#filter-modal .model-select option:selected').length > 0) {
			$('#filter-modal .model-select option:selected').each(function (i) {
				typeModelArr.push($(this).val());
				typeModelNameArr += $(this).text() + ', ';
				$('#filter-modal .model-select')[0].sumo.unSelectItem(i);
			});
			typeModelArr = JSON.stringify(typeModelArr);
			typeModelNameArr = typeModelNameArr.slice(0, -2);
			data.push({
				name: "type_model_id",
				value: typeModelArr
			}, {
				name: "model_name",
				value: typeModelNameArr
			});
		}

		if ($('#filter-modal .property-state-select option:selected').length > 0) {
			$('#filter-modal .property-state-select option:selected').each(function (i) {
				propertStateArr.push($(this).val());
				propertStateNameArr += $(this).text() + ', ';
				$('#filter-modal .property-state-select')[0].sumo.unSelectItem(i);
			});
			propertStateArr = JSON.stringify(propertStateArr);
			propertStateNameArr = propertStateNameArr.slice(0, -2);
			data.push({
				name: "property_state_id",
				value: propertStateArr
			}, {
				name: "property_state_name",
				value: propertStateNameArr
			});
		}

		if ($('#filter-modal .schedules-select option:selected').length > 0) {
			$('#filter-modal .schedules-select option:selected').each(function (i) {
				schedulesArr.push($(this).val());
				schedulesNameArr += $(this).text() + ', ';
				$('#filter-modal .schedules-select')[0].sumo.unSelectItem(i);
			});
			schedulesArr = JSON.stringify(schedulesArr);
			schedulesNameArr = schedulesNameArr.slice(0, -2);
			data.push({
				name: "schedules_id",
				value: schedulesArr
			}, {
				name: "schedule_name",
				value: schedulesNameArr
			});
		}

		if ($('#filter-modal .educations-select option:selected').length > 0) {
			$('#filter-modal .educations-select option:selected').each(function (i) {
				educationArr.push($(this).val());
				educationNameArr += $(this).text() + ', ';
				$('#filter-modal .educations-select')[0].sumo.unSelectItem(i);
			});
			educationArr = JSON.stringify(educationArr);
			educationNameArr = educationNameArr.slice(0, -2);
			data.push({
				name: "education_id",
				value: educationArr
			}, {
				name: "education_name",
				value: educationNameArr
			});
		}

		if ($('#filter-modal .certificates-select option:selected').length > 0) {
			$('#filter-modal .certificates-select option:selected').each(function (i) {
				certificateArr.push($(this).val());
				certificateNameArr += $(this).text() + ', ';
				$('#filter-modal .certificates-select')[0].sumo.unSelectItem(i);
			});
			certificateArr = JSON.stringify(certificateArr);
			certificateNameArr = certificateNameArr.slice(0, -2);
			data.push({
				name: "certificate_id",
				value: certificateArr
			}, {
				name: "certificate_name",
				value: certificateNameArr
			});
		}

		var templateId = $("#filter-form input.template-id").val();

		//push values as strings for bookmark
		if ($('#filter-modal .template[data-template-id="' + templateId + '"] .status-select option:selected').val() !== "") {
			data.push({
				name: "state_name",
				value: $('#filter-modal .template[data-template-id="' + templateId + '"] .status-select option:selected').text()
			});
		}

		if ($('#filter-modal .city-select option:selected').val() !== "") {
			data.push({
				name: "city_name",
				value: $('#filter-modal .city-select option:selected').text()
			});
		}

		if ($('#filter-modal .location-select option:selected').val() !== "") {
			data.push({
				name: "location_name",
				value: $('#filter-modal .location-select option:selected').text()
			});
		}
		if ($('#filter-modal .type-select option:selected').val() !== "") {
			data.push({
				name: "type_name",
				value: $('#filter-modal .type-select option:selected').text()
			});
		}
		if ($('#filter-modal select[name="with_furniture"] option:selected').val() !== "") {
			data.push({
				name: "furniture_name",
				value: $('#filter-modal select[name="with_furniture"] option:selected').text()
			});
		}
		if ($('#filter-modal .automatic-select option:selected').val() !== "") {
			data.push({
				name: "automatic_name",
				value: $('#filter-modal .automatic-select option:selected').text()
			});
		}

		if ($('#filter-modal .gender-select option:selected').val() !== "") {
			data.push({
				name: "gender_name",
				value: $('#filter-modal .gender-select option:selected').text()
			});
		}

		query = $(this).find('input.search').val();
		if (query !== "") {
			data.push({
				name: "query",
				value: query
			});
		}

		//remove commas from numbers before sending the request
		var numbersWithComma = ["price_min", "kilometer_min", "space_min", "salary_min", "price_max", "kilometer_max", "space_max", "salary_max"];

		for (i in data) {
			if (numbersWithComma.indexOf(data[i].name) > -1) {
				data[i].value = data[i].value.replace(/,/g, '');
			}
		}

		data1 = $.param(data);

		sessionStorage.removeItem('bookmark');
		sessionStorage.setItem('bookmark', data1);

		$.ajax({
			type: "get",
			url: base_url + '/api/items_control/search',
			dataType: "json",
			data: data1
		}).done(function (data) {
			if (data.status === false) {} else {
				window.location = base_url + '/search_control?' + data1;
			}
		});
	});

	//open chat modal when click on chat with seller
	$("#card-modal button.chat").click(function () {
		if (!logged) {
			$("#card-modal").modal("hide");

			setTimeout(function () {
				$("#ask-register-modal").modal("show");
			}, 500);
			return;
		}
		var adId;
		adId = $("#card-modal").find(".card").data("adId");
		$("#chat-form .ad-id").val(adId);
		$("#chat-modal .is-seller").val("0");


		$("#chat-modal .chat-header .ad-name").text($(this).parents(".modal-content").find(".card-title").text());
		$("#chat-modal .chat-header .user-name").text($(this).parents(".modal-content").find(".seller-val").text());

		//get chat message
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_chat_messages',
			dataType: "json",
			data: {
				ad_id: adId
			}
		}).done(function (data) {
			if (data.status === false) {} else {
				$("#chat-modal .chat").empty();
				//				$("#chat-modal .session-id").val(data.data[0]);
				var msgDate
				//if there are previous chat messages
				if (data.data.length > 0) {
					//					$("#chat-modal .session-id").val(data.data[0].chat_session_id);
					msgDate = data.data[0].created_at.split(' ')[0];
					$("#chat-modal .chat").append('<div class="day">' + msgDate + '</div>');
					for (i in data.data) {
						if (data.data[i].to_seller === "1") {
							// message from me to seller
							template = $('#chat-self-template').html();
						} else {
							template = $('#chat-other-template').html();
						}
						//check msg date
						if (data.data[i].created_at.split(' ')[0] !== msgDate) {
							//update msg date
							msgDate = data.data[i].created_at.split(' ')[0];
							$("#chat-modal .chat").append('<div class="day">' + msgDate + '</div>');
						}

						data.data[i].time = new Date(data.data[i].created_at).toLocaleString('en-US', {
							hour: 'numeric',
							minute: 'numeric',
							hour12: true
						});
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$("#chat-modal .chat").append(rendered);
					}
				} else {
					msgDate = new Date();
					year = msgDate.getFullYear();
					month = msgDate.getMonth() + 1;
					day = msgDate.getDate();
					msgDate = year + "-" + month + "-" + day;
					$("#chat-modal .chat").append('<div class="day">' + msgDate + '</div>');
				}
				$("#card-modal").modal("hide");
				setTimeout(function () {
					$("#chat-modal").modal("show");
				}, 500);
			}
		});
	});

	var notSeenMsgs = 0,
		newNotSeenMsgs = 0,
		notSeenInterval;
	var sound_notify_path = site_url + 'admin_assets/definite.mp3';
	//check for not seen messages
	function checkNewMsgs() {
		//get chat messages
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_my_chat_sessions',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: $("#chat-form").serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				//								notSeenMsgs = 0;
				newNotSeenMsgs = 0;
				var newMsgSessions = [];
				for (i in data.data) {
					if ((user_id == data.data[i].seller_id && data.data[i].seller_seen == 0) || (user_id == data.data[i].user_id && data.data[i].user_seen == 0)) {
						newMsgSessions.push(data.data[i].chat_session_id);
						//						notSeenMsgs += 1;
						newNotSeenMsgs += 1;
					}
				}
				var diff = newNotSeenMsgs - notSeenMsgs;
				//				if (notSeenMsgs > 0) {
				if (diff > 0) {
					$.playSound(sound_notify_path);
				}
				if (newNotSeenMsgs > 0) {
					$("header .new-msg").removeClass("d-none");

				} else {
					$("header .new-msg").addClass("d-none");
					$(".profile-page .session .new-msg").addClass("d-none");
				}
				var existingSessionID = [];
				if ($(".profile-page").length > 0) {
					$(".profile-page .sessions .session").each(function (i) {
						existingSessionID.push($(this).data("sessionId"));
						$(this).removeAttr("style");
						$(this).find(".new-msg").addClass("d-none");
						for (i in newMsgSessions) {
							if ($(this).data("sessionId") == newMsgSessions[i]) {
								$(this).css("background-color", "rgba(195, 10, 48, 0.22)");
								$(this).find(".new-msg").removeClass("d-none");
							}
						}
					});

					//if new chat session created append it instantly in profile page
					if ($(".profile-page .sessions .session").length > 0 && $(".profile-page .sessions .session").length < data.data.length) {
						var sessionData, sessionImage, username, existed;

						for (i in data.data) {
							existed = 0;
							for (j in existingSessionID) {
								if (data.data[i].chat_session_id == existingSessionID[j]) {
									existed = 1;
									break;
								}
							}

							if (existed === 0) {
								//add new session
								sessionData = [];
								if (data.data[i].seller_id == user_id) {
									sessionImage = data.data[i].user_pic;
									username = data.data[i].user_name;
								} else {
									sessionImage = data.data[i].seller_pic;
									username = data.data[i].seller_name;
								}
								if (!sessionImage) {
									sessionImage = '/assets/images/user1.jpg';
								}
								sessionData = {
									image: sessionImage,
									username: username,
									details: data.data[i]
								};
								template = $('#chat-sessions-template').html();
								Mustache.parse(template);
								rendered = Mustache.render(template, sessionData);
								$(".profile-page .chats ul.sessions").prepend(rendered);
							}
						}
					}
				}
				notSeenMsgs = newNotSeenMsgs;

			}
		});
		notSeenInterval = setTimeout(checkNewMsgs, 1000);
	}

	if (logged) {
		checkNewMsgs();
	}

	//auto check for new messages for an opened chat session and append it
	function checkLiveSessionMsg() {
		//get chat messages for a live(opened) chat session
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_chat_messages',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: $("#chat-form").serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				//there are previous chat messages
				if ($('#chat-modal .chat li').length > 0 && data.data.length > 0) {
					var lastMsgId, msgDate, startIndex, isSeller;
					lastMsgId = $('#chat-modal .chat li').last().data("msgId");
					msgDate = data.data[data.data.length - 1].created_at.split(' ')[0];
					isSeller = $("#chat-modal .is-seller").val();

					if (data.data[data.data.length - 1].message_id != lastMsgId) {
						//if there are new msgs not displayed yet
						//to know last msg index in data.data array
						for (i = data.data.length - 1; i >= 0; i -= 1) {
							if (data.data[i].message_id == lastMsgId) {
								startIndex = i;
								msgDate = data.data[i].created_at.split(' ')[0];
								break;
							}
						}
						for (i = startIndex + 1; i < data.data.length; i += 1) {
							//check msg date

							if (data.data[i].created_at.split(' ')[0] !== msgDate) {
								//update msg date
								msgDate = data.data[i].created_at.split(' ')[0];
								$("#chat-modal .chat").append('<div class="day">' + msgDate + '</div>');
							}
							data.data[i].time = new Date(data.data[i].created_at).toLocaleString('en-US', {
								hour: 'numeric',
								minute: 'numeric',
								hour12: true
							});


							if ((isSeller === "1" && data.data[i].to_seller === "1") || (isSeller === "0" && data.data[i].to_seller === "0")) {
								template = $('#chat-other-template').html();
							} else {
								//already viewed with send_msg service
								template = $('#chat-self-template').html();
							}

							Mustache.parse(template);
							rendered = Mustache.render(template, data.data[i]);
							$("#chat-modal .chat").append(rendered);
						}
						$("#chat-modal .chat").stop().animate({
							scrollTop: $("#chat-modal .chat")[0].scrollHeight
						}, 300);
					}

				}
			}
		});
		intervalId = setTimeout(checkLiveSessionMsg, 100);
	}

	var intervalId;
	$('#chat-modal').on('shown.bs.modal', function (e) {
		$("#chat-modal .chat").stop().animate({
			scrollTop: $("#chat-modal .chat")[0].scrollHeight
		}, 300);
		$("#chat-modal input[name='msg']").focus();
		//		var lastMsgId;
		intervalId = setTimeout(checkLiveSessionMsg);
	});

	$('#chat-modal').on('hide.bs.modal', function (e) {
		clearTimeout(intervalId);
	});

	$("#chat-form").submit(function (e) {
		e.preventDefault();
		e.stopPropagation();

		$.ajax({
			type: "post",
			url: base_url + '/api/users_control/send_msg',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				data.data.time = new Date(data.data.created_at).toLocaleString('en-US', {
					hour: 'numeric',
					minute: 'numeric',
					hour12: true
				});

				template = $('#chat-self-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, data.data);
				//always rely on checkLiveSessionMsg() function to append msgs even the send ones except if there is no previous msgs (new chat session) then display only the first send
				if ($('#chat-modal .chat li').length === 0) {
					$("#chat-modal .chat").append(rendered);
				}

				$("#chat-form input[name='msg']").val("");
				$("#chat-form input[name='msg']").focus();

				$("#chat-modal .chat").stop().animate({
					scrollTop: $("#chat-modal .chat")[0].scrollHeight
				}, 300);
			}
		});
	});

	//language
	$(".language-switch span").click(function (e) {
		var language = "en";
		if ($(this).hasClass("english")) {
			language = "en";
		} else if ($(this).hasClass("arabic")) {
			language = "ar";
		}
		$.ajax({
			type: "get",
			url: base_url + '/users_control_web/change_language?language=' + language,
			dataType: "json",
			complete: function () {
				location.reload();
			}
		});
	});

	//register
	$('#register-form').submit(function (e) {
		$('#register-modal .error-message').addClass("d-none");
		var pass1, pass2, data, phone, whatsup;
		phone = $(this).find(".phone").val();

		if (phone.length !== 9 || phone[0] !== "9") {
			if (lang === "ar") {
				$('#register-modal .error-message').text("رقم الهاتف يجب أن يتكون من 9 أرقام ويبدأ بالرقم 9");
			} else {
				$('#register-modal .error-message').text("Phone must be exactly 9 characters in length and start with 9");
			}
			$('#register-modal .error-message').removeClass("d-none");
			$("#register-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}

		pass1 = $(this).find(".password").val();
		pass2 = $(this).find(".confirm_password").val();
		if (pass1 !== pass2) {
			if (lang === "ar") {
				$('#register-modal .error-message').text("الرجاء إدخال تأكيد كلمة المرور بشكل صحيح");
			} else {
				$('#register-modal .error-message').text("Please retype password correctly");
			}
			$('#register-modal .error-message').removeClass("d-none");
			$("#register-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}

		whatsup = $(this).find(".whatsup").val();

		if (whatsup.length !== 9 && whatsup !== "") {
			if (lang === "ar") {
				$('#register-modal .error-message').text("رقم الواتساب يجب أن يتكون من 9 أرقام");
			} else {
				$('#register-modal .error-message').text("Whatsapp number must be exactly 9 characters in length");
			}
			$('#register-modal .error-message').removeClass("d-none");
			$("#register-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}
		e.preventDefault();
		e.stopImmediatePropagation();

		//save user data in verify modal to use for login
		$("#verify-modal").find(".phone").val($(this).find(".phone").val());
		localStorage.setItem('phone', $(this).find(".phone").val());
		$("#verify-modal").find(".password").val($(this).find(".password").val());
		localStorage.setItem('password', $(this).find(".password").val());

		$(this).find(".lang").val(lang);

		$.ajax({
			type: "post",
			url: base_url + '/users_control_web/register',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += '-' + errorMessage[node].innerHTML;
					} else {
						wholeMessage += '<br>';
					}
				}
				$('#register-modal .error-message').html(wholeMessage);
				$('#register-modal .error-message').removeClass("d-none");
				$("#register-modal").animate({
					scrollTop: $("body").offset().top
				}, 500);
			} else {
				$("#register-modal").modal("hide");
				setTimeout(function () {
					$("#verify-modal .text").removeClass("d-none");
					$("#verify-modal").modal("show");
				}, 500);
				//reset
				$('#register-form').trigger("reset");
			}
		});
	});

	$("#login-form .verify-registered").click(function () {
		$("#verify-modal").find(".phone").val(localStorage.getItem('phone'));
		$("#verify-modal").find(".password").val(localStorage.getItem('password'));

		$("#login-modal").modal("hide");
		setTimeout(function () {
			$("#verify-modal .text").addClass("d-none");
			$("#verify-modal").modal("show");
		}, 500);
	});

	//verify
	$('#verify-form').submit(function (e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		var data = $(this).serializeArray();
		data.push({
			name: "is_multi",
			value: 1
		});
		$.ajax({
			type: "post",
			url: base_url + '/api/users_control/verify',
			dataType: "json",
			data: $.param(data)
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += '-' + errorMessage[node].innerHTML;

					} else if (errorMessage[node].nodeName === '#text') {
						wholeMessage = data.message;
					} else {
						wholeMessage += '<br>';
					}
				}
				$('#verify-modal .error-message').html(wholeMessage);
				$('#verify-modal .error-message').removeClass("d-none");
			} else {
				//				if($(".profile-page").length > 0){
				//					setTimeout(function () {
				//					$("#notification-modal .text").html("Phone number changed successfully,<br>You will be logged out, please sign in with your new number")
				//					$("#notification-modal").modal("show");
				//				}, 500);
				//				} else{
				//			}
				//login directly
				localStorage.removeItem('phone');
				localStorage.removeItem('password');
				$("#verify-modal").modal("hide");
				setTimeout(function () {
					if (lang === "ar") {
						$("#success-modal .text").html("تم إنشاء حسابك بنجاح");
					} else {
						$("#success-modal .text").html("You have successfully registered");
					}

					$("#success-modal").modal("show");
				}, 500);
				setTimeout(function () {
					$("#success-modal").modal("hide");

				}, 3000);
				$.ajax({
					type: "post",
					url: base_url + '/users_control_web/login',
					dataType: "json",
					data: {
						phone: $("#verify-modal").find(".phone").val(),
						password: $("#verify-modal").find(".password").val()
					}
				}).done(function (data) {
					if (data.status === false) {
						var errorMessage = $.parseHTML(data.message),
							node,
							wholeMessage = '';
						for (node in errorMessage) {
							if (errorMessage[node].nodeName === 'P') {
								wholeMessage += errorMessage[node].textContent;
							} else {
								if (node === "0") {
									wholeMessage = errorMessage[node].textContent;
								} else {
									wholeMessage += '<br>';
								}
							}
						}
						$('#verify-modal .error-message').html(wholeMessage);
						$('#verify-modal .error-message').removeClass("d-none");
					} else {
						window.location = base_url;
					}
				});
			}
		});
	});

	$('#login-form').submit(function (e) {
		var phone = $(this).find(".phone").val();

		if (phone.length !== 9) {
			if (lang === "ar") {
				$('#login-modal .error-message').text("رقم الهاتف يجب أن يتكون من 9 أرقام");
			} else {
				$('#login-modal .error-message').text("Phone must be exactly 9 characters in length");
			}
			$('#login-modal .error-message').removeClass("d-none");
			$("#login-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}
		e.preventDefault();
		e.stopImmediatePropagation();

		$.ajax({
			type: "post",
			url: base_url + '/users_control_web/login',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += errorMessage[node].textContent;
					} else {
						if (node === "0") {
							wholeMessage = errorMessage[node].textContent;
						} else {
							wholeMessage += '<br>';
						}
					}
				}
				$('#login-modal .error-message').html(wholeMessage);
				$('#login-modal .error-message').removeClass("d-none");
			} else {
				if (data.data.cms_logged === 1) {
					window.location = base_url + "/admin/dashboard";
				} else {
					window.location = base_url;
				}
			}
		});

	});

	//favorite icon hover
	$("#card-modal").on("mouseenter", ".card .fav .icon", function () {
		$(this).css("color", "#FF87A0");
	});

	$("#card-modal").on("mouseleave", ".card .fav .icon", function () {
		if ($(this).data("added") === 0) {
			$(this).css("color", "#bbb");
		} else if ($(this).data("added") === 1) {
			$(this).css("color", "#FF87A0");
		}
	});

	//add/remove from favorite
	$("#card-modal").on("click", ".card .fav .icon", function () {
		if (!logged) {
			$("#card-modal").modal("hide");
			setTimeout(function () {
				$("#ask-register-modal").modal("show");
			}, 500);
			return;
		}
		var adId, url;
		adId = $(this).parents(".card").data("adId");
		if ($(this).data("added") === 0) {
			$(this).html('<i class="fas fa-heart fa-2x"></i>');
			$(this).css("color", "FF87A0");
			$(this).data("added", 1);
			$(this).attr("title", "Remove from favorites");
			url = base_url + '/api/items_control/set_as_favorite';
		} else if ($(this).data("added") === 1) {
			$(this).html('<i class="far fa-heart fa-2x"></i>');
			$(this).css("color", "#bbb");
			$(this).data("added", 0);
			$(this).attr("title", "Add to favorites");
			url = base_url + '/api/items_control/remove_from_favorite';
		}

		$.ajax({
			type: "post",
			url: url,
			dataType: "json",
			data: {
				ad_id: adId
			}
		}).done(function (data) {
			if (data.status === false) {} else {}
		});

	});

	$("#notification-modal").on("hidden.bs.modal", function () {
		location.reload();
	});

	//report ad
	$.ajax({
		type: "get",
		url: base_url + '/api/items_control/get_report_messages',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {} else {
			for (i in data.data) {
				$("#report-form .report-select").append($('<option>', {
					value: data.data[i].report_message_id,
					text: data.data[i].msg
				}));
			}
			$("#report-form .report-select")[0].sumo.reload();
		}
	});

	$("#card-modal").on("click", ".report", function () {
		$("#report-form .ad-id").val($("#card-modal .card").data("adId"));
		$("#card-modal").modal("hide");
		setTimeout(function () {
			$("#report-modal").modal("show");
		}, 500);
	});

	$("#report-form").submit(function (e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		$.ajax({
			type: "post",
			url: base_url + '/api/items_control/report_item',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				$("#report-form").trigger("reset");
				$("#report-form .report-select")[0].sumo.unSelectAll();
				$("#report-modal").modal("hide");
				setTimeout(function () {
					if (lang === "ar") {
						$("#success-modal .text").html("تم التبليغ عن الإعلان");
					} else {
						$("#success-modal .text").html("Ad reported successfully");
					}

					$("#success-modal").modal("show");
				}, 500);
				setTimeout(function () {
					$("#success-modal").modal("hide");
				}, 3000);
			}
		});
	});

	$("#report-modal").on("hide.bs.modal", function () {
		$("#report-form").trigger("reset");
		$('#report-modal .report-select')[0].sumo.unSelectAll();
	});

	//footer content
	$.ajax({
		type: "get",
		url: base_url + '/api/data_control/get_about_info',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {} else {
			template = $('#footer-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, data.data);
			$(".page-footer").append(rendered);

			template = $('#social-fixed-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, data.data);
			$(".social-fixed .icons").append(rendered);

			//terms of service
			$("#terms-modal .text").append(data.data.terms);
		}
	});

	$(".terms").click(function (e) {
		e.preventDefault();
		$("#terms-modal").modal("show");
	});

	$("#terms-modal").on("hidden.bs.modal", function () {
		$("body").addClass("modal-open");
	});

	var generatedQR;
	$("#login-modal .qr-login").click(function () {
		$.ajax({
			type: "post",
			url: base_url + '/api/QR_users_control/generate_QR_code',
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {} else {
				var img;
				img = data.data.QR_path;
				generatedQR = data.data.gen_code;
				$("#qr-modal .qr-img img").attr("src", site_url + img);
				$("#login-modal").modal("hide");
				setTimeout(function () {
					$("#qr-modal").modal("show");
				}, 500);
			}
		});

	});

	$("#qr-form input[name=\"secret_code\"]").keyup(function () {
		if ($(this).val().length === 6) {
			$("#qr-modal .submit").removeAttr("disabled");
		} else {
			$("#qr-modal .submit").attr("disabled", true);
		}
	});


	$("#qr-form").submit(function (e) {
		e.preventDefault();

		var data = $(this).serializeArray();
		data.push({
			name: "gen_code",
			value: generatedQR
		});
		$.ajax({
			type: "post",
			url: base_url + '/api/QR_users_control/login_by_qr_code',
			dataType: "json",
			data: $.param(data)
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += errorMessage[node].textContent;
					} else {
						if (node === "0") {
							wholeMessage = errorMessage[node].textContent;
						} else {
							wholeMessage += '<br>';
						}
					}
				}
				$('#qr-modal .error-message').html(wholeMessage);
				$('#qr-modal .error-message').removeClass("d-none");
				$("#qr-modal").animate({
					scrollTop: $("body").offset().top
				}, 500);
			} else {
				if (data.data.cms_logged === 1) {
					window.location = base_url + "/admin/dashboard";
				} else {
					window.location = base_url;
				}
			}
		});
	});

	//notifications
	//admin notifications
	$("header .bell-icon").click(function () {
		$.ajax({
			type: "get",
			url: base_url + '/users_control_web/get_notifications_for_me',
			dataType: "json",
			global: false // this makes sure ajaxStart is not triggered
		}).done(function (data) {
			if (data.status === false) {} else {
				$("header .notifications-dropdown .dropdown-menu").empty();

				for (i in data.data) {
					if (data.data[i].to_user_id) {
						data.data[i].new = "1";
					} else {
						data.data[i].new = "0";
					}
				}
				template = $('#notifications-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, data.data);
				$("header .notifications-dropdown .dropdown-menu").append(rendered);

				$(".notifications-dropdown .number").addClass("d-none");
				notCount = 0;
			}
		});
	});

	var notCount = 0;

	function checkNotCount() {
		$.ajax({
			type: "get",
			url: base_url + '/users_control_web/get_my_notifications_count',
			dataType: "json",
			global: false // this makes sure ajaxStart is not triggered
		}).done(function (data) {
			if (data.status === false) {} else {
				if (Number(data.data) - notCount > 0) {
					if (lang === "ar") {
						$(".notification-alert .text").html("لديك إشعار جديد");
					} else {
						$(".notification-alert .text").html("You have a new notification");
					}
					$(".notification-alert").removeClass("d-none");
					setTimeout(function () {
						$(".notification-alert").addClass("d-none");
					}, 5000);
					notCount = Number(data.data);
					$(".notifications-dropdown .number").text(data.data);
					$(".notifications-dropdown .number").removeClass("d-none");
				}

			}
		});
		setTimeout(checkNotCount, 2000);
	}
	//admin notifications count for user
	if (logged) {
		checkNotCount();
	}

	$("header").on("click", ".notification", function () {
		var title, body;
		title = $(this).find(".title").text();
		body = $(this).find(".body").text();

		$("#success-btn-modal .text").html(title + "<br>" + body);
		$("#success-btn-modal").modal("show");
	});

	//ads notifications
	$("header .notes-icon").click(function () {
		$.ajax({
			type: "get",
			url: base_url + '/users_control_web/get_my_items',
			dataType: "json",
			global: false // this makes sure ajaxStart is not triggered
		}).done(function (data) {
			if (data.status === false) {} else {
				$("header .notes-dropdown .dropdown-menu").empty();
				template = $('#notes-template').html();
				Mustache.parse(template);
				for (i in data.data) {
					if (data.data[i].user_seen === "0") {
						data.data[i].new = "1";
					} else {
						data.data[i].new = "0";
					}
					if (data.data[i].status === "1") {
						if (lang === "ar") {
							data.data[i].status = "قيد الانتظار";
						} else {
							data.data[i].status = "Pending";
						}

					} else if (data.data[i].status === "2") {
						if (lang === "ar") {
							data.data[i].status = "موافق عليه";
						} else {
							data.data[i].status = "Accepted";
						}
					} else if (data.data[i].status === "3") {
						if (lang === "ar") {
							data.data[i].status = "منتهي";
						} else {
							data.data[i].status = "Expired";
						}
					} else if (data.data[i].status === "4") {
						if (lang === "ar") {
							data.data[i].status = "مخفي";
						} else {
							data.data[i].status = "Hidden";
						}
					} else if (data.data[i].status === "5") {
						if (lang === "ar") {
							data.data[i].status = "مرفوض";
						} else {
							data.data[i].status = "Rejected";
						}
					}
					rendered = Mustache.render(template, data.data[i]);
					$("header .notes-dropdown .dropdown-menu").append(rendered);
					//					}
				}
				$(".notes-dropdown .number").addClass("d-none");
				adNotCount = 0;
			}
		});
	});

	var adNotCount = 0;

	function checkAdNotCount() {
		$.ajax({
			type: "get",
			url: base_url + '/users_control_web/get_my_items_unseen_count',
			dataType: "json",
			global: false // this makes sure ajaxStart is not triggered
		}).done(function (data) {
			if (data.status === false) {} else {
				if (Number(data.data) - adNotCount > 0) {
					if (lang === "ar") {
						$(".notification-alert .text").html("لديك تحديث في حالة أحد إعلاناتك");
					} else {
						$(".notification-alert .text").html("You have a new ad status update");
					}
					$(".notification-alert").removeClass("d-none");
					setTimeout(function () {
						$(".notification-alert").addClass("d-none");
					}, 5000);
					adNotCount = Number(data.data);

					$(".notes-dropdown .number").text(data.data);
					$(".notes-dropdown .number").removeClass("d-none");
				}

			}
		});
		setTimeout(checkAdNotCount, 1500);
	}
	//ads notifications count
	if (logged) {
		checkAdNotCount();
	}

	//ask-register-modal
	$("#ask-register-modal .submit").click(function () {
		$("#ask-register-modal").modal("hide");
		setTimeout(function () {
			$("#register-modal").modal("show");
		}, 500);
	});

	//add commas while user typing numbers
	$('input.number').keyup(function (event) {
		// skip for arrow keys
		if (event.which >= 37 && event.which <= 40) return;

		// format number
		$(this).val(function (index, value) {
			return value
				.replace(/\D/g, "")
				.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		});
	});

});
