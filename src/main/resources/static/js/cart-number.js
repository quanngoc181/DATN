$(function() {
	
});

function getCartNumber() {
	$.ajax({
		url : "/user/cart/count",
		data: { },
		success : function(data) {
			$('.my-header .cart-number').text(data);
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}