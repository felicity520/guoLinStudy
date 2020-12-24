package com.ryd.gyy.guolinstudy.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String bookName;
    private String author;
    private int publishTime;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(int publishTime) {
        this.publishTime = publishTime;
    }

    public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
        /**
         *从序列化后的对象中创建原始对象
         * @param source
         * @return
         */
        @Override
        public Book createFromParcel(Parcel source) {
            Book mBook = new Book();
            mBook.bookName = source.readString();
            mBook.author = source.readString();
            mBook.publishTime = source.readInt();
            return mBook;
        }

        /**
         * 创建指定长度的原始对象数组
         * @param size
         * @return
         */
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    /**
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将当前对象写入序列化结构中
     *
     * @param parcel
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(bookName);
        parcel.writeString(author);
        parcel.writeInt(publishTime);
    }
}
