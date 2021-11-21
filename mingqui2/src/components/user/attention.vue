<template>
  <el-page-header @back="goBack" content="我的消息"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-dialog v-model="detailDialog" :title="detail.title">
    <div>{{ detail.message }}</div>

    <template #footer>
      <span class="little-gray">发送日期 {{ formateDate(detail.time, "yyyy-MM-dd hh:mm") }} &nbsp;</span>
      <el-button type="primary" @click="detailDialog = false" size="small">确定</el-button>
    </template>
  </el-dialog>

  <div v-for="attention in attentions" :key="attention.id">
    <el-card @click="showDetail(attention)">
      <div class="space-between-center-flex">
        <div>
          <div>{{ attention.title }}</div>
          <p class="little-gray">{{ attention.message }}</p>
        </div>
        <div>
          <span v-if="attention.todo" class="little-gray"><span style="color:red">●</span> 待办</span>
          <span class="little-gray">&nbsp; {{ formateDate(attention.time, "yyyy-MM-dd hh:mm") }}</span>
        </div>
      </div>
    </el-card>

    <!-- 空行 -->
    <p></p>
  </div>
</template>

<script>
import formateDate from "../../jsmodule/format-date";

export default {
  data() {
    return {
      detailDialog: false,
      detail: {
        id: 1,
        todo: true,
        type: "未知类型",
        title: "一条新消息的标题",
        message: "这是一条新消息的内容",
        time: new Date(),
      },

      attentions: [
        {
          id: 1,
          todo: true,
          type: "未知类型",
          title: "一条新消息的标题",
          message: "这是一条新消息的内容",
          time: new Date(),
        },
        {
          id: 1,
          todo: false,
          type: "未知类型",
          title: "一条新消息的标题",
          message: "这是一条新消息的内容",
          time: new Date(),
        },
      ],
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
    showDetail(attention) {
      attention.todo = false;

      // /
      this.$axiosLogin
        .put("/users/self/attentions/" + attention.id + "/done")
        .then(() => {
          this.$message.success("消息已读");
        })
        .catch((e) => this.$solve(e));

      this.detail = attention;
      this.detailDialog = true;
    },
  },
  mounted() {
    this.$axiosLogin
      .get("/users/self/attentions")
      .then((res) => {
        let array = res.data.data;
        console.log(array);
        for (let id in array) {
          let item = array[id];
          item.time = new Date(item.time);
          if (!item.postTag) {
            item.postTag = "默认";
          }
        }
        this.attentions = array;
      })
      .catch((e) => this.$solve(e));
  },
};
</script>

<style scoped>
.little-gray {
  font-size: 0.8em;
  color: rgb(134, 134, 134);
}

.space-between-center-flex {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
