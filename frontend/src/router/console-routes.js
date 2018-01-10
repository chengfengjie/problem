export default [
    {
        path: 'general',
        component: resolve => require(['~/views/console/General.vue'], resolve),
    },
    {
        path: '',
        redirect: 'general',
    },
    {
        path: 'activityProblem',
        component: resolve => require(['~/views/console/ActivityProblem.vue'], resolve),
        props: true,
    },
    {
        path: 'allProblem',
        component: resolve => require(['~/views/console/AllProblem.vue'], resolve),
    },
    {
        path: 'problemStatistics',
        component: resolve => require(['~/views/console/ProblemStatistics.vue'], resolve),
    },
    {
        path: 'myActivityProblem',
        component: resolve => require(['~/views/console/MyActivityProblem.vue'], resolve),
    },
    {
        path: 'assignToMeProblem',
        component: resolve => require(['~/views/console/AssignToMeProblem.vue'], resolve),
    },
    {
        path: 'myAssignedProblem',
        component: resolve => require(['~/views/console/MyAssignedProblem.vue'], resolve),
    },
    {
        path: 'version',
        component: resolve => require(['~/views/console/Version.vue'], resolve),
    },
]
