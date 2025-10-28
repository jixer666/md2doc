<template>
  <div class="points-container">
    <el-table v-loading="loading" :data="tableList" style="width: 100%">
      <el-table-column
        label="流水号"
        prop="flowId"
        align="center"
        width="170"
      />
      <el-table-column
        label="变动积分"
        prop="changePoints"
        align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.ruleType === 4">
            + {{ scope.row.changePoints }}</span>
          <span v-else> - {{ scope.row.changePoints }}</span>
        </template></el-table-column>
      <el-table-column
        label="总积分"
        prop="totalPoints"
        align="center"
      />
      <el-table-column
        label="变动类型"
        prop="ruleType"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ruleType === 1">免费转换</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 2">AI专业转换</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 3">导出</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 4">新人注册</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        align="center"
        width="150"
      />
    </el-table>

    <pagination
      v-show="searchForm.total > 0"
      :total="searchForm.total"
      :page.sync="searchForm.pageNum"
      :limit.sync="searchForm.pageSize"
      style="padding: 0px"
      layout="prev, pager, next"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getUserPointsFlowPage } from '@/api/biz/pointsFlow'
import Pagination from '@/components/Pagination'

export default {
  name: 'PointsFlowRecord',
  components: {
    Pagination
  },
  data() {
    return {
      loading: false,
      tableList: [],
      searchForm: {
        pageNum: 1,
        pageSize: 5,
        total: 0,
        changeType: ''
      }
    }
  },
  // 移除created钩子中的自动数据获取
  created() {
    // 不再在创建时自动获取数据
  },
  methods: {
    getList() {
      this.loading = true
      getUserPointsFlowPage(this.searchForm)
        .then((response) => {
          this.tableList = response.data.list
          this.searchForm.total = parseInt(response.data.total)
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },

    resetForm() {
      this.searchForm.changeType = ''
      this.searchForm.pageNum = 1
      this.getList()
    }
  }
}
</script>

<style scoped></style>
