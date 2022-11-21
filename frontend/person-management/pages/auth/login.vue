<template>
  <div class="container mt-5">
    <h2>Login</h2>

    <div class="shadow-lg p-3 bg-body rounded">
      <form class="row g-3" @submit.prevent>
        <div class="col-12">
          <label for="email" class="form-label">Email</label>
          <input
            id="email"
            v-model="authRequest.email"
            type="email"
            class="form-control"
          />
        </div>
        <div class="col-12">
          <label for="password" class="form-label">Password</label>
          <input
            id="password"
            v-model="authRequest.password"
            type="password"
            class="form-control"
          />
        </div>

        <div class="col-12">
          <button type="submit" class="btn btn-primary" @click="loginRequest()">
            Entrar
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

interface AuthRequest {
  email: string;
  password: string;
}
export default defineComponent({
  layout: 'auth',
  data() {
    return {
      authRequest: {} as AuthRequest,
    };
  },
  methods: {
    async loginRequest() {
      await useFetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        body: this.authRequest,
        onResponse({ response }) {
          if (response.status == 200) {
            localStorage.setItem('accessToken', response._data.accessToken);
            navigateTo('/');
          }
          console.log(response);
        },
        onRequestError({ response }) {
          console.warn(response);
        },
      });
    },
  },
});
</script>
