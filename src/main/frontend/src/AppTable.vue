<script setup>
	import {useRouter} from 'vue-router';

	const props = defineProps({
		rows: Array,
		columns: Array,
		path: String
	});

	const router = useRouter();

	function routerPush(row) {
		if (props.path) {
			router.push(`${props.path}/${row.id}`);
		}
	}
</script>

<template>
	<table :class="{'has-path': path}">
		<thead>
			<tr>
				<th v-for="column in columns" :key="column.key" :style="column.style">
					{{ column.label }}
				</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="row in [rows].flat().filter(Boolean)" :key="row.id" @click="routerPush(row)">
				<td v-for="column in columns" :key="column.key" :style="column.style">
					{{ (column.formatter ?? (value => value))(row[column.key]) }}
				</td>
			</tr>
		</tbody>
	</table>
</template>

<style scoped>
	table {
		border-collapse: collapse;
		width: 100%;
	}

	thead {
		border-bottom: 1px solid #999;
		text-align: left;
	}

	table.has-path tbody tr:hover {
		background: #eee;
		cursor: pointer;
	}

	tr:not(:last-child) {
		border-bottom: 1px solid #ccc;
	}

	th, td {
		padding: 0.25rem 0.5rem;
	}
</style>
