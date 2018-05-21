<template>
    <div>
        <div class="padding">
            <a class="button is-primary is-outlined size" @click="SiteEngineer">Site Engineer</a><br><br><br>
            <a class="button is-primary is-outlined size" @click="WorksEngineer">Works Engineer</a>
        </div>
    </div>
</template>

<script>

import OrderCreation from './OrderCreation.vue';

export default {
    name: "EmployeeRights",
    methods: {
        SiteEngineer: function() {
            this.$router.replace('ordercreation')
        },
        WorksEngineer: function() {
            this.$router.replace('orderaproval')
        }
    }

}
</script>

<style>

.padding {
    padding-top: 100px
}
.size {
    height: 40px;
    width: 170px;
}

</style>
