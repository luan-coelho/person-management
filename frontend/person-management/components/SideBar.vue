<template>
  <header>
    <nav>
      Gerência de Pessoas
      <NuxtLink to="/physical-person">Gerência de Pessoas</NuxtLink>
    </nav>
  </header>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'SideBar',
    data() {
      return {};
    },
  });
</script>

<style scoped>
  nav {
    font-family: 'Poppins', sans-serif;
    background-color: #242526;
    color: #ffffff;
    padding: 8px;
    font-size: 1.6em;
  }
</style>
