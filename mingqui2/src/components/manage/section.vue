<template>
  <el-page-header @back="goBack" content="板块管理"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card>
    <template #header>
      新增板块
    </template>
    <el-space>
      <el-input v-model="newSectionName" size="small"></el-input>
      <el-button type="success" size="small" @click="newSection()">新增板块</el-button>
    </el-space>
  </el-card>

  <p></p>

  <el-card>
    <el-table :data="sectionArray" style="width: 100%" row-key="id">
      <el-table-column type="expand">
        <template #default="scope">
          <el-space wrap>
            <el-tag
              v-for="tag in scope.row.tags"
              :key="scope.row.id + '-' + tag.id"
              :type="tag.available ? '' : 'info'"
              :closable="tag.available && !scope.row.abandoned"
              @close="handleDeleteTag(scope.$index, scope.row.id, tag.id)"
            >
              {{ tag.tagName }}
            </el-tag>

            <el-input v-if="scope.row.newTagVisible" v-model="scope.row.newTagName" size="small" @blur="newTag(scope.$index, scope.row.id)">
            </el-input>
            <el-button v-else size="small" @click="[scope.row.newTagVisible, scope.row.newTagName] = [true, '']">+ New Tag</el-button>
          </el-space>
        </template>
      </el-table-column>
      <el-table-column label="ID" prop="id" width="100"> </el-table-column>
      <el-table-column label="板块名" prop="sectionName"> </el-table-column>
      <el-table-column label="昨日回复数" prop="yesterdayReply"> </el-table-column>
      <el-table-column label="状态">
        <template #default="{row}">
          {{ row.abandoned ? "已废弃" : "生效中" }}
        </template>
      </el-table-column>
      <el-table-column align="right" fixed="right">
        <template #default="scope">
          <el-button size="mini" type="danger" :disabled="scope.row.abandoned" @click="handleDeleteSection(scope.$index, scope.row.id)"
            >废弃</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      newSectionName: "",

      //管理员一览
      sectionArray: [],
    };
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    newSection() {
      if (!this.newSectionName) {
        this.$message.warning("请输入板块名");
        return;
      }
      this.$axiosLogin
        .post("/admins/sections", { sectionName: this.newSectionName })
        .then(() => {
          this.$message.success("添加成功");
          this.loadSections();
        })
        .catch((e) => this.$solve(e));
    },
    //axios加载板块，并且给每一行添加new属性
    loadSections() {
      this.$axiosLogin
        .get("/admins/sections")
        .then((res) => {
          let array = res.data.data.list;
          for (let item of array) {
            [item.newTagVisible, item.newTagName] = [false, ""];
          }
          this.sectionArray = array;
        })
        .catch((e) => this.$solve(e));
    },
    handleDeleteSection($index, id) {
      this.$axiosLogin
        .delete("/admins/sections/" + id)
        .then(() => {
          this.$message.success("废弃成功");
          this.sectionArray[$index].abandoned = true;
        })
        .catch((e) => this.$solve(e));
    },
    newTag($index, sid) {
      if (!this.sectionArray[$index].newTagName) {
        return;
      }

      this.$axiosLogin
        .post(`admins/sections/${sid}/tags`, { tagName: this.sectionArray[$index].newTagName })
        .then(() => {
          this.$message.success("新增Tag成功");
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        })
        .catch((e) => this.$solve(e));
    },
    handleDeleteTag($index, sid, tid) {
      this.$axiosLogin
        .delete(`admins/sections/${sid}/tags/${tid}`)
        .then(() => {
          this.$message.success("删除Tag成功");
          for (let tag of this.sectionArray[$index].tags) {
            if (tag.id == tid) {
              tag.available = false;
              return;
            }
          }
        })
        .catch((e) => this.$solve(e));
    },
  },
  created() {
    this.loadSections();
  },
};
</script>
