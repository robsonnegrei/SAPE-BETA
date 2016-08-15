$(function () {
    $('#fileupload').fileupload({
    	add: function(e, data) {
            var uploadErrors = [];
            
            if(data.originalFiles[0]['size'] && data.originalFiles[0]['size'] > 2000000) {
                uploadErrors.push('Tamanho de arquivo excedido, maximo 2MB');
            }
            if(uploadErrors.length > 0) {
                alert(uploadErrors.join("\n"));
            } else {
                data.submit();
            }
    	},
        dataType: 'json',
        done: function (e, data) {
        	$("tr:has(td)").remove();
            $.each(data.result, function (index, file) {
            	
            	
                $("#uploaded-files").append(
                		$('<tr/>')
                		.append($('<td/>').text(file.fileName))
                		.append($('<td/>').text(file.fileSize))
                		.append($('<td/>').text(file.fileType))
                		.append($('<td/>').html("<a href='controller/get/"+index+"'>Click</a>"))
                		)//end $("#uploaded-files").append()
            }); 
        },
        fail: function (e, data) {
            $.each(data.messages, function (index, error) {
                $('<p style="color: red;">Upload file error: ' + error + '<i class="elusive-remove" style="padding-left:10px;"/></p>')
                    .appendTo('#errors');
            });
        },
        progressall: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );
   		},
   		
   		
		dropZone: $('#dropzone')
    });
});