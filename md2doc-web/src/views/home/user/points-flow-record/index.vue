<template>
  <div class="points-container">
    <el-table v-loading="loading" :data="tableList" style="width: 100%">
      <el-table-column label="ID" prop="id" align="center" width="80" />
      <el-table-column
        label="变动前积分"
        prop="beforePoints"
        align="center"
        width="120"
      />
      <el-table-column
        label="变动积分"
        prop="changePoints"
        align="center"
        width="120"
      >
        <template slot-scope="scope">
          <span :class="scope.row.changePoints > 0 ? 'positive' : 'negative'">
            {{ scope.row.changePoints > 0 ? "+" : ""
            }}{{ scope.row.changePoints }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        label="变动后积分"
        prop="afterPoints"
        align="center"
        width="120"
      />
      <el-table-column
        label="变动类型"
        prop="changeType"
        align="center"
        width="120"
      >
        <template slot-scope="scope">
          <el-tag
            :type="
              scope.row.changeType === '1'
                ? 'success'
                : scope.row.changeType === '2'
                  ? 'danger'
                  : 'warning'
            "
          >
            {{
              scope.row.changeType === "1"
                ? "充值"
                : scope.row.changeType === "2"
                  ? "消费"
                  : "奖励"
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="备注"
        prop="remark"
        align="center"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="创建时间"
        prop="createTime"
        align="center"
        width="160"
      />
    </el-table>

    <pagination
      v-show="searchForm.total > 0"
      :total="searchForm.total"
      :page.sync="searchForm.pageNum"
      :limit.sync="searchForm.pageSize"
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
        pageSize: 10,
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
