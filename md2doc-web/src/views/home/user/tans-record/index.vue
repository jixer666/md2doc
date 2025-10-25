<template>
  <div class="trans-container">
    <el-table v-loading="loading" :data="tableList" style="width: 100%">
      <el-table-column label="转换前" prop="fileSize" align="center">
        <template slot-scope="scope">
          <div class="content-cell">{{ scope.row.preContent }}</div>
        </template>
      </el-table-column>
      <el-table-column label="转换后" prop="fileSize" align="center">
        <template slot-scope="scope">
          <div class="content-cell">{{ scope.row.transContent }}</div>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        align="center"
        width="160"
      />
      <el-table-column label="操作" align="center" width="60">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleDownload(scope.row)"
          >导出</el-button>
        </template>
      </el-table-column>
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
import { getUserTransPage, exportTransMd } from '@/api/biz/trans'
import Pagination from '@/components/Pagination'

export default {
  name: 'TransRecord',
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
        fileName: ''
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
      getUserTransPage(this.searchForm)
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
      this.searchForm.fileName = ''
      this.searchForm.pageNum = 1
      this.getList()
    },

    handlePreview(row) {
      // 预览逻辑
      this.$message.info('预览功能待实现')
    },

    handleDownload(row) {
      exportTransMd({
        transId: row.transId
      })
        .then((res) => {
          this.$modal.msgSuccess('导出成功')
          this.loading = false
        })
        .catch((error) => {
          console.error('操作失败:', error)
          this.loading = false
        })
    }
  }
}
</script>

<style scoped>
.content-cell {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.5;
  max-height: 3em;
}
</style>
