$(function () {

    // Bootstrap修改密码表单验证
    $('#editForm').bootstrapValidator({
        message: 'This value is not valid',
        //提供输入验证图标提示
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '密码长度必须在6到12位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '密码只能包含大写、小写、数字和下划线'
                    }
                }
            },
        }
    })
        .on('success.form.bv', function (e) {//点击提交之后
            e.preventDefault();
            let password = $("input[name=password]").val()
            let confirmPassword = $(".confirm").val()
            if (password !== confirmPassword) {
                $("input[name=password]")
                    .parent()
                    .append('<small ' +
                        'data-bv-validator="notEmpty"' +
                        'data-bv-validator-for="password" ' +
                        'class="help-block start"' +
                        'data-bv-result="INVALID">两次密码不一致</small>')
                    .addClass("has-error");
                $("input[name=password]").one("input", function () {
                    $(this).siblings(".start").remove()
                })
                return false
            }
            let $form = $(e.target);
            $.post($form.attr('action'), $form.serialize(), function (result) {
                if (result.isSuccess) {
                    window.location.href = `${ contextPath }/loginAndRegister/page?method=1`
                } else {
                    alert(result.message)
                }
            });
        });

    class LoginValidator {
        constructor(config) {
            this.parent = config.parent
            this.message = config.message
            this.errorClassification = config.errorClassification
            this.initValidator();
        }

        // 初始化验证方法
        initValidator() {
            switch (this.errorClassification) {
                case 1:
                    this.object = "code"
                    break;
                case 2:
                    this.object = "username"
                    break;
                case 3:
                    this.object = "password"
                    break;
                case 4:
                    alert(this.message)
                    break;
            }
            if (this.errorClassification !== 4) {
                this.echoError();
            }
        }

        // 回显错误
        echoError() {
            this.parent.find(`input[name='${ this.object }']`)
                .parent()
                .append('<small ' +
                    'data-bv-validator="notEmpty"' +
                    'data-bv-validator-for="'+ this.object +'" ' +
                    'class="help-block start"' +
                    'data-bv-result="INVALID">' + this.message+ '</small>')
                .addClass("has-error");
            this.removeError()
        }

        // 移出错误
        removeError() {
            this.parent.find(`input[name='${ this.object }']`).one("input", function () {
                $(this).siblings(".start").remove()
            })
        }
    }
})