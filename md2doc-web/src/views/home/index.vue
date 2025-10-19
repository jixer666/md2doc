<template>
  <div class="container">
    <el-header class="header">
      <div class="navbar">
        <div class="logo">MD2DOC</div>
        <el-menu mode="horizontal" :default-active="activeIndex" class="nav-menu">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">文档</el-menu-item>
          <el-menu-item index="3">关于</el-menu-item>
        </el-menu>
        <div class="nav-actions">
          <el-button type="primary" size="small">导出</el-button>
        </div>
      </div>
    </el-header>

    <div class="editor-container">
      <v-md-editor v-model="text" :toolbar="toolbar" class="editor"></v-md-editor>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
  data() {
    return {
      text: '',
      activeIndex: '1',
      toolbar: {
        // 自定义工具栏按钮
        bold: {
          title: '加粗',
          icon: 'v-md-icon-bold',
        },
        italic: {
          title: '斜体',
          icon: 'v-md-icon-italic',
        },
        heading: {
          title: '标题',
          icon: 'v-md-icon-heading',
        },
        underline: {
          title: '下划线',
          icon: 'v-md-icon-underline',
        }
      }
    }
  },
  mounted() {

  },
  methods: {
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
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
  height: 60px;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
  margin-right: 30px;
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

.nav-actions {
  margin-left: 20px;
}

.editor-container {
  flex: 1;
  min-height: 0;
  margin: 10px;
  border-radius: 4px;
  overflow: hidden;
  position: relative;
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
</style>