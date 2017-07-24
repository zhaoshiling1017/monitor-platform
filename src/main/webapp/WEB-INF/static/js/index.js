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
           Alert(0,"网络错误");
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
       		Alert(0,"网络错误");
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
