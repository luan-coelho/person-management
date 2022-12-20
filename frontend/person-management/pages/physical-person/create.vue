<template>
  <div>
    <InputText type="text" v-model="person.name" />
    <Dropdown
      v-model="selectedCity"
      :options="cities"
      optionLabel="name"
      placeholder="Select a City"
    />
  </div>
</template>

<script lang="ts">
import Dropdown from 'primevue/dropdown';
import InputText from 'primevue/inputtext';
import IPhysicalPerson from '~~/types/IPhysucalPerson';

export default defineComponent({
  layout: 'default',
  components: {
    InputText,
    Dropdown,
  },
  name: 'Create',
  data() {
    return {
      person: {} as IPhysicalPerson,
      selectedCity: null,
      cities: [
        { name: 'New York', code: 'NY' },
        { name: 'Rome', code: 'RM' },
        { name: 'London', code: 'LDN' },
        { name: 'Istanbul', code: 'IST' },
        { name: 'Paris', code: 'PRS' },
      ],
    };
  },
});
</script>
