<template>
  <el-page-header @back="goBack" content="封禁管理"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card>
    <template #header>
      条件查询
    </template>
    <div style="display:flex;flex-direction: column">
      <el-space direction="vertical">
        <el-space>
          <el-input size="mini" v-model="query.banId" placeholder="禁用ID"></el-input>
          <el-input size="mini" v-model="query.banType" placeholder="类型"></el-input>
        </el-space>
        <el-space>
          <el-input size="mini" v-model="query.state" placeholder="状态"></el-input>
          <el-input size="mini" v-model="query.adminId" placeholder="管理员ID"></el-input>
        </el-space>
        <el-space alignment="right">
          <el-button size="mini" type="primary" @click="loadBanLog()">查询</el-button>
        </el-space>
      </el-space>
    </div>
  </el-card>

  <!-- 空行 -->
  <p></p>

  <el-card>
    <template #header>
      查询结果
    </template>
    <el-table :data="dataArray" style="width: 100%" row-key="id">
      <el-table-column label="类型">
        <template #default="{row}">{{ formateAccusationType(row.banType) }}</template>
      </el-table-column>
      <el-table-column label="管理员ID" prop="aid"> </el-table-column>
      <el-table-column label="封禁时间">
        <template #default="{row}">{{ formateDate(row.createdTime, "yyyy-MM-dd") }}</template>
      </el-table-column>
      <el-table-column label="状态">
        <template #default="{row}">{{ row.state == 1 ? "封禁" : "解封" }}</template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import formateDate from "../../jsmodule/format-date";

export default {
  data() {
    return {
      query: { banId: "", banType: "", state: "", adminId: "" },

      dataArray: [],
    };
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    loadBanLog() {
      let param = !this.query.banId && !this.query.banType && !this.query.state && !this.query.adminId ? undefined : this.query;
      console.log(param);
      this.$axiosLogin
        .post("/admins/ban-log", param)
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
    formateAccusationType(type) {
      //1-举报用户 2-举报帖子 3-举报楼 4-举报楼中楼
      switch (type) {
        case 1:
          return "用户";
        case 2:
          return "帖子";
        case 3:
          return "楼";
        default:
          return "楼中楼";
      }
    },
    formateDate(date, format) {
      return formateDate(date, format);
    },
  },
  mounted() {
    console.log("wskldfwekljfh");
    this.loadBanLog();
  },
};
</script>
