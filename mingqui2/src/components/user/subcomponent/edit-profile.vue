<template>
  <!-- 编辑个人信息 -->
  <el-dialog v-model="changeDialog" @open="copyInfo()">
    <el-result v-if="resultShow" icon="success" title="修改成功" subTitle="即将刷新页面"> </el-result>

    <el-form v-if="!resultShow" :model="changeForm" label-width="120px" :rules="rules" ref="changeForm">
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="changeForm.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="个性签名" prop="message">
        <el-input v-model="changeForm.message" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="changeForm.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="changeForm.phone" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="changeDialog = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  props: ["user", "show"],
  emits: ["close"],
  data() {
    return {
      resultShow: false,

      changeForm: { nickname: "", message: "", email: "", phone: "" },

      //校验规则
      rules: {
        nickname: [
          { required: true, message: "请输入昵称", trigger: "change" },
          { min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "change" },
        ],
        message: [
          { required: true, message: "请输入个性签名", trigger: "change" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "change" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "change" },
          { type: "email", message: "请输入正确的邮箱", trigger: "change" },
        ],
        phone: [
          { required: true, message: "请输入电话", trigger: "change" },
          { min: 11, max: 11, message: "电话长度必须是11位", trigger: "change" },
        ],
      },
    };
  },
  computed: {
    changeDialog: {
      get() {
        return this.show;
      },
      set(newValue) {
        if (!newValue) {
          this.$emit("close");
        }
      },
    },
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    copyInfo() {
      this.changeForm = {
        nickname: this.user.nickname,
        message: this.user.message,
        email: this.user.email,
        phone: this.user.telephone,
      };
    },
    submit() {
      this.$refs.changeForm.validate((valid) => {
        if (valid) {
          this.$axiosLogin
            .put("/users/self", {
              nickname: this.changeForm.nickname,
              message: this.changeForm.message,
              email: this.changeForm.email,
              phone: this.changeForm.phone,
            })
            .then(() => {
              this.resultShow = true;
              setTimeout(() => {
                this.$router.go(0);
              }, 1000);
            })
            .catch((e) => {
              this.$solve(e, "信息冲突，修改失败");
            });
        }
      });
    },
    close() {
      this.emits("close");
    },
  },
};
</script>
