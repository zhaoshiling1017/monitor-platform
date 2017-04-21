$(function(){
	//附件上传
	var uuid = $('#uuid').val();
	var url = $('#uuid').attr("data-url");
    $('#upload').diyUpload({
        url:url + uuid,
        success:function( data ) {
        },
        error:function( err ) {
            console.info( err );
        },
        buttonText : '附件上传',
        threads:1,   //上传并发数
        accept: {}
    });
})