<template>
    <div>
        <div style="margin: 10px 0;margin-top: 4.3%;">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入学号"
                v-model="studentid"></el-input>
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入姓名"
                v-model="amname"></el-input>
            <el-select v-model="status" placeholder="请选择">
                <el-option v-for="item in ['党员', '预备党员', '共青团员', '群众', '']" :key="item" :label="item" :value="item">
                </el-option>
            </el-select>

            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg" style="width: 100%;">
            <el-table-column type="index" width="50" :index="getindex"></el-table-column>
            <el-table-column prop="studentid" label="社员学号">
            </el-table-column>
            <el-table-column prop="classid" label="社员班号">
            </el-table-column>
            <el-table-column prop="amname" label="社员姓名">
            </el-table-column>
            <el-table-column prop="phone" label="社员电话">
            </el-table-column>
            <el-table-column prop="status" label="政治面貌">
            </el-table-column>
            <el-table-column prop="intotime" label="加入社团时间">
            </el-table-column>
            <el-table-column label="角色">
                <template slot-scope="scope">
                    <span>{{ getrole(scope.row.roleid) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="所属社团">
                <template slot-scope="scope">
                    <span>{{ getclub(scope.row.clubid) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop=" " label=" " width="180">
                <template slot-scope="scope">
                    <!-- <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                    <el-popconfirm class="ml-5" confirm-button-text='好的' cancel-button-text='我再想想' icon="el-icon-info"
                        icon-color="red" title="你确定删除吗？" @confirm="handleDel(scope.row.id)">
                        <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm> -->
                    <el-button type="success" @click="up(scope.row)" v-if="scope.row.roleid !== 0">升职<i
                            class="el-icon-edit"></i></el-button>
                    <el-button type="danger" @click="down(scope.row)" v-if="scope.row.roleid !== 4">降职<i
                            class="el-icon-edit"></i></el-button>
                </template>
            </el-table-column>

        </el-table>

        <div style="padding: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
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
            status: "",
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
                status: this.status,
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
                this.status = ""
            this.load()
        },
        up(row) {
            row.roleid--;
            personapi.save(row).then(res => {
                if (res.code === '200') {
                    this.$message.success("升职成功"),
                        this.load()
                } else {
                    this.$message.error("失败")
                }
            })
        }, down(row) {
            row.roleid++;
            personapi.save(row).then(res => {
                if (res.code === '200') {
                    this.$message.success("降职成功"),
                        this.load()
                } else {
                    this.$message.error("失败")
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