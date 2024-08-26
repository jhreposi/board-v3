<template>
    <div v-if="comments">
        <div v-for="comment in comments">
            <span>{{ comment.postDate }}</span>
            <div>{{ comment.comment }}</div>
        </div>
    </div>
    <input type="text" id="comment"/>
    <button type="button" @click="createComment">등록</button>
</template>

<script setup>
    import { boardApi } from '@/api/apiInstance';

    const props = defineProps({
        comments: Array,
        articleId: String,
    })

    //댓글 등록시
    const createComment = () => {
        
        boardApi.post('/comment',{
            articleId: props.articleId,
            comment: document.getElementById('comment').value

        }).then((response)=> {
            alert('댓글이 등록되었습니다')
            document.getElementById('comment').value = ''
            props.comments.push(response.data);

        }).catch((error)=> {
            console.log(error);

        })
    }
</script>

<style>
</style>
