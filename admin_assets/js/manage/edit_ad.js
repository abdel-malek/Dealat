//global variables
	var typesData, adImgs, mixer, category_id, category_name, i, template, rendered, subcategories = [];
	adImgs = [];

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
	$("#edit-ad-modal").on("hide.bs.modal", function () {
		resetEditAd();
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

			// if ($(".profile-page").length > 0) {
				$('#edit-ad-modal .city-select')[0].sumo.reload();
				$('#edit-ad-modal .location-select')[0].sumo.disable();
			// }
		

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
							
							// if ($(".profile-page").length > 0) {
								$('#edit-ad-modal .location-select')[0].sumo.reload();
							// }

							// if ($(this).parents("#ad-modal").length > 0) {
								// $('#ad-modal .location-select')[0].sumo.enable();
							// } else 
							if ($(this).parents("#edit-ad-modal").length > 0) {
								$('#edit-ad-modal .location-select')[0].sumo.enable();
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


			// if ($(".profile-page").length > 0) {
				$('#edit-ad-modal select.type-select')[0].sumo.reload();
			// }

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
		
			// if ($(".profile-page").length > 0) {
				$('#edit-ad-modal .period-select')[0].sumo.reload();
			// }
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
	
	//ad modal engine_capacity
	for (i = 1100; i <= 5400; i += 100) {
		$("#ad-modal .engine-capacity-select, #edit-ad-modal .engine-capacity-select").append($('<option>', {
			value: i,
			text: i
		}));
	}

	//reload just for the multiple
	// if ($(".profile-page").length > 0) {
		// $('#edit-ad-modal select.engine-capacity-select')[0].sumo.reload();
	// }
	
	
	var registerImg = [];
		var uploadPersonal, deleteString;
		if (lang === "ar") {
			uploadPersonal = "اختر صورة";
			deleteString = "حذف";
		} else {
			uploadPersonal = "Upload Image";
			deleteString = "Delete";
		}
	var uploadEdit, uploadMainEdit, uploadVideoEdit, editAdImgs = [],
			tempEditAdImgs = [],
			editMainImg = [],
			tempEditMainImg = [],
			editAdVideo = [];
		if (lang === "ar") {
			uploadEdit = "اختر صور إضافية";
			uploadMainEdit = "اختر صورة الإعلان الرئيسية";
			uploadVideoEdit = "اختر فيديو";
		} else {
			uploadEdit = "Upload more images";
			uploadMainEdit = "Upload main ad image";
			uploadVideoEdit = "Upload video";
		}

		uploadobjEditMain = $("#fileuploader-edit-ad-main").uploadFile({
			url: base_url + '/api/items_control/item_images_upload',
			multiple: false,
			dragDrop: false,
			fileName: "image",
			acceptFiles: "image/*",
			maxFileSize: 10000 * 1024,
			maxFileCount: 1,
			showDelete: true,
			dragdropWidth: "100%",
			showPreview: true,
			showProgress: true,
			showFileSize: false,
			previewHeight: "100px",
			previewWidth: "100px",
			deleteStr: deleteString,
			uploadStr: uploadMainEdit,
			returnType: "json",
			onSuccess: function (files, data, xhr, pd) {
				editMainImg.push(data.data);
				$("#edit-ad-modal .ad-images .main-img").remove();
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
						for (i in editMainImg) {
							if (editMainImg[i] === deleted) {
								editMainImg.splice(i, 1);
							}
						}
					});
			}
		});

		uploadobjEditOther = $("#fileuploader-edit-ad").uploadFile({
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
			uploadStr: uploadEdit,
			returnType: "json",
			onSuccess: function (files, data, xhr, pd) {
				editAdImgs.push(data.data);
				tempEditAdImgs.push(data.data);
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
						for (i in editAdImgs) {
							if (editAdImgs[i] === deleted) {
								editAdImgs.splice(i, 1);
							}
						}
					});
			}
		});

		//video
		uploadobjEditVideo = $("#fileuploader-edit-ad-video").uploadFile({
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
			uploadStr: uploadVideoEdit,
			returnType: "json",
			onSuccess: function (files, data, xhr, pd) {
				editAdVideo.push(data.data);
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
						for (i in editAdVideo) {
							if (editAdVideo[i] === deleted) {
								editAdVideo.splice(i, 1);
							}
						}
					});
			}
		});
	
	
		//open edit user ad modal
		$("#edit_btn_modal").on("click", function () {
			var templateId, adId, catId;
			adId =  $('.ads_details  #post_id').val();
			templateId = $('.ads_details  #ad_details_template_id').val();
			console.log(templateId);
			$("#edit-ad-form .template-id").val(templateId);
			catId = $('.ads_details  #ad_details_category_id').val();
			console.log(catId);
			$.ajax({
				type: "get",
				url: base_url + '/api/items_control/get_item_details',
				dataType: "json",
				data: {
					ad_id: adId,
					template_id: templateId
				}
			}).done(function (data) {
				if (data.status === false) {} else {
					//remove upload video except for properties category
					if (templateId === 2) {
						$("#edit-ad-modal #fileuploader-edit-ad-video").removeClass("d-none");
					} else {
						$("#edit-ad-modal #fileuploader-edit-ad-video").addClass("d-none");
					}

					//if category is job remove price and negotiable inputs
					if (templateId === 8) {
						$("#edit-ad-modal input[name='price']").closest(".form-group").addClass("d-none");
						$("#edit-ad-modal input[name='price']").removeAttr("required");
						$("#edit-ad-modal input[name='price']").val("0");
						$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").addClass("d-none");
					} else {
						$("#edit-ad-modal input[name='price']").closest(".form-group").removeClass("d-none");
						$("#edit-ad-modal input[name='price']").attr("required", true);
						$("#edit-ad-modal input[name='price']").val("");
						$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").removeClass("d-none");
					}


					$("#edit-ad-modal input[name='ad_id']").val(data.data.ad_id);
					$("#edit-ad-modal input[name='title']").val(data.data.title);
					$("#edit-ad-modal select[name='show_period']").val(data.data.show_period);
				    $("#edit-ad-modal .period-select")[0].sumo.reload();
					$("#edit-ad-modal input[name='price']").val(new Intl.NumberFormat().format(data.data.price));
					$("#edit-ad-modal select[name='city_id']").val(data.data.city_id).change();
					$("#edit-ad-modal select[name='city_id']")[0].sumo.reload();


					if (data.data.location_id) {
						$("#edit-ad-modal select[name='location_id']").val(data.data.location_id);
						$("#edit-ad-modal select[name='location_id']")[0].sumo.reload();
					}
					$("#edit-ad-modal select[name='location_id']")[0].sumo.enable();

					if (data.data.is_negotiable === "1") {
						$("#edit-ad-modal input[name='is_negotiable']").prop("checked", true);
					}

					$("#edit-ad-modal textarea[name='description']").val(data.data.description);

					if (data.data.type_id) {
						$("#edit-ad-modal select[name='type_id']").val(data.data.type_id).change();
						$("#edit-ad-modal select[name='type_id']")[0].sumo.reload();
						if (data.data.type_model_id) {
							$("#edit-ad-modal select[name='type_model_id']").closest(".form-group").removeClass("d-none");
							$("#edit-ad-modal select[name='type_model_id']").val(data.data.type_model_id);
							$("#edit-ad-modal select[name='type_model_id']")[0].sumo.reload();
						}
					}

					if (data.data.manufacture_date) {
						$("#edit-ad-modal input[name='manufacture_date']").val(data.data.manufacture_date);
					}
					if (data.data.kilometer) {
						$("#edit-ad-modal input[name='kilometer']").val(new Intl.NumberFormat().format(data.data.kilometer));
					}
					if (data.data.engine_capacity) {
						$("#edit-ad-modal select[name='engine_capacity']").val(data.data.engine_capacity);
						$("#edit-ad-modal select[name='engine_capacity']")[0].sumo.reload();
					} else {
						$("#edit-ad-modal select[name='engine_capacity']")[0].sumo.unSelectAll();
					}
					if (data.data.is_automatic) {
						$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_automatic']").val(data.data.is_automatic);
						$("#edit-ad-modal select[name='is_automatic']")[0].sumo.reload();
					}
					if (data.data.is_new) {
						$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").val(data.data.is_new);
						$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']")[0].sumo.reload();
						//change all status values from all categories because when submit it takes the last value
						$('#edit-ad-modal .status-select').each(function () {
							$(this).val(data.data.is_new);
						});
					}
					if (data.data.space) {
						$("#edit-ad-modal input[name='space']").val(new Intl.NumberFormat().format(data.data.space));
					}
					if (data.data.rooms_num) {
						$("#edit-ad-modal input[name='rooms_num']").val(data.data.rooms_num);
					}
					if (data.data.floor) {
						$("#edit-ad-modal input[name='floor']").val(data.data.floor);
					}
					if (data.data.floors_number) {
						$("#edit-ad-modal input[name='floors_number']").val(data.data.floors_number);
					}
					if (data.data.property_state_id) {
						$("#edit-ad-modal select[name='property_state_id']").val(data.data.property_state_id);
						$("#edit-ad-modal select[name='property_state_id']")[0].sumo.reload();
					}
					if (data.data.with_furniture === "1") {
						$("#edit-ad-modal input[name='with_furniture']").prop("checked", true);
					}
					if (data.data.size) {
						$("#edit-ad-modal input[name='size']").val(data.data.size);
					}
					if (data.data.schedule_id) {
						$("#edit-ad-modal select[name='schedule_id']").val(data.data.schedule_id);
						$("#edit-ad-modal select[name='schedule_id']")[0].sumo.reload();
					}
					if (data.data.education_id) {
						$("#edit-ad-modal select[name='education_id']").val(data.data.education_id);
						$("#edit-ad-modal select[name='education_id']")[0].sumo.reload();
					}
					if (data.data.certificate_id) {
						$("#edit-ad-modal select[name='certificate_id']").val(data.data.certificate_id);
						$("#edit-ad-modal select[name='certificate_id']")[0].sumo.reload();
					}
					if (data.data.experience) {
						$("#edit-ad-modal input[name='experience']").val(data.data.experience);
					}
					if (data.data.gender) {
						$("#edit-ad-modal select[name='gender']").val(data.data.gender);
						$("#edit-ad-modal select[name='gender']")[0].sumo.reload();
					} else {
						$("#edit-ad-modal select[name='gender']")[0].sumo.unSelectAll();
					}
					if (data.data.salary) {
						$("#edit-ad-modal input[name='salary']").val(new Intl.NumberFormat().format(data.data.salary));
					}

					if (data.data.ad_visible_phone === "1") {
						$("#edit-ad-modal input[name='ad_visible_phone']").prop("checked", true);
					}

					editAdImgs = [];

					for (i in data.data.images) {
						editAdImgs.push(data.data.images[i].image);
					}

					tempEditMainImg = [];
					if (data.data.main_image) {
						tempEditMainImg.push(data.data.main_image);
					}

					$("#edit-ad-modal .ad-images").empty();
					template = $('#ad-edit-images-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, data.data);
					$("#edit-ad-modal .ad-images").append(rendered);

					if (data.data.images.length === 0) {
						$("#edit-ad-modal .ad-images .secondary-imgs").remove();
					}
				}
			});

			var subId, has_types = 0;

			subId = $(this).parents(".card").data("categoryId");

			//put all fields as shown
			$("#edit-ad-modal .field").each(function () {
				$(this).removeClass("d-none");
			});


			$("#edit-ad-modal .template").each(function () {
				$(this).addClass("d-none");

				if ($(this).data("templateId") == templateId) {
					$(this).removeClass("d-none");
				}
			});

			//only display selected template types
			$("#edit-ad-modal .type-select option").each(function () {
				$(this).addClass("d-none");
				if ($(this).data("categoryId") == subId) {
					has_types = 1;
					$(this).removeClass("d-none");
				}
			});

			$('#edit-ad-modal select.type-select')[0].sumo.reload();
			//display types select only if category is vehicles, mobiles or electronics
			if (has_types !== 0) {
				$("#edit-ad-modal .type-select").parents(".form-group").removeClass("d-none");
				$("#edit-ad-modal .model-select").parents(".form-group").addClass("d-none");
			} else {
				$("#edit-ad-modal .type-select").parents(".form-group").addClass("d-none");
				$("#edit-ad-modal .model-select").parents(".form-group").addClass("d-none");
			}

			//vehicles
			if (templateId === 1) {
				if ($.inArray("manufacture_date", hideArr) === -1) {
					$("#edit-ad-modal input[name='manufacture_date']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='manufacture_date']").removeAttr("required");
				}
				if ($.inArray("kilometer", hideArr) === -1) {
					$("#edit-ad-modal input[name='kilometer']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='kilometer']").removeAttr("required");
				}
				if ($.inArray("is_automatic", hideArr) === -1) {
					$("#edit-ad-modal select[name='is_automatic']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='is_automatic']").removeAttr("required");
				}
				if ($.inArray("type_name", hideArr) === -1 && has_types !== 0) {
					$("#edit-ad-modal select[name='type_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='type_id']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal input[name='manufacture_date']").removeAttr("required");
				$("#edit-ad-modal input[name='kilometer']").removeAttr("required");
				$("#edit-ad-modal select[name='is_automatic']").removeAttr("required");
				$("#edit-ad-modal select[name='type_id']").removeAttr("required");
			}

			if (templateId === 1 || templateId === 3 || templateId === 4 || templateId === 5 || templateId === 6 || templateId === 7 || templateId === 9) {
				if ($.inArray("is_new", hideArr) === -1) {
					$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").attr("required", true);
				} else {
					$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal select[name='is_new']").each(function () {
					$(this).removeAttr("required");
				});
			}

			//only show upload video in properties
			if (templateId === 2) {
				$("#edit-ad-modal #fileuploader-ad-video").removeClass("d-none");

				if ($.inArray("space", hideArr) === -1) {
					$("#edit-ad-modal input[name='space']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='space']").removeAttr("required");
				}
				if ($.inArray("rooms_num", hideArr) === -1) {
					$("#edit-ad-modal input[name='rooms_num']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='rooms_num']").removeAttr("required");
				}
				if ($.inArray("floor", hideArr) === -1) {
					$("#edit-ad-modal input[name='floor']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='floor']").removeAttr("required");
				}
				if ($.inArray("property_state_name", hideArr) === -1) {
					$("#edit-ad-modal select[name='property_state_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='property_state_id']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal #fileuploader-ad-video").addClass("d-none");

				$("#edit-ad-modal input[name='space']").removeAttr("required");
				$("#edit-ad-modal input[name='rooms_num']").removeAttr("required");
				$("#edit-ad-modal input[name='floor']").removeAttr("required");
				$("#edit-ad-modal select[name='property_state_id']").removeAttr("required");
			}

			//if category is job remove price and negotiable inputs
			if (templateId === 8) {
				$("#edit-ad-modal input[name='price']").closest(".form-group").addClass("d-none");
				$("#edit-ad-modal input[name='price']").removeAttr("required");
				$("#edit-ad-modal input[name='price']").val("0");
				$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").addClass("d-none");

				if ($.inArray("education_name", hideArr) === -1) {
					$("#edit-ad-modal select[name='education_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='education_id']").removeAttr("required");
				}
				if ($.inArray("gender", hideArr) === -1) {
					$("#edit-ad-modal select[name='gender']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='gender']").removeAttr("required");
				}
				if ($.inArray("certificate_name", hideArr) === -1) {
					$("#edit-ad-modal select[name='certificate_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='certificate_id']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal input[name='price']").closest(".form-group").removeClass("d-none");
				$("#edit-ad-modal input[name='price']").attr("required", true);
				$("#edit-ad-modal input[name='price']").val("");
				$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").removeClass("d-none");

				$("#edit-ad-modal select[name='education_id']").removeAttr("required");
				$("#edit-ad-modal select[name='gender']").removeAttr("required");
				$("#edit-ad-modal select[name='certificate_id']").removeAttr("required");
			}


			$("#edit-ad-modal").modal("show");
		});
		


	   $("#edit-ad-modal").on("hide.bs.modal", function () {
			//delete images if user close modal without editing the ad
			var deleteImgs = [];

			if (editMainImg.length > 0) {
				for (i in editMainImg) {
					deleteImgs.push(editMainImg[i]);
				}
			}

			if (tempEditAdImgs.length > 0) {
				for (i in tempEditAdImgs) {
					deleteImgs.push(tempEditAdImgs[i]);
				}
			}

			if (tempEditAdImgs.length > 0 || editMainImg.length > 0) {
				$.ajax({
					type: "post",
					url: base_url + '/api/items_control/delete_images',
					dataType: "json",
					data: {
						images: JSON.stringify(deleteImgs)
					}
				});
			}
			//delete video if user close modal without editing the ad
			if (editAdVideo.length > 0) {
				var deleteVid = [];
				for (i in editAdVideo) {
					deleteVid.push(editAdVideo[i]);
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
			editMainImg = [];
			editAdImgs = [];
			editAdVideo = [];
			deleteImgArr = [];
			deleteMainImgArr = [];
			deleteVideoArr = [];
			tempEditAdImgs = [];
		});
		

    	var deleteImgArr = [],
		deleteMainImgArr = [],
		deleteVideoArr = [];
		$("#edit-ad-modal .ad-images").on("click", ".delete", function () {
			var url = $(this).parents(".image-wrapper").data("url");
			if ($(this).parents(".image-wrapper").hasClass("main-image")) {
				deleteMainImgArr.push(url);
				deleteImgArr.push(url);
				tempEditMainImg = [];
			} else if ($(this).parents(".image-wrapper").hasClass("video")) {
				deleteVideoArr.push(url);
			} else {
				deleteImgArr.push(url);
				for (i in editAdImgs) {
					if (editAdImgs[i] == url) {
						editAdImgs.splice(i, 1);
					}
				}
			}
			$(this).parents(".image-wrapper").remove();

		});

		//change all is_new select values if one select is changed
		$('#edit-ad-modal').on("change", ".status-select", function () {
			var newVal = $(this).val();
			$('#edit-ad-modal .status-select').each(function () {
				$(this).val(newVal);
			});
		});
		

       		var editAdData;
		//submit edit user ad
		$("#edit-ad-form").submit(function (e) {
			e.preventDefault();
			e.stopPropagation();

			var i,
				secondary_imgs = [],

			editAdData = $(this).serializeArray();


			if (editMainImg.length > 0) {
				editAdData.push({
					name: "main_image",
					value: editMainImg[0]
				});
			}

			if (deleteMainImgArr.length > 0 && editMainImg.length === 0 && editAdImgs.length === 0) {
				//in case no images added
				var templateId = $("#edit-ad-form .template-id").val();
				if (templateId == 1) {
					if (lang === "ar") {
						$('#edit-ad-modal .error-message').html("الرجاء إرفاق صورة واحدة للإعلان على الأقل ثم حاول مجدداً");
					} else {
						$('#edit-ad-modal .error-message').html("Please upload at least one image and try again");
					}
					$('#edit-ad-modal .error-message').removeClass("d-none");
					$("#edit-ad-modal").animate({
						scrollTop: $("body").offset().top
					}, 500);
					return false;
				}

				//to keep default image
				editAdData.push({
					name: "main_image",
					value: "-1"
				});
			}

			if (tempEditMainImg.length === 0 && editMainImg.length === 0 && tempEditAdImgs.length === 0 && editAdImgs.length === 0) {
				//in case no images added
				var templateId = $("#edit-ad-form .template-id").val();
				if (templateId == 1) {
					if (lang === "ar") {
						$('#edit-ad-modal .error-message').html("الرجاء إرفاق صورة واحدة للإعلان على الأقل ثم حاول مجدداً");
					} else {
						$('#edit-ad-modal .error-message').html("Please upload at least one image and try again");
					}
					$('#edit-ad-modal .error-message').removeClass("d-none");
					$("#edit-ad-modal").animate({
						scrollTop: $("body").offset().top
					}, 500);
					return false;
				}
			}

			if (tempEditMainImg.length === 0 && editMainImg.length === 0) {
				//there is no main image
				if (editAdImgs.length > 0) {
					if (lang === "ar") {
						$('#edit-ad-modal .error-message').html("الرجاء رفع صورة رئيسية للإعلان");
					} else {
						$('#edit-ad-modal .error-message').html("Please upload main ad image");
					}
					$('#edit-ad-modal .error-message').removeClass("d-none");
					$("#edit-ad-modal").animate({
						scrollTop: $("body").offset().top
					}, 500);
					return false;
				}
			}

			if (editAdImgs.length > 0) {
				//copy adimgs into uploaded_imgs
				for (i in editAdImgs) {
					secondary_imgs.push(editAdImgs[i]);
				}
				secondary_imgs = JSON.stringify(secondary_imgs);
				editAdData.push({
					name: "images",
					value: secondary_imgs
				});
			}

			if (editAdVideo.length > 0) {
				editAdData.push({
					name: "main_video",
					value: editAdVideo[0]
				});
			}

			if (deleteImgArr.length > 0) {
				deleteImgArr = JSON.stringify(deleteImgArr);

				editAdData.push({
					name: "deleted_images",
					value: deleteImgArr
				});
			}

			if (deleteVideoArr.length > 0) {
				deleteVideoArr = JSON.stringify(deleteVideoArr);

				editAdData.push({
					name: "deleted_videos",
					value: deleteVideoArr
				}, {
					name: "main_video",
					value: "-1"
				});
			}

			for (i in editAdData) {
				//send -1 for empty values
				if (editAdData[i].value === "") {
					editAdData[i].value = "-1";
				}
			}

			var numbersWithComma = ["price", "kilometer", "space", "salary"];
			for (i in editAdData) {
				if (numbersWithComma.indexOf(editAdData[i].name) > -1) {
					editAdData[i].value = editAdData[i].value.replace(/,/g, '');
				}
			}

			$("#confirm-edit-modal").modal("show");
		});

		$("#confirm-edit-modal").on("hidden.bs.modal", function () {
			$("body").addClass("modal-open");
		});

		$("#confirm-edit-modal .submit").click(function () {
			$.ajax({
				type: "post",
				url: base_url + '/api/items_control/edit',
				dataType: "json",
				data: $.param(editAdData)
			}).done(function (data) {
				if (data.status === false) {} else {
					//reset edit arrays
					editMainImg = [];
					editAdImgs = [];
					editAdVideo = [];
					deleteImgArr = [];
					deleteMainImgArr = [];
					deleteVideoArr = [];
					tempEditAdImgs = [];

					$("#confirm-edit-modal").modal("hide");
					$("#edit-ad-modal").modal("hide");
					setTimeout(function () {
						if (lang === "ar") {
							$("#notification-modal .text").html("تم تعديل إعلانك بنجاج وهو الآن بانتظار الموافقة عليه");
						} else {
							$("#notification-modal .text").html("Your ad has been edited successfully and it is now pending waiting for admin approval");
						}

						$("#notification-modal").modal("show");
					}, 500);
				}
			});
		});