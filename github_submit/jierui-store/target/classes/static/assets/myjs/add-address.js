$(function () {
    $("#btn-addnew").click(function () {
        addAddress();
    });

    showProvinceList();

    $("#province-list").change(function () {
        showCityList();

        $("#area-list").empty();
        var defaultOp = '<option value="0">----- 请选择 -----</option>';
        $("#area-list").append(defaultOp);
    });

    $("#city-list").change(function () {
        showAreaList();
    });
})

function showProvinceList() {
    var defaultOp = '<option value="0">----- 请选择 -----</option>';
    $("#province-list").append(defaultOp);
    $("#city-list").append(defaultOp);
    $("#area-list").append(defaultOp);

    $.ajax({
        "url": "/districts/",
        "data": "parent=86",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var op = '<option value="' + list[i].code + '">' + list[i].name + '</option>';
                $("#province-list").append(op);
            }
        }
    });
}

function showCityList() {
    $("#city-list").empty();
    var defaultOp = '<option value="0">----- 请选择 -----</option>';
    $("#city-list").append(defaultOp);

    var provinceCode = $("#province-list").val();
    if (provinceCode == 0) {
        return;
    }

    $.ajax({
        "url": "/districts/",
        "data": "parent=" + provinceCode,
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var op = '<option value="' + list[i].code + '">' + list[i].name + '</option>';
                $("#city-list").append(op);
            }
        }
    });
}

function showAreaList() {
    $("#area-list").empty();
    var defaultOp = '<option value="0">----- 请选择 -----</option>';
    $("#area-list").append(defaultOp);

    var cityCode = $("#city-list").val();
    if (cityCode == 0) {
        return;
    }

    $.ajax({
        "url": "/districts/",
        "data": "parent=" + cityCode,
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var op = '<option value="' + list[i].code + '">' + list[i].name + '</option>';
                $("#area-list").append(op);
            }
        }
    });
}

function addAddress() {
    $.ajax({
        "url": "/addresses/addnew",
        "data": $("#form-addnew").serialize(),
        "type": "post",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                setTimeout(function () {
                    location.href="/web/account-address.html";
                },2000)
                // 跳转到某个页面
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您的登录信息已经过期！请重新登录！");
            // location.href = "/web/login.html";
        }
    });
}