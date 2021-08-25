package com.wei.musicserver.service.impl;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.wei.musicserver.constant.URLList;
import com.wei.musicserver.dao.DownloadInfo;
import com.wei.musicserver.dao.SongInfo;
import com.wei.musicserver.service.MusicParserService;
import com.wei.musicserver.util.JSON;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MusicParserServiceImpl implements MusicParserService {


    @Override
    public List<SongInfo> search(String keyword, Integer pn, Integer rn) {
        List<SongInfo> res = new ArrayList<>();
        String URL = String.format(URLList.SONG_SEARCH_URL, keyword, pn, rn);
        String jsonRes = HttpUtil.get(URL);
        JsonNode head;
        try {
            head = JSON.begin(jsonRes);
        }catch (Exception e){
            return res;
        }
        //获取搜索结果的个数
        int count = JSON.getWithString(head, "SHOW").asInt();
        if (count == 0){
            return res;
        }
        //获取abslist
        JsonNode abslist = JSON.getWithString(head, "abslist");
        for (int i = 0; i < count; i++) {
            //获取单个歌曲信息列表
            JsonNode subList = JSON.getWithIndex(abslist, i);
            SongInfo songInfo = new SongInfo();
            //获取歌曲rid
            String rid = JSON.getStringWithString(subList, "MUSICRID").substring(6);
            String songName = JSON.getStringWithString(subList, "SONGNAME");
            String artist = JSON.getStringWithString(subList, "ARTIST");
            String album = JSON.getStringWithString(subList, "ALBUM");

            String pic = String.format(URLList.SONG_PIC_URL, JSON.getStringWithString(subList, "web_albumpic_short"));
            String MINFO = JSON.getStringWithString(subList, "MINFO");
            List<DownloadInfo> downloadInfoList = getDownloadInfoList(MINFO);
            songInfo.setMusicID(rid);
            songInfo.setSongName(songName);
            songInfo.setArtist(artist);
            songInfo.setAlbum(album);
            songInfo.setPic(pic);
            songInfo.setDownloadInfoList(downloadInfoList);
            res.add(songInfo);
        }
        return res;
    }
    

    private List<DownloadInfo> getDownloadInfoList(String minfo) {
        List<DownloadInfo> downloadInfos = new ArrayList<>();
        String[] types = minfo.split(";");
        for (String type: types) {
            DownloadInfo downloadInfo = new DownloadInfo();
            String[] elements = type.split(",");
            downloadInfo.setLevel(elements[0].split(":")[1]);
            downloadInfo.setBitrate(elements[1].split(":")[1]);
            downloadInfo.setFormat(elements[2].split(":")[1]);
            downloadInfo.setSize(elements[3].split(":")[1]);
            downloadInfos.add(downloadInfo);
        }
        return downloadInfos;
    }

    @Override
    public List<String> getSongDownloadUrl(String format, String rid, String br) {
        List<String> res = new ArrayList<>(1);
        String URL = String.format(URLList.SONG_DOWNLOAD_URL, format, rid, br);
        res.add(HttpUtil.get(URL));
        return res;
    }

    @Override
    public Map<String, String> getSongLrc(String rid) {
        HashMap<String, String> map = new HashMap<>();
        String lrcURL = String.format(URLList.SONG_LRC_URL, rid);
        String resJson = HttpUtil.get(lrcURL);
        String temp = resJson;
        JsonNode lrcNode = null;
        String lrc = "";
        try {
            lrcNode = JSON.begin(temp);
            lrc = JSON.getStringWithString(JSON.getWithString(lrcNode, "data"), "lrclist");
            map.put("lrc", toLrc(lrc));
        }catch (Exception e){
            map.put("error", e.toString());
            map.put("response", resJson);
        }
        return map;
    }

    private String toLrc(String s){
        // 按指定模式在字符串查找
        String pattern = "(?<=:).*?(?=[,}])";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        StringBuilder sb = new StringBuilder();
        List<String> wordList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        //复数歌词，单数时间
        int count = 0;
        while(m.find()){
            String res = m.group();
            if (count++ % 2 != 0){
                String rt = "[%s:%s]";
                double d = Double.parseDouble(res);
                int oneMinute = 60;
                int c = (int) d / oneMinute;
                d -= c * oneMinute;
                String min = String.valueOf(c);
                String sec = String.format("%.2f", d);
                if (c < 60){
                    min = "0" + c;
                }
                if (d < 10){
                    sec = "0" + sec;
                }
                res = String.format(rt, min, sec);
            }
            if (count % 2 != 0){
                wordList.add(res);
            }else {
                timeList.add(res);
            }
        }
        for (int i = 0; i < wordList.size(); i++) {
            sb.append(timeList.get(i)).append(wordList.get(i)).append("\n");
        }
        return sb.toString();
    }

}
