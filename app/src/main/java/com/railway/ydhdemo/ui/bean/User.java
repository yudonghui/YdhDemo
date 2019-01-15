package com.railway.ydhdemo.ui.bean;


import com.railway.ydhdemo.common.HttpResponse;

import java.io.Serializable;
import java.util.List;

public class User extends HttpResponse {
    private List<DataBean> data;

    @Override
    public String toString() {
        return "TestBean{" +
                "data=" + data +
                '}';
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 2762
         * rid : 1000797
         * author : 网络
         * title : 于氏
         * summary : 于氏家谱
         * picurl : null
         * count : 1
         * create_time : 1538023180
         * status : 1
         * reply_number : 0
         * like : 0
         * isanonymous : 0
         * content_imgs :
         * city : 中国
         * nikename : 东东
         * avatar : 11,0c36bc1e07ce
         */

        private String id;
        private String rid;
        private String author;
        private String title;
        private String summary;
        private Object picurl;
        private String count;
        private String create_time;
        private String status;
        private String reply_number;
        private String like;
        private String isanonymous;
        private String content_imgs;
        private String city;
        private String nikename;
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public Object getPicurl() {
            return picurl;
        }

        public void setPicurl(Object picurl) {
            this.picurl = picurl;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReply_number() {
            return reply_number;
        }

        public void setReply_number(String reply_number) {
            this.reply_number = reply_number;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getIsanonymous() {
            return isanonymous;
        }

        public void setIsanonymous(String isanonymous) {
            this.isanonymous = isanonymous;
        }

        public String getContent_imgs() {
            return content_imgs;
        }

        public void setContent_imgs(String content_imgs) {
            this.content_imgs = content_imgs;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "{" +
                    "id='" + id + '\'' +
                    ", rid='" + rid + '\'' +
                    ", author='" + author + '\'' +
                    ", title='" + title + '\'' +
                    ", summary='" + summary + '\'' +
                    ", picurl=" + picurl +
                    ", count='" + count + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", status='" + status + '\'' +
                    ", reply_number='" + reply_number + '\'' +
                    ", like='" + like + '\'' +
                    ", isanonymous='" + isanonymous + '\'' +
                    ", content_imgs='" + content_imgs + '\'' +
                    ", city='" + city + '\'' +
                    ", nikename='" + nikename + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }
}
