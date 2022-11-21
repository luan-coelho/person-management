<template>
  <footer>Desenvolvido por Luan Coêlho de Souza</footer>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'TheFooter',
  });
</script>

<style scoped>
  footer {
    position: fixed;
    padding: 5px;
    left: 0;
    bottom: 0;
    width: 100%;
    background-color: #242526;
    color: white;
    text-align: center;
  }
</style>
