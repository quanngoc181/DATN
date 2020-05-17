$(function() {
	fetchProduct();
	
	$("#keyword").on('keyup', debounce(function() {
		fetchProduct();
	}, 500));
	
	$('input[name="categories"], input[name="discount"]').on('change', function() {
		fetchProduct();
	});
});

function fetchProduct() {
	let data = objectifyForm('#search-form');
	let categories = '';
	$('input[name="categories"]').each(function(index, element) {
		if($(this).prop('checked')) {
			categories += $(this).val() + ';';
		}
	});
	if(categories.length != 0) categories = categories.slice(0, -1);
	data.categories = categories;
	console.log(data);
	$.ajax({
		url : "/product/fetch-product",
		data: data,
		success : function(data) {
			$('#product-region').html(data);
		},
		error : function(err) {
			notify('error', err.responseJSON.message);
		}
	});
}