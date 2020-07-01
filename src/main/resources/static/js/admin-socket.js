var stompClient = null;

$(function() {
	updateContactList();

	var socket = new SockJS('/app-endpoint');
	stompClient = Stomp.over(socket);
	stompClient.debug = null;
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/user/queue/chat-updates', function(data) {
			notify('info', 'Có tin nhắn mới');
			updateContactList();
		});
	});
});

function updateContactList() {
	let oldActive = $('.chat-item.active').data('user');

	$.ajax({
		url: "/admin/chat-management/update",
		data: {},
		success: function(data) {
			$('#contact-list').html(data);
			let unSeen = $('<div>' + data + '</div>').find('.unseen-tmp').data('value');
			$('.message-number').text(unSeen);
			// $('.conversation-container').html('');
			if (oldActive) {
				$('.chat-item[data-user="' + oldActive + '"]').addClass('active');
				$('.chat-item[data-user="' + oldActive + '"]').trigger('click');
			}
		},
		error: function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}