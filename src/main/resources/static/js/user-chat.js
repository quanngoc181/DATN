var stompClient = null;

$(function() {
	updateChat();
	
	var socket = new SockJS('/app-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/chat-updates', function (data) {
        	notify('info', 'Có tin nhắn mới');
        	updateChat();
        });
    });
	
	$('.send-message').on('click', function(e) {
		e.preventDefault();
		
		let mes = $('.input-message').val();
		$(this).closest('form').trigger('reset');
		if(mes.trim().length == 0)
			return;
		
		$.ajax({
			url : "/user/chat/add",
			method: 'post',
			data: { message: mes },
			success : function(data) {
				$('#direct-chat-messages').html(data);
				let unSeen = $('#direct-chat-messages').find('.unseen-tmp').data('value');
				$('.message-number').text(unSeen);
				let el = document.getElementById("direct-chat-messages");
				el.scrollTop = el.scrollHeight;
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('.input-message').on('focus', function() {
		$.ajax({
			url : "/user/chat/seen",
			method: 'post',
			data: { },
			success : function() {
				$('.message-number').text(0);
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});

function updateChat() {
	$.ajax({
		url : "/user/chat/update",
		method: 'post',
		data: { },
		success : function(data) {
			$('#direct-chat-messages').html(data);
			let unSeen = $('#direct-chat-messages').find('.unseen-tmp').data('value');
			$('.message-number').text(unSeen);
			let el = document.getElementById("direct-chat-messages");
			el.scrollTop = el.scrollHeight;
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}