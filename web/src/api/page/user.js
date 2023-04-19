import ajax from '@/utils/request'


export default {
    getPage(params) {
        return ajax.get("/user/page", { params });
    },
    save(data) {
        return ajax.post("/user/", data);
    },
    delete(data) {
        return ajax.delete("/user/" + data);
    },
    multidelete(data) {
        return ajax.post("/user/del/batch", data);
    },
    getinfo() {
        return ajax.get("/user/username");
    }
}