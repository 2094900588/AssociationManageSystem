<template>
    <div class="home">
        <!-- <div style="margin: 10px 0">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入学号"
                v-model=""></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div> -->
        <el-pagination :page-size="1" :pager-count="19" layout="prev, pager, next" :total="19"
            @current-change="handleCurrentChange">
        </el-pagination>
        <el-select v-model="roles" multiple placeholder="请选择角色" @change="roleCurrentChange">
            <el-option v-for="item in origin_roles" :key="item.id" :label="item.rolename" :value="item.id">
            </el-option>
        </el-select>
        <el-table :data="tabledata" tooltip-effect="dark" size="small">
            <el-table-column label="节次" width="120">
                <template slot-scope="scope">
                    {{ getjcName(scope.$index) }}
                </template>
            </el-table-column>
            <el-table-column :label="getWeek(index)" v-for="(item, index) in  tabledata[0] " :key="index">
                <template slot-scope="scope">
                    <div :style="{ color: scope.row[index] == '全有课' ? 'red' : '' }">
                        <div v-if="scope.row[index] == '全有课' || scope.row[index] == '全无课'">{{ scope.row[index] }}</div>
                        <div v-else>
                            <el-row :gutter="2"
                                v-for="(item, x) in scope.row[index].slice(parseInt(scope.row[index].length / 3))">
                                <el-col :span="10" style="width: 33.33%;">
                                    {{ scope.row[index][x * 3] }}
                                </el-col>
                                <el-col :span="10" style="width: 33.33%;">
                                    {{ scope.row[index][x * 3 + 1] }}
                                </el-col>
                                <el-col :span="10" style="width: 33.34%;">
                                    {{ scope.row[index][x * 3 + 2] }}
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </template>
            </el-table-column>

        </el-table>
    </div>
</template>

<script>

import courseapi from '@/api/page/course'
import personapi from '@/api/page/person'
import roleapi from '@/api/page/role'
export default {
    data() {
        return {
            origin_course: {},
            origin_person: {},
            origin_roles: [],
            roles: [],
            persons: {},
            courses: [],
            tabledata: []
        }
    },
    created() {
        this.load()
    },
    watch: {
        persons(e) {
            if (JSON.stringify(this.courses) != '{}') this.init()
        },
        courses(e) {
            if (JSON.stringify(this.persons) != '{}') this.init()
        }

    },
    methods: {
        handleCurrentChange(e) {
            this.courses = this.origin_course.filter(item => item.begin <= e && item.end >= e)
            this.courses = this.courses.filter(item => item.isoeweek == 0 || item.isoeweek % 2 == e % 2) //真保留 假不保留
            // console.log(this.courses);
        },
        roleCurrentChange(e) {
            if (this.roles.length == 0) {
                this.persons = JSON.parse(JSON.stringify(this.origin_person))
            } else {
                this.persons = this.origin_person.filter(item => e.indexOf(item.roleid) != -1)
            }
        },
        init() { //生成无课课表 
            let person = {}
            let NoneCourseClass = []
            let NoneCourse = []
            let student = this.courses.filter(item => item.studentid != "")
            let id_name = id => {
                let tmp = this.persons.filter(item => id == item.studentid)
                if (tmp.length != 0) return tmp[0].amname
                return false
            }
            for (var i of this.persons) {
                if (person[i.classid] == null) {
                    person[i.classid] = [i.amname]
                } else {
                    person[i.classid].push(i.amname)
                }
            }
            let totalperson = this.persons.length
            let classlist = Object.keys(person)
            for (var i = 0; i < 6; i++) {
                NoneCourseClass[i] = []
                for (var j = 0; j < 7; j++) {
                    NoneCourseClass[i].push(JSON.parse(JSON.stringify(classlist)))
                }
            }

            for (var i of this.courses) {
                NoneCourseClass[i.coursedate][i.week] = NoneCourseClass[i.coursedate][i.week].filter(item => item != i.classid)
            }
            for (var i = 0; i < NoneCourseClass.length; i++) {
                NoneCourse[i] = []
                for (var j = 0; j < NoneCourseClass[i].length; j++) {
                    NoneCourse[i][j] = []
                    for (var k = 0; k < NoneCourseClass[i][j].length; k++) {
                        NoneCourse[i][j] = NoneCourse[i][j].concat(person[NoneCourseClass[i][j][k]])
                    }
                    if (NoneCourse[i][j].length == totalperson) NoneCourse[i][j] = "全无课"
                    else if (NoneCourse[i][j].length == 0) NoneCourse[i][j] = "全有课"
                    //else this.NoneCourse[i][j] = this.NoneCourse[i][j].join("\n")
                }
            }
            // for (var i of student) {
            //     let t=id_name(i.studentid)
            //     if(t){
            //         if(NoneCourse[i.coursedate][i.week] ==  "全无课")
            //     }
            // }
            // console.log(this.NoneCourse);
            this.tabledata = JSON.parse(JSON.stringify(NoneCourse))
            // this.tabledata = this.NoneCourse
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
        },
        getWeek(n) {
            let dx = ['一', '二', '三', '四', '五', '六', '日'];
            return "星期" + dx[n]
        },
        load() {
            courseapi.list().then(res => {
                this.origin_course = res.data
                this.courses = JSON.parse(JSON.stringify(this.origin_course))
                personapi.list().then(res => {
                    this.origin_person = res.data
                    this.persons = JSON.parse(JSON.stringify(this.origin_person))
                })
            })
            roleapi.getall().then(res => {
                this.origin_roles = res.data
            })

        }
    }
}
</script>

<style lang="stylus" scoped>
.home{
  ::v-deep .el-table{
    .cell {
      white-space: pre-line;
    }
  }
}
</style>