<template>
    <tr>
        <td>{{ article.name }}</td>
        <td>
            <span v-if="article.withFile == 1">
                <FileIcon></FileIcon>
            </span>
        </td>
        <td><RouterLink :to="`/board/view/${article.id}`" replace>{{ article.title }}</RouterLink></td>
        <td>{{ article.author }}</td>
        <td>{{ article.viewCount }}</td>
        <td>{{ article.postDate }}</td>
        <td>{{ article.editDate }}</td>
    </tr>
</template>

<script setup>
import FileIcon from './FileIcon.vue';

defineProps({
    article: Object
})
</script>