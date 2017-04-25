$(function () {
    $(".tab-pane[id^='part'] .icon-5").click(function(){
        var target = $(this).closest(".tab-pane");
    	var lis = target.find("li");
        var next = target.find("li:visible").next();
        var first = target.find("li:first");
        var text = '';
        if(next.is("li")){
        	text = next.index()+1+'/2';
        	lis.hide();
            next.fadeIn();
        }else{
        	text = first.index()+1+'/2';
        	lis.hide();
            first.fadeIn();
        }
        $(this).prev().text(text);
    })
    $(".tab-pane[id^='part'] .icon-4").click(function(){
        var target = $(this).closest(".tab-pane");
    	var lis = target.find("li");
        var prev = target.find("li:visible").prev();
        var last = target.find("li:last");
        var text = '';
        if(prev.is("li")){
        	text = prev.index()+1+'/2';
        	lis.hide();
        	prev.fadeIn();
        }else{
        	text = last.index()+1+'/2';
        	lis.hide();
        	last.fadeIn();
        }
        $(this).next().text(text);
    })
})