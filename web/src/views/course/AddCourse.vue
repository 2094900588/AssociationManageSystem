<template>
    <div>
        <el-form :model="formData" ref="formData" label-width="auto" style="width: 100%;margin-top: 4.2%;">
            <span style="color: red;">注：学号和班级号 只需要输入一个即可</span><br>
            <el-input style="width: 200px;cursor: pointer" placeholder="请输入学号" :disabled="formData.classid !== ''"
                v-model="formData.studentid"></el-input>
            <el-input style="width: 200px;cursor: pointer" placeholder="请输入班级" :disabled="formData.studentid !== ''"
                v-model="formData.classid" class="ml-5"></el-input>
            <el-table :data="formData.course" tooltip-effect="dark" size="small">
                <el-table-column label="节次" width="120">
                    <template slot-scope="scope">
                        {{ getjcName(scope.$index) }}
                    </template>
                </el-table-column>
                <el-table-column :label="getWeek(index)" v-for="(item, index) in formData.course[0]" :key="index">
                    <template slot-scope="scope">
                        <el-form-item :prop="'course.' + scope.$index">
                            <el-checkbox v-model="scope.row[index].iscourse">有课</el-checkbox>
                            <div class="inputDeep" v-if="scope.row[index].iscourse">
                                <el-input v-model="scope.row[index].begin" style="width: 30px;cursor: pointer;"></el-input>
                                <span>~</span>
                                <el-input v-model="scope.row[index].end" style="width: 30px;cursor: pointer;"></el-input>
                                <br>
                                <el-select v-model="scope.row[index].isoeweek" placeholder="请选择单双周" style="width: 80px;">
                                    <el-option label="全周" value="0">
                                    </el-option>
                                    <el-option label="单周" value="1">
                                    </el-option>
                                    <el-option label="双周" value="2">
                                    </el-option>
                                </el-select>
                            </div>
                        </el-form-item>
                    </template>
                </el-table-column>
            </el-table>
        </el-form>
        <el-button @click="submit">提交</el-button>
    </div>
</template>

<script>
import courseapi from '@/api/page/course'

export default {
    data() {
        return {
            formData: {
                course: [
                    // [false, false, false, false, false, false, false],
                    // [false, false, false, false, false, false, false],
                    // [false, false, false, false, false, false, false],
                    // [false, false, false, false, false, false, false],
                    // [false, false, false, false, false, false, false],
                    // [false, false, false, false, false, false, false],
                    [{ iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }],
                    [{ iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }],
                    [{ iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }],
                    [{ iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }],
                    [{ iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }],
                    [{ iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }, { iscourse: false, isoeweek: "0", begin: 1, end: 19 }],
                ],
                studentid: '',
                classid: '',
            },
        }
    }, created() {
        if (JSON.stringify(this.$route.query) !== '{}')
            this.getone(this.$route.query)
    },
    methods: {
        getone(n) {
            courseapi.getone(n).then(res => {
                console.log(res.data);
                for (var i of res.data) {
                    this.formData.course[i.coursedate][i.week].iscourse = true
                    this.formData.course[i.coursedate][i.week].begin = i.begin
                    this.formData.course[i.coursedate][i.week].isoeweek = i.isoeweek.toString()
                    this.formData.course[i.coursedate][i.week].end = i.end

                }
                this.formData.studentid = res.data[0].studentid
                this.formData.classid = res.data[0].classid
            })
        },
        getjcName(n) {
            let dx = ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'];
            // console.log(n);
            let e = (n + 1) * 2, b = e - 1;

            return "第" + dx[b - 1] + "~" + dx[e - 1] + "节"
            /* 
            0 1-2
            1 3-4
            2 5-6
            */
        }, getWeek(n) {
            let dx = ['一', '二', '三', '四', '五', '六', '日'];
            return "星期" + dx[n]
        },
        //点击校验
        submit() {
            courseapi.save(this.formData).then(res => {
                if (res.code === '200') {
                    this.$message.success("添加成功")
                } else {
                    this.$message.error(res.msg)
                    // this.$message.error("添加失败")
                }
            })
        }
    }
}

</script>

<style scoped>
.inputDeep>>>.el-input__inner {
    padding: 4px;
}
</style>