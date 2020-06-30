<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>门店管理</el-breadcrumb-item>
            <el-breadcrumb-item>账号管理</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <!-- 搜索栏 新建按钮-->
        <el-form class="user-search" :inline="true" label-width="90px">
            <el-form-item prop="store" label="门店ID: ">
                <el-input v-model="value" placeholder="请输入门店ID" clearable></el-input>
            </el-form-item>
            <el-form-item label="">
                <el-button size="small" type="primary"  icon="iconfont icon-chazhao" class="title" round @click="Search()"> 查询门店 </el-button>
            </el-form-item>
            <el-form-item label="">
                <el-button size="small" type="primary" icon="iconfont icon-tianjiaxindeyangbenhe" round @click="handleEdit()"> 添加门店</el-button>
            </el-form-item>
        </el-form>

        <!-- 列表 -->
        <el-table :data="tempList":fit="true" :show-header="true"
                  :default-sort = "{prop: 'storeID', order: 'descending'}" border="true" max-height="430">
            <el-table-column prop="storeID" label="门店账号" align="center">
            </el-table-column>
            <el-table-column prop="password" label="门店密码" align="center">
            </el-table-column>
            <el-table-column prop="name" label="门店名称" align="center">
            </el-table-column>
            <el-table-column prop="address" label="门店地址" align="center">
            </el-table-column>
            <el-table-column prop="tel" label="联系方式" align="center">
            </el-table-column>
            <el-table-column label="操作" fixed="right" align="center" width="200px">
                <template slot-scope="scope">
                    <el-button mc-type="column-el-button" size="mini" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button mc-type="column-el-button" size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <br/>
        <br/>

        <!-- 分页 -->
        <div class="block" align="center">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 15, 20, 25]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total= totalSize>
            </el-pagination>
        </div>

        <!-- 编辑界面 -->
        <el-dialog :title="title" :visible.sync="editFormVisible" width="30%">
            <el-form label-width="80px" :model="editForm" :rules="rules" >
                <el-form-item label="门店账号" prop="storeID"  required>
                    <el-input size="small" v-model="editForm.storeID" auto-complete="off" prefix-icon="iconfont icon-denglu" placeholder="请输入门店账号"></el-input>
                </el-form-item>
                <el-form-item label="门店密码" prop="password"  required>
                    <el-input size="small" v-model="editForm.password" auto-complete="off" prefix-icon="iconfont icon-mima" placeholder="请输入门店密码"></el-input>
                </el-form-item>
                <el-form-item label="门店名称" prop="name"  required>
                    <el-input size="small" v-model="editForm.name" prefix-icon="iconfont icon-mendian" placeholder="请输入门店名称"></el-input>
                </el-form-item>
                <el-form-item label="门店地址" prop="address"  required>
                    <el-input size="small" v-model="editForm.address" prefix-icon="iconfont icon-dizhi" placeholder="请输入门店地址"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="tel"  required>
                    <el-input size="small" v-model="editForm.tel" prefix-icon="iconfont icon-lianxifangshi" placeholder="请输入联系方式"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="small" @click='closeDialog()'>取消</el-button>
                <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default
    {
        data()
        {
            return{

                /* 暂时填充用户列表 */
                UserList:
                    [
                        {
                            storeID: 'halo7',
                            password: '7777777',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                        {
                            storeID: 'halo6',
                            password: '6666666',
                            name: '福建',
                            address: 'fujian',
                            tel: '123456',
                        },
                    ],

                /* 分页 */
                pageSize:10,
                currentPage:1,
                totalSize:20,   // 总条数，用于死数据
                tempList: [],   // 用于死数据

                /* 编辑页面样式 */
                editForm: {
                    storeID: '',
                    password: '',
                    name: '',
                    address: '',
                    tel: '',
                },

                editFormVisible:false,   // 控制编辑页面显示与隐藏
                title:"添加门店",

                /* rules表单验证 */
                rules: {
                    storeID: [
                        { required: true, message: '请输入门店账号', trigger: 'blur' },
                        { pattern: /^S[A-Za-z0-9]+/, required: true, message: '请输入正确门店账号(以S开头)', trigger: 'blur'},
                        { required: true, message: '请输入门店账号', trigger: 'change' },
                        { pattern: /^S[A-Za-z0-9]+/, required: true, message: '请输入正确门店账号(以S开头)', trigger: 'change'},
                    ],
                    password: [
                        { required: true, message: '请输入门店密码', trigger: 'blur' },
                        { required: true, message: '请输入门店密码', trigger: 'change' },
                    ],
                    name: [
                        { required: true, message: '请输入门店名称', trigger: 'blur' },
                        { required: true, message: '请输入门店名称', trigger: 'change' },
                    ],
                    address: [
                        { required: true, message: '请输入门店地址', trigger: 'blur' },
                        { required: true, message: '请输入门店地址', trigger: 'change' },
                    ],
                    tel: [
                        { required: true, message: '请输入联系方式', trigger: 'blur' },
                        { required: true, message: '请输入联系方式', trigger: 'change' },
                    ],
                },
            }
        },

        methods: {
            /* TO DO:查询 */
            Search()
            {

            },

            /* 分页更新功能 */
            handleSizeChange(val)
            {
                this.pageSize = val;
                this.handleCurrentChange(this.currentPage);
            },

            /* 分页与排序不可兼得 */
            handleCurrentChange(val)
            {
                this.totalSize = this.UserList.length;   // 更新总条数，用于死数据
                this.currentPage = val;
                let from = (this.currentPage - 1) * this.pageSize;
                let to = this.currentPage * this.pageSize;
                this.tempList = [];
                for (; from < to; from++)
                {
                    if (this.UserList[from])
                    {
                        this.tempList.push(this.UserList[from]);
                    }
                }
            },

            /* 显示编辑界面 */
            handleEdit: function(index, row)
            {
                this.editFormVisible = true
                if (row !== undefined && row !== 'undefined')
                {
                    this.title = '修改门店'
                    this.editForm.storeID = row.storeID;
                    this.editForm.password = row.password;
                    this.editForm.name = row.name;
                    this.editForm.address = row.address;
                    this.editForm.tel = row.tel;
                }
                else
                {
                    this.title = '添加门店'
                    this.editForm.storeID = '';
                    this.editForm.password = '';
                    this.editForm.name = '';
                    this.editForm.address = '';
                    this.editForm.tel = '';
                }

            },

            /* 关闭编辑栏 */
            closeDialog()
            {
                this.editFormVisible = false
            },

            // 编辑、添加提交方法
            submitForm(editData) {
                this.$refs[editData].validate(valid => {
                    if (valid) {
                        // 请求方法
                        userSave(this.editForm)
                            .then(res => {
                                this.editFormVisible = false
                                this.loading = false
                                if (res.success)
                                {
                                    this.getdata(this.formInline)
                                    this.$message({
                                        type: 'success',
                                        message: '数据保存成功！'
                                    })
                                }
                                else
                                {
                                    this.$message({
                                        type: 'info',
                                        message: res.msg
                                    })
                                }
                            })
                            .catch(err => {
                                this.editFormVisible = false
                                this.loading = false
                                this.$message.error('保存失败，请稍后再试！')
                            })
                    }
                    else
                    {
                        return false
                    }
                })
            },

            // 删除用户
            deleteUser(index, row) {
                this.$confirm('确定要删除吗?', '信息', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(() => {
                        // 删除
                        userDelete(row.id)
                            .then(res => {
                                if (res.success)
                                {
                                    this.$message({
                                        type: 'success',
                                        message: '数据已删除!'
                                    })
                                    this.getdata(this.formInline)
                                }
                                else
                                {
                                    this.$message({
                                        type: 'info',
                                        message: res.msg
                                    })
                                }
                            })
                            .catch(err => {
                                this.loading = false
                                this.$message.error('数据删除失败，请稍后再试！')
                            })
                    })
                    .catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除！'
                        })
                    })
            },
        }
    }
</script>

<style lang="less" scoped>

</style>