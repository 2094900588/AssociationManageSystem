import ajax from '@/utils/request'


export default {
    getPage(params) {
        return ajax.get("/operator/page", { params });
    }
}