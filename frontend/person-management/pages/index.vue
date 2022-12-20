<template>
  <FormCard title="Cadastro de Pessoa">
    <FormContent />
  </FormCard>
</template>

<script lang="ts">
  import FormCard from '@/components/form/FormCard.vue';
  import FormContent from '@/components/form/FormContent.vue';

  export default defineComponent({
    name: 'IndexPage',
    components: {
      FormCard,
      FormContent,
    },
  });
</script>
