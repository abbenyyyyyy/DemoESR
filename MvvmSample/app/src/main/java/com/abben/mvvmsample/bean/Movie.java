package com.abben.mvvmsample.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

/**
 * Created by abben on 2017/5/2.
 */
@Entity(indices = {@Index("name")}, tableName = "movies")
public class Movie implements Serializable {

    /**
     * type : 国产电影
     * name : 港囧
     * imageOfMovie : http://abben-picture.oss-cn-shenzhen.aliyuncs.com/港囧.jpg
     * summaryOfMovie : 导演: 徐峥 编剧: 束焕 / 苏亮 / 赵英俊 / 邢爱娜 / 徐峥 主演: 徐峥 / 赵薇 / 包贝尔 / 杜鹃 / 葛民辉 / 李璨琛 / 潘虹 / 赵有亮 / 朱媛媛 / 王迅 / 冯勉恒 / 王晶 / 叶竞生 / 林晓峰 / 车保罗 / 苑琼丹 / 林雪 / 江约诚 / 郑丹瑞 / 田启文 / 吴耀汉 / 林路迪 / 詹瑞文 / 庄思敏 / 陶虹 类型: 喜剧 制片国家/地区: 中国大陆 语言: 汉语普通话 / 粤语 上映日期: 2015-09-25(中国大陆) 片长: 114分钟 又名: 人再囧途之港囧 / 人在囧途之港囧 / 泰囧续集 / Lost In Hong Kong 徐来（徐峥 饰）曾经的梦想是成为一名油画家，可如今他早已沦为理想炮灰，在一家大地内衣公司当小老板。大地胸罩公司是岳父开的，他是上门女婿。虽然被全家人尊为最有出息人，但他对俗气的一家人很看不上，并把理想的沦陷归咎于他们。徐来对老婆蔡波（赵薇 饰）也一肚子意见。 大学同学20周年聚会上，徐来听到同学们谈论初恋杨伊（杜鹃 饰），顿时心猿意马。 不日，他陪伴老婆及家人来到香港游玩，计划和多年未见的杨伊偷偷会面，不料却被小舅子蔡拉拉（包贝尔 饰）缠住，拉拉一直梦想成为导演。在拍摄一部以家人为主题的纪录片。在约见初恋的过程中，二人不知不觉卷入了一起坠楼命案。甜蜜的约会之旅，变成了一场惊心动魄的历险与逃亡...... 徐来和拉拉，将如何度过香港这难忘的一天？
     * printscreen : http://abben-picture.oss-cn-shenzhen.aliyuncs.com/港囧.jpg
     * baiduyun : http://pan.baidu.com/s/1i3o6q7j
     * yunPassword : 百度网盘(提取码：zf7q)
     * imageOfMovieHeight : 600
     * imageOfMovieWedth : 412
     */

    private String type;

    @NonNull
    @PrimaryKey
    private String name;
    private String imageOfMovie;
    private String summaryOfMovie;
    private String printscreen;
    private String baiduyun;
    private String yunPassword;
    private String imageOfMovieHeight;
    private String imageOfMovieWidth;

    public Movie(String type, @NonNull String name, String imageOfMovie, String summaryOfMovie, String printscreen,
                 String baiduyun, String yunPassword, String imageOfMovieHeight, String imageOfMovieWidth) {
        this.type = type;
        this.name = name;
        this.imageOfMovie = imageOfMovie;
        this.summaryOfMovie = summaryOfMovie;
        this.printscreen = printscreen;
        this.baiduyun = baiduyun;
        this.yunPassword = yunPassword;
        this.imageOfMovieHeight = imageOfMovieHeight;
        this.imageOfMovieWidth = imageOfMovieWidth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageOfMovie() {
        return imageOfMovie;
    }


    public void setImageOfMovie(String imageOfMovie) {
        this.imageOfMovie = imageOfMovie;
    }

    public String getSummaryOfMovie() {
        return summaryOfMovie;
    }

    public void setSummaryOfMovie(String summaryOfMovie) {
        this.summaryOfMovie = summaryOfMovie;
    }

    public String getPrintscreen() {
        return printscreen;
    }

    public void setPrintscreen(String printscreen) {
        this.printscreen = printscreen;
    }

    public String getBaiduyun() {
        return baiduyun;
    }

    public void setBaiduyun(String baiduyun) {
        this.baiduyun = baiduyun;
    }

    public String getYunPassword() {
        return yunPassword;
    }

    public void setYunPassword(String yunPassword) {
        this.yunPassword = yunPassword;
    }

    public String getImageOfMovieHeight() {
        return imageOfMovieHeight;
    }

    public void setImageOfMovieHeight(String imageOfMovieHeight) {
        this.imageOfMovieHeight = imageOfMovieHeight;
    }

    public String getImageOfMovieWidth() {
        return imageOfMovieWidth;
    }

    public void setImageOfMovieWidth(String imageOfMovieWidth) {
        this.imageOfMovieWidth = imageOfMovieWidth;
    }
}
