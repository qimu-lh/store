$(function () {
    //定义一个数组
    var pageData02={ sid:1,store:1, merchants:2, applyTime:3, audit:4};
    //定义循环生成的数组
    var pageData_array02 = new Array();
    showShopList();
    function showShopList() {
        $.ajax({
            "url": "/shops/",
            "type": "get",
            "dataType": "json",
            "success": function (json) {
                var list = json.data;
                console.log("count=" + list.length);
                for (var i = 0; i < list.length; i++) {
                    console.log(list[i].sid);
                    pageData02={};
                    pageData02.sid=list[i].sid;
                    pageData02.store=list[i].shopname;
                    pageData02.merchants=list[i].storename;
                    pageData02.applyTime=list[i].applyTime;
                    pageData02.audit=(list[i].status===0)?"审核":"已审核";
                    pageData_array02[i]=pageData02;
                }
                console.log(pageData_array02)
                //调用分页jq代码
                createShopWorld(pageData_array02)
            }
        });
    }
    function createShopWorld(pageData02) {
        //分页显示
        var pages = pageData02.length;//数据长度
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
                var lab1 = document.createElement("lable");
                //为创建的标签赋属性
                var $td1 = $(td1);
                var $td2 = $(td2);
                var $td3 = $(td3);
                $(div1).attr("style", "margin-left: 100px");
                $(div2).attr("style", "margin-left: 100px");
                $(div3).attr("style", "margin-left: 100px");
                var $btn1;
                if (pageData02[i].audit==="审核"){
                    $btn1=$(btn1).attr({"type": "button", "class": "auditButton btn btn-default","onclick":"auditShop(this)"});
                }else {
                    $btn1=$(btn1).attr({"type": "button", "class": "auditButton btn btn-default","onclick":"auditShop(this)","disabled":"disabled"});
                }
                var $lab1 = $(lab1).attr({ "style": "display:none"});
                //为创建的标签赋内容
                $(th).text(pageData02[i].store);
                $(div1).text(pageData02[i].merchants);
                $(div2).text(pageData02[i].applyTime);
                $(btn1).text(pageData02[i].audit);
                $(lab1).text(pageData02[i].sid);
                //在指定标签后赋上标签
                $td1.append($(div1).attr("style", "margin-left: 100px"));
                $td2.append($(div2).attr("style", "margin-left: 100px"));
                $td3.append($(div3).attr("style", "margin-left: 100px"));
                $(div3).append($btn1);
                $(div3).append($lab1);
                $(tr).append($(th));
                $(tr).append($td1);
                $(tr).append($td2);
                $(tr).append($td3);
                //在tbody标签后赋上标签
                $("tbody").append($(tr));
            }
        }
    }
});
