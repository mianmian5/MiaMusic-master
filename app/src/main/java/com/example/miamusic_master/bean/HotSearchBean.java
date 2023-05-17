package com.example.miamusic_master.bean;

import java.util.List;

public class HotSearchBean {
    @Override
    public String toString() {
        return "HotSearchBean{" +
                "code=" + code +
                ", result=" + result +
                '}';
    }

    /**
     * code : 200
     * result : {"hots":[{"first":"他只是经过","second":1,"third":null,"iconType":1},{"first":"会不会","second":1,"third":null,"iconType":1},{"first":"永不失联的爱","second":1,"third":null,"iconType":1},{"first":"不曾遗忘的符号","second":1,"third":null,"iconType":1},{"first":"薛之谦","second":1,"third":null,"iconType":1},{"first":"日不落","second":1,"third":null,"iconType":1},{"first":"Dear John","second":1,"third":null,"iconType":1},{"first":"为你我受冷风吹","second":1,"third":null,"iconType":1},{"first":"魏大勋新歌","second":1,"third":null,"iconType":1},{"first":"起风了","second":1,"third":null,"iconType":1}]}
     */

    private int code;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<HotsBean> hots;

        public List<HotsBean> getHots() {
            return hots;
        }

        public void setHots(List<HotsBean> hots) {
            this.hots = hots;
        }

        public static class HotsBean {
            /**
             * first : 他只是经过
             * second : 1
             * third : null
             * iconType : 1
             */

            private String first;
            private int second;
            private Object third;
            private int iconType;

            public String getFirst() {
                return first;
            }

            public void setFirst(String first) {
                this.first = first;
            }

            public int getSecond() {
                return second;
            }

            public void setSecond(int second) {
                this.second = second;
            }

            public Object getThird() {
                return third;
            }

            public void setThird(Object third) {
                this.third = third;
            }

            public int getIconType() {
                return iconType;
            }

            public void setIconType(int iconType) {
                this.iconType = iconType;
            }
        }
    }
}
