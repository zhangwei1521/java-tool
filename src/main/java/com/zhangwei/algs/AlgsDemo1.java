package com.zhangwei.algs;

import edu.princeton.cs.algs4.Queue;

public class AlgsDemo1 {
    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        for (int i=0;i<10;i++){
            queue.enqueue(i+1);
        }
        int size = queue.size();
        for (int i=0;i<size;i++){
            System.out.println(queue.dequeue());
        }
    }
}
