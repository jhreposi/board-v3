import HomeVIew from "@/components/HomeVIew.vue";
import ArticleDetail from "../components/ArticleDetail.vue";
import ArticleList from "../components/ArticleList.vue";
import { createMemoryHistory, createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        component: HomeVIew
    },
    {
        path: '/board/list',
        component: ArticleList
    },
    {
        path: '/board/view/:id',
        component: ArticleDetail
    },
];

const router = createRouter({
    // history: createMemoryHistory(),
    history: createWebHistory('/'),
    routes,
});

export default router;