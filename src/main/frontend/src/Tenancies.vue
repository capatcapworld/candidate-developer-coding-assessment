<script setup>
	import {ref} from 'vue';
	import TenancyTable from '@/TenancyTable.vue';

	const tenancies = ref();

	fetch('/api/tenancies')
		.then(response => response.json())
		.then(json => tenancies.value = json);
</script>

<template>
	<h1>Tenancies</h1>
	<TenancyTable :tenancies="tenancies" path="/tenancies"/>
</template>
