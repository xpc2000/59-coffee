<template>
  <el-page-header @back="goBack" content="管理团队"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <el-card v-if="Unauthorized">
    <el-result icon="error" title="权限不足" subTitle="您不是超管，无法对管理团队进行操作"> </el-result>
  </el-card>

  <div v-if="!Unauthorized">
    <el-card>
      <el-table :data="adminArray" style="width: 100%" row-key="id">
        <el-table-column label="ID" prop="id" width="60"> </el-table-column>
        <el-table-column label="管理员" prop="adminName"> </el-table-column>
        <el-table-column label="邮箱" prop="email"> </el-table-column>
        <el-table-column label="电话" prop="phone"> </el-table-column>
        <el-table-column label="角色">
          <template #default="{row}">
            {{ row.role ? (row.role == 1 ? "超管" : "管理员") : "已废弃" }}
          </template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template #default="{row}">
            {{ formateDate(row.updatedTime, "yyyy-MM-dd") }}
          </template>
        </el-table-column>
        <el-table-column align="right" fixed="right" width="150">
          <template #header>
            <el-button type="success" size="mini" @click="handleNew">新增</el-button>
          </template>
          <template #default="scope">
            <el-button size="mini" :disabled="scope.row.role == 0" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" :disabled="scope.row.role <= 1" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增管理员 -->
    <el-dialog title="新建管理员" v-model="newAdminDialog">
      <el-form :model="newAdminForm" label-width="120px" :rules="adminRules" ref="newAdminForm">
        <el-form-item label="昵称" prop="adminName">
          <el-input v-model="newAdminForm.adminName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="newAdminForm.password" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="newAdminForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="newAdminForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="newAdminDialog = false">取 消</el-button>
          <el-button type="primary" @click="submitNewAdmin">创 建</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 修改管理员信息 -->
    <el-dialog title="修改管理员信息" v-model="changeAdminDialog">
      <el-form :model="changeAdminForm" label-width="120px" :rules="adminRules" ref="changeAdminForm">
        <el-form-item label="昵称" prop="adminName">
          <el-input v-model="changeAdminForm.adminName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="changeAdminForm.password" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="changeAdminForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="changeAdminForm.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="changeAdminDialog = false">取 消</el-button>
          <el-button type="primary" @click="submitChangeAdmin">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import formateDate from "../../jsmodule/format-date";
import { getPayload } from "../../jsmodule/token";

export default {
  data() {
    return {
      Unauthorized: false,

      newAdminDialog: false,
      newAdminForm: { adminName: "", password: "", email: "", phone: "", role: 2 },

      changeAdminDialog: false,
      changeAdminForm: { id: 0, adminName: "", password: "", email: "", phone: "", role: 2 },

      //校验规则
      adminRules: {
        adminName: [
          { required: true, message: "请输入昵称", trigger: "blur" },
          { min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "change" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "change" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { type: "email", message: "请输入正确的邮箱", trigger: "change" },
        ],
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话长度必须是11位", trigger: "change" },
        ],
      },

      //管理员一览
      adminArray: [],
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
    submitNewAdmin() {
      this.$refs["newAdminForm"].validate((valid) => {
        let param = {
          adminName: this.newAdminForm.adminName,
          password: this.newAdminForm.password,
          email: this.newAdminForm.email,
          phone: this.newAdminForm.phone,
          role: this.newAdminForm.role,
        };
        console.log("param", param);
        if (valid) {
          this.$axiosLogin
            .post("/admins", param)
            .then(() => {
              this.$message.success("修改成功");
              setTimeout(() => {
                this.$router.go(0);
              }, 1000);
            })
            .catch((e) => this.$solve(e));
        }
      });
    },
    submitChangeAdmin() {
      this.$refs["changeAdminForm"].validate((valid) => {
        if (valid) {
          this.$axiosLogin
            .put("/admins/" + this.changeAdminForm.id, {
              adminName: this.changeAdminForm.adminName,
              password: this.changeAdminForm.password,
              email: this.changeAdminForm.email,
              phone: this.changeAdminForm.phone,
              role: this.changeAdminForm.role,
            })
            .then(() => {
              this.$message.success("修改成功");
              setTimeout(() => {
                this.$router.go(0);
              }, 1000);
            })
            .catch((e) => this.$solve(e));
        }
      });
    },
    handleNew() {
      this.newAdminDialog = true;
      this.newAdminForm = { adminName: "", password: "", email: "", phone: "", role_id: 2 };
    },
    handleEdit(_, row) {
      this.changeAdminForm = { ...row };
      this.changeAdminDialog = true;
    },
    handleDelete($index, row) {
      this.$confirm(`此操作将永久删除 ${row.adminName}, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axiosLogin
            .delete("/admins/" + row.id)
            .then(() => {
              this.$message.success("删除成功");
              this.adminArray[$index].state = 3;
            })
            .catch((e) => this.$solve(e));
        })
        .catch();
    },
  },
  mounted() {
    //Unauthorized
    let payload = getPayload();
    if (!payload || payload.roleId != 1) {
      this.Unauthorized = true;
      return;
    }

    this.$axiosLogin
      .get("/admins")
      .then((res) => {
        let array = res.data.data.list;
        for (let id in array) {
          let item = array[id];
          item.createdTime = new Date(item.createdTime);
          item.updatedTime = new Date(item.updatedTime);
          item.phone = item.telephone;
        }
        this.adminArray = array;
      })
      .catch((e) => this.$solve(e));
  },
};
</script>
