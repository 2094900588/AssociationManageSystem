<template>
    <div>
        <div style="width: 500px;">
            <el-form label-width="100px" size="small">
                <el-form-item label="活动名称">
                    <el-input v-model="form.optionname" type="text" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="活动描述">
                    <el-input v-model="form.optiondesc" type="textarea" autosize autocomplete="off"></el-input>
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
                <el-form-item label="活动周数">
                    <el-select v-model="week" placeholder="请选择周数" @change="weekCurrentChange">
                        <el-option
                            v-for="item in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19']"
                            :key="item" :label="item" :value="item">
                        </el-option>
                    </el-select>
                    <span style="color: red;">{{ msg }}</span>
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
        </div>


    </div>
</template>

<script>
import courseapi from '@/api/page/course'
import personapi from '@/api/page/person'

import roleapi from '@/api/page/role'
import clubapi from '@/api/page/club'
import { mapState } from 'vuex'
export default {
    data() {
        return {
            form: {},
            origin_course: {},
            origin_person: {},
            roles: [],
            clubs: [],
            persons: {},
            courses: [],
            tabledata: [],
            week: 1,
            msg: ''
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
    computed: {
        ...mapState(['user'])
    }, methods: {
        weekCurrentChange(e) {
            this.courses = this.origin_course.filter(item => item.begin <= e && item.end >= e)
            this.courses = this.courses.filter(item => item.isoeweek == 0 || item.isoeweek % 2 == e % 2) //真保留 假不保留
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
        init() { //生成无课课表 
            let person = {}
            let NoneCourseClass = []
            let NoneCourse = []
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
            // this.tabledata = JSON.parse(JSON.stringify(NoneCourse))
            let min = 99, x = 0, y = 0;
            console.log(NoneCourse);
            for (var i = 0; i < NoneCourse.length - 2; i++) {
                for (var j = 0; j < NoneCourse[i].length - 2; j++) {
                    if (NoneCourse[i][j] instanceof Array) {
                        if (NoneCourse[i][j].length < min) {
                            min = NoneCourse[i][j].length
                            x = i
                            y = j
                        }
                    }
                }
            }
            this.msg = this.getWeek(y) + '的' + this.getjcName(x)
            // this.tabledata = this.NoneCourse
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
                this.roles = res.data
            })

            clubapi.getall().then(res => {
                this.clubs = res.data
            })
        }

    }
}
</script>


<style scoped></style>