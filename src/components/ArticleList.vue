<template>
    <h2>자유게시판 - 목록</h2>
    <SearchBox></SearchBox>
    <div>게시글 수 - {{ paging.totalCount }}</div>
    <table>
        <thead>
            <tr>
                <td>카테고리</td>
                <td>파일 </td>
                <td>제목</td>
                <td>작성자</td>
                <td>조회수</td>
                <td>등록 일시</td>
                <td>수정 일시</td>
            </tr>
        </thead>
        <tbody>
            <ArticleItem
                v-for="article in articles"
                :key = "article.id"
                :article = "article"
            >
            </ArticleItem>
        </tbody>
    </table>
    <div v-if="paging" style="margin-left: 30%;">
        <Paging :lastPage="paging.lastPage"></Paging>
    </div>
    <div>
        <RouterLink to="/board/post">글 등록</RouterLink>
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import { storeSearch } from '@/store/index.js';
import { boardApi } from '../api/apiInstance.js';
import ArticleItem from './ArticleItem.vue';
import Paging from './Paging.vue';
import SearchBox from './SearchBox.vue';
import router from '@/router/index.js';
import { errorAlert } from '@/api/errorAlert.js';

const searchStore = storeSearch();
let articles = ref([]);
let paging = ref({});
    
onMounted(() => {
    boardApi.get('/list', {
    }).then(response => {
        articles.value = response.data.articles;
        paging.value = {
            totalCount: response.data.paging.totalCount,
            lastPage: response.data.paging.lastPage,
        }
        
    }).catch(error => {
        errorAlert(error)
    
    });
});

watch(() => searchStore.search,
    (search) => {
        
        boardApi.get('/list',{
            params: {
                pageNum: search.pageNum,
                startDate: search.startDate,
                endDate: search.endDate,
                category: search.category,
                keyword: search.keyword,
            }
        }).then(response => {
            articles.value = response.data.articles
            paging.value = {
                totalCount: response.data.paging.totalCount,
                lastPage: response.data.paging.lastPage,

            }
            
            router.push({
                path: '/board/list',
                query: {
                    pageNum: search.pageNum,
                    startDate: search.startDate,
                    endDate: search.endDate,
                    category: search.category,
                    keyword: search.keyword,
                }
            });
            
        }).catch(error => {
            errorAlert(error)
            
        })
        

}, {deep: true})


</script>
