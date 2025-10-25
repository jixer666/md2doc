<template>
  <div class="pointsFlow-container">
    <div>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="积分流水名称">
          <el-input v-model="searchForm.pointsFlowName" placeholder="请输入积分流水名称" />
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
        <el-table-column key="flowId" label="流水ID" align="center" prop="flowId" :show-overflow-tooltip="true" />
        <el-table-column key="userId" label="用户ID" align="center" prop="userId" :show-overflow-tooltip="true" />
        <el-table-column key="ruleType" label="积分规则类型" align="center" prop="ruleType" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.biz_points_rule_type" :value="scope.row.ruleType" />
          </template>
        </el-table-column>
        <el-table-column key="changePoints" label="变化积分" align="center" prop="changePoints" :show-overflow-tooltip="true" />
        <el-table-column key="totalPoints" label="变化后积分" align="center" prop="totalPoints" :show-overflow-tooltip="true" />
        <el-table-column key="status" label="状态" align="center" width="100">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.common_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
        <el-table-column label="更新时间" align="center" prop="updateTime" width="160" />
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
              @click="handleDelete([scope.row.pointsFlowId])"
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
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" />
        </el-form-item>
        <el-form-item label="积分规则类型" prop="ruleType">
          <el-input v-model="form.ruleType" />
        </el-form-item>
        <el-form-item label="变化积分" prop="changePoints">
          <el-input v-model="form.changePoints" />
        </el-form-item>
        <el-form-item label="变化后积分" prop="totalPoints">
          <el-input v-model="form.totalPoints" />
        </el-form-item>
        <el-form-item label="积分流水状态">
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
import { getPointsFlowPage, addPointsFlow, updatePointsFlow, deletePointsFlow } from '@/api/biz/pointsFlow'

export default {
  name: 'PointsFlow',
  dicts: ['common_status', 'biz_points_rule_type'],
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
        pointsFlowName: [
          { required: true, message: '积分流水名称不能为空', trigger: 'blur' }
        ],
        pointsFlowKey: [
          { required: true, message: '积分流水字符不能为空', trigger: 'blur' }
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
      getPointsFlowPage(this.searchForm).then(res => {
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
          if (this.form.pointsFlowId != undefined) {
            updatePointsFlow(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.submitLoading = false
              this.dialogVisible = false
              this.getList()
            }).catch(error => {
              this.submitLoading = false
            })
          } else {
            addPointsFlow(this.form).then(response => {
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
      this.dialogTitle = '修改积分流水'
      this.dialogVisible = true
    },
    handleDelete(ids) {
      if (ids === null || ids.length === 0) {
        this.$modal.msgWarning('未选中积分流水列表')
        return
      }
      this.$modal.confirm('是否确认删除积分流水编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true
        return deletePointsFlow({
          pointsFlowIds: ids
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
      this.dialogTitle = '新增积分流水'
      this.dialogVisible = true
    },
    handleClose() {
      this.form = {
        menuCheckStrictly: true
      }
      this.dialogVisible = false
    },
    handleSelectionChange(val) {
      this.multipleSelectionIds = val.map(item => item.pointsFlowId)
    }
  }
}
</script>

<style lang="scss" scoped>
.pointsFlow-container {
  padding: 20px;
}

.btn-div {
  display: flex;
  justify-content: space-between;
  align-content: center;
}

</style>
