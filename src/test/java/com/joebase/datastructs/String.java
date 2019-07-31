package com.joebase.datastructs;

public interface String {

    public void clear();
    public boolean isEmpty();
    public int length();
    public char charAt(int index);
    public String subString(int begin, int end);
    public String insert(int offset, String str);
    public String delete(int begin, int end);
    public String concat(String str);
    public int compareTo(String str);
    public int indexOf(String str, int start);

}