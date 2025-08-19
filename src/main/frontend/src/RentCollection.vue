<script setup>
	import {ref, watchEffect} from 'vue';
	import RentCollectionTable from '@/RentCollectionTable.vue';
	import OrderLineTable from '@/OrderLineTable.vue';

	const props = defineProps({rentCollectionId: String});
	const rentCollection = ref();
	const orderLines = ref();

	const load = (endpoint, reference) => fetch(endpoint)
		.then(response => response.json())
		.then(json => reference.value = json);

	watchEffect(() => {
		load(`/api/rent-collections/${props.rentCollectionId}`, rentCollection);
		load(`/api/order-lines?rentCollectionId=${props.rentCollectionId}`, orderLines);
	});
</script>

<template>
	<h1>Rent collection</h1>
	<RentCollectionTable :rent-collections="[rentCollection]"/>

	<h1>Order lines</h1>
	<OrderLineTable :order-lines="orderLines"/>
</template>
