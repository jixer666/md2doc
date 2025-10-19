import Vue from 'vue';
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';


import createKatexPlugin from '@kangc/v-md-editor/lib/plugins/katex/cdn';


// highlightjs
import hljs from 'highlight.js';

VMdEditor.use(githubTheme, {
  Hljs: hljs,
});
VMdEditor.use(createKatexPlugin());


Vue.use(VMdEditor);
