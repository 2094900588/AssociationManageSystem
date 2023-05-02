<template>
    <div style="width: 100%;">
        <!-- <h1>这是一个主页</h1> -->
        <div>
            <el-row :gutter="10" style="margin-bottom: 40px;margin-left: 50px;">
                <el-col :span="3">
                    <el-card style="color:#409EFF">
                        <div><i class="el-icon-postcard" />社团总数</div>
                        <div style="padding: 10px 0; text-align: center; font-weight: bold">
                            {{ count_club }}
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="3" style="margin-left: 30px;">
                    <el-card style="color:#F56C6C">
                        <div><i class="el-icon-user-solid" />社员总人数</div>
                        <div style="padding: 10px 0; text-align: center; font-weight: bold">
                            {{ count_am }}
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="3" style="margin-left: 30px;">
                    <el-card style="color:#F56C6C">
                        <div><i class="el-icon-user" />用户总人数</div>
                        <div style="padding: 10px 0; text-align: center; font-weight: bold">
                            {{ count_user }}
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
        <div>
            <el-row style="top:0px; margin-left: 50px;">
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
            ],
            count_club: 0,
            count_am: 0,
            count_user: 0
        }
    },
    created() {
        this.InteLoad()
        this.NumLoad()
        this.getinfo()
    },
    methods: {
        InteLoad() {
            //请求分页查询数据
            let params = {
                pageNum: this.IntepageNum,
                pageSize: this.IntepageSize,
            }
            homeapi.getIntePage(params).then(res => {
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

        },
        getinfo() {
            homeapi.getinfo().then(res => {
                this.count_club = res.data.count_club
                this.count_am = res.data.count_am
                this.count_user = res.data.count_user
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