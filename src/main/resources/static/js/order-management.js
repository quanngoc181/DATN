$(function() {
	fetchInfo();
	
	$("#search-order").on('keyup', debounce(function() {
		let value = $(this).val();
		$('.order-item').addClass('d-none');
		$('.order-item').each(function(index, element) {
			let title = $(element).find('.card-title').text();
			let account = $(element).find('.order-account').val();
			
			if(title.includes(value) || account.includes(value))
				$(element).removeClass('d-none');
		});
	}, 500));
	
	$('.change-status').on('change', function() {
		let value = $(this).val();
		let order = $(this).closest('.order-item').data('id');
		
		confirmAction(function() {
			$.ajax({
				url : "/admin/order-management/change-status",
				method: 'post',
				data: {
					id: order,
					status: value
				},
				success : function() {
					location.reload();
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		}, function() {
			location.reload();
		});
	});
	
	$('#add-order').on('click', function() {
		$.ajax({
			url : "/admin/order-management/add",
			data: {},
			success : function(data) {
				$('#add-order-container').html(data);
				document.getElementById("add-order-container").scrollIntoView();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '#add-order-submit', function(e) {
		e.preventDefault();
		
		let orderData = objectifyForm('#add-order-form');
		let productsData = [];
		$('.order-product-container .product-preview').each(function(index, element) {
			productsData.push({
				productId: $(element).data('product'),
				items: $(element).data('items'),
				amount: +$(element).find('.amount').data('value')
			});
		});
		orderData.products = productsData;
		
		$.ajax({
			url : "/admin/order-management/add",
			method: 'post',
	        data : JSON.stringify(orderData),
	        contentType: "application/json; charset=utf-8",
            dataType: "json",
			success : function() {
//				notify('success', 'Thành công');
				location.reload();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.add-item', function() {
		let id = $(this).closest('.product-preview').data('id');
		
		$.ajax({
			url : "/user/product/add-to-cart",
			data: { id: id },
			success : function(data) {
				$('#add-to-cart-modal .modal-content').html(data);
				initScrollbar();
				$('#add-to-cart-modal').modal('show');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.modal .increase', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		$amount.data('value', +current + 1);
		$amount.text(+current + 1);
		calculateTotalCost();
	});
	
	$(document).on('click', '.modal .decrease', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		if(+current > 1) {
			$amount.data('value', +current - 1);
			$amount.text(+current - 1);
			calculateTotalCost();
		}
	});
	
	$(document).on('click', '.order-product-container .increase', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		$amount.data('value', +current + 1);
		$amount.text(+current + 1);
		
		let product = $(this).closest('.product-preview').data('product');
		let items = $(this).closest('.product-preview').data('items');
		updateAmount($(this), product, +current + 1, items);
	});
	
	$(document).on('click', '.order-product-container .decrease', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		if(+current > 1) {
			$amount.data('value', +current - 1);
			$amount.text(+current - 1);
			
			let product = $(this).closest('.product-preview').data('product');
			let items = $(this).closest('.product-preview').data('items');
			updateAmount($(this), product, +current - 1, items);
		}
	});
	
	$(document).on('change', '.modal .product-detail input', function() {
		calculateTotalCost();
	});
	
	$(document).on('click', '#add-to-cart', function(e) {
		e.preventDefault();
		let productId = $('.modal .product-detail').data('id');
		let amount = $('.modal .counting').find('.amount').data('value');
		let items = '';
		$('.modal .product-detail input').each(function(index, element) {
			if($(this).prop('checked')) items += $(this).val() + ';';
		});
		if(items.length != 0) items = items.slice(0, -1);
		
		$.ajax({
			url : "/admin/order-management/add-to-order",
			method: 'post',
			data: {
				productId: productId,
				amount: amount,
				items: items
			},
			success : function(data) {
				$('#add-to-cart-modal').modal('hide');
				$('.order-product-container').append(data);
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.delete-item', function() {
		$(this).closest('.product-preview').remove();
	});
});

function updateAmount($this, product, amount, items) {
	$.ajax({
		url : "/admin/order-management/update-amount",
		method: 'post',
		data: {
			productId: product,
			amount: amount,
			items: items
		},
		success : function(data) {
			$this.closest('.product-preview').find('.total-cost-number').text(data + ' đ');
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}

function calculateTotalCost() {
	let id = $('.modal .product-detail').data('id');
	let amount = $('.modal .counting').find('.amount').data('value');
	let items = '';
	$('.modal .product-detail input').each(function(index, element) {
		if($(this).prop('checked')) items += $(this).val() + ';';
	});
	if(items.length != 0) items = items.slice(0, -1);
	
	$.ajax({
		url : "/user/product/calculate-total-cost",
		data: {
			id: id,
			amount: amount,
			items: items
		},
		success : function(data) {
			$('.modal .counting .total-cost-number').text(data + ' đ');
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}