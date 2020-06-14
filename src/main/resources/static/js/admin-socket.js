var stompClient = null;

$(function() {
	updateContactList();
	
	var socket = new SockJS('/chat-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/chat-updates', function (data) {
        	notify('info', 'Có tin nhắn mới');
        	updateContactList();
        });
    });
});

function updateContactList() {
	$.ajax({
		url : "/admin/chat-management/update",
		data: { },
		success : function(data) {
			$('#contact-list').html(data);
			let unSeen = $('<div>' + data + '</div>').find('.unseen-tmp').data('value');
			$('.message-number').text(unSeen);
			$('.conversation-container').html('');
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}