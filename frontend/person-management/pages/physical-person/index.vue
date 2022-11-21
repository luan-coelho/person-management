<template>
  <div class="container">
    <NuxtLink to="physical-person/create" class="btn btn-success float-end mb-2"
      >Cadastrar</NuxtLink
    >
    <div>
      <table
        v-if="persons.length > 0"
        class="table table-dark table-striped table-hover table-bordered"
      >
        <thead>
          <tr>
            <th>Nome</th>
            <th>CPF</th>
            <th>Email</th>
            <th>Data de Nascimento</th>
            <th>Sexo</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(person, index) in persons" :key="index">
            <th>{{ person.name + person.surname }}</th>
            <th>{{ person.cpf }}</th>
            <th>{{ person.email }}</th>
            <th>{{ person.birthday }}</th>
            <th>{{ person.gender }}</th>
          </tr>
        </tbody>
      </table>

      <span v-else>Não há nenhum registro</span>
    </div>
  </div>
</template>

<script lang="ts">
import IPhysicalPerson from '@/types/IPhysucalPerson';

export default defineComponent({
  name: 'IndexPage',
  layout: 'default',
  data() {
    return {
      persons: [] as IPhysicalPerson[],
    };
  },
  methods: {
    async getPersons() {
      await useFetch('/physical-persons', {
        headers: {
          'Content-type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
        },
        onResponse({ response }) {
          if (response.status === 401 || response.status === 403) {
            navigateTo('/auth/login');
          }
        },
        onRequestError({ response }) {
          console.warn(response);
        },
      });
    },
  },
  mounted() {
    this.getPersons();
  },
});
</script>
