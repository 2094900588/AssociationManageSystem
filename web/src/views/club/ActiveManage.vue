<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入活动名称"
                v-model="optionname"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" class="ml-5" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i>
            </el-button>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg" style="width: 100%; margin-top: 30px;">
            <!-- <el-table-column type="selection" width="50"></el-table-column> -->
            <!-- <el-table-column prop="id" label="id" width="80">
            </el-table-column> -->
            <el-table-column type="index" width="50" :index="getindex"></el-table-column>
            <el-table-column prop="optionname" label="活动名称">
            </el-table-column>
            <el-table-column prop="optiondesc" label="活动描述">
            </el-table-column>
            <el-table-column prop="optionaim" label="活动目的">
            </el-table-column>
            <el-table-column prop="optionnum" label="活动参与人数">
            </el-table-column>
            <el-table-column prop="optionnum" label="活动成绩表">
            </el-table-column>
            <el-table-column prop="optionnum" label="活动是否评分">
            </el-table-column>
            <el-table-column prop="optionnum" label="活动评分">
            </el-table-column>
            <el-table-column prop="optiondate" label="活动时间">
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
            <el-dialog title="活动信息" :visible.sync="dialogFormVisible" width="30%">
                <el-form label-width="100px" size="small">
                    <el-form-item label="活动名称">
                        <el-input v-model="form.optionname" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="活动描述">
                        <el-input v-model="form.optiondesc" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="活动目的">
                        <el-input v-model="form.optionaim" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="活动参与人数">
                        <el-input v-model="form.optionnum" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="活动成绩表">
                        <el-input v-model="form.optionfile" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="活动是否评分">
                        <el-input v-model="form.optiongrade" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="活动评分">
                        <el-input v-model="form.optiongrade" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="活动时间">
                        <el-date-picker v-model="form.optiondate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
                        </el-date-picker>
                        <!-- <el-input v-model="form.clubtime" type="text" autocomplete="off"></el-input> -->
                    </el-form-item>
                    <el-form-item label="角色" v-if="user.sysroleid !== 3">
                        <!-- <el-input v-model="form.address" type="text" autocomplete="off"></el-input> -->
                        <el-select v-model="form.roleid" filterable placeholder="请选择">
                            <el-option v-for="item in roles" :key="item.id" :label="item.rolename" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属社团" v-if="user.sysroleid !== 3">
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
import activeapi from '@/api/page/active'
import roleapi from '@/api/page/role'
import clubapi from '@/api/page/club'
import { mapState } from 'vuex'
export default {
    name: "Active",
    data() {
        return {
            tableData: [],
            roles: [],
            clubs: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            optionname: "",
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
                optionname: this.optionname,
            }
            console.log(params);
            activeapi.getPage(params).then(res => {
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
            this.optionname = "",
                this.load()
        },
        save() {
            activeapi.save(this.form).then(res => {
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
            activeapi.delete(id).then(res => {
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