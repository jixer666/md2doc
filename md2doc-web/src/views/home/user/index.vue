<template>
  <div>
    <!-- 登录/注册抽屉 -->
    <el-drawer
      :visible.sync="drawer"
      :with-header="false"
      size="600px"
      direction="rtl"
      custom-class="user-drawer"
      :before-close="handleClose"
    >
      <div class="drawer-container">
        <!-- 头部标题 -->
        <div class="drawer-header">
          <h2>个人信息</h2>
          <div class="header-actions">
            <el-button
              size="small"
              type="danger"
              class="logout-btn-header"
              @click="handleLogout"
            >退出账号</el-button>
            <el-button
              icon="el-icon-close"
              circle
              class="close-btn"
              @click="closeDrawer"
            />
          </div>
        </div>

        <div class="user-container">
          <!-- 用户基本信息卡片 -->
          <div class="user-card">
            <div class="avatar-wrapper">
              <el-avatar :src="$store.getters.avatar" :size="80" />
            </div>
            <div class="user-details">
              <div class="u-nickname">{{ $store.getters.name }}</div>
              <div class="points-info">
                <span class="points-label">积分：</span>
                <span class="points-value">{{
                  pointsInfo.availablePoints
                }}</span>
                <el-button
                  size="mini"
                  type="primary"
                  class="recharge-btn"
                  @click="showRechargeDialog"
                >充值</el-button>
              </div>
            </div>
          </div>

          <!-- 功能标签页 -->
          <div class="tabs-container">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="转换记录" name="first">
                <div class="tab-content">
                  <TransRecord ref="transRecord" />
                </div>
              </el-tab-pane>
              <el-tab-pane label="积分流水" name="second">
                <div class="tab-content">
                  <PointsFlowRecord ref="pointsFlowRecord" />
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>

        <!-- 底部信息 -->
        <div class="drawer-footer">
          <p>© 2023 MD2DOC. All rights reserved.</p>
        </div>
      </div>
    </el-drawer>

    <!-- 充值价格表对话框 -->
    <RechargeDialog
      :dialog-visible.sync="dialogVisible"
      :recharge-plans="rechargePlans"
      @confirm-recharge="handleRechargeConfirm"
    />
  </div>
</template>

<script>
import TransRecord from './tans-record'
import PointsFlowRecord from './points-flow-record'
import RechargeDialog from './recharge/index.vue'
import { getUserPoints } from '@/api/biz/points'

export default {
  name: 'LoginDrawer',
  components: {
    TransRecord,
    PointsFlowRecord,
    RechargeDialog
  },
  data() {
    return {
      drawer: false, // 控制抽屉显示
      loading: false, // 提交按钮加载状态
      activeName: 'first', // 默认激活的标签页
      dialogVisible: false, // 充值对话框显示控制
      rechargePlans: [
        { id: 1, points: 100, price: 10, discount: '' },
        { id: 2, points: 500, price: 45, discount: '节省5元' },
        { id: 3, points: 1000, price: 80, discount: '节省20元' },
        { id: 4, points: 3000, price: 210, discount: '节省90元' }
      ],
      pointsInfo: 0 // 用户积分
    }
  },
  mounted() {},
  methods: {
    // 关闭抽屉前的处理
    handleClose(done) {
      done()
    },

    // 关闭抽屉
    closeDrawer() {
      this.drawer = false
    },

    // 从父组件打开抽屉的方法
    openDrawer() {
      this.drawer = true
      // 调用接口获取用户积分
      this.fetchUserPoints()
      // 打开抽屉时，默认加载第一个标签页的数据
      this.$nextTick(() => {
        if (this.activeName === 'first' && this.$refs.transRecord) {
          this.$refs.transRecord.getList()
        }
      })
    },

    // 获取用户积分
    fetchUserPoints() {
      getUserPoints().then(response => {
        this.pointsInfo = response.data
      }).catch(error => {
        console.error('获取用户积分失败:', error)
        this.$message.error('获取积分信息失败')
      })
    },

    // 标签页点击事件
    handleClick(tab, event) {
      if (tab.name === 'first' && this.$refs.transRecord) {
        this.$refs.transRecord.getList()
      } else if (tab.name === 'second' && this.$refs.pointsFlowRecord) {
        this.$refs.pointsFlowRecord.getList()
      }
    },

    // 显示充值对话框
    showRechargeDialog() {
      this.dialogVisible = true
    },

    // 退出登录
    async handleLogout() {
      try {
        await this.$store.dispatch('user/logout')
        this.$message.success('退出登录成功')
        this.drawer = false
        window.location.reload()
      } catch (error) {
        this.$message.error('退出登录失败')
      }
    },

    handleRechargeConfirm() {

    }
  }
}
</script>

<style scoped>
.drawer-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.drawer-header {
  padding: 20px;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.drawer-header h2 {
  margin: 0;
  color: #333;
  font-weight: 600;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.close-btn {
  padding: 8px;
  border: none;
  background: transparent;
  font-size: 18px;
  color: #909399;
}

.close-btn:hover {
  color: #409eff;
  background: #f5f5f5;
}

.logout-btn-header {
  padding: 6px 12px;
  font-size: 14px;
}

.user-container {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

/* 用户卡片样式 */
.user-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 15px 20px;
  color: #333;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  margin-bottom: 15px;
  border: 1px solid #e9ecef;
}

.avatar-wrapper {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
}

.user-details {
  text-align: center;
}

.u-nickname {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 15px;
  color: #212529;
}

.points-info {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.points-label {
  font-size: 16px;
}

.points-value {
  font-size: 18px;
  font-weight: 700;
  color: #409eff;
}

.recharge-btn {
  margin-left: 10px;
}

/* 标签页容器 */
.tabs-container {
  flex: 1;
}

.tab-content {
  min-height: 200px;
}

::v-deep .el-tabs__item {
  font-size: 14px;
  font-weight: 500;
}

::v-deep .el-tabs__content {
  height: calc(100% - 55px);
}

.drawer-footer {
  padding: 20px;
  text-align: center;
  border-top: 1px solid #eee;
  color: #999;
  font-size: 12px;
}

/* 深度选择器，用于覆盖element-ui默认样式 */
::v-deep .user-drawer {
  overflow: hidden;
}

::v-deep .el-drawer__body {
  padding: 0;
  height: 100%;
}

</style>
