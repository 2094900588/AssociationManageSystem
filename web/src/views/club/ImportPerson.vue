<template>
    <div>
        <div>
            <el-button type="primary" class="ml-5" @click="download" style="float: left;">下载模板表<i
                    class="el-icon-circle-plus-outline"></i>
            </el-button>
            <el-upload class="upload-demo" action="#" :show-file-list="false" :auto-upload="false"
                style="float: left;margin-left: 30px;" :on-change="handleChange">
                <el-button size="small" type="primary">导入社员名单</el-button>
            </el-upload>

        </div>
        <div>
            <el-table :data="tableData" border stripe :header-cell-class-name="headBg" style="width: 100%;">
                <el-table-column type="index" width="50"></el-table-column>
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
                <el-table-column prop="sex" label="性别">
                </el-table-column>
                <el-table-column prop="roleid" label="角色">
                </el-table-column>
                <el-table-column prop="clubid" label="所属社团">
                </el-table-column>
                <el-table-column prop=" " label=" " width="165">
                    <template slot-scope="scope">
                        <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                        <el-popconfirm class="ml-5" confirm-button-text='好的' cancel-button-text='我再想想' icon="el-icon-info"
                            icon-color="red" title="你确定删除吗？" @confirm="handleDel(scope.row)">
                            <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"></i></el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>

            </el-table>
            <el-button type="primary" class="ml-5" @click="handleAdd" v-if="tableData.length != 0">确认导入<i
                    class="el-icon-circle-plus-outline"></i>
            </el-button>
        </div>


        <div style="padding: 10px 0">

            <el-dialog title="社员信息" :visible.sync="dialogFormVisible" width="30%">
                <el-form label-width="100px" size="small">
                    <el-form-item label="社员学号">
                        <el-input v-model="form.studentid" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社员班号">
                        <el-input v-model="form.classid" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社员姓名">
                        <el-input v-model="form.amname" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="社员电话">
                        <el-input v-model="form.phone" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-input v-model="form.sex" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="政治面貌">
                        <el-input v-model="form.status" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="角色">
                        <el-input v-model="form.roleid" type="text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="所属社团" v-if="user.sysroleid !== 3">
                        <el-input v-model="form.clubid" type="text" autocomplete="off"></el-input>
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
import * as XLSX from 'xlsx'
import personapi from '@/api/page/person'
import roleapi from '@/api/page/role'
import clubapi from '@/api/page/club'
import { mapState } from 'vuex'
export default {
    name: "Person",
    data() {
        return {
            tableData: [],
            dialogFormVisible: false,
            headBg: 'headBg',
            form: {},
            tmp: {},
            roles: [],
            clubs: []
        }
    }, computed: {
        ...mapState(['user'])
    },
    created() {
        roleapi.getall().then(res => {
            for (var i of res.data) {
                this.roles[i["rolename"]] = i["id"]
            }
        })
        clubapi.getall().then(res => {
            for (var i of res.data) {
                this.roles[i["clubname"]] = i["id"]
            }
        })
    },
    methods: {

        download() {
            window.location.href = "/社团导入模板表.xlsx"
        },
        handleChange(file) {
            let filename = file.raw.name
            let suffix = filename.split(".")
            suffix = suffix[suffix.length - 1]
            if (suffix !== "xlsx") {
                this.$message.error("请上传xlsx文件！")
                return
            }
            var reader = new FileReader();
            reader.onload = (e) => {
                this.tableData = []
                let data = new Uint8Array(e.target.result);
                let workbook = XLSX.read(data, { type: "array", cellDates: true });
                // var fileData = reader.result;
                // var wb = XLSX.read(fileData, { type: 'binary'});
                // {header:1} 取消标题列.
                // var rowObj: (any) = XLSX.utils.sheet_to_json(wb.Sheets['Sheet1'], { header: 1 });
                //将Excel 第一个sheet内容转为json格式
                let worksheet = workbook.Sheets[workbook.SheetNames[0]];
                let json = XLSX.utils.sheet_to_json(worksheet).slice(2);
                let obj_obj = {
                    "学号": "studentid",
                    "班级": "classid",
                    "姓名": "amname",
                    "电话": "phone",
                    "政治面貌": "status",
                    "职务": "roleid",
                    "性别": "sex",
                    "社团": "clubid"
                }

                for (var i of json) {
                    let t = {}
                    for (var j of Object.keys(i)) {
                        t[obj_obj[j]] = i[j]
                    }
                    this.tableData.push(t)
                }
            };

            // 已二进制的形式读取文件
            reader.readAsArrayBuffer(file.raw);

        },
        handleDel(e) {
            this.tableData = this.tableData.filter((item) => item != e)
        },
        save() {
            for (var i = 0; i < this.tableData.length; i++) {
                if (this.tableData[i] == this.tmp) {
                    this.tableData[i] = this.form
                }
            }
            this.tableData = JSON.parse(JSON.stringify(this.tableData)) //手动刷新数组
            this.dialogFormVisible = false
        },
        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row))
            this.tmp = row
            this.dialogFormVisible = true
        },
        handleAdd() {
            let formdata = JSON.parse(JSON.stringify(this.tableData))

            for (var i of formdata) {
                if (i["sex"] == "男") i["sex"] = 0
                else if (i["sex"] == "女") i["sex"] = 1
                else {
                    this.$message.error("学号为" + i["studentid"] + "的性别值错误，请更正")
                    return
                }
                if (this.roles[i["clubid"]] == null) {
                    this.$message.error("存在错误或不存在的社团名称，请纠正 " + i["clubid"])
                    return
                }
                if (this.roles[i["roleid"]] == null) {
                    this.$message.error("存在错误或不存在的角色名称，请纠正 " + i["roleid"])
                    return
                }
                i["roleid"] = this.roles[i["roleid"]]
                i["clubid"] = this.roles[i["clubid"]]
                if (this.user.sysroleid == 3) i["clubid"] = this.user.clubid
                // i["id"]=null
                // i["intotime"]=null

            }

            personapi.importAll(formdata).then(res => {
                if (res.code === '200') {
                    this.$message.success("添加成功")
                    this.tableData = []
                } else {
                    this.$message.error(res.msg)
                }
            })
        },

    }
}
</script>

<style></style>