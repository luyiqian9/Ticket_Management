<template>
    <div class="container margin-top">
        <div class="card wid m-auto">
            <div class="card-body text-center">
                <h1>修改列车信息</h1>
                <form class="row g-3" style="margin-top: 10px">
                    <div class="col-12">
                        <label for="inputAddress" class="form-label">列车号</label>
                        <input v-model="tid" type="text" class="form-control" id="inputAddress" placeholder="请输入待修改列车号">
                    </div>
                    <div class="col-md-6">
                        <label for="inputEmail4" class="form-label">起点站</label>
                        <input v-model="start_station" type="text" class="form-control" id="inputEmail4"
                            placeholder="请输入起点站">
                    </div>
                    <div class="col-md-6">
                        <label for="inputPassword4" class="form-label">终点站</label>
                        <input v-model="end_station" type="text" class="form-control" id="inputPassword4"
                            placeholder="请输入终点站">
                    </div>
                    <div class="col-12">
                        <label for="inputAddress" class="form-label">核载人数</label>
                        <input v-model="load" type="text" class="form-control" id="inputAddress" placeholder="请输入核载人数">
                    </div>
                    <div class="col-md-6">
                        <label for="inputEmail4" class="form-label">发车时间</label>
                        <input v-model="st_time" type="datetime-local" class="form-control" id="inputEmail4">
                    </div>
                    <div class="col-md-6">
                        <label for="inputPassword4" class="form-label">到站时间</label>
                        <input v-model="ed_time" type="datetime-local" class="form-control" id="inputPassword4">
                    </div>
                    <div class="col-12" style="margin-top: 50px">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="gridCheck">
                            <label class="form-check-label" for="gridCheck">
                                Check me out
                            </label>
                        </div>
                    </div>
                    <div class="col-12">
                        <a class="btn btn-primary" @click="modify()">确认修改</a>
                    </div>
                </form>
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
        let load = ref('');
        let start_station = ref('');
        let end_station = ref('');
        let st_time = ref('');
        let ed_time = ref('');

        const modify = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/update/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    tid: tid.value,
                    load: load.value,
                    start_station: start_station.value,
                    end_station: end_station.value,
                    st_time: st_time.value,
                    ed_time: ed_time.value,
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
            tid,
            load,
            start_station,
            end_station,
            st_time,
            ed_time,
            modify,
        }
    }
}
</script>

<style scoped>
.margin-top {
    margin-top: 5vh;
}

.wid {
    width: 65%;
}
</style>