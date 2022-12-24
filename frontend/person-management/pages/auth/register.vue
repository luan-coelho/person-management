<template>
  <FormCard title="Cadastrar" :creation-or-editing="true" :action="print">
    <FormCardContent :action="print">
      <PhysicalPersonForm />
    </FormCardContent>
  </FormCard>
</template>

<script setup lang="ts">
  import IPhysicalPerson from '~/types/IPhysucalPerson';
  import { useToast } from 'primevue/usetoast';
  import { cleanForm } from '~/utils/FormValidator';

  definePageMeta({
    authIntercept: false,
  });

  useHead({
    title: 'Cadastro de Pessoa Física',
  });

  const person = reactive({} as IPhysicalPerson);

  const toast = useToast();

  function print() {
    toast.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Person created',
      life: 3000,
      closable: true,
    });
    cleanForm();
  }

  async function register() {
    await useFetch('/auth/register', {
      body: person,
      onResponse({ response }) {
        if (response.status === 200 || response.status === 204) {
          navigateTo('/auth/login');
        }
      },
      onRequestError({ response }) {
        console.warn(response);
      },
    });
  }
</script>
