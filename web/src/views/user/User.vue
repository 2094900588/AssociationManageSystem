<template>
    <div>
        <div style="margin: 10px 0;margin-top: 2.3%;">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入名称"
                v-model="username"></el-input>
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-message" placeholder="请输入邮箱" v-model="email"
                class="ml-5"></el-input>
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-position" placeholder="请输入地址"
                v-model="address" class="ml-5"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" class="ml-5" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i>
            </el-button>
            <!--            二次确认按钮-->
            <el-popconfirm class="ml-5" confirm-button-text='好的' cancel-button-text='我再想想' icon="el-icon-info"
                icon-color="red" title="你确定删除吗？" @confirm="delBatch">
                <el-button type="danger" slot="reference" class="ml-5">批量删除<i class="el-icon-remove-outline"></i>
                </el-button>
            </el-popconfirm>
            <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx"
                :on-success="handleExcelImportSuccess" style="display: inline-block">
                <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i> </el-button>
            </el-upload>
            <el-button type="primary" class="ml-5" @click="exp">导出<i class="el-icon-top"></i> </el-button>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg" @selection-change="handleSelectionChange"
            style="width: 100%;">
            <el-table-column type="selection" width="50">
            </el-table-column>
            <!-- <el-table-column prop="id" label="id" width="80"></el-table-column> -->
            <el-table-column type="index" width="50" :index="getindex"></el-table-column>
            <el-table-column prop="username" label="用户名">
            </el-table-column>
            <el-table-column prop="nickname" label="昵称">
            </el-table-column>
            <el-table-column prop="email" label="邮箱地址">
            </el-table-column>
            <el-table-column prop="phone" label="电话">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column prop="name" label="真实姓名">
            </el-table-column>
            <el-table-column label="角色">
                <template slot-scope="scope">
                    <span>{{ getrole(scope.row.roleid, scope.row.sysroleid) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="所属社团">
                <template slot-scope="scope">
                    <span>{{ getclub(scope.row.clubid) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="studentid" label="学号">
            </el-table-column>
            <el-table-column prop="" label="头像">
                <template slot-scope="scope">
                    <img v-if="scope.row.userphoto" :src="scope.row.userphoto"
                        style="height: 30px;width: 30px; border-radius: 50%;">
                    <span v-else>暂无</span>
                </template>
            </el-table-column>

            <el-table-column prop=" " label=" " width="165">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                    <el-popconfirm class="ml-5" confirm-button-text='好的' cancel-button-text='我再想想' icon="el-icon-info"
                        icon-color="red" title="你确定删除吗？" @confirm="handleDel(scope.row.id)">
                        <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>

        </el-table>

        <div style="padding: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>

            <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">

                <el-form label-width="70px" size="small">
                    <el-form-item label="用户名">
                        <el-input v-model="form.username" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="form.password" type="password" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="昵称">
                        <el-input v-model="form.nickname" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱地址">
                        <el-input v-model="form.email" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="电话">
                        <el-input v-model="form.phone" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="地址">
                        <el-input v-model="form.address" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="学号">
                        <el-input v-model="form.studentid" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="真实姓名">
                        <el-input v-model="form.name" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="头像">
                        <el-upload class="upload-demo" ref="upload" action="#" :show-file-list="false"
                            :on-change="handleChange" :auto-upload="false" list-type="picture-card">
                            <!-- <i class="el-icon-plus"></i> -->
                            <img v-if="form.userphoto" :src="form.userphoto" class="avatar" width="148" height="148">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="角色" v-if="user.sysroleid === 0 || user.sysroleid === 1">
                        <!-- <el-input v-model="form.address" type="text" autocomplete="off"></el-input> -->
                        <el-select v-model="form.sysroleid" filterable placeholder="请选择">
                            <el-option v-for="item in rolesys" :key="item.id" :label="item.sysrolename" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="从属角色" v-if="form.sysroleid === 3 || user.sysroleid === 2 || user.sysroleid === 3">
                        <!-- <el-input v-model="form.address" type="text" autocomplete="off"></el-input> -->
                        <el-select v-model="form.roleid" filterable placeholder="请选择">
                            <el-option v-for="item in roles" :key="item.id" :label="item.rolename" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属社团">
                        <!-- <el-input v-model="form.address" type="text" autocomplete="off"></el-input> -->
                        <el-select v-model="form.clubid" filterable placeholder="请选择">
                            <el-option v-for="item in clubs" :key="item.id" :label="item.clubname" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveUser">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import { mapState } from 'vuex'
import userapi from '@/api/page/user'
import roleapi from '@/api/page/role'
import clubapi from '@/api/page/club'
import fileapi from '@/api/page/file'
export default {
    name: "User",
    data() {
        return {
            file: null,
            tableData: [],
            roles: [],
            clubs: [],
            rolesys: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            username: "",
            nickname: "",
            email: "",
            address: "",
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            headBg: 'headBg'
        }
    },
    created() {
        this.load()
    },
    computed: {
        ...mapState(['user'])
    },
    methods: {
        //上传文件让第二次覆盖第一的文件
        handleChange(file, fileList) {
            const isJPG = file.raw.type.indexOf("image") !== -1;
            const isLt2M = file.raw.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
                return
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
                return
            }
            this.form.userphoto = file.url
            this.file = file
        },
        getindex(index) {
            return this.pageSize * (this.pageNum - 1) + index + 1
        },
        getsysrole(id) {
            var t = this.rolesys.filter((item) => id == item.id)
            if (t.length === 1) return t[0].sysrolename;
            return id;
        },
        getrole(id, sysid) {
            if (sysid != null && sysid !== 3) return this.getsysrole(sysid);
            var t = this.roles.filter((item) => id == item.id)
            if (t.length === 1) return t[0].rolename;
            return id;
        },
        getclub(id) {
            var t = this.clubs.filter((item) => id == item.id)
            if (t.length === 1) return t[0].clubname;
            return id;
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum
            this.load()
        },
        load() {
            //请求分页查询数据
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                username: this.username,
                email: this.email,
                address: this.address,
            }
            userapi.getPage(params).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
            roleapi.getall().then(res => {
                this.roles = res.data
            })
            roleapi.getSysall().then(res => {
                this.rolesys = res.data
            })
            clubapi.getall().then(res => {
                this.clubs = res.data
            })
        },
        reset() {
            this.username = "",
                this.email = "",
                this.address = "",
                this.load()
        },
        saveUser() {
            let fromdata = new FormData()
            fromdata.append("file", this.file.raw)
            fileapi.upload_photo(fromdata).then(res => {
                if (res.code === '200') {
                    this.form.userphoto = res.data.url
                    userapi.save(this.form).then(res => {
                        if (res.code === '200') {
                            this.$message.success("保存成功"),
                                this.dialogFormVisible = false,
                                this.load()
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                } else {
                    this.$message.error(res.msg)
                    return false
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true;
            this.form = {}
        },
        handleEdit(row) {
            this.form = row,
                this.dialogFormVisible = true
        },
        handleDel(id) {
            userapi.delete(id).then(res => {
                if (res.code === '200') {
                    this.$message.success("删除成功"),
                        this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        },
        //导出用户信息
        exp() {
            window.open("http://localhost:9090/user/export")
        },
        //文件导入成功提示
        handleExcelImportSuccess() {
            this.$message.success("导入成功");
            this.load();
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id) //[{},{},{},{}]   ==>  1,2,3
            userapi.multidelete(ids).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功"),
                        this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            // console.log(val),
            this.multipleSelection = val
        }
    }
}
</script>
<style>
.headBg {
    background: lightblue !important
}
</style>