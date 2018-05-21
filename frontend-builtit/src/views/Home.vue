<template>
  <div class="home">
    <section class="hero is-primary is-bold animated fadeIn">
      <div class="hero-body">
          <div class="container">
              <h1 class="title">
                  BUILDIT
              </h1>
          </div>
      </div>
    </section>
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'home',
  components: {
    HelloWorld
  }
}
</script>
