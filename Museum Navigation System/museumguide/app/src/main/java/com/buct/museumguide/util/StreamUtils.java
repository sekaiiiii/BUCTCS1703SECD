package com.buct.museumguide.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public class StreamUtils {
    private boolean isWrite;

    //序列化
    public static <T> boolean writeObject(List<T> list , File file)
    {
        T[] array = (T[]) list.toArray();
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(array);
            out.flush();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <E> List<E> readObjectForList(File file)
    {
        E[] object;
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file))){
            object = (E[]) out.readObject();
            return Arrays.asList(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isWrite() {
        return isWrite;
    }

    public void setWrite(boolean write) {
        isWrite = write;
    }
}
