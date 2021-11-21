<template>
  <el-page-header @back="goBack" content="帖子管理"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card>
    <template #header>
      条件查询
    </template>
    <el-space>
      <el-input size="mini" v-model="query.pid" placeholder="帖子ID"></el-input>
      <el-input size="mini" v-model="query.sid" placeholder="板块ID"></el-input>
      <el-input size="mini" v-model="query.tid" placeholder="用户ID"></el-input>
      <el-input size="mini" v-model="query.uid" placeholder="标签ID"></el-input>
      <el-input size="mini" v-model="query.title" placeholder="帖子标题"></el-input>
      <el-button size="mini" type="primary" width="7em" @click="loadPosts()">
        查询
      </el-button>
    </el-space>
  </el-card>

  <!-- 空行 -->
  <p></p>

  <el-card>
    <template #header>
      查询结果
    </template>
    <el-table :data="dataArray" style="width: 100%" row-key="id">
      <el-table-column label="ID" prop="id" width="100"> </el-table-column>
      <el-table-column label="标题" prop="title"> </el-table-column>
      <el-table-column label="回复数" prop="replyCount"> </el-table-column>
      <el-table-column label="最后回复时间">
        <template #default="{row}">
          {{ formateDate(row.lastReplyTime, "yyyy-MM-dd") }}
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template #default="{row}">{{ row.state == 1 ? "正常" : row.state == 2 ? "封禁" : "删除" }}</template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="{row, $index}">
          <el-button size="mini" :disabled="row.state != 1" type="danger" @click="handleForbid($index, row)">封禁</el-button>
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

      query: { pid: "", sid: "", tid: "", uid: "", title: "" },

      dataArray: [
        {
          id: 1,
          title: "震惊！郭美美再入狱！",
          replyCount: 99,
          lastReplyTime: new Date(),
          state: 1,
        },
        {
          id: 2,
          title: "暖心！郭美美再入狱！",
          replyCount: 99,
          lastReplyTime: new Date(),
          state: 2,
        },
        {
          id: 3,
          title: "舒适！郭美美再入狱！",
          replyCount: 99,
          lastReplyTime: new Date(),
          state: 3,
        },
      ],
    };
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    loadPosts() {
      let param =
        !this.query.pid && !this.query.sid && !this.query.tid && !this.query.uid && !this.query.title
          ? undefined
          : {
              pid: this.query.pid,
              sid: this.query.sid,
              tid: this.query.tid,
              uid: this.query.uid,
              title: this.query.title,
            };
      this.$axiosLogin
        .post("/admins/posts/query", param)
        .then((res) => {
          let array = res.data.data.list;
          for (let item of array) {
            item.lastReplyTime = new Date(item.lastReplyTime);
          }
          this.dataArray = array;
        })
        .catch((e) => this.$solve(e));
    },
    formateDate(date, format) {
      return formateDate(date, format);
    },
    handleForbid($index, row) {
      this.$axiosLogin
        .put(`admins/posts/${row.id}/ban`)
        .then(() => {
          this.$message.success("封禁成功");
          this.dataArray[$index].state = 2;
        })
        .catch((e) => this.$solve(e));
    },
  },
  mounted() {
    this.loadPosts();
  },
};
</script>
