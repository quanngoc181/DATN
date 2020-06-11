$(function() {
	fetchProduct();
	getCartNumber();
	
	var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
	if(width > 576) {
		$('.toggle-filter').trigger('click');
	}
	
	$("#keyword").on('keyup', debounce(function() {
		fetchProduct();
	}, 500));
	
	$('input[name="categories"], input[name="discount"]').on('change', function() {
		fetchProduct();
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
	
	$(document).on('click', '.increase', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		$amount.data('value', +current + 1);
		$amount.text(+current + 1);
		calculateTotalCost();
	});
	
	$(document).on('click', '.decrease', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		if(+current > 1) {
			$amount.data('value', +current - 1);
			$amount.text(+current - 1);
			calculateTotalCost();
		}
	});
	
	$(document).on('change', '.product-detail input', function() {
		calculateTotalCost();
	});
	
	$(document).on('click', '#add-to-cart', function(e) {
		e.preventDefault();
		let productId = $('.product-detail').data('id');
		let amount = $('.counting').find('.amount').data('value');
		let items = '';
		$('.product-detail input').each(function(index, element) {
			if($(this).prop('checked')) items += $(this).val() + ';';
		});
		if(items.length != 0) items = items.slice(0, -1);
		
		$.ajax({
			url : "/user/product/add-to-cart",
			method: 'post',
			data: {
				productId: productId,
				amount: amount,
				items: items
			},
			success : function(data) {
				$('#add-to-cart-modal').modal('hide');
				notify('success', 'Thành công');
				getCartNumber();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});

function calculateTotalCost() {
	let id = $('.product-detail').data('id');
	let amount = $('.counting').find('.amount').data('value');
	let items = '';
	$('.product-detail input').each(function(index, element) {
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
			$('.counting .total-cost-number').text(data);
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}

function fetchProduct() {
	let data = objectifyForm('#search-form');
	let categories = '';
	$('input[name="categories"]').each(function(index, element) {
		if($(this).prop('checked')) {
			categories += $(this).val() + ';';
		}
	});
	if(categories.length != 0) categories = categories.slice(0, -1);
	data.categories = categories;

	$.ajax({
		url : "/product/fetch-product",
		data: data,
		success : function(data) {
			$('#product-region').html(data);
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}