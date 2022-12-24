export default defineNuxtRouteMiddleware((from, to) => {
  const router = useRouter();
  const auth = useAuthData();

  if (!from.path.includes('/login')) {
    if (!auth.authenticated) {
      router.push('/auth/login');
    }
  }
});
