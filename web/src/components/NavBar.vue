<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container wid">
            <!-- logo -->
            <!-- <img src="../assets/images/logo.png" class="img-fluid pos" alt="Face detect" /> -->

            <!-- 名 -->
            <a class="navbar-brand" href="#">Ticket_Management</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- 功能区 -->
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item pos">
                        <router-link :class="route_name == 'home' ? 'nav-link active' : 'nav-link'" aria-current="page"
                            :to="{ name: 'home' }">首页</router-link>
                    </li>
                    <li class="nav-item pos">
                        <a class="nav-link " href="#">购票</a>
                    </li>
                    <li class="nav-item pos">
                        <a class="nav-link" href="#">退票</a>
                    </li>
                    <li class="nav-item pos">
                        <router-link :class="route_name === 'queryview' ? 'nav-link active' : 'nav-link'"
                            :to="{ name: 'queryview' }">查询</router-link>
                    </li>
                    <li class="nav-item pos">
                        <router-link :class="route_name == 'backstageview' ? 'nav-link active' : 'nav-link'"
                            :to="{ name: 'backstageview' }">管理后台</router-link>
                    </li>
                </ul>


                <!-- dropdown -->
                <ul class="navbar-nav" v-if="$store.state.user.is_login">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            {{ $store.state.user.username }}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'loginview' }">我的资料</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a href="#" class="dropdown-item" @click="logout">退出登录</a>
                            </li>
                        </ul>
                    </li>
                </ul>

                <ul class="navbar-nav" v-else>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            个人中心
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'loginview' }">Login</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><router-link class="dropdown-item" :to="{ name: 'registerview' }">Register</router-link>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>
  
<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex';

export default {
    setup() {
        const route = useRoute();
        const store = useStore();
        let route_name = computed(() => route.name);

        const logout = () => {
            store.dispatch("logout");
        }

        return {
            route_name,
            logout,
        }
    }
}
</script>
  
<style scoped>
.wid {
    width: 80%;
    margin: auto;
}

nav .bgc {
    color: linear-gradient(#141e30, #243b55);
}

img {
    border-radius: 30px 30px 30px 30px;
}

.pos {
    margin-left: 20px;

}

.active {
    border-bottom: 2px solid skyblue;
}
</style>
  