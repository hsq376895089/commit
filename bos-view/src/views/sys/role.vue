<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="name" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" sortable="custom" type="index" align="center" width="80">
      </el-table-column>
      <el-table-column label="角色名" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="职位" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.remark }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createBy }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后修改人" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.lastUpdateBy }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.lastUpdateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="sysRole" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="角色名" prop="name">
          <el-input v-model="sysRole.name" placeholder="请输入角色名"/>
        </el-form-item>
        <el-form-item label="职位" prop="remark">
          <el-input v-model="sysRole.remark" placeholder="请输入职位"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定
        </el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
  // import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
  import { add, deleteRole, update, list, selectByName } from '@/api/sys/role'
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import { getToken } from '@/utils/auth'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination

  export default {
    name: 'ComplexTable',
    components: { Pagination },
    directives: { waves },
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'info',
          deleted: 'danger'
        }
        return statusMap[status]
      },
      typeFilter(type) {
        return calendarTypeKeyValue[type]
      }
    },
    data() {
      return {
        tableKey: 0,
        list: null,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10,
          name: '',
        },
        sysRole: {
          id: undefined,
          name: '',
          remark: '',
          createBy:''
        },
        uname:'',
        dialogFormVisible: false,
        dialogStatus: '',
        title: '添加角色',
        rules: {
          name: [{ required: true, message: '角色名不能为空', trigger: 'change' }],
          remark: [{ required: true, message: '职位不能为空', trigger: 'change' }],
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        list(this.listQuery).then(response => {
          this.list = response.data.row
          this.total = response.data.total
          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 1.5 * 1000)
        })
      },
      handleFilter() {
        this.listQuery.page = 1
        this.getList()
      },
      resetTemp() {
        this.sysRole = {
          id: undefined,
          name: '',
          remark: '',
          createBy:''
        }
      },
      handleCreate() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.title = '添加角色'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      createData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            selectByName(this.sysRole.name).then(response => {
              if (response.data.flag){
                this.sysRole.createBy = getToken()
                this.sysRole.lastUpdateBy = getToken()
                add(this.sysRole).then(() => {
                  this.dialogFormVisible = false
                  this.$message({
                    type:'success',
                    message:'添加成功'

                  })

                  this.getList()
                })
              }else {
                this.$message({
                  type:'error',
                  message:'角色名已注册，请重新输入'
                })
              }
            })
          }
        })
      },
      handleUpdate(row) {
        this.sysRole = Object.assign({}, row) // copy obj
        this.uname = this.sysRole.name
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.title = '修改角色'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            const tempData = Object.assign({},this.sysRole)
            var na = tempData.name
            if (this.uname==tempData.name){
              na=''
            }
            selectByName(na).then(response =>{
              if (response.data.flag) {
                this.sysRole.lastUpdateBy = getToken()
                update(tempData).then(() => {
                  this.dialogFormVisible = false
                  this.$message({
                    type: 'success',
                    message: '修改成功'
                  })
                  this.getList()
                })
              }else {
                this.$message({
                  type:'error',
                  message:'角色名已注册，请重新输入'
                })
              }
            })
          }
        })
      },
      handleDelete(row) {
        this.$confirm('确认删除该角色吗', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.debug(row.id)
          if (row.name==getToken()) {
            this.$message({
              type: 'error',
              message: '用户不能自己删自己!'
            })
          }else {
            deleteRole(row.id).then((res) => {
              this.getList()
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            })
          }
        }).catch((err) => {
          this.$message({
            type: 'info',
            message: err
          })
        })
        console.log(index, row)
      }
    }
  }
</script>
