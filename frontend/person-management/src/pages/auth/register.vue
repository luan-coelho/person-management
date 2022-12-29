<template>
  <FormCard title="Cadastrar">
    <PhysicalPersonForm :id="data" />
  </FormCard>
</template>

<script setup lang="ts">
import FormCard from '../../components/form/FormCard.vue';
import PhysicalPersonForm from '../../components/physical-person/PhysicalPersonForm.vue';
import { onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const data = ref<Number>();

onBeforeMount(() => {
  if (route.params?.id) {
    data.value = Number.parseInt(route.params.id[0]);
  }
});
</script>
