<template>
    <div class="row margin-top">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <table class="table table-striped table-hover" style="text-align: center;">
                <thead>
                    <tr>
                        <th style="color: #2e4e7e; font-size: 20px">班车号</th>
                        <th style="color: #2e4e7e; font-size: 20px">发车时间</th>
                        <th style="color: #2e4e7e; font-size: 20px">售票量</th>
                        <th style="color: #2e4e7e; font-size: 20px">余票量</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="record in records" :key="record.tid" class="shape">
                        <td>{{ record.tid }}</td>
                        <td>{{ record.st_time }}</td>
                        <td>{{ record.soldout }}</td>
                        <td>{{ record.remainTicks }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-sm-2"></div>
    </div>
</template>

<script>
import $ from 'jquery'
import { ref } from 'vue'
import { useStore } from 'vuex'

export default {
    setup() {
        const store = useStore();
        let records = ref([]);

        const getSoldoutInfo = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/read/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    records.value = resp.train;
                    // console.log(resp);
                    // alert(resp.error_msg);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        getSoldoutInfo();

        return {
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
    height: 37px;
    line-height: 37px;
}
</style>