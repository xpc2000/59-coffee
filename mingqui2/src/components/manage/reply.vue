<template>
  <el-page-header @back="goBack" content="回复管理"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card>
    <template #header>
      条件查询
    </template>
    <el-space>
      <el-input size="mini" v-model="query.uid" placeholder="用户ID"></el-input>
      <el-input size="mini" v-model="query.pid" placeholder="帖子ID"></el-input>
      <el-input size="mini" v-model="query.lid" placeholder="跟帖ID"></el-input>
      <el-input size="mini" v-model="query.iid" placeholder="回复ID"></el-input>
      <el-button size="mini" type="primary" @click="queryReplys('layer')">查询跟帖</el-button>
      <el-button size="mini" type="primary" @click="queryReplys('interlayer')">查询回复</el-button>
    </el-space>
  </el-card>

  <!-- 空行 -->
  <p></p>

  <el-card>
    <template #header>
      查询结果
    </template>
    <el-table :data="dataArray" style="width: 100%" row-key="id">
      <el-table-column label="类型" width="80">
        <template #default="{row}">{{ row.iid ? "回复" : "跟帖" }}</template>
      </el-table-column>
      <el-table-column label="内容" prop="content"> </el-table-column>
      <el-table-column label="回复时间">
        <template #default="{row}">{{ formateDate(row.time, "yyyy-MM-dd") }}</template>
      </el-table-column>
      <el-table-column label="状态">
        <template #default="{row}">{{ row.ban ? "封禁" : "正常" }}</template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="80">
        <template #default="{row}">
          <el-button size="mini" :disabled="row.ban" type="danger" @click="handleForbid(row)">封禁</el-button>
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
      query: { uid: "", pid: "", lid: "", iid: "" },

      dataArray: [],
    };
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    formateDate(date, format) {
      return formateDate(date, format);
    },
    queryReplys(type) {
      let param =
        !this.query.uid && !this.query.pid && !this.query.lid && !this.query.iid
          ? undefined
          : {
              uid: this.query.uid,
              pid: this.query.pid,
              lid: this.query.lid,
              iid: this.query.iid,
            };
      this.$axiosLogin
        .post("/admins/replies/" + type, param)
        .then((res) => {
          let array = res.data.data.list;
          for (let item of array) {
            item.id = `${item.lid}-${item.iid}`;
            item.time = new Date(item.time);
          }
          this.dataArray = array;
        })
        .catch((e) => this.$solve(e));
    },
    handleForbid(row) {
      this.$axiosLogin
        .put(`admins/replies/${row.iid ? 4 : 3}/${row.iid ? row.iid : row.lid}/ban`)
        .then(() => {
          this.$message.success("封禁成功");
          row.ban = true;
        })
        .catch((e) => this.$solve(e));
    },
  },
  mounted() {
    this.queryReplys("layer");
  },
};
</script>
