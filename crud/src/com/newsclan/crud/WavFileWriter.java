package com.newsclan.crud;

import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * 写音频工具类
 * 
 * @author zhangyun
 * @date 2014-5-6
 * 
 *  2020-5-14 （迁到服务器端）
 */
public class WavFileWriter {
     public static int HEAD_LEN = 44;
    public static int SAMPLE_RATE_16K = 16000;
    public static int HEAD_LYB = 4;

    private static short mBitsPerSample = 16;
    /** Indicates PCM format. */
    private static final short FORMAT_PCM = 1;
    private short mNumChannels = 1;
    private int mSampleRate = SAMPLE_RATE_16K;
    private String mFileName = "";
    private RandomAccessFile mFile = null;
    private int mFileBytes = 0;
    private int mTime = 0;

    /** 保存 原始数据 */
    public int writeData(byte[] data, int offset,int len) {
        if (null == mFile || data == null) {
            System.out.println("writeOriginalData file is null");
            return 0;
        }
        try {

            mFile.write(data, offset, len);
            mFileBytes += len;
            // 时间使用如果 8000采样， 1ms大小为16字节
            // 如果16000采样 1ms大小为32字节
            mTime += (len / (mSampleRate * 2 / 1000) / mNumChannels);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return mFileBytes;
    }

    /** 保存 原始数据 */
    public int writeData(byte[] data) {
        return writeData(data,0, data.length);
    }

    /**
     * 打开文件
     */
    public boolean open(String filePath, int sampleRate, short channle) {
        mFileBytes = 0;
        mTime = 0;
        mSampleRate = sampleRate;
        mFileName = filePath;
        mNumChannels = channle;
        boolean ret = true;
        try {
            mFile = new RandomAccessFile(mFileName, "rw");
            byte[] bufHead = new byte[HEAD_LEN];
            mFile.setLength(0);
            mFile.write(bufHead, 0, HEAD_LEN);
        } catch (Exception e) {

            mFile = null;
            ret = false;
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /** 关闭文件 */
    public void close() {
        if (null != mFile) {
            try {
                mFile.seek(0);
                writeHeader(mFile, mFileBytes);
                mFile.close();
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            } finally {
                mFile = null;
            }
        }
    }

    /** 文件大小 */
    public int getLength() {
        int len = 0;
        if (null == mFile) {
            return len;
        }
        try {
            len = (int) mFile.length();
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
        return len;
    }

    /** 文件名 */
    public String getFileName() {
        return mFileName;
    }

    /** 录音长度 */
    public int getTime() {
        return mTime;
    }

    private void writeHeader(RandomAccessFile out, int len) throws IOException {
        /* RIFF header */
        writeId(out, "RIFF");
        writeInt(out, 36 + len);
        writeId(out, "WAVE");

        /* FMT chunk */
        writeId(out, "fmt "); // 16
        writeInt(out, 16); // 20
        writeShort(out, FORMAT_PCM); // 22
        writeShort(out, mNumChannels); // 24
        writeInt(out, mSampleRate); // 28

        writeInt(out, mNumChannels * mSampleRate * mBitsPerSample / 8); // 32
        writeShort(out, (short) (mNumChannels * mBitsPerSample / 8)); // 34
        writeShort(out, mBitsPerSample); // 36

        writeId(out, "data");
        writeInt(out, len);
    }

    private void writeId(RandomAccessFile out, String id) throws IOException {
        for (int i = 0; i < id.length(); i++)
            out.write(id.charAt(i));
    }

    private void writeInt(RandomAccessFile out, int val) throws IOException {
        out.write(val >> 0);
        out.write(val >> 8);
        out.write(val >> 16);
        out.write(val >> 24);
    }

    private void writeShort(RandomAccessFile out, short val) throws IOException {
        out.write(val >> 0);
        out.write(val >> 8);
    }

}
