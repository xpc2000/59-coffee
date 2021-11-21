<template>
  <el-page-header @back="goBack" content="用户管理"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card>
    <template #header>
      条件查询
    </template>
    <el-space>
      <el-input size="mini" v-model="query.id" placeholder="请输入用户id"></el-input>
      <el-input size="mini" v-model="query.username" placeholder="请输入用户名"></el-input>
      <el-input size="mini" v-model="query.nickname" placeholder="请输入用户昵称"></el-input>
      <el-button size="mini" type="primary" width="7em" @click="queryUsers()">
        查询{{ !query.id && !query.username && !query.nickname ? "全部用户" : "" }}
      </el-button>
    </el-space>
  </el-card>

  <!-- 空行 -->
  <p></p>

  <el-card>
    <template #header>
      查询结果
    </template>
    <el-table :data="userArray" style="width: 100%" row-key="id">
      <el-table-column label="UID" prop="id" width="100"> </el-table-column>
      <el-table-column label="用户名" prop="username"> </el-table-column>
      <el-table-column label="昵称" prop="nickname"> </el-table-column>
      <el-table-column label="签名" prop="message"> </el-table-column>
      <el-table-column label="状态">
        <template #default="{row}">{{ row.state == 1 ? "正常" : "封禁" }}</template>
      </el-table-column>
      <el-table-column align="right" fixed="right" label="操作" width="150">
        <template #default="{row, $index}">
          <el-button size="mini" @click="handleEdit($index, row)">编辑</el-button>
          <el-button size="mini" v-if="row.state == 1" type="danger" @click="handleForbid($index, row)">封禁</el-button>
          <el-button size="mini" v-if="row.state != 1" @click="handleUnseal($index, row)">解封</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <!-- 修改用户信息 -->
  <el-dialog title="修改用户信息" v-model="changeUserDialog">
    <el-form :model="changeUserForm" label-width="120px" :rules="userRules" ref="changeUserForm">
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="changeUserForm.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="签名" prop="message">
        <el-input v-model="changeUserForm.message" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="changeUserDialog = false">取 消</el-button>
        <el-button type="primary" @click="submitChangeUser">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      changeUserDialog: false,
      changeUserForm: { id: 0, username: "", nickname: "", message: "", state: 1 },

      query: { uid: 1, username: "", nickname: "" },

      //校验规则
      userRules: {
        nickname: [
          { required: true, message: "请输入昵称", trigger: "blur" },
          { min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "change" },
        ],
        message: [
          { required: true, message: "请输入签名", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "change" },
        ],
      },

      userArray: [],
    };
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    queryUsers() {
      let param =
        !this.query.id && !this.query.username && !this.query.nickname
          ? undefined
          : {
              uid: this.query.id,
              username: this.query.username,
              nickname: this.query.nickname,
            };
      this.$axiosLogin
        .post("/admins/users/query", param)
        .then((res) => {
          let array = res.data.data.list;
          this.userArray = array;
        })
        .catch((e) => this.$solve(e));
    },
    submitChangeUser() {
      this.$refs["changeUserForm"].validate((valid) => {
        if (valid) {
          this.$message.success("handleForbid" + this.changeUserForm.nickname);

          let param = {
            nickname: this.changeUserForm.nickname,
            message: this.changeUserForm.message,
          };
          this.$axiosLogin
            .put("/admins/users/" + this.changeUserForm.id, param)
            .then(() => {
              this.$message.success("修改成功");
              this.changeUserDialog = false;
              for (let item of this.userArray) {
                if (item.id == this.changeUserForm.id) {
                  item.nickname = this.changeUserForm.nickname;
                  item.message = this.changeUserForm.message;
                  break;
                }
              }
            })
            .catch((e) => this.$solve(e));
        }
      });
    },
    handleEdit(_, row) {
      this.changeUserForm = { ...row };
      this.changeUserDialog = true;
    },
    handleForbid($index, row) {
      this.$axiosLogin
        .put(`admins/users/${row.id}/ban`)
        .then(() => {
          this.$message.success("封禁成功");
          this.userArray[$index].state = 2;
        })
        .catch((e) => this.$solve(e));
    },
    handleUnseal($index, row) {
      this.$axiosLogin
        .put(`admins/users/${row.id}/unban`)
        .then(() => {
          this.$message.success("解禁成功");
          this.userArray[$index].state = 1;
        })
        .catch((e) => this.$solve(e));
    },
  },
  mounted() {
    this.queryUsers();
  },
};
</script>
