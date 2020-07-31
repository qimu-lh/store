$(function () {
    showIsDefaultAddress();
})
function showIsDefaultAddress() {
    $.ajax({
        "url": "/addresses/get_isDefault_Address",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                $("#address-aid").text(json.data.aid);
                $("#isDefault-province").val(json.data.provinceName);
                $("#isDefault-city").val(json.data.cityName);
                $("#isDefault-area").val(json.data.areaName);
                $("#isDefault-address").val(json.data.address);
                $("#isDefault-name").val(json.data.name);
                $("#isDefault-phone").val(json.data.phone);
            } else {
                alert(json.message);
            }
        }
    });
}