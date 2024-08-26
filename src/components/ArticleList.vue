<template>
    <h2>자유게시판 - 목록</h2>
    <SearchBox></SearchBox>
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

const searchStore = storeSearch();
let articles = ref([]);
let paging = null;
    
onMounted(() => {
    boardApi.get('/list', {
    }).then(response => {
        articles.value = response.data.articles;
        paging = response.data.paging;
        
    }).catch(error => {
        console.log(error);
    
    });
});

//조건을 변경해서 검색 누를시   queryString으로
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
            paging = response.data.paging

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
            console.log(error);
            
        })
        

}, {deep: true})


</script>
