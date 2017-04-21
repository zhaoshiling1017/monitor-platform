/**
 * Created by lj on 16/8/16.
 */
$(function(){
    $(".switch span").click(function(){
        $(this).parent().children().removeClass("checked");
        $(this).addClass("checked");
    });
    $("#nextMsg").click(function(){
        var liList = $(".nav-icon-tab li");
        liList.removeClass("active");
        liList.eq(1).addClass("active");
        $(this).parent().css("display","none");
        $("#fillMsg").css("display","block");
    });
    $("#nextBingo").click(function(){
        var liList = $(".nav-icon-tab li");
        liList.removeClass("active");
        liList.eq(2).addClass("active");
        $(this).parent().css("display","none");
        $("#bingo").css("display","block");
    });
    $(".btn-identify").click(function(){
        countDown(this);
    });
    $(".btn-next").click(function(){
        $(this).parent().css("display","none");
        $(this).parent().next().css("display","block");
    });
});

var wait = 60;
function countDown(o){
    $(o).click(function(event){
        event.preventDefault();
    });
    if (wait == 0) {
        $(o).removeAttr("disabled");
        $(o).html("重新接收验证码");
        $(o).removeClass("forbidden");
        wait = 60;
    } else {
        $(o).attr("disabled", true);
        $(o).html("<span id='timer'>"+ wait +"</span>"+ "秒后重新获取");
        $(o).css("width",106 + "px");
        $(o).addClass("forbidden");
        wait--;
        setTimeout(function() {
            countDown(o);
        }, 1000);
    }
}