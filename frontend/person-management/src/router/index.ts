import { createRouter, createWebHistory } from 'vue-router';
import { useAuthData } from '../store/auth';

const routes = [
  {
    path: '/',
    name: 'Index',
    component: () => import('../pages/index.vue'),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/auth/login',
    name: 'Login',
    component: () => import('../pages/auth/login.vue'),
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/auth/register',
    name: 'Register',
    component: () => import('../pages/auth/register.vue'),
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/physical-person',
    name: 'Index',
    component: () => import('../pages/physical-person/index.vue'),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/physical-person/save/:id?',
    name: 'Save',
    component: () => import('../pages/physical-person/save.vue'),
    meta: {
      requiresAuth: true,
    },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const { requiresAuth } = to.meta;
  const authData = useAuthData();

  if (requiresAuth) {
    if (localStorage.getItem('authData')) {
      next();
    }
    next('/auth/login');
  } else {
    next();
  }
});

export default router;
