<template>
  <div class="points-container">
    <div>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="用户ID">
          <el-input v-model="searchForm.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="medium" @click="getList">搜索</el-button>
          <el-button size="medium" @click="searchForm = {}">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="btn-div">
      <div>
        <el-button plain size="mini" type="primary" @click="handleAdd">新增</el-button>
        <el-button plain size="mini" type="danger" :disabled="multipleSelectionIds.length === 0" @click="handleDelete(multipleSelectionIds)">批量删除</el-button>
      </div>
      <div />
    </div>
    <div>
      <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column key="userId" label="用户ID" align="center" prop="userId" :show-overflow-tooltip="true" />
        <el-table-column key="totalPoints" label="积累总积分" align="center" prop="totalPoints" :show-overflow-tooltip="true" />
        <el-table-column key="availablePoints" label="可用积分" align="center" prop="availablePoints" :show-overflow-tooltip="true" />
        <el-table-column key="frozenPoints" label="冻结积分" align="center" prop="frozenPoints" :show-overflow-tooltip="true" />
        <el-table-column key="usedPoints" label="已用积分" align="center" prop="usedPoints" :show-overflow-tooltip="true" />
        <el-table-column key="userType" label="用户类型" align="center" prop="userType" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.biz_points_user_type" :value="scope.row.userType" />
          </template>
        </el-table-column>
        <el-table-column key="beginTime" label="会员开始时间" align="center" prop="beginTime" :show-overflow-tooltip="true" />
        <el-table-column key="endTime" label="会员结束时间" align="center" prop="endTime" :show-overflow-tooltip="true" />
        <el-table-column
          label="操作"
          align="center"
          width="250"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete([scope.row.userId])"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      v-show="searchForm.total > 0"
      :total="searchForm.total"
      :page.sync="searchForm.pageNum"
      :limit.sync="searchForm.pageSize"
      @pagination="getList"
    />
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" label-width="80px" :rules="rules">
        <el-form-item label="积累总积分" prop="totalPoints">
          <el-input v-model="form.totalPoints" />
        </el-form-item>
        <el-form-item label="可用积分" prop="availablePoints">
          <el-input v-model="form.availablePoints" />
        </el-form-item>
        <el-form-item label="冻结积分" prop="frozenPoints">
          <el-input v-model="form.frozenPoints" />
        </el-form-item>
        <el-form-item label="已用积分" prop="usedPoints">
          <el-input v-model="form.usedPoints" />
        </el-form-item>
        <el-form-item label="用户类型" prop="userType">
          <el-input v-model="form.userType" />
        </el-form-item>
        <el-form-item label="会员开始时间" prop="beginTime">
          <el-input v-model="form.beginTime" />
        </el-form-item>
        <el-form-item label="会员结束时间" prop="endTime">
          <el-input v-model="form.endTime" />
        </el-form-item>
        <el-form-item label="积分状态">
          <el-radio-group v-model="form.status">
            <el-radio v-for="(item, index) in dict.type.common_status" :key="index" :label="parseInt(item.value)">{{ item.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="onSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getPointsPage, addPoints, updatePoints, deletePoints } from '@/api/biz/points'

export default {
  name: 'Points',
  dicts: ['common_status', 'biz_points_user_type'],
  data() {
    return {
      searchForm: {
        pageNum: 1,
        pageSize: 10,
        total: 0
      },
      form: {},
      tableList: [],
      loading: false,
      submitLoading: false,
      dialogVisible: false,
      dialogTitle: null,
      rules: {
        pointsName: [
          { required: true, message: '积分名称不能为空', trigger: 'blur' }
        ],
        pointsKey: [
          { required: true, message: '积分字符不能为空', trigger: 'blur' }
        ]
      },
      multipleSelectionIds: []
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      getPointsPage(this.searchForm).then(res => {
        this.tableList = res.data.list
        this.searchForm.total = parseInt(res.data.total)
        this.loading = false
      }).catch(error => {
        this.loading = false
      })
    },
    onSubmit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.userId != undefined) {
            updatePoints(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.submitLoading = false
              this.dialogVisible = false
              this.getList()
            }).catch(error => {
              this.submitLoading = false
            })
          } else {
            addPoints(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.submitLoading = false
              this.dialogVisible = false
              this.getList()
            }).catch(error => {
              this.submitLoading = false
            })
          }
        }
      })
    },
    handleUpdate(item) {
      this.form = item
      this.dialogTitle = '修改积分'
      this.dialogVisible = true
    },
    handleDelete(ids) {
      if (ids === null || ids.length === 0) {
        this.$modal.msgWarning('未选中积分列表')
        return
      }
      this.$modal.confirm('是否确认删除积分编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true
        return deletePoints({
          userIds: ids
        })
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.loading = false
        this.getList()
      }).catch((error) => {
        this.loading = false
      })
    },
    handleAdd() {
      this.dialogTitle = '新增积分'
      this.dialogVisible = true
    },
    handleClose() {
      this.form = {
        menuCheckStrictly: true
      }
      this.dialogVisible = false
    },
    handleSelectionChange(val) {
      this.multipleSelectionIds = val.map(item => item.userId)
    }
  }
}
</script>

<style lang="scss" scoped>
.points-container {
  padding: 20px;
}

.btn-div {
  display: flex;
  justify-content: space-between;
  align-content: center;
}

</style>
