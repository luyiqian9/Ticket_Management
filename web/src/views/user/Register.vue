<template>
    <div class="login-box">
        <h2>Register</h2>
        <form @submit.prevent="register">
            <div class="user-box">
                <input v-model="username" type="text" name="" required="true">
                <label>Username</label>
            </div>
            <div class="user-box">
                <input v-model="password" type="password" name="" required="true">
                <label>Password</label>
            </div>
            <div class="row">
                <div class="col-sm-8">
                    <div class="user-box">
                        <input v-model="sendto" type="text" name="" required="true">
                        <label>Email</label>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="user-box">
                        <button id="code" class="btn btn-outline-secondary" type="button" @click="getcode()">get
                            code</button>
                    </div>
                </div>
            </div>

            <div class="user-box">
                <input v-model="accesscode" type="text" name="" required="true">
                <label>Access code</label>
            </div>

            <a href="#" @click="register()">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                Submit
            </a>
        </form>
    </div>
</template>

<script>
import { ref } from 'vue'
import $ from 'jquery'
import router from '../../router';

export default {
    setup() {
        let username = ref('');
        let password = ref('');
        let sendto = ref('');
        let accesscode = ref('');

        const register = () => {
            $.ajax({
                xhrFields: {
                    withCredentials: true,
                },
                crossDomain: true,
                url: "http://127.0.0.1:3000/user/register/",
                type: "post",
                data: {
                    username: username.value,
                    password: password.value,
                    email: sendto.value,
                    accesscode: accesscode.value,
                },
                success(resp) {
                    console.log(resp);
                    alert(resp.error_msg);
                    if (resp.error_msg === "注册成功") {
                        router.push({ name: 'loginview' });
                    }
                },
                error(resp) {
                    console.log(resp);
                    alert(resp.error_msg);
                }
            })
        }

        function countdown(val, time, cnt) {
            if (time === 0) {
                val.disabled = false;
                val.innerHTML = "get code";
                clearInterval(cnt);
            } else {
                val.innerHTML = 'resend code(' + time + ')';
                time--;
            }
            return time;
        }


        const getcode = () => {
            let time = 60;
            let object = document.getElementById('code');
            object.disabled = true;
            countdown(object, time);
            let cnt = setInterval(function () {
                time = countdown(object, time, cnt);
            }, 1000);


            $.ajax({
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                url: "http://127.0.0.1:3000/getcode/",
                type: "post",
                data: {
                    sendto: sendto.value,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }

            })
        }

        return {
            username,
            password,
            register,
            getcode,
            sendto,
            accesscode,
        }
    }
}

</script>

<style scoped>
html {
    height: 100%;
}

body {
    margin: 0;
    padding: 0;
    font-family: sans-serif;
    background: linear-gradient(#141e30, #243b55);
    /* background: linear-gradient(#fff); */
}

.login-box {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 400px;
    padding: 40px;
    transform: translate(-50%, -50%);
    background: rgba(0, 0, 0, .7);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
    border-radius: 10px;
}

.login-box h2 {
    margin: 0 0 30px;
    padding: 0;
    color: #fff;
    text-align: center;
}

.login-box .user-box {
    position: relative;
}

.login-box .user-box input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    margin-bottom: 30px;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    background: transparent;
}

.login-box .user-box label {
    position: absolute;
    top: 0;
    left: 0;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    pointer-events: none;
    transition: .5s;
}

.login-box .user-box input:focus~label,
.login-box .user-box input:valid~label {
    top: -20px;
    left: 0;
    color: #03e9f4;
    font-size: 12px;
}

.login-box form a {
    position: relative;
    display: inline-block;
    padding: 10px 20px;
    color: #03e9f4;
    font-size: 16px;
    text-decoration: none;
    text-transform: uppercase;
    overflow: hidden;
    transition: .5s;
    margin-top: 40px;
    letter-spacing: 4px
}

.login-box a:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
        0 0 25px #03e9f4,
        0 0 50px #03e9f4,
        0 0 100px #03e9f4;
}

.login-box a span {
    position: absolute;
    display: block;
}

.login-box a span:nth-child(1) {
    top: 0;
    left: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(90deg, transparent, #03e9f4);
    animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
    0% {
        left: -100%;
    }

    50%,
    100% {
        left: 100%;
    }
}

.login-box a span:nth-child(2) {
    top: -100%;
    right: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(180deg, transparent, #03e9f4);
    animation: btn-anim2 1s linear infinite;
    animation-delay: .25s
}

@keyframes btn-anim2 {
    0% {
        top: -100%;
    }

    50%,
    100% {
        top: 100%;
    }
}

.login-box a span:nth-child(3) {
    bottom: 0;
    right: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(270deg, transparent, #03e9f4);
    animation: btn-anim3 1s linear infinite;
    animation-delay: .5s
}

@keyframes btn-anim3 {
    0% {
        right: -100%;
    }

    50%,
    100% {
        right: 100%;
    }
}

.login-box a span:nth-child(4) {
    bottom: -100%;
    left: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(360deg, transparent, #03e9f4);
    animation: btn-anim4 1s linear infinite;
    animation-delay: .75s
}

@keyframes btn-anim4 {
    0% {
        bottom: -100%;
    }

    50%,
    100% {
        bottom: 100%;
    }
}
</style>