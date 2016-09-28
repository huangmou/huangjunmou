package com.welink.myapp.entry;

/**
 * Created by dell on 2016/8/24.
 */
public class JokeEntry {
    public String content;
    public String hashId;
    public String unixtime;
    public String updatetime;
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(String unixtime) {
        this.unixtime = unixtime;
    }

    public String getUpadtetime() {
        return updatetime;
    }

    public void setUpadtetime(String upadtetime) {
        this.updatetime = upadtetime;
    }

    @Override
    public String toString() {
        return "JokeEntry{" +
                "content='" + content + '\'' +
                ", hadhId='" + hashId + '\'' +
                ", unixtime='" + unixtime + '\'' +
                ", upadtetime='" + updatetime + '\'' +
                '}';
    }
}
