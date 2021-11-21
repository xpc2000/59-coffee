<template>
  <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
  <div id="topdiv">
    <div id="bar1" style="width: 400px; height: 400px"></div>
    <div id="bar2" style="width: 400px; height: 400px"></div>
    <div id="bar3" style="width: 400px; height: 400px"></div>
    <div id="bar4" style="width: 400px; height: 400px"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  data: function() {
    return {
      xdata: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
      ydata: [5, 20, 36, 10, 10, 20],

      dataGroup: [
        { id: "bar1", title: "近5日浏览图", legend: "浏览次数", xdata: [], ydata: [20, 16,17,48,108], url: "/admins/data/browse" },
        { id: "bar2", title: "近5日收藏图", legend: "收藏次数", xdata: [], ydata: [3,5,7,3,5], url: "/admins/data/favorite" },
        { id: "bar3", title: "近5日发帖图", legend: "发帖次数", xdata: [], ydata: [11,2,15,21,12], url: "/admins/data/post" },
        { id: "bar4", title: "近5日回复图", legend: "回复次数", xdata: [], ydata: [38,23,124,64,18], url: "/admins/data/reply" },
      ],
    };
  },
  mounted() {
    for (let item of this.dataGroup) {
      this.$axiosLogin
        .get(item.url)
        .then((res) => {
          let data = res.data.data;
          for (let D of data) {
            item.xdata.push(D.date);
          }
          item.xdata.reverse();
          var option = {
            title: {
              text: item.title,
            },
            legend: {
              data: [item.legend],
            },
            xAxis: {
              data: item.xdata,
            },
            yAxis: {},
            series: [
              {
                name: "次数",
                type: "bar",
                data: item.ydata,
              },
            ],
          };
          let myChart = echarts.init(document.getElementById(item.id));
          myChart.setOption(option);
        })
        .catch((e) => this.$solve(e));
    }
  },
};

// 基于准备好的dom，初始化echarts实例
</script>

<style scoped>
#topdiv {
  width: 75%;
  margin: 10% auto;
}
#topdiv > div {
  float: left;
}
</style>
