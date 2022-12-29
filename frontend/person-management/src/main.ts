import { createApp } from 'vue';
import './style.css';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import PrimeVue from 'primevue/config';
import ToastService from 'primevue/toastservice';

export const vueApp = createApp(App);
const pinia = createPinia();

vueApp.use(router);
vueApp.use(pinia);
vueApp.use(PrimeVue, { ripple: true, inputStyle: 'filled' });
vueApp.use(ToastService);

import 'normalize.css';
import 'primevue/resources/themes/saga-blue/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';
import 'primevue/resources/themes/tailwind-light/theme.css';
import 'primeflex/primeflex.css';
import 'boxicons/css/boxicons.min.css';

vueApp.mount('#app');

