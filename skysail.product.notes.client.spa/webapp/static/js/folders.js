
	$.getJSON('/notes/folders', function(data) {
		var items = [];
		items.push('<li class="nav-header">Folders</li>');
		items.push('<li><a href="#myModal" role="button" data-toggle="modal">[new]</a></li>');
		items.push('<li class="divider"></li>');
		$.each(data.data, function(key, val) {
			items.push('<li><a href="'+val.link+'">' + val.folderName + '</a></li>');
		});
		items.push('<li class="nav-header">Favorites</li>');
		
		$('<ul/>', { 'class': 'nav nav-list', html: items.join('')}).appendTo('#folders');
	});
