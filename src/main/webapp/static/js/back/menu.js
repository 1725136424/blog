$(function () {
    // 数据表格
    $('#dg').treegrid({
        url: `${contextPath}/menu/page`,
        idField: 'id',
        treeField: 'text',
        columns: [[
            {field: 'text', title: '名称', width: 100, align: 'left'},
            {
                field: 'level', title: '权限级别', width: 100, align: 'center', formatter: function (value, row) {
                    if (row) {
                        try {
                            let roles = row.permission.roles
                            let str = ""
                            roles.forEach((v, index) => {
                                if (index !== roles.length - 1) {
                                    str += v.name + ","
                                } else {
                                    str += v.name
                                }
                            })
                            return str;
                        } catch (e) {
                            console.log(e);
                        }
                    }
                }
            },
            {field: 'url', title: '请求路径', width: 100, align: 'center'},
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
            $('#dg').treegrid("collapseAll")
        }
    });

    // 对话框
    $('#dialog').dialog({
        title: 'My Dialog',
        width: 300,
        height: 300,
        closed: true,
        buttons: [
            {
                text: "保存",
                iconCls: 'icon-save',
                handler: function () {
                    let url;
                    if ($("input[name=id]").val()) {
                        url = `${contextPath}/menu/edit`
                    } else {
                        url = `${contextPath}/menu/save`
                    }
                    $("#saveForm").form("submit", {
                        url,
                        success: function (data) {
                            let res = JSON.parse(data);
                            if (res.isSuccess) {
                                $("#dialog").dialog("close")
                                $("#dg").treegrid("reload")
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
        let selectedRow = $("#dg").treegrid("getSelected")
        if (selectedRow) {
            $("#saveForm").form("load", selectedRow)
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
                let selectedRow = $("#dg").datagrid("getSelected")
                $.ajax({
                    url: `${contextPath}/menu/delete?id=${selectedRow.id}`,
                    method: "get",
                    dataType: "json",
                    success: function (data) {
                        if (data.isSuccess) {
                            $("#dg").treegrid("reload")
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
        $("#dg").treegrid("reload", {})
    })

    // 搜索
    $(".search").click(function () {
        let keywords = $("input[name=keywords]").val().trim();
        if (keywords) {
            $("#dg").treegrid("load", {
                keywords
            })
        } else {
            $("#dg").treegrid("load", {})
        }

    })

    // 父菜单下拉框
    $('#parent').combotree({
        url: `${contextPath}/menu/findParent`,
        lines: true,
        prompt: "请选择父菜单",
        onLoadSuccess: function (node, data) {
            let tree = $('#parent').combotree("tree")
            tree.tree("collapseAll")
        }
    });

    // 权限下拉框
    $("#permission").combotree({
        url: `${contextPath}/permission/findParent`,
        prompt: "请选择父权限",
        lines: true,
        loadFilter: function (data, parent) {
            return parseTree(data);
        },
        onLoadSuccess: function (node, data) {
            let tree = $('#permission').combotree("tree")
            tree.tree("collapseAll")
        },
    })

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