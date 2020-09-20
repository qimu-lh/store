$(function () {
    showAddressList();
})

function showAddressList() {
    $.ajax({
        "url": "/addresses/",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);

            $("#address-list").empty();

            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var html = '<tr>'
                    + '<td>#{name}</td>'
                    + '<td>#{address}</td>'
                    + '<td>#{phone}</td>'
                    + '<td><a onclick="deleteByAid(#{aid})" class="btn btn-danger" href="#">删除</a></td>'
                    + '<td><a onclick="setDefault(#{aid})" class="btn btn-outline-info add-def" href="#">设为默认</a></td>'
                    + '</tr>';

                html = html.replace(/#{aid}/g, list[i].aid);
                html = html.replace(/#{name}/g, list[i].name);
                html = html.replace(/#{address}/g, list[i].provinceName + list[i].cityName + list[i].areaName + list[i].address);
                html = html.replace(/#{phone}/g, list[i].phone);

                $("#address-list").append(html);
            }

            $(".add-def:eq(0)").hide();
        }
    });
}

function deleteByAid(aid) {
    if (confirm("您确定要删除此收货地址吗？")){
        $.ajax({
            "url": "/addresses/" + aid + "/delete",
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    showAddressList();
                } else {
                    alert(json.message);
                }
            },
            "error": function () {
                alert("您的登录信息已过期，请重新登录！");
            }
        });
    }
}

function setDefault(aid) {
    $.ajax({
        "url": "/addresses/" + aid + "/set_default",
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                showAddressList();
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}
