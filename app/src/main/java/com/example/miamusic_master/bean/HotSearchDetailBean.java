package com.example.miamusic_master.bean;

import java.util.List;

public class HotSearchDetailBean {
    @Override
    public String toString() {
        return "HotSearchDetailBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : 200
     * data : [{"searchWord":"天黑黑","score":10196,"content":"我爱上 让我奋不顾身的一个人","source":0,"iconType":1,"iconUrl":"https://p1.music.126.net/2zQ0d1ThZCX5Jtkvks9aOQ==/109951163968000522.png","url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"薛之谦","score":9480,"content":"老薛一发歌就能掀起狂潮！","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"字字句句","score":9333,"content":"爱不是凝视对方 爱是展望同一个方向","source":0,"iconType":0,"iconUrl":"http://p4.music.126.net/nmiMWKVIe46vacnhAFrQvw==/1.jpg","url":"","alg":"featured"},{"searchWord":"林俊杰","score":9036,"content":"看看行走的CD机又发什么新歌啦~","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"天若有情","score":6517,"content":"当年的电影《天若有情》，现在听着歌都会泪目啊~","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"就让这大雨全都落下","score":6422,"content":"","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"张碧晨","score":5704,"content":"综艺节目《中国好声音》的首位女冠军！","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"中国说唱巅峰对决2023","score":5691,"content":"","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"金曲奖","score":5534,"content":"第34届金曲奖提名公布","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"featured"},{"searchWord":"五月天","score":5464,"content":"深情最是 阿信词 怪兽曲♡","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"你要的全拿走","score":5390,"content":"胡彦斌深情演唱虐心情歌","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"法老","score":5341,"content":"硬核chop说唱歌手法老！","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"张杰","score":4512,"content":"与张杰一起，从现在到未来，一直温暖无限！","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"恶作剧","score":4163,"content":"经典偶像剧《恶作剧之吻》片头曲","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"第三人称","score":3910,"content":"别难过，你在自己的生活中永远是第一人称。","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"极乐净土","score":3825,"content":"","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"起风了","score":2965,"content":"吴青峰献唱《加油，你是最棒的》主题曲","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"可不可以","score":2963,"content":"可不可以和你在一起","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"郑润泽","score":3402,"content":"","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"},{"searchWord":"陈奕迅","score":3298,"content":"年少不听陈奕迅，听懂已不再年少","source":0,"iconType":0,"iconUrl":null,"url":"","alg":"alg_search_rec_hotquery_base_hotquery"}]
     * message : success
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * searchWord : 天黑黑
         * score : 10196
         * content : 我爱上 让我奋不顾身的一个人
         * source : 0
         * iconType : 1
         * iconUrl : https://p1.music.126.net/2zQ0d1ThZCX5Jtkvks9aOQ==/109951163968000522.png
         * url :
         * alg : alg_search_rec_hotquery_base_hotquery
         */

        private String searchWord;
        private int score;
        private String content;
        private int source;
        private int iconType;
        private String iconUrl;
        private String url;
        private String alg;

        public String getSearchWord() {
            return searchWord;
        }

        public void setSearchWord(String searchWord) {
            this.searchWord = searchWord;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getIconType() {
            return iconType;
        }

        public void setIconType(int iconType) {
            this.iconType = iconType;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }
    }
}
