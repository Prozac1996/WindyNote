package cn.lcu.threepersontravel.WindyNote;

/**
 * Created by Administrator on 2017/6/4.
 * 笔记的对象
 * 包含了在数据库中存储的笔记ID，题目，修改时间，具体内容。
 */
public class Note {
    private int mId;
    private String mTitle;
    private String mDate;
    private String mContent;
    private int Category;

    public Note(int mId, String mTitle, String mDate, String mContent,int Category) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mContent = mContent;
        this.Category = Category;
    }

    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmContent() {
        return mContent;
    }

    public int getCategory() {
        return Category;
    }
}
