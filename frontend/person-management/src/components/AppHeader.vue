<template>
  <header>
    <div class="logo-details">
      <i class="bx bx-menu bx-sm" id="btn"></i>
      <div class="logo-name">
        <span>Logo</span>
      </div>
      <div class="user-profile">
        <i class="bx bxs-user-circle bx-sm"></i>
        <span>Luan</span>
        <button @click="logout()">Sair</button>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
  import router from '../router';
  import { useAuthData } from '../store/auth';
  const authData = useAuthData();

  function logout() {
    localStorage.removeItem('authData');
    router.push('/auth/login');
  }
</script>

<style scoped>
  * {
    color: #fff;
  }
  header {
    padding: 8px;
    background-color: rgba(17, 16, 29, 0.73);
  }

  .logo-details {
    display: flex;
    align-items: center;
  }

  .logo-details :first-child {
    padding-left: 14px;
  }

  .logo-name {
    margin-left: 10px;
  }

  .user-profile {
    display: flex;
    align-items: center;
    position: absolute;
    right: 30px;
  }

  .user-profile :first-child {
    margin-right: 5px;
  }

  i,
  .user-profile {
    cursor: pointer;
  }
</style>
