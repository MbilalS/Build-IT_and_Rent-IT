<template>
<div class="modal-card" style="width: auto">
    <header class="modal-card-head">
        <p class="modal-card-title">Create Plant Hire Request</p>
    </header>
    <section class="modal-card-body">
        <p>
        Your selected plant has been save. <br>
        Wait for Works Engineer's feedback<br>
        </p>
    </section>
    <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
    </footer>
</div>
</template>

<script>
export default {
    name: "SelectedPO"
}
</script>