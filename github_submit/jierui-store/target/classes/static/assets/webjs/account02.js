$(function () {
    //定义一个数组
    var pageData03={ account:1, user:2, status:3, operation:4};
    //定义循环生成的数组
    var pageData03_array = new Array();
    //创建管理员请求的AJAX代码
    $("#btn-create").click(function () {
        if ($("#create-username").val()===""){
            alert("账号名不能为空！")
        }else {
            $.ajax({
                "url": "/admins/create",
                "data": $("#form-create").serialize(),
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state === 2000) {
                        alert("管理员创建成功！");
                        // 跳转到某个页面
                        location.href="/static/web/accountManagement.html";
                        window.event.returnValue=false;
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
    });
    //根据aid修改归属人AJAX
    $("#modifyBelongerByAid").click(function () {
        var aid = $("#adminId").text();
        var belonger = $("#adminBelonger").val();
        $.ajax({
            "url": "/admins/"+aid+"/changeBelonger/"+belonger,
            "type": "post",
            "dataType": "json",
            "success": function (json) {
                if (json.state === 2000) {
                    alert("归属人修改成功！")
                    $("#adminId").text(json.data.aid);
                    $("#adminBelonger").val(json.data.belonger)
                    //location.href="/static/web/accountManagement.html"
                } else {
                    alert(json.message);
                }
            }
        });
    })
    $("#selectAdmin").click(function () {
        var aid=$("#selectAdminID").val();
        var adminname=$("#selectAdminname").val();
        var status=($("#selectAdminStatus").val()==="正常")?0:1;
        //精准查询
        if (aid!==""&&(adminname!=="")&&($("#selectAdminStatus").val()==="===请选择===")){
            $.ajax({
                "url": "/admins/"+aid+"/findAdminByCondition/"+adminname,
                "type": "get",
                "dataType": "json",
                "success": function (json) {
                    if (json.state === 2000) {
                        var list = json.data;
                        $("tbody").empty();
                        $("#page").empty();
                        var html='<tr>'
                            +'<th>#{aid}</th>'
                            +'<td><div style="margin-left: 100px">#{adminname}</div></td>'
                            +'<td><div style="margin-left: 100px">#{status}</div></td>'
                            +'<td><div style="margin-left: 100px"><button type="button" class="freeze btn btn-info" onclick="freezeAdmin(this)">冻结</button><button style="margin-left:4px" type="button" class="delete btn btn-danger" onclick="deleteAdmin(this)">删除</button><button style="margin-left:4px" type="button" class="modify btn btn-success" data-toggle="modal" data-target="#exampleModal02" onclick="modifyBelonger(this)">修改归属</button></div></td>'
                            +'</tr>';
                        html = html.replace(/#{aid}/g, list.aid);
                        html = html.replace(/#{adminname}/g, list.adminname);
                        html = html.replace(/#{status}/g, (list.status===0)?"正常":"冻结");

                        var html01='<div class="accountManagement-pagination row">\n' +
                            '        <div class="col-md-3"></div>\n' +
                            '        <div class="col-md-9" id="page">\n' +
                            '            <nav aria-label="...">\n' +
                            '                <ul class="pagination">\n' +
                            '                    <li class="disabled" id="pre"><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>\n' +
                            '                    <li class="active aPage" id="li01"><a href="#l1" aria-controls="l1" role="tab" data-toggle="tab">1</a></li>\n' +
                            '                    <li class="disabled" id="next"><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>\n' +
                            '                </ul>\n' +
                            '            </nav>\n' +
                            '        </div>\n' +
                            '    </div>';
                        $("tbody").append(html);
                        $("#page").append(html01);
                        //检查权限
                        getAuthority();
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
        //根据状态查询用户
        if ((aid==="")&&(adminname==="")&&($("#selectAdminStatus").val()!=="===请选择===")){
            $("tbody").empty();
            $("#page").empty();
            var html01='<div class="accountManagement-pagination row">\n' +
                '        <div class="col-md-3"></div>\n' +
                '        <div class="col-md-9" id="page">\n' +
                '            <nav aria-label="...">\n' +
                '                <ul class="pagination">\n' +
                '                    <li class="disabled" id="pre"><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>\n' +
                '                    <li class="active aPage" id="li01"><a href="#l1" aria-controls="l1" role="tab" data-toggle="tab">1</a></li>\n' +
                '                    <li id="next"><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>\n' +
                '                </ul>\n' +
                '            </nav>\n' +
                '        </div>\n' +
                '    </div>';
            $("#page").append(html01);
            showListByStatus(status);
        }
    })

    //根据状态码查询管理员
    function showListByStatus(status) {
        $.ajax({
            "url": "/admins/"+status+"/findAdminsByStatus",
            "type": "get",
            "dataType": "json",
            "success": function (json) {
                var list = json.data;
                console.log("count=" + list.length);
                for (var i = 0; i < list.length; i++) {
                    console.log(list[i].aid);
                    pageData03={};
                    pageData03.account=list[i].aid;
                    pageData03.user=list[i].adminname;
                    pageData03.status=(list[i].status===0)?"正常":"冻结";
                    pageData03.operation=[{freeze:(list[i].status===0)?"冻结":"正常",delete:"删除",modify:"修改归属"}];
                    pageData03_array[i]=pageData03;
                }
                console.log(pageData03_array)
                //检查权限
                getAuthority();
                //调用分页jq代码
                createWorld03(pageData03_array)
            }
        });
    }
    function createWorld03(pageData03) {
        //分页显示
        var pages03 = pageData03.length;//数据长度
        var finalPage = 4;//一页最多四行数据
        var page = Math.ceil(pages03 / finalPage);//页数
        console.log("页数:" + page);
        var j = 1;
        //创建第一个以后的li
        for (let i = 1; i < page; i++) {
            //在一个li后添加其他li
            j = j + 1;
            $("#next").before("<li id='li0" + j + "' class='aPage'><a href='#l" + j + "' aria-controls='l" + j + "' role='tab' data-toggle=tab'>" + j + "</a></li>")
        }

        //单个点击实现active
        $(".aPage").click(function () {
            $(this).attr("class", "aPage active");
            $(this).siblings().attr("class", "aPage");
            $("#pre").attr("class", "");
            $("#next").attr("class", "");

        });
        //第一个标签显示时，向前按钮变灰
        $("#li01").click(function () {
            $("#pre").attr("class", "disabled");
        });
        //最后一个标签显示时，向后按钮变灰
        $("#next").prev().click(function () {
            $("#next").attr("class", "disabled");
        });

        //向前的按钮
        $("#pre").click(function () {
            var lis = $(".pagination li");
            var num = 0;
            $("ul").find(".active").each(function () {
                num = $(".active").text();
                num--;
                $(lis[num]).attr("class", "aPage active");
                $(lis[num]).siblings().attr("class", "");
                if (num < 2) {
                    $("#pre").attr("class", "disabled");
                }
                //添加数据
                $("tbody").html("");//创建时首先清空一下
                var page01 = parseInt($(this).prev().text());//此处是第几页
                //创建中间页
                if (page01 < page) {
                    createTable((page01 - 1) * 4, (page01 - 1) * 4 + 4);
                }
                //创建最后一页
                if (parseInt($(this).prev().text()) === page) {
                    createTable((page - 1) * 4, pages03);
                }
            });
        });
        //向后的按钮
        $("#next").click(function () {
            var lis = $(".pagination li");
            console.log(lis);
            console.log(lis[1]);
            var num = 0;
            $("ul").find(".active").each(function () {
                num = $(".active").text();
                console.log(num);
                num++;
                $(lis[num]).attr("class", "aPage active");
                $(lis[num]).siblings().attr("class", "");
                if (num > lis.length - 3) {
                    $("#next").attr("class", "disabled");
                }

                //添加数据
                $("tbody").html("");//创建时首先清空一下
                var page01 = parseInt($(this).next().text());//此处是第几页
                //创建中间页
                if (page01 < page) {
                    createTable((page01 - 1) * 4, (page01 - 1) * 4 + 4);
                }
                //创建最后一页
                if (parseInt($(this).next().text()) === page) {
                    createTable((page - 1) * 4, pages03);
                }
            });


        });
        //首先创建第一页
        createTable(0, 4);
        /*点击单页码动态创建表格*/
        $(".aPage").on("click", function createTable02() {
            $("tbody").html("");//创建时首先清空一下
            var page01 = parseInt($(this).text());//此处是第几页
            //创建中间页
            if (page01 < page) {
                createTable((page01 - 1) * 4, (page01 - 1) * 4 + 4);
            }
            //创建最后一页
            if (parseInt($(this).text()) === page) {
                createTable((page - 1) * 4, pages03);
            }
        });

        //创建一页表格
        function createTable(startI, finalPage) {
            for (var i = startI; i < finalPage; i++) {
                var tr = document.createElement('tr');
                //创建标签
                var th = document.createElement("th");
                var td1 = document.createElement("td");
                var td2 = document.createElement("td");
                var td3 = document.createElement("td");
                var div1 = document.createElement("div");
                var div2 = document.createElement("div");
                var div3 = document.createElement("div");
                var btn1 = document.createElement("button");
                var btn2 = document.createElement("button");
                var btn3 = document.createElement("button");
                //为创建的标签赋属性
                var $td1 = $(td1);
                var $td2 = $(td2);
                var $td3 = $(td3);
                $(div1).attr("style", "margin-left: 100px");
                $(div2).attr("style", "margin-left: 100px");
                $(div3).attr("style", "margin-left: 100px");
                var $btn1 = $(btn1).attr({"type": "button", "class": "freeze btn btn-info","onclick":"freezeAdmin(this)"});
                var $btn2 = $(btn2).attr({"style": "margin-left:4px", "type": "button", "class": "delete btn btn-danger","onclick":"deleteAdmin(this)"});
                var $btn3 = $(btn3).attr({
                    "style": "margin-left:4px",
                    "type": "button",
                    "class": "modify btn btn-success",
                    "data-toggle": "modal",
                    "data-target": "#exampleModal02",
                    "onclick":"modifyBelonger(this)"
                });
                //为创建的标签赋内容
                $(th).text(pageData03[i].account);
                $(div1).text(pageData03[i].user);
                $(div2).text(pageData03[i].status);
                $(btn1).text(pageData03[i].operation[0].freeze);
                $(btn2).text(pageData03[i].operation[0].delete);
                $(btn3).text(pageData03[i].operation[0].modify);
                //在指定标签后赋上标签
                $td1.append($(div1).attr("style", "margin-left: 100px"));
                $td2.append($(div2).attr("style", "margin-left: 100px"));
                $td3.append($(div3).attr("style", "margin-left: 100px"));
                $(div3).append($btn1);
                $(div3).append($btn2);
                $(div3).append($btn3);
                $(tr).append($(th));
                $(tr).append($td1);
                $(tr).append($td2);
                $(tr).append($td3);
                //在tbody标签后赋上标签
                $("tbody").append($(tr));
                //检查权限
                getAuthority();
            }
        }
    }

})
//冻结AJAX
function freezeAdmin(obj) {
    var aid = $(obj).parent().parent().prev().prev().prev().text();
    var status = ($(obj).parent().parent().prev().text() === "正常") ? 1 : 0;
    console.log("cid:"+aid+",status:"+status);
    $.ajax({
        "url": "/admins/"+aid+"/changeStatus/"+status,
        "type": "post",
        "dataType": "json",
        "success": function (json) {
            if (json.state === 2000) {
                alert("状态修改成功！")
                var statusCh=json.data.status===0?"正常":"冻结";
                var unStatusCh=json.data.status===1?"正常":"冻结";
                $(obj).parent().parent().prev().find('div').text(statusCh);
                $(obj).parent().parent().find('.freeze').text(unStatusCh);
                //location.href="/static/web/accountManagement.html"
            } else {
                alert(json.message);
            }
        }
    });
}
//删除管理员AJAX
function deleteAdmin(obj) {
    var aid = $(obj).parent().parent().prev().prev().prev().text();
    if (confirm("您确定要删除此管理员吗？")){
        $.ajax({
            "url": "/admins/" + aid + "/delete_admin",
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state === 2000) {
                    alert("删除成功！")
                    $(obj).parent().parent().parent().remove();
                    //location.href="/static/web/accountManagement.html";
                } else {
                    alert(json.message);
                    location.href="/static/web/accountManagement.html";
                }
            },
            "error": function () {
                alert("您的登录信息已过期，请重新登录！");
            }
        });
    }
}
//修改归属人按钮
function modifyBelonger(obj) {
    var aid = $(obj).parent().parent().prev().prev().prev().text();
    $.ajax({
        "url": "/admins/" + aid + "/findAdmin",
        "type": "GET",
        "dataType": "json",
        "success": function (json) {
            if (json.state === 2000) {
                $("#adminId").text(json.data.aid);
                $("#adminBelonger").val(json.data.belonger)
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}
