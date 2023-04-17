import ajax from '@/utils/request'


export default {
    getPage(params) {
        return ajax.get("/role/page", { params });
    },
    save(data) {
        return ajax.post("/role/", data);
    },
    delete(data) {
        return ajax.delete("/role/" + data);
    }
}