var zTree = null;
var openTreeNodeIdMap = new Object();
var zNodes = [ {
	id : "root",
	name : "单位结构",
	isParent : true,
	type : "Unit",
	icon : baseUrl + "/static/common/default/img/user/root.png",
	parentId : null
} ];
var setting = {
	edit : {
		enable : true,
		showRenameBtn : false,
		showRemoveBtn : false
	},
	check : {
		enable : false
	},
	view : {
		showIcon : true
	},
	async : {
		enable : true,
		url : baseUrl + "/unit/treeNode.do",
		autoParam : [ "id", "name", "level", "type", "parentId" ],
		type : "post"
	},
	callback : {
		// 点击触发的方法
		// onRightClick : zTreeOnRightClick,
		// onMouseDown : zTreeOnMouseDown,
		onClick : zTreeOnClick,
		onExpand : zTreeOnExpand,
		onCollapse : zTreeOnCollapse,
		onAsyncSuccess : zTreeOnAsyncSuccess
	}
};

function zTreeOnClick(event, treeId, treeNode) {
	var ids = "";
	for (prop in openTreeNodeIdMap) {
		ids += (prop + ",")
	}
	window.parent.frames['bodyFrame'].location.href = (baseUrl + "/user/list.do?frame=true&unitId=" + treeNode.id + "&openTreeNode=" + ids);
}

function zTreeOnExpand(event, treeId, treeNode) {
	openTreeNodeIdMap[treeNode.id] = true;
};

function zTreeOnCollapse(event, treeId, treeNode) {
	if (openTreeNodeIdMap[treeNode.id]) {
		delete openTreeNodeIdMap[treeNode.id];
	}
};

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("zTree");
	for (prop in openTreeNodeIdMap) {
		var node = treeObj.getNodeByParam("id", prop, treeNode);
		if (node) {
			if (!node.open) {
				treeObj.expandNode(node, true, false, true, true);
			}
		}
	}
};

$(function() {
	zTree = $.fn.zTree.init($("#zTree"), setting, zNodes);
	var openTreeNode = $("#openTreeNode").val();
	if (0 < openTreeNode.length) {
		var node = zTree.getNodeByParam("id", "root", null);
		var idsArr = openTreeNode.split(",");
		for (var i = 0, len = idsArr.length; i < len; i++) {
			if (0 < idsArr[i].length) {
				openTreeNodeIdMap[idsArr[i]] = true;
			}
		}
		zTree.expandNode(node, true, false, true, false);
	}
});