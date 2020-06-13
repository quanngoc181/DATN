$(function() {
	fetchInfo();
	updateContactList();
	
	$(document).on('click', '.chat-item', function() {
		let user = $(this).data('user');
		let name = $(this).text();
		
		$.ajax({
			url : "/admin/chat-management/get-conversation",
			data: { user: user },
			success : function(data) {
				$('.conversation-container').html(data);
				$('.conversation-container').find('.input-message').trigger('focus');
				$('.conversation-container').find('.card-title').text(name);
				let el = document.getElementById("direct-chat-messages");
				el.scrollTop = el.scrollHeight;
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('focus', '.input-message', function() {
		let username = $(this).closest('form').find('input[name="username"]').val();
		
		$.ajax({
			url : "/admin/chat-management/seen",
			method: 'post',
			data: { user: username },
			success : function(data) {
				$('#contact-list').html(data);
				let unSeen = $('#contact-list').find('.unseen-tmp').data('value');
				$('.message-number').text(unSeen);
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.send-message', function(e) {
		e.preventDefault();
		let $this = $(this);
		
		let mes = $('.input-message').val();
		if(mes.trim().length == 0) {
			$(this).closest('form').trigger('reset');
			return;
		}
		
		let oldname = $('.conversation-container').find('.card-title').text();
		
		$.ajax({
			url : "/admin/chat-management/add",
			method: 'post',
			data: objectifyForm('#form-message'),
			success : function(data) {
				$this.closest('form').trigger('reset');
				$('.conversation-container').html(data);
				$('.conversation-container').find('.input-message').trigger('focus');
				$('.conversation-container').find('.card-title').text(oldname);
				let el = document.getElementById("direct-chat-messages");
				el.scrollTop = el.scrollHeight;
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});

function updateContactList() {
	$.ajax({
		url : "/admin/chat-management/update",
		data: { },
		success : function(data) {
			$('#contact-list').html(data);
			let unSeen = $('#contact-list').find('.unseen-tmp').data('value');
			$('.message-number').text(unSeen);
			$('.conversation-container').html('');
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}