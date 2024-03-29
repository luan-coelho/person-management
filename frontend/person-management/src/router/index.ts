import {createRouter, createWebHistory} from 'vue-router';
import fetchApi, {get} from "../utils/fetchApi";
import apiRoutes from "./api-routes";

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
    const {requiresAuth} = to.meta;

    if (requiresAuth) {
        const accessToken = JSON.parse(localStorage.getItem('authData')!).accessToken;
        if (!accessToken) {
            next('/auth/login');
            return;
        }

        const response = get(apiRoutes.auth.verifyToken + accessToken);
        response.then((response) => {
            if (response.status == 401 || response.status == 403) {
                console.log('Entrou')
                next('/auth/login');
                return
            }
        })
    }
    next();
});

export default router;
