import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import zhLocate from 'element-plus/es/locale/lang/zh-cn'

import { createApp } from 'vue'

import './style.css'
import 'element-plus/dist/index.css'



const app = createApp(App)
app.use(router)
app.use(ElementPlus, { locale: zhLocate }) // 确保这一行存在
app.mount('#app')
