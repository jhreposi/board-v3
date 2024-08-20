<template>
    <div>
        <h2>게시판- 보기</h2>
        <div v-if="article">
            <div>
                <span>{{ article.author }}</span>
                <span>{{ article.postDate }}</span>
                <span>{{ article.editDate }}</span>
            </div>
            <div>
                <span>[{{ article.name }}]</span>
                <span>{{ article.title }}</span>
                <span>{{ article.viewCount }}</span>
                <span>{{ article.viewCount }}</span>
            </div>
            <div>
                <textarea>{{ article.content }}</textarea>
            </div>
        </div>

        <div>
            <span v-for="file in files">
                {{ file.originalName }}
            </span>
        </div>

        <div>
            <input type="text"/>
            <button type="button">등록</button>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import { boardApi } from '@/api/apiInstance';
import { useRoute } from 'vue-router';

const route = useRoute()
const articleId = ref(0);
let article = ref();
let comments = ref();
let files = ref();

watch(
    () => route.params.id,
    (newId) => {
        articleId.value = newId;
    },
    { immediate: true }
);

onMounted(() => {
    boardApi.get(`/view/${articleId.value}`)
    .then(response => {
        article.value = response.data.article;
        comments.value = response.data.comments;
        files.value = response.data.files;
        console.log(article.value);
        
    }).catch(error => {
        console.log(error);
    })
})

</script>