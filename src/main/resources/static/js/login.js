$(function(){
	$('#register').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/register",
			method: 'post',
			data: objectifyForm('#register-form'),
			success : function() {
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
			success : function() {
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
		
		$.blockUI({css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 0.5,
            color: '#fff'
        }});
		
		$.ajax({
			url : "/forgot-password",
			method: 'post',
			data: { username: username },
			success : function(data) {
				$("#forgot-password-form").trigger("reset");
				$.unblockUI();
				notify('info', data);
			},
			error : function(err) {
				$.unblockUI();
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('#reset-password-submit').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/reset-password",
			method: 'post',
			data: objectifyForm('#reset-password-form'),
			success : function() {
				$("#reset-password-form").trigger("reset");
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});