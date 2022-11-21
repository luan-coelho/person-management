<template>
  <div class="container mt-5">
    <h2>Cadastro</h2>

    <div class="shadow-lg p-3 bg-body rounded">
      <form class="row g-3" @submit.prevent>
        <div class="col-md-6">
          <label for="name" class="form-label">Name</label>
          <input id="name" v-model="person.name" class="form-control" />
        </div>

        <div class="col-md-6">
          <label for="surname" class="form-label">Surname</label>
          <input id="surname" v-model="person.surname" class="form-control" />
        </div>

        <div class="col-md-3">
          <label for="cpf" class="form-label">CPF</label>
          <input id="cpf" v-model="person.cpf" class="form-control" />
        </div>

        <div class="col-9">
          <label for="email" class="form-label">Email</label>
          <input
            id="email"
            v-model="person.email"
            class="form-control"
            placeholder="joao@exemplo.com"
          />
        </div>

        <div class="col-md-6">
          <label for="password" class="form-label">Password</label>
          <input
            id="password"
            v-model="person.password"
            type="password"
            class="form-control"
          />
        </div>

        <div class="col-md-6">
          <label for="confirm" class="form-label">Confirm password</label>
          <input
            id="confirm"
            v-model="person.confirmPassword"
            type="password"
            class="form-control"
          />
        </div>

        <div class="col-md-3">
          <label for="birthday" class="form-label">Birthday</label>
          <input
            id="birthday"
            v-model="person.birthday"
            type="date"
            class="form-control"
          />
        </div>

        <div class="col-md-3">
          <label for="sexo" class="form-label">Sexo</label>
          <select id="sexo" v-model="person.gender" class="form-select">
            <option selected>Selecione...</option>
            <option value="MALE">MASCULINO</option>
            <option value="FEMALE">FEMININO</option>
          </select>
        </div>

        <div class="col-12">
          <button type="submit" class="btn btn-primary" @click="register()">
            Cadastrar
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import IPhysicalPerson from '@/types/IPhysucalPerson';
import { defineComponent } from 'vue';

export default defineComponent({
  layout: 'auth',
  data() {
    return {
      person: {} as IPhysicalPerson,
    };
  },
  methods: {
    async register() {
      await useFetch('/auth/register', {
        body: this.person,
        onResponse({ response }) {
          if (response.status === 200 || response.status === 204) {
            navigateTo('/auth/login');
          }
        },
        onRequestError({ response }) {
          console.warn(response);
        },
      });
    },
  },
});
</script>
