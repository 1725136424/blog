$(function () {

    // 初始化数据表格
    $('#datagrid').datagrid({
        url: `${contextPath}/role/page`,
        columns: [[
            {field: 'name', title: '名称', width: 100, align: 'center'},
            {field: 'number', title: '编号', width: 100, align: 'center'},
        ]],
        fit: true,
        fitColumns: true,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize: 0,
        toolbar: "#tb"
    })

    // 对话框
    $('#dialog').dialog({
        width: 450,
        height: 450,
        closed: true,
        buttons: [
            {
                text: "保存",
                iconCls: 'icon-save',
                handler: function () {
                    let url
                    if ($("input[name=id]").val()) {
                        url = `${contextPath}/role/edit`
                    } else {
                        url = `${contextPath}/role/save`
                    }
                    $("#saveForm").form("submit", {
                        url,
                        onSubmit: function (param) {
                            // 获取选中的权限
                            let selectedRows = $(".selectedPermission").datagrid("getRows")
                            selectedRows.forEach((v, i) => {
                                param[`permissions[${i}].id`] = v.id
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
        },
        onOpen: function () {
            let isLoad = $(".dialog_bottom").get(0).dataset.isload
            if (isLoad === 'false') {
                // 加载所有权限数据
                $(".allPermission").treegrid({
                    url: `${contextPath}/permission/findParent`,
                    idField: 'id',
                    treeField: 'name',
                    columns: [[
                        {field: 'name', title: '所有权限', width: 100, align: 'left'},
                    ]],
                    singleSelect: true,
                    fit: true,
                    fitColumns: true,
                    panelHeight: "auto",
                    onSelect: function (rowDate) {
                        let isExist = false;
                        let allRows = $(".selectedPermission").datagrid("getRows");
                        $.each(allRows, function (index, value) {
                            if (value.id === rowDate.id) {
                                isExist = true;
                                return false;
                            }
                        })
                        if (!isExist) {
                            // 添加至选择权限
                            $(".selectedPermission").datagrid("appendRow", rowDate)
                        }
                    },
                    onLoadSuccess: function () {
                        $(".dialog_bottom").get(0).dataset.isload = 'true';
                        $('.allPermission').treegrid("collapseAll")
                    }
                })
            }
        }
    })

    // 已选权限
    $(".selectedPermission").datagrid({
        columns: [[
            {field: 'name', title: '已选权限', width: 100, align: 'center'},
        ]],
        singleSelect: true,
        fit: true,
        fitColumns: true,
        panelHeight: "auto",
        onSelect: function (rowIndex, rowDate) {
            // 取消选中当前行
            $(".selectedPermission").datagrid("deleteRow", rowIndex)
        }
    })

    // 新增按钮
    $(".save").click(function () {
        $("#dialog").dialog("open").dialog("setTitle", "新增")
    })

    // 编辑按钮
    $(".edit").click(function () {
        // 数据回显
        let selectedRow = $("#datagrid").datagrid("getSelected")
        if (selectedRow) {
            // 回显表单
            $("#saveForm").form("load", selectedRow)
            // 回显数据表格 发送同步请求
            let id = selectedRow.id
            let url = `${contextPath}/permission/findByRid?rid=${id}`;
            $.ajax({
                url,
                async: false,
                success: function (data) {
                    // 回显选择权限
                    if (data) {
                        $(".selectedPermission").datagrid("loadData", data)
                    } else {
                        $(".selectedPermission").datagrid("loadData", [])
                    }
                }
            })
            $("#dialog").dialog("open").dialog("setTitle", "编辑")
        } else {
            $.messager.alert("温馨提示", "请选择一条数据进行编辑")
        }
    })

    // 删除
    $(".delete").click(function () {
        // 数据回显
        let selectedRow = $("#datagrid").datagrid("getSelected")
        if (selectedRow) {
            let id = selectedRow.id
            let url = `${contextPath}/role/delete?id=${id}`
            $.ajax({
                url,
                dataType: "json",
                success: function (data) {
                    if (data.isSuccess) {
                        $("#dialog").dialog("close")
                        $("#datagrid").datagrid("reload")
                    } else {
                        $.messager.alert("温馨提示", data.message)
                    }
                }
            })
        } else {
            $.messager.alert("温馨提示", "请选择一条数据进行删除")
        }
    })

    // 刷新
    $(".reload").click(function () {
        $("#datagrid").datagrid("reload")
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
})