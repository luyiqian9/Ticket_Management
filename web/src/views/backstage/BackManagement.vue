<template>
    <div class="main-header margin-top">
        <div class="content-header">
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2"></div>
                <div class="col-sm-8">
                    <table class="table table-hover">
                        <tbody>
                            <tr>
                                <th>新增列车</th>
                                <td>
                                    <router-link class="btn btn-primary" :to="{ name: 'createview' }">添加</router-link>
                                </td>
                            </tr>
                            <tr>
                                <th>修改列车信息</th>
                                <td>
                                    <a class="btn btn-primary">修改</a>
                                </td>
                            </tr>
                            <tr>
                                <th>统计售票情况</th>
                                <td>
                                    <router-link class="btn btn-primary" :to="{ name: 'soldoutview' }">查询</router-link>
                                </td>
                            </tr>
                            <tr>
                                <th>停运列车</th>
                                <td>
                                    <a class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#staticBack">停运</a>

                                    <!-- Modal -->
                                    <div class="modal fade" id="staticBack" data-bs-backdrop="static"
                                        data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel"
                                        aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">请输入待停运的班车号：
                                                    </h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <input v-model="tid" type="text" class="form-control">
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-danger"
                                                        @click="deletev()">确认停运</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-sm-2"></div>
            </div>
        </div>
    </div>

</template>

<script>
import $ from 'jquery'
import { useStore } from 'vuex'
import { ref } from 'vue'

export default {
    setup() {
        const store = useStore();
        let tid = ref('');

        const deletev = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    tid: tid.value,
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
            deletev,
            tid,
        }
    }
}

</script>

<style scoped>
.margin-top {
    margin-top: 3vh;
}
</style>