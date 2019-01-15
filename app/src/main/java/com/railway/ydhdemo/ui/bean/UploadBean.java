package com.railway.ydhdemo.ui.bean;

public class UploadBean {

    /**
     * fid : 12,269764ec299a
     * fileName : 102977074253_avatar.png
     * fileUrl : http://res.ixungen.cn:8083/12,269764ec299a
     * size : 34185
     */

    private String fid;
    private String fileName;
    private String fileUrl;
    private int size;

    @Override
    public String toString() {
        return "UploadBean{" +
                "fid='" + fid + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", size=" + size +
                '}';
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
