const ROOT_PATH = '/chart';

function confirmedTopN(id, n = 7) {
    const chartDom = document.getElementById(id);
    let myChart = echarts.init(chartDom);
    let option;

    option = {
        angleAxis: {
            type: 'category', data: ['上海市', '江苏省', '湖北省', '四川省']
        }, radiusAxis: {}, polar: {}, series: [{
            type: 'bar',
            data: [12121, 541, 125, 1654, 455],
            coordinateSystem: 'polar',
            name: '确诊人数',
            stack: 'a',
            emphasis: {
                focus: 'series'
            }
        }, {
            type: 'bar',
            data: [12121, 541, 125, 1654, 455],
            coordinateSystem: 'polar',
            name: '治愈人数',
            stack: 'a',
            emphasis: {
                focus: 'series'
            }
        }, {
            type: 'bar',
            data: [12121, 541, 125, 1654, 455],
            coordinateSystem: 'polar',
            name: '死亡人数',
            stack: 'a',
            emphasis: {
                focus: 'series'
            }
        }], legend: {
            bottom: 0, textStyle: {
                color: 'wheat'
            }, show: true, data: ['确诊人数', '治愈人数', '死亡人数']
        }
    };

    fetch(ROOT_PATH + '/provinceTopN?n=' + n).then(r => r.json()).then(json => {
        let name = new Array(json.length);
        let province_confirmedCount = new Array(json.length);
        let province_curedCount = new Array(json.length);
        let province_deadCount = new Array(json.length);
        for (let i = 0; i < json.length; i++) {
            name[i] = json[i].name;
            province_confirmedCount[(i + 3) % json.length] = json[i].province_confirmedCount;
            province_curedCount[(i + 3) % json.length] = json[i].province_curedCount;
            province_deadCount[(i + 3) % json.length] = json[i].province_deadCount;
        }
        option.angleAxis.data = name
        option.series[0].data = province_confirmedCount
        option.series[1].data = province_curedCount
        option.series[2].data = province_deadCount


        myChart.setOption(option)

    })
}

let masterChart;

let CNOption = {
    title: {
        text: 'COVID-19 Realtime Statistics',
        subtext: 'Data from www.dxy.cn',
        sublink: 'https://www.dxy.cn',
        left: 'center',
        top: 'top'
    }, tooltip: {
        trigger: 'item', showDelay: 0, transitionDuration: 0.2
    }, visualMap: {
        left: 'right', min: 0, max: 38000000, inRange: {
            color: ['#4575b4', '#fdae61', '#f46d43', '#d73027', '#a50026']
        }, text: ['High', 'Low'], calculable: true
    }, toolbox: {
        show: true, //orient: 'vertical',
        left: 'left', top: 'top', feature: {
            dataView: {readOnly: false}, restore: {}, saveAsImage: {}
        }
    }, series: [{
        name: '最新已确诊人数', type: 'map', map: 'CN', nameProperty: 'name', projection: {
            project: (point) => [point[0] / 180 * Math.PI, -Math.log(Math.tan((Math.PI / 2 + point[1] / 180 * Math.PI) / 2))],
            unproject: (point) => [point[0] * 180 / Math.PI, 2 * 180 / Math.PI * Math.atan(Math.exp(point[1])) - 90]
        }, emphasis: {
            label: {
                show: true
            }
        }, data: []
    }]
};

function mainChart(id, adcode = 100000) {
    let map;
    const chartDom = document.getElementById(id);
    const myChart = echarts.init(chartDom, 'dark');
    masterChart = myChart;

    myChart.showLoading();
    $.when($.get('/data/' + adcode + '_full.json'), $.getScript('/js/d3-array.js'), $.getScript('/js/d3-geo.js')).done(function (res) {
        const fullCountry = res[0];
        map = res[0];
        myChart.hideLoading();
        echarts.registerMap('CN', fullCountry);
        fetch(ROOT_PATH + '/totalConfirmed').then((r) => r.json()).then(json => {
            let maxValue = 0;
            let minValue = Number.MAX_VALUE;

            out:
                for (const mapElement of map.features) {
                    let name = mapElement.properties.name;
                    for (const n of json) {
                        if (n.name === name) continue out;
                    }
                    json.push({})
                    json[json.length - 1].name = name;
                    json[json.length - 1].value = 0;
                }

            for (let i = 0; i < json.length; i++) {
                maxValue = Math.max(json[i].value, maxValue)
                minValue = Math.min(json[i].value, minValue)
            }
            CNOption.series[0].data = json
            if (minValue > maxValue) minValue = maxValue;
            CNOption.visualMap.max = maxValue
            CNOption.visualMap.min = minValue
            masterChart.setOption(CNOption)
        })
    });

    /* onclickEvent */
    myChart.on('click', function (p) {
        console.log(p);
        /**
         * 国家数据
         * */
        if (!p.data.adcode) {
            masterChart.setOption(CNOption);
        }
        /**
         * 省份数据
         * */ else {
            let option = {
                title: {
                    text: 'COVID-19 ' + p.data.name + '实时统计',
                    subtext: 'Data from www.dxy.cn',
                    sublink: 'https://www.dxy.cn',
                    left: 'center',
                    top: 'top'
                }, tooltip: {
                    trigger: 'item', showDelay: 0, transitionDuration: 0.2
                }, visualMap: {
                    left: 'right', min: 0, max: 38000000, inRange: {
                        color: ['#4575b4', '#fdae61', '#f46d43', '#d73027', '#a50026']
                    }, text: ['High', 'Low'], calculable: true
                }, toolbox: {
                    show: true, //orient: 'vertical',
                    left: 'left', top: 'top', feature: {
                        dataView: {readOnly: false}, restore: {}, saveAsImage: {}
                    }
                }, series: [{
                    name: '最新已确诊人数',
                    type: 'map',
                    map: p.data.name,
                    nameProperty: 'name',
                    projection: CNOption.series[0].projection,
                    data: []
                }]
            };
            $.when($.getJSON('/data/' + p.data.adcode + '_full.json')).done(function (res) {
                const map = res;
                // const projection = d3.geoAlbers()
                myChart.hideLoading();
                echarts.registerMap(p.data.name, map);
                fetch(ROOT_PATH + '/cityConfirmed?adcode=' + p.data.adcode).then((r) => r.json()).then(json => {
                    let maxValue = 0;
                    let minValue = Number.MAX_VALUE;
                    let map = echarts.getMap(p.data.name).geoJSON;
                    out:
                        for (const mapElement of map.features) {
                            let name = mapElement.properties.name;
                            for (const n of json) {
                                if (name.startsWith(n.name)) {
                                    n.name = name;
                                    continue out;
                                }
                                if (n.name === name) continue out;
                            }
                            json.push({})
                            json[json.length - 1].name = name;
                            json[json.length - 1].value = 0;
                        }

                    for (let i = 0; i < json.length; i++) {
                        maxValue = Math.max(json[i].value, maxValue)
                        minValue = Math.min(json[i].value, minValue)
                        json[i].adcode = 0;
                    }
                    option.series[0].data = json
                    if (minValue > maxValue) minValue = maxValue;
                    option.visualMap.max = maxValue
                    option.visualMap.min = minValue;

                    masterChart.setOption(option)

                })
            });


        }
    })

}

function suspectedTopN(id, n) {
    const chartDom = document.getElementById(id);
    const myChart = echarts.init(chartDom);
    let option;

    option = {
        tooltip: {
            trigger: 'item'
        }, legend: {
            top: '0', left: 'center', textStyle: {
                color: 'wheat'
            },
        }, series: [{
            name: '疑似病例',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10, borderColor: '#fff', borderWidth: 2
            },
            label: {
                show: false, position: 'center'
            },
            emphasis: {
                label: {
                    show: true, fontSize: '40', fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [{value: 1048, name: '湖北省'}, {value: 735, name: '湖南省'}, {value: 580, name: '台湾省'}, {
                value: 484,
                name: '上海市'
            }, {value: 300, name: '吉林省'}]
        }]
    };
    fetch(ROOT_PATH + '/provinceSuspectedTopN?n=' + n).then(r => r.json()).then(resp => {
        option.series.data = resp;
        option && myChart.setOption(option);
    })
}

function curedTopN(id, n = 5) {
    const chartDom = document.getElementById(id);
    const myChart = echarts.init(chartDom);
    let option;

    option = {
        dataset: {
            source: [['dead/cured ratio', 'Cured Count', 'name']]
        },
        grid: {containLabel: true},
        xAxis: {name: 'Cured Count', nameLocation: 'center'},
        yAxis: {type: 'category'},
        visualMap: {
            orient: 'horizontal', left: 'center', min: 0, max: 20, text: ['High', 'Low'], // Map the score column to color
            textStyle: {
                color: 'wheat'
            }, dimension: 0, itemWidth: 10, // bottom: 20,
            padding: [20, 0], inRange: {
                color: ['#65B581', '#FFCE34', '#FD665F']
            }, zlevel: 5
        },
        series: [{
            type: 'bar', encode: {
                // Map the "Cured Count" column to X axis.
                x: 'Cured Count', // Map the "name" column to Y axis
                y: 'name'
            }
        }]
    };

    fetch(ROOT_PATH + '/provinceCuredTopNAndDead?n=' + n).then(r => r.json()).then(resp => {
        let source = option.dataset.source;
        let max = 0;
        let min = Number.MAX_VALUE;
        for (let i = 0; i < resp.length; i++) {
            let o = [];
            let ratio = resp[(i + 3) % resp.length].province_deadCount / resp[(i + 3) % resp.length].province_curedCount * 100;
            min = Math.min(ratio, min);
            max = Math.max(ratio, max);
            o.push(ratio)
            o.push(resp[(i + 3) % resp.length].province_curedCount / 1000)
            o.push(resp[(i + 3) % resp.length].name)
            source.push(o);
        }
        if (min > max) min = 10;
        option.visualMap.min = min;
        option.visualMap.max = max;
        option && myChart.setOption(option);
    })
}


function compareTopN(id, n = 5) {
    const chartDom = document.getElementById(id);
    const myChart = echarts.init(chartDom, 'dark');
    let option;

    option = {
        title: {
            text: ''
        }, tooltip: {
            trigger: 'axis'
        }, legend: {
            data: ['确诊', '死亡', '疑似', '治愈']
        }, grid: {
            left: '3%', right: '4%', bottom: '3%', containLabel: true
        }, toolbox: {
            feature: {
                saveAsImage: {}
            }
        }, xAxis: {
            type: 'category', boundaryGap: false, data: ['湖北省', '上海市', '北京市', '吉林省', '浙江省']
        }, yAxis: {
            type: 'value'
        }, series: [{
            name: '确诊', type: 'line', data: [120, 132, 101, 134, 90, 230, 210]
        }, {
            name: '死亡', type: 'line', data: [220, 182, 191, 234, 290, 330, 310]
        }, {
            name: '疑似', type: 'line', data: [150, 232, 201, 154, 190, 330, 410]
        }, {
            name: '治愈', type: 'line', data: [150, 232, 201, 154, 190, 240, 410]
        }]
    };

    fetch(ROOT_PATH + '/provinceCompareTopN?n=' + n).then(r => r.json()).then(json => {
        let data = new Array(5);
        data[0] = [];
        data[1] = [];
        data[2] = [];
        data[3] = [];
        data[4] = [];

        for (let i = 0; i < json.length; i++) {
            data[0].push(json[i].name);
            data[1].push(json[i].province_confirmedCount);
            data[2].push(json[i].province_deadCount);
            data[3].push(json[i].province_suspectedCount);
            data[4].push(json[i].province_curedCount)
        }
        option.xAxis.data = data[0];
        option.series[0].data = data[1];
        option.series[1].data = data[2];
        option.series[2].data = data[3];
        option.series[3].data = data[4];
        option && myChart.setOption(option);
    }).catch(e => {
        console.log(e);
        option && myChart.setOption(option);
    })

}

function countryConfirmedOverall(id) {

    const chartDom = document.getElementById(id);
    const myChart = echarts.init(chartDom, 'dark');
    let option;

    let date = [];
    let data = [];
    /*    for (let i = 1; i < 20000; i++) {
            const now = new Date((base += oneDay));
            date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'));
            data.push(i + 1);
        }*/
    option = {
        tooltip: {
            trigger: 'axis', position: function (pt) {
                return [pt[0], '10%'];
            }
        }, title: {
            left: 'center', text: '疫情感染人数总览'
        }, toolbox: {
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                }, restore: {}, saveAsImage: {}
            }
        }, xAxis: {
            type: 'category', boundaryGap: false, data: date
        }, yAxis: {
            type: 'value', boundaryGap: [0, '100%'], max: 'dataMax'
        }, dataZoom: [{
            type: 'inside', start: 0, end: 100
        }, {
            start: 0, end: 10
        }], series: [{
            name: '感染人数', type: 'line', symbol: 'none', sampling: 'lttb', itemStyle: {
                color: 'rgb(255, 70, 131)'
            }, areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0, color: 'rgb(255, 158, 68)'
                }, {
                    offset: 1, color: 'rgb(255, 70, 131)'
                }])
            }, data: data
        }]
    };
    fetch(ROOT_PATH + '/countryOverall').then(r => r.json()).then(json => {
        for (let i = 0; i < json.length; i++) {
            data.push(json[i].value)
            date.push(json[i].date)
        }
        option && myChart.setOption(option);
    });
}

