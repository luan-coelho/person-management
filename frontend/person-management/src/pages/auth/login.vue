<template>
  <div>
    <form @submit.prevent="login()">
      <div>
        <label for="login">Email</label>
        <input v-model="authRequest.email" id="login" type="text" />
      </div>

      <div>
        <label for="senha">Password</label>
        <input v-model="authRequest.password" id="senha" type="text" />
      </div>

      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script setup lang="ts">
  import { reactive } from 'vue';
  import { useAuthData } from '../../store/auth';
  import router from '../../router';
  import fetchApi from '../../utils/fetchApi';
  import improvedToast from '../../utils/improvedToast';
  import apiRoutes from "../../router/api-routes";

  const authData = useAuthData();

  const authRequest = reactive({
    email: '',
    password: '',
  });

  function login() {
    const authResponse = fetchApi.post(apiRoutes.auth.login, authRequest);
    authResponse.then((response) => {
        if (response.status == 200 && response.data.accessToken) {
          localStorage.setItem('authData', JSON.stringify({
              user: response.data.email,
              accessToken: response.data.accessToken,
            })
          );
          router.push('/physical-person');
        } else {
          improvedToast.error('Erro', response.data);
        }
      })
      .catch((error) => {
        improvedToast.error(error.response.data.error, ...error.response.data.message);
      });
  }
</script>

<style scoped></style>
