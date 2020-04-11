package com.ryd.gyy.guolinstudy.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Util.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 危险权限需要动态获取。按照权限申请流程跑一遍。使用内容提供器查询手机联系人。增删改查内容提供者的数据库。
 */
public class PerContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_call;
    private Button createDatabase;
    private Button add_data;
    private Button update_data;
    private Button delete_data;
    private Button query_data;

    ArrayAdapter<String> adapter;

    List<String> contactsList = new ArrayList<>();

    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_my);
        initView();
    }

    private void initView() {
        btn_call = (Button) findViewById(R.id.btn_call);
        btn_call.setOnClickListener(this);

//        查询联系人部分-------------------------------
        ListView contactsView = (ListView) findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);
        readMyContact();//读取本app的内容提供者的数据库。注意：可以在本app里面查看自己的内容管理者的数据库
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
        } else {
            readContacts();
        }

//        内容提供者的增删改查-------------------------------
        add_data = (Button) findViewById(R.id.add_data);//增加
        add_data.setOnClickListener(this);
        update_data = (Button) findViewById(R.id.update_data);//修改
        update_data.setOnClickListener(this);
        delete_data = (Button) findViewById(R.id.delete_data);//删除
        delete_data.setOnClickListener(this);
        query_data = (Button) findViewById(R.id.query_data);//查询
        query_data.setOnClickListener(this);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call:
//                打电话
                if (ContextCompat.checkSelfPermission(PerContactsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PerContactsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);//请求码唯一就可以
                } else {
                    call();
                    Log.e("yyy", "有权限:------------- ");
                }
                break;
            case R.id.add_data:
                // 添加数据
                Uri uri = Uri.parse("content://com.ryd.gyy.guolinstudy/book");
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("author", "George Martin");
                values.put("pages", 1040);
                values.put("price", 55.55);
                Uri newUri = getContentResolver().insert(uri, values);//返回的是：/book/3
                Log.e("yyy", "newUri: " + newUri.getPath());
                newId = newUri.getPathSegments().get(1);//从/开始获取，get(0)就是book get(1)就是3
                break;
            case R.id.update_data:
//                修改表格
                Uri uri2 = Uri.parse("content://com.ryd.gyy.guolinstudy/book/" + newId);
                ContentValues values1 = new ContentValues();
                values1.put("name", "A Storm of Swords");
                values1.put("pages", 1216);
                values1.put("price", 24.05);
                int updatarow = getContentResolver().update(uri2, values1, null, null);
                Log.e("yyy", "updatarow: " + updatarow);//返回的是修改的行数，也就是总共修改了几行，这里只修改了1行
                break;
            case R.id.delete_data:
//                删除表格
                Uri uri3 = Uri.parse("content://com.ryd.gyy.guolinstudy/book/" + newId);
                int deleterow = getContentResolver().delete(uri3, null, null);
                Log.e("yyy", "deleterow: " + deleterow);//返回的是共删除了几行
                break;
            case R.id.query_data:
//                查询表格
                // 查询Book表中所有的数据
                Uri uri1 = Uri.parse("content://com.ryd.gyy.guolinstudy/book");
                Cursor cursor = getContentResolver().query(uri1, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("yyy", "book name is " + name);
                        Log.d("yyy", "book author is " + author);
                        Log.d("yyy", "book pages is " + pages);
                        Log.d("yyy", "book price is " + price);
                    }
                    cursor.close();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 0表示同意
                    call();
                    Log.e("yyy", "用户同意:------------- ");
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 拨打电话
     */
    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取联系人
     */
    private void readContacts() {
        Cursor cursor = null;
        try {
            // 查询联系人数据：ContactsContract有对应Uri，直接传入就好了。Uri的组合：authority + path = 包名 + 表
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 获取联系人姓名：有对应的常量字符串
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 获取联系人手机号：有对应的常量字符串
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName + "\n" + number);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            最后记得关闭
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void readMyContact() {
        Uri uri = Uri.parse("content://com.ryd.gyy.guolinstudy/book");
        Cursor cursor = null;
        try {
            // 查询联系人数据：ContactsContract有对应Uri，直接传入就好了。Uri的组合：authority + path = 包名 + 表
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 获取联系人姓名：有对应的常量字符串
                    String displayName = cursor.getString(cursor.getColumnIndex("name"));
                    // 获取联系人手机号：有对应的常量字符串
                    String number = cursor.getString(cursor.getColumnIndex("pages"));
                    contactsList.add(displayName + "\n" + number);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            最后记得关闭
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
