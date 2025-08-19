<script setup>
	import AppTable from '@/AppTable.vue';

	defineProps({
		tenancies: Array,
		path: String
	});
</script>

<template>
	<AppTable
		:rows="tenancies"
		:columns="[{
			key: 'name',
			label: 'Name'
		}]"
		:path="path"
	/>
</template>
