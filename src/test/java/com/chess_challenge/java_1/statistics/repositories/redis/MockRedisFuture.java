package com.chess_challenge.java_1.statistics.repositories.redis;

import io.lettuce.core.RedisFuture;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class MockRedisFuture<T> implements RedisFuture<T> {
    private CompletableFuture<T> value;

    private MockRedisFuture(T value) {
        this.value = CompletableFuture.completedFuture(value);
    }

    private MockRedisFuture(Throwable throwable) {
        this.value = CompletableFuture.failedFuture(throwable);
    }

    public static <T> MockRedisFuture<T> success(T value) {
        return new MockRedisFuture<>(value);
    }

    public static <T> MockRedisFuture<T> failed(Throwable throwable) {
        return new MockRedisFuture<>(throwable);
    }

    @Override
    public String getError() {
        return null;
    }

    @Override
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <U> CompletionStage<U> thenApply(Function<? super T, ? extends U> fn) {
        return this.value.thenApply(fn);
    }

    @Override
    public <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return null;
    }

    @Override
    public CompletionStage<Void> thenAccept(Consumer<? super T> action) {
        return null;
    }

    @Override
    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action) {
        return null;
    }

    @Override
    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return null;
    }

    @Override
    public CompletionStage<Void> thenRun(Runnable action) {
        return null;
    }

    @Override
    public CompletionStage<Void> thenRunAsync(Runnable action) {
        return null;
    }

    @Override
    public CompletionStage<Void> thenRunAsync(Runnable action, Executor executor) {
        return null;
    }

    @Override
    public <U, V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return null;
    }

    @Override
    public <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return null;
    }

    @Override
    public <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return null;
    }

    @Override
    public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return null;
    }

    @Override
    public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return null;
    }

    @Override
    public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return null;
    }

    @Override
    public CompletionStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return null;
    }

    @Override
    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return null;
    }

    @Override
    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return null;
    }

    @Override
    public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return null;
    }

    @Override
    public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return null;
    }

    @Override
    public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return null;
    }

    @Override
    public CompletionStage<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return null;
    }

    @Override
    public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return null;
    }

    @Override
    public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return null;
    }

    @Override
    public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return null;
    }

    @Override
    public CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return null;
    }

    @Override
    public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return null;
    }

    @Override
    public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return null;
    }

    @Override
    public CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return null;
    }

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return null;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
