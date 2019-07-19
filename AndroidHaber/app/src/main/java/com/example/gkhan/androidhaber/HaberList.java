package com.example.gkhan.androidhaber;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tchzafer on 21/03/2018.
 */

public class HaberList {

    private String HaberHeader;
    private String HaberContent;
    private String HaberTur;
    private int Like ;
    private int Unlike;
    private int ViewNum;
    private int imageID;
    private int HaberId;

    public static List<Integer> HaberImages = new ArrayList<>();
    public static List<String> HaberHeaders = new ArrayList<>();
    public static List<String> HaberTurs = new ArrayList<>();
    public static List<String> HaberContents = new ArrayList<>();
    public static List<Integer> HaberLikes = new ArrayList<>();
    public static List<Integer> HaberUnlikes = new ArrayList<>();
    public static List<Integer> ViewNums = new ArrayList<>();
    public static List<Integer> HaberIds = new ArrayList<>();



    public HaberList() {
    }

    public HaberList(int imageID,String haberTur,int haberId ,String HaberHeader, String HaberContent,int Like,int Unlike, int ViewNum) {
        this.imageID = imageID;
        this.HaberId = haberId;
        this.HaberTur = haberTur;
        this.HaberHeader = HaberHeader;
        this.HaberContent = HaberContent;
        this.Like = Like;
        this.Unlike = Unlike;
        this.ViewNum = ViewNum;

    }

    public int getImageID() {
        return imageID;
    }
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getHaberTur() {
        return HaberTur;
    }
    public void setHaberTur(String HaberTur) {
        this.HaberTur = HaberTur;
    }

    public  int getHaberId() {
        return HaberId;
    }
    public void setHaberId(int HaberId) {
        this.HaberId = HaberId;
    }

    public int getLike() {
        return Like;
    }
    public void setLike(int Like) {
        this.Like = Like;
    }

    public int getUnlike() {
        return Unlike;
    }
    public void setUnlike(int Unlike) {
        this.Unlike = Unlike;
    }

    public int getViewNum() {
        return ViewNum;
    }
    public void setViewNum(int ViewNum) {
        this.ViewNum = ViewNum;
    }

    public String getHaberHeader() {
        return HaberHeader;
    }

    public void setHaberHeader(String HaberHeader) {
        this.HaberHeader = HaberHeader;
    }

    public String getHaberContent() {
        return HaberContent;
    }

    public void setHaberContent(String HaberContent) {
        this.HaberContent = HaberContent;
    }

    public static ArrayList<HaberList> getData() {
        ArrayList<HaberList> haberList = new ArrayList<HaberList>();
        for (int i = 0; i < HaberImages.size(); i++) {
            HaberList temp = new HaberList();
            temp.setHaberId(HaberIds.get(i));
            temp.setHaberTur(HaberTurs.get(i));
            temp.setImageID(HaberImages.get(i));
            temp.setHaberHeader(HaberHeaders.get(i));
            temp.setHaberContent(HaberContents.get(i));
            temp.setLike(HaberLikes.get(i));
            temp.setUnlike(HaberUnlikes.get(i));
            temp.setViewNum(ViewNums.get(i));
            haberList.add(temp);

        }


        return haberList;
    }

}