var stompClient1 = null;

$(function() {
	initScrollbarSelector('.notification-container');
	updateNotification();
	
	var socket = new SockJS('/app-endpoint');
    stompClient1 = Stomp.over(socket);
    stompClient1.debug = null;
    stompClient1.connect({}, function (frame) {
        stompClient1.subscribe('/user/queue/notification-updates', function (data) {
        	updateNotification();
        });
    });
});

function updateNotification() {
	$.ajax({
		url : "/user/notification/update",
		data: { },
		success : function(data) {
			$('.notification-container').find('.os-content').html(data);
			let unSeen = $('#unSeen-tmp').data('value');
			$('.notification-number').text(unSeen);
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
	
	$(document).on('click', '.notification-item', function(e) {
		e.preventDefault();
		let url = $(this).attr('href');
		let id = $(this).data('id');

		$.ajax({
			url : "/user/notification/seen",
			method: 'post',
			data: {
				id: id
			},
			success : function(data) {
				window.location = url;
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
}