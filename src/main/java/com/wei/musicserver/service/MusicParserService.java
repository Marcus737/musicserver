package com.wei.musicserver.service;

import com.wei.musicserver.dao.SongInfo;

import java.util.List;
import java.util.Map;

public interface MusicParserService {

    List<SongInfo> search(String keyword, Integer pn, Integer rn);

    List<String> getSongDownloadUrl(String format, String rid, String br);

    Map<String, String> getSongLrc(String rid);
}
