package com.crayon2f.java8.collector.impl;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Created by feifan.gou@gmail.com on 2019/5/6 16:32.
 * <p>
 * 原生Collectors中的toMap,其实已经很好用了,但是其中有两个我觉得不方便的地方：
 * -- 1: 当我不传mergeFunction是的时候, 默认是抛出异常 throwingMerger(), "Duplicate key xx" , 因为数据的未执性,许多情况下我们是希望覆盖或者随便取一个的,
 * 每次都得传入mergeFunction: (pre,next) -> pre, 以保证不报错。预期的结果是，默认是如果key重复了，别报错，而是后者覆盖前者
 * <p>
 * -- 2: 在 1满足的情况下, 对重复的key进行 merge的时候,调用的是 map 1.8 以后的
 * merge(K key, V value,BiFunction<? super V, ? super V, ? extends V>) 方法
 * 其中明确指定了, value 不能为空，但实际情况我们很难保证数据不能为null，除非在stream时候把 null的数据filter，但是有些时候我们是需要这些数据的，
 * 所以我们可以自己指定一个规则: 如果两个值存在null,那么取不为null那个;如果两个都不为null,那么后者覆盖前者, 但是不能不能报错
 */
public class ToMap<T, K, V> implements CollectorImpl<T, Map<K, V>, Map<K, V>> {

    private final Supplier<Map<K, V>> supplier; //供应商
    //累加器,对应map其实应该是put,但是会存在 key重复的情况,所以提供了mergeFunction,来合并重复的key
    private final BiConsumer<Map<K, V>, T> accumulator;
    private final BinaryOperator<Map<K, V>> combiner; //组合器
    private final UnaryOperator<Map<K, V>> finisher; //结果转换器

    public ToMap(Function<T, K> keyMapper, Function<T, V> valueMapper, BinaryOperator<V> mergeFunction, Supplier<Map<K, V>> supplier) {

        this.supplier = supplier;
        this.accumulator = (map, element) -> merge(map, keyMapper.apply(element), valueMapper.apply(element), mergeFunction);
        this.combiner = (m1, m2) -> {
            m1.forEach((key, value) -> merge(m2, key, value, mergeFunction));
            return m2;
        };
        this.finisher = UnaryOperator.identity();
    }


    @Override
    public Supplier<Map<K, V>> supplier() {
        return supplier;
    }

    @Override
    public BiConsumer<Map<K, V>, T> accumulator() {
        return accumulator;
    }

    @Override
    public BinaryOperator<Map<K, V>> combiner() {
        return combiner;
    }

    @Override
    public UnaryOperator<Map<K, V>> finisher() {
        return finisher;
    }

    @Override
    public Set<Characteristics> characteristics() {

        return CH_ID;
    }

    /**
     * 逻辑 :
     * (oldValue = null newValue = null) : 不做操作
     * (oldValue != null newValue = null) : 不做操作
     * (oldValue == null newValue =! null) : 取newValue
     * (oldValue != null newValue =! null) : 执行 mergeFunction:默认后者覆盖前者
     */
    private void merge(Map<K, V> map, K key, V newValue, BinaryOperator<V> mergeFunction) {

        V oldValue = map.get(key);
        if (Objects.nonNull(newValue)) {
            if (Objects.nonNull(oldValue)) {
                if (Objects.nonNull(mergeFunction)) {
                    map.put(key, mergeFunction.apply(oldValue, newValue));
                    return;
                }
            }
            map.put(key, newValue);
        }
    }

}
