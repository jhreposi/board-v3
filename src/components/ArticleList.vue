<template>
    <h2>자유게시판 - 목록</h2>
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
    <div v-if="paging">
        <Paging :lastPage="paging.lastPage"></Paging>
    </div>
</template>

<script setup>
import { boardApi } from '../api/apiInstance.js';
import { onMounted, ref, watch } from 'vue';
import ArticleItem from './ArticleItem.vue';
import Paging from './Paging.vue';
import { searchStore } from '@/store/index.js';

let articles = ref([]);
let search = null;
let paging = null;
    
onMounted(() => {
    boardApi.get('/list', {
    }).then(response => {
        articles.value = response.data.articles;
        paging = response.data.paging;
        search = response.data.search;

        searchStore.setSearch(search);
        
    }).catch(error => {
        console.log(error);
    
    });
});

watch(() => searchStore.search,
    (storeSearch, oldSearch) => {
        console.log('search watching');
        boardApi.get('/list',{
            params: {
                pageNum: storeSearch.pageNum,
                startDate: storeSearch.startData,
                endDate: storeSearch.endDate,
                cateory: storeSearch.category,
                keyword: storeSearch.keyword,
            }
        }).then(response => {
            articles.value = response.data.articles
            paging = response.data.paging
            search = response.data.search
            
        }).catch(error => {
            console.log(error);
            
        })

}, {deep: true})

</script>
