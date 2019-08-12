<!--管理定区-->

<template>
  <div class="app-container">
    <div class="filter-container">
      <!--<el-input v-model="listQuery.name" placeholder="name" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>-->
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
      <el-table-column label="定区编码" width="170px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.zonecode }}</span>
        </template>
      </el-table-column>
      <el-table-column label="定区名称" width="170px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.zonename }}</span>
        </template>
      </el-table-column>

      <el-table-column label="定区负责人" width="170px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.basInfo.customname }}</span>
        </template>
      </el-table-column>

      <el-table-column label="联系电话" width="170px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.telphone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属单位" width="170px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.dept.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="330" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="text" icon="el-icon-edit" @click="handleSelect(row)">
            查看列表
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="basZoneInfo" label-position="left" label-width="110px" style="width: 400px; margin-left:50px;">
        <el-form-item label="定区编码" prop="zonecode">
          <el-input v-model="basZoneInfo.zonecode" placeholder="请输入定区编码"/>
        </el-form-item>
        <el-form-item label="定区名称" prop="zonename">
          <el-input v-model="basZoneInfo.zonename" placeholder="请输入定区名称"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="telphone">
          <el-input v-model="basZoneInfo.telphone" placeholder="请输入联系电话"/>
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
  import { add, deleteById, update, list } from '@/api/body/hsq/zoneInfo'
  import waves from '@/directive/waves' // waves directive
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
        basZoneInfo: {
          id: undefined,
          zonecode:'',
          zonename:'',
          telphone:'',
          zonepeople:'',
          subordinateunit:''
        },
        dialogFormVisible: false,
        dialogStatus: '',
        title: '添加',
        rules: {
          zonecode: [{ required: true, message: '定区编码不能为空', trigger: 'change' }],
          zonename: [{ required: true, message: '定区名称不能为空', trigger: 'change' }],
          telphone: [{ required: true, message: '联系电话不能为空', trigger: 'change' }]
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        /*debugger*/
        this.listLoading = true
        list(this.listQuery).then(response => {
          this.list = response.data.row
          // console.debug(this.list)
          this.total = response.data.total
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
        this.basZoneInfo = {
          id: undefined,
          zonecode:'',
          zonename:'',
          telphone:'',
          zonepeople:'',
          subordinateunit:''
        }
      },
      handleCreate() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.title = '添加'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      createData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            add(this.basZoneInfo).then(() => {
              this.dialogFormVisible = false
              this.$message({
                type:'success',
                message:'添加成功'

              })

              this.getList()
            })
          }
        })
      },
      handleUpdate(row) {
        this.basZoneInfo = Object.assign({}, row) // copy obj
        delete this.basZoneInfo.dept
        delete this.basZoneInfo.basInfo
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.title = '修改'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            const tempData = Object.assign({},this.basZoneInfo)
            console.debug(tempData)
            update(tempData).then(() => {
              this.dialogFormVisible = false
              this.$message({
                type: 'success',
                message: '修改成功'
              })
              this.getList()
            })
          }
        })
      },
      handleDelete(row) {
        this.$confirm('确认删除吗', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.debug(row.id)
          deleteById(row.id).then((res) => {
            this.getList()
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
          // }
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
