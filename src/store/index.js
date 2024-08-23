import { defineStore } from "pinia";
import { ref } from "vue";

export const storeSearch = defineStore('search', () => {
    const search = ref({
        startDate: '',
        endDate: '',
        category: '',
        keyword: '',
        pageNum: 1,
    });

    function setSearch(val) {
        if (val.startDate !== undefined) search.value.startDate = val.startDate
        if (val.endDate !== undefined) search.value.endDate = val.endDate
        if (val.category !== undefined) search.value.category = val.category
        if (val.keyword !== undefined) search.value.keyword = val.keyword
        search.value.pageNum = 1;
    }

    return { search, setSearch };

})