<template>
    <div class="text-center container mt">
        <h1>全部列车信息</h1>
        <h3 id="time" style="color: lightcoral; margin-top: 10px">{{ date }}</h3>
        <table class="table table-striped table-hover mt" style="text-align: center;">
            <thead>
                <tr>
                    <th style="color: #2e4e7e; font-size: 20px">班车号</th>
                    <th style="color: #2e4e7e; font-size: 20px">起点站</th>
                    <th style="color: #2e4e7e; font-size: 20px">终点站</th>
                    <th style="color: #2e4e7e; font-size: 20px">发车时间</th>
                    <th style="color: #2e4e7e; font-size: 20px">运行时间</th>
                    <th style="color: #2e4e7e; font-size: 20px">是否发车</th>
                    <th style="color: #2e4e7e; font-size: 20px">余票</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.tid" class="shape">
                    <td>{{ record.tid }}</td>
                    <td>{{ record.startStation }}</td>
                    <td>{{ record.endStation }}</td>
                    <td>{{ record.startTime }}</td>
                    <td>{{ record.runtime }}</td>
                    <td v-if="compare(record.startTime)">未发车</td>
                    <td v-else>已发车</td>
                    <td>{{ record.remainTicks }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import $ from 'jquery'
import { ref } from 'vue'
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();
        let date = ref('');
        let records = ref([]);

        function myDate() {
            var now = new Date();
            var year = now.getFullYear();
            var month = now.getMonth() + 1;
            var day = now.getDate();
            var hours = now.getHours();
            var minutes = now.getMinutes();
            var seconds = now.getSeconds();

            date.value = year + "年" + month + "月" + day + "日" + hours + "：" + minutes + "：" + seconds;

        }
        setInterval(myDate, 1000);

        let compare = (t) => {
            var c_time = new Date();
            var t1 = new Date(t);
            if (t1.getTime() > c_time.getTime())
                return true;
            else
                return false;
        }

        const pull_page = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/traininfo/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    // console.log(resp);
                    records.value = resp.train;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        pull_page();

        return {
            myDate,
            records,
            compare,
            date,
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