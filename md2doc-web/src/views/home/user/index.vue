<template>
  <div>
    <!-- 登录/注册抽屉 -->
    <el-drawer
      :visible.sync="drawer"
      :with-header="false"
      size="500px"
      direction="rtl"
      custom-class="user-drawer"
      :before-close="handleClose"
    >
      <div class="drawer-container">
        <!-- 头部标题 -->
        <div class="drawer-header">
          <h2>个人信息</h2>
          <el-button
            icon="el-icon-close"
            circle
            class="close-btn"
            @click="closeDrawer"
          />
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
                <span class="points-value">111</span>
                <el-button size="mini" type="primary" class="recharge-btn" @click="showRechargeDialog">充值</el-button>
              </div>
            </div>
          </div>

          <!-- 功能标签页 -->
          <div class="tabs-container">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="转换记录" name="first">
                <div class="tab-content">
                  <el-empty description="暂无转换记录" />
                </div>
              </el-tab-pane>
              <el-tab-pane label="导出记录" name="second">
                <div class="tab-content">
                  <el-empty description="暂无导出记录" />
                </div>
              </el-tab-pane>
              <el-tab-pane label="积分流水" name="third">
                <div class="tab-content">
                  <el-empty description="暂无积分流水" />
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
    <el-dialog title="积分充值" :visible.sync="dialogVisible" width="600px" :before-close="handleRechargeClose">
      <div class="recharge-container">
        <div class="price-options">
          <div
            v-for="plan in rechargePlans"
            :key="plan.id"
            class="price-card"
            :class="{ 'active': selectedPlan === plan.id }"
            @click="selectPlan(plan.id)"
          >
            <div class="price-amount">{{ plan.points }}积分</div>
            <div class="price-value">￥{{ plan.price }}</div>
            <div v-if="plan.discount" class="price-discount">{{ plan.discount }}</div>
          </div>
        </div>

        <div class="payment-methods">
          <div class="method-title">支付方式</div>
          <div class="methods">
            <el-radio-group v-model="paymentMethod">
              <el-radio label="alipay">支付宝</el-radio>
              <el-radio label="wechat">微信支付</el-radio>
              <el-radio label="bank">银行卡</el-radio>
            </el-radio-group>
          </div>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmRecharge">确认充值</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'LoginDrawer',
  data() {
    return {
      drawer: false, // 控制抽屉显示
      loading: false, // 提交按钮加载状态
      activeName: 'first', // 默认激活的标签页
      dialogVisible: false, // 充值对话框显示控制
      selectedPlan: null, // 选中的充值套餐
      paymentMethod: 'alipay', // 默认支付方式
      rechargePlans: [
        { id: 1, points: 100, price: 10, discount: '' },
        { id: 2, points: 500, price: 45, discount: '节省5元' },
        { id: 3, points: 1000, price: 80, discount: '节省20元' },
        { id: 4, points: 3000, price: 210, discount: '节省90元' }
      ]
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
    },

    // 标签页点击事件
    handleClick(tab, event) {
      console.log(tab, event)
    },

    // 显示充值对话框
    showRechargeDialog() {
      this.dialogVisible = true
    },

    // 关闭充值对话框
    handleRechargeClose(done) {
      this.selectedPlan = null
      done()
    },

    // 选择充值套餐
    selectPlan(planId) {
      this.selectedPlan = planId
    },

    // 确认充值
    confirmRecharge() {
      if (!this.selectedPlan) {
        this.$message.warning('请选择充值套餐')
        return
      }

      const selectedPlan = this.rechargePlans.find(plan => plan.id === this.selectedPlan)
      this.$message.success(`已选择${selectedPlan.points}积分套餐，金额：${selectedPlan.price}元`)
      // 这里可以调用实际的充值接口
      this.dialogVisible = false
      this.selectedPlan = null
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
  padding: 15px 0;
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

/* 充值对话框样式 */
.recharge-container {
  padding: 20px 0;
}

.price-options {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  margin-bottom: 30px;
}

.price-card {
  border: 2px solid #e9ecef;
  border-radius: 8px;
  padding: 15px 10px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
}

.price-card:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.2);
}

.price-card.active {
  border-color: #409eff;
  background-color: #ecf5ff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.2);
}

.price-amount {
  font-size: 16px;
  font-weight: 600;
  color: #212529;
  margin-bottom: 5px;
}

.price-value {
  font-size: 18px;
  font-weight: 700;
  color: #409eff;
  margin-bottom: 5px;
}

.price-discount {
  font-size: 12px;
  color: #67c23a;
}

.payment-methods {
  border-top: 1px solid #e9ecef;
  padding-top: 20px;
}

.method-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 15px;
  color: #212529;
}

.methods {
  padding-left: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
