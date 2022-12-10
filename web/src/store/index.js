import { createStore } from 'vuex'
import ModuleUser from './user'   // 引入user.js 供其他地方使用

export default createStore({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
  }
})
