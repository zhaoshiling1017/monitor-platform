/**
 * Created by lj on 16/8/31.
 */
$(function(){
//    initListNumHeight();

    //全选
    $(".table thead th :checkbox").click(function(){
        selectListAll(this);
    });
    $(".table-footer :checkbox").click(function(){
        var table = $(this).parent().prev();
        if($(this).prop("checked")){
            table.find(":checkbox").prop("checked",true);
        }else {
            table.find(":checkbox").prop("checked",false);
        }
    });

    //点击单个checkbox时,更改全选checkbox的状态
    $(".table tbody td :checkbox").click(function(){
        var all = $(".table thead th :checkbox");
        var _all = $(".table-footer :checkbox");
        //判断当前点击的checkbox是否为选中状态;如果是,则继续判断
        if($(this).prop("checked")){
            if(ifAllChecked()){
                all.prop("checked",true);
                _all.prop("checked",true);
            }else{
                all.prop("checked",false);
                _all.prop("checked",false);
            }
        }else{
            all.prop("checked",false);
            _all.prop("checked",false);
        }
    });

    //点击标题一次后转为已读
    $(".table tbody .inform-title").one('click',function(){
        $(this).parent().parent().removeClass("bold");
    });

    //标为已读
    $("[action='read']").click(function(){
        var all = $(".table thead th :checkbox");
        var _all = $(".table-footer :checkbox");
        var table = $(this).parent().prev();
        if(all.prop("checked")){
        	var arr = getCheckedId();
        	for(var i=0;i<arr.length;i++){
        		readMessage(arr[i]);
        	}
            table.find("tr").removeClass("bold");
            table.find(":checkbox").prop("checked",false);
            _all.prop("checked",false);
        }else {
        	var arr = getCheckedId();
        	for(var i=0;i<arr.length;i++){
        		readMessage(arr[i]);
        	}
            table.find(":checkbox:checked").parent().parent().removeClass("bold");
            table.find(":checkbox").prop("checked",false);
        }
    });
    //删除
    $("[action='delete']").click(function(){
        var all = $(".table thead th :checkbox");
        var _all = $(".table-footer :checkbox");
        var table = $(this).parent().prev();
        if(all.prop("checked")){
        	var arr = getCheckedIdAll();
        	for(var i=0;i<arr.length;i++){
        		delMessage(arr[i]);
        	}
            all.prop("checked",false);
            _all.prop("checked",false);
            table.find("tbody").empty();
        }else{
        	var arr = getCheckedIdAll();
        	for(var i=0;i<arr.length;i++){
        		delMessage(arr[i]);
        	}
            //table.find(":checkbox:checked").parent().parent().remove();
//            $(".alert-box .btn-cancel").on('click',function(){
                table.find(":checkbox:checked").parent().parent().remove();
//            });
            AlertBox(0);
        }
    });

    //权限配置:选择全部
    $(".table-auth").find("#selectAll").click(function(){
        selectAll(this);
    });

    //权限配置:选中所有子项
    $(".table-auth").find(".selectChild").click(function(){
        selectChild(this);
    });

    $("[action='on'],[action='off']").click(function(){
        var action = $(this).attr("action");
        switchAction(action,this);
    });
    
  //权限全选功能
	$(document).on("change",".permission :checkbox",function(){
		var obj = $(this).parent();
		var list = $(this).closest(".list");
		if(obj.siblings().is("ul")){
			obj.siblings("ul").find(":checkbox").prop("checked",$(this).prop("checked"));
		}
		list.find("li ul").each(function(){
			var obj2 = $(this).siblings("label").find(":checkbox");
			if($(this).find(":checked").length > 0){
				obj2.prop("checked",true);
			}
		})
		 
		if(list.find("li :checked").length > 0){
			list.find(".P-role :checkbox").prop("checked",true);
		}
		
	})

});

//获取选中条目的未读id
function getCheckedId(){
	var items = $(".table tbody .bold").find(":checkbox:checked");
	var arr = [];
	for(var i = 0;i<items.length;i++){
		arr.push($(items[i]).attr("value"));
	}
	return arr;
}
//获取选中条目的id
function getCheckedIdAll(){
	var items = $(".table tbody").find(":checkbox:checked");
	var arr = [];
	for(var i = 0;i<items.length;i++){
		arr.push($(items[i]).attr("value"));
	}
	return arr;
}

//判断是否所有列表项均被选中
function ifAllChecked() {
    var sign = 1;
    $(".table tbody td :checkbox").each(function(){
        if(!$(this).prop("checked")){
            sign = 0;
            return false;
        }
    });
    return sign;
}

//初始化主页中通知数目容器的高度
function initListNumHeight() {
    $(".list-num").each(function(){
        var h = $(this).next().innerHeight();
        $(this).css({height:h+'px',lineHeight:h+'px'});
    });
}

//选中所有列表项
function selectListAll(eObj) {
    var table = $(eObj).parentsUntil(".table").filter("thead").parent();
    if($(eObj).prop("checked")){
        table.find("tbody :checkbox").prop("checked",true);
        table.next().find(":checkbox").prop("checked",true);
    }else{
        table.find("tbody :checkbox").prop("checked",false);
        table.next().find(":checkbox").prop("checked",false);
    }
}

//权限配置:全选
function selectAll(eObj) {
    var all = $(eObj).parents("tbody").find(":checkbox");
    if($(eObj).prop("checked")){
        all.prop("checked",true);
    }else{
        all.prop("checked",false);
    }
}

//权限配置:选中所有子项
function selectChild(eObj) {
    var children = $(eObj).next().find(":checkbox");
    if($(eObj).prop("checked")){
        children.prop("checked",true);
    }else{
        children.prop("checked",false);
    }
}

//启用和停用，待完成
function switchAction(action,ele) {
    switch(action){
        case 'on':
            $(ele).text("停用").attr("action","off");
            break;
        case 'off':
            $(ele).text("启用").attr("action","on");
            break;
        default:
            break;
    }
}

//判断日期输入是否合法，待完成
function isTimeLegal(){
    var timeStart = $(".date-picker.start").val();
    var timeEnd = $(".date-picker.end").val();

    if(timeStart && timeEnd){
        if(timeStart > timeEnd){
            return false;
        }else {
            return true;
        }
    }else {
        return false;
    }
}