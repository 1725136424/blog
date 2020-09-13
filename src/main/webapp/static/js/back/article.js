$(function () {

    // 初始化ueditor
    let ue = UE.getEditor('editor');

    let searchCategory

    // 初始化数据表格
    $('#datagrid').datagrid({
        // 按时间排序获取所有文章
        url: `${contextPath}/article/page`,
        columns: [[
            {
                field: 'picture', title: '图片', width: 100, align: 'center', formatter: function (value) {
                    if (value) {
                        return `<img src="${value}" style="height: 50px; border-radius: 18px; padding: 10px"></img>`
                    }
                }
            },
            {field: 'title', title: '标题', width: 100, align: 'center'},
            {field: 'publishDate', title: '发布时间', width: 100, align: 'center'},
            {field: 'editDate', title: '修改时间', width: 100, align: 'center'},
            {
                field: 'category', title: '所属分类', width: 100, align: 'center', formatter: function (value) {
                    if (value) {
                        return value.name
                    }
                }
            },
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
        width: 300,
        height: 350,
        closed: true,
        buttons: [
            {
                text: "保存",
                iconCls: 'icon-save',
                handler: function () {
                    let url
                    if ($("input[name=id]").val()) {
                        url = `${contextPath}/article/edit`
                    } else {
                        url = `${contextPath}/article/save`
                    }
                    $("#saveForm").form("submit", {
                        url,
                        onSubmit: function (param) {
                            if ($(this).form("validate")) {
                                param.content = ue.getContent();
                                return true
                            } else {
                                return false
                            }
                        },
                        success: function (data) {
                            let res = JSON.parse(data);
                            if (res.isSuccess) {
                                ue.setContent("");
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
            $("#saveForm").form("reset");
            // 回显图片
            $(".pictureView").removeClass("show")
        }
    })

    // 关闭模态弹窗
    $(".background").click(function (event) {
        let object = event.target
        if ($(object).hasClass("background")) {
            $(this).hide("300")
        }
    })

    $(".closeArticle").click(function () {
        $(".background").trigger("click")
        $("#saveForm").form("reset");
    })

    // 文章保存按钮点击
    $(".saveArticle").click(function () {
        let content = ue.getContent();
        if (content) {
            // 关闭模态弹窗
            $(".background").hide("300", function () {
                $('#dialog').dialog("open")
            })
        } else {
            $.messager.alert("警告", "请输入文章内容")
        }
    })

    // 新增按钮
    $(".save").click(function () {
        ue.setContent("");
        $(".background").show("300")
    })

    // 编辑按钮
    $(".edit").click(function () {
        // 数据回显
        let selectedRow = $("#datagrid").datagrid("getSelected")
        if (selectedRow) {
            // 回显文章内容
            ue.setContent(selectedRow.content)
            $(".background").show("300")
            // 回显对话框内容
            $("#saveForm").form("load", selectedRow)
            // 回显图片
            $(".pictureView").addClass("show")
            $(".pictureView img").attr("src", selectedRow.picture)
        }
    })

    // 删除
    $(".delete").click(function () {
        let selectedRow = $("#datagrid").datagrid("getSelected")
        if (selectedRow) {
            $.messager.confirm("警告", "文章一旦删除不能恢复", function (isTrue) {
                if (isTrue) {
                    // 数据回显
                    let id = selectedRow.id
                    let url = `${contextPath}/article/delete?id=${id}`
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

    // 获取分类数据 同步请求ajax
    let url = `${contextPath}/category/findParent`
    $.ajax({
        url,
        dataType: "json",
        async: false,
        success: function (data) {
            appendFather($("#menu"), data)
            $("#menu").append($("<div data-options=\"iconCls:'icon-reload'\">清空</div>"))
        }
    })

    // 菜单初始化
    $("#menu").menu({
        onClick: function (item) {
            let reg = /(<span>|<\/span>)/g
            let text = item.text.replace(reg, "")
            if (text !== "清空") {
                $(".search").siblings("input[type=text]").attr("placeholder", "搜索 " + text + " 下")
                searchCategory = text
            } else {
                searchCategory = ''
                $(".search").siblings("input[type=text]").attr("placeholder", "根据名称搜索")
            }
        }
    })

    // 搜索按钮
    $(".search").menubutton({
        iconCls: 'icon-edit',
        menu: '#menu',
    });

    // 搜索
    $(".search").click(function () {
        let keywords = $("input[name=keywords]").val().trim();
        if (keywords || searchCategory) {
            $("#datagrid").datagrid("load", {
                keywords,
                searchCategory
            })
        } else {
            $("#datagrid").datagrid("load", {})
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
        $(".pictureView").addClass("show")
        $(".pictureView img").attr("src", url)
    })

    // 分类下拉菜单初始化
    $('#category').combotree({
        url: `${contextPath}/category/findParent`,
        prompt: "请选择分类",
        lines: true,
        loadFilter: function (data, parent) {
            return parseTree(data);
        },
        onLoadSuccess: function (node, data) {
            let tree = $('#category').combotree("tree")
            tree.tree("collapseAll")
        },
    });

    function appendFather($father, data) {
        data.forEach(v => {
            let $div = $(`<div><div id="inner">${v.name}</div></div>`)
            // 处理子数据
            if (v.children instanceof Array) {
                $div.find("#inner").replaceWith($(`<span>${v.name}</span>`))
                appendFather($div, v.children)
            }
            $father.append($div)
        })
    }

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