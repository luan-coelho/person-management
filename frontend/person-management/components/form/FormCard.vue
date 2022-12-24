<template>
  <div class='container'>
    <Card class="card card-header p-card-footer">
      <template #title>
        {{ props.title }}
      </template>
      <template #content class="p-card-body">
        <slot />
      </template>
    </Card>
  </div>
</template>

<script setup lang="ts">
  import Card from 'primevue/card';

  const props = defineProps({
    title: {
      type: String,
      required: true,
    },
  });

</script>

<style scoped>
  .container{
    min-height: 90vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .card {
    min-width: 90vh;
    color: #000000 !important;
    padding-bottom: 0;
    box-shadow: 10px 10px 10px 10px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 10px 10px 10px 10px rgba(0, 0, 0, 0.3);
    -moz-box-shadow: 10px 10px 10px 10px rgba(0, 0, 0, 0.3);
  }

  .card-header {
    border-top: solid 10px #1064b9ff !important;
    border-radius: 10px;
  }

  .p-card-body {
    padding-bottom: 0;
  }
</style>
