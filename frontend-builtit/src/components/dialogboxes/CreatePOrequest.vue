<template>
<div class="modal-card" style="width: auto">
    <header class="modal-card-head">
        <p class="modal-card-title">Create Purchase Order</p>
    </header>
    <section class="modal-card-body">
        <p> Please wait!<br>
        For rentit employee's feedback. <br>
        Your Purchase order request has been submitted.<br>
        </p>
    </section>
    <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
    </footer>
</div>
</template>

<script>
export default {
    name: "CreatePOrequest"
}
</script>