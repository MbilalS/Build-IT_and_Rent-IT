<template>
    <div>
        <b-tabs type="is-toggle" expanded>
            <b-tab-item label="Order Aprroval">
            </b-tab-item>
        </b-tabs>
        <table class="table is-striped is-fullwidth">
            <thead>
                <tr>
                    <th class="has-text-center">Plant</th>
                    <th class="has-text-center">Rental period</th>
                    <th class="has-text-center">Price</th>
                    <th class="has-text-center">Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(order, order_index) in orders" :key="order_index" v-if="order.status == 'PENDING'">
                    <td>
                        {{order.plant.name}} <br/>
                        <a class="button is-link is-small is-outlined"
                            @click="plantModal(order.plant)">
                            See details
                        </a>
                    </td>
                    <td>{{order.rentalPeriod.startDate}} / {{order.rentalPeriod.endDate}}</td>
                    <td>{{order.plant.price}}</td>
                    <td>
                        <a v-for="(link, rel) in order._links" :key="rel" v-if="rel !== 'self' & rel !== 'extensions' & rel !== 'close'" 
                            v-bind:class="{ 'is-danger': link.method === 'DELETE', 'is-link': link.method !== 'DELETE' }"
                            class="button is-small is-outlined"
                            @click="followLink(link, rel, order_index)">
                            {{rel}}
                        </a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

import axios from "axios";
import PlantModal from "./PlantModal.vue"
import Vue from "vue"
// import Generic from "./Generic.vue"

export default {
    name: "OrderCreation",
    data: function() {
        return {
            orders: []
        }
    },
    mounted: function() {
        axios.get("http://localhost:8090/api/sales/orders")
        .then(response => {
            this.orders = !response.data._embedded ? [] : response.data._embedded.orders;
            var count = 0
            for (var i = 0; i < this.orders.length; i++) {
                if(this.orders[i].status == "PENDING")
                {
                    count = count + 1
                }
            }
            if(count == 0)
            {
                this.$toast.open({
                duration: 5000,
                message: `There are no more PENDING_APPROVALs available`,
                position: 'is-bottom',
                type: 'is-success'
                })
            }
            count = 0
        });
    },
    methods: {
        plantModal: function(plant) {
            this.$modal.open({
                parent: this,
                component: PlantModal,
                props: { plant: plant }
            });
        },
        followLink: function(link, rel, order_index) {
            if (link.method) {
                axios({
                    method: link.method,
                    url: link.href
                }).then(response => {
                    Vue.set(this.orders, order_index, response.data);
                }).catch(error => console.log(error));
                this.$router.go(this.$router.currentRoute)
            }
            //   else {
            //     axios.get(link.href)
            //         .then(response => {
            //             let collection = [];
            //             if (response.data._embedded && response.data._embedded.extensions)
            //                 collection = response.data._embedded.extensions;
            //             this.$modal.open({
            //                 parent: this,
            //                 component: Generic,
            //                 props: {
            //                     title: rel,
            //                     collection: collection,
            //                     template: response.data._templates.default,
            //                     url: response.data._links.self.href
            //                 }
            //             });
            //         })
            //         .catch(error => console.log(error));
            // }
        }
    }
}
</script>
