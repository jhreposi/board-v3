<template>
    <div>
        <form id="searchForm" action="/board/list" method="get">
                <input type="date" name="startDate" id="startDate">&nbsp~
                <input type="date" name="endDate" id="endDate">
                <select name="category" id="category">
                    <option value="">전체 카테고리</option>
                    <option value="1" >자유</option>
                    <option value="2" >일상</option>
                    <option value="3" >음악</option>
                    <option value="4" >영화</option>
                    <option value="5" >여행</option>
                </select>
                <input type="text" name="keyword" id="keyword">
                <button type="button" @click="articleSearch">검색</button>
            </form>
    </div>
</template>

<script setup>
import { boardApi } from '@/api/apiInstance';

    const articleSearch = () => {
        boardApi.get('/list',
            {
                startData: document.getElementById('startDate'),
                endDate: document.getElementById('endDate'),
                category: document.getElementById('category'),
                keyword: document.getElementById('keyword'),
            }
        ).then(resp => {
            console.log(resp);
            
        }).catch(error => {
            console.log(error);
            
        })
    }
</script>

<style>
</style>
