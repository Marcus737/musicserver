package com.wei.musicserver.constant;

public class URLList {

//    public static final String SONG_DOWNLOAD_URL = "http://www.kuwo.cn/url?format=%s&response=url&type=convert_url&rid=%s&br=%s"; 被修复了
    public static final String SONG_DOWNLOAD_URL = "http://antiserver.kuwo.cn/anti.s?type=convert_url&rid=%s&format=mp3&response=url";

    public static final String SONG_SEARCH_URL = "http://search.kuwo.cn/r.s?client=kt&all=%s&pn=%d&rn=%d&uid=374255680&ver=kwplayer_ar_9.0.4.2&vipver=1&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerg=1&mobi=1\n";

    public static final String SONG_PIC_URL = "https://img2.kuwo.cn/star/albumcover/%s";

    public static final String SONG_LRC_URL = "http://m.kuwo.cn/newh5/singles/songinfoandlrc?musicId=%s";
}
