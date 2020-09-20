$(function () {
    //昵称不能为空
    $("#reg-username").blur(function () {
        var data1=$("#reg-username").val();
        if (data1==""||data1==null){
            $("#v1").after("<label id='l1' class=\"control-label\" style='color: red'>昵称不能为空！</label>")
        }else{
            $("#l1").remove();
        }
        if ($("#l1").length>0) {$("#l1").next().remove();}
    });
    //手机号为11位
    $("#phone").blur(function () {
        var data2=$("#phone").val();
        if (data2.length<11||(isPhoneNo(data2)===false)){
            $("#v2").after("<label id='l2' class=\"control-label\" style='color: red'>手机号不符合规范！</label>")
        }else{
            $("#l2").remove();
        }
        if ($("#l2").length>0) {$("#l2").next().remove();}
    });
    //密码至少8位长
    $("#reg-newPassword").blur(function () {
        var data5=$("#reg-newPassword").val();
        if (data5.length<8){
            $("#v5").after("<label id='l5' class=\"control-label\" style='color: red'>密码至少为8位!</label>")
        }else{
            $("#l5").remove();
        }
        if ($("#l5").length>0) {$("#l5").next().remove();}
    })
    //确认密码
    $("#reg-confirmPassword").blur(function () {
        var data6=$("#reg-confirmPassword").val();
        var password=$("#reg-newPassword").val();
        if (data6==password){
            $("#l6").remove();
        }else{
            $("#v6").after("<label id='l6' class=\"control-label\" style='color: red'>前后密码不相同！</label>")
        }
        if ($("#l6").length>0) {$("#l6").next().remove();}
    });
    //注册请求的AJAX代码
    $("#btn-reg").click(function () {
        if ($("#reg-username").val()===""){
            alert("用户名不能为空！")
        }else if ($("#reg-newPassword").val()!==$("#reg-confirmPassword").val()){
            alert("前后密码不一致！")
        }else if ($("#reg-newPassword").val()===""){
            alert("密码不能为空！")
        } else if (!$("#agreement").prop('checked')===true){
            alert("请阅读并同意用户协议！")
        }else if ($("#phone").val().length!==11){
            alert("请填写合法的手机号码！")
        }else {
            $.ajax({
                "url": "/users/reg",
                "data": $("#form-reg").serialize(),
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state === 2000) {
                        alert("注册成功");
                        // 跳转到某个页面
                        location.href="/static/web/account-login.html";
                        window.event.returnValue=false;
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
    });

});

// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};

// 验证手机号
function isPhoneNo(phone) {
    var pattern = /^1[34578]\d{9}$/;
    return pattern.test(phone);
}
// 验证中文名称
function isChinaName(name) {
    var pattern = /^[\u4E00-\u9FA5]{1,6}$/;
    return pattern.test(name);
}