111

```js
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="../../header.jsp" %>
<%
    request.setAttribute("pduId", request.getParameter("pduId"));
    request.setAttribute("userName", request.getParameter("userName"));
    request.setAttribute("versionName", request.getParameter("versionName"));
    request.setAttribute("pbiCode", request.getParameter("pbiCode"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>softwareAndHardwareRelationView</title>
    <link href="${_ctx }/common/default/css/softwareAndHardwareRelationView/softwareAndHardwareRelationView.css?v=4"
          rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        var global = {};
        //当前tab页是否有版本信息,默认为true
        global.hasVersionInfo = true;
        //基本信息
        global.pduId = "${pduId}";
        global.userName = "${userName}";
        global.versionName = "${versionName}";
        global.pbiCode = "${pbiCode}";

    </script>
</head>
<body class="easyui-layout" id="easyuiLayout">

<div data-options="region:'center',title:''" style="padding:5px;background:#eee;">
    <div id="tabContainer">
        <div id="tab-div" class="tab-div">
            <button id="pmsHard-info" class="item tabBtn tabSelect" data-value="179" data-attrflag="2">
                <span>&nbsp;&nbsp;软硬件配套视图&nbsp;&nbsp;</span>
            </button>
        </div>
    </div>
    <div class="mainIfram" style="height: 92%">
        <!-- tab引入 -->
        <div id="centerZone" style="height:100%"
             data-options="region:'center',title:'&nbsp;',collapsible:false,border:false,fit:true" style="padding:1px;">
            <div id="btn-div">
                <a id="btnConfirm" href="javascript:void(0)" class="addBtn" onclick="showAddConfigPage()">Add</a>
                <a id="btnDelete" href="javascript:void(0)" class="refBtn" onclick="showDeleteConfigPage()">Delete</a>
                <a id="btnRefuse" href="javascript:void(0)" class="refBtn" onclick="showEditConfigPage()"
                   style="display: none">Edit</a>
                <a id="btnBaseline" href="javascript:void(0)" class="refBtn"
                   onclick="showBaselineConfigPage()">Baseline</a>
            </div>
            <div id="table-div" style="height: 89%;width: 99%;">
                <table id="hardAndSoftwareGrid"></table>
            </div>
        </div>
    </div>
</div>

<div id="addConfigPage">
    <span class="right-toggle" onclick="closeAddConfigPage()"></span>
    <div class="title">Add software</div>
    <iframe id="addConfigPageIframe" name="addConfigPageIframe" width="100%" height="98%" frameBorder="0" border="0"
            vspace="0" hspace="0" marginwidth="0" marginheight="0" framespacing="0" frameborder="0"></iframe>
</div>

<div id="editConfigPage">
    <span class="right-toggle" onclick="closeEditConfigPage()"></span>
    <div class="title">Edit software</div>
    <iframe id="editConfigPageIframe" name="editConfigPageIframe" width="100%" height="98%" frameBorder="0" border="0"
            vspace="0" hspace="0" marginwidth="0" marginheight="0" framespacing="0" frameborder="0"></iframe>
</div>

<div id="baselineConfigPage">
    <span class="right-toggle" onclick="closeBaselineConfigPage()"></span>
    <div class="title">Baseline</div>
    <iframe id="baselineConfigPageIframe" name="baselineConfigPageIframe" width="100%" height="98%" frameBorder="0"
            border="0" vspace="0" hspace="0" marginwidth="0" marginheight="0" framespacing="0" frameborder="0"></iframe>
</div>

<div data-options="region:'west',title:'',split:true" style="width:300px;margin-left: 15px">
    <div style="display: flex;padding-top: 10px;padding-bottom: 15px">
        <div><input type="text" id="searchText" placeholder="please input a name to search"
                    style="width:200px; height:20px;"></div>
        <div class="icon-search" onclick="searchTreeNodeByInput() "></div>
    </div>
    <div>
        <ul id="hardAndSoftwareTree"></ul>
    </div>
</div>
<script>
    var versionName = global.versionName;
    var addPageFlag = 0;
    var clickRows = [];
    var clickRow = {};
    var commonData;

    //加载左侧的树
    $('#hardAndSoftwareTree').tree({
        onClick: function (node) {
            loadTreegridBytree(node);
        }
    });

    //右侧树表数据结构
    var columns =
        [
            [
                {field: 'a', title: '选择', align: 'center', 'rowspan': 2, checkbox: true},
                // {field: 'ID', title: '全选', align: 'center', frozen: true, 'rowspan': 2, width: "10%", hidden: true}
                {field: 'text', title: '软件名称', 'rowspan': 2, halign: 'center', width: "20%"},//合并2行
                {field: 'softwareType', title: '软件类型', align: 'center', 'rowspan': 2, width: "10%"},//合并2行
                {field: 'internalModel', title: '对内型号', align: 'center', 'rowspan': 2, width: "10%"},//合并2行
                {field: 'subcategoryName', title: '小类名称', align: 'center', 'rowspan': 2, width: "10%"},//合并2行
                {field: 'partNum', title: 'part编码', align: 'center', 'rowspan': 2, width: "10%"},//合并2行
                {field: 'softwareVersion', title: '软件版本', align: 'center', 'rowspan': 2, width: "10%"},//合并2行
                {title: 'EOM', align: 'center', 'colspan': 2},//合并2列
                {title: 'EOP', align: 'center', 'colspan': 2},//合并2列
                {title: 'EOFS', align: 'center', 'colspan': 2},//合并2列
                {title: 'EOS', align: 'center', 'colspan': 2}//合并2列
            ],
            [
                {field: 'eomStart', title: '计划时间', align: 'center', 'colspan': 1, width: "8%"},
                {field: 'eomActual', title: '实际时间', align: 'center', 'colspan': 1, width: "8%"},
                {field: 'eopStart', title: '计划时间', align: 'center', 'colspan': 1, width: "8%"},
                {field: 'eopActual', title: '实际时间', align: 'center', 'colspan': 1, width: "8%"},
                {field: 'eofsStart', title: '计划时间', align: 'center', 'colspan': 1, width: "8%"},
                {field: 'eofsActual', title: '实际时间', align: 'center', 'colspan': 1, width: "8%"},
                {field: 'eosStart', title: '计划时间', align: 'center', 'colspan': 1, width: "8%"},
                {field: 'eosActual', title: '实际时间', align: 'center', 'colspan': 1, width: "8%"}
            ]
        ];
    //初始化树表
    $("#hardAndSoftwareGrid").treegrid({
        idField: 'id',
        treeField: 'text',
        width: "100%",
        height: '95%',
        method: "post",
        columns: columns,
        singleSelect: false,
        onClickRow: function (row) {
            addPageFlag = row.level;
        }
    });

    //初始化树和树表
    initData();

    /**
     * 初始化树和树表
     */
    function initData() {
        $.ajax({
            type: 'post',
            url: _rest + "/softAndHardwareTree/treeGrid",
            dataType: 'json',
            data: {
                versionName: versionName,
                sourceNum: 1,
            },
            success: function (data) {
                commonData = data.data;
                $('#hardAndSoftwareTree').tree('loadData', commonData);
                $('#hardAndSoftwareGrid').treegrid('loadData', commonData);
                $('#hardAndSoftwareGrid').treegrid('selectAll');
                $('#hardAndSoftwareGrid').treegrid('unselectAll');
            }
        });
    }

    /**
     * 通过搜索框找到相应的节点并高亮显示
     */
    function searchTreeNodeByInput() {
        var searchText = $("#searchText").val().trim().toUpperCase();
        var searchNodes = [];
        var rootData = $('#hardAndSoftwareTree').tree('getRoots');
        var allData = $('#hardAndSoftwareTree').tree('getChildren', rootData[0].target);
        allData.push(rootData[0]);
        for (var i = 0; i < allData.length; i++) {
            if (allData[i].text.toUpperCase().indexOf(searchText) !== -1) {
                searchNodes.push(allData[i].id);
            }
        }
        var rootNode = $('#hardAndSoftwareTree').tree('getRoot');
        var rootNodeId = rootNode.id;
        for (var i = 0; i < searchNodes.length; i++) {
            expandAllParentNode(searchNodes[i], rootNodeId);
            var node = $('#hardAndSoftwareTree').tree('find', searchNodes[i]);
            $(node.target).context.lastChild.classList.add("search-border");
        }

    }

    /**
     * 展开节点的所有父节点
     * @param id 节点id
     * @param rootNodeId 根节点id
     */
    function expandAllParentNode(id, rootNodeId) {
        if (id === rootNodeId) {
            return;
        }
        var node = $('#hardAndSoftwareTree').tree('find', id);
        var nodeParent = $('#hardAndSoftwareTree').tree('getParent', node.target);
        $('#hardAndSoftwareTree').tree('expand', nodeParent.target);
        expandAllParentNode(nodeParent.id, rootNodeId);
    }

    /**
     * 点击左侧树节点，加载右侧树表数据
     */
    function loadTreegridBytree(node) {
        $('#hardAndSoftwareGrid').treegrid('loadData', commonData);
        var nodeByClickTree = $('#hardAndSoftwareGrid').treegrid('find', node.id);
        $('#hardAndSoftwareGrid').treegrid('loadData', [nodeByClickTree]);
    }

    /**
     * 点击add按钮显示右侧页面
     */
    function showAddConfigPage() {
        clickRows = $('#hardAndSoftwareGrid').treegrid('getSelections');
        if ((clickRows.length === 1) && (clickRows[0].level === 4)) {
            clickRow = clickRows[0];
            var url = '../../fullProductInfoTree/softwareAdd.jsp?hdId=' +
                clickRow.attrId + '&userName=' + encodeURI(encodeURI(global.userName)) +
                '&pbiCode=' + global.pbiCode;
            $("#addConfigPageIframe").attr("src", url);
            $('#addConfigPage').show();
        } else if ((clickRows.length === 1) && (clickRows[0].level === 3) && (clickRows[0].children.length !== 0)) {
            clickRow = clickRows[0];
            var list = clickRow.children;
            var ids = list.map(o => o.attrId).join(",");
            var url = '../../fullProductInfoTree/softwareAdd.jsp?hdId=' +
                ids + '&userName=' + encodeURI(encodeURI(global.userName)) +
                '&pbiCode=' + global.pbiCode;
            $("#addConfigPageIframe").attr("src", url);
            $('#addConfigPage').show();
        } else if ((clickRows.length === 1) && (clickRows[0].level === 3) && (clickRows[0].children.length === 0)) {
            showTipsOrAlert("info", 'Info', '此制成板无可添加软件的关联成品板！');
        } else {
            showTipsOrAlert("info", 'Info', '请点击一个成品板或制成板进行添加!');
        }

    }

    /**
     * 点击关闭右侧弹出框
     */
    function closeAddConfigPage() {
        var needToRefresh = $("#btnConfig").data('refresh');
        if (needToRefresh) {
            document.getElementById("publicIframe").contentWindow.refreshHardwareType();
        }
        $('#addConfigPage').hide();
    }

    /**
     * 点击edit按钮显示右侧页面
     */
    function showEditConfigPage() {
        clickRows = $('#hardAndSoftwareGrid').treegrid('getSelections');
        if ((clickRows.length === 1) && (clickRows[0].level === 5)) {
            clickRow = clickRows[0];
            var url = '../../fullProductInfoTree/softwareEdit.jsp?softId=' +
                clickRow.softwareId +
                '&name=' + encodeURI(encodeURI(clickRow.text)) +
                '&type=' + encodeURI(encodeURI(clickRow.softwareType)) +
                '&internalModel=' + encodeURI(encodeURI(clickRow.internalModel)) +
                '&subcategoryName=' + encodeURI(encodeURI(clickRow.subcategoryName)) +
                '&spartEncoding=' + clickRow.partNum +
                '&softVersion=' + encodeURI(encodeURI(clickRow.softwareVersion)) +
                '&userName=' + encodeURI(encodeURI(global.userName));
            $("#editConfigPageIframe").attr("src", url);
            $('#editConfigPage').show();
        } else {
            showTipsOrAlert("info", 'Info', '请点击一个软件进行修改!');
        }
    }

    /**
     * 点击关闭右侧弹出框
     */
    function closeEditConfigPage() {
        var needToRefresh = $("#btnConfig").data('refresh');
        if (needToRefresh) {
            document.getElementById("publicIframe").contentWindow.refreshHardwareType();
        }
        $('#editConfigPage').hide();
    }

    /**
     * 点击基线按钮显示右侧页面
     */
    function showBaselineConfigPage() {
        var url = '../../fullProductInfoTree/softwareBaseline.jsp?version=' +
            global.versionName +
            '&userName=' + encodeURI(encodeURI(global.userName));
        $("#baselineConfigPageIframe").attr("src", url);
        $('#baselineConfigPage').show();
    }

    /**
     * 点击关闭基线页面弹出框
     */
    function closeBaselineConfigPage() {
        var needToRefresh = $("#btnConfig").data('refresh');
        if (needToRefresh) {
            document.getElementById("publicIframe").contentWindow.refreshHardwareType();
        }
        $('#baselineConfigPage').hide();
    }

    /**
     * 点击删除按钮显示删除页面
     */
    function showDeleteConfigPage() {
        //取出所有点击的行
        clickRows = $('#hardAndSoftwareGrid').treegrid('getSelections');
        //未点击，提示信息
        if(clickRows.length===0){
            showTipsOrAlert("info", 'Info', '请点击软件进行删除!');
            return;
        }
        //取出所有点击的软件id
        var deleteParam = {};
        for (var i = 0; i < clickRows.length; i++) {
            if (clickRows[i].level !== 5) {
                showTipsOrAlert("info", 'Info', '请点击软件进行删除!');
                return;
            }
            var softwareId = clickRows[i].softwareId;
            var boardId = $('#hardAndSoftwareGrid').treegrid('getParent', clickRows[i].id).attrId;
            if (deleteParam.hasOwnProperty(boardId)) {
                deleteParam[boardId] = deleteParam[boardId] + ',' + softwareId;
            } else {
                deleteParam[boardId] = softwareId;
            }
        }
        //是否删除
        $.messager.confirm('Confirm', 'Confirm delete the selected software?',
            function (r) {
                if (r) {
                    //确认删除，取出所有的软硬件关联信息
                    $.ajax({
                        type: "post",
                        url: _rest + "/versionHdRel/deleteSoftware",
                        data: {param: JSON.stringify(deleteParam), userName: global.userName},
                        dataType: "json",
                        success: function (data) {
                            var result = data.status;
                            if (result === "success") {
                                parent.showTipsOrAlert('', 'Message', "delete success");
                                initData();
                            } else {
                                parent.showTipsOrAlert("info", 'Failed', data.message);
                            }
                        },
                    });
                }
            })
    }
</script>
</body>
<style>
    .search-border {
        background-color: #ffffaa;
        border: 1px solid #5a5a5a;
    }

    .tree-icon, .tree-folder, .tree-folder-open {
        display: none;
    }

    .icon-search {
        width: 20px;
        height: 20px;
        display: inline-block;
        background-image: url(./search.png);
        margin-left: 10px;
        cursor: pointer
    }
</style>
</html>
```



22
