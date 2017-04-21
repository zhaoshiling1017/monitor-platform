/**
 * Created by zhangboya on 2016/8/31.
 */
$(function () {
    WinPageChange();
    changeWidth();
    $(window).resize(function () {
        WinPageChange();
        changeWidth();
    })
    //响应 页面改变
    function WinPageChange() {
        //设置 section 部分的高度
        $('.section').height($(window).height() - 120);
        //设置日历的高度
        $('#calendar').height($('.iframe-parent').height())
    }

    //li 增加点击事件
    $('.nav-ul li').click(function () {
        !$(this).hasClass('multi-select')
        && $(this).addClass('selected').siblings().removeClass('selected');
        $(this).attr('data-href')
        && window.open($(this).attr('data-href'), 'myiframe')
    })
    // li 增加 hover 事件
    $(".nav-ul li,.main-select").hover(function () {
        $(this).find('.seat-icon').removeClass('icon-left-triangle').addClass('icon-lower-triangle');
        $(this).find('.collapse').show();
        $(this).next('.collapse').show();
    }, function () {
        $('.nav-ul li').removeClass('active');
        $(this).find('.collapse').hide();
        $(this).next('.collapse').hide();
        $(this).find('.seat-icon').removeClass('icon-lower-triangle').addClass('icon-left-triangle');
        //$(this).hasClass('selected')?$(this).find('.seat-icon').removeClass('icon-lower-triangle').addClass('icon-left-triangle'):$(this).find('.seat-icon').removeClass().addClass('seat-icon');
    })
    //折叠框 中li hover 事件
    $('.nav-ul li .collapse li').hover(function () {
        $(this).parents('.link').addClass('active')
    })
    $('.nav-ul li .collapse li').click(function () {
        $(this).parents('.link').addClass('selected')
                                .siblings().removeClass('selected');
    })
   
    //动态设置表单的宽度
    function changeWidth() {
    	var width =$(".changed-width").parents('.the-information-input').width() - $('.changed-width').prev('label').width();
        $('.changed-width').width(width - 18)
        $('.changed-width.dropDown').width($(".changed-width.dropDown").parents('.the-information-input').width() - $('.changed-width.dropDown').prev('label').width() - 7)
         $('textarea.changed-width,select.changed-width,input.changed-width').width(width-14);
        $('ul.dropDown').width($('ul.dropDown').siblings('input.changed-width').width()-4)
    }

    //设置.height-marki 高度
    $('.height-mark1').height($('.height-mark1-anchor').height() - 2);
    $('.height-mark2').height($('.height-mark2-anchor').height() - 2);
    //触发按钮显示取消模态框
    $('[data-modal]').click(function () {
        $('#' + $(this).attr('data-modal')).show();
        $('#mask', parent.document).show()
    })
    //触发取消模态框
    $('.modal-header .icon-delete,.modal-footer .disappear-modal').click(function () {
        $(this).parents('.modal ').hide();
        $('#mask', parent.document).hide();
    })
    //关闭日历关
    $('.icon-close:not(:animated)').click(function () {
        $(".iframe-parent").animate({
            marginRight: "0px"
        }, 500)
        $('#calendar').animate({
            right: "-250px"
        }, 500, function () {
            $('#calendar').hide();
        })
        $('.icon-calendar-alt-stroke').animate({
            opacity: 1
        }, 500)
    })
    //打开日历开
    $('nav .icon-calendar-alt-stroke:not(:animated)').click(function () {
        $('#calendar').show();
        $(".iframe-parent").animate({
            marginRight: "250px"
        }, 500)
        $('#calendar').animate({
            right: "15px"
        }, 500)
        $('.icon-calendar-alt-stroke').animate({
            opacity: 0
        }, 500)
    })
    //AlertBox(4,"icon-ok",'orange','你好','是,取消',true);
    //提示框 例 AlertBox(4,"icon-ok",'orange','你好','确定,取消',true);
    function AlertBox(status, icon, color, text, btnText, boolean) {
        var str = '<div id="alert-box" class="alert-box clear">'
            + '<div class="alert-header clear">'
            + '<span class="right icon-delete"></span>'
            + '</div>'
            + '<div class="alert-body">'
            + '<span id="icon-placeholder"></span>'
            + '<p class="weight-bold"></p>'
            + '</div>'
            + '<div class="alert-footer clear">'
            + '<div class="btn-group">'
            + '<a href="javascript:void(0)" class="btn btn-primary"></a>'
            + '<a href="javascript:void(0)" class="btn btn-cancel"></a>'
            + '</div>'
            + '</div>'
            + '</div>';
        $("body").append(str);
        if (status == 0) {//删除框
            $('#icon-placeholder').removeClass().addClass('icon-amazed');
            $('.alert-body .weight-bold').text('数据即将删除，是否执行操作？')
            $('.btn-group .btn-primary').text('是');
            $('.btn-group .btn-cancel').text('否');
            $('.alert-box').data('disappear', false);
        } else if (status == 1) {//操作成功
            $('#icon-placeholder').removeClass().addClass('icon-ok');
            $('.alert-body .weight-bold').text('操作成功')
            $('.btn-group .btn-primary').text('确定');
            $('.btn-group .btn-cancel').hide();
            $('.alert-box').data('disappear', true);
        } else if (status == 2) {//操作失败
            $('#icon-placeholder').removeClass().addClass('icon-delete');
            $('.alert-body .weight-bold').text('操作失败，请重试');
            $('.btn-group .btn-primary').text('确定');
            $('.btn-group .btn-cancel').hide();
            $('.alert-box').data('disappear', true);
        } else if (status == 4) {//自定义 (需要传入 icon图标，icon 颜色, 文字作为参数,按钮组中中的按钮个数,按钮上的文字k,是否自动消失)
            $('#icon-placeholder').removeClass().addClass(icon).css('color', color);
            $('.alert-body .weight-bold').text(text);
            var arrText = btnText.split(',');
            arrText.length == 1 && $('.btn-group .btn-cancel').hide();
            arrText.forEach(function () {
                $('.btn-group .btn-primary').text(arrText[0]);
                $('.btn-group .btn-cancel').text(arrText[1]);
            })
            $('.alert-box').data('disappear', boolean);
        }
        //判断自定义的提示框是否自动消失
        if ($('.alert-box').data('disappear') == true) {
            $('.alert-box').slideUp(2500, function () {
                $(this).remove();
            })
        } else {
            $('#mask', parent.document).show();
            $('body').append('<div id="mask-iframe"></div>');
            $('#mask-iframe').show()
        }
        //点击关闭和取消按钮
        $('#alert-box .icon-delete,#alert-box .btn-cancel').click(function () {
            $("#mask", parent.document).hide();
            $('#mask-iframe').remove();
            $('.alert-box').remove();
        })
    }
    
    //刷新功能
    $('.icon-refresh').parent().click(function () {
        window.location.reload();
    })
    //下拉多选框
    $('body').click(function () {
        $('.can-select,ul.dropDown').hide()           
    })
    //阻止冒泡
    $('.dropDown,.select-drop li').click(function () {
        event.stopPropagation()
    })
    $('.select-drop').click(function () {
        $(this).next('.can-select').toggle(200);
    })
    //下拉多选 全选功能
    $('.Multiselect .can-select li:has(".select-all-items")').click(function () {
        var this_drop = $(this).parents('.dropDown').find('.select-drop');
        var this_checkbox = $(this).find(':checkbox');
        this_checkbox.prop("checked", !this_checkbox.prop("checked"));
        if (!this_checkbox.prop("checked")) {
            $(this).siblings().find(':checkbox').prop("checked", false);
            this_drop.empty();
        } else {
            $(this).siblings().find(':checkbox').prop('checked', true);
            this_drop.empty();
            $(this).parents('.can-select').find("li:not(:has('.select-all-items')) label").each(function () {
                var str = '<li><a class="icon-delete red-font" href="javascript:void(0)"></a>' +
                    '<b>' + $(this).text() +
                    '</b></li>';
                this_drop.append(str)
            })
        }
    })
    // .select-all-items 点击事件
    $('.select-all-items').click(function (event) {
        event.stopPropagation();
        var this_select_drop = $(this).parents('.dropDown').find('.select-drop');
        if ($(this).prop('checked')) {
            this_select_drop.empty();
            $(this).parent().siblings().find(':checkbox').prop('checked', true);
            $(this).parents('.can-select').find("li:not(:has('.select-all-items')) label").each(function () {
                var str = '<li> <a class="icon-delete red-font" href="javascript:void(0)"></a>' +
                    '<b>' + $(this).text() +
                    '</b></li>';
                this_select_drop.append(str)
            })
        } else {
            $(this).parent().siblings().find(':checkbox').prop('checked', false);
            this_select_drop.empty();
        }
    })
    //单项删除按钮
    $('.select-drop').on('click', '.icon-delete', function (event) {
        event.stopPropagation();
        var can_select = $(this).parents(".dropDown").find('.can-select');
        $(this).parent('li').remove();
        can_select.find('li:contains(' + $(this).next('b').text() + ') :checkbox').prop('checked', false);
        can_select.find('.select-all-items').prop('checked', false)
    })
    //当单项选择时
    $('.Multiselect .can-select li:not(:has(".select-all-items"))').click(function () {
        var str = '<li>' +
            '<a class="icon-delete red-font" href="javascript:void(0)"></a>' +
            '<b>' + $(this).find('label').text() +
            '</b></li>';
        $(this).parents('.can-select').prev('.select-drop').find('li b').text().indexOf($(this).find('label').text()) == -1
            ? ($(this).find(':checkbox').prop('checked', true) && $(this).parents('.can-select').prev('.select-drop').append(str))
            : ($(this).find(':checkbox').prop('checked', false)
            && $(this).parents('.dropDown').find('.select-drop li:contains(' + $(this).find('label').text() + ')').remove()
            && $(this).parents('.dropDown').find('.select-all-items').prop('checked', false)
        )
    })
    //下拉单选
    $('.Radio .can-select li input').click(function () {
    	console.log($(this).prop('checked'))
    	var L = $(this).closest('.Radio').find('.select-drop li').length;
    	var obj = $(this).parents('.can-select').prev('.select-drop');
    	var text = $(this).next().text();
    	var flag = $(this).prop('checked');
    	var str = '<li>' +
        '<a class="icon-delete red-font" href="javascript:void(0)"></a>' +
        '<b>' + text +'</b></li>';
    	if(!flag){
    		obj.html('');
    	}else{
    		if(L == 0){
        		obj.append(str);
        	}else{
        		obj.find('b').text(text);
        		$(this).parent().siblings().find('input').prop('checked',false);
        	}	
    	}
    	$(this).parents('.can-select').hide();
    })
    $('.Radio .can-select li label').click(function(){
    	$(this).prev().click();
    })
    //初始化下拉菜单列表
    /*$('.can-select').each(function () {
        var select_li = $(this).find('li:not(:first)');
        select_li.each(function () {
            var str = '<li>' +
                '<a class="icon-delete red-font" href="javascript:void(0)"></a>' +
                '<b>' + $(this).next().text() +
                '</b></li>';
            var the_checkbox = $(this).find(':checkbox');
            the_checkbox.each(function () {
                if ($(this).prop('checked')) {
                    $(this).parents('.dropDown').find('.select-drop').append(str);
                }
            })
        })
    })*/
    var brr=[];
    var arr=[];
   
    //icon-add add-people 增加点击事件
    $('.choose-role-g div.changed-width').click(function(){
    	  event.stopPropagation();
    })
    $('.icon-add.add-people').click(function(){
    	$(this).siblings('ul.dropDown').show();
    })
    //全选
    $('.dropDown .select-all').click(function (){
    	if(($(this).prop('checked'))){
    		 arr =[];
    		var selects=$('.dropDown input:not(".select-all")');
    		selects.prop('checked',true);
    		selects.each(function(){
    			 arr.push($(this).next().text())
    		 }) 
    		 for(var i=1;i<=selects.length;i++){
    			 brr.push(i)
    		 }
    		 
    	}else{
    		arr=[];
    		brr=[];
    	}
    	$(this).parents('.choose-role-g').find('[name="roleIds"]').val(arr.join(","));
    	$('#choose-role-input').val(brr.join(','))
    })
    $('.dropDown input:not(".select-all")').click(function(){
    	var text=$(this).next().text();
    	if($(this).prop('checked')){
    		arr.push(text);
    		brr.push($(this).val());
    	}else{
    		for(var key in arr){
    			arr[key]==text&&arr.splice(key,1)&&brr.splice(key,1);
    		}
    	}
    	$(this).parents('.choose-role-g').find('[name="roleIds"]').val(arr.join(","));
    	$('#choose-role-input').val(brr.join(','))
    })
    //返回按钮关联导航栏的选中状态
    $(".btn-back").click(function(){
    	var href = $(this).attr("href");
    	var obj = $('.nav-ul li[data-href="'+href+'"]',parent.document) || $(".nav-ul a[href='"+href+"']",parent.document).closest("ul").closest("li");
    	if(obj.lenght>0){
    		$(".nav-ul li",parent.document).removeClass("selected");
        	obj.addClass("selected");
    	}
    })
    //任务详情字段名称长度
    $(".the-information-list").each(function(){
    	var w = $(this).width() - $(this).find("p label").width() - 35;
    	$(this).find(">p>span").width(w);
    })
});

function noticeUnCountIndex(){
	jQuery.ajax({
       type: "post",
       url: "notices/noticeUnCount",
       dataType: "json",
       error: function (request) {
       		initAlert(0,"网络错误");   
       },
       success: function (data) {
    	   $("#noticeCount").text(data);      
       	}
	});
}
function noticeUnCount(){
	jQuery.ajax({
       type: "post",
       url: "noticeUnCount",
       dataType: "json",
       error: function (request) {
       		initAlert(0,"网络错误");   
       },
       success: function (data) {
    	   var obj = $("#noticeCount",parent.document);
    	   //obj.text("");
    	   obj.text(data);
       	}
	});
}

//表单验证
function formValidation(form){
	var obj = form.find(".red-star").next();
	var n = 0;
	obj.each(function(){
		//文本框验证
		if(($(this).is("input") && $.trim($(this).val()) == '') || 
			($(this).is(".dropDown") && $(this).find(".select-drop li").length == 0) || 
			($(this).is("select") && $(this).val() == 0) ||
			($(this).is("textarea") && $.trim($(this).val()) == '') ||
			($(this).is(".datetimepicker") && $.trim($(this).find("input").val()) == '')
		){
			var text = $(this).prev().text();
			var error = text.substring(0,text.length-1);
			var str = ($(this).is("select") ? '<b>请选择'+ error +'</b>' : '<b>'+ error +'不能为空！</b>');
			if($(this).next().is("b")){
				str = ($(this).is("select") ? '请选择'+ error : error +'不能为空！');
				$(this).next().text(str);
			}else{
				$(str).insertAfter($(this));
			}
			n++;
		 }else{
			 if($(this).next().is("b")){
				$(this).next().text('');
			}
		 }
	 })
	 return (n == 0 ? true : false);
}
