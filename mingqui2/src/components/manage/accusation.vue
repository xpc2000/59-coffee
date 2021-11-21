<template>
  <el-page-header @back="goBack" content="举报管理"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card>
    <template #header>
      条件查询
    </template>
    <el-space>
      <el-input size="mini" v-model="query.type" placeholder="类型"></el-input>
      <el-input size="mini" v-model="query.uid" placeholder="用户ID"></el-input>
      <el-input size="mini" v-model="query.state" placeholder="状态"></el-input>
      <el-button size="mini" type="primary" @click="queryAccusations()">查询</el-button>
    </el-space>
  </el-card>

  <!-- 空行 -->
  <p></p>

  <el-card>
    <template #header>
      查询结果
    </template>
    <el-table :data="dataArray" style="width: 100%" row-key="id">
      <el-table-column label="类型" width="100">
        <template #default="{row}">{{ formateAccusationType(row.type) }}</template>
      </el-table-column>
      <el-table-column label="举报原因" prop="reason"> </el-table-column>
      <el-table-column label="举报时间">
        <template #default="{row}">{{ formateDate(row.createdTime, "yyyy-MM-dd") }}</template>
      </el-table-column>
      <el-table-column label="状态">
        <template #default="{row}">{{ row.state == 1 ? "未受理" : row.state == 3 ? "否决" : "已受理" }}</template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="{row}">
          <el-button size="mini" :disabled="row.state != 1" @click="handleSolve(row, false)">否决</el-button>
          <el-button size="mini" :disabled="row.state != 1" type="primary" @click="handleSolve(row, true)">受理</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import formateDate from "../../jsmodule/format-date";

export default {
  data() {
    return {
      query: { type: "", uid: "", state: "" },

      dataArray: [],
    };
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    formateAccusationType(type) {
      //1-举报用户 2-举报帖子 3-举报楼 4-举报楼中楼
      switch (type) {
        case 1:
          return "用户";
        case 2:
          return "帖子";
        case 3:
          return "跟帖";
        default:
          return "回复";
      }
    },
    formateDate(date, format) {
      return formateDate(date, format);
    },
    queryAccusations() {
      let param =
        !this.query.type && !this.query.uid && !this.query.state
          ? undefined
          : {
              type: this.query.type,
              uid: this.query.uid,
              state: this.query.state,
            };
      this.$axiosLogin
        .post("/admins/accusations", param)
        .then((res) => {
          let array = res.data.data.list;
          console.log(array);
          for (let item of array) {
            item.createdTime = new Date(item.createdTime);
          }
          this.dataArray = array;
        })
        .catch((e) => this.$solve(e));
    },
    handleSolve(row, solve) {
      this.$axiosLogin
        .put("/admins/accusations/" + row.id, {
          solve: solve,
        })
        .then(() => {
          row.state = solve ? 2 : 3;
        })
        .catch((e) => this.$solve(e));
    },
  },
  mounted() {
    this.queryAccusations();
  },
};
</script>
