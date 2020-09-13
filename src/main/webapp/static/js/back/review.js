$(function () {
    // 数据表格
    $('#datagrid').datagrid({
        url: `${contextPath}/review/page`,
        columns: [[
            {field: 'content', title: '内容', width: 100, align: 'center'},
            {
                field: 'remarkDate', title: '时间', width: 100, align: 'center'
            },
            {field: 'article', title: '评论文章', width: 100, align: 'center', formatter: function (value) {
                if (value) {
                    return value.title
                }
                }},
        ]],
        fit: true,
        fitColumns: true,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        toolbar: "#tb",
        scrollbarSize: 0
    });


    // 通过按钮
    $(".commit").click(function () {
        let selectedRow = $("#datagrid").datagrid("getSelected")
        if (selectedRow) {
            let id = selectedRow.id
            let url = `${ contextPath }/review/commitReview?id=` + id
            $.get(url, function (data) {
                if (data.isSuccess) {
                    $('#datagrid').datagrid("load")
                } else {
                    $.messager.alert("温馨提示", data.message)
                }
            }, 'json')
        } else {
            $.messager.alert("温馨提示", "请选择一条数据进行授权")
        }
    })

    // 删除
    $(".rollback").click(function () {
        let selectedRow = $("#datagrid").datagrid("getSelected")
        if (selectedRow) {
            let id = selectedRow.id
            let url = `${ contextPath }/review/deleteReview?id=` + id
            $.get(url, function (data) {
                if (data.isSuccess) {
                    $('#datagrid').datagrid("load")
                } else {
                    $.messager.alert("温馨提示", data.message)
                }
            }, 'json')
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

})
