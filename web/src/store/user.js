import $ from 'jquery'

export default {
    state: {
        uid: "",
        username: "",
        token: "",
        is_login: false,
        limit: "",
    },
    getters: {
    },
    mutations: {
        updateUser(state, user) {
            state.uid = user.uid;
            state.username = user.username;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.uid = "";
            state.username = "";
            state.is_login = false;
            state.token = "";
        },
    },
    actions: {
        login(context, data) {
            $.ajax({
                url: "http://127.0.0.1:3000/user/login/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp) {
                    if (resp.error_msg === "登录成功") {
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    }
                    else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
        logout(context) {
            context.commit("logout");
        },
        getinfo(context, data) {
            $.ajax({
                url: "http://127.0.0.1:3000/user/getinfo/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.error_msg === "success") {
                        context.commit("updateUser", {
                            uid: resp.user.uid,
                            username: resp.user.username,
                            is_login: true,
                        });
                        data.success(resp);
                    }
                    else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            })
        }

    },
    modules: {

    }

}