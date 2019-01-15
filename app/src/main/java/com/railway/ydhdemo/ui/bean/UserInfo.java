package com.railway.ydhdemo.ui.bean;

import com.railway.ydhdemo.common.HttpResponse;

public class UserInfo extends HttpResponse {

    /**
     * data : {"rid":"1000796","phone":"17621211799","nikename":"辉辉","truename":"冬辉","idcard":"41022219871226455X","surname":"于","email":null,"avatar":"12,0e42dbcd0204","coin":"0","money":"0.00","sex":"0","birthday":null,"region":"237641,240649,241594","city":"贵州省 毕节地区 黔西县","flag":"1","shop_status":1,"token":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyaWQiOiIxMDAwNzk2IiwicGhvbmUiOiIxNzYyMTIxMTc5OSIsImV4cCI6MTU0ODQ4MzMzM30.x7FRSI-5MHcBu3AkoByZuWZQyQ0O7hLGr-UCxZynGFA","exp":1548483333},"rong_yun_token":"N3gIPX9S4n2W/D0h9sv8kg1LEE/MkTj9euthjgs5NxffboXoipkYdkPRD8YEaSqWUVmxcks64EcqzswwmCkJGKUL2wp8ckEm","clanAssociationIds":"76,71,77"}
     * serverTime : 2019-01-11 14:15:33
     */

    private DataBean data;
    private String serverTime;

    @Override
    public String toString() {
        return "UserInfo{" +
                "data=" + data +
                ", serverTime='" + serverTime + '\'' +
                '}';
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public static class DataBean {
        /**
         * rid : 1000796
         * phone : 17621211799
         * nikename : 辉辉
         * truename : 冬辉
         * idcard : 41022219871226455X
         * surname : 于
         * email : null
         * avatar : 12,0e42dbcd0204
         * coin : 0
         * money : 0.00
         * sex : 0
         * birthday : null
         * region : 237641,240649,241594
         * city : 贵州省 毕节地区 黔西县
         * flag : 1
         * shop_status : 1
         * token : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyaWQiOiIxMDAwNzk2IiwicGhvbmUiOiIxNzYyMTIxMTc5OSIsImV4cCI6MTU0ODQ4MzMzM30.x7FRSI-5MHcBu3AkoByZuWZQyQ0O7hLGr-UCxZynGFA","exp":1548483333}
         * rong_yun_token : N3gIPX9S4n2W/D0h9sv8kg1LEE/MkTj9euthjgs5NxffboXoipkYdkPRD8YEaSqWUVmxcks64EcqzswwmCkJGKUL2wp8ckEm
         * clanAssociationIds : 76,71,77
         */

        private String rid;
        private String phone;
        private String nikename;
        private String truename;
        private String idcard;
        private String surname;
        private Object email;
        private String avatar;
        private String coin;
        private String money;
        private String sex;
        private Object birthday;
        private String region;
        private String city;
        private String flag;
        private int shop_status;
        private TokenBean token;
        private String rong_yun_token;
        private String clanAssociationIds;

        @Override
        public String toString() {
            return "DataBean{" +
                    "rid='" + rid + '\'' +
                    ", phone='" + phone + '\'' +
                    ", nikename='" + nikename + '\'' +
                    ", truename='" + truename + '\'' +
                    ", idcard='" + idcard + '\'' +
                    ", surname='" + surname + '\'' +
                    ", email=" + email +
                    ", avatar='" + avatar + '\'' +
                    ", coin='" + coin + '\'' +
                    ", money='" + money + '\'' +
                    ", sex='" + sex + '\'' +
                    ", birthday=" + birthday +
                    ", region='" + region + '\'' +
                    ", city='" + city + '\'' +
                    ", flag='" + flag + '\'' +
                    ", shop_status=" + shop_status +
                    ", token=" + token +
                    ", rong_yun_token='" + rong_yun_token + '\'' +
                    ", clanAssociationIds='" + clanAssociationIds + '\'' +
                    '}';
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public int getShop_status() {
            return shop_status;
        }

        public void setShop_status(int shop_status) {
            this.shop_status = shop_status;
        }

        public TokenBean getToken() {
            return token;
        }

        public void setToken(TokenBean token) {
            this.token = token;
        }

        public String getRong_yun_token() {
            return rong_yun_token;
        }

        public void setRong_yun_token(String rong_yun_token) {
            this.rong_yun_token = rong_yun_token;
        }

        public String getClanAssociationIds() {
            return clanAssociationIds;
        }

        public void setClanAssociationIds(String clanAssociationIds) {
            this.clanAssociationIds = clanAssociationIds;
        }

        public static class TokenBean {
            /**
             * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyaWQiOiIxMDAwNzk2IiwicGhvbmUiOiIxNzYyMTIxMTc5OSIsImV4cCI6MTU0ODQ4MzMzM30.x7FRSI-5MHcBu3AkoByZuWZQyQ0O7hLGr-UCxZynGFA
             * exp : 1548483333
             */

            private String token;
            private int exp;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getExp() {
                return exp;
            }

            public void setExp(int exp) {
                this.exp = exp;
            }
        }
    }
}
