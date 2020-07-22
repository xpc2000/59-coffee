<template>
    <div>
      <!-- 面包屑 -->
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>库存管理</el-breadcrumb-item>
        <el-breadcrumb-item>进货</el-breadcrumb-item>
      </el-breadcrumb>
  
      <!-- 分割线 -->
      <el-divider></el-divider>
  
      <!-- 搜索栏 新建按钮-->
      <!-- <el-form class="user-search" :inline="true" label-width="90px">
        <el-form-item prop="store" label="餐品名称: ">
          <el-input v-model="name" placeholder="请输入餐品名称" clearable></el-input>
        </el-form-item>
        <el-form-item label>
          <el-button
            size="small"
            type="primary"
            icon="iconfont icon-chazhao"
            class="title"
            round
            @click="Search()"
          >查询餐品</el-button>
        </el-form-item>
      </el-form> -->
  
      <!-- 循环卡片列表 -->
      <div style="margin-left:0.5%;margin-right:1%">
        <el-row>
          <el-col :span="4" v-for="(item) in tempList" :key="item.idFood" :offset="1">
            <div style="margin-top:15px">
              <el-card :body-style="{ padding: '0px'}" shadow="hover">
                <img :src="item.url" class="image" />
                <div style="padding: 14px;">
                  <div style="padding-bottom: 14px; padding-top: 14px">
                    <span>
                      <i class="el-icon-star-on">{{item.idFood}}</i>
                    </span>
                  </div>
                  <div style="padding-bottom: 14px; padding-top: 14px">
                    <span>
                      <i class="el-icon-star-on">{{item.name}}</i>
                    </span>
                  </div>
                  <span>
                    <i class="el-icon-menu">{{item.type}}</i>
                  </span>
                  <div style="padding-bottom: 14px; padding-top: 14px">
                    <div style="padding-top: 14px">
                      <el-row type="flex">
                        <el-button type="success" size="mini" round @click="handleClick(item)">进货</el-button>
                      </el-row>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
  
        <br />
        <br />
        <!-- 编辑界面 -->
        <el-dialog title="进货" :visible.sync="EditFormVisible" width="30%">
          <el-form label-width="80px" :model="EditForm" :rules="editRules" ref="EditFormRef">
            <el-form-item label="进货数量" prop="num" required>
              <el-input
                size="small"
                v-model="EditForm.num"
                prefix-icon="iconfont icon-RectangleCopy"
                placeholder="请输入进货数量"
              ></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button size="small" @click="EditFormVisible = false">取消</el-button>
            <el-button size="small" type="primary" class="title" @click="buyFood()">保存</el-button>
          </div>
        </el-dialog>
        <!-- 分页 -->
        <div class="block" align="center">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[4,8, 12, 16]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalSize"
          ></el-pagination>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        EditFormVisible: false,
        shopid:'',
        params: {
          name: "",
          shopid: ""
        },
        para_ano: {
          foodid: "",
          shopid: ""
        },
        uploadURL: "https://api.uomg.com/api/image.ali?file=multipart",
        name: "", // 搜索框 餐品名称
        EditFormVisible: false, // 控制编辑页面显示与隐藏
        /* 分页 */
        pageSize: 4,
        currentPage: 1,
        totalSize: 20, // 总条数，用于死数据
        tempList: [], // 分页数据
        /* 死数据 循环卡片列表的数据源 */
        tableData: [
          {
            idFood: "1",
            name: "餐品1",
            type: "类型",
            price: "单价",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "500",
            sellOut: "1"
          },
          {
            idFood: "2",
            name: "餐品2",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "600",
            sellOut: "1"
          },
          {
            idFood: "3",
            name: "餐品3",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "500",
            sellOut: "1"
          },
          {
            idFood: "4",
            name: "餐品4",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "500",
            sellOut: "1"
          },
          {
            idFood: "5",
            name: "餐品5",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "100",
            sellOut: "1"
          },
          {
            idFood: "6",
            name: "餐品6",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "200",
            sellOut: "1"
          },
          {
            idFood: "7",
            name: "餐品7",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "600",
            sellOut: "1"
          },
          {
            idFood: "8",
            name: "餐品8",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "700",
            sellOut: "1"
          },
          {
            idFood: "9",
            name: "餐品9",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "500",
            sellOut: "1"
          },
          {
            idFood: "10",
            name: "餐品10",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "100",
            sellOut: "1"
          },
          {
            idFood: "11",
            name: "餐品11",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "700",
            sellOut: "1"
          },
          {
            idFood: "12",
            name: "餐品12",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "700",
            sellOut: "1"
          },
          {
            idFood: "13",
            name: "餐品13",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "700",
            sellOut: "1"
          },
          {
            idFood: "14",
            name: "餐品14",
            type: "3",
            price: "4",
            url:
              "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
            num: "700",
            sellOut: "1"
          }
        ],
        /* 编辑 页面样式 */
        EditForm: {
          idShop: "",
          idFood: "",
          num: "",
        }
      };
    },
    /* 初始化 */
    created() {
      this.getList();
    },
    methods: {
      /* 获得列表 */
      async getList() {
        if (this.name === "") {
          this.name = "null";
        }
        // this.params.name = this.name;
        // this.params.shopid = window.sessionStorage.getItem("username");
        this.shopid=window.sessionStorage.getItem("username");
        const { data: res } = await this.$http.post(
          "getFoodNotInShop",
          this.shopid
        );
        //设置列表数据
        this.tableData = res;
        this.handleSizeChange(this.pageSize); // 更新分页 界面
      },
      /* 查询 */
      Search() {
        this.getList();
        this.totalSize = this.tableData.length; // 更新总条数
        this.handleSizeChange(this.pageSize); // 更新分页 界面
      },
      /* 分页更新功能 */
      handleSizeChange(val) {
        this.pageSize = val;
        this.handleCurrentChange(this.currentPage);
      },
      /* 更新 */
      handleCurrentChange(val) {
        //   this.totalSize = this.tableData.length;   // 更新总条数，用于死数据
        this.currentPage = val;
        let from = (this.currentPage - 1) * this.pageSize;
        let to = this.currentPage * this.pageSize;
        this.tempList = [];
        for (; from < to; from++) {
          if (this.tableData[from]) {
            this.tempList.push(this.tableData[from]);
          }
        }
      },
      // 上架商品
      handleClick(item) {
        console.log("this");
        this.EditForm.idShop = window.sessionStorage.getItem("username");
        this.EditForm.idFood = item.idFood;
        this.EditForm.num = item.num;
        this.EditFormVisible = true;
      },
      buyFood() {
        this.$refs.EditFormRef.validate(async valid => {
          if (valid) {
            console.log("post");
            console.log(this.EditForm);
            const { data: res } = await this.$http.post(
              "addfoodinshop",
              this.EditForm
            );
            console.log(this.EditForm);
            if (res === "ok") {
              console.log("ok");
              this.EditFormVisible = false;
              await this.getList();
              this.$message({
                type: "success",
                message: "进货成功"
              });
            } else {
              this.$message.error("进货失败");
            }
          } else {
            return false;
          }
        });
      }
    }
  };
  </script>
  
  <style lang="less" scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .avatar-uploader-icon {
    font-size: 100px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
  .image {
    width: 210px;
    height: 235px;
    align-content: center;
  }
  </style>