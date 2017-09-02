package cn.lcu.threepersontravel.WindyNote.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.*;
import cn.lcu.threepersontravel.WindyNote.CategoryAdapter;
import cn.lcu.threepersontravel.WindyNote.Note;
import cn.lcu.threepersontravel.WindyNote.NoteAdapter;
import cn.lcu.threepersontravel.WindyNote.R;
import cn.lcu.threepersontravel.WindyNote.database.NoteDatabaseHandler;

import java.util.Collections;
import java.util.List;

public class MyActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    /**
     * Called when the activity is first created.
     */

    public static final int REQUEST_ADD = 1;
    private ListView mLv_Notes;
    private ImageView mIv_add;
    private ImageView mIv_picture;
//    private ImageView mIv_upload;
//    private ImageView mIv_download;
    private DrawerLayout mDrawerLayout;
    private NoteAdapter mAdapter;
    private NoteDatabaseHandler mDataHandler;

    private CategoryAdapter mCategoryAdapter;

    private long nowCategory = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        mLv_Notes = (ListView) findViewById(R.id.lv_notes);
        mIv_add = (ImageView) findViewById(R.id.iv_add);
        mIv_picture = (ImageView) findViewById(R.id.iv_add_picture);
//        mIv_upload = (ImageView) findViewById(R.id.iv_menu_upload);
//        mIv_download = (ImageView) findViewById(R.id.iv_menu_down);
        mLv_Notes.setEmptyView(findViewById(R.id.layout_empty));
        mDataHandler = new NoteDatabaseHandler(this);
        mAdapter = new NoteAdapter(this,getNotes(0));
        mLv_Notes.setAdapter(mAdapter);
        mLv_Notes.setOnItemClickListener(this);
        mLv_Notes.setOnItemLongClickListener(this);
        mIv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail = new Intent(MyActivity.this, DetailActivity.class);
                startActivityForResult(intentDetail,REQUEST_ADD);
            }
        });
        mIv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        mIv_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //上传至服务器
//                List<Note> list = getNotes(0);
//                MyLeanCloudApp.uploadNotes(list);
//                Toast.makeText(MyActivity.this, "上传完成", Toast.LENGTH_SHORT).show();
//            }
//        });
//        mIv_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //下载
//                AVQuery<AVObject> query = new AVQuery<>("Note");
//                query.findInBackground(new FindCallback<AVObject>() {
//                    @Override
//                    public void done(List<AVObject> list, AVException e) {
//                        ArrayList<Note> notes = new ArrayList();
//                        for(AVObject obj : list){
//                            int id = (int) obj.get("id");
//                            String title = obj.getString("Title");
//                            String content = obj.getString("Content");
//                            int category = (int) obj.get("Category");
//                            String date = obj.getString("Date");
//                            Note temp = new Note(id,title,date,content,category);
//                            notes.add(temp);
//                        }
//                        for(Note note : notes){
//                            mDataHandler.add(note.getmTitle(),note.getmContent(),0);
//                        }
//                        mAdapter.notifyData(notes);
//                    }
//                });
//
//
//            }
//        });
        initDrawer();
    }


    //侧滑菜单初始化
    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView iv_menu = (ImageView) findViewById(R.id.iv_menu_left);
        RelativeLayout category_all = (RelativeLayout) findViewById(R.id.category_all);
        ListView lv_category = (ListView) findViewById(R.id.drawer_list_category);
        mCategoryAdapter = new CategoryAdapter(this,mDataHandler.queryAllCategory());
        lv_category.setAdapter(mCategoryAdapter);

        lv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //主页面显示分类后的结果
                nowCategory = id;
                mAdapter.notifyData(getNotes(nowCategory));
                mDrawerLayout.closeDrawers();
            }
        });

        lv_category.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //长按后显示提示框，修改或删除分类
                AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
                builder.setTitle("修改分组名");
                EditText et_add = new EditText(MyActivity.this);
                et_add.setText(((TextView)view.findViewById(R.id.tv_category_name)).getText().toString());
                builder.setView(et_add);
                builder.setPositiveButton("修改分类名", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = mCategoryAdapter.getItemId(position);
                        mDataHandler.modifyCategory(id,et_add.getText().toString());
                        dialog.dismiss();
                        mCategoryAdapter.notifyData(mDataHandler.queryAllCategory());
                    }
                });
                builder.setNegativeButton("删除此分类", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = mCategoryAdapter.getItemId(position);
                        mDataHandler.deleteCategory(id);
                        dialog.dismiss();
                        mCategoryAdapter.notifyData(mDataHandler.queryAllCategory());
                        if(nowCategory == id) {
                            nowCategory = 0;
                            mAdapter.notifyData(getNotes(nowCategory));
                        }
                    }
                });
                builder.create().show();
                return false;
            }
        });
        Button btn_edit_category = (Button) findViewById(R.id.drawer_btn_edit);
        btn_edit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
                builder.setTitle("增加分组");
                EditText et_add = new EditText(MyActivity.this);
                builder.setView(et_add);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDataHandler.addCategory(et_add.getText().toString());
                        mCategoryAdapter.notifyData(mDataHandler.queryAllCategory());
                    }
                });
                builder.create().show();
            }
        });
        category_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击所有笔记后显示
                nowCategory = 0;
                mAdapter.notifyData(getNotes(nowCategory));
                mDrawerLayout.closeDrawers();
            }
        });
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开侧边栏
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                    mDrawerLayout.closeDrawers();
                else
                    mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }


    private List<Note> getNotes(long id) {
        List list;
        if(id == 0)
            list = mDataHandler.queryAll();
        else
            list = mDataHandler.queryByCategory(id);
        Collections.reverse(list);
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //此处写点击笔记后的代码，显示详细页面查看笔记
        Intent intentDetail = new Intent(this, DetailActivity.class);
        intentDetail.putExtra("ID",id);
        startActivityForResult(intentDetail, REQUEST_ADD);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.item_add:
//                Intent intentDetail = new Intent(this, DetailActivity.class);
//                startActivityForResult(intentDetail,REQUEST_ADD);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //此处写长按笔记之后的代码，显示提示是否删除的Dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否确认删除？");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDataHandler.delete(id);
                mAdapter.notifyData(getNotes(nowCategory));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("不删了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main,menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_ADD){
                mAdapter.notifyData(getNotes(nowCategory));
            }
        }
    }
}
