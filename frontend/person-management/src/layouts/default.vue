<template>
  <AppHeader />
  <div class="layout-container">
    <SideBar />
    <main>
      <router-view />
    </main>
  </div>
  <AppFooter />
</template>

<script setup lang="ts">
  import AppHeader from '../components/AppHeader.vue';
  import SideBar from '../components/SideBar.vue';
  import AppFooter from '../components/AppFooter.vue';
</script>

<style scoped>
  .layout-container {
    display: flex;
  }
  main {
    margin: 1.5rem;
    width: 100%;
  }

  @media (max-width: 420px) {
    main {
      display: none;
    }
  }

  @media not (max-width: 420px) {
    main {
      display: block;
    }
  }
</style>
