<template>
    <el-table :data="tableData" border stripe :header-cell-class-name="headBg" style="width: 100%; margin-top: 6.8%;">
        <!-- <el-table-column prop="id" label="id" width="80"></el-table-column> -->
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="studentid" label="学号">
        </el-table-column>
        <el-table-column prop="classid" label="班级号">
        </el-table-column>

        <el-table-column prop=" " label=" " width="165">
            <template slot-scope="scope">
                <el-button type="success" @click="edit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                <el-popconfirm class="ml-5" confirm-button-text='好的' cancel-button-text='我再想想' icon="el-icon-info"
                    icon-color="red" title="你确定删除吗？" @confirm="del(scope.row)">
                    <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"></i></el-button>
                </el-popconfirm>
            </template>
        </el-table-column>

    </el-table>
</template>

<script>
import courseapi from '@/api/page/course'
import router from '@/router'
export default {
    data() {
        return {
            headBg: 'headBg',
            tableData: [],
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            courseapi.getall().then(res => {
                this.tableData = res.data
            })
        },
        edit(n) {
            router.push({
                name: "添加无课课表",
                query: n
            })
        },
        del(n) {
            courseapi.del(n).then(res => {
                if (res.code === '200') {
                    this.$message.success("保存成功")
                    this.load()
                } else {
                    this.$message.error(res.msg)
                }
            })
        }
    }
}

</script>

<style scoped></style>