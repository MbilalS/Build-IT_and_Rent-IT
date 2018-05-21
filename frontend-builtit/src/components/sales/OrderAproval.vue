<template>
    <b-tabs type="is-toggle" expanded>
        <b-tab-item label="Review order">
            <review-order :phrs="phrs"></review-order>
        </b-tab-item>
        <b-tab-item label="Purchase orders status">
            <created-p-os></created-p-os>
        </b-tab-item>
    </b-tabs>
</template>

<script>

import ReviewOrder from "./ReviewOrder.vue"
import CreatedPOs from "./CreatedPOs.vue"

import axios from "axios"

export default {
    name: "OrderAproval",
    components: {
        ReviewOrder,
        CreatedPOs
    },
    data: function() {
        return {
            phrs: {},
        }
    },
    mounted: function() {
        axios.get("http://localhost:8080/api/procurements/requests")
        .then(response => {
            this.phrs = response.data._embedded.plantHireRequestDTOList
            var count = 0
            for (var i = 0; i < this.phrs.length; i++) {
                if(this.phrs[i].status == "PENDING_APPROVAL")
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
        }).catch(error => {
            this.$toast.open({
            duration: 5000,
            message: `Something's not good, PHR's are not loaded`,
            position: 'is-bottom',
            type: 'is-danger'
            })
        })   
    }
}
</script>
