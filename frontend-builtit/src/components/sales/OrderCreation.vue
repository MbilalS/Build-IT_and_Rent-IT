<template>
    <b-tabs type="is-toggle" expanded v-model="activeTab">
        <b-tab-item label="Query catalog">
            <catalog-query @submitCatalogQuery="handleCatalogQuery"></catalog-query>
        </b-tab-item>
        <b-tab-item label="Select plant">
            <query-result :plants="plants" :givenRentalPeriod="givenRentalPeriod" @selectReviewOrder="handleOrder"></query-result>
        </b-tab-item>
    </b-tabs>
</template>

<script>

import CatalogQuery from "./CatalogQuery.vue"
import QueryResult from "./QueryResult.vue"
import SelectedPO from "../dialogboxes/SelectedPO.vue"

import axios from "axios"

export default {
    name: "OrderCreation",
    components: {
        CatalogQuery,
        QueryResult
    },
    data: function() {
        return {
            activeTab: 0,
            plants: [{id: 1, name:"exc", description:"Mini", price:"100"}],
            givenRentalPeriod: {
                startDate: "",
                endDate: ""
            },
            order: {
                plant: {},
                rentalPeriod: {}
            },
            createdorder: {
                customerDetails: {},
                plant: {},
                rentalPeriod: {}
            }
        }
    },
    methods: {
        handleCatalogQuery: function(query) {
            if (query.name && query.startDate && query.endDate) {
                let params = {
                    name: query.name,
                    startDate: "2018-04-01",
                    endDate: "2018-04-10"
                }
                axios.get("http://localhost:8080/api/procurements/plants", {params: params})
                .then(response => {
                    console.log(response.data)
                    this.activeTab = 1
                    this.plants = response.data._embedded.plantList
                    this.givenRentalPeriod.startDate = query.startDate
                    this.givenRentalPeriod.endDate = query.endDate
                })
            }
        },
        handleOrder: function(selectedPHR) {
            console.log(selectedPHR)
            this.$modal.open({
                parent: this,
                component: SelectedPO
            });
            axios.post("http://localhost:8080/api/procurements/requests", selectedPHR)
            .then(response => {
                console.log(response.data, "something")
                this.$toast.open({
                message: 'PHR request has been created',
                type: 'is-success'
                })
            }).catch(error => {
                this.$toast.open({
                duration: 5000,
                message: `Something's not good, PHR request is not created`,
                position: 'is-bottom',
                type: 'is-danger'
                })
            })
            this.$router.replace('/')
        }
    }
}
</script>
