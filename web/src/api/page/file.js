import ajax from '@/utils/request'


export default {
    uploadfile(file) {
        let headers = {
            'Content-Type': 'multipart/form-data',
        }
        return ajax.post("/file/upload", file, headers)
    }
}