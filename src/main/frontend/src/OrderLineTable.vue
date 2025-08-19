<script setup>
	import AppTable from '@/AppTable.vue';
	import {booleanFormatter, dateFormatter, numberFormatter} from '@/formatters.js';

	defineProps({orderLines: Array});
</script>

<template>
	<AppTable
		:rows="orderLines"
		:columns="[
			{
				key: 'name',
				label: 'Name'
			},
			{
				key: 'amount',
				label: 'Amount',
				formatter: numberFormatter,
				style: {'text-align': 'right'}
			},
			{
				key: 'bookingDate',
				label: 'Booking date',
				formatter: dateFormatter
			},
			{
				key: 'booked',
				label: 'Booked',
				formatter: booleanFormatter
			}
		]"
	/>
</template>
