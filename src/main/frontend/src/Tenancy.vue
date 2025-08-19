<script setup>
	import {ref, watchEffect} from 'vue';
	import TenancyTable from '@/TenancyTable.vue';
	import RentCollectionTable from '@/RentCollectionTable.vue';

	const props = defineProps({tenancyId: String});
	const tenancy = ref();
	const rentCollections = ref();

	const load = (endpoint, reference) => fetch(endpoint)
		.then(response => response.json())
		.then(json => reference.value = json);

	watchEffect(() => {
		load(`/api/tenancies/${props.tenancyId}`, tenancy);
		load(`/api/rent-collections?tenancyId=${props.tenancyId}`, rentCollections);
	});
</script>

<template>
	<h1>Tenancy</h1>
	<TenancyTable :tenancies="[tenancy]"/>

	<h1>Rent collections</h1>
	<RentCollectionTable :rent-collections="rentCollections" path="/rent-collections"/>
</template>
