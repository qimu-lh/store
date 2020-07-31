$(function () {
    showUserList();
});

/*显示所有用户*/
function showUserList() {
    $.ajax({
        "url": "/admin/",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);

            $("#user-list").empty();

            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var html = '<tr>'
                    + '<td>#{username}</td>'
                    + '<td>#{temperature}</td>'
                    + '<td>#{humidity}</td>'
                    + '<td>#{airMode}</td>'
                    + '<td>#{airTemperature}</td>'
                    + '<td>#{airWindSpeed}</td>'
                    + '<td>#{lamp1}</td>'
                    + '<td>#{lamp2}</td>'
                    + '<td>#{lamp3}</td>'
                    + '<td>#{lamp4}</td>'
                    + '<td>#{windowMode}</td>'
                    + '<td><a onclick="deleteByUid(#{uid})" href="#" class="btn btn-xs add-del btn-danger">删除</a></td>'
                    + '</tr>';

                html = html.replace(/#{uid}/g, list[i].uid);
                html = html.replace(/#{username}/g, list[i].username);
                html = html.replace(/#{temperature}/g, list[i].temperature);
                html = html.replace(/#{humidity}/g, list[i].humidity);
                html = html.replace(/#{airMode}/g, list[i].airMode);
                html = html.replace(/#{airTemperature}/g, list[i].airTemperature);
                html = html.replace(/#{airWindSpeed}/g, list[i].airWindSpeed);
                html = html.replace(/#{lamp1}/g, list[i].lamp1);
                html = html.replace(/#{lamp2}/g, list[i].lamp2);
                html = html.replace(/#{lamp3}/g, list[i].lamp3);
                html = html.replace(/#{lamp4}/g, list[i].lamp4);
                html = html.replace(/#{windowMode}/g, list[i].windowMode);

                $("#user-list").append(html);
            }
        }
    });
}

/*根据id删除用户*/
function deleteByUid(uid) {
    if (confirm("确定删除这个用户吗？")) {
        $.ajax({
            "url": "/admin/" + uid + "/delete",
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    showUserList();
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