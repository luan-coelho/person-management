<template>
  <FormCard title="Cadastro de Pessoa">
    <FormContent />
  </FormCard>
</template>

<script lang="ts">
  import IPhysicalPerson from '@/types/IPhysucalPerson';
  import Card from 'primevue/card';
  import FormCard from '~/components/form/FormCard.vue';
  import FormContent from '~/components/form/FormContent.vue';

  export default defineComponent({
    name: 'IndexPage',
    layout: 'default',
    components: {
      Card,
      FormCard,
      FormContent,
    },
    data() {
      return {
        persons: [] as IPhysicalPerson[],
      };
    },
  });
</script>

