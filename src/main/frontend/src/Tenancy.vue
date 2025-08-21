<script setup>
	import {ref, watchEffect} from 'vue';
	import TenancyTable from '@/TenancyTable.vue';
	import RentCollectionTable from '@/RentCollectionTable.vue';
	import { ElMessageBox } from 'element-plus';
	import 'element-plus/dist/index.css'

	const props = defineProps({tenancyId: String});
	const tenancy = ref();
	const rentCollections = ref();
	const message = ref();

	const load = (endpoint, reference) => fetch(endpoint)
		.then(response => response.json())
		.then(json => reference.value = json);

	watchEffect(() => {
		load(`/api/tenancies/${props.tenancyId}`, tenancy);
		load(`/api/rent-collections?tenancyId=${props.tenancyId}`, rentCollections);
	});

    // Call Spring Boot PUT endpoint
    const bookOrderLines = async () => {
        try {
            const response = await fetch(`/api/order-lines?tenancyId=${props.tenancyId}`, { method: 'PUT' })
            if (!response.ok) throw new Error(`Failed: ${response.status}`)
            ElMessageBox.alert('Order lines booked successfully', 'Success', { confirmButtonText: 'OK'});
        } catch (err) {
            console.error(err);
            ElMessageBox.alert('Failed to book order lines', 'Error', { confirmButtonText: 'OK'});
        }
    }
</script>

<template>
	<h1>Tenancy</h1>
	<TenancyTable :tenancies="[tenancy]"/>

	<h1>Rent collections</h1>
	<RentCollectionTable :rent-collections="rentCollections" path="/rent-collections"/>
	<button @click="bookOrderLines">
        Book all order lines for this tenancy
    </button>
</template>
