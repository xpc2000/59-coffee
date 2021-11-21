<template>
  <el-page-header @back="goBack()" content="我的头像"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card>
    <template #header>上传新头像</template>
    <el-upload class="avatar-uploader" :show-file-list="false" :http-request="upload" :file-list="fileList">
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </el-card>

  <p></p>

  <el-card>
    <template #header>历史头像</template>
    <el-space :size="40">
      <el-avatar
        v-for="pair in history"
        :key="pair.id"
        :class="{ 'hover-pointer': true, now: avatarUrl == pair.avatarUrl }"
        :src="pair.avatarUrl"
        :size="80"
        @click="choose(pair.id)"
        shape="square"
      ></el-avatar>
    </el-space>
  </el-card>
</template>

<script>
import { mapState } from "vuex";

import { getToken } from "../../jsmodule/token";

export default {
  data() {
    return {
      fileList: [],

      imageUrl: "",

      history: [
        {
          id: 1,
          avatarUrl: "",
        },
      ],
    };
  },
  computed: {
    ...mapState(["avatarUrl"]),
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
    loadAvatars() {
      this.$axiosLogin.get("users/self/avatar").then((res) => {
        this.history = [];
        for (let id in res.data.data) {
          this.history.push({
            id: id,
            avatarUrl: res.data.data[id],
          });
        }
      });
    },
    upload(param) {
      let form = new FormData();
      form.append("file", param.file);

      this.$axios
        .post("http://localhost:8081/users/self/avatar", form, {
          headers: {
            Authorization: getToken(),
          },
        })
        .then(() => {
          this.$message.success("上传成功");
          this.loadAvatars();
        })
        .catch((e) => {
          this.$solve(e);
        });
    },
    choose(id) {
      this.$axiosLogin
        .put("/users/self/avatar/" + id)
        .then(() => {
          this.$message.success("头像更换成功");
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        })
        .catch((e) => {
          this.$solve(e);
        });
    },
  },
  mounted() {
    this.loadAvatars();
  },
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 2px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style scoped>
.little-gray {
  font-size: 0.8em;
  color: rgb(134, 134, 134);
}

.space-between-flex {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
}

.hover-pointer:hover {
  cursor: pointer;
}

.now {
  border: 2px solid #409eff;
}
</style>
