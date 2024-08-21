import { reactive } from "vue";

export const searchStore = reactive({
    
    search : Object,
   
    setSearch(val) {
        this.search = val
    }
})