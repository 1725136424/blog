$(function () {
    // 数据表格
    $('#datagrid').treegrid({
        url: `${contextPath}/permission/page`,
        idField: 'id',
        treeField: 'name',
        columns: [[
            {field: 'name', title: '名称', width: 100, align: 'left'},
            {field: 'resource', title: '资源地址', width: 100, align: 'center'}
        ]],
        fit: true,
        fitColumns: true,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        toolbar: "#tb",
        scrollbarSize: 0,
        striped: true,
        onLoadSuccess: function(){
            $('#datagrid').treegrid("collapseAll")
        }
    });

    // 对话框
    $('#dialog').dialog({
        width: 300,
        height: 260,
        closed: true,
        buttons: [
            {
                text: "保存",
                iconCls: 'icon-save',
                handler: function () {
                    let url;
                    if ($("input[name=id]").val()) {
                        url = `${contextPath}/permission/edit`
                    } else {
                        url = `${contextPath}/permission/save`
                    }
                    $("#saveForm").form("submit", {
                        url,
                        success: function (data) {
                            let res = JSON.parse(data);
                            if (res.isSuccess) {
                                $("#dialog").dialog("close")
                                $("#datagrid").treegrid("reload")
                            } else {
                                $.messager.alert("温馨提示", res.message)
                            }
                        }
                    })
                }
            },
            {
                text: "关闭",
                iconCls: 'icon-cancel',
                handler: function () {
                    $("#dialog").dialog("close")
                }
            }
        ],
        onClose: function () {
            // 关闭触发事件
            $("#saveForm").form("clear");
        }
    });

    // 新增按钮
    $(".save").click(function () {
        $('#dialog').dialog("open").dialog("setTitle", "新增")
    })

    // 编辑按钮
    $(".edit").click(function () {
        let selectedData =  $('#datagrid').treegrid("getSelected")
        if (selectedData) {
            $("#saveForm").form("load", selectedData)
            $('#parent').combotree("setValue", selectedData.parentId)
            $("#dialog").dialog("open").dialog("setTitle", "编辑")
        } else {
            $.messager.alert("温馨提示", "请选择一条数据进行编辑")
        }
    })

    // 删除按钮点击
    $(".delete").click(function () {
        $.messager.confirm("温馨提示", "是否删除", function (confirm) {
            if (confirm) {
                // 获取当前选择的数据
                let selectedRow = $('#datagrid').treegrid("getSelected")
                $.ajax({
                    url: `${contextPath}/permission/delete?id=${selectedRow.id}`,
                    method: "get",
                    dataType: "json",
                    success: function (data) {
                        if (data.isSuccess) {
                            $("#datagrid").treegrid("reload")
                        } else {
                            $.messager.alert("温馨提醒",)
                        }
                    }
                })
            }
        })
    })

    // 刷新按钮
    $(".reload").click(function () {
        $("#datagrid").treegrid("reload", {})
    })

    // 搜索
    $(".search").click(function () {
        let keywords = $("input[name=keywords]").val().trim();
        if (keywords) {
            $("#datagrid").treegrid("load", {
                keywords
            })
        } else {
            $("#datagrid").treegrid("load", {})
        }
    })

    // 父权限下拉框
    $('#parent').combotree({
        url: `${contextPath}/permission/findParent`,
        prompt: "请选择父权限",
        lines: true,
        loadFilter: function (data, parent) {
            return parseTree(data);
        },
        onLoadSuccess: function (node, data) {
            let tree = $('#parent').combotree("tree")
            tree.tree("collapseAll")
        },
    });


    function parseTree(array) {
        return array.map(v => {
            let id = v.id
            let text = v.name
            let children = v.children
            if (children instanceof Array) {
                children = parseTree(children)
            }
            return {id, text, children}
        })
    }
})