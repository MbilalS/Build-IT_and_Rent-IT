<template>
  <table class="table is-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Description</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="(plant, plant_index) in plants" :key="plant_index">
            <td>{{plant.name}}</td>
            <td>{{plant.description}}</td>
            <td class="has-text-right">{{plant.price}}</td>
            <td>
                <a class="button is-link is-small is-outlined" @click="select(plant)">Select plant</a>
            </td>
        </tr>
    </tbody>
  </table>
</template>

<script>

import moment from 'moment'

export default {
    name: "QueryResult",
    props: ["plants", "givenRentalPeriod"],
    data: function() {
        return {
            selectedPHR: {
                rentalPeriod: {
                    startDate: "",
                    endDate: ""
                },
                siteEngineer: {_id: 1},
                constructionSite: {_id: 1},
                plantInventoryEntry: {
                    description: "",
                    name: "",
                    price: "",
                    _id: "",
                    _link: "",
                }
            }
        }
    },
    methods: {
        select: function(plant) {
            // this.selectedPlant.rentalPeriod.startDate = moment(String(this.givenRentalPeriod.startDate)).format('MM/DD/YYYY')
            // this.selectedPlant.rentalPeriod.endDate = moment(String(this.givenRentalPeriod.endDate)).format('MM/DD/YYYY')
            this.selectedPHR.plantInventoryEntry.description = plant.description
            this.selectedPHR.plantInventoryEntry.name = plant.name
            this.selectedPHR.plantInventoryEntry.price = plant.price
            this.selectedPHR.plantInventoryEntry._id = plant._id
            this.selectedPHR.plantInventoryEntry._link = plant._links.self.href
            this.selectedPHR.rentalPeriod.startDate = this.givenRentalPeriod.startDate
            this.selectedPHR.rentalPeriod.endDate = this.givenRentalPeriod.endDate
            this.$emit("selectReviewOrder", this.selectedPHR)
        }
    }

}
</script>
