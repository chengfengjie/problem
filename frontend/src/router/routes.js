/**
 * Created by chengfj on 2017/10/7.
 */

import ConsoleChildren from './console-routes'

export default [
    {
        name: 'Login',
        path: '/login',
        meta: {requireAuth: false},
        component: resolve => require.ensure([], () => resolve(require('~/views/Login.vue')), 'login'),
    },
    {
        name: 'Console',
        path: '/console',
        meta: {requireAuth: true},
        component: resolve => require.ensure([], () => resolve(require('~/views/Console.vue')), 'console'),
        children: ConsoleChildren,
    },
    {
        name: 'Project',
        path: '/project',
        meta: {requireAuth: true},
        component: resolve => require.ensure([], () => resolve(require('~/views/Project.vue')), 'project'),
    },
    {
        path: '*',
        redirect: '/project',
    },
]
