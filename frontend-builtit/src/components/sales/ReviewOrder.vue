<template>
    <div>
        <table class="table is-table-bordered is-table-striped is-fullwidth">
            <thead>
                <tr>
                    <th class="has-text-center">Name</th>
                    <th class="has-text-center">Rental Period</th>
                    <th class="has-text-center">Total Price</th>
                    <th class="has-text-center">Status</th>
                    <th class="has-text-center">Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(phr, phr_index) in phrs" :key="phr_index" v-if="phr.status === 'PENDING_APPROVAL'">
                    <td>
                        {{phr.plantInventoryEntry.name}}<br>
                        <a class="button is-link is-small is-outlined"
                            @click="plantModal(phr)">
                            See details
                        </a>
                    </td>
                    <td>{{phr.rentalPeriod.startDate}} / {{phr.rentalPeriod.endDate}}</td>
                    <td>{{phr.totalPrice}}</td>
                    <td>{{phr.status}}</td>
                    <a class="button is-success is-outlined" @click="phrDecision(phr)">Decision</a>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

import PlantModal from "./PlantModal.vue"
import Vue from "vue"
import PHRdecision from "./PHRdecision.vue"

export default {
    name: "ReviewOrder",
    props: ["phrs"],
    methods: {
        plantModal: function(phr) {
            this.$modal.open({
                parent: this,
                component: PlantModal,
                props: { phr: phr }
            });
        },
        phrDecision: function(phr) {
            this.$modal.open({
                parent: this,
                component: PHRdecision,
                props: { phr: phr }
            });
        }
    }
}
</script>

