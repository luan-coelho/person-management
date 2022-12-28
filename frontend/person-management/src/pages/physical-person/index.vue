<template>
  <FormCard title="Listagem de Pessoas">
    <div>
      <Button
        @click="router.push('/physical-person/save')"
        label="Cadastrar"
        class="p-button-success" />

      <DataTable
        class="datatable"
        :value="data.persons"
        responsiveLayout="scroll">
        <Column field="fullName" header="Name">
          <template #body="slotProps">
            {{ slotProps.data.name + ' ' + slotProps.data.surname }}
          </template>
        </Column>
        <Column field="email" header="Email" />
        <Column field="cpf" header="CPF" />
        <Column field="birthday" header="birthday">
          <template #body="slotProps">
            {{ slotProps.data.birthday }}
          </template>
        </Column>
        <Column field="gender" header="Gender" />
        <Column header="Action">
          <template #body="slotProps">
            <Button
              @click="
                router.push(`/physical-person/save/${slotProps.data.id}`)
              "
              label="Editar"
              class="p-button-secondary" />
          </template>
        </Column>
        <template #footer>
          In total there are
          {{ data.persons ? data.persons.length : 0 }} persons.
        </template>
      </DataTable>
    </div>
  </FormCard>
</template>

<script setup lang="ts">
  import Button from 'primevue/button';
  import DataTable from 'primevue/datatable';
  import Column from 'primevue/column';
  import { onBeforeMount, reactive } from 'vue';
  import fetchApi from '../../utils/fetchApi';
  import router from '../../router';
  import FormCard from '../../components/form/FormCard.vue';

  const data = reactive({
    persons: [],
  });

  async function getPersons() {
    const response = fetchApi.get('/physical-persons');
    response.then((response) => {
      data.persons = response.data.content;
    });
  }

  onBeforeMount(() => {
    getPersons();
    console.log(data.persons);
  });
</script>

<style scoped>
  .datatable {
    margin-top: 1.2rem;
  }
</style>
