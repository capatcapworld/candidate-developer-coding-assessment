import {createApp} from 'vue';
import {createRouter, createWebHistory, RouterView} from 'vue-router';

createApp(RouterView)
	.use(createRouter({
		history: createWebHistory(),
		routes: [
			{
				path: '/',
				redirect: '/tenancies'
			},
			{
				path: '/tenancies',
				component: () => import('@/Tenancies.vue')
			},
			{
				path: '/tenancies/:tenancyId',
				component: () => import('@/Tenancy.vue'),
				props: true
			},
			{
				path: '/rent-collections/:rentCollectionId',
				component: () => import('@/RentCollection.vue'),
				props: true
			}
		]
	}))
	.mount('#app');
