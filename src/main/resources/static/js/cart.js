$(function() {
	$(document).on('click', '.increase', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		$amount.data('value', +current + 1);
		$amount.text(+current + 1);
	});
	
	$(document).on('click', '.decrease', function() {
		let $amount = $(this).closest('.counting').find('.amount');
		let current = $amount.data('value');
		if(+current > 1) {
			$amount.data('value', +current - 1);
			$amount.text(+current - 1);
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
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		}, "Xóa sản phẩm này khỏi giỏ hàng?");
	});
});