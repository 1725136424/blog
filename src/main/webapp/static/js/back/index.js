$(function () {

    // 初始化tab
    $("#tab").tabs({
        fit: true
    })

    // 加载树形数据
    $("#tree").tree({
        url: `${ contextPath }/menu/findParent`,
        lines: true,
        animate: true,
        cascadeCheck: false,
        onSelect: function (node) {
            if (!node.url) return
            let title = node.text;
            let tabIsExist = $("#tab").tabs("exists", title)
            if (!tabIsExist) {
                $("#tab").tabs("add", {
                    title: title,
                    select: true,
                    closable: true,
                    content: `<iframe src="${ node.url }" frameborder="0" width="100%" height="100%"></iframe>`
                })
            } else {
                $("#tab").tabs("select", title)
            }
        },
        onLoadSuccess: function (node, data) {
            if (data.length > 0) {
                let node = $('#tree').tree('find', data[0].children[0].id);
                $('#tree').tree('select', node.target);
                /*openFirst(data)*/
            }
        }
    })

    function openFirst(data) {
        if (data instanceof Array) {
            data.forEach((v, i) => {
                if (i !== 0) {
                    let close = $('#tree').tree('find', v.id);
                    $("#tree").tree("collapse", close.target)
                    openFirst(data.children)
                }
            })

        }
    }
})