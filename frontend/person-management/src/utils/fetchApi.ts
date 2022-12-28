import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
});

const authData = JSON.parse(localStorage.getItem('authData')!);

export async function get(url: string) {
  return await instance.get(url, {
    headers: { Authorization: 'Bearer ' + authData.accessToken },
  });
}

export async function post(url: string, body: Object) {
  return await instance.post(url, body);
}

const fetchApi = {
  axiosInstace: instance,
  get: get,
  post: post,
};

export default fetchApi;
