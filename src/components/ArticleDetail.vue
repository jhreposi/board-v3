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
                    <FileIcon></FileIcon>
                </span>
                {{ file.originalName }}
            </span>
        </div>

        <div>
            <Comment :comments="comments" :articleId="articleId"></Comment>
        </div>
        <div>
            <ListButton :name="'목록'"></ListButton>
            <RouterLink :to="`/board/modify/${articleId}`">수정</RouterLink>
            <button type="button" @click="articleDelete">삭제</button>
            
            <div v-if="articleId">
                <PasswordCheck v-model="modalCheck" :articleId="articleId"></PasswordCheck>
            </div>
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
import FileIcon from './FileIcon.vue';
import PasswordCheck from './PasswordCheck.vue';
import router from '@/router';

const route = useRoute()
const articleId = ref(0);
let article = ref();
let comments = ref([]);
let files = null;
const modalCheck = ref(false);

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
        router.push({
            path:'/board/list'
        })
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

const articleDelete = () => {
    modalCheck.value = !modalCheck.value
}

</script>