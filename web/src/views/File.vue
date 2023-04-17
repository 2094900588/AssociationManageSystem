<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;cursor: pointer" suffix-icon="el-icon-search" placeholder="请输入文件名称"  v-model="name"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-upload action="http://localhost:9090/file/upload"
                       :show-file-list="false"
                       :on-success="handleFileUploadSuccess"
                       style="display: inline-block">
                <el-button type="primary" class="ml-5">上传文件<i class="el-icon-bottom"></i> </el-button>
            </el-upload>
            <!--            二次确认按钮-->
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='好的'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="你确定删除吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference" class="ml-5" >批量删除<i class="el-icon-remove-outline"></i> </el-button>
            </el-popconfirm>
        </div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50">
            </el-table-column>
            <el-table-column prop="id" label="id" width="80">
            </el-table-column>
            <el-table-column prop="name" label="文件名称">
            </el-table-column>
            <el-table-column prop="type" label="文件类型">
            </el-table-column>
            <el-table-column prop="size" label="文件大小(KB)">
            </el-table-column>
            <el-table-column label="预览"  width="150">
                <template slot-scope="scope">
                    <el-button type="primary" @click="look(scope.row.url)">预览</el-button>
                </template>
            </el-table-column>
            <el-table-column label="下载"  width="150">
                <template slot-scope="scope">
                   <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column  label="启用" width="150">
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
                </template>
            </el-table-column>
            <el-table-column prop=" " label=" " width="230">
                <template slot-scope="scope">
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='好的'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="你确定删除吗？"
                            @confirm="handleDel(scope.row.id)"
                    >
                        <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "File",
        data() {
            return {
                tableData:[],
                name: '',
                multipleSelection: [],
                pageNum: 1,
                pageSize: 10,
                total: 0,
                headBg:'headBg',
            }
        },
        created() {
            this.load()
        },
        methods:{
            look(){

            },
            handleSizeChange(pageSize){
                this.pageSize=pageSize
                this.load()
            },
            handleCurrentChange(pageNum){
                this.pageNum=pageNum
                this.load()
            },
            load(){
                //请求分页查询数据
                this.request.get("/file/page",{
                    params:{
                        pageNum : this.pageNum,
                        pageSize : this.pageSize,
                        name : this.name,
                    }
                }).then(res=>
                {
                    this.tableData = res.data.records
                    this.total = res.data.total
                })

            },
            reset(){
                this.name="",
                this.load()
            },
            handleDel(id){
                this.request.delete("/file/"+id).then(res=>{
                    if(res.code === '200'){
                        this.$message.success("删除成功"),
                            this.load()
                    }else {
                        this.$message.error("删除失败")
                    }
                })
            },
            delBatch(){
                let ids=this.multipleSelection.map(v=>v.id) //[{},{},{},{}]   ==>  1,2,3
                this.request.post("/file/del/batch",ids).then(res=>{
                    if(res.code === '200'){
                        this.$message.success("批量删除成功"),
                            this.load()
                    }else {
                        this.$message.error("批量删除失败")
                    }
                })
            },
            handleSelectionChange(val){
                console.log(val),
                    this.multipleSelection=val
            },
            handleFileUploadSuccess(res){
                console.log(res)
                this.load()
            },
            download(url){
                window.open(url)
            },
            changeEnable(row){
                this.request.post("/file/update",row).then(res=>{
                    if(res.code === '200'){
                        this.$message.success("更新成功")
                    }else {
                        this.$message.error("更新失败")
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>