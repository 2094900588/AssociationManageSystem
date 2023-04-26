import ajax from '@/utils/request'


export default {
    getPage(params) {
        return ajax.get("/am/page", { params });
    },
    save(data) {
        return ajax.post("/am/", data);
    },
    delete(data) {
        return ajax.delete("/am/" + data);
    },
    list() {
        return ajax.get("/am");
    }
}