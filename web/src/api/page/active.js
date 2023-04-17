import ajax from '@/utils/request'


export default {
    getPage(params) {
        return ajax.get("/option/page", { params });
    },
    save(data) {
        return ajax.post("/option/", data);
    },
    delete(data) {
        return ajax.delete("/option/" + data);
    }
}