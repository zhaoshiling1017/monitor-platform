/**
 * Created by lj on 16/8/31.
 */
$(function(){
	//初始化日期控件
	if($(".datetimepicker").length>0){
		$(".datetimepicker").datetimepicker({
	        format: 'YYYY-MM-DD'
	    })
	}
	//组时间插件触发 dp.change事件
    $(".start_time,.end_time").on("dp.change", function (event) {
    	console.log(111)
        var obj = $(this).siblings(".datetimepicker");
        $(event.target).is(".end_time") ? obj.data("DateTimePicker").maxDate($(this).find("input").val()) : obj.data("DateTimePicker").minDate($(this).find("input").val());
    })
    resize();
    $(window).resize(function(){
        resize();
    })
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
            table.find(":checkbox:checked").parent().parent().remove();
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
	//模态框中的取消按钮事件
	$(".modal .btn-cancel").click(function(){
		$(".modal").hide();
	})
});

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
function resize(){
    var obj = $(".ui-jqgrid,.ui-jqgrid-view,.ui-jqgrid-hdiv,.ui-jqgrid-htable,.ui-jqgrid-bdiv,.ui-jqgrid-btable,.ui-jqgrid-pager");
    obj.css("width","100%");
}