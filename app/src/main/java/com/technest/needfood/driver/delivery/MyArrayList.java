package com.technest.needfood.driver.delivery;

import java.util.ArrayList;

public class MyArrayList extends ArrayList<String> {

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int indexOf(Object o) {
        int size = this.size();
        if (o == null) {
            for (int i = 0; i < size ; i++) {
                if (this.get(i) == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size ; i++) {
                if (this.get(i).contains(String.valueOf(o))) {
                    return i;
                }
            }
        }
        return -1;
    }
}
