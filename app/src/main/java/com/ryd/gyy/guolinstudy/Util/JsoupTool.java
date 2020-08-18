package com.ryd.gyy.guolinstudy.Util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ryd.gyy.guolinstudy.Model.JianzhiResult;
import com.ryd.gyy.guolinstudy.Thread.ThreadStudy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsoupTool {

    private static final String TAG = "JsoupTool";

    //pn是第几页，rn是一页显示多少条数据：https://zhaopin.baidu.com/jianzhi?city=温州&pn=5&rn=3
//    private String POST_URL = "https://zhaopin.baidu.com/jianzhi?city=温州&pn=2";

    private static final int GET_DATA_SUCC = 2011;
    private static final int GET_DATA_FAIL = 2012;

    private JsoupResultListener mListener;

    private ExecutorService mThreadPool;

    private static JsoupTool sInstance;

    //非静态 Handler 导致 Activity 泄漏
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCC:
                    if (mListener != null) {
                        mListener.onSuccess((ArrayList<JianzhiResult>) msg.obj);
                    }
                    break;
                case GET_DATA_FAIL:
                    if (mListener != null) {
                        mListener.onFail(null);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    //上面的Handler也会造成Activity内存泄漏，一般需要将其置为static，然后内部持有一个Activity的弱引用来避免内存泄漏。如下所示：
    private static class MyHandler extends Handler {

        private final WeakReference<ThreadStudy> mActivity;

        public MyHandler(ThreadStudy activity) {
            mActivity = new WeakReference<ThreadStudy>(activity);
        }

        @SuppressWarnings("unchecked")
        @Override

        public void handleMessage(Message msg) {
            ThreadStudy activity = mActivity.get();
        }
    }

    /**
     * @return 返回实例
     */
    public static JsoupTool getInstance() {
        if (sInstance == null) {
            sInstance = new JsoupTool();
        }
        return sInstance;
    }


    /**
     * 构造方法
     */
    @SuppressLint("HandlerLeak")
    public JsoupTool() {
        // 创建单线程池
        mThreadPool = Executors.newSingleThreadExecutor();
    }

    /**
     * 真正执行网页解析的方法
     * 线程池中开启新的线程执行解析，解析完成之后发送消息
     * 将结果传递到主线程中
     */
    public void runJsoup(final String url, final JsoupResultListener jsoupResultListener) {
        mListener = jsoupResultListener;
        // 创建单线程池
        mThreadPool = Executors.newSingleThreadExecutor();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<JianzhiResult> result = getJzResults(url);
                if (result == null) {
                    mHandler.sendEmptyMessage(GET_DATA_FAIL);
                    return;
                }
                mHandler.obtainMessage(GET_DATA_SUCC, result).sendToTarget();
            }
        });
    }

    private ArrayList<JianzhiResult> getJzResults(final String url) {
//        Document doc = Jsoup.connect("http://www.oschina.net/")
//                .data("query", "Java")   // 请求参数
//                .userAgent("I ’ m jsoup") // 设置 User-Agent
//                .cookie("auth", "token") // 设置 cookie
//                .timeout(3000)           // 设置连接超时时间
//                .post();                 // 使用 POST 方法访问 URL

//                String positionTitle = element.text(); // 岗位名称
//                String title = element.select("div").text(); // 新闻标题
//                String url = element.select("a").attr("href"); // 新闻内容链接

        // 开启一个新线程

        // 从 URL 直接加载 HTML 文档
        Document doc = null;
        try {
            if (url == null) {
                return null;
            }
            doc = Jsoup.connect(url).timeout(60 * 1000).get();
            ArrayList<JianzhiResult> JzResults = new ArrayList<JianzhiResult>();
            Elements elements1 = doc.select("div.single");
            Log.e(TAG, "爬到多少条数据elements1.size(): " + elements1.size());
            for (Element element : elements1) {
                JianzhiResult jianzhiResult = new JianzhiResult();
                String loadurl = element.select("div.inlineblock.percent47").attr("url"); //详情页网址
                Log.e(TAG, "loadurl: " + loadurl.toString());
                String name = element.select("div.title").text(); // 岗位名称
                String salary = element.select("span.inlineblock.num").text(); // 薪资
                String location = element.select("div.detail").text(); // 位置
                String company = element.select("div.company").text(); // 公司
                JzResults.add(jianzhiResult);
                Log.e(TAG, "name: " + name + "\n" + "salary: " + salary + "\n" + "location: " + location + "\n" + "company: " + company + "\n");
            }
            return JzResults;
        } catch (IOException e) {
            Log.e(TAG, "报错啦------: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 回调接口 获取数据之后，通过该接口设置数据传递
     */
    public interface JsoupResultListener {
        public void onSuccess(ArrayList<JianzhiResult> results);

        public void onFail(ArrayList<JianzhiResult> results);
    }

}
