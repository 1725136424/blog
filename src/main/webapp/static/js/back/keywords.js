$(function () {
    // 数据表格
    $('#dg').datagrid({
        url: `${contextPath}/keywords/page`,
        columns: [[
            {field: 'name', title: '名称', width: 100, align: 'center'},
        ]],
        fit: true,
        fitColumns: true,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        toolbar: "#tb",
        scrollbarSize: 0
    });

    // 对话框
    $('#dialog').dialog({
        title: 'My Dialog',
        width: 300,
        height: 250,
        closed: true,
        buttons: [
            {
                text: "保存",
                iconCls: 'icon-save',
                handler: function () {
                    let url;
                    if ($("input[name=id]").val()) {
                        url = `${contextPath}/keywords/edit`
                    } else {
                        url = `${contextPath}/keywords/save`
                    }
                    $("#saveForm").form("submit", {
                        url,
                        success: function (data) {
                            let res = JSON.parse(data);
                            if (res.isSuccess) {
                                $("#dialog").dialog("close")
                                $("#dg").datagrid("reload")
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
        let selectedRow = $("#dg").datagrid("getSelected")
        if (selectedRow) {
            // 回显图片
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
                    url: `${contextPath}/keywords/delete?id=` + selectedRow.id,
                    method: "get",
                    dataType: "json",
                    success: function (data) {
                        if (data.isSuccess) {
                            $("#dg").datagrid("reload")
                        } else {
                            $.messager.alert("温馨提醒", )
                        }
                    }
                })
            }
        })
    })

    // 刷新按钮
    $(".reload").click(function () {
        $("#dg").datagrid("reload", {})
    })

    // 搜索
    $(".search").click(function () {
        let keywords = $("input[name=keywords]").val().trim();
        if (keywords) {
            $("#dg").datagrid("load", {
                keywords
            })
        } else {
            $("#dg").datagrid("load", {})
        }

    })

})