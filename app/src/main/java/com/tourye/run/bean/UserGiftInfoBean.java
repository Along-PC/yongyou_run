package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   UserGiftInfoBean
 *
 * @Author:   along
 *
 * @Description:  地址管理信息实体
 *
 * @CreateDate:   2019/3/27 10:09 AM
 *
 */
public class UserGiftInfoBean {

    /**
     * status : 0
     * timestamp : 1553651990
     * data : {"name":null,"gender":null,"age":null,"mobile":null,"district_1_id":null,"district_2_id":null,"district_3_id":null,"address":null,"gifts":[{"id":44,"name":"套餐1","specification":null,"need_gender":true,"choices":[{"id":27,"gender":"female","text":"S","left_count":null},{"id":28,"gender":"female","text":"M","left_count":null},{"id":29,"gender":"female","text":"L","left_count":null},{"id":30,"gender":"female","text":"XL","left_count":null},{"id":31,"gender":"female","text":"XXL","left_count":null},{"id":32,"gender":"male","text":"L","left_count":null},{"id":33,"gender":"male","text":"XL","left_count":null},{"id":34,"gender":"male","text":"XXL","left_count":null},{"id":35,"gender":"male","text":"XXXL","left_count":null},{"id":36,"gender":"male","text":"XXXXL","left_count":null}],"can_change":true,"size_table":"http://static.ro-test.xorout.com/package_gift_size_table/2019-03-26-14-54-09/3f315f37365e4f60a0648ea516d145f2.jpg?auth_key=1553655590-eb231db4e50e4616ad581a0bc7cca3db-0-33187e2052365f6f69680d41138f7176"}],"can_change_address":true}
     */

    private int status;
    private int timestamp;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : null
         * gender : null
         * age : null
         * mobile : null
         * district_1_id : null
         * district_2_id : null
         * district_3_id : null
         * address : null
         * gifts : [{"id":44,"name":"套餐1","specification":null,"need_gender":true,"choices":[{"id":27,"gender":"female","text":"S","left_count":null},{"id":28,"gender":"female","text":"M","left_count":null},{"id":29,"gender":"female","text":"L","left_count":null},{"id":30,"gender":"female","text":"XL","left_count":null},{"id":31,"gender":"female","text":"XXL","left_count":null},{"id":32,"gender":"male","text":"L","left_count":null},{"id":33,"gender":"male","text":"XL","left_count":null},{"id":34,"gender":"male","text":"XXL","left_count":null},{"id":35,"gender":"male","text":"XXXL","left_count":null},{"id":36,"gender":"male","text":"XXXXL","left_count":null}],"can_change":true,"size_table":"http://static.ro-test.xorout.com/package_gift_size_table/2019-03-26-14-54-09/3f315f37365e4f60a0648ea516d145f2.jpg?auth_key=1553655590-eb231db4e50e4616ad581a0bc7cca3db-0-33187e2052365f6f69680d41138f7176"}]
         * can_change_address : true
         */

        private String name;
        private String gender;
        private String age;
        private String mobile;
        private String district_1_id;
        private String district_2_id;
        private String district_3_id;
        private String address;
        private boolean can_change_address;
        private List<GiftsBean> gifts;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDistrict_1_id() {
            return district_1_id;
        }

        public void setDistrict_1_id(String district_1_id) {
            this.district_1_id = district_1_id;
        }

        public String getDistrict_2_id() {
            return district_2_id;
        }

        public void setDistrict_2_id(String district_2_id) {
            this.district_2_id = district_2_id;
        }

        public String getDistrict_3_id() {
            return district_3_id;
        }

        public void setDistrict_3_id(String district_3_id) {
            this.district_3_id = district_3_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isCan_change_address() {
            return can_change_address;
        }

        public void setCan_change_address(boolean can_change_address) {
            this.can_change_address = can_change_address;
        }

        public List<GiftsBean> getGifts() {
            return gifts;
        }

        public void setGifts(List<GiftsBean> gifts) {
            this.gifts = gifts;
        }

        public static class GiftsBean {
            /**
             * id : 44
             * name : 套餐1
             * specification : null
             * need_gender : true
             * choices : [{"id":27,"gender":"female","text":"S","left_count":null},{"id":28,"gender":"female","text":"M","left_count":null},{"id":29,"gender":"female","text":"L","left_count":null},{"id":30,"gender":"female","text":"XL","left_count":null},{"id":31,"gender":"female","text":"XXL","left_count":null},{"id":32,"gender":"male","text":"L","left_count":null},{"id":33,"gender":"male","text":"XL","left_count":null},{"id":34,"gender":"male","text":"XXL","left_count":null},{"id":35,"gender":"male","text":"XXXL","left_count":null},{"id":36,"gender":"male","text":"XXXXL","left_count":null}]
             * can_change : true
             * size_table : http://static.ro-test.xorout.com/package_gift_size_table/2019-03-26-14-54-09/3f315f37365e4f60a0648ea516d145f2.jpg?auth_key=1553655590-eb231db4e50e4616ad581a0bc7cca3db-0-33187e2052365f6f69680d41138f7176
             */

            private int id;
            private String name;
            private int specification;
            private boolean need_gender;
            private boolean can_change;
            private String size_table;
            private List<ChoicesBean> choices;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSpecification() {
                return specification;
            }

            public void setSpecification(int specification) {
                this.specification = specification;
            }

            public boolean isNeed_gender() {
                return need_gender;
            }

            public void setNeed_gender(boolean need_gender) {
                this.need_gender = need_gender;
            }

            public boolean isCan_change() {
                return can_change;
            }

            public void setCan_change(boolean can_change) {
                this.can_change = can_change;
            }

            public String getSize_table() {
                return size_table;
            }

            public void setSize_table(String size_table) {
                this.size_table = size_table;
            }

            public List<ChoicesBean> getChoices() {
                return choices;
            }

            public void setChoices(List<ChoicesBean> choices) {
                this.choices = choices;
            }

            public static class ChoicesBean {
                /**
                 * id : 27
                 * gender : female
                 * text : S
                 * left_count : null
                 */

                private int id;
                private String gender;
                private String text;
                private int left_count;
                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getLeft_count() {
                    return left_count;
                }

                public void setLeft_count(int left_count) {
                    this.left_count = left_count;
                }
            }
        }
    }

}
