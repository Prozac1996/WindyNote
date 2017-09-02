package cn.lcu.threepersontravel.WindyNote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import cn.lcu.threepersontravel.WindyNote.Category;
import cn.lcu.threepersontravel.WindyNote.CategoryAdapter;
import cn.lcu.threepersontravel.WindyNote.Note;
import cn.lcu.threepersontravel.WindyNote.R;
import cn.lcu.threepersontravel.WindyNote.database.NoteDatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */
public class DetailActivity extends Activity {

    private EditText et_title,et_content;
    private Spinner spinner_category;
    private NoteDatabaseHandler mDbHandler;
    private CategoryAdapter mCategoryAdapter;
    private boolean isUpdate = false;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("ID",0);
        setContentView(R.layout.activity_detail);
        et_title = (EditText) findViewById(R.id.et_detail_title);
        et_content = (EditText) findViewById(R.id.et_detail_content);
        spinner_category = (Spinner) findViewById(R.id.spinner_category);

        mDbHandler = new NoteDatabaseHandler(this);
        mCategoryAdapter = new CategoryAdapter(this,initCategory());
        spinner_category.setAdapter(mCategoryAdapter);
        if(id != 0) {
            isUpdate = true;
            Note note = mDbHandler.query(id);
            if (note != null) {
                et_title.setText(note.getmTitle());
                et_content.setText(note.getmContent());
                long categoryId = note.getCategory();
                spinner_category.setSelection(mCategoryAdapter.getPosition(categoryId));
            }
        }


    }

    private List<Category> initCategory() {
        List list = new ArrayList<>();
        list.add(new Category(0,"默认分类"));
        list.addAll(mDbHandler.queryAllCategory());
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                if(isUpdate){
                    mDbHandler.modify(id,et_title.getText().toString(),et_content.getText().toString(),spinner_category.getSelectedItemId());
                    setResult(RESULT_OK);

                }else{
                    mDbHandler.add(et_title.getText().toString(),et_content.getText().toString(),spinner_category.getSelectedItemId());
                    setResult(RESULT_OK);
                }
                finish();
                break;
            case R.id.item_share:
                EditText editText=(EditText)findViewById(R.id.et_detail_title);
                String s=editText.getText().toString();
                EditText editText1=(EditText)findViewById(R.id.et_detail_content);
                String h=editText1.getText().toString();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,s+":"+h+"   ————来自清风笔记");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
