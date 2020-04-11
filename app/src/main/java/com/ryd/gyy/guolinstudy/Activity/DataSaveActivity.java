package com.ryd.gyy.guolinstudy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ryd.gyy.guolinstudy.Model.Comment;
import com.ryd.gyy.guolinstudy.Model.News;
import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Util.MySQLiteHelper;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ryd.gyy.guolinstudy.Util.MainApplication.getGlobalContext;

public class DataSaveActivity extends BaseActivity {

    private static final String TAG = "DataSaveActivity";

    SQLiteDatabase mdb;


    @BindView(R.id.editText3)
    EditText editText3;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @BindView(R.id.account)
    EditText accountEdit;

    @BindView(R.id.password)
    EditText passwordEdit;

    @BindView(R.id.remember_pass)
    CheckBox rememberPass;

    @BindView(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        文件存储部分
        String inputText = load();
        //TextUtils.isEmpty(inputText)相当于判断字符是否为null或者""空字符
        if (!TextUtils.isEmpty(inputText)) {
            editText3.setText(inputText);
            editText3.setSelection(inputText.length());//将输入光标移动到文本的末尾
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }
//SharedPreferences保存密码的功能
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            // 将账号和密码都设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

//        SQLite学习：升级需要有网络
//        版本为2：增加整张表
//        SQLiteOpenHelper dbHelper = new MySQLiteHelper(this, "demo.db", null, 2);
//        版本为3：增加一列
        SQLiteOpenHelper dbHelper = new MySQLiteHelper(this, "demo.db", null, 5);
        mdb = dbHelper.getWritableDatabase();//创建数据库
//        SQLiteDatabase db1 = Connector.getDatabase();

        insertData();
        modifyingData();
        deleteData();
        queryData();
        aggregateMethod();
    }

    /**
     * 插入数据的方法汇总
     * SQlite插入数据的几种方式：
     * 1、使用原生的SQL语言。比如db.execSQL("insert into person(name, age, sex) values('张三', 18,'男')");
     * 2、使用ContentValues(如下)
     * 3、使用实体类的save方法。见DataSaveActivity
     */
    private void insertData() {
//        验证插入数据---------------------------
//        News news = new News();
//        news.setTitle("这是一条新闻标题");
//        news.setContent("这是一条新闻内容");
//        news.setPublishDate(new Date());
//        Log.d("TAG", "news id is " + news.getId());
//        boolean saveState = news.save();
////        获取插入行对应的id，注意在save之后
//        Log.d("TAG", "news id is " + news.getId());
////        news.saveThrows(); //存储失败就会抛出一个DataSupportException异常
//        if (saveState) {
//            Toast.makeText(getGlobalContext(), "存储成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getGlobalContext(), "存储失败", Toast.LENGTH_SHORT).show();
//        }
//        验证插入数据：每次运行都会插入---------------------------
        Comment comment1 = new Comment();
        comment1.setContent("好评！");
        comment1.setPublishDate(new Date());
        comment1.save();
        if (comment1.save()) {
            Toast.makeText(getGlobalContext(), "comment1存储成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getGlobalContext(), "comment1存储失败", Toast.LENGTH_SHORT).show();
        }
        Comment comment2 = new Comment();
        comment2.setContent("赞一个");
        comment2.setPublishDate(new Date());
        comment2.save();
        News news = new News();
        news.getCommentList().add(comment1);
        news.getCommentList().add(comment2);
        news.setTitle("第二条新闻标题");
        news.setContent("第二条新闻内容");
        news.setPublishDate(new Date());
        news.setCommentCount(news.getCommentList().size());
        news.save();
//        一次性插入多条
        List<News> newsList = new ArrayList<News>();
        News news1 = new News();
        news1.setTitle("GYY第一条");
        News news2 = new News();
        news2.setTitle("GYY第二条");
        newsList.add(news1);
        newsList.add(news2);
        LitePal.saveAll(newsList);
    }

    /**
     * 修改数据的方法汇总
     */
    private void modifyingData() {
//      修改数据：使用LitePal修改数据：将第13行的title改为今日iPhone6发布
//        ContentValues values = new ContentValues();
//        values.put("title", "今日iPhone6发布");
//        LitePal.update(News.class, values, 13);

//      修改数据：把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”
//      注意：之前运行无效的原因是commentCount的判断条件是>0,应该写成>=0,因为评论数都是0根本没有符合大于0的数据
//        ContentValues values1 = new ContentValues();
//        values1.put("title", "今日iPhone6 Plus发布");
//        通过占位符的方式:运行无效
//        LitePal.updateAll(News.class, values1, "title = ? and commentCount > ?", "今日iPhone6发布", "0");

//        修改数据：在不使用ContentValues的情况下修改12行的title
        News updateNews = new News();
        updateNews.setContent("这是第一条的评论");
        updateNews.update(89);

//      updateAll()方法在不指定约束条件的情况下就是修改所有行的数据：这里是将所有的title都修改
//        ContentValues values = new ContentValues();
//        values.put("title", "今日iPhone6 Plus发布");
//        LitePal.updateAll(News.class, values);

//     修改数据(不使用ContentValues)：把news表中标题为“GYY第二条”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus++++发布”
//      注意：之前运行无效的原因是commentCount的判断条件是>0,应该写成>=0,因为评论数都是0根本没有符合大于0的数据
        News updateNews1 = new News();
        updateNews1.setTitle("今日iPhone6 Plus++++发布");
        updateNews1.updateAll("title = ? and commentCount >= ?", "GYY第二条", "0");

//        修改数据(不使用ContentValues)：把某一条数据修改成默认值。这里比如将所有的评论数置0，这里仅仅是将news中的commentCount置0，实际的评论内容(comment表)还是在的，并没有删除
//        News updateNews2 = new News();
//        updateNews2.setCommentCount(0);//这样设置是无效的，因为评论数默认是0，应该用setToDefault这个方法
//        updateNews2.setToDefault("commentCount");//这里先去掉这句话，先不把评论数置0
//        updateNews2.updateAll();
    }

    /**
     * 删除数据的方法汇总
     */
    private void deleteData() {
//      删除id是9的数据，包括以9作为外键的数据，返回的是被删除的记录。这里news中有一条数据，comment有两条以9为外键的数据，所以返回是3
//        删除之后id为9的数据就不存在了，只有8 10
        int deleteCount = LitePal.delete(News.class, 9);
        Log.d("TAG", "delete count is " + deleteCount);

//        news表中标题为“今日iPhone6发布”且评论数等于0的所有新闻都删除掉
        LitePal.deleteAll(News.class, "title = ? and commentCount = ?", "今日iPhone6 Plus发布", "0");

//        news表中所有的数据全部删除掉：有效
//        LitePal.deleteAll(News.class);

//        另外一种删除方法：作用于对象上上的。前提必须先持久化 1、有调用save() 2、通过LitePal中提供的查询方法从数据库中查出来的对象也是经过持久化的
        News newsdelete = new News();
        newsdelete.setTitle("这是一条即将被删除的数据");
        newsdelete.save();
        if (newsdelete.isSaved()) {
            Log.e(TAG, "deleteData: --------------------");
            newsdelete.delete();
        }
    }

    /**
     * 查询数据库
     */
    private void queryData() {
//        获取指定id的数据
        News news = LitePal.find(News.class, 88);
        Log.e(TAG, "queryData第一行: " + news.getTitle());
        List<Comment> listcomment = news.getComments();
        Log.e(TAG, "放弃使用激进查询,而使用连缀查询: " + listcomment.size());
        for (int i = 0; i < listcomment.size(); i++) {
            Log.e(TAG, "放弃使用激进查询:   " + i + "  " + listcomment.get(i).getContent());
            Log.e(TAG, "放弃使用激进查询:   " + i + "  " + listcomment.get(i).getId());
        }

        //获取News表中的第一条数据：第一条数据的id并不一定是1
        News firstNews = LitePal.findFirst(News.class);
        Log.e(TAG, "queryData firstNews第一行: " + firstNews.getTitle());

        //获取News表中的最后一条数据
        News lastNews = LitePal.findLast(News.class);
        Log.e(TAG, "queryData lastNews最后一行: " + lastNews.getTitle());

//        查询指定id的数据
        List<News> newsList = LitePal.findAll(News.class, 88, 89, 90);
        Log.e(TAG, "queryData: " + newsList.get(0).getTitle());
        Log.e(TAG, "queryData: " + newsList.get(1).getTitle());
        Log.e(TAG, "queryData: " + newsList.get(2).getTitle());

//        findAll接收传入数组
        long[] ids = new long[]{88, 89, 90};
        List<News> newsList1 = LitePal.findAll(News.class, ids);

//        查询news表所有的数据
        List<News> allNews = LitePal.findAll(News.class);

//        连缀查询
//        select:只返回对应两列（title content）的数据
//        筛选commentCount = 0
//        order排序方式。asc表示正序排序，desc表示倒序排序
//        limit：用于指定查询前几条数据(一个id是一条)
//        offset：偏移10，即11-20
        List<News> newsList2 = LitePal.select("title", "content")
                .where("commentCount = ?", "0")
                .order("publishDate asc").limit(4)
                .find(News.class);
        Log.e(TAG, "连缀查询: " + newsList2.size());
        for (int i = 0; i < newsList2.size(); i++) {
            Log.e(TAG, "连缀查询: " + i + "数据：" + newsList2.get(i).getContent() + " " + newsList2.get(i).getTitle() + " " + newsList2.get(i).getId());
        }

//        原生查询
        Cursor cursor = LitePal.findBySQL("select * from news where content!=?", "null");
        List<News> newsListC = new ArrayList<News>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Log.e(TAG, "id: " + id);
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
//查询到这里的时候报的异常Java.Lang.IllegalStateException: Couldn't read row 0, col -1 from CursorWindow.  Make sure the Cursor is initialized correctly before accessing data from it.
//解决：获取的列的名字有错误，应该是publishdate而不是publishDate，以数据表中列的名字为准。同理commentcount也是。导致getColumnIndex返回是-1
                Date publishDate = new Date(cursor.getLong(cursor.getColumnIndex("publishdate")));
                int commentCount = cursor.getInt(cursor.getColumnIndex("commentcount"));
                Log.e(TAG, "title: " + title);
                Log.e(TAG, "content: " + content);
                Log.e(TAG, "publishDate: " + publishDate);
                Log.e(TAG, "commentCount: " + commentCount);
                News newsC = new News();
                newsC.setId(id);
                newsC.setTitle(title);
                newsC.setContent(content);
                newsC.setPublishDate(publishDate);
                newsC.setCommentCount(commentCount);
                newsListC.add(newsC);
            } while (cursor.moveToNext());
        }

    }

    /**
     * 聚合函数:聚合函数都是要使用rawQuery()方法进行SQL查询，然后结果会封装到Cursor对象当中，接着我们再从Cursor中将结果取出
     */
    public void aggregateMethod() {
// 传统的聚合函数用法:统计news表中一共有多少行
        Cursor c = mdb.rawQuery("select count(*) from news", null);
        if (c != null && c.moveToFirst()) {
            int count = c.getInt(0);
            Log.d("TAG", "result is " + count);
        }
        c.close();

//传统的聚合函数用法:统计出news表中评论的总数量
        Cursor c1 = mdb.rawQuery("select sum(commentcount) from news", null);
        if (c1 != null && c1.moveToFirst()) {
            int count = c1.getInt(0);
            Log.d("TAG", "result111 is " + count);
        }
        c1.close();

//----------------count()方法主要是用于统计行数的----------------
//LitePal的聚合函数:统计news表中一共有多少行
        int result = LitePal.count(News.class);
        Log.e(TAG, "aggregateMethod: result:" + result);

//LitePal的聚合函数:统计news表中评论数为0的有多少行
        int result1 = LitePal.where("commentCount = ?", "0").count(News.class);
        Log.e(TAG, "aggregateMethod: result1:" + result1);

//----------------sum()方法主要是用于对结果进行求合的----------------
//        统计news表中评论的总数量
        int result2 = LitePal.sum(News.class, "commentCount", int.class);
        Log.e(TAG, "aggregateMethod: result2:" + result2);

//----------------average()方法主要是用于统计平均数----------------
//        统计news表中平均每条新闻有多少评论
        double result3average = LitePal.average(News.class, "commentCount");
        Log.e(TAG, "aggregateMethod: result3average:" + result3average);

//----------------max()方法主要用于求出某个列中最大的数值----------------
//        news表中所有新闻里面最高的评论数是多少
        int resultmax = LitePal.max(News.class, "commentCount", int.class);
        Log.e(TAG, "aggregateMethod: resultmax:" + resultmax);

//----------------min()方法主要用于求出某个列中最小的数值----------------
//        news表中所有新闻里面最少的评论数是多少
        int resultmin = LitePal.min(News.class, "commentCount", int.class);
        Log.e(TAG, "aggregateMethod: resultmin" + resultmin);
    }

    @OnClick({R.id.login})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.login:
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                // 如果账号是admin且密码是123456，就认为登录成功
                if (account.equals("admin") && password.equals("123456")) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) { // 检查复选框是否被选中
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(DataSaveActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DataSaveActivity.this, "account or password is invalid",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    void initData() {

    }

    @Override
    void initView() {

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_data_save;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = editText3.getText().toString();
        save(inputText);
    }


    /**
     * @param inputText 需要保存的数据
     *                  openFileOutput是context保存数据的一个方法，默认将数据保存在/data/data/<packages>/files/目录下
     *                  第一个参数是文件名
     *                  第二个参数是Context.MODE_PRIVATE(覆盖写)Context.MODE_APPEND(追加内容)
     */
    public void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return 读取/data/data/<packages>/files/data文件下的数据
     */
    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
