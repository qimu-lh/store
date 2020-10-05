$(function () {
    //注册
    $("#shop-register").click(function () {
        var file01 = $("#upload-card-pre")[0].files[0];
        var file02 = $("#upload-card-back")[0].files[0];
        var file03 = $("#upload-card-hand")[0].files[0];
        var fd = new FormData();
        fd.append("business",$("#form-reg").serialize());
        fd.append("idCardFace",file01);
        fd.append("idCardBack",file02);
        fd.append("personCard",file03);
        if ($("#businessName").val()===""||$("#IDcard").val()===""
        ||$("#province").text()===""||$("#city").text()===""
        ||$("#district").text()===""||$("#address").val()===""
        ||$("#phone").val()===""||$("#email").val()===""
        ||$("#upload-card-pre").val()===""||$("#upload-card-back").val()===""
        ||$("#upload-card-hand").val()===""){
            alert("表单填写不完整！")
        }
        $.ajax({
            "url": "/businesses/reg",
            "data": fd,
            "type": "post",
            "dataType": "json",
            "processData": false,//用于对data参数进行序列化处理 这里必须false
            "contentType": false,//必须
            "success": function (json) {
                if (json.state === 2000) {
                    alert("邮件发送成功，请登录邮箱并点击链接激活账号！");
                    // 跳转到某个页面
                } else {
                    alert(json.message);
                }
            }
        });
    })
    //点击上传照片，在前端显示
    $("#upload-card-pre").change(function () {
        var t = $(this)[0].files[0]
        var url = window.URL.createObjectURL(t)
        $("#upload-card-pre-img").attr("src",url)
    })
    $("#upload-card-back").change(function () {
        var t = $(this)[0].files[0]
        var url = window.URL.createObjectURL(t)
        $("#upload-card-back-img").attr("src",url)
    })
    $("#upload-card-hand").change(function () {
        var t = $(this)[0].files[0]
        var url = window.URL.createObjectURL(t)
        $("#upload-card-hand-img").attr("src",url)
    })
})
//显示、隐藏示例图
$("#card-pre").click(function () {
    if($('#card-pre-img').is(':hidden')){//如果当前隐藏
        $('#card-pre-img').show();//那么就显示图片
        $("#card-pre").text("隐藏示例")
        $(this).addClass( "gz1" )
    }else{//否则
        $('#card-pre-img').hide();//就隐藏图片
        $("#card-pre").text("显示示例")
        $(this).removeClass( "gz1" )
    }
})
$("#card-back").click(function () {
    if($('#card-back-img').is(':hidden')){//如果当前隐藏
        $('#card-back-img').show();//那么就显示图片
        $("#card-back").text("隐藏示例")
        $(this).addClass( "gz2" )
    }else{//否则
        $('#card-back-img').hide();//就隐藏图片
        $("#card-back").text("显示示例")
        $(this).removeClass( "gz2" )
    }
})
$("#card-hand").click(function () {
    if($('#card-hand-img').is(':hidden')){//如果当前隐藏
        $('#card-hand-img').show();//那么就显示图片
        $("#card-hand").text("隐藏示例")
        $(this).addClass( "gz3" )
    }else{//否则
        $('#card-hand-img').hide();//就隐藏图片
        $("#card-hand").text("显示示例")
        $(this).removeClass( "gz3" )
    }
})