import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/homepage',
    name: 'homepage',
    component: () => import('../views/Homepage')
  },
  {
    path: '/recipe',
    name: 'recipe',
    component: () => import('../views/Recipe')
  },
  {
    path: '/createRecipe',
    name: 'createRecipe',
    component: () => import('../views/CreateRecipe')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
