<template>
    <div>
        <!-- <h1>这是一个主页</h1> -->
        <div>
            <el-row style="top:150px; left: 70px;">
                <el-col :span="10" :offset="0">
                    <el-card :body-style="{ padding: '0px' }" class="table_card">
                        <h1 style="margin-top: 30px;margin-bottom: 20px;">社团积分榜</h1>
                        <el-table :data="InteTableData" :show-header="false" class="table_card">
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
                            <el-table-column prop="integral">
                            </el-table-column>
                        </el-table>

                    </el-card>
                </el-col>

                <el-col :span="10" :offset="2">
                    <el-card :body-style="{ padding: '0px' }" class="table_card">
                        <h1 style="margin-top: 30px;margin-bottom: 20px;">社团人数榜</h1>
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
                            <el-table-column prop="sum">
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
            IntepageSize: 5,
            NumTableData: [],
            Numtotal: 0,
            NumpageNum: 1,
            NumpageSize: 5,
            rankphoto: [
                require('@/assets/1.png'),
                require('@/assets/2.png'),
                require('@/assets/3.png'),
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
                for (var i = 0; i < this.InteTableData.length; i++)
                    if (this.InteTableData[i].integral == null) this.InteTableData[i].integral = 0
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
                this.NumTableData = res.data.records
                this.Numtotal = res.data.total
                for (var i = 0; i < this.NumTableData.length; i++)
                    if (this.NumTableData[i].num == null) this.NumTableData[i].num = 0
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

.table_card {
    display: flex;
    justify-content: center;
}

.img {
    height: 450px;
    width: auto\9;
    width: 100%;
}
</style>