package io.github.vwenx.common.exceptionhandle;

import java.util.function.Function;

/**
 * 简化异常捕获处理
 *     catchThrow(): 执行实现逻辑，发生异常默认使用RuntimeException包装后抛出。
 *     getOrThrow(): 执行实现逻辑并返回结果，发生异常默认使用RuntimeException包装后抛出。
 *
 * @author XuWen
 */
public class SimpleCatch {


    public static void catchThrow(Runner runner){
        catchThrow(runner, RuntimeException::new);
    }

    public static void catchThrow(Runner runner, String exMsg){
        catchThrow(runner, cause -> new RuntimeException(exMsg, cause));
    }

    public static void catchThrow(Runner runner, Function<Throwable, RuntimeException> exceptionConvert){
        try {
            runner.run();
        } catch (Throwable e) {
            throw exceptionConvert.apply(e);
        }
    }

    public static <R> R getOrThrow(Supplier<R> supplier){
        return getOrThrow(supplier, RuntimeException::new);
    }

    public static <R> R getOrThrow(Supplier<R> supplier, String exMsg){
        return getOrThrow(supplier, e -> new RuntimeException(exMsg, e));
    }

    public static <R> R getOrThrow(Supplier<R> supplier, Function<Throwable, RuntimeException> exceptionConvert){
        try {
            return supplier.get();
        } catch (Throwable e) {
            throw exceptionConvert.apply(e);
        }
    }

    public interface Supplier<R> {
        R get() throws Throwable;
    }
    public interface Runner {
        void run() throws Throwable;
    }

}
