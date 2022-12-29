import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
});

const authData = JSON.parse(localStorage.getItem('authData')!);
const accessToken = authData?.accessToken;

export async function get(url: string) {
  return await instance.get(url, {
    headers: { Authorization: 'Bearer ' + accessToken },
  });
}

export async function post(url: string, body: Object) {
  if (accessToken) {
    return await instance.post(url, body, {
      headers: { Authorization: 'Bearer ' + accessToken },
    });
  }
  return await instance.post(url, body);
}

const fetchApi = {
  axiosInstace: instance,
  get: get,
  post: post,
};

export default fetchApi;
