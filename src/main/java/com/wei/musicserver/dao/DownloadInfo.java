package com.wei.musicserver.dao;

import lombok.Data;

@Data
public class DownloadInfo {

    private String level;
    private String bitrate;
    private String format;
    private String size;
}
