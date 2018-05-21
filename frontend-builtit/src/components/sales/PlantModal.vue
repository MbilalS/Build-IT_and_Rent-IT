<template>
<div class="modal-card" style="width: auto">
    <header class="modal-card-head">
        <p class="modal-card-title">Plant Hire Request info</p>
    </header>
    <section class="modal-card-body">
        <b-field label="Name">
            <b-input v-model="phr.plantInventoryEntry.name" readonly></b-input>
        </b-field>
        <b-field label="Description">
            <b-input v-model="phr.plantInventoryEntry.description" readonly></b-input>
        </b-field>
        <b-field label="Price">
            <b-input v-model="phr.plantInventoryEntry.price" readonly></b-input>
        </b-field>
        <b-field label="Construction Site">
            <b-input v-model="phr.constructionSite.name" readonly></b-input>
        </b-field>
        <b-field label="Site Engineer">
            <b-input v-model="phr.siteEngineer.name" readonly></b-input>
        </b-field>
        <b-field label="Status">
            <b-input v-model="phr.status" readonly></b-input>
        </b-field>
    </section>
    <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
    </footer>
</div>
</template>

<script>
export default {
    name: "PlantModal",
    props: ['phr']
}
</script>