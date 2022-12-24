import { defineNuxtPlugin } from '#app';
import PrimeVue from 'primevue/config';
import ToastService from 'primevue/toastservice';
import Toast from 'primevue/toast';

export default defineNuxtPlugin((nuxtApp) => {
  nuxtApp.vueApp.component('Toast', Toast);
  nuxtApp.vueApp.use(ToastService, { ripple: true });
  nuxtApp.vueApp.use(PrimeVue);
  nuxtApp.provide('Toast' ,ToastService);

  return {
    provide: {
      toast: nuxtApp.vueApp.config.globalProperties.$toast
    }
  }
});
