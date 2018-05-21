<template>
<div class="modal-card" style="width: auto">
    <header class="modal-card-head">
        <p class="modal-card-title">Plant Hire Request Decision</p>
    </header>
    <section class="modal-card-body">
        <b-field>
            <b-input type="textarea"
                v-model="comments"
                minlength="10"
                maxlength="500"
                placeholder="comments">
            </b-input>
        </b-field>
    </section>
    <footer class="modal-card-foot">
        <button class="button is-success is-outlined" type="button" @click="approve(phr)" v-on:click="$parent.close()">Approve</button>
        <button class="button is-danger is-outlined" type="button" @click="reject(phr)" v-on:click="$parent.close()">Reject</button>
        <button class="button is-dark is-outlined" type="button" @click="$parent.close()">Close</button>
    </footer>
</div>
</template>

<script>

import axios from "axios"

export default {
    name: "PHRdecision",
    props: ['phr'],
    data: function() {
        return {
            comments: "",
            worksEngineer: {
                _id: 2,
                name: "Masla wo nahi",
                type: "WORKS_ENGINEER",
                _links: {
                    self: {
                        href: "http://localhost:8080/api/employees/2"
                        }
                    }
                }
        }
    },
    methods: {
        approve: function(phr) {
            phr.comments = this.comments
            phr.worksEngineer = this.worksEngineer
            var id = phr._id
            console.log(phr, "phr")
            axios.post("http://localhost:8080/api/procurements/requests/" + id + "/accept", phr)
            .then(response => {
                console.log(response.data, "response.data")
            }).catch(error => {
                this.$toast.open({
                duration: 5000,
                message: `Something's not good, PHR is not created`,
                position: 'is-bottom',
                type: 'is-danger'
                })
            })
            this.$router.go(this.$router.parentRoute)
        },
        reject: function(phr) {
            phr.comments = this.comments
            phr.worksEngineer = this.worksEngineer
            var id = phr._id
            console.log(phr, "phr")
            axios.delete("http://localhost:8080/api/procurements/requests/" + id + "/accept", {data: phr})
            .then(response => {
                console.log(response.data, "response.data")
            }).catch(error => {
                this.$toast.open({
                duration: 5000,
                message: `Something's not good, PHR is not created`,
                position: 'is-bottom',
                type: 'is-danger'
                })
            })
            this.$router.go(this.$router.parentRoute)
        }
    }
}
</script>