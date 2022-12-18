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
                    <!-- <li class="nav-item pos">
                        <a class="nav-link" href="#">退票</a>
                    </li> -->
                    <li class="nav-item pos">
                        <router-link :class="route_name === 'queryview' ? 'nav-link active' : 'nav-link'"
                            :to="{ name: 'queryview' }">查询</router-link>
                    </li>
                    <li class="nav-item pos">
                        <!-- Button trigger modal -->
                        <a class="nav-link" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                            购票
                        </a>

                        <!-- Modal -->
                        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false"
                            tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="staticBackdropLabel">请输入班车号进行购票：
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input v-model="tid" type="text" class="form-control">
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" @click="purchase()">确认购买</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item pos" v-if="$store.state.user.limit === 1">
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
                                <router-link class="dropdown-item" :to="{ name: 'loginview' }">我的订单</router-link>
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
import { ref } from 'vue'
import $ from 'jquery'

export default {
    setup() {
        const route = useRoute();
        const store = useStore();
        let route_name = computed(() => route.name);
        let tid = ref('');

        const logout = () => {
            store.dispatch("logout");
        }

        const purchase = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/buy/",
                type: "post",
                data: {
                    tid: tid.value,
                    status: "1",
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                    alert(resp.error_msg);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        return {
            route_name,
            logout,
            purchase,
            tid,
        }
    }
}
</script>
  
<style scoped>
body {
    overflow-x: hidden;
    font-family: 'Roboto', sans-serif;
    font-size: 16px;
}

#viewport {
    padding-left: 250px;
    -webkit-transition: all 0.5s ease;
    -moz-transition: all 0.5s ease;
    -o-transition: all 0.5s ease;
    transition: all 0.5s ease;
}

#sidebar {
    z-index: 1000;
    position: fixed;
    left: 250px;
    width: 250px;
    height: 100%;
    margin-left: -250px;
    overflow-y: auto;
    background: rgb(233, 239, 241);
    -webkit-transition: all 0.5s ease;
    -moz-transition: all 0.5s ease;
    -o-transition: all 0.5s ease;
    transition: all 0.5s ease;
}

#sidebar header {
    background-color: #3d4e5b;
    font-size: 20px;
    line-height: 62px;
    text-align: center;
}

#sidebar header a {
    color: #fff;
    display: block;
    text-decoration: none;
}

#sidebar .nav {}

#sidebar .nav a {
    background: none;
    /* border-bottom: 1px solid #838f95; */
    color: #000;
    font-size: 16px;
    padding: 12px 24px;
    display: block;
    width: 250px;
    text-decoration: none;
}

#sidebar .nav a:hover {
    background: none;
    color: skyblue;
}

#sidebar .nav a i {
    margin-right: 16px;
}

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
  