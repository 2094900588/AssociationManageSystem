<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入学号"
                v-model="studentid"></el-input>
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入姓名"
                v-model="amname"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" class="ml-5" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i>
            </el-button>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg">
            <!-- <el-table-column prop="id" label="id" width="80"></el-table-column> -->
            <el-table-column type="index" width="50" :index="getindex"></el-table-column>
            <el-table-column prop="studentid" label="社员学号" width="140">
            </el-table-column>
            <el-table-column prop="amname" label="社员姓名" width="120">
            </el-table-column>
            <el-table-column prop="phone" label="社员电话" width="150">
            </el-table-column>
            <el-table-column prop="status" label="政治面貌" width="150">
            </el-table-column>
            <el-table-column prop="intotime" label="加入社团时间" width="150">
            </el-table-column>
            <!-- <el-table-column prop="integral" label="社团积分" width="150">
            </el-table-column> -->
            <el-table-column label="角色" width="150">
                <template slot-scope="scope">
                    <span>{{ getrole(scope.row.roleid) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="所属社团" width="150">
                <template slot-scope="scope">
                    <span>{{ getclub(scope.row.clubid) }}</span>
                </template>
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
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>

            <el-dialog title="社员信息" :visible.sync="dialogFormVisible" width="30%">

                <el-form label-width="100px" size="small">
                    <el-form-item label="社员学号">
                        <el-input v-model="form.studentid" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社员姓名">
                        <el-input v-model="form.amname" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社员电话">
                        <el-input v-model="form.phone" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="政治面貌">
                        <el-input v-model="form.status" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="加入社团时间">
                        <el-date-picker v-model="form.intotime" type="date" placeholder="选择日期">
                        </el-date-picker>
                        <!-- <el-input v-model="form.clubtime" type="text" autocomplete="off"></el-input> -->
                    </el-form-item>

                    <el-form-item label="角色">
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
                    <el-button type="primary" @click="save">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import personapi from '@/api/page/person'
import roleapi from '@/api/page/role'
import clubapi from '@/api/page/club'
export default {
    name: "Person",
    data() {
        return {
            tableData: [],
            roles: [],
            clubs: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            studentid: "",
            amname: "",
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
        getindex(index) {
            return this.pageSize * (this.pageNum - 1) + index + 1
        },
        getrole(id) {
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
                studentid: this.studentid,
                amname: this.amname,
            }
            personapi.getPage(params).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
            roleapi.getall().then(res => {
                this.roles = res.data
            })
            clubapi.getall().then(res => {
                this.clubs = res.data
            })

        },
        reset() {
            this.studentid = "",
                this.amname = "",
                this.load()
        },
        save() {
            personapi.save(this.form).then(res => {
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
            personapi.delete(id).then(res => {
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