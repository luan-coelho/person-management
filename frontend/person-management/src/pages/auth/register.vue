<template>
  <FormCard title="Cadastrar" :creation-or-editing="true" :action="print">
    <FormCardContent :action="register">
      <PhysicalPersonForm />
    </FormCardContent>
  </FormCard>
</template>

<script setup lang="ts">
  import { reactive } from 'vue';
  import IPhysicalPerson from '../../types/IPhysucalPerson';
  import { useToast } from 'primevue/usetoast';
  import FormCard from '../../components/form/FormCard.vue';
  import FormCardContent from '../../components/form/FormCardContent.vue';
  import PhysicalPersonForm from '../../components/physical-person/PhysicalPersonForm.vue';
  import { cleanForm } from '../../utils/FormValidator';
  import fetchApi from '../../utils/fetchApi';
  import router from '../../router';

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
    const response = fetchApi.post('/auth/register', person);
    response.then((response) => {
      if (response.status == 200 || response.status == 201) {
        router.push('/auth/login');
      }
    });
  }
</script>
