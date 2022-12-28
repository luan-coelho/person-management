import { defineStore } from 'pinia';
import IPhysicalPerson from '../types/IPhysucalPerson';

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
