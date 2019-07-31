package com.joebase.datastructs;

public class ArrayString implements String {
    
    private char[] value;
    private int length;
    
    public char[] getValue() {
        return value;
    }
    
    public int getLength() {
        return length;
    }

    public ArrayString() {
        value = new char[0];
        length = 0;
    }

    public ArrayString(java.lang.String str) {
        char[] temp = str.toCharArray();
        value = temp;
        length = temp.length;
    }
    
    public ArrayString(char[] charArray) {
        value = new char[charArray.length];
        for(int i=0; i<charArray.length; i++) {
            value[i] = charArray[i];
        }
        length = charArray.length;
    }

    @Override
    public void clear() {
        value = null;
        length = 0;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        if(index<0 || index>=length) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    @Override
    public String subString(int begin, int end) {
        if(begin < 0) {
            throw new StringIndexOutOfBoundsException("起始位置不能小于0");
        }
        if(end > length) {
            throw new StringIndexOutOfBoundsException("结束位置不能大于串的当前长度:" + length);
        }
        if(begin > end) {
            throw new StringIndexOutOfBoundsException("开始位置不能大于结束位置");
        }
        if(begin == 0 && end == length) {
            return this;
        } else {
            char[] buffer = new char[end - begin];
            for(int i=0; i<buffer.length; i++) {
                buffer[i] = this.value[i+begin];
            }
            return new ArrayString(buffer);
        }
    }
    
    public String subString(int begin) {
        return subString(begin, value.length);
    }

    @Override
    public String insert(int offset, String str) {
        if ((offset < 0) || (offset > this.length)) {
            throw new StringIndexOutOfBoundsException("插入位置不合法");
        }
        int len = str.length();
        int newLength = this.length + len;
        if(newLength > value.length) {
            allocate(newLength);
        }
        for(int i=this.length-1; i>=offset; i--) {
            value[len+i] = value[i];
        }
        for(int i=0; i<len; i++) {
            value[offset+i] = str.charAt(i);
        }
        this.length = newLength;
        return this;
    }

    public void allocate(int newCapacity) {
        char[] temp = value;
        value = new char[newCapacity];
        for(int i=0; i<temp.length; i++) {
            value[i] = temp[i];
        }
    }

    @Override
    public String delete(int begin, int end) {
        if(begin < 0) {
            throw new StringIndexOutOfBoundsException("起始位置不能小于0");
        }
        if(end > length) {
            throw new StringIndexOutOfBoundsException("结束位置不能大于串的当前长度:" + length);
        }
        if(begin > end) {
            throw new StringIndexOutOfBoundsException("开始位置不能大于结束位置");
        }
        for(int i=0; i<length-end; i++) {
            value[begin + i] = value[end + i];
        }
        length = length - (end - begin);
        return this;
    }

    @Override
    public String concat(String str) {
        return insert(length, str);
    }

    @Override
    public int compareTo(String str) {
        return compareTo((ArrayString)str);
    }

    public int compareTo(ArrayString str) {
        int len1 = this.length;
        int len2 = str.length;
        int n = Math.min(len1, len2);
        for(int k=0; k<n; k++) {
            if(value[k]!=str.value[k])
                return(value[k]-str.value[k]);
        }
        return len1 - len2;
    }

    @Override
    public int indexOf(String str, int start) {
        return index_KMP(str, start, true);
    }

    // 模式匹配的Brute-Force 算法
    //返回模式串t在主串中从start开始的第一次匹配位置，匹配失败时返回－1。
    public int index_BF(ArrayString t, int start) {
        if(this != null && t != null && t.length() > 0 && this.length() >= t.length()) { // 当主串比模式串长时进行比较
            int slen, tlen, i = start, j = 0; // i表示主串中某个子串的序号
            slen = this.length();
            tlen = t.length();
            while((i < slen) && (j < tlen)) {
                if(this.charAt(i) == t.charAt(j)) { // j为模式串当前字符的下标
                    i++;
                    j++;
                } else { // 继续比较后续字符
                    i = i - j + 1; // 继续比较主串中的下一个子串
                    j = 0; // 模式串下标退回到0
                }
            }
            if(j >= t.length()) { // 一次匹配结束，匹配成功
                return i - tlen; // 返回子串序号
            } else {
                return -1;
            }
        }
        return -1; // 匹配失败时返回-1
    }
    
    //KMP模式匹配算法
    public int index_KMP(String t, int start, boolean getnext) {
        //在当前主串中从start开始查找模式串T
        //若找到，则返回模式串T在主串中的首次匹配位置，否则返回-1
        int[] next = getnext ? getNext(t) : getNextVal(t);     //计算模式串的next[]函数值
        int i = start;               //主串指针
        int j = 0;                   //模式串指针
        //对两串从左到右逐个比较字符
        while(i < this.length() && j < t.length()) {
            //若对应字符匹配
            if(j == -1 || this.charAt(i) == t.charAt(j)) { // j==-1表示S[i]!=T[0]
                i++;
                j++;         //则转到下一对字符
            } else  { //当S[i]不等于T[j]时
                j = next[j];        //模式串右移
            }
        }
        if(j < t.length()) {
            return -1;                  //匹配失败
        } else {
            return (i - t.length());    //匹配成功
        }
    }

    //计算模式串T的next[]函数值
    private int[] getNext(String t) {
        int[] next = new int[t.length()];  //next[]数组
        int j = 1;    //主串指针
        int k = 0;   //模式串指针
        next[0] = -1;
        if(t.length()>1) next[1] = 0;
        while(j < t.length() - 1) {
            if(t.charAt(j) == t.charAt(k)) {  //匹配
                next[j+1] = k + 1;
                j++;
                k++;
            } else if(k == 0) {  //失配
                next[j+1] = 0;
                j++;
            } else {
                k = next[k];
            }
        }
        return (next);
    }

    //计算模式串T的nextval[]函数值
    private int[] getNextVal(String t) {
        int[] nextval = new int[t.length()];  //nextval[]数组
        int j = 0;
        int k = -1;
        nextval[0] = -1;
        while(j < t.length() - 1) {
            if(k == -1 || t.charAt(j) == t.charAt(k)) {
                j++;
                k++;
                if (t.charAt(j) != t.charAt(k)) {
                    nextval[j] = k;
                } else {
                    nextval[j] = nextval[k];
                }
            } else {
                k = nextval[k];
            }
        }
        return (nextval);
    }
    
}