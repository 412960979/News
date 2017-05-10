package com.study.news.model;

import java.util.List;

public class VideoDataBean {
    private int count;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String movie_id;
        private String title;
        private String content;
        private String sort;
        private int praisenum;
        private String input_date;
        private String story_type;
        private String summary;
        private String audio;
        private String anchor;
        private String copyright;
        private UserBean user;
        private String charge_edt;
        private String editor_email;
        private List<AuthorListBean> author_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMovie_id() {
            return movie_id;
        }

        public void setMovie_id(String movie_id) {
            this.movie_id = movie_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getPraisenum() {
            return praisenum;
        }

        public void setPraisenum(int praisenum) {
            this.praisenum = praisenum;
        }

        public String getInput_date() {
            return input_date;
        }

        public void setInput_date(String input_date) {
            this.input_date = input_date;
        }

        public String getStory_type() {
            return story_type;
        }

        public void setStory_type(String story_type) {
            this.story_type = story_type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

        public String getAnchor() {
            return anchor;
        }

        public void setAnchor(String anchor) {
            this.anchor = anchor;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getCharge_edt() {
            return charge_edt;
        }

        public void setCharge_edt(String charge_edt) {
            this.charge_edt = charge_edt;
        }

        public String getEditor_email() {
            return editor_email;
        }

        public void setEditor_email(String editor_email) {
            this.editor_email = editor_email;
        }

        public List<AuthorListBean> getAuthor_list() {
            return author_list;
        }

        public void setAuthor_list(List<AuthorListBean> author_list) {
            this.author_list = author_list;
        }

        public static class UserBean {
            /**
             * user_id : 5957194
             * user_name : 壹毛镜
             * desc : 跳跳神儿
             * wb_name : 壹毛镜
             * is_settled : 0
             * settled_type : 0
             * summary : 跳跳神儿
             * fans_total : 10
             * web_url : http://image.wufazhuce.com/FrYUagTx04bGJF1dUv7KaixmjJY_
             */

            private String user_id;
            private String user_name;
            private String desc;
            private String wb_name;
            private String is_settled;
            private String settled_type;
            private String summary;
            private String fans_total;
            private String web_url;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getWb_name() {
                return wb_name;
            }

            public void setWb_name(String wb_name) {
                this.wb_name = wb_name;
            }

            public String getIs_settled() {
                return is_settled;
            }

            public void setIs_settled(String is_settled) {
                this.is_settled = is_settled;
            }

            public String getSettled_type() {
                return settled_type;
            }

            public void setSettled_type(String settled_type) {
                this.settled_type = settled_type;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getFans_total() {
                return fans_total;
            }

            public void setFans_total(String fans_total) {
                this.fans_total = fans_total;
            }

            public String getWeb_url() {
                return web_url;
            }

            public void setWeb_url(String web_url) {
                this.web_url = web_url;
            }
        }

        public static class AuthorListBean {
            /**
             * user_id : 5957194
             * user_name : 壹毛镜
             * desc : 跳跳神儿
             * wb_name : 壹毛镜
             * is_settled : 0
             * settled_type : 0
             * summary : 跳跳神儿
             * fans_total : 10
             * web_url : http://image.wufazhuce.com/FrYUagTx04bGJF1dUv7KaixmjJY_
             */

            private String user_id;
            private String user_name;
            private String desc;
            private String wb_name;
            private String is_settled;
            private String settled_type;
            private String summary;
            private String fans_total;
            private String web_url;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getWb_name() {
                return wb_name;
            }

            public void setWb_name(String wb_name) {
                this.wb_name = wb_name;
            }

            public String getIs_settled() {
                return is_settled;
            }

            public void setIs_settled(String is_settled) {
                this.is_settled = is_settled;
            }

            public String getSettled_type() {
                return settled_type;
            }

            public void setSettled_type(String settled_type) {
                this.settled_type = settled_type;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getFans_total() {
                return fans_total;
            }

            public void setFans_total(String fans_total) {
                this.fans_total = fans_total;
            }

            public String getWeb_url() {
                return web_url;
            }

            public void setWeb_url(String web_url) {
                this.web_url = web_url;
            }
        }
    }
}
