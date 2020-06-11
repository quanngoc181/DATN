$(function() {
	getCartNumber();
	getTotalCost();
	
	$(document).on('click', '.increase', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		$amount.data('value', +current + 1);
		$amount.text(+current + 1);
		let cartId = $(this).closest('.product-preview').data('id');
		updateAmount($(this), cartId, +current + 1);
	});
	
	$(document).on('click', '.decrease', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		if(+current > 1) {
			$amount.data('value', +current - 1);
			$amount.text(+current - 1);
			let cartId = $(this).closest('.product-preview').data('id');
			updateAmount($(this), cartId, +current - 1);
		}
	});
	
	$('.delete-item').on('click', function() {
		let $product = $(this).closest('.product-preview');
		let id = $product.data('id');
		
		confirmDelete(function() {
			$.ajax({
				url : "/user/cart/delete",
				method: 'post',
				data: { id: id },
				success : function(data) {
					$product.remove();
					getCartNumber();
					getTotalCost();
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		}, "Xóa sản phẩm này khỏi giỏ hàng?");
	});
});

function updateAmount($this, cartId, amount) {
	$.ajax({
		url : "/user/cart/update-amount",
		method: 'post',
		data: { id: cartId, amount: amount },
		success : function(data) {
			$this.closest('.product-preview').find('.total-cost-number').text(data);
			getTotalCost();
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}

function getTotalCost() {
	$.ajax({
		url : "/user/cart/total-cost",
		data: { },
		success : function(data) {
			$('.total-cart .amount').text(data + ' đ');
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}