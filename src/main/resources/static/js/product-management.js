$(function() {
	let productTable = $('#product-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/product-management/datatable'
		},
		"drawCallback" : function(settings) {
			// initSwitch();
		},
		"columns" : [ {
			data : 'productCode',
			name : 'productCode'
		}, {
			data : 'name',
			name : 'name'
		}, {
			data : 'cost',
			name : 'cost'
		} ]
	});
	
	$('.product-container').each(function(id, el) {
		new Sortable(el, {
			group: 'shared',
			animation: 200,
			onEnd: function (evt) {
				let element = $(evt.item);
				let productId = element.data('id');
				let categoryId = element.closest('.card').data('id');
				
				$.ajax({
					url : "/admin/product-management/change-category",
					method: 'post',
					data: {
						productId: productId,
						categoryId: categoryId
					},
					success : function() {
						notify('success', 'Thành công');
					},
					error : function(err) {
						notify('error', err.responseJSON.message);
					}
				});
			}
		});
	});
	
	$('.add-product').on('click', function() {
		let id = $(this).closest('.card').data('id');
		
		$.ajax({
			url : "/admin/product-management/add",
			data: { id: id },
			success : function(data) {
				$('#add-product-modal .modal-content').html(data);
				bsCustomFileInput.init();
				$('#add-product-modal').modal('show');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('.delete-item').on('click', function() {
		let $this = $(this);
		let id = $this.closest('.product-preview').data('id');
		console.log(id);
		confirmDelete(function() {
			$.ajax({
				url : "/admin/product-management/delete",
				method: 'post',
				data: { id: id },
				success : function(data) {
					$this.closest('.product-preview').remove();
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		});
	});
	
	$(document).on('click', '#add-product', function(e) {
		e.preventDefault();
		let formData = new FormData($('#add-product-form')[0]);
		
		$.ajax({
			url : "/admin/product-management/add",
			method: 'post',
			enctype: 'multipart/form-data',
			processData: false,
	        contentType: false,
	        cache: false,
			data: formData,
			success : function(data) {
				location.reload();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});