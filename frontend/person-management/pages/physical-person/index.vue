<template>
 <h3>Listagem de Pessoas</h3>
  <Button @click="navigateTo('/physical-person/create')" label="Cadastrar" class="p-button-success" />
</template>

<script setup lang="ts">
  import Button from 'primevue/button';

  useHead({
    title: 'Listagem'
  })
</script>

