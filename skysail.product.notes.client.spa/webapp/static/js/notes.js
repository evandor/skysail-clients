    
    var getNotes = function() {
	    $.getJSON('/notes/notes', function(data) {
			var items = [];
	        items.push('<ul class="nav nav-list">');
			items.push('<li class="nav-header">Notes</li>');
			$.each(data.data, function(key, val) {
		        var dt_to = $.datepicker.formatDate('dd.mm.yy', new Date(val.created));
				items.push('<li><span id="'+val.pid+'" class="showDetails">' + dt_to + ': ' + val.title + '</span></li>');
			});
	        items.push('</ul>');
			$('<div/>', { id: 'notesSummary', html: items.join('')}).replaceAll('#notesSummary');
		});
	};
	
    $(document).on("click", ".showDetails", function(event){
        var id = $(this).attr('id');
        $.getJSON('/notes/note/' + id, function(payload) {
			var items = [];
			items.push('<p>&nbsp;</p>');
            items.push('<form id="myForm" action="/notes/note/'+id+'?method=PUT" method="POST">');
			items.push('  <h3 class="editable" name="title">' + payload.data.title + '</h3>');
			items.push('  <div class="editable" name="content" style="width:100%; height:350px">' + payload.data.content + '</div>');
            //items.push('    <input type="text" name="title" value="'+payload.data.title+'"><br>');
            //items.push('    <textarea style="width:100%; height:200px;" class="tinymce" id="content" name="content">'+payload.data.content+'</textarea><br>'); 
            items.push('    <input type="submit" value="submit"><br>');
            items.push('</form>');
            $('<div/>', { id: 'selectedNote', html: items.join('\n')}).replaceAll('#selectedNote');
            
            tinymce.init({
			    selector: "h3.editable",
			    inline: true,
			    toolbar: "undo redo",
			    menubar: false
			});
            
            tinymce.init({
                selector: "div.editable",
                inline: true,
                plugins: [
                    "advlist autolink lists link image charmap print preview anchor",
                    "searchreplace visualblocks code fullscreen",
                    "insertdatetime media table contextmenu paste"
                ],
                toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image"
            });
        });
    });
