$(function() {
	var stripe = Stripe('pk_test_51GtnsjJG8N5EtzpVmMsU6IKiw7waHLHarBWDHiWGSAYQpES6DNmvsys5LdBMcKwqFOFgqLai2CxlSC4kGngrkAoD005debkkzG');
	var elements = stripe.elements();

	var card = elements.create("card", {});
	card.mount("#card-element");

	$('#payment-form').on('submit', function(ev) {
		ev.preventDefault();

		let clientSecret = $(this).find('#submit').data('secret');
		let order = $(this).find('#submit').data('order');

		stripe.confirmCardPayment(clientSecret, {
			payment_method : {
				card : card
			}
		}).then(function(result) {
			if (result.error) {
				notify('error', result.error.message);
			} else {
				if (result.paymentIntent.status === 'succeeded') {
					$.ajax({
						url : "/user/payment-success",
						method: 'post',
						data: {
							orderId: order
						},
						success : function(data) {
							$('.payment-card').html('');
							alertSuccess(function() {
								location.href = "/user/my-order";
							});
						},
						error : function(err) {
							notify('error', err.responseJSON.message);
						}
					});
				}
			}
		});
	});
});