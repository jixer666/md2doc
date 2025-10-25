<template>
  <el-dialog
    title="积分充值"
    :visible="dialogVisible"
    width="600px"
    :before-close="handleRechargeClose"
    @close="handleRechargeClose"
  >
    <div class="recharge-container">
      <!-- 原有的充值选项暂时隐藏 -->
      <div class="price-options">
        <div
          v-for="plan in rechargePlans"
          :key="plan.id"
          class="price-card"
          :class="{ active: selectedPlan === plan.id }"
          @click="selectPlan(plan.id)"
        >
          <div class="price-amount">{{ plan.points }}积分</div>
          <div class="price-value">￥{{ plan.price }}</div>
          <div v-if="plan.discount" class="price-discount">
            {{ plan.discount }}
          </div>
        </div>
      </div>

      <div class="maintenance-notice">
        <div class="notice-content">
          <h3>支付功能维护中</h3>
          <p class="contact-info">
            请联系客服进行充值
            <span class="highlight">QQ: 2770063826</span>
          </p>
          <div class="contact-actions">
            <el-button
              type="primary"
              size="mini"
              @click="copyQQ"
            >复制QQ号</el-button>
            <el-button
              type="success"
              size="mini"
              @click="openChat"
            >在线联系</el-button>
          </div>
          <p class="notice-footer">我们正在努力完善在线支付功能，敬请期待！</p>
        </div>
      </div>

      <!-- <div class="payment-methods">
        <div class="method-title">支付方式</div>
        <div class="methods">
          <el-radio-group v-model="paymentMethod">
            <el-radio label="alipay">支付宝</el-radio>
            <el-radio label="wechat">微信支付</el-radio>
            <el-radio label="bank">银行卡</el-radio>
          </el-radio-group>
        </div>
      </div> -->
    </div>

    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog">取 消</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: 'RechargeDialog',
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    rechargePlans: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      selectedPlan: null, // 选中的充值套餐
      paymentMethod: 'alipay' // 默认支付方式
    }
  },
  methods: {
    // 关闭充值对话框
    handleRechargeClose(done) {
      this.selectedPlan = null
      this.$emit('update:dialogVisible', false)
      if (done) done()
    },

    // 新增关闭对话框方法
    closeDialog() {
      this.$emit('update:dialogVisible', false)
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

      const selectedPlan = this.rechargePlans.find(
        (plan) => plan.id === this.selectedPlan
      )

      this.$emit('confirm-recharge', selectedPlan)
      // 重置选择
      this.selectedPlan = null
      this.$emit('update:dialogVisible', false)
    },

    // 复制QQ号
    copyQQ() {
      navigator.clipboard
        .writeText('2770063826')
        .then(() => {
          this.$message.success('QQ号已复制到剪贴板')
        })
        .catch(() => {
          this.$message.error('复制失败，请手动复制')
        })
    },

    // 打开在线聊天
    openChat() {
      window.open(
        'https://wpa.qq.com/msgrd?v=3&uin=2770063826&site=qq&menu=yes'
      )
    }
  }
}
</script>

<style scoped>
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

.notice-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.notice-icon {
  font-size: 24px;
  color: #e6a23c;
  margin-right: 10px;
}

.notice-content {
  flex: 1;
}

.notice-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 5px;
}

.notice-message {
  font-size: 14px;
  color: #606266;
  margin-bottom: 5px;
}

.qq-number {
  font-weight: 700;
  color: #409eff;
}

.notice-tip {
  font-size: 12px;
  color: #909399;
}
</style>
