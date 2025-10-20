<template>
  <div class="container">
    <el-header class="header">
      <div class="navbar">
        <div class="logo">MD2DOC</div>
        <div class="nav-slogan">
          <span class="slogan-text">AI智能解析 · 专业格式转换 · 多平台无缝对接 · 高效文档生产力</span>
        </div>
        <div class="nav-actions">
          <el-tooltip class="item" effect="dark" content="若预览格式有问题，可以登录后用AI专业转换解决" placement="bottom">
            <el-button size="small" icon="el-icon-question" @click="previewMdContent">免费转换预览</el-button>
          </el-tooltip>
          <el-button v-if="isLogin" size="small" icon="el-icon-question" @click="previewMdContent">AI专业转换</el-button>
          <el-button type="primary" size="small" @click="openLoginDrawer">导出</el-button>
        </div>
      </div>
    </el-header>

    <div class="editor-layout">
      <!-- 左侧：原始编辑器 -->
      <div class="editor-panel">
        <div class="panel-header">编辑区域</div>
        <v-md-editor
          v-model="text"
          class="editor"
          :left-toolbar="leftToolbar"
          :right-toolbar="rightToolbar"
          mode="edit"
          @change="handleEditorChange"
        />
      </div>

      <!-- 右侧：预览编辑器 -->
      <div class="editor-panel">
        <div class="panel-header">预览区域</div>
        <v-md-preview
          :text="previewContent"
          class="editor"
        />
      </div>
    </div>

    <!-- 登录抽屉组件 -->
    <login-drawer ref="loginDrawer" />
  </div>
</template>

<script>
import { previewTransMd } from '@/api/biz/trans'
import LoginDrawer from './login/index.vue'

export default {
  name: 'HomeView',
  components: {
    LoginDrawer
  },
  data() {
    return {
      text: '', // 原始内容
      previewContent: '阿斯顿撒', // 预览内容
      // 自定义左侧工具栏
      leftToolbar:
        'undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code',
      // 自定义右侧工具栏
      rightToolbar: 'fullscreen',
      isLogin: false
    }
  },
  mounted() {
    this.isLogin = this.$store.getters.roles && this.$store.getters.roles.length > 0
  },
  methods: {
    previewMdContent() {
      if (!this.text) {
        return
      }
      previewTransMd({
        content: this.text
      }).then((res) => {
        this.previewContent = res.data.content
      })
    },
    handleEditorChange() {
      this.previewContent = this.text
    },
    // 打开登录抽屉
    openLoginDrawer() {
      if (!this.isLogin) {
        this.$refs.loginDrawer.openDrawer()
      }
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  background-color: #f5f5f5;
  box-sizing: border-box;
  overflow: hidden;
}

.header {
  flex-shrink: 0;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 0;
  width: 100%;
}

.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
  padding: 0 20px;
  position: relative;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
  margin-right: 30px;
  flex-shrink: 0;
}

.nav-slogan {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  text-align: center;
}

.slogan-text {
  font-size: 16px;
  color: #606266;
  font-weight: 500;
  background: linear-gradient(135deg, #409eff, #67c23a);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  white-space: nowrap;
  padding: 4px 12px;
  border-radius: 6px;
  background-color: rgba(64, 158, 255, 0.08);
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-shrink: 0;
}

.nav-menu {
  flex: 1;
  border-bottom: none !important;
}

.nav-menu ::v-deep .el-menu-item {
  height: 60px;
  line-height: 60px;
  border-bottom: 2px solid transparent;
}

.nav-menu ::v-deep .el-menu-item.is-active {
  color: #409eff;
  border-bottom: 2px solid #409eff;
}

.editor-layout {
  flex: 1;
  display: flex;
  min-height: 0;
  margin: 0;
  gap: 10px;
  padding: 10px;
  box-sizing: border-box;
}

.editor-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  border-radius: 4px;
  overflow: hidden;
  background: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.panel-header {
  padding: 10px 15px;
  background: #f8f9fa;
  border-bottom: 1px solid #e9ecef;
  font-weight: bold;
  color: #495057;
}

.editor {
  width: 100%;
  height: 100%;
  overflow: auto;
}

/* 添加额外的防护样式 */
.editor ::v-deep .v-md-editor {
  width: 100%;
  height: 100%;
}

.editor ::v-deep .scrollbar__wrap {
  overflow: auto;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .slogan-text {
    font-size: 14px;
    padding: 2px 8px;
  }

  .navbar {
    padding: 0 10px;
  }

  .logo {
    margin-right: 15px;
    font-size: 18px;
  }
}
</style>
