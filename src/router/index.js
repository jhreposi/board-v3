import { createRouter, createWebHistory } from 'vue-router'
import HomeVIew from "@/components/HomeVIew.vue";
import ArticleDetail from "@/components/ArticleDetail.vue";
import ArticleList from "@/components/ArticleList.vue";
import ArticlePost from "@/components/ArticlePost.vue";
import NotFound from '@/components/NotFound.vue';

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
    {
        path: '/board/post',
        component: ArticlePost
    },
    {
        path: '/:pathMatch(.*)*',
        component: NotFound
    },
];

const router = createRouter({
    history: createWebHistory('/'),
    routes,
});

export default router;