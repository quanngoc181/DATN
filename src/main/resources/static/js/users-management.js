$(function() {
	// $('#users-datatable').DataTable();
	$('#users-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/users-management/datatable'
		},
		"columns" : [ {
			data : 'username',
			name : 'username'
		}, {
			data : 'password',
			name : 'password'
		}, {
			data : 'enabled',
			name : 'enabled',
			render : function(data, type, row, meta) {
				if (data)
					return "yes";
				return "no";
			}
		} ]
	});
});