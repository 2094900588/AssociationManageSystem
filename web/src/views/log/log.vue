<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入名称"
                v-model="rolename"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headBg" style="width: 100%; margin-top: 60px;">
            <el-table-column prop="id" label="id">
            </el-table-column>
            <el-table-column prop="operatename" label="操作名称">
            </el-table-column>
            <el-table-column prop="username" label="操作人员名称">
            </el-table-column>
            <el-table-column prop="rolename" label="操作人员角色名称">
            </el-table-column>
            <el-table-column prop="clubname" label="操作人员社团名称">
            </el-table-column>
            <el-table-column prop="operatetime" label="操作时间">
            </el-table-column>
            <el-table-column prop=" " label=" " width="165">
                <template slot-scope="scope">
                    <!-- <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button> -->
                    <div v-if="scope.row.cloperate === 0">
                        <el-popconfirm class="ml-5" confirm-button-text='好的' cancel-button-text='我再想想' icon="el-icon-info"
                            icon-color="red" title="你确定撤销操作吗？" @confirm="revoke(scope.row.id)">
                            <el-button slot="reference" type="danger">撤销<i class="el-icon-remove-outline"></i></el-button>
                        </el-popconfirm>
                    </div>
                    <div v-else>
                        已撤销
                    </div>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>

<script>
import logapi from '@/api/page/log'
export default {
    name: "log",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            rolename: "",
            nickname: "",
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
                rolename: this.rolename,
            }
            logapi.getPage(params).then(res => {
                console.log(res);
                this.tableData = res.data.records
                this.total = res.data.total
            })

        },
        reset() {
            this.rolename = "",
                this.load()
        },
        revoke(id) {
            logapi.revoke(id).then(res => {
                if (res.code === '200') {
                    this.$message.success("已撤销"),
                        this.load()
                } else {
                    this.$message.error("撤销失败")
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