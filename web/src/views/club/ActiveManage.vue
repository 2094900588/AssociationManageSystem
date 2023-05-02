<template>
    <div>
        <div style="margin: 10px 0;margin-top: 2.3%;">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入活动名称"
                v-model="optionname"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" class="ml-5" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i>
            </el-button>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg" style="width: 100%;">
            <el-table-column type="index" width="50" :index="getindex"></el-table-column>
            <el-table-column prop="optionname" label="活动名称">
            </el-table-column>
            <el-table-column prop="optiondesc" label="活动描述">
            </el-table-column>
            <el-table-column prop="optionaim" label="活动目的">
            </el-table-column>
            <el-table-column prop="optionnum" label="活动参与人数">
            </el-table-column>
            <el-table-column label="活动是否评分">
                <template slot-scope="scope">
                    <div v-if="scope.row.isgrade !=0">
                        {{ scope.row.optiongrade }}
                    </div>
                    <div v-else>
                        <el-button type="primary" @click="handleEdittwo(scope.row)">评分</el-button>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="optiondate" label="活动时间">
            </el-table-column>
            <el-table-column label="活动是否审批">
                <template slot-scope="scope">
                    <div v-if="scope.row.ispass !=0 || scope.row.ispass ==-1">
                        {{ scope.row.notes }}
                    </div>
                    <div v-else>
                        <el-button type="primary" @click="handleEditpass(scope.row)">审批</el-button>
                    </div>
                </template>
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
            <el-table-column label="活动成绩表">
                <template slot-scope="scope">
                    <div v-if="scope.row.optionfile != null">
                        <a :href="scope.row.optionfile" target="_blank">点击下载</a>
                    </div>
                    <div v-else>
                        暂无
                    </div>
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
                        <el-upload class="upload-demo" action="#" :show-file-list="true" :auto-upload="false"
                            :on-change="handleChange" :file-list="fileList">
                            <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="活动时间">
                        <el-date-picker v-model="form.optiondate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="角色" v-if="user.sysroleid !== 3">
                        <el-select v-model="form.roleid" filterable placeholder="请选择">
                            <el-option v-for=" item  in  roles " :key="item.id" :label="item.rolename" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属社团" v-if="user.sysroleid !== 3">
                        <el-select v-model="form.clubid" filterable placeholder="请选择">
                            <el-option v-for=" item  in  clubs " :key="item.id" :label="item.clubname" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="活动信息" :visible.sync="dialogFormVisibletwo" width="30%">
                <el-form label-width="150px" size="small">
                    <el-form-item label="请对该活动进行评分">
                        <el-input v-model="form.optiongrade" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisibletwo = false">取 消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="活动审批" :visible.sync="dialogFormVisiblepass" width="30%">
                <el-form label-width="150px" size="small">
                    <el-form-item label="审批活动">
                        <el-input v-model="form.notes" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="是否通过">
                        <template>
                         <el-radio v-model="radio" :label="1">通过</el-radio>
                        <el-radio v-model="radio" :label="-1">不通过</el-radio>
                    </template>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisibletwo = false">取 消</el-button>
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
import fileapi from '@/api/page/file'
import { mapState } from 'vuex'
export default {
    name: "Active",
    data() {
        return {
            tableData: [],
            fileList: [],
            roles: [],
            clubs: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            optionname: "",
            dialogFormVisible: false,
            dialogFormVisibletwo: false,
            dialogFormVisiblepass: false,
            form: {},
            multipleSelection: [],
            headBg: 'headBg',
            radio: 0
        }
    },
    created() {
        this.load()
    },
    computed: {
        ...mapState(['user'])
    },
    methods: {
        handleChange(file, fileList) {
            if (fileList.length > 0) {
                this.fileList = [fileList[fileList.length - 1]]
            }
        },
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
        async upload() {
            if (JSON.stringify(this.fileList) == '[]') return

            let fromdata = new FormData()
            fromdata.append("file", this.fileList[0].raw)
            fileapi.upload_annex(fromdata).then(res => {
                if (res.code === '200') {
                    this.form.optionfile = res.data.url
                    return true
                } else {
                    this.$message.error(res.msg)
                    return false
                }
            })
        },
        save() {
            if(this.radio == -1){
                this.form.ispass = -1
            }
            if(this.form.notes != null && this.radio !=-1){
                this.form.ispass = 1
            }
            if(this.form.optiongrade != null){
                this.form.isgrade = 1
            }
            if (JSON.stringify(this.fileList) == '[]') {
                activeapi.save(this.form).then(res => {
                    if (res.code === '200') {
                        this.$message.success("保存成功"),
                            this.dialogFormVisible = false,
                            this.dialogFormVisibletwo = false,
                            this.dialogFormVisiblepass = false,
                            this.load()
                    } else {
                        this.$message.error("保存失败")
                    }
                })
            } else {
                let fromdata = new FormData()
                fromdata.append("file", this.fileList[0].raw)
                fileapi.upload_annex(fromdata).then(res => {
                    if (res.code === '200') {
                        this.form.optionfile = res.data.url
                        activeapi.save(this.form).then(res => {
                            if (res.code === '200') {
                                this.$message.success("保存成功"),
                                    this.dialogFormVisible = false,
                                    this.load()
                            } else {
                                this.$message.error("保存失败")
                            }
                        })
                    } else {
                        this.$message.error(res.msg)
                        return false
                    }
                })
                this.fileList = []
            }

        },
        //审批弹窗
        handleEditpass(row){
            this.form = row,
            this.dialogFormVisiblepass = true
        },
        // 评分弹窗
        handleEdittwo(row) {
            this.form = row,
                this.dialogFormVisibletwo = true
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