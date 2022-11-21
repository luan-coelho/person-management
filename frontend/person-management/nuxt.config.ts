export default defineNuxtConfig({
  app: {
    head: {
      title: 'Gerência de Pessoas',
      htmlAttrs: {
        lang: 'pt-br',
      },
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
        { hid: 'description', name: 'description', content: '' },
        { name: 'format-detection', content: 'telephone=no' },
      ],
      link: [
        {
          rel: 'stylesheet',
          href: 'https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css',
        },
        {
          rel: 'stylesheet',
          href: 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css',
        },
      ],
    },
  },
  typescript: {
    shim: false,
  },
  components: true,
  css: ['~/node_modules/bootstrap/dist/css/bootstrap.min.css'],
});
