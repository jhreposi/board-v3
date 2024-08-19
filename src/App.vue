<script setup>

const getData = () => {
  fetch('http://localhost:8080/',{
    method: 'get',
  }).then(resp => {
    console.log(resp);
    
  }).catch(error => {
    console.log(error);
    
  })
}
const getData2 = () => {
  fetch('/api',{
    method: 'get',
  }).then(resp => {
    console.log(resp);
    
  }).catch(error => {
    console.log(error);
    
  })
}
</script>

<template>
  <button @click="getData">데이터 호출</button>
  <button @click="getData2">데이터 호출2</button>
  <div>카운트:{{ count }}</div>
</template>

<style scoped>
</style>
