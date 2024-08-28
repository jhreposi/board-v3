<template>
    <div class="modal-wrap" v-show="modalShow" @click="modalClose">
        <div class="modal-container" @click.stop="">

            비밀번호<input type="password" v-model="inputValue">

            <div class="modal-btn">
                <button @click="modalClose">닫기</button>
                <button @click="passwordCheck">확인</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { boardApi } from '@/api/apiInstance';
import { errorAlert } from '@/api/errorAlert';
import router from '@/router';
import { ref } from 'vue';

const modalShow = defineModel();
const props = defineProps({
    articleId: String
})
const inputValue = ref('');

const modalClose = () => {
    modalShow.value = false
}
const passwordCheck = () => {

    boardApi.delete('/', {
        data: {
            id: props.articleId,
            password: inputValue.value
        }
    }
    ).then(resp => {
        alert(resp.data)
        modalClose()
        router.push({path:'/board/list'})
        
    }).catch(error => {
        errorAlert(error)
    })
    
}
</script>

<style>
.modal-wrap {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.4);
}

/* modal or popup */
.modal-container {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 550px;
    background: #fff;
    border-radius: 10px;
    padding: 20px;
    box-sizing: border-box;
}
</style>
