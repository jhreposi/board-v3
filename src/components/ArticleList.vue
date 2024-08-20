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

</template>

<script setup>
import { boardApi } from '../api/apiInstance.js';
import { onMounted, ref } from 'vue';
import ArticleItem from './ArticleItem.vue';

let articles = ref([]);
let search = ref();
let paging = ref();
    
//마운티드 총 게시글
onMounted(() => {
    boardApi.get('/list', {
        params: {pageNum: 1}//임시 페이지번호

    }).then(response => {
        articles.value = response.data.articles;
        paging.value = response.data.paging;
        search.value = response.data.search;
    }).catch(error => {
        console.log(error);
    
    });
});

</script>
