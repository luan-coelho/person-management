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
    padding: 5px;
    width: 100%;
    background-color: #242526;
    color: white;
    text-align: center;
  }
</style>
