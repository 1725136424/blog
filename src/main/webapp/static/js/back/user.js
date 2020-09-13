$(function () {
    // 数据表格
    $('#datagrid').datagrid({
        url: `${contextPath}/user/page`,
        columns: [[
            {field: 'name', title: '用户名', width: 100, align: 'center'},
            {field: 'sex', title: '性别', width: 100, align: 'center', formatter: function (value) {
                if (value) {
                    return "男"
                } else {
                    return "女"
                }
                }},
            {field: 'registerDate', title: '注册时间', width: 100, align: 'center'},
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
        width: 300,
        height: 250,
        closed: true,
        buttons: [
            {
                text: "保存",
                iconCls: 'icon-save',
                handler: function () {
                    let url = `${contextPath}/user/auth`
                    $("#saveForm").form("submit", {
                        url,
                        onSubmit: function(param) {
                            let roles = $("#role").combobox("getValues")
                            roles.forEach((v, i) => {
                                param[`roles[${i}].id`] = v
                            })
                        },
                        success: function (data) {
                            let res = JSON.parse(data);
                            if (res.isSuccess) {
                                $("#dialog").dialog("close")
                                $("#datagrid").datagrid("reload")
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

    // 授权按钮
    $(".auth").click(function () {
        let selectedRow = $("#datagrid").datagrid("getSelected")
        if (selectedRow) {
            // 修改角色数据
            let roles = selectedRow.roles;
            if (roles) {
                let rolesMap = roles.map(v => {
                    return v.id;
                })
                $("#role").combobox("setValues", rolesMap)
            }
            $("#saveForm").form("load", selectedRow)
            $("#dialog").dialog("open").dialog("setTitle", "授权")
        } else {
            $.messager.alert("温馨提示", "请选择一条数据进行授权")
        }
    })

    // 刷新按钮
    $(".reload").click(function () {
        $("#datagrid").datagrid("reload", {})
    })

    // 搜索
    $(".search").click(function () {
        let keywords = $("input[name=keywords]").val().trim();
        if (keywords) {
            $("#datagrid").datagrid("load", {
                keywords
            })
        } else {
            $("#datagrid").datagrid("load", {})
        }

    })

    // 角色多选框
    $("#role").combobox({
        valueField: 'id',
        textField: 'name',
        panelHeight: "auto",
        prompt: "请选择赋予的角色",
        required: true,
        editable: false,
        multiple: true,
        url: `${contextPath}/role/list`
    })

})