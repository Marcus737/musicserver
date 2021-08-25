package com.wei.musicserver.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SongInfo {

    //歌曲id
    String musicID;
    //歌名
    String songName;
    //作家
    String artist;
    //专辑名
    String album;
    //封面
    String pic;
    //json歌词
    String lrc;
    //播放地址，默认MP3
    String playURL;
    //音乐格式
    List<DownloadInfo> downloadInfoList;

}


