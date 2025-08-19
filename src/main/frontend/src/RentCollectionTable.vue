<script setup>
	import AppTable from '@/AppTable.vue';
	import {dateFormatter} from '@/formatters.js';

	defineProps({
		rentCollections: Array,
		path: String
	});
</script>

<template>
	<AppTable
		:rows="rentCollections"
		:columns="[
			{
				key: 'date',
				label: 'Date',
				formatter: dateFormatter
			}
		]"
		:path="path"
	/>
</template>
