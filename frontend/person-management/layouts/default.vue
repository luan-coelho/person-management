<template>
  <div>
    <SideBar />
    <main>
      <div class="container pt-3 pb-3">
        <NuxtPage></NuxtPage>
      </div>
    </main>
    <TheFooter />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import SideBar from '~/components/SideBar.vue';
import TheFooter from '~/components/TheFooter.vue';

export default defineComponent({
  components: {
    SideBar,
    TheFooter,
  },
  data() {
    return {};
  },
  mounted() {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
      this.$router.push('/auth/login');
    }
  },
});
</script>
