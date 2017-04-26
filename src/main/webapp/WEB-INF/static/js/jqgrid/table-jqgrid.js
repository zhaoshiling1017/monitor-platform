/**
 * Created by chenyun on 16/10/13.
 */
$(function(){
	resize();
	$(window).resize(function(){
		resize();
	})
})

function resize(){
	var obj = $(".ui-jqgrid,.ui-jqgrid-view,.ui-jqgrid-hdiv,.ui-jqgrid-htable,.ui-jqgrid-bdiv,.ui-jqgrid-btable,.ui-jqgrid-pager");
	obj.css("width","100%");
}
