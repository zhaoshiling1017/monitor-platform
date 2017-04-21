function initAlert(status, message) {
    var str = '<div class="alert-box">';
    str += '<div class="alert-header clear">';
    str += '<span class="left">提示</span>';
    str += '<span class="icon-delete right"></span></div>';
    str += '<div class="alert-body text-center"><span></span><p></p></div>';
    str += '<div class="alert-footer" style="display:none;"><p class="right">';
    str += '<a href="#" class="btn btn-primary">确认</a>';
    str += '<a href="#" class="btn btn-cancel">取消</a>';
    str += '</p></div></div>';
    str += '<div class="mask"></div>';
    if($(".alert-box").length == 0){
        $("body").append(str);
    }
    var obj = $(".alert-box");								//弹出框
    var span = $(".alert-body span");						//提示图标
    var btns = $(".alert-footer");							//按钮
    var p = $(".alert-body p");								//提示信息
    var maskP = $("#mask",parent.document) || $("#mask");	//父级页面遮罩层
    var maskC = $(".mask");									//iframe页面遮罩层
    
    //status为0时:失败提示
    if(status == 0 || status == 1){
    	if(status == 0){
    		obj.removeClass().addClass("alert-box danger");
        	span.removeClass().addClass("icon-amazed");
    	}else{
        	obj.removeClass().addClass("alert-box success");
        	span.removeClass().addClass("icon-ok");
    	}
    	p.text(message);
    	maskC.show();
    	maskP.show();
    	obj.show();
        setTimeout(function(){
            obj.slideUp(400);
            maskC.fadeOut(400);
            maskP.fadeOut(400);
        },2000)
    }else{
    	obj.removeClass().addClass("alert-box success");
    	span.removeClass().addClass("icon-delete");
        p.text("确认删除这条数据吗?");
        btns.show();
        maskC.show();
    }
    //点击取消
    $(document).on("click",".alert-box .btn-cancel",function(){
        close();
    })
    //点击关闭
    $(document).on("click",".alert-box .icon-delete",function(){
        close();
    })
    function close(){
        obj.slideUp(400);
        maskP.hide();
        maskC.hide();
    }
}