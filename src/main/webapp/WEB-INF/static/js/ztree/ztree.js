/**
 * Created by chen yun  on 2016/8/9.
 */
$(function(){
	if($("#nodes").length>0) {
        var data = $("#nodes").val();
        var zNodes = JSON.parse(data);
        $.fn.zTree.init($("#defectTree"), setting, zNodes);
    }
})
var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: true
    },
    edit: {
        enable: true,
        editNameSelectAll: true,
        showRemoveBtn: showRemoveBtn,
        showRenameBtn: showRenameBtn,
        removeTitle: '删除',
        renameTitle: '编辑'
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onRemove: onRemove,
        onRename: onRename,
        onClick: onClick
    }
};


var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
    return false;
}
function beforeEditName(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    var zTree = $.fn.zTree.getZTreeObj("defectTree");
    zTree.selectNode(treeNode);
    return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");

}
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    var zTree = $.fn.zTree.getZTreeObj("defectTree");
    zTree.selectNode(treeNode);
    var result = false;
    var nodeId = treeNode.id;
    if(!confirm("确认删除 节点 -- " + treeNode.name + " 吗？")){
        return result;
    }
    $.ajax({
        type: 'GET',
        url: "dics/" + nodeId + "/beforeDel",
        async: false,
        data: null,
        dataType: "json",
        success: function(rs) {
            if (rs.data == 1) {
                result = true;
            } else {
                Alert("fail", rs.message);
            }
        }
    });
    return result;
}
function onRemove(e, treeId, treeNode) {
    var nodeId = treeNode.id;
    showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    $.ajax({
        type: 'DELETE',
        url: "dics/" + nodeId,
        async: false,
        data: null,
        dataType: "json",
        success: function(result) {
            if (result.data == 0) {
                Alert("fail", result.message);
            } else {
                Alert("success", result.message);
            }
        }
    });
}
function beforeRename(treeId, treeNode, newName, isCancel) {
    className = (className === "dark" ? "":"dark");
    showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    if (newName.length == 0) {
        setTimeout(function() {
            var zTree = getTree();
            zTree.cancelEditName();
            Alert("fail", "节点名称不能为空.");
        }, 0);
        return false;
    }
    return true;
}
function onRename(e, treeId, treeNode, isCancel) {
    showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    $.ajax({
        type: 'POST',
        url: "dics/" + treeNode.id,
        data: {_method:"PUT",nodeName:treeNode.name},
        dataType: "json",
        success: function(data) {
        }
    });
}
function showRemoveBtn(treeId, treeNode) {
    if (treeNode.id=='000') {
        return false;
    } else {
        return true;
    }
}
function showRenameBtn(treeId, treeNode) {
    if(treeNode.id=='000'){
        return false;
    }else{
        return true;
    }
}
function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now= new Date(),
        h=now.getHours(),
        m=now.getMinutes(),
        s=now.getSeconds(),
        ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    var str = sObj.closest("li").attr("class");
    var level = str.substring(5,str.length);
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    if(level<5){ //限制层级在小于四级的时候显示添加按钮
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='新增' onfocus='this.blur();'></span>";
        sObj.after(addStr);
    }
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function() {
        saveNode(treeNode);
        return false;
    });
};

/*保存新的节点*/
function saveNode(parentNode){
    var zTree = getTree();
    var _nodeName = "新节点";
    $.ajax({
        type: 'POST',
        url: "dics",
        data: {pId:parentNode.id,name:_nodeName},
        dataType: "json",
        success: function(data) {
            var newCode = {id:data.nodeCode,pId:parentNode.id,name:_nodeName};
            zTree.addNodes(parentNode,newCode);
        }
    });
}

function getTree(){
    return $.fn.zTree.getZTreeObj("defectTree");
}

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
}

/*单击节点显示节点详情*/
function onClick(e,treeId,treeNode) {
    show(treeNode);
}

/*右侧区域显示*/
function show(treeNode) {
    $('#nodeCode').val(treeNode.id);
    $("#timeon").val("");
    $("#timeoff").val("");
    $("#carNumber").val("");
    getTableData();
}

function selectAll() {
    var zTree = $.fn.zTree.getZTreeObj("defectTree");
    zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
}