<template>
    <div>
        <form id="saveForm" ref="saveForm" enctype="multipart/form-data">
            <div class="form-row">
                <label for="category">카테고리</label>
                <select name="categoryId" v-model="category">
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
                <input type="text" name="author" id="author" v-model="author"/>
            </div>

            <div class="form-row">
                <label for="pass">비밀번호</label>
                <input type="password" name="password" id="pass" v-model="pass" placeholder="4이상 16미만,영문/특수/숫자포함">
                <input type="password" id="passCheck">
            </div>

            <div class="form-row">
                <label for="title">제목</label>
                <input type="text" name="title" id="title" v-model="title">
            </div>

            <div class="form-row">
                <label for="content">내용</label>
                <input type="text" name="content" id="content" v-model="content">
            </div>

            <div class="form-row">
                <label for="file">파일 첨부</label>
                <input type="file" name="files" id="file">
            </div>

            <div class="form-buttons">
                <button type="button" onclick="location.href='/board/list'">취소</button>
                <button type="button" @click="ArticleCreate">저장</button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { boardApi } from '@/api/apiInstance';
import { errorAlert } from '@/api/errorAlert';
import router from '@/router';
import { ref } from 'vue';

//게시글 등록
const ArticleCreate = () => {
    //axios변경시 불편, 감추기,
    boardApi.post('/write',
        new FormData(document.getElementById('saveForm'))

    ).then(resp => {
        alert('게시글이 등록되었습니다')
        router.push('/board/view/' + resp.data)

    }).catch(error => {
        errorAlert(error);
    })
}
const saveForm = ref(null);
const category = ref('');
const author = ref('');
const title = ref('');
const content = ref('');
const pass = ref('');
const passCheck = ref('');

function checkListConfirm() {
    if (category.value === '0') {
        alert('카테고리 선택은 필수 입니다')
        return false;
    }
    if (lengthCheck(author, 2, 5)) {
        alert('작성자는 3글자 이상 5글자미만으로 가능합니다')
        return false;
    }
    if (pass.value !== passCheck.value) {
        alert('비밀번호가 일치하지 않습니다')
        return false;
    }
    if (!validatePassword(pass.value)) {
        alert('비밀번호 규칙을 확인해주세요')
        return false;
    }
    if (lengthCheck(title, 4, 100)) {
        alert('제목은 4글자이상 100글자 미만으로 입력해주세요')
        return false;
    }
    if (lengthCheck(content, 4, 2000)) {
        alert('내용은 4글자이상 2000글자 미만으로 입력해주세요')
        return false;
    }
    return true;
}

function validatePassword(password) {
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{4,15}$/;
    return passwordPattern.test(password);
}

function lengthCheck(target, up, down) {
    return target.value.length < up || target.value.length > down;
}


</script>

<style></style>
