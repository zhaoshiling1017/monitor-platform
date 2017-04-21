/**
 * Created by lj on 16/9/8.
 */
$(function(){
    $('.datetime').datetimepicker({
        format: 'YYYY-MM-DD HH:mm'
    });
    
    $('.date').datetimepicker({
        format: 'YYYY-MM-DD'
    });
    $(".datepiker-year").datetimepicker({
    	format:'YYYY'
    })
});