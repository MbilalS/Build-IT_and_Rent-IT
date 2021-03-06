import Vue from 'vue'
import App from './App.vue'
import router from './router'
import animated from 'animate.css'
import Buefy from 'buefy'
import 'buefy/lib/buefy.css'
import moment from "moment"

Vue.use(Buefy)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
