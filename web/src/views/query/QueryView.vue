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
                                <th>按班车号查询</th>
                                <td>
                                    <input v-model="query.tid" class="form-control float-start" placeholder="请输入列车号">
                                </td>
                                <td>
                                    <a class="btn btn-primary" @click="queryById()">查询</a>
                                </td>
                            </tr>
                            <tr>
                                <th>按终点站查询</th>
                                <td>
                                    <input v-model="query.destination" class="form-control float-start"
                                        placeholder="请输入终点站">
                                </td>
                                <td>
                                    <a class="btn btn-primary" @click="queryByDest()">查询</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <table class="table table-striped table-hover margin-top" style="text-align: center;">
                        <thead>
                            <tr>
                                <th>班车号</th>
                                <th>起点站</th>
                                <th>终点站</th>
                                <th>发车时间</th>
                                <th>余票</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="record in records" :key="record.tid" class="shape">
                                <td>{{ record.tid }}</td>
                                <td>{{ record.st }}</td>
                                <td>{{ record.ed }}</td>
                                <td>{{ record.st_time }}</td>
                                <td>{{ record.remainTicks }}</td>
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
import { ref, reactive } from 'vue'

export default {
    setup() {
        const store = useStore();

        const query = reactive({
            tid: "",
            destination: "",
        });

        let records = ref([]);

        const queryById = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/query/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    flextid: query.tid,
                    status: "tid",
                },
                success(resp) {
                    query.tid = "";
                    // console.log(resp);
                    records.value = resp.route;
                    alert(resp.error_msg);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const queryByDest = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/query/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    flextid: query.destination,
                    status: "spot",
                },
                success(resp) {
                    query.destination = "";
                    records.value = resp.route;
                    alert(resp.error_msg);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        return {
            queryById,
            queryByDest,
            query,
            records,
        }

    }
}


</script>

<style scoped>
.margin-top {
    margin-top: 6vh;
}

.shape {
    height: 40px;
    line-height: 40px;
}
</style>