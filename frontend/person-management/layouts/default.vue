<template>
  <AppHeader />
  <div class="layout-container">
    <SideBar />
    <main>
      <NuxtPage />
    </main>
  </div>
  <AppFooter />
</template>

<style scoped>
  .layout-container {
    display: flex;
  }
  main {
    margin: 1.5rem;
  }
</style>
