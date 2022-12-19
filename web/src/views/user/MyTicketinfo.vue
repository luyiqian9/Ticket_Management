<template>
    <div class="text-center container mt">
        <h2>我的订单</h2>
        <h3 id="time" style="color: lightcoral; margin-top: 10px">{{ date }}</h3>
        <table class="table table-striped table-hover mt" style="text-align: center;">
            <thead>
                <tr>
                    <th style="color: #2e4e7e; font-size: 18px">班车号</th>
                    <th style="color: #2e4e7e; font-size: 18px">起点站</th>
                    <th style="color: #2e4e7e; font-size: 18px">终点站</th>
                    <th style="color: #2e4e7e; font-size: 18px">发车时间</th>
                    <th style="color: #2e4e7e; font-size: 18px">运行时间</th>
                    <th style="color: #2e4e7e; font-size: 18px">订单状态</th>
                    <th style="color: #2e4e7e; font-size: 18px">操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.tid" class="shape">
                    <td>{{ record.tid }}</td>
                    <td>{{ record.startStation }}</td>
                    <td>{{ record.endStation }}</td>
                    <td>{{ record.startTime }}</td>
                    <td>{{ record.runtime }}</td>
                    <td v-if="record.status == 1">购票成功</td>
                    <td v-else>退票成功</td>
                    <td v-if="record.status == 1"><button class="btn btn-primary"
                            @click="quit(record.tid, record.pid)">退票</button>
                    </td>
                    <td v-else><button class="btn btn-danger">已退</button></td>

                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import $ from 'jquery'
import { useStore } from 'vuex'
import { ref } from 'vue'

export default {
    setup() {
        const store = useStore();
        let records = ref([]);

        const refresh_Tickinfo = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/record/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    records.value = resp.info;
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        refresh_Tickinfo();

        const quit = (tid, pid) => {
            $.ajax({
                url: "http://127.0.0.1:3000/buy/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    tid: tid,
                    pid: pid,
                    status: "0",
                },
                success(resp) {
                    alert(resp.error_msg);
                    console.log(resp);
                    refresh_Tickinfo();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        return {
            records,
            quit,
        }

    }

}
</script>

<style scoped>
.mt {
    margin-top: 22px;
}

.shape {
    height: 37px;
    line-height: 37px;
}
</style>