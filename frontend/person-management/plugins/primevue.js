import { defineNuxtPlugin } from '#app';
import Button from 'primevue/button';
import PrimeVue from 'primevue/config';

export default defineNuxtPlugin((nuxtApp) => {
  nuxtApp.vueApp.use(PrimeVue, { ripple: true });
  nuxtApp.vueApp.component('Button', Button);
});
