package com.opencms.util.common;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-17
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */
public class FileFilterImpl implements FileFilter {

    private boolean file = true;

    private boolean dictionary = false;

    private String startWith;

    private String endWith;

    public FileFilterImpl() {
    }

    public FileFilterImpl(boolean file, boolean dictionary, String startWith, String endWith) {
        this.file = file;
        this.dictionary = dictionary;
        this.startWith = startWith;
        this.endWith = endWith;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }

    public boolean isDictionary() {
        return dictionary;
    }

    public void setDictionary(boolean dictionary) {
        this.dictionary = dictionary;
    }

    public String getStartWith() {
        return startWith;
    }

    public void setStartWith(String startWith) {
        this.startWith = startWith;
    }

    public String getEndWith() {
        return endWith;
    }

    public void setEndWith(String endWith) {
        this.endWith = endWith;
    }

    @Override
    public boolean accept(File file) {
        String tmp = file.getName().toLowerCase();
        if(isFile()){
            if(file.isDirectory()){
                return false;
            }
        } else if(isDictionary()){
            if(file.isFile()){
                return false;
            }
        }
        if(startWith != null){
            if(!tmp.startsWith(startWith)){
                return false;
            }
        }
        if(endWith != null){
            if(!tmp.endsWith(endWith)){
                return false;
            }
        }
        return true;
    }
}
