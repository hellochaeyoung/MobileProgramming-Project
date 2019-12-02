package kr.ac.hansung.ume.Board;

import android.graphics.Bitmap;

public class ItemDetail {//Item에 대한 정보들 관리하기 위한 클래스
    private Bitmap itmeImage;
    private String itemTitle;
    private String itemcontent;

    public Bitmap getItmeImage() {
        return itmeImage;
    }

    public void setItmeImage(Bitmap itmeImage) {
        this.itmeImage = itmeImage;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemcontent() {
        return itemcontent;
    }

    public void setItemcontext(String itemcontent) {
        this.itemcontent = itemcontent;
    }
}
