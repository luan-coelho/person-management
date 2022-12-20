<template>
  <div>
    <Card class='card card-header'>
      <template #title >
        {{ title }}
      </template>
      <template #content>
        <slot />
      </template>
    </Card>
  </div>
</template>

<script lang='ts'>
  import { defineComponent } from 'vue';
  import Card from 'primevue/card';

  export default defineComponent({
    name: 'FormCard',
    components: {
      Card
    },
    props: {
      title: {
        type: String,
        required: true
      }
    }
  });
</script>

<style scoped>
  .card {
    margin: 20px;
    color: #000000 !important;
  }
  .card-header {
    border-top: solid 10px #1064B9FF !important;
    border-radius: 10px;
  }
</style>
