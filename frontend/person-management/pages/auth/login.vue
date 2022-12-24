<template>
  <div>
    <form @submit.prevent="logar()">
      <div>
        <label for="login">Login</label>
        <input v-model="authRequest.login" id="login" type="text" />
      </div>

      <div>
        <label for="senha">Senha</label>
        <input v-model="authRequest.senha" id="senha" type="text" />
      </div>

      <button type="submit">Logar</button>
    </form>
  </div>
</template>

<script setup lang="ts">
  definePageMeta({
    layout: 'auth'
  })

  const authData = useAuthData();

  const authRequest = ref({
    login: '',
    senha: '',
  });

  function logar() {
    if (authRequest.value.login === 'admin') {
      authData.$patch({
        authenticated: true,
      });
      navigateTo('/');
    }
  }
</script>

<style scoped></style>
