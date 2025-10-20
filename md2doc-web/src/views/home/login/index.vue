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
          <h2>{{ isLogin ? '用户登录' : '用户注册' }}</h2>
          <el-button icon="el-icon-close" circle class="close-btn" @click="closeDrawer" />
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

            <!-- 登录/注册按钮 -->
            <el-form-item>
              <el-button
                type="primary"
                :loading="loading"
                class="submit-btn"
                style="width: 100%"
                @click="handleSubmit"
              >
                {{ isLogin ? '登录' : '注册' }}
              </el-button>
            </el-form-item>

            <!-- 切换登录/注册 -->
            <el-form-item>
              <div class="switch-tip">
                {{ isLogin ? '还没有账号？' : '已有账号？' }}
                <el-button type="text" @click="toggleAuthMode">
                  {{ isLogin ? '立即注册' : '立即登录' }}
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

    return {
      drawer: false, // 控制抽屉显示
      isLogin: true, // true为登录模式，false为注册模式
      loading: false, // 提交按钮加载状态
      formData: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 切换登录/注册模式
    toggleAuthMode() {
      this.isLogin = !this.isLogin
      // 清空表单验证
      this.$refs.authForm.clearValidate()
    },

    // 提交表单
    handleSubmit() {
      this.$refs.authForm.validate((valid) => {
        if (valid) {
          this.loading = true

          // 模拟提交过程
          setTimeout(() => {
            this.loading = false
            if (this.isLogin) {
              this.handleLogin()
            } else {
              this.handleRegister()
            }
          }, 1000)
        }
      })
    },

    // 登录处理
    handleLogin() {
      // 这里应该是实际的登录逻辑
      console.log('登录:', this.formData)
      this.$message.success('登录成功')
      this.closeDrawer()
      // 实际项目中这里会跳转到主页或其他操作
    },

    // 注册处理
    handleRegister() {
      this.$message.success('注册成功，请登录')
      this.isLogin = true // 注册成功后切换到登录模式
      // 清空表单
      this.formData = {
        username: '',
        password: '',
        confirmPassword: ''
      }
    },

    // 关闭抽屉前的处理
    handleClose(done) {
      this.formData = {}
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
  color: #409EFF;
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
