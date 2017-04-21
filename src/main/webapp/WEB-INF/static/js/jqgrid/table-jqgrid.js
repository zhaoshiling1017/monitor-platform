/**
 * Created by chenyun on 16/10/13.
 */
$(function(){
	//系统消息标为已读
	$(".page-left .mark-read").click(function(){
		$(".ui-jqgrid-btable input:checked").each(function(){
			var id = $(this).closest("tr").attr("id");
			readMessage(id);
		})
		$(".ui-jqgrid-btable").jqGrid().trigger('reloadGrid');
	})
	//系统消息删除表格中的数据
	$(".page-left .delete").click(function(){
		$(".ui-jqgrid-btable input:checked").each(function(){
			var id = $(this).closest("tr").attr("id");
			delMessage(id);
		})
		$(".ui-jqgrid-btable").jqGrid().trigger('reloadGrid');
	})
	//集团消息删除
	$(".page-left .msg-delete").click(function(){
		$(".ui-jqgrid-btable input:checked").each(function(){
			var id = $(this).closest("tr").attr("id");
			delGroupMessage(id);
		})
		$(".ui-jqgrid-btable").jqGrid().trigger('reloadGrid');
	})
	//系统通知标记已读
	$(".page-left .notice-mark-read").click(function(){
		$(".ui-jqgrid-btable .bold input:checked").each(function(){
			var id = $(this).closest("tr").attr("id");
			readNotice(id);
		})
		$(".ui-jqgrid-btable").jqGrid().trigger('reloadGrid');
		noticeUnCount();
	})
	//系统通知删除表格中的数据
	$(".page-left .notice-delete").click(function(){
		$(".ui-jqgrid-btable input:checked").each(function(){
			var id = $(this).closest("tr").attr("id");
			delNotice(id);
		})
		$(".ui-jqgrid-btable").jqGrid().trigger('reloadGrid');
		noticeUnCount();
	})
})
//系统消息标记已读
function readMessage(id){
	jQuery.ajax({
		type: "get",
		url: "readMessage/"+id,
		dataType: "json",
		error: function (request) {
			alert("网络错误");
			
		},
		success: function (data) {
			$("#"+id).removeClass("bold");
		}
	});	
}
//系统消息消息通知删除
function delMessage(id){
	jQuery.ajax({
		type: "get",
		url: "delMessage/"+id,
		dataType: "json",
		error: function (request) {
			alert("网络错误");
		},
		success: function (data) {
		}
	});	
}
//集团消息删除
function delGroupMessage(id){
	jQuery.ajax({
		type: "get",
		url: "delGroupMessages/"+id,
		dataType: "json",
		error: function (request) {
			alert("网络错误");
		},
		success: function (data) {
		}
	});	
}
//系统通知标记已读
function readNotice(id){
	jQuery.ajax({
		type: "get",
		url: "readNotice/"+id,
		dataType: "json",
		error: function (request) {
			alert("网络错误");
			
		},
		success: function (data) {
			$("#"+id).removeClass("bold");
			noticeUnCount();
		}
	});	
}
//系统通知删除表格中的数据
function delNotice(id){
	jQuery.ajax({
		type: "get",
		url: "delNotice/"+id,
		dataType: "json",
		error: function (request) {
			alert("网络错误");
		},
		success: function (data) {
			noticeUnCount();
		}
	});	
}

function resize(){
	var obj = $(".ui-jqgrid,.ui-jqgrid-view,.ui-jqgrid-hdiv,.ui-jqgrid-htable,.ui-jqgrid-bdiv,.ui-jqgrid-btable,.ui-jqgrid-pager");
	obj.css("width","100%");
}
