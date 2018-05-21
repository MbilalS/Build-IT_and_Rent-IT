<template>
    <div>
        <table class="table is-table-bordered is-table-striped is-fullwidth">
            <thead>
                <tr>
                    <th class="has-text-center">Name</th>
                    <th class="has-text-center">Rental Period</th>
                    <th class="has-text-center">Total Price</th>
                    <th class="has-text-center">PHR Status</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(phr, phr_index) in phrs" :key="phr_index" v-if="phr.status === 'PO_ACCEPTED' | phr.status === 'PO_REJECTED' | phr.status === 'APPROVED'">
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
                </tr>
            </tbody>
        </table>
    </div>
</template>


<script>

import axios from "axios";
import PlantModal from "./PlantModal.vue"
import Vue from "vue"

export default {
    name: "CreatedPOs",
    data: function() {
        return {
            phrs: {}
        }
    },
    mounted: function() {
        axios.get("http://localhost:8080/api/procurements/requests")
        .then(response => {
            this.phrs = response.data._embedded.plantHireRequestDTOList
        }).catch(error => {
            this.$toast.open({
            duration: 5000,
            message: `Something's not good, PHR's are not loaded`,
            position: 'is-bottom',
            type: 'is-danger'
            })
        })   
    },
    methods: {
        plantModal: function(phr) {
            this.$modal.open({
                parent: this,
                component: PlantModal,
                props: { phr: phr }
            });
        }
    }
}
</script>
