$(function() {
	
});

function fetchInfo() {
	$.ajax({
		url : "/admin/getInfo",
		data: { },
		success : function(data) {
			$('#nav-avatar').attr('src', data.avatar);
			$('#nav-lastname').text(data.lastname);
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}