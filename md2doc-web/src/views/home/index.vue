<template>
  <div v-loading="loading" class="container">
    <el-header class="header">
      <div class="navbar">
        <div class="logo">MD2DOC</div>
        <div class="nav-slogan">
          <span class="slogan-text">AI智能解析 · 多平台无缝对接 · 一键格式转换Word文档</span>
        </div>
        <div class="nav-actions">
          <el-tooltip class="item" effect="dark" placement="bottom">
            <div slot="content">
              <div>若预览数学符号等格式有问题，点击可对特殊符号内容进行转换</div>
              <div>若转换后问题任然出现，可以登录后用AI专业格式转换解决</div>
            </div>
            <el-button size="small" icon="el-icon-question" @click="previewMdContent">免费格式转换</el-button>
          </el-tooltip>
          <el-button v-if="isLogin" type="danger" size="small" @click="previewAiMdContent">AI专业转换</el-button>
          <el-button type="primary" size="small" @click="exportMdContent">导出文档</el-button>

          <el-avatar v-if="isLogin" :src="$store.getters.avatar" :size="35" class="u-avatar" @click.native="openUserDrawer" />
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
    <user-drawer ref="userDrawer" />

  </div>
</template>

<script>
import { previewTransMd, exportTransMd } from '@/api/biz/trans'
import LoginDrawer from './login/index.vue'
import UserDrawer from './user/index.vue'

export default {
  name: 'HomeView',
  components: {
    LoginDrawer,
    UserDrawer
  },
  data() {
    return {
      text: '', // 原始内容
      previewContent: '', // 预览内容
      // 自定义左侧工具栏
      leftToolbar:
        'undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code',
      // 自定义右侧工具栏
      rightToolbar: 'fullscreen',
      isLogin: false,
      loading: false
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
      this.loading = true
      previewTransMd({
        preContent: this.text
      }).then((res) => {
        this.previewContent = res.data.transContent
        this.loading = false
      }).catch(error => {
        console.error('操作失败:', error)
        this.loading = false
      })
    },
    previewAiMdContent() {
      this.$message.warning('待开发')
    },
    handleEditorChange() {
      this.previewContent = this.text
    },
    // 打开登录抽屉
    openLoginDrawer() {
      if (!this.isLogin) {
        this.$refs.loginDrawer.openDrawer()
      }
    },
    openUserDrawer() {
      this.$refs.userDrawer.openDrawer()
    },
    exportMdContent() {
      if (!this.isLogin) {
        this.openLoginDrawer()
        return
      }
      this.loading = true
      exportTransMd({
        preContent: this.previewContent
      }).then(res => {
        this.$modal.msgSuccess('导出成功')
        this.loading = false
      }).catch(error => {
        console.error('操作失败:', error)
        this.loading = false
      })
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
  overflow-y: hidden;
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
  padding: 0 40px;
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
  //background: linear-gradient(135deg, #409eff, #67c23a);
  //-webkit-background-clip: text;
  //-webkit-text-fill-color: transparent;
  //background-clip: text;
  //white-space: nowrap;
  padding: 4px 12px;
  border-radius: 6px;
  //background-color: rgba(64, 158, 255, 0.08);
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

.u-avatar {
  margin-left: 30px;
  cursor: pointer;
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
