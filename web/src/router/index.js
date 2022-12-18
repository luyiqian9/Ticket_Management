import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/user/Login'
import RegisterView from '../views/user/Register'
import NotFoundView from '../views/error/NotFound'
import IndexView from '../views/IndexView'
import BackView from '../views/backstage/BackManagement'
import QueryView from '../views/query/QueryView'
import SoldoutView from '../views/backstage/SoldoutInfo'
import CreateView from '../views/backstage/CreateTrain'
import store from '../store/index'


const routes = [
  {
    path: "/",
    name: "home",
    component: IndexView,
    meta: {
      requestAuth: true,
    },
  },
  {
    path: "/login/",
    name: "loginview",
    component: LoginView,
    meta: {
      requestAuth: false,
    },
  },
  {
    path: "/register/",
    name: "registerview",
    component: RegisterView,
    meta: {
      requestAuth: false,
    },
  },
  {
    path: "/backstage/",
    name: "backstageview",
    component: BackView,
    meta: {
      requestAuth: true,
    },
  },
  {
    path: "/query/",
    name: "queryview",
    component: QueryView,
    meta: {
      requestAuth: true,
    },
  },
  {
    path: "/soldoutinfo/",
    name: "soldoutview",
    component: SoldoutView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/create/",
    name: "createview",
    component: CreateView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/404/",
    name: "NotFoundview",
    component: NotFoundView,
    meta: {
      requestAuth: false,
    },
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

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({ name: 'loginview' });
  } else {
    next();
  }
})

export default router
