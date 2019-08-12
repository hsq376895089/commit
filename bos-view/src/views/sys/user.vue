<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.username" placeholder="username" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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
      <el-table-column label="用户名" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Email" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
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
      <el-form ref="dataForm" :rules="rules" :model="sysUser" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">

        <el-form-item label="部门" prop="sex">
          <el-select v-model="sysUser.deptId" placeholder="请选择">
            <el-option-group
              v-for="group in deptList"
              :key="group.id"
              :label="group.name">
              <el-option-group
                v-for="items in group.items"
                :key="items.id"
                :label="items.name">
                <el-option
                  v-for="item in items.items"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-option-group>
            </el-option-group>
          </el-select>
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-input v-model="sysUser.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="sysUser.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="email" prop="email">
          <el-input v-model="sysUser.email" />
        </el-form-item>
        <el-form-item label="电话号码" prop="mobile">
          <el-input v-model="sysUser.mobile" />
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
  import { add, deleteUser, update, list,selectByUsername } from '@/api/sys/user'
  import {groupDept} from "@/api/sys/dept";
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import { getToken } from '@/utils/auth'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination

  export default {
    name: 'ComplexTable',
    components: {Pagination},
    directives: {waves},
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
          username: '',
        },
        sysUser: {
          id: undefined,
          username: '',
          password: '',
          email: '',
          mobile: '',
          createBy: '',
          deptId:'',
          lastUpdateBy: ''
        },
        uname: '',
        dialogFormVisible: false,
        dialogStatus: '',
        title: '添加用户',
        rules: {
          username: [{required: true, message: '用户名不能为空', trigger: 'change',}],
          password: [{required: true, message: '请输入正确的密码格式', trigger: 'change'}],
          email: [{type: 'email', required: true, message: '请输入正确的email格式', trigger: 'change'}],
          mobile: [{pattern: /^1[34578]\d{9}$/, required: true, message: '请输入正确的电话号码格式', trigger: 'change'}]
        }
      }
    },
    created() {
      this.getList()
      this.getGroupDept()
    },
      methods: {
        getGroupDept(){
          groupDept().then((response) => {
            this.deptList = response.data.deptList
          })
        },
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
          this.sysUser = {
            id: undefined,
            username: '',
            password: '',
            email: '',
            mobile: '',
            createBy: ''
          }
        },
        handleCreate() {
          this.resetTemp()
          this.dialogStatus = 'create'
          this.dialogFormVisible = true
          this.title = '添加用户'
          this.$nextTick(() => {
            this.$refs['dataForm'].clearValidate()
          })
        },
        createData() {
          this.$refs['dataForm'].validate((valid) => {
            if (valid) {
              selectByUsername(this.sysUser.username).then(response => {
                if (response.data.flag){
                  this.sysUser.createBy = getToken()
                  this.sysUser.lastUpdateBy = getToken()
                  add(this.sysUser).then(() => {
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
                  message:'用户名已注册，请重新输入'
                })
            }
          })
          }
          })
        },
        handleUpdate(row) {
          this.sysUser = Object.assign({}, row) // copy obj
          this.uname = this.sysUser.username
          this.dialogStatus = 'update'
          this.dialogFormVisible = true
          this.title = '修改用户'
          this.$nextTick(() => {
            this.$refs['dataForm'].clearValidate()
          })
        },
        updateData() {
          this.$refs['dataForm'].validate((valid) => {
            if (valid) {
              const tempData = Object.assign({},this.sysUser)
              var na = tempData.username
              if (this.uname==tempData.username){
                na=''
              }
              selectByUsername(na).then(response =>{
                if (response.data.flag) {
                  this.sysUser.lastUpdateBy = getToken()
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
                    message:'用户名已注册，请重新输入'
                  })
                }
              })
            }
          })
        },
        handleDelete(row) {
          this.$confirm('确认删除该用户吗', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            console.debug(row.id)
            if (row.username==getToken()) {
              this.$message({
                type: 'error',
                message: '用户不能自己删自己!'
              })
            }else{
              deleteUser(row.id).then((res) => {
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
