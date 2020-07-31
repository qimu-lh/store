$(function () {
    $(document).ready(function () {
        showUserInfo();
        showHumData()
    });

    function showUserInfo() {
        $.ajax({
            "url": "/users/get_by_uid",
            "type": "get",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    $("#client-uid").text(json.data.uid);
                    $("#client-username").text(json.data.username);
                } else {
                    alert(json.message);
                }
            }
        });
    }

    function showHumData() {
        $.ajax({
            "url": "/dashboard/",
            "type": "get",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    var list = json.data;
                    console.log("list'length=" + list.length);
                    if (list.length == 1) {
                        var ctx = document.getElementById("myChart");
                        var myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: [list[0].stringModifiedTime],
                                datasets: [{
                                    data: [list[0].humidity],
                                    lineTension: 0,
                                    backgroundColor: 'transparent',
                                    borderColor: '#007bff',
                                    borderWidth: 4,
                                    pointBackgroundColor: '#007bff'
                                }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: false
                                        }
                                    }]
                                },
                                legend: {
                                    display: false,
                                }
                            }
                        });
                    }
                    if (list.length == 2) {
                        var ctx = document.getElementById("myChart");
                        var myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: [list[1].stringModifiedTime, list[0].stringModifiedTime],
                                datasets: [{
                                    data: [list[1].humidity, list[0].humidity],
                                    lineTension: 0,
                                    backgroundColor: 'transparent',
                                    borderColor: '#007bff',
                                    borderWidth: 4,
                                    pointBackgroundColor: '#007bff'
                                }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: false
                                        }
                                    }]
                                },
                                legend: {
                                    display: false,
                                }
                            }
                        });
                    }
                    if (list.length == 3) {
                        var ctx = document.getElementById("myChart");
                        var myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: [list[2].stringModifiedTime, list[1].stringModifiedTime, list[0].stringModifiedTime],
                                datasets: [{
                                    data: [list[2].humidity, list[1].humidity, list[0].humidity],
                                    lineTension: 0,
                                    backgroundColor: 'transparent',
                                    borderColor: '#007bff',
                                    borderWidth: 4,
                                    pointBackgroundColor: '#007bff'
                                }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: false
                                        }
                                    }]
                                },
                                legend: {
                                    display: false,
                                }
                            }
                        });
                    }
                    if (list.length == 4) {
                        var ctx = document.getElementById("myChart");
                        var myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: [list[3].stringModifiedTime, list[2].stringModifiedTime, list[1].stringModifiedTime, list[0].stringModifiedTime],
                                datasets: [{
                                    data: [list[3].humidity, list[2].humidity, list[1].humidity, list[0].humidity],
                                    lineTension: 0,
                                    backgroundColor: 'transparent',
                                    borderColor: '#007bff',
                                    borderWidth: 4,
                                    pointBackgroundColor: '#007bff'
                                }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: false
                                        }
                                    }]
                                },
                                legend: {
                                    display: false,
                                }
                            }
                        });
                    }
                    if (list.length == 5) {
                        var ctx = document.getElementById("myChart");
                        var myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: [list[4].stringModifiedTime, list[3].stringModifiedTime, list[2].stringModifiedTime, list[1].stringModifiedTime, list[0].stringModifiedTime],
                                datasets: [{
                                    data: [list[4].humidity, list[3].humidity, list[2].humidity, list[1].humidity, list[0].humidity],
                                    lineTension: 0,
                                    backgroundColor: 'transparent',
                                    borderColor: '#007bff',
                                    borderWidth: 4,
                                    pointBackgroundColor: '#007bff'
                                }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: false
                                        }
                                    }]
                                },
                                legend: {
                                    display: false,
                                }
                            }
                        });
                    }
                    if (list.length == 6) {
                        var ctx = document.getElementById("myChart");
                        var myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: [list[5].stringModifiedTime, list[4].stringModifiedTime, list[3].stringModifiedTime, list[2].stringModifiedTime, list[1].stringModifiedTime, list[0].stringModifiedTime],
                                datasets: [{
                                    data: [list[5].humidity, list[4].humidity, list[3].humidity, list[2].humidity, list[1].humidity, list[0].humidity],
                                    lineTension: 0,
                                    backgroundColor: 'transparent',
                                    borderColor: '#007bff',
                                    borderWidth: 4,
                                    pointBackgroundColor: '#007bff'
                                }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: false
                                        }
                                    }]
                                },
                                legend: {
                                    display: false,
                                }
                            }
                        });
                    }
                    if (list.length == 7) {
                        var ctx = document.getElementById("myChart");
                        var myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: [list[6].stringModifiedTime, list[5].stringModifiedTime, list[4].stringModifiedTime, list[3].stringModifiedTime, list[2].stringModifiedTime, list[1].stringModifiedTime, list[0].stringModifiedTime],
                                datasets: [{
                                    data: [list[6].humidity, list[5].humidity, list[4].humidity, list[3].humidity, list[2].humidity, list[1].humidity, list[0].humidity],
                                    lineTension: 0,
                                    backgroundColor: 'transparent',
                                    borderColor: '#007bff',
                                    borderWidth: 4,
                                    pointBackgroundColor: '#007bff'
                                }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: false
                                        }
                                    }]
                                },
                                legend: {
                                    display: false,
                                }
                            }
                        });
                    }
                } else {
                    alert(json.message);
                }
            }
        });
    }

})

