
    function getNoteForm (payload) {
        var items = [];
        items.push('<form id="myForm" action="/notes/note/0" method="POST">');
        /*$.each(payload.data, function(key, val) {
            var tag = val.tag;
            items.push('  <div><label class="control-label" for="'+val.id+'">'+val.id+'</label>');
            items.push('    <div class="controls">');
            if (tag == "textarea") {
            	items.push('    <textarea class="tinymce" id="' + val.id + '" name="' + val.id + '">'+val.value+'</textarea>');
            }
            else {
            	items.push('    <input type="text" id="' + val.id + '" name="' + val.id + '" placeholder="" value="">');
            }
            items.push('  </div></div>');
        });*/
        
        items.push('  <div><label class="control-label" for="parent">Folder</label>');
        items.push('    <div class="controls">');
		items.push('    <input type="text" id="parent" name="parent" placeholder="1" value="">');
        items.push('  </div></div>');

        items.push('  <div><label class="control-label" for="title">Title</label>');
        items.push('    <div class="controls">');
		items.push('    <input type="text" id="title" name="title" placeholder="title" value="">');
        items.push('  </div></div>');
        
        items.push('  <div><label class="control-label" for="conent">Content</label>');
        items.push('    <div class="controls">');
		items.push('    <textarea class="tinymce" id="content" name="content"></textarea>');
        items.push('  </div></div>');
        
        items.push('  <div class="control-group">');
        items.push('  <div class="controls">');
        items.push('    <button type="submit" class="btn">Submit</button>');
        items.push('  </div>');
        items.push('  </div>');
        items.push('</form>');
        return items.join('');
	};

    $(document).on("click", "#addNoteButton", function(event){
        $.getJSON('/notes/note', function(payload) {
            $('<div/>', { 'id': 'selectedNote', html: getNoteForm(payload) }).replaceAll('#selectedNote');

            tinymce.init({selector:'.tinymce'});

            $('#myForm').on("submit", function(e) {
                e.preventDefault();
                console.log('Sending request to '+$(this).attr('action')+' with data: '+$(this).serialize());
                alert("Thank you for your comment!"); 
                
                $.ajax({
			        type     : "POST",
			        cache    : false,
			        url      : $(this).attr('action'),
			        data     : $(this).serialize(),
			        success  : function(data) {
			            $(".printArea").empty().append(data).css('visibility','visible');
			        }
				});
				getNotes();
            });
        });

    });
