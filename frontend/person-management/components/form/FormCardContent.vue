<template>
  <div>
    <form @submit.prevent="action()">
      <slot />

      <div class="buttons">
        <Button type="submit" :label="label" class="p-button-success" />
        <Button @click="comeBack()" label="Cancelar" class="p-button-danger" />
      </div>

      <input id="resetButton" type="reset" />
    </form>
  </div>
</template>

<script setup lang="ts">
  import Button from 'primevue/button';

  const route = useRoute();

  const props = defineProps({
    action: {
      type: Function,
      required: true,
    },
    edition: {
      default: false,
    },
    creationOrEditing: {
      default: false,
    },
  });

  const label = computed(() => {
    return props.edition ? 'Salvar' : 'Cadastrar';
  });

  function comeBack() {
    const parentRoute = buildRoute(route.fullPath);
    navigateTo(parentRoute);
  }

  function buildRoute(route: string): string {
    let lastBar = route.lastIndexOf('/');
    return route.substring(0, lastBar);
  }
</script>

<style scoped>
  #resetButton {
    display: none;
  }

  .buttons {
    display: flex;
    justify-content: flex-end;
  }

  .buttons :first-child {
    margin-right: 6px;
  }
</style>
