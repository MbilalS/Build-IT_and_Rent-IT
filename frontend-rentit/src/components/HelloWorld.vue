<template>
  <div class="hello">
    <section class="hero is-primary is-bold animated fadeIn">
      <div class="hero-body">
          <div class="container">
              <h1 class="title">
                  RENTIT
              </h1>
          </div>
      </div>
    </section>
    <order-creation></order-creation>
  </div>
</template>

<script>

import OrderCreation from './sales/OrderCreation.vue';

export default {
  name: 'HelloWorld',
  components: {
    OrderCreation
  },
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
