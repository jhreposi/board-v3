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
            <span v-for="file in files" @click="downloadFile(file.id, file.originalName)">
                <span>
                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 0 24 24" width="24px" fill="#5f6368"><path d="M0 0h24v24H0z" fill="none"/><path d="M16.5 6v11.5c0 2.21-1.79 4-4 4s-4-1.79-4-4V5c0-1.38 1.12-2.5 2.5-2.5s2.5 1.12 2.5 2.5v10.5c0 .55-.45 1-1 1s-1-.45-1-1V6H10v9.5c0 1.38 1.12 2.5 2.5 2.5s2.5-1.12 2.5-2.5V5c0-2.21-1.79-4-4-4S7 2.79 7 5v12.5c0 3.04 2.46 5.5 5.5 5.5s5.5-2.46 5.5-5.5V6h-1.5z"/></svg>
                </span>
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
import { errorAlert } from '@/api/errorAlert';

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
        errorAlert(error);
    })
})

const downloadFile = async (fileId, originalName) => {
    
    boardApi.get('/file-download', {
        params: {
            fileId: fileId,
        },
        responseType: 'blob',
    }).then(response => {
        
        //blob로 url생성 다운로드 
        const blob = new Blob([response.data], { type: response.headers['content-type'] });
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', originalName);
        document.body.appendChild(link);
        link.click();
        link.remove();
        window.URL.revokeObjectURL(url);
    }).catch(error => {
        errorAlert(error);
    })
}

</script>