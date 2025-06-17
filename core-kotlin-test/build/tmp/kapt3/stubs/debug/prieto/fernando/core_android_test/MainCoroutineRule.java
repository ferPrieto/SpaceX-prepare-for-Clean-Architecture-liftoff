package prieto.fernando.core_android_test;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\t\u0010\f\u001a\u00020\rH\u0097\u0001J\u0012\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\u0012\u0010\u0011\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8\u0016X\u0097\u0005\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2 = {"Lprieto/fernando/core_android_test/MainCoroutineRule;", "Lorg/junit/rules/TestWatcher;", "Lkotlinx/coroutines/test/TestCoroutineScope;", "()V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "testScheduler", "Lkotlinx/coroutines/test/TestCoroutineScheduler;", "getTestScheduler", "()Lkotlinx/coroutines/test/TestCoroutineScheduler;", "cleanupTestCoroutines", "", "finished", "description", "Lorg/junit/runner/Description;", "starting", "core-android-test_debug"})
@kotlinx.coroutines.ExperimentalCoroutinesApi()
public final class MainCoroutineRule extends org.junit.rules.TestWatcher implements kotlinx.coroutines.test.TestCoroutineScope {
    
    public MainCoroutineRule() {
        super();
    }
    
    @java.lang.Override()
    protected void starting(@org.jetbrains.annotations.Nullable()
    org.junit.runner.Description description) {
    }
    
    @java.lang.Override()
    protected void finished(@org.jetbrains.annotations.Nullable()
    org.junit.runner.Description description) {
    }
    
    @java.lang.Override()
    @kotlinx.coroutines.ExperimentalCoroutinesApi()
    @java.lang.Deprecated()
    public void cleanupTestCoroutines() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.test.TestCoroutineScheduler getTestScheduler() {
        return null;
    }
}