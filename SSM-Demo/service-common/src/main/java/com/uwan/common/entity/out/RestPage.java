package com.uwan.common.entity.out;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class RestPage<T> implements Page<T> {
    private PageImpl<T> pageDelegate = new PageImpl<>(new ArrayList<>(0));

    @Override
    public List<T> getContent() {
        return pageDelegate.getContent();
    }

    @Override
    public int getNumber() {
        return pageDelegate.getNumber();
    }

    @Override
    public int getNumberOfElements() {
        return pageDelegate.getNumberOfElements();
    }

    @Override
    public int getSize() {
        return pageDelegate.getSize();
    }

    @Override
    public Sort getSort() {
        return pageDelegate.getSort();
    }

    @Override
    public long getTotalElements() {
        return pageDelegate.getTotalElements();
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return pageDelegate.map(converter);
    }

    @Override
    public int getTotalPages() {
        return pageDelegate.getTotalPages();
    }

    @Override
    public boolean hasContent() {
        return pageDelegate.hasContent();
    }

    @Override
    public boolean hasNext() {
        return pageDelegate.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return pageDelegate.hasPrevious();
    }

    @Override
    public boolean isFirst() {
        return pageDelegate.isFirst();
    }

    @Override
    public boolean isLast() {
        return pageDelegate.isLast();
    }

    @Override
    public Iterator<T> iterator() {
        return pageDelegate.iterator();
    }

//    @Override
//    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
//        return pageDelegate.map(converter);
//    }

    @Override
    public Pageable nextPageable() {
        return pageDelegate.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return pageDelegate.previousPageable();
    }

    public void setContent(List<T> content) {
        pageDelegate = new PageImpl<>(content, null, getTotalElements());
    }


    public void setTotalElements(int totalElements) {
        pageDelegate = new PageImpl<>(getContent(), null, totalElements);
    }

    @Override
    public String toString() {
        return pageDelegate.toString();
    }

}