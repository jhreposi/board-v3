<template>
    <div>
        <button type="button">
            <RouterLink to="/board/list">{{ name }}</RouterLink>
        </button>
    </div>
</template>

<script setup>
defineProps({
    name : String
})
</script>

<style>
</style>
