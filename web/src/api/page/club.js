import ajax from '@/utils/request'


export default {
    getPage(params) {
        return ajax.get("/club/page", { params });
    },
    save(data) {
        return ajax.post("/club/", data);
    },
    delete(data) {
        return ajax.delete("/club/" + data);
    }
}