<template>
  <div class="grid p-fluid">
    <div class="col-6">
      <label for="name">Name</label>
      <div class="p-inputgroup">
        <InputText id="name" type="text" v-model="person.name" />
      </div>
    </div>

    <div class="col-6">
      <label for="surname">Surname</label>
      <div class="p-inputgroup">
        <InputText id="surname" type="text" v-model="person.surname" />
      </div>
    </div>

    <div class="col-2">
      <label for="cpf">CPF</label>
      <div class="p-inputgroup">
        <InputMask
          id="cpf"
          type="text"
          v-model="person.cpf"
          mask="999.999.999-99"
          maxlength="14" />
      </div>
    </div>

    <div class="col-5">
      <label for="email">Email</label>
      <div class="p-inputgroup">
        <InputText
          @blur="validateEmail(person.email)"
          id="email"
          type="text"
          v-model="person.email"
          maxlength="255" />
      </div>
    </div>

    <div class="col-2">
      <label for="email">Birthday</label>
      <div class="p-inputgroup">
        <Calendar
          v-model="person.birthday"
          dateFormat="dd/mm/yy"
          :showButtonBar="true"
          :minDate="minDate"
          :maxDate="maxDate" />
      </div>
    </div>

    <div class="col-2">
      <label for="email">Gender</label>
      <div class="p-inputgroup">
        <div class="gender-radio">
          <label for="male">Male</label>
          <RadioButton name="male" value="MALE" v-model="person.gender" />
        </div>
        <div class="gender-radio">
          <label for="female">Female</label>
          <RadioButton name="female" value="FEMALE" v-model="person.gender" />
        </div>
        <div class="gender-radio">
          <label for="female">Other</label>
          <RadioButton name="outro" value="outro" v-model="person.gender" />
        </div>
      </div>
    </div>

    <div class="col-6">
      <label for="password">Password</label>
      <div class="p-inputgroup">
        <Password id="password" v-model="person.password" maxlength="255" />
      </div>
    </div>

    <div class="col-6">
      <label for="confirmPassword">Confirm password</label>
      <div class="p-inputgroup">
        <Password
          id="confirmPassword"
          v-model="person.password"
          maxlength="255"
          label />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import IPhysicalPerson from '~/types/IPhysucalPerson';
  import InputText from 'primevue/inputtext';
  import InputMask from 'primevue/inputmask';
  import Calendar from 'primevue/calendar';
  import RadioButton from 'primevue/radiobutton';
  import Password from 'primevue/password';
  import { validEmail } from '~/utils/FormValidator';
  import { useToast } from 'primevue/usetoast';

  const toast = useToast();

  const minDate = ref(new Date('1910-01-01') as Date);
  const maxDate = ref(new Date() as Date);

  const person = reactive({} as IPhysicalPerson);

  function validateEmail() {
    console.log(person);
    if (!validEmail(person.email)) {
      person.email = '';
      toast.add({
        severity: 'error',
        summary: 'Validação falhou',
        detail: 'Email inválido',
        life: 3000,
        closable: true,
      });
    }
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
</style>
