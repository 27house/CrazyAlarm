package com.sunn.xhui.crazyalarm.db;

import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * object序列化工具
 * Filename:SerializedUtils
 * Author:XHui.sun
 * Date:2016/10/17 14:06
 * Version:2.0.1.6
 **/
class SerializedUtils {
    /**
     * 将一个可序列化对象序列化为BLOB
     *
     * @param obj 实现了Serializable接口的对象
     * @return BLOB对象，即二进制流，byte数组
     */
    static byte[] getSerializedObject(Serializable obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
            return null;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return baos.toByteArray();
    }

    /**
     * 从BLOB中读取被序列化的对象
     *
     * @param in BLOB对象，即二进制流，byte数组
     * @return 被序列化的对象
     */
    static Object readSerializedObject(byte[] in) {
        Object result = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(in);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            result = ois.readObject();
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            result = null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Throwable e) {
                LogUtil.e(e.getMessage());
            }
        }
        return result;
    }
}
