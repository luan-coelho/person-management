<template>
  <form @submit.prevent="save()">
    <div class="grid p-fluid">
      <div class="sm:col-12 md:col-6 col-12">
        <label for="name">Name</label>
        <div class="p-inputgroup">
          <InputText id="name" type="text" v-model="data.person.name" />
        </div>
      </div>

      <div class="sm:col-12 md:col-6 col-12">
        <label for="surname">Surname</label>
        <div class="p-inputgroup">
          <InputText id="surname" type="text" v-model="data.person.surname" />
        </div>
      </div>

      <div class="sm:col-12 md:col-4 col-12">
        <label for="cpf">CPF</label>
        <div class="p-inputgroup">
          <InputMask
            id="cpf"
            type="text"
            v-model="data.person.cpf"
            mask="999.999.999-99"
            maxlength="14" />
        </div>
      </div>

      <div class="sm:col-12 md:col-8 col-12">
        <label for="email">Email</label>
        <div class="p-inputgroup">
          <InputText
            @blur="validateEmail()"
            id="email"
            type="text"
            v-model="data.person.email"
            maxlength="255" />
        </div>
      </div>

      <div class="sm:col-12 md:col-6 col-12">
        <label for="birthday">Birthday</label>
        <div class="p-inputgroup">
          <Calendar
            id="birthday"
            v-model="data.person.birthday"
            dateFormat="dd/mm/yy"
            :showButtonBar="true"
            :minDate="minDate"
            :maxDate="maxDate" />
        </div>
      </div>

      <div class="sm:col-12 md:col-6 col-12">
        <label for="gender">Gender</label>
        <div class="p-inputgroup">
          <div class="gender-radio">
            <label for="male">Male</label>
            <RadioButton
              name="male"
              value="MALE"
              v-model="data.person.gender" />
          </div>
          <div class="gender-radio">
            <label for="female">Female</label>
            <RadioButton
              name="female"
              value="FEMALE"
              v-model="data.person.gender" />
          </div>
          <div class="gender-radio">
            <label for="female">Other</label>
            <RadioButton
              name="outro"
              value="OTHER"
              v-model="data.person.gender" />
          </div>
        </div>
      </div>

      <div class="sm:col-12 md:col-6 col-12">
        <label for="password">Password</label>
        <div class="p-inputgroup">
          <Password
            id="password"
            v-model="data.person.password"
            maxlength="255" />
        </div>
      </div>

      <div class="sm:col-12 md:col-6 col-12">
        <label for="confirmPassword">Confirm password</label>
        <div class="p-inputgroup">
          <Password
            id="confirmPassword"
            v-model="data.person.confirmPassword"
            maxlength="255"
            label />
        </div>
      </div>

      <input id="resetButton" type="reset" />
    </div>

    <div class="buttons">
      <Button type="submit" :label="label" class="p-button-success" />
      <Button @click="router.back()" label="Cancelar" class="p-button-danger" />
    </div>
  </form>
</template>

<script setup lang="ts">
  import InputText from 'primevue/inputtext';
  import InputMask from 'primevue/inputmask';
  import Calendar from 'primevue/calendar';
  import RadioButton from 'primevue/radiobutton';
  import Password from 'primevue/password';
  import { useToast } from 'primevue/usetoast';
  import { computed, onMounted, reactive, ref } from 'vue';
  import IPhysicalPerson from '../../types/IPhysucalPerson';
  import {cleanForm, validEmail} from '../../utils/formValidator';
  import router from '../../router';
  import Button from 'primevue/button';
  import fetchApi from '../../utils/fetchApi';

  const props = defineProps({
    id: {
      type: Number,
      default: null,
    },
    edition: {
      default: false,
    },
  });

  const toast = useToast();

  const data = reactive({
    person: {} as IPhysicalPerson,
  });

  const minDate = ref(new Date('1910-01-01') as Date);
  const maxDate = ref(new Date() as Date);

  const label = computed(() => {
    return props.edition ? 'Salvar' : 'Cadastrar';
  });

  function validateEmail() {
    if (!validEmail(data.person.email)) {
      data.person.email = '';
      toast.add({
        severity: 'error',
        summary: 'Validação falhou',
        detail: 'Email inválido',
        life: 3000,
        closable: true,
      });
    }
  }

  function findPersonByid(id: number) {
    const response = fetchApi.get(`/physical-persons/${id}`);
    response.then((response) => {
      data.person = response.data;
    });
  }

  onMounted(() => {
    findPersonByid(props?.id);
  });

  function save() {
    const response = fetchApi.post('/auth/register', data.person);
    response.then((response) => {
      if (response.status === 201) {
        toast.add({
          severity: 'success',
          summary: 'Sucesso!',
          detail: 'Cadastrada realizado com sucesso',
          life: 3000,
          closable: true,
        });
        cleanForm();
      } else {
        toast.add({
          severity: 'error',
          summary: 'Algo falhou',
          detail: response.data.message,
          life: 3000,
          closable: true,
        });
      }
    });
  }
</script>

<style scoped>
  .gender-radio {
    margin-top: 10px;
    display: flex;
  }

  .gender-radio :last-child {
    margin: 0 15px 0 2px;
  }

  .buttons {
    display: flex;
    justify-content: flex-end;
  }

  .buttons :first-child {
    margin-right: 5px;
  }

  #resetButton {
    display: none;
  }
</style>
