<template>
    <div>
        <h2>게시글 수정</h2>
        <form id="saveForm" ref="saveForm" enctype="multipart/form-data">
            <div class="form-row">
                <label for="category">카테고리</label>
                <select name="categoryId" v-model="form.categoryId">
                    <option value=0>카테고리 선택</option>
                    <option value="1">자유</option>
                    <option value="2">일상</option>
                    <option value="3">음악</option>
                    <option value="4">영화</option>
                    <option value="5">여행</option>
                </select>
            </div>

            <div class="form-row">
                <label for="author">작성자</label>
                <input type="text" name="author" v-model="form.author" />
            </div>

            <div class="form-row">
                <label for="pass">비밀번호</label>
                <input type="password" name="password" v-model="form.password" placeholder="4이상 16미만,영문/특수/숫자포함">
            </div>

            <div class="form-row">
                <label for="title">제목</label>
                <input type="text" name="title" v-model="form.title">
            </div>

            <div class="form-row">
                <label for="content">내용</label>
                <input type="text" name="content" v-model="form.content">
            </div>

            <div v-for="file in files" v-show="file.isActive !== false">
                <span>
                    <FileIcon></FileIcon>
                    <span>{{ file.originalName }}</span>
                    <button type="button" @click="removeFile(file.id, file)"> X </button>
                </span>
            </div>

            <div class="form-buttons">
                <button type="button" onclick="location.href='/board/list'">취소</button>
                <button type="button" @click="articleSave">저장</button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { boardApi } from '@/api/apiInstance';
import { onMounted, reactive, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import FileIcon from './FileIcon.vue';
import { errorAlert } from '@/api/errorAlert';
import router from '@/router';

const route = useRoute();
const article = ref(null);
const articleId = ref(0);
const files = ref([]);
let removeFileList = new Set([]);

watch(() => route.params.id,
    (paramId) => {
        articleId.value = paramId;
    },
    { immediate: true });

const form = reactive({
    categoryId: '',
    author: '',
    title: '',
    content: '',
    password: '',
    files:[],
});

onMounted(() => {

    boardApi.get(`/view/${articleId.value}`)
        .then(response => {
            article.value = response.data.article;

            form.categoryId = article.value.categoryId;
            form.author = article.value.author;
            form.title = article.value.title;
            form.content = article.value.content;
            
            files.value = response.data.files;
            form.files = files.value;
            
        
        }).catch(error => {
            errorAlert(error);
        })
})

const removeFile = (fileId, file) => {
    if(confirm('파일을 제거하시겠습니까?')) {
        removeFileList.add(fileId)
        file.isActive = false;
    }
}

const articleSave = () => {
    let arrayFromSet = Array.from(removeFileList);
    form.id = articleId.value;
    form.removeFileList = arrayFromSet;

    boardApi.put('/modify', {
        author: form.author,
        title: form.title,
        content: form.content,
        id: form.id,
        categoryId: form.categoryId,
        removeFiles: form.removeFileList,
        password: form.password
    }).then(resp => {
        alert(resp.data);
        router.push('/board/view/' + articleId.value)
    }).catch(error => {
        errorAlert(error);
    })
}

</script>

<style></style>
