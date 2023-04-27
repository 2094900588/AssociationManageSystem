import ajax from '@/utils/request'


export default {
    upload_photo(file) {
        let headers = {
            'Content-Type': 'multipart/form-data',
        }
        return ajax.post("/file/upload_photo", file, headers)
    },
    upload_annex(file) {
        let headers = {
            'Content-Type': 'multipart/form-data',
        }
        return ajax.post("/file/upload_annex", file, headers)
    }
}