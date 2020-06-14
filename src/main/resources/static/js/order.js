$(function() {
	getCartNumber();
	
	$(document).on('click', '.change-address', function() {
		$.ajax({
			url : "/user/order-preview/get-address",
			data: {},
			success : function(data) {
				$('#address-modal .modal-content').html(data);
				$('#address-modal').modal('show');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.receive-address', function() {
		let id = $(this).data('id');
		
		$.ajax({
			url : "/user/order-preview/change-address",
			method: 'post',
			data: { id: id },
			success : function(data) {
				$('#receive-address-container').html(data);
				$('#address-modal').modal('hide');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('#create-order').on('click', function(e) {
		e.preventDefault();
		
		let addressId = $('#order-address').data('id');
		let note = $('#order-note').val();
		
		$.ajax({
			url : "/user/create-order",
			method: 'post',
			data: {
				addressId: addressId,
				note: note
			},
			success : function(data) {
				location.href = "/user/payment?orderId=" + data;
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});