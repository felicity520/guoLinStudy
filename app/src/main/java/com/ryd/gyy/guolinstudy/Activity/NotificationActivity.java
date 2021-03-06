package com.ryd.gyy.guolinstudy.Activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.ryd.gyy.guolinstudy.Fragment.Fragment2;
import com.ryd.gyy.guolinstudy.Fragment.Fragment4;
import com.ryd.gyy.guolinstudy.IMyInterface;
import com.ryd.gyy.guolinstudy.Model.Book;
import com.ryd.gyy.guolinstudy.Model.MessageEvent;
import com.ryd.gyy.guolinstudy.Model.Person;
import com.ryd.gyy.guolinstudy.Model.WsListener;
import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Service.MyService;
import com.ryd.gyy.guolinstudy.testjava.ISay;
import com.ryd.gyy.guolinstudy.testjava.SayException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import dalvik.system.DexClassLoader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * 用于通知下拉的Activity
 * 学习Fragment，必须继承FragmentActivity
 */
public class NotificationActivity extends FragmentActivity {

    private static final String TAG = "NotificationActivity_A";

    private IMyInterface myInterface;

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;

    Fragment2 fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification2);
//        setContentView(R.layout.activity_test);//学习ConstraintLayout的布局

        setContentView(R.layout.activity_fragment_test);

        testMouse();
        addFragByDynamic();
        initView();
        testProcess();

        Log.i(TAG, "onCreate currentThread: " + Thread.currentThread().getId());


        studyClassLoader();
        studyEventbus();
        initWebSocket();
        startActivity();
    }

    private void startActivity() {
        Button btn_test_activity = (Button) findViewById(R.id.btn_test_activity);
        btn_test_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationActivity.this, AddPeopleActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, TAG + " onStart: ---------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, TAG + " onResume: ---------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, TAG + " onPause: ---------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, TAG + " onStop: ---------");
    }


    String mWbSocketUrl;
    OkHttpClient mClient;
    WebSocket mWebSocket;

    /**
     * 服务端在另外一个工程里
     * websocket学习连接：
     * https://mp.weixin.qq.com/s/c1qgZbFuf98VI-2cgrhXtQ
     */
    private void initWebSocket() {
        mWbSocketUrl = "ws://localhost:47823/";
        mClient = new OkHttpClient.Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(mWbSocketUrl)
                .build();
        mWebSocket = mClient.newWebSocket(request, new WsListener());

        //获取连接url，初始化websocket客户端
        send("gyy");
    }


    //发送String消息
    public void send(final String message) {
        if (mWebSocket != null) {
            mWebSocket.send(message);
        }
    }

    /**
     * 发送byte消息
     *
     * @param message
     */
    public void send(final ByteString message) {
        if (mWebSocket != null) {
            mWebSocket.send(message);
        }
    }

    //主动断开连接
    public void disconnect(int code, String reason) {
        if (mWebSocket != null)
            mWebSocket.close(code, reason);
    }

    private void studyEventbus() {
        //在需要订阅事件的地方注册事件
        EventBus.getDefault().register(this);
        Button btn_event_bus = (Button) findViewById(R.id.btn_event_bus);
        btn_event_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布订阅事件
                EventBus.getDefault().post(new MessageEvent("我是发送的消息"));

//                Intent intent = new Intent(NotificationActivity.this, AnimationActivity.class);
//                startActivity(intent);
////                验证粘性事件在发送事件之后再注册是否能接收到
//                EventBus.getDefault().postSticky(new MessageEvent("我是粘性事件"));
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 5)
    public void getEventMessage1(MessageEvent messageEvent) {
        Toast.makeText(this, messageEvent.getMessage().toString() + "GYY", Toast.LENGTH_SHORT).show();
    }

    /**
     * 普通事件
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventMessage(MessageEvent messageEvent) {
        Toast.makeText(this, messageEvent.getMessage().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消事件订阅
        EventBus.getDefault().unregister(this);
    }

    ISay say;

    private void studyClassLoader() {
        //当一个 App 被安装到手机后，apk 里面的 class.dex 中的 class 均是通过 PathClassLoader 来加载的，可以通过如下代码验证：
        //onCreate currentThread: dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.ryd.gyy.guolinstudy-d1NMxc1vkvKfGtyp_LzZ5w==/base.apk"],nativeLibraryDirectories=[/data/app/com.ryd.gyy.guolinstudy-d1NMxc1vkvKfGtyp_LzZ5w==/lib/arm64, /data/app/com.ryd.gyy.guolinstudy-d1NMxc1vkvKfGtyp_LzZ5w==/base.apk!/lib/arm64-v8a, /system/lib64, /system/product/lib64]]]
        ClassLoader classLoader = NotificationActivity.class.getClassLoader();
        Log.i(TAG, "onCreate currentThread: " + classLoader.toString());

        Button btnSay = findViewById(R.id.btn_say);
        btnSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取hotfix的jar包文件
                final File jarFile = new File(Environment.getExternalStorageDirectory().getPath()
                        + File.separator + "say_something_hotfix.jar");

                if (!jarFile.exists()) {
                    //如果补丁包不存在，就加载之前的异常
                    say = new SayException();
                    Toast.makeText(NotificationActivity.this, say.saySomething(), Toast.LENGTH_SHORT).show();
                } else {
                    //加载补丁生成的jar包，jar就是修复后的代码
                    // 只要有读写权限的路径均可
                    DexClassLoader dexClassLoader = new DexClassLoader(jarFile.getAbsolutePath(),
                            getExternalCacheDir().getAbsolutePath(), null, getClassLoader());
                    try {
                        // 加载 SayHotFix 类
                        Class clazz = dexClassLoader.loadClass("material.danny_jiang.com.dexclassloaderhotfix.SayHotFix");
                        // 强转成 ISay, 注意 ISay 的包名需要和hotfix jar包中的一致
                        ISay iSay = (ISay) clazz.newInstance();
                        Toast.makeText(NotificationActivity.this, iSay.saySomething(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    static class Bean implements Serializable {
        //transient
        private byte[] data = new byte[2 * 1024 * 1024];
        String str = "data string";
    }

    private void testMouse() {
        //测试结果就是：当鼠标的光标停留在Button所在的View时，会触发ACTION_HOVER_ENTER
        //鼠标在View上滑动时触发ACTION_HOVER_MOVE
        //鼠标离开View触发ACTION_HOVER_EXIT
        Button btn_shubiao = (Button) findViewById(R.id.btn_shubiao);
        btn_shubiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, AnimationActivity.class);
//                intent.putExtra("data", new Bean());//  2097880
                intent.putExtra("data", new Gson().toJson(new Bean()));
                NotificationActivity.this.startActivity(intent);
            }
        });

        btn_shubiao.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                int what = event.getAction();
                switch (what) {
                    case MotionEvent.ACTION_HOVER_ENTER:  //鼠标进入view
                        System.out.println("bottom ACTION_HOVER_ENTER");
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE:  //鼠标在view上
                        System.out.println("bottom ACTION_HOVER_MOVE");
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:  //鼠标离开view
                        System.out.println("bottom ACTION_HOVER_EXIT");
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        System.out.println("bottom ACTION_BUTTON_PRESS");
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 测试多进程的代码
     */
    private void testProcess() {
        Button btn_start_process = findViewById(R.id.btn_start_process);
        btn_start_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NotificationActivity.this, ProcessActivity.class);
//                startActivity(intent);

                new Thread() {
                    public void run() {
                        //在子线程启动后的service线程还是在主线程
                        //就跟在子线程里面启动activity一样，新启动的activity肯定是在主线程，
                        // 跟启动流程有关，可以去研究下，好像是handler切换了下
                        Log.i(TAG, "btn_start_process currentThread: " + Thread.currentThread().getId());
                        Intent intent = new Intent(NotificationActivity.this, MaterialActivity.class);
                        startActivity(intent);
                    }
                }.start();
            }
        });
        Button btn_start_process2 = findViewById(R.id.btn_start_process2);
        btn_start_process2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "skywang");
                bundle.putInt("height", 175);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //学习序列化：发送基本数据类型
        Button mBtnBasic = (Button) findViewById(R.id.btnBasic);
        mBtnBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBasicDataThroughBundle();
            }
        });
        //发送Parcelable数据
        Button mBtnPar = (Button) findViewById(R.id.btnPar);
        mBtnPar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendParcelableDataThroughBundle();
            }
        });
        //发送serialize数据
        Button mBtnSer = (Button) findViewById(R.id.btnSer);
        mBtnSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSeriableDataThroughBundle();
            }
        });
        //启动services进程
        Button btn_start_services_process = (Button) findViewById(R.id.btn_start_services_process);
        btn_start_services_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAndBindService();
            }
        });
    }

    public final static int ACTIVITYID = 0X0002;
    //客户端的Messnger
    private Messenger aMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == ACTIVITYID) {
                //客户端接受服务端传来的消息
                Log.d(TAG, "服务端传来了消息=====>>>>>>>");
                String str = (String) msg.getData().get("content");
                Log.d(TAG, str);
            }
        }
    });


    //服务端传来的Messenger
    Messenger sMessenger;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //这部分是学习AIDL的代码------
            //利用new IMyInterface.Stub()向上转型成了IBinder
            //传回来的IBinder(service)就是我们在Service的onBind( )方法所return的IBinder
            //然后我们调用Stub中的静态方法asInterface并把返回来的IBinder当参数传进去。
//            myInterface = IMyInterface.Stub.asInterface(service);
//            Log.i(TAG, "gyy111 连接Service 成功");
//            try {
//                String s = myInterface.getInfor("我是Activity传来的字符串");
//                Log.i(TAG, "gyy111 从Service得到的字符串：" + s);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
            //这部分是学习AIDL的代码------

            //这部分是学习Messenger的代码------
            sMessenger = new Messenger(service);

            Message message = Message.obtain();
//            演示的代码1---
//            Message.obtain(new Handler(), new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//            演示的代码2---
//            Message.obtain(new Handler(new Handler.Callback() {
//                @Override
//                public boolean handleMessage(Message msg) {
//                    //这里返回true可以拦截后面默认的实现
//                    return false;
//                }
//            }), new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });

            message.arg1 = 0x0001;
            //注意这里，把`Activity`的`Messenger`赋值给了`message`中，当然可能你已经发现这个就是`Service`中我们调用的`msg.replyTo`了。
            message.replyTo = aMessenger;

            Bundle bundle = new Bundle();
            bundle.putString("content", "我就是Activity传过来的字符串");
            message.setData(bundle);

            try {
                //消息从客户端发出
                //这个send方法等同于getInfor方法
                sMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //这部分是学习Messenger的代码------
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "连接Service失败");
        }
    };

    /**
     * 启动Service
     */
    private void startAndBindService() {
        Intent service = new Intent(NotificationActivity.this, MyService.class);
        startService(service);
        bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    // sent object through Pacelable
    private void sendParcelableDataThroughBundle() {
        Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);

        Book mBook = new Book();
        mBook.setBookName("Android");
        mBook.setAuthor("skywang");
        mBook.setPublishTime(2013);

        Bundle mBundle = new Bundle();
        mBundle.putParcelable("ParcelableValue", mBook);
        intent.putExtras(mBundle);

        startActivity(intent);
    }

    // sent object through seriable
    private void sendSeriableDataThroughBundle() {
        Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);

        Person mPerson = new Person();
        mPerson.setName("skywang");
        mPerson.setAge(24);

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("SeriableValue", mPerson);
        intent.putExtras(mBundle);

        startActivity(intent);
    }


    private void sendBasicDataThroughBundle() {
        Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);

        Bundle bundle = new Bundle();
        bundle.putString("name", "skywang");
        bundle.putInt("height", 175);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    private void initView() {
        Button btn_switch = findViewById(R.id.btn_switch);
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment4 fragment4 = new Fragment4();
                fragmentTransaction.replace(R.id.fragment_container, fragment4).commit();
            }
        });
    }

    /**
     * 动态添加Fragment
     */
    private void addFragByDynamic() {
        // 步骤1：获取FragmentManager，androidx使用getSupportFragmentManager
        fragmentManager = getSupportFragmentManager();

        // 步骤2：获取FragmentTransaction
        fragmentTransaction = fragmentManager.beginTransaction();

        // 步骤3：创建需要添加的Fragment ：Fragment2
        fragment = new Fragment2();

        // 步骤4：动态添加fragment
        // 即将创建的fragment添加到Activity布局文件中定义的占位符中（FrameLayout）
        fragmentTransaction.add(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();
    }
}
