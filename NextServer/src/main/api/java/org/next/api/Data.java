package org.next.api;

public abstract class Data <T>{

    private byte[] content;

    public Data(byte[] content){
        this.content = content;
    }
    int getInt(){
        return 0;
    }
    String getString(){
        return "";
    }
    long getLong(){
        return 0;
    }
    boolean getBoolean(){
        return false;
    }
    short getShort(){
        return 0;
    }
    Price getPrice(){
        return null;
    }

    byte getByte(){
        return 0;
    }
    public abstract T parse();
}
