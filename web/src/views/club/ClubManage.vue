<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入名称"
                v-model="clubname"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" class="ml-5" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i>
            </el-button>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg">
            <el-table-column type="selection" width="50">
            </el-table-column>
            <el-table-column prop="id" label="id" width="80">
            </el-table-column>
            <el-table-column prop="clubname" label="社团名称" width="140">
            </el-table-column>
            <el-table-column prop="clubdesc" label="社团描述" width="120">
            </el-table-column>
            <el-table-column prop="clubfounder" label="社团创始人" width="150">
            </el-table-column>
            <el-table-column prop="clubtime" label="社团创建时间" width="150">
            </el-table-column>
            <el-table-column prop="clubphoto" label="社团照片" width="150">
            </el-table-column>
            <el-table-column prop="integral" label="社团积分" width="150">
            </el-table-column>
            <el-table-column prop=" " label=" " width="230">
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
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNum" :page-sizes="[2, 5, 10, 20]"
                :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>

            <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">

                <el-form label-width="100px" size="small">
                    <el-form-item label="社团名称">
                        <el-input v-model="form.clubname" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社团描述">
                        <el-input v-model="form.clubdesc" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社团创始人">
                        <el-input v-model="form.clubfounder" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社团创建时间">
                        <el-date-picker v-model="form.clubtime" type="date" placeholder="选择日期">
                        </el-date-picker>
                        <!-- <el-input v-model="form.clubtime" type="text" autocomplete="off"></el-input> -->
                    </el-form-item>
                    <el-form-item label="社团照片">
                        <el-input v-model="form.clubphoto" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社团积分">
                        <el-input v-model="form.integral" type="text" autocomplete="off"></el-input>
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
import clubapi from '@/api/page/club'
export default {
    name: "User",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            clubname: "",
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            headBg: 'headBg'
        }
    },
    created() {
        this.load()
    },
    methods: {
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
                clubname: this.clubname,
            }
            console.log(params);
            clubapi.getPage(params).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })

        },
        reset() {
            this.clubname = "",
                this.load()
        },
        saveUser() {
            clubapi.save(this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success("保存成功"),
                        this.dialogFormVisible = false,
                        this.load()
                } else {
                    this.$message.error("保存失败")
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
            clubapi.delete(id).then(res => {
                if (res.code === '200') {
                    this.$message.success("删除成功"),
                        this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        }
    }
}
</script>
<style>
.headBg {
    background: lightblue !important
}
</style>