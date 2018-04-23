package com.anso.mapalao.vo;

/**
 * @author qiumeng
 * @Package com.itrus.laweyecloud.wuzhong.entity
 * @Description:
 * @date 2018 2018/1/24 14:04
 */
public class WZ_API_FileDownLoadResponse extends WZ_API_Response {
    /**
     * 下载文件名字
     */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
