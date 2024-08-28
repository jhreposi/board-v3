<template>
    <div>
        <span v-for="num in lastPage" :key="num"
                @click="currentPageValue(num)"
                style="margin: 5px 5px;"
                >
            {{ num }}
        </span>
    </div>
</template>

<script setup>
import { storeSearch } from '@/store';

const searchStore = storeSearch();

const props = defineProps({
    lastPage: Number
});

const currentPageValue = (currentPage) => {
    searchStore.search.pageNum = currentPage
}
</script>

<style></style>
