<template>
    <div>
        <span v-for="currentPage in lastPage"
                :key="currentPage"
                @click="currentPageValue(currentPage)"
                >
            {{ currentPage }}
        </span>
    </div>
</template>

<script setup>
import { searchStore } from '@/store';

    const props = defineProps({
        lastPage: Number
    });
    
    const currentPageValue = (value) => {
        searchStore.search.pageNum = value;
    }
</script>

<style>
</style>
