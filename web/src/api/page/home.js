import ajax from '@/utils/request'


export default {
    getIntePage(params) {
        return ajax.get("/club/page/ranking", { params });
    },
    getNumPage(params) {
        return ajax.get("/club/page/sum", { params });
    },
    getinfo() {
        return ajax.get("/sys/getinfo")
    }
}