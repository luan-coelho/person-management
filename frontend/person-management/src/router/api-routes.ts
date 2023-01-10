const apiRoutes =
    {
        auth: {
            base: '/auth',
            login: '/auth/login',
            register: '/auth/register',
            verifyToken: '/auth/verify-token/'
        },
        physicalPerson: {
            base: '/physical-person',
            findById: '/physical-person/',
            findByCpf: '/physical-person/find-by-cpf/',
            findByEmail: '/physical-person/find-by-email/',
            changeActivity: '/physical-person/change-activity/'
        }
    }

export default apiRoutes;