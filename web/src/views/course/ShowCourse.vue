<template>
    <div class="home">
        <el-table :data="tabledata" tooltip-effect="dark" size="small">
            <el-table-column label="节次" width="120">
                <template slot-scope="scope">
                    {{ getjcName(scope.$index) }}
                </template>
            </el-table-column>
            <el-table-column :label="getWeek(index)" v-for="(item, index) in  tabledata[0] " :key="index">
                <template slot-scope="scope">
                    <div :style="{ color: scope.row[index] == '全有课' ? 'red' : '' }">
                        <el-row :gutter="16"
                            v-for="(item, x) in scope.row[index].slice(parseInt(scope.row[index].length / 3))">
                            <el-col :span="10">
                                {{ scope.row[index][x * 3] }}
                            </el-col>
                            <el-col :span="10" style="margin-right: -23px; margin-left: -22px;">
                                {{ scope.row[index][x * 3 + 1] }}
                            </el-col>
                            <el-col :span="10">
                                {{ scope.row[index][x * 3 + 2] }}
                            </el-col>
                        </el-row>
                    </div>
                </template>
            </el-table-column>

        </el-table>
    </div>
</template>

<script>

import courseapi from '@/api/page/course'
import personapi from '@/api/page/person'
export default {
    data() {
        return {
            x: 0,
            person: {},
            totalperson: 0,
            courses: [],
            NoneCourseClass: [

            ],
            NoneCourse: [

            ],
            tabledata: [
                [], []
            ]
        }
    },
    created() {
        this.load()
    },
    watch: {
        totalperson: function (e) {
            let classlist = Object.keys(this.person)
            for (var i = 0; i < 6; i++) {
                this.NoneCourseClass[i] = []
                for (var j = 0; j < 7; j++) {
                    this.NoneCourseClass[i].push(JSON.parse(JSON.stringify(classlist)))
                }
            }
            if (JSON.stringify(this.courses) != '{}') this.init()
        },
        courses: function (e) {
            if (this.totalperson != 0) this.init()
        }

    },
    methods: {
        init() { //生成无课课表 

            for (var i of this.courses) {
                this.NoneCourseClass[i.coursedate][i.week] = this.NoneCourseClass[i.coursedate][i.week].filter(item => item != i.classid)
            }
            for (var i = 0; i < this.NoneCourseClass.length; i++) {
                this.NoneCourse[i] = []
                for (var j = 0; j < this.NoneCourseClass[i].length; j++) {
                    this.NoneCourse[i][j] = []
                    for (var k = 0; k < this.NoneCourseClass[i][j].length; k++) {
                        this.NoneCourse[i][j] = this.NoneCourse[i][j].concat(this.person[this.NoneCourseClass[i][j][k]])
                    }
                    if (this.NoneCourse[i][j].length == this.totalperson) this.NoneCourse[i][j] = "全员无课"
                    else if (this.NoneCourse[i][j].length == 0) this.NoneCourse[i][j] = "全有课"
                    //else this.NoneCourse[i][j] = this.NoneCourse[i][j].join("\n")
                }
            }
            this.tabledata = JSON.parse(JSON.stringify(this.NoneCourse))
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
                this.courses = res.data
            })
            personapi.list().then(res => {
                this.person = {}
                for (var i of res.data) {
                    if (this.person[i.classid] == null) {
                        this.person[i.classid] = [i.amname]
                    } else {
                        this.person[i.classid].push(i.amname)
                    }
                }
                this.totalperson = res.data.length
                // console.log(this.courses);
                // setTimeout(() => {
                // }, 500);
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