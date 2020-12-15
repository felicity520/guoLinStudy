package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ryd.gyy.guolinstudy.Model.Book;
import com.ryd.gyy.guolinstudy.Model.Person;
import com.ryd.gyy.guolinstudy.R;

public class ProcessActivity2 extends AppCompatActivity {

    private static final String TAG = "ProcessActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process2);

        Log.e(TAG, "ProcessActivity2 onCreate TEST_ID: " + MyActivity.TEST_ID);

        receiveBasicData();
        receiveParcelableData();
        receiveSeriableData();
    }

    private void receiveBasicData() {
        Bundle bundle = this.getIntent().getExtras();

        assert bundle != null;
        String name = bundle.getString("name");
        int height = bundle.getInt("height");
        if (name != null && height != 0)
            Log.d(TAG, "receice basic data -- " +
                    "name=" + name + ", height=" + height);
    }

    private void receiveParcelableData() {
        Book mBook = (Book) getIntent().getParcelableExtra("ParcelableValue");
        if (mBook != null)
            Log.d(TAG, "receice parcel data -- " +
                    "Book name is: " + mBook.getBookName() + ", " +
                    "Author is: " + mBook.getAuthor() + ", " +
                    "PublishTime is: " + mBook.getPublishTime());
    }

    private void receiveSeriableData() {
        Person mPerson = (Person) getIntent().getSerializableExtra("SeriableValue");
        if (mPerson != null)
            Log.d(TAG, "receice serial data -- " +
                    "The name is:" + mPerson.getName() + ", " +
                    "age is:" + mPerson.getAge());
    }


}