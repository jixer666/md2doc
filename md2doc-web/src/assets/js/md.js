import Vue from 'vue';
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import createKatexPlugin from '@kangc/v-md-editor/lib/plugins/katex/cdn';

import hljs from 'highlight.js';

githubTheme.Hljs = {
  ...hljs,
  highlight: function(lang, code) {
    console.log(lang, code);
    if (!lang) return code;
    return hljs.highlight(lang, code).value;
  }
};

VMdEditor.use(githubTheme, {
  Hljs: hljs,
});
VMdPreview.use(githubTheme, {
  Hljs: hljs,
});

VMdEditor.use(createKatexPlugin());
VMdPreview.use(createKatexPlugin());

Vue.use(VMdPreview);
Vue.use(VMdEditor);
