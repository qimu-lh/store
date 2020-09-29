$(function () {
    /*默认加载一个界面*/
    //TODO

    /*动态创建左侧单元*/
    var ul=document.createElement('ul');
    for (var i=0;i<data.length;i++){
        //创建标签
        var li = document.createElement("li");
        var icon = document.createElement("span");
        var text = document.createElement("span");
        //为创建的标签赋属性和内容
        var $li=$(li);
        $(icon).attr("class",data[i].icon);
        $(text).text(data[i].name);
        //在指定标签后赋上标签
        $li.append($(icon).attr("class",data[i].icon));
        $li.append($(text).text(data[i].name));
        $(ul).append($li);
    }
    //在指定标签后赋上标签
    $(".leftArea").append($(ul).attr({"class":"leftUl","style":"height: 428px;"}));

    /*根据左侧点击，动态创建右侧单元*/
    var lis = document.querySelectorAll(".leftUl>li");
    for (let i = 0; i < lis.length; i++) {
        lis[i].onclick = function() {
            var arr = data[i].items;
            //添加样式
            $(lis[i]).attr({"style":"color:white;border-left: 2px solid dodgerblue;background-color:#313944"});
            $(lis[i]).siblings().attr({"style":"border-left: 2px solid #2C343F;"});
            insert(arr);
            /*根据右侧点击，动态创建右侧界面*/
            addIframe(arr);
        }
    }

    //判断权限，是否显示账号管理
    $('li').each(function () {
        var res=$(this).text();
        console.log(res)
        if (res==="账号管理"){
            $(this).click(function () {
                getAccountManageAuthority();
            })
        }
    })
});
//插入右侧ul li
function insert(arr) {
    $(".rightArea").html("");
    var ul = document.createElement("ul");
    for (var i = 0; i < arr.length; i++) {
        var li = document.createElement("li");
        $(ul).append($(li).text(arr[i].name));
    }
    $(".rightArea").append($(ul).attr({"class":"rightUl","style":"height: 30px;line-height:30px"}));
}
//插入右侧iframe
function addIframe(data) {
    var lis2 = document.querySelectorAll(".rightUl>li");
    for (let i = 0; i < lis2.length; i++) {
        lis2[i].onclick = function() {
            //添加样式
            $(lis2[i]).attr({"style":"border-left: 2px solid dodgerblue;background-color:#36424E"});
            $(lis2[i]).siblings().attr({"style":"border-left: 2px solid #2C343F;"});

            //添加iframe的src值
            $("#if01").attr({"src":data[i].index});
        }
    }
}
//数据源
var data = [
    {name:"账号管理",
        icon:"glyphicon glyphicon-calendar",
        items:[
            {name:"修改密码",index:"changePassword.html"},
            {name:"管理账号",index:"accountManagement.html"}],
    },
    {name:"商家管理",
        icon:"glyphicon glyphicon-th",
        items:[
            {name:"商家信息查询",index:"../../qwe.html"},
            {name:"商家店铺查询",index:"../../qwe1.html"},
            {name:"商家考核查询",index:"../../qwe2.html"},
            {name:"商家投诉查询",index:"../../qwe3.html"}],
    },
    {name:"店铺管理",
        icon:"glyphicon glyphicon-gift",
        items:[
            {name:"店铺查询",index:"../../qwe.html"},
            {name:"入驻申请",index:"inApplication.html"},
            {name:"推广申请",index:"../../qwe2.html"},
            {name:"广告管理",index:"../../qwe3.html"},
            {name:"发布推广管理",index:"../../qwe3.html"},
            {name:"发布广告管理",index:"../../qwe3.html"}],
    },
    {name:"用户管理",
        icon:"glyphicon glyphicon-user",
        items:[
            {name:"用户信息查询",index:"../../qwe.html"},
            {name:"用户订单查询",index:"../../qwe.html"},
            {name:"用户申诉",index:"../../qwe.html"},
            {name:"用户建议",index:"../../qwe.html"},
            {name:"用户消费统计",index:"../../qwe1.html"}],
    },
    {name:"投诉管理",
        icon:"glyphicon glyphicon-bell",
        items:[
            {name:"商家投诉",index:"../../qwe.html"},
            {name:"用户投诉",index:"../../qwe3.html"}],
    }
];
//根据权限判断是否显示账号管理
function getAccountManageAuthority() {
    $.ajax({
        "url": "/bosses/authority",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state === 2000) {
                if (json.data.power==="v2"){
                    $('li').each(function () {
                        var res=$(this).text();
                        console.log(res)
                        if (res==="管理账号"){
                            $(this).attr({"style":"display:none"})
                        }
                        if (res==="修改密码"){
                            $(this).click(function () {
                                $(this).next().attr({"style":"display:none"})
                            })
                        }
                    })
                }
            } else {
                alert(json.message);
            }
        }
    });
}