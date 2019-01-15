package com.railway.ydhdemo.ui.bean;

import com.railway.ydhdemo.common.HttpResponse;

public class VersionInfo extends HttpResponse {


    /**
     * data : {"id":"11","type":"android","version":"2.0.4","status":"0","build":"204","download_url":"https://www.ixungen.cn/download/xungen.apk","remark":"更流畅的体验","check":"1","create_time":"1494058486"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * type : android
         * version : 2.0.4
         * status : 0
         * build : 204
         * download_url : https://www.ixungen.cn/download/xungen.apk
         * remark : 更流畅的体验
         * check : 1
         * create_time : 1494058486
         */

        private String id;
        private String type;
        private String version;
        private String status;
        private String build;
        private String download_url;
        private String remark;
        private String check;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCheck() {
            return check;
        }

        public void setCheck(String check) {
            this.check = check;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
