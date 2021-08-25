package com.wei.musicserver.controller;

import com.wei.musicserver.dao.SongInfo;
import com.wei.musicserver.service.MusicParserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MusicParserController {

    @Resource
    MusicParserService musicParserService;

    /**
     *
     * @param keyword 查询的关键字
     * @param pn 查询的页码数
     * @param rn 当前页的返回数量
     * @return 歌曲信息列标
     */
    @GetMapping("/search")
    public List<SongInfo> search(@RequestParam("keyword")String keyword,
                                 @RequestParam(value = "pn", required = false, defaultValue = "0")Integer pn,
                                 @RequestParam(value = "rn", required = false, defaultValue = "10")Integer rn){
        //去除字符串前后空格
        keyword = StringUtils.trimWhitespace(keyword);
        //判断关键字是否为空
        if (!StringUtils.hasText(keyword)){
            return new ArrayList<>();
        }
        return musicParserService.search(keyword, pn, rn);
    }

    @GetMapping("/getSongDownloadUrl")
    public List<String> getSongDownloadUrl(@RequestParam(value = "format", required = false,defaultValue = "mp3")String format,
                                           @RequestParam(value = "rid")String rid,
                                           @RequestParam(value = "br",required = false,defaultValue = "320kmp3")String br){

        return musicParserService.getSongDownloadUrl(format, rid, br);
    }

    @GetMapping("/getSongLrc")
    public Map<String, String> getSongLrc(@RequestParam(value = "rid")String rid){
        return   musicParserService.getSongLrc(rid);

    }

}
