import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/user/Login'
import RegisterView from '../views/user/Register'
import NotFoundView from '../views/error/NotFound'
import IndexView from '../views/IndexView'
import BackView from '../views/backstage/BackManagement'
import QueryView from '../views/query/QueryView'


const routes = [
  {
    path: "/",
    name: "home",
    component: IndexView,
  },
  {
    path: "/login/",
    name: "loginview",
    component: LoginView,
  },
  {
    path: "/register/",
    name: "registerview",
    component: RegisterView,
  },
  {
    path: "/backstage/",
    name: "backstageview",
    component: BackView,
  },
  {
    path: "/query/",
    name: "queryview",
    component: QueryView,
  },
  {
    path: "/404/",
    name: "NotFoundview",
    component: NotFoundView,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/",
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
