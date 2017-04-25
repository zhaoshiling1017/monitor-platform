/**
 * Created by chen yun  on 2017/1/5.
 */
$(function() {
    $("#jqGrid").jqGrid({
        url: 'http://trirand.com/blog/phpjqgrid/examples/jsonp/getjsonp.php?callback=?&qwery=longorders',
        mtype: "GET",
        datatype: "jsonp",
        colModel: [
            { label: 'OrderID', name: 'OrderID', key: true, width: 75 },
            { label: 'Customer ID', name: 'CustomerID', width: 150 },
            { label: 'Order Date', name: 'OrderDate', width: 150 },
            { label: 'Freight', name: 'Freight', width: 150 ,formatter:function(cellvalue){
                var str = cellvalue + "<span class='icon-search'></span>";
                return str;
            }},
            { label:'Ship Name', name: 'ShipName', width: 150 },
            {label:"操作",name:'aa',width:150,formatter:function(){
            	return '<a href="faults/show">详情</a>';
            }}
        ],
        viewrecords: true,
        autowidth:true,
        height:'auto',
        rowNum: 10,
        pager: "#jqGridPager"
    });
})