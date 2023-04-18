<template>
    <div>
        <!-- <h1>这是一个主页</h1> -->

        <div>
            <el-row>
                <el-col :span="12" :offset="0">
                    <el-card :body-style="{ padding: '20px' }">
                        <h1>社团积分榜</h1>
                        <el-table :data="InteTableData" :show-header="false">
                            <el-table-column width="80">
                                <template slot-scope="scope">
                                    <img v-if="scope.$index < rankphoto.length" :src="rankphoto[scope.$index]" class="image"
                                        width="60" height="60">
                                    <div class="rank-item" v-else>
                                        {{ scope.$index + 1 }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="clubname" width="180">
                            </el-table-column>
                            <el-table-column prop="integral" width="180">
                            </el-table-column>
                        </el-table>

                    </el-card>
                </el-col>

                <el-col :span="10" :offset="2">
                    <el-card :body-style="{ padding: '20px' }">
                        <h1>社团人数榜</h1>
                        <el-table :data="NumTableData" :show-header="false">
                            <el-table-column width="80">
                                <template slot-scope="scope">
                                    <img v-if="scope.$index < rankphoto.length" :src="rankphoto[scope.$index]" class="image"
                                        width="60" height="60">
                                    <div class="rank-item" v-else>
                                        {{ scope.$index + 1 }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="clubname" width="180">
                            </el-table-column>
                            <el-table-column prop="sum" width="180">
                            </el-table-column>
                        </el-table>

                    </el-card>
                </el-col>
            </el-row>

        </div>



    </div>
</template>

<script>
import homeapi from '@/api/page/home'
export default {
    name: "Home",
    data() {
        return {
            InteTableData: [],
            Intetotal: 0,
            IntepageNum: 1,
            IntepageSize: 10,
            NumTableData: [],
            Numtotal: 0,
            NumpageNum: 1,
            NumpageSize: 10,
            rankphoto: [
                "https://static.shiyanlou.com/lanqiao/frontend/dist/img/p-medal-1.465632f.png",
                "https://static.shiyanlou.com/lanqiao/frontend/dist/img/p-medal-2.b7a14ad.png",
                "https://static.shiyanlou.com/lanqiao/frontend/dist/img/p-medal-3.0ce2247.png"
            ]
        }
    },
    created() {
        this.InteLoad()
        this.NumLoad()
    },
    methods: {
        InteLoad() {
            //请求分页查询数据
            let params = {
                pageNum: this.IntepageNum,
                pageSize: this.IntepageSize,
            }
            homeapi.getIntePage(params).then(res => {
                console.log(res);
                this.InteTableData = res.data.records
                this.Intetotal = res.data.total
            })

        },
        NumLoad() {
            //请求分页查询数据
            let params = {
                pageNum: this.NumpageNum,
                pageSize: this.NumpageSize,
            }
            homeapi.getNumPage(params).then(res => {
                // console.log(res);
                this.NumTableData = res.data
                this.Numtotal = res.data.total
                // console.log(this.NumTableData);
            })

        }
    }
}
</script>

<style scoped>
.rank-item {
    width: 60px;
    height: 60px;
    text-align: center;
    font-size: 20px;
    padding: 20px 0;
}
</style>