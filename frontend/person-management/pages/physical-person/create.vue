<template>
  <FormCard title="Cadastrar" :creation-or-editing="true" :action="print">
    <FormCardContent :action="print">
      <PhysicalPersonForm />
    </FormCardContent>
  </FormCard>
</template>

<script setup lang="ts">
  import { useToast } from 'primevue/usetoast';
  import { cleanForm } from '~/utils/FormValidator';
  import { useAuthData } from '~/composables/auth';

  const toast = useToast();

  const auth = useAuthData();

  useHead({
    title: 'Cadastro de Pessoa Física',
  });

  function print() {
    console.log(auth.accessToken);
    toast.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Person created',
      life: 3000,
      closable: true,
    });
    cleanForm();
    auth.$patch({
      accessToken: 'T'
    })
    console.log(auth.accessToken);
    auth.$reset()
    console.log(auth.accessToken);
  }
</script>
