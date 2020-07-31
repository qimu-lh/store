$(function () {
    showIsDefaultAddressAid();
})
function showIsDefaultAddressAid() {
    $.ajax({
        "url": "/addresses/get_isDefault_Address",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                $("#address-aid").attr("href","checkout-review.html?aid="+json.data.aid);
            } else {
                alert(json.message);
            }
        }
    });
}