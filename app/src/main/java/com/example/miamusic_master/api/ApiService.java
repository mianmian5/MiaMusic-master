package com.example.miamusic_master.api;

import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.CatlistBean;
import com.example.miamusic_master.bean.DailyRecommendBean;
import com.example.miamusic_master.bean.HighQualityPlayListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;
import com.example.miamusic_master.bean.RecommendPlayListBean;
import com.example.miamusic_master.bean.TopListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "http://127.0.0.1:3000";

//    @GET("login/cellphone")
//    Observable<LoginBean> login(@Query("phone") String phone, @Query("password") String password);
//
//    @GET("logout")
//    Observable<LogoutBean> logout();

    @GET("banner")
//    Observable<BannerBean> getBanner();
    Observable<BannerBean> getBanner(@Query("type") String type);

    @GET("recommend/resource")
    Observable<MainRecommendPlayListBean> getRecommendPlayList();

    @GET("recommend/songs")
    Observable<DailyRecommendBean> getDailyRecommend();

    @GET("toplist")
    Observable<TopListBean> getTopList();

//    @GET("dj/recommend")
//    Observable<DjRecommendBean> getRadioRecommend();
//
//    @GET("dj/recommend/type")
//    Observable<DjRecommendTypeBean> getDjRecommend(@Query("type") int type);

    @GET("top/playlist")
    Observable<RecommendPlayListBean> getPlayList(@Query("cat") String type, @Query("limit") int limit);

    @GET("top/playlist/highquality")
    Observable<HighQualityPlayListBean> getHighquality(@Query("limit") int limit, @Query("before") long before);

    @GET("playlist/catlist")
    Observable<CatlistBean> getCatlist();

//    @GET("playlist/detail")
//    Observable<PlaylistDetailBean> getPlaylistDetail(@Query("id") long id);
//
//    @GET("check/music")
//    Observable<MusicCanPlayBean> getMusicCanPlay(@Query("id") long id);
//
//    @GET("user/playlist")
//    Observable<UserPlaylistBean> getUserPlaylist(@Query("uid") long uid);
//
//    @GET("user/event")
//    Observable<UserEventBean> getUserEvent(@Query("uid") long uid, @Query("limit") int limit, @Query("lasttime") long time);
//
//    @GET("user/detail")
//    Observable<UserDetailBean> getUserDetail(@Query("uid") long uid);
//
//    @GET("search/hot/detail")
//    Observable<HotSearchDetailBean> getSearchHotDetail();
//
//    @GET("search")
//    Observable<SongSearchBean> getSongSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Observable<FeedSearchBean> getFeedSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Observable<SingerSearchBean> getSingerSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Observable<AlbumSearchBean> getAlbumSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Observable<PlayListSearchBean> getPlayListSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Observable<RadioSearchBean> getRadioSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Observable<UserSearchBean> getUserSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Observable<SynthesisSearchBean> getSynthesisSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("artists")
//    Observable<SingerSongSearchBean> getSingerHotSong(@Query("id") long id);
//
//    @GET("artist/album")
//    Observable<SingerAblumSearchBean> getSingerAlbum(@Query("id") long id);
//
//    @GET("artist/desc")
//    Observable<SingerDescriptionBean> getSingerDesc(@Query("id") long id);
//
//    @GET("simi/artist")
//    Observable<SimiSingerBean> getSimiSinger(@Query("id") long id);
//
//    @GET("likelist")
//    Observable<LikeListBean> getLikeList(@Query("uid") long uid);
//
//    @GET("song/detail")
//    Observable<SongDetailBean> getSongDetail(@Query("ids") long ids);
//
//    @GET("like")
//    Observable<LikeMusicBean> likeMusice(@Query("id") long id);
//
//    @GET("comment/music")
//    Observable<MusicCommentBean> getMusicComment(@Query("id") long id);
//
//    @GET("comment/like")
//    Observable<CommentLikeBean> likeComment(@Query("id") long id, @Query("cid") long cid, @Query("t") int t, @Query("type") int type);
//
//    @GET("playmode/intelligence/list")
//    Observable<PlayModeIntelligenceBean> getIntelligenceList(@Query("id") long id, @Query("pid") long pid);
//
//    @GET("album/sublist")
//    Observable<AlbumSublistBean> getAlbumSublist();
//
//    @GET("artist/sublist")
//    Observable<ArtistSublistBean> getArtistSublist();
//
//    @GET("mv/sublist")
//    Observable<MvSublistBean> getMvSublist();
//
//    @GET("personal_fm")
//    Observable<MyFmBean> getMyFm();
//
//    @GET("event")
//    Observable<MainEventBean> getMainEvent();
//
//    @GET("lyric")
//    Observable<LyricBean> getLyric(@Query("id") long id);
//
//    @GET("comment/playlist")
//    Observable<PlayListCommentBean> getPlaylistComment(@Query("id") long id);
//
//    @GET("dj/paygift")
//    Observable<DjPaygiftBean> getDjPaygift(@Query("limit") int limit, @Query("offset") int offset);
//
//    @GET("dj/category/recommend")
//    Observable<DjCategoryRecommendBean> getDjCategoryRecommend();
//
//    @GET("dj/catelist")
//    Observable<DjCatelistBean> getDjCatelist();
//
//    @GET("dj/sub")
//    Observable<DjSubBean> subDj(@Query("rid") long rid, @Query("t") int isSub);
//
//    @GET("dj/program")
//    Observable<DjProgramBean> getDjProgram(@Query("rid") long rid);
//
//    @GET("dj/detail")
//    Observable<DjDetailBean> getDjDetail(@Query("rid") long rid);
}