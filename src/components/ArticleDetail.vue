<template>
    <div>
        <h2>게시판- 보기</h2>
        <div v-if="article">
            <div>
                <span>작성자:{{ article.author }}</span>
                <span>작성일:{{ article.postDate }}</span>
                <span>수정일:{{ article.editDate }}</span>
            </div>
            <div>
                <span>[{{ article.name }}]</span>
                <span>제목:{{ article.title }}</span>
                <span>{{ article.viewCount }}</span>
                조회수: <span>{{ article.viewCount }}</span>
            </div>
            <div>
                <div>{{ article.content }}</div>
            </div>
        </div>

        <div>
            <span v-for="file in files">
                {{ file.originalName }}
            </span>
        </div>

        <div>
            <Comment :comments="comments" :articleId="articleId"></Comment>
        </div>
        <div>
            <ListButton :name="'목록'"></ListButton>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import { boardApi } from '@/api/apiInstance';
import { useRoute } from 'vue-router';
import Comment from './Comment.vue';
import ListButton from './ListButton.vue';

const route = useRoute()
const articleId = ref(0);
let article = ref();
let comments = ref([]);
let files = null;

watch(() => route.params.id,
    (paramId) => {
        articleId.value = paramId;
    },
    {immediate: true });

onMounted(() => {
    boardApi.get(`/view/${articleId.value}`)
    .then(response => {
        article.value = response.data.article;
        comments.value = response.data.comments;
        files = response.data.files;

    }).catch(error => {
        console.log(error);
    })
})

// const createComment = () => {
    
//     boardApi.post('/comment',{
//         articleId: articleId.value,
//         comment: document.getElementById('comment').value

//     }).then((response)=> {
//         alert('댓글이 등록되었습니다')
//         document.getElementById('comment').value = ''
//         comments.value.push(response.data)

//     }).catch((error)=> {
//         console.log(error);

//     })
// }

</script>