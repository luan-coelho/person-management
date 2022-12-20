<template>
  <div class="layout-container">
    <SideBar />
    <main>
      <NuxtPage />
    </main>
  </div>
  <AppFooter />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import SideBar from '@/components/SideBar.vue';
  import AppFooter from '@/components/AppFooter.vue';

  export default defineComponent({
    components: {
      SideBar,
      AppFooter,
    },
    data() {
      return {};
    },
  });
</script>

<style scoped>
  .layout-container {
    display: flex;
  }
  main {
    margin-top: 1.2rem;
  }
</style>
