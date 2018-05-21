import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import About from './views/About.vue'
import OrderCreation from './components/sales/OrderCreation.vue'
import OrderAproval from './components/sales/OrderAproval.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      component: About
    },
    {
      path: '/ordercreation',
      name: 'ordercreation',
      component: OrderCreation
    },
    {
      path: '/orderaproval',
      name: 'orderaproval',
      component: OrderAproval
    }
  ]
})
