<template>
    <div>
        <div style="margin: 10px 0;margin-top: 2.3%;">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入名称"
                v-model="clubname"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" class="ml-5" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i>
            </el-button>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg" style="width: 100%;">
            <el-table-column type="index" width="50" :index="getindex"></el-table-column>
            <el-table-column prop="clubname" label="社团名称">
            </el-table-column>
            <el-table-column prop="clubdesc" label="社团描述">
            </el-table-column>
            <el-table-column prop="clubfounder" label="社团创始人">
            </el-table-column>
            <el-table-column prop="clubtime" label="社团创建时间">
            </el-table-column>
            <el-table-column prop="" label="社团照片">
                <template slot-scope="scope">
                    <img v-if="scope.row.clubphoto" :src="scope.row.clubphoto"
                        style="height: 30px;width: 30px; border-radius: 50%;">
                    <span v-else>暂无</span>
                </template>
            </el-table-column>
            <el-table-column prop="integral" label="社团积分">
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

            <el-dialog title="社团信息" :visible.sync="dialogFormVisible" width="30%">

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
                    <el-form-item label="社团照片">
                        <el-upload class="upload-demo" ref="upload" action="#" :show-file-list="false"
                            :on-change="handleChange" :auto-upload="false" list-type="picture-card">
                            <img v-if="form.clubphoto" :src="form.clubphoto" class="avatar" width="148" height="148">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
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
import clubapi from '@/api/page/club'
import fileapi from '@/api/page/file'
export default {
    name: "ClubManage",
    data() {
        return {
            tableData: [],
            file: null,
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
            this.form.clubphoto = file.url
            this.file = file
        },
        getindex(index) {
            return this.pageSize * (this.pageNum - 1) + index + 1
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
                clubname: this.clubname,
            }
            clubapi.getPage(params).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
                for (var i = 0; i < this.tableData.length; i++)
                    if (this.tableData[i].integral == null) this.tableData[i].integral = 0
            })

        },
        reset() {
            this.clubname = "",
                this.load()
        },
        save() {
            if (this.file == null) {
                clubapi.save(this.form).then(res => {
                    if (res.code === '200') {
                        this.$message.success("保存成功"),
                            this.dialogFormVisible = false,
                            this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            } else {
                let fromdata = new FormData()
                fromdata.append("file", this.file.raw)
                fileapi.upload_photo(fromdata).then(res => {
                    if (res.code === '200') {
                        this.form.clubphoto = res.data.url
                        clubapi.save(this.form).then(res => {
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
            }

        },
        handleAdd() {
            this.dialogFormVisible = true;
            this.form = {
                clubphoto: ''
            }
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
                    // this.$message.error(res.msg)
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