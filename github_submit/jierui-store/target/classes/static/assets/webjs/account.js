$(function () {
    //定义一个数组
    var pageData={ account:1, user:2, status:3, operation:4};
    //定义循环生成的数组
    var pageData_array = new Array();
    showList();
    function showList() {
        $.ajax({
            "url": "/admins/",
            "type": "get",
            "dataType": "json",
            "success": function (json) {
                var list = json.data;
                console.log("count=" + list.length);
                for (var i = 0; i < list.length; i++) {
                    console.log(list[i].aid);
                    pageData={};
                    pageData.account=list[i].aid;
                    pageData.user=list[i].adminname;
                    pageData.status=(list[i].status===0)?"正常":"冻结";
                    pageData.operation=[{freeze:(list[i].status===0)?"冻结":"正常",delete:"删除",modify:"修改归属"}];
                    pageData_array[i]=pageData;
                }
                //检查权限
                getAuthority();
                console.log(pageData_array)
                //调用分页jq代码
                createWorld(pageData_array)
            }
        });
    }
    function createWorld(pageData) {
    //分页显示
    var pages = pageData.length;//数据长度
    var finalPage = 4;//一页最多四行数据
    var page = Math.ceil(pages / finalPage);//页数
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
                createTable((page - 1) * 4, pages);
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
                createTable((page - 1) * 4, pages);
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
            createTable((page - 1) * 4, pages);
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
            $(th).text(pageData[i].account);
            $(div1).text(pageData[i].user);
            $(div2).text(pageData[i].status);
            $(btn1).text(pageData[i].operation[0].freeze);
            $(btn2).text(pageData[i].operation[0].delete);
            $(btn3).text(pageData[i].operation[0].modify);
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

});
//检查权限
function getAuthority() {
    $.ajax({
        "url": "/bosses/authority",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state === 2000) {
                console.log(json.data)
                if (json.data.power==="v1"){
                    $(".delete").attr({"style": "margin-left:4px", "type": "button", "class": "btn btn-default","disabled":"disabled"});
                }
            } else {
                alert(json.message);
            }
        }
    });
}
