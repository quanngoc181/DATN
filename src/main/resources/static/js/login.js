$(function(){
	$('#register').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/register",
			method: 'post',
			data: objectifyForm('#register-form'),
			success : function(data) {
				$("#register-form").trigger("reset");
				notify('success', "Thành công");
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('#change-password-submit').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/user/change-password",
			method: 'post',
			data: objectifyForm('#change-password-form'),
			success : function(data) {
				$("#change-password-form").trigger("reset");
				notify('success', "Thành công");
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('#forgot-password-submit').on('click', function(e) {
		e.preventDefault();
		let username = $('#username').val();
		if(username.trim().length == 0) return;
		$.ajax({
			url : "/forgot-password",
			method: 'post',
			data: { username: username },
			success : function(data) {
				console.log(data);
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});