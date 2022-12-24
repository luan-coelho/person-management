export default defineNuxtConfig({
  app: {
    head: {
      htmlAttrs: {
        lang: 'pt-br',
      },
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      ],
      link: [
        {
          rel: 'stylesheet',
          href: 'https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css',
        },
      ],
    },
  },
  modules: [
    [
      '@pinia/nuxt',
      {
        autoImports: [['defineStore', 'definePiniaStore']],
      },
    ],
  ],
  components: true,
  css: [
    '/assets/css/normalize.css',
    'primevue/resources/themes/saga-blue/theme.css',
    'primevue/resources/primevue.css',
    'primeicons/primeicons.css',
    'primevue/resources/themes/tailwind-light/theme.css',
    '/assets/css/style.css',
    '/node_modules/primeflex/primeflex.css',
  ],
  build: {
    transpile: ['primevue'],
  },
  runtimeConfig: {
    public: {
      apiBaseUrl: 'http://localhost:3000/api',
    },
  },
});
