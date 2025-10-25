<template>
  <div>
    <!-- 登录/注册抽屉 -->
    <el-drawer
      :visible.sync="drawer"
      :with-header="false"
      size="400px"
      direction="rtl"
      custom-class="login-drawer"
      :before-close="handleClose"
    >
      <div class="drawer-container">
        <!-- 头部标题 -->
        <div class="drawer-header">
          <h2>{{ isLogin ? "用户登录" : "用户注册" }}</h2>
          <el-button
            icon="el-icon-close"
            circle
            class="close-btn"
            @click="closeDrawer"
          />
        </div>

        <!-- 表单区域 -->
        <div class="form-container">
          <el-form
            ref="authForm"
            :model="formData"
            :rules="rules"
            label-width="0px"
            class="auth-form"
          >
            <!-- 用户名 -->
            <el-form-item prop="username">
              <el-input
                v-model="formData.username"
                placeholder="请输入用户名"
                prefix-icon="el-icon-user"
                clearable
              />
            </el-form-item>

            <!-- 密码 -->
            <el-form-item prop="password">
              <el-input
                v-model="formData.password"
                type="password"
                placeholder="请输入密码"
                prefix-icon="el-icon-lock"
                show-password
              />
            </el-form-item>

            <!-- 注册时需要确认密码 -->
            <el-form-item v-if="!isLogin" prop="confirmPassword">
              <el-input
                v-model="formData.confirmPassword"
                type="password"
                placeholder="请确认密码"
                prefix-icon="el-icon-lock"
                show-password
              />
            </el-form-item>

            <!-- 注册时需要邮箱 -->
            <el-form-item v-if="!isLogin" prop="email">
              <el-input
                v-model="formData.email"
                type="email"
                placeholder="请输入邮箱"
                prefix-icon="el-icon-message"
                clearable
              />
            </el-form-item>

            <!-- 注册时需要邮箱验证码 -->
            <el-form-item v-if="!isLogin" prop="emailCode">
              <el-row :gutter="10">
                <el-col :span="13">
                  <el-input
                    v-model="formData.emailCode"
                    placeholder="请输入邮箱验证码"
                    prefix-icon="el-icon-key"
                    clearable
                  />
                </el-col>
                <el-col :span="11">
                  <el-button
                    :disabled="isSendEmailCodeDisabled"
                    style="width: 100%"
                    @click="sendEmailCode"
                  >
                    {{ emailCodeButtonText }}
                  </el-button>
                </el-col>
              </el-row>
            </el-form-item>

            <el-form-item v-if="!isLogin" prop="code">
              <el-row>
                <el-col :span="8">
                  <el-image
                    :src="formData.captchaImg"
                    style="height: 36px; wight: 100px"
                    @click="loadCaptchaImg"
                  />
                </el-col>
                <el-col :span="16">
                  <el-input
                    v-model="formData.code"
                    type="text"
                    placeholder="请输入图形验证码"
                  />
                </el-col>
              </el-row>
            </el-form-item>

            <!-- 登录/注册按钮 -->
            <el-form-item>
              <el-button
                type="primary"
                class="submit-btn"
                style="width: 100%"
                :loading="loading"
                @click="handleSubmit"
              >
                {{ isLogin ? "登录" : "注册" }}
              </el-button>
            </el-form-item>

            <!-- 切换登录/注册 -->
            <el-form-item>
              <div class="switch-tip">
                {{ isLogin ? "还没有账号？" : "已有账号？" }}
                <el-button type="text" @click="toggleAuthMode">
                  {{ isLogin ? "立即注册" : "立即登录" }}
                </el-button>
              </div>
            </el-form-item>
          </el-form>
        </div>

        <!-- 底部信息 -->
        <div class="drawer-footer">
          <p>© 2023 MD2DOC. All rights reserved.</p>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getCaptchaImg, register } from '@/api/system/user'
import { sendRegisterEmail } from '@/api/system/email'

export default {
  name: 'LoginDrawer',
  data() {
    // 确认密码验证规则
    const validateConfirmPassword = (rule, value, callback) => {
      if (!this.isLogin && value !== this.formData.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }

    // 邮箱验证规则
    const validateEmail = (rule, value, callback) => {
      if (!this.isLogin && !value) {
        callback(new Error('请输入邮箱'))
      } else {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        if (!this.isLogin && !emailRegex.test(value)) {
          callback(new Error('请输入正确的邮箱格式'))
        } else {
          callback()
        }
      }
    }

    // 邮箱验证码验证规则
    const validateEmailCode = (rule, value, callback) => {
      if (!this.isLogin && !value) {
        callback(new Error('请输入邮箱验证码'))
      } else {
        callback()
      }
    }

    const validateCode = (rule, value, callback) => {
      if (!this.isLogin && !value) {
        callback(new Error('请输入图形验证码'))
      } else {
        callback()
      }
    }

    return {
      drawer: false, // 控制抽屉显示
      isLogin: true, // true为登录模式，false为注册模式
      loading: false, // 提交按钮加载状态
      // 邮箱验证码相关
      isSendEmailCodeDisabled: false,
      emailCodeButtonText: '发送验证码',
      emailCodeTimer: null,
      emailCodeCountdown: 60,
      formData: {
        username: '',
        password: '',
        confirmPassword: '',
        emailUuid: '',
        email: '',
        emailCode: '',
        authType: 1,
        uuid: '',
        captchaImg: '',
        code: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 3,
            max: 20,
            message: '长度在 3 到 20 个字符',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 6,
            max: 20,
            message: '长度在 6 到 20 个字符',
            trigger: 'blur'
          }
        ],
        confirmPassword: [
          { validator: validateConfirmPassword, trigger: 'blur' }
        ],
        email: [{ validator: validateEmail, trigger: 'blur' }],
        emailCode: [{ validator: validateEmailCode, trigger: 'blur' }],
        code: [{ validator: validateCode, trigger: 'blur' }]
      }
    }
  },
  mounted() {
    // 页面加载时检查是否有未完成的倒计时
    this.checkAndRestoreCountdown()
  },
  beforeDestroy() {
    // 清除定时器
    if (this.emailCodeTimer) {
      clearInterval(this.emailCodeTimer)
    }
  },
  methods: {
    loadCaptchaImg() {
      getCaptchaImg().then((res) => {
        this.formData.uuid = res.data.uuid
        this.formData.captchaImg = 'data:image/gif;base64,' + res.data.img
      })
    },

    // 检查并恢复倒计时状态
    checkAndRestoreCountdown() {
      const countdownData = localStorage.getItem('emailCodeCountdown')
      if (countdownData) {
        const { timestamp, remainingTime } = JSON.parse(countdownData)
        const now = Date.now()
        const elapsed = Math.floor((now - timestamp) / 1000)
        const actualRemaining = remainingTime - elapsed

        if (actualRemaining > 0) {
          // 恢复倒计时
          this.isSendEmailCodeDisabled = true
          this.emailCodeCountdown = actualRemaining
          this.emailCodeButtonText = `${this.emailCodeCountdown}秒后重新发送`

          // 继续倒计时
          this.emailCodeTimer = setInterval(() => {
            this.emailCodeCountdown--
            this.emailCodeButtonText = `${this.emailCodeCountdown}秒后重新发送`

            // 更新本地存储
            const newCountdownData = {
              timestamp: Date.now(),
              remainingTime: this.emailCodeCountdown
            }
            localStorage.setItem('emailCodeCountdown', JSON.stringify(newCountdownData))

            if (this.emailCodeCountdown <= 0) {
              this.resetEmailCodeButton()
              // 清除本地存储
              localStorage.removeItem('emailCodeCountdown')
            }
          }, 1000)
        } else {
          // 倒计时已结束，清除状态
          this.resetEmailCodeButton()
          localStorage.removeItem('emailCodeCountdown')
        }
      }
    },

    // 发送邮箱验证码
    sendEmailCode() {
      if (!this.formData.email) {
        this.$message.warning('请先输入邮箱')
        return
      }

      // 验证邮箱格式
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      if (!emailRegex.test(this.formData.email)) {
        this.$message.warning('请输入正确的邮箱格式')
        return
      }

      sendRegisterEmail({
        email: this.formData.email
      })
        .then((res) => {
          this.formData.emailUuid = res.data.emailUuid
          this.$message.success('验证码已发送至您的邮箱，请查收')
          // 启动倒计时
          this.startEmailCodeCountdown()
        })
        .catch((error) => {
          console.log('操作失败:', error)
          this.$message.error('验证码发送失败，请稍后重试')
        })
    },

    // 开始邮箱验证码倒计时
    startEmailCodeCountdown() {
      this.isSendEmailCodeDisabled = true
      this.emailCodeCountdown = 60
      this.emailCodeButtonText = `${this.emailCodeCountdown}秒后重新发送`

      // 保存倒计时状态到本地存储
      const countdownData = {
        timestamp: Date.now(),
        remainingTime: this.emailCodeCountdown
      }
      localStorage.setItem('emailCodeCountdown', JSON.stringify(countdownData))

      this.emailCodeTimer = setInterval(() => {
        this.emailCodeCountdown--
        this.emailCodeButtonText = `${this.emailCodeCountdown}秒后重新发送`

        // 更新本地存储
        const newCountdownData = {
          timestamp: Date.now(),
          remainingTime: this.emailCodeCountdown
        }
        localStorage.setItem('emailCodeCountdown', JSON.stringify(newCountdownData))

        if (this.emailCodeCountdown <= 0) {
          this.resetEmailCodeButton()
          // 清除本地存储
          localStorage.removeItem('emailCodeCountdown')
        }
      }, 1000)
    },

    // 重置邮箱验证码按钮
    resetEmailCodeButton() {
      clearInterval(this.emailCodeTimer)
      this.isSendEmailCodeDisabled = false
      this.emailCodeButtonText = '发送验证码'
    },

    // 切换登录/注册模式
    toggleAuthMode() {
      this.isLogin = !this.isLogin
      if (!this.isLogin) {
        this.loadCaptchaImg()
      }
      // 清空表单验证
      this.$refs.authForm.clearValidate()
    },

    // 提交表单
    handleSubmit() {
      this.$refs.authForm.validate((valid) => {
        if (valid) {
          if (this.isLogin) {
            this.handleLogin()
          } else {
            this.handleRegister()
          }
        }
      })
    },

    // 登录处理
    handleLogin() {
      this.loading = true
      this.$store
        .dispatch('user/login', this.formData)
        .then(() => {
          this.$message.success('登录成功')
          this.loading = false
          this.closeDrawer()
          window.location.reload()
        })
        .catch(() => {
          this.loading = false
        })
    },

    // 注册处理
    handleRegister() {
      this.loading = true
      register(this.formData)
        .then(() => {
          this.$message.success('注册成功，请登录')
          this.isLogin = true
          this.formData = {
            username: '',
            password: '',
            captchaImg: '',
            confirmPassword: '',
            email: '',
            emailCode: '',
            authType: 1,
            code: ''
          }
          // 重置邮箱验证码按钮状态
          this.resetEmailCodeButton()
        })
        .catch(() => {
          this.loading = false
        })
    },

    // 关闭抽屉前的处理
    handleClose(done) {
      this.isLogin = true
      this.formData = {
        username: '',
        password: '',
        captchaImg: '',
        confirmPassword: '',
        email: '',
        emailCode: '',
        authType: 1,
        code: ''
      }
      // 重置邮箱验证码按钮状态
      this.resetEmailCodeButton()
      done()
    },

    // 关闭抽屉
    closeDrawer() {
      this.drawer = false
    },

    // 从父组件打开抽屉的方法
    openDrawer() {
      this.drawer = true
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

.form-container {
  flex: 1;
  padding: 30px 20px;
  overflow-y: auto;
}

.auth-form {
  max-width: 300px;
  margin: 0 auto;
}

.submit-btn {
  margin-top: 20px;
}

.switch-tip {
  text-align: center;
  margin-top: 20px;
  color: #666;
}

.drawer-footer {
  padding: 20px;
  text-align: center;
  border-top: 1px solid #eee;
  color: #999;
  font-size: 12px;
}

/* 深度选择器，用于覆盖element-ui默认样式 */
::v-deep .login-drawer {
  overflow: hidden;
}

::v-deep .el-drawer__body {
  padding: 0;
  height: 100%;
}
</style>
