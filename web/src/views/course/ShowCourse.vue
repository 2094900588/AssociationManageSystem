<template>
    <div class="home">
        <el-table :data="tabledata" tooltip-effect="dark" size="small">
            <el-table-column label="节次">
                <template slot-scope="scope">
                    {{ getjcName(scope.$index) }}
                </template>
            </el-table-column>
            <el-table-column :label="getWeek(index)" v-for="(item, index) in tabledata[0]" :key="index">
                <template slot-scope="scope">
                    {{ scope.row[index] }}
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
    methods: {
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
                for (var i of res.data) {
                    if (this.person[i.classid] == null) {
                        this.person[i.classid] = [i.amname]
                    } else {
                        this.person[i.classid].push(i.amname)
                    }
                }
                this.totalperson = res.data.length
                let classlist = Object.keys(this.person)
                for (var i = 0; i < 6; i++) {
                    this.NoneCourseClass[i] = []
                    for (var j = 0; j < 7; j++) {
                        this.NoneCourseClass[i].push(JSON.parse(JSON.stringify(classlist)))
                    }
                }
                // console.log(this.courses);
                setTimeout(() => {
                    for (var i of this.courses) {
                        this.NoneCourseClass[i.coursedate][i.week] = this.NoneCourseClass[i.coursedate][i.week].filter(item => item != i.classid)
                        // console.log(this.NoneCourseClass[i.coursedate][i.week]);
                        // console.log();
                    }
                    // console.log(this.NoneCourseClass);
                    for (var i = 0; i < this.NoneCourseClass.length; i++) {
                        this.NoneCourse[i] = []
                        for (var j = 0; j < this.NoneCourseClass[i].length; j++) {
                            this.NoneCourse[i][j] = []
                            for (var k = 0; k < this.NoneCourseClass[i][j].length; k++) {
                                this.NoneCourse[i][j] = this.NoneCourse[i][j].concat(this.person[this.NoneCourseClass[i][j][k]])
                            }
                            if (this.NoneCourse[i][j].length == this.totalperson) this.NoneCourse[i][j] = "全员无课"
                            else this.NoneCourse[i][j] = this.NoneCourse[i][j].join("\n")
                        }
                    }
                    this.tabledata = this.NoneCourse
                    console.log(JSON.stringify(this.NoneCourse));
                }, 500);
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