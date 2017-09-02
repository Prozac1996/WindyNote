package cn.lcu.threepersontravel.WindyNote;

/**
 * Created by Administrator on 2017/6/10.
 */
public class Category {
    private long mId;
    private String mName;

    public Category(long mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public long getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }
}
