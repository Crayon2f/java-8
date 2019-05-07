package com.crayon2f.java8.collector.impl;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Created by feifan.gou@gmail.com on 2019/5/6 16:33.
 */
interface CollectorImpl<T, A, R> extends Collector<T, A, R> {

    Set<Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

}
