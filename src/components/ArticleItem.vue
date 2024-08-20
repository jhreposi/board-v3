<template>
    <tr>
        <td>{{ article.name }}</td>
        <td>{{ article.withFile }}</td>
        <td><RouterLink :to="`/board/view/${article.id}`" replace>{{ article.title }}</RouterLink></td>
        <td>{{ article.author }}</td>
        <td>{{ article.viewCount }}</td>
        <td>{{ article.postDate }}</td>
        <td>{{ article.editDate }}</td>
    </tr>
</template>

<script setup>
defineProps({
    article: Object
})
</script>