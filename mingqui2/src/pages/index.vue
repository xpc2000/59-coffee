<template>
  <main-layout>
    <div id="index-major">
      <div class="index-block" v-for="section in sections" :key="'s' + section.sid">
        <el-card>
          <template #header>
            <span class="hover-pointer" @click="goSection(section.sid)">{{ section.sname }}</span>
          </template>

          <div>
            <div v-for="post in section.posts" :key="'p' + post.pid" class="space-between-center-flex hover-pointer hover-gray" @click="goPost(post.pid)">
              <p>{{ post.postTitle }} &nbsp;&nbsp;<el-tag v-if="post.good" size="small" type="danger">加精</el-tag></p>
              <p class="little-gray">{{ formateDate(post.postTime, "yyyy-MM-dd") }}</p>
            </div>
          </div>
        </el-card>

        <p></p>
      </div>
    </div>
  </main-layout>
</template>

<script>
import mainLayout from "../components/main-layout";
import formateDate from "../jsmodule/format-date";

export default {
  data() {
    return {
      sections: [
        {
          sid: 2,
          sname: "娱乐",
          posts: [
            {
              pid: 111,
              postSection: "娱乐",
              postTag: "瓜",
              postTitle: "都美竹加油",
              postReplies: 12,
              postPraise: 666,
              postTime: new Date(),
              good: true,
            },
            {
              pid: 112,
              postSection: "娱乐",
              postTag: "瓜",
              postTitle: "吴亦凡惨遭解约",
              postReplies: 12,
              postPraise: 666,
              postTime: new Date(),
              good: false,
            },
          ],
        },
        {
          sid: 3,
          sname: "外参",
          posts: [
            {
              pid: 111,
              postSection: "外参",
              postTag: "瓜",
              postTitle: "都美竹加油",
              postReplies: 12,
              postPraise: 666,
              postTime: new Date(),
              good: true,
            },
            {
              pid: 112,
              postSection: "外参",
              postTag: "瓜",
              postTitle: "吴亦凡惨遭解约",
              postReplies: 12,
              postPraise: 666,
              postTime: new Date(),
              good: false,
            },
          ],
        },
      ],
    };
  },
  components: {
    mainLayout,
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    goSection(id) {
      this.$router.push("/section/" + id);
    },
    goPost(id) {
      this.$router.push("/post/" + id);
    },
    formateDate(date, format) {
      return formateDate(date, format);
    },
    goUserhome(id) {
      this.$router.push(`/userhome/visit/${id}`);
    },
  },
  onMounted() {
    console.log("index page ready!");
  },
};
</script>

<style>
/* 影响全局的变量 */
#index-major div.el-card__header {
  padding: 8px 12px;
}

#index-major div.el-card__body {
  padding: 0;
}
</style>

<style scoped>
#index-major {
  min-height: 500px;
  width: 100%;
  overflow: hidden;
}

.index-block {
  width: 100%;
}

.hover-pointer:hover {
  cursor: pointer;
}

.hover-gray {
  padding: 5px 20px;
}

.hover-gray:hover {
  background-color: rgb(250, 250, 250);
}

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
