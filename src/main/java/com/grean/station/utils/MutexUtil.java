//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;

public class MutexUtil {
    private final MutexUtil.Sync sync = new MutexUtil.Sync();

    public MutexUtil() {
    }

    public void lock() {
        this.sync.acquire(1);
    }

    public boolean tryLock() {
        return this.sync.tryAcquire(1);
    }

    public void unlock() {
        this.sync.release(1);
    }

    public Condition newCondition() {
        return this.sync.newCondition();
    }

    public boolean isLocked() {
        return this.sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return this.sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        this.sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return this.sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    private static class Sync extends AbstractQueuedSynchronizer {
        private Sync() {
        }

        protected boolean isHeldExclusively() {
            return this.getState() == 1;
        }

        public boolean tryAcquire(int acquires) {
            assert acquires == 1;

            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else {
                return false;
            }
        }

        protected boolean tryRelease(int releases) {
            assert releases == 1;

            if (this.getState() == 0) {
                throw new IllegalMonitorStateException();
            } else {
                this.setExclusiveOwnerThread((Thread)null);
                this.setState(0);
                return true;
            }
        }

        Condition newCondition() {
//            return new ConditionObject(this);
            return new ConditionObject();
        }
    }
}
