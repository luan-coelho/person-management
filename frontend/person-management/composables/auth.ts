import IPhysicalPerson from '~/types/IPhysucalPerson';
import { defineStore } from 'pinia';

export const useAuthData = defineStore('authData', {
  state: () => ({
    user: {} as IPhysicalPerson,
    authenticated: false,
    accessToken: '',
  }),
  getters: {
    get: (state) => state,
  },
});
