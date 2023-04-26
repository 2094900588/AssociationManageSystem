import ajax from '@/utils/request'


export default {
    save(data) {
        return ajax.post("/course/", data);
    },
    getall() {
        return ajax.get("/course/");
    },
    getone(params) {
        return ajax.get("/course/one", { params });
    },
    del(params) {
        return ajax.delete("/course/", { params });
    },
    list() {
        return ajax.get("/course/list");
    }
}