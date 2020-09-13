$(function () {
    // 数据表格
    $('#dg').datagrid({
        url: `${contextPath}/announcement/page`,
        columns: [[
            {field: 'picture', title: '图片', width: 100, align: 'center', formatter: function (value) {
                    if (value) {
                        return `<img src="${value}" style="height: 50px; border-radius: 18px; padding: 10px"></img>`
                    }
                }},
            {field: 'title', title: '名称', width: 100, align: 'center'},
            {field: 'content', title: '内容', width: 100, align: 'center'},
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
        height: 360,
        closed: true,
        buttons: [
            {
                text: "保存",
                iconCls: 'icon-save',
                handler: function () {
                    let url;
                    if ($("input[name=id]").val()) {
                        url = `${contextPath}/announcement/edit`
                    } else {
                        url = `${contextPath}/announcement/save`
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
            $(".pictureView").css("display", "flex")
            $(".pictureView img").attr("src", selectedRow.picture)
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
                    url: `${contextPath}/announcement/delete?id=` + selectedRow.id,
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

    // 图片选择事件
    $("#picture").change(function () {
        let path = this.files[0]
        let url
        if (window.createObjcectURL !== undefined) {
            url = window.createOjcectURL(path);
        } else if (window.URL !== undefined) {
            url = window.URL.createObjectURL(path);
        } else if (window.webkitURL !== undefined) {
            url = window.webkitURL.createObjectURL(path);
        }
        $(".pictureView").css("display", "flex")
        $(".pictureView img").attr("src", url)
    })
})