<template>
    <div>
        <form id="searchForm" action="/board/list" method="get" @submit.prevent>
            <input type="date" name="startDate" id="startDate">&nbsp~
            <input type="date" name="endDate" id="endDate">
            <select name="category" id="category">
                <option value="">전체 카테고리</option>
                <option value="1">자유</option>
                <option value="2">일상</option>
                <option value="3">음악</option>
                <option value="4">영화</option>
                <option value="5">여행</option>
            </select>
            <input type="text" name="keyword" id="keyword" >
            <button type="button" @click="articleSearch">검색</button>
            <button type="button" @click="formReset">초기화</button>
        </form>
    </div>
</template>

<script setup>
import { storeSearch } from '@/store';

const searchStore = storeSearch();

const articleSearch = () => {

    searchStore.setSearch({
        startDate: document.getElementById('startDate').value,
        endDate: document.getElementById('endDate').value,
        category: document.getElementById('category').value,
        keyword: document.getElementById('keyword').value,
    });
}

const formReset = () => {
    document.getElementById('searchForm')?.reset();
}

</script>

<style></style>
