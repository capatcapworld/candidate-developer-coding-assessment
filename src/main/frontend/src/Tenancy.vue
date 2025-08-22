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
            const response = await fetch(`/api/order-lines?tenancyId=${props.tenancyId}`, { method: 'PUT' });
            const message = await response.text();
            console.log(message);
            if (response.ok) {
               ElMessageBox.alert(message, 'Success', { confirmButtonText: 'OK'});
            } else {
               throw new Error(`Failed to book order lines for tenancy: ${props.tenancyId} -  Status: ${response.status}`);
            }
        } catch (err) {
            console.error(err);
            ElMessageBox.alert(err, 'Error', { confirmButtonText: 'OK'});
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
