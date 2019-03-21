package com.gwm.android;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 带有iterator()方法的SparseArray 可以使用foreach遍历和Iterator迭代器遍历
 */

public class SparseArray<E> extends android.util.SparseArray<E> implements Iterable<E> {
    private int index = 0;

    /**
     * 为了防止出现同一个key值下有多个value使用了该方法保存对象后请不要在使用含有key的方法，
     * @param obj 保存对象
     */
    public synchronized void put(E obj){
        put(index,obj);
        index++;
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return new SparseArrayIterator<E>(this);
    }

    private static class SparseArrayIterator<E> implements ListIterator<E> {
        private SparseArray<E> mArray;
        private int mCursor = -1;
        private boolean mCursorNowhere = true;
        public SparseArrayIterator(SparseArray<E> array) {
            mArray = array;
        }
        /**
         * 获取当前指针所指向的key值
         * @return
         */
        public int currentKey() {
            if (!mCursorNowhere){
                return mArray.keyAt(mCursor);
            } else {
                throw new IllegalStateException();
            }
        }

        @Override
        public void add(E object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return mCursor < mArray.size() - 1;
        }

        @Override
        public boolean hasPrevious() {
            return mCursorNowhere && mCursor >= 0 || mCursor > 0;
        }

        @Override
        public int nextIndex() {
            if (hasNext()) {
                return mCursor + 1;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public int previousIndex() {
            if (hasPrevious()) {
                if (mCursorNowhere) {
                    return mCursor;
                } else {
                    return mCursor - 1;
                }
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public E next() {
            if (hasNext()) {
                if (mCursorNowhere) {
                    mCursorNowhere = false;
                }
                mCursor++;
                return mArray.get(currentKey());
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public E previous() {
            if (hasPrevious()) {
                if (mCursorNowhere) {
                    mCursorNowhere = false;
                } else {
                    mCursor--;
                }
                return mArray.get(currentKey());
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if (!mCursorNowhere) {
                mArray.remove(currentKey());
                mCursorNowhere = true;
                mCursor--;
            } else {
                throw new IllegalStateException();
            }
        }

        @Override
        public void set(E object) {
            if (!mCursorNowhere) {
                mArray.put(currentKey(), object);
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
