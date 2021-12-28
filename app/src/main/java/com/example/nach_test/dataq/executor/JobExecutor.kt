package com.example.nach_test.dataq.executor

import com.example.domain.executor.ThreadExecutor
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobExecutor @Inject internal constructor() : ThreadExecutor {
    private val threadPoolExecutor: ThreadPoolExecutor
    override fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private val counter = 0
        override fun newThread(runnable: Runnable): Thread {
            return Thread(
                runnable,
                JobExecutor.JobThreadFactory.Companion.THREAD_NAME + counter
            )
        }

        companion object {
            private const val THREAD_NAME = "android_"
        }
    }

    companion object {
        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5
        private const val KEEP_ALIVE_TIME = 10
        private val KEEP_ALIVE_TIME_UNIT =
            TimeUnit.SECONDS
    }

    init {
        val workQueue: BlockingQueue<Runnable> =
            LinkedBlockingQueue()
        val threadFactory: ThreadFactory = JobExecutor.JobThreadFactory()
        threadPoolExecutor = ThreadPoolExecutor( INITIAL_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME.toLong(),
            KEEP_ALIVE_TIME_UNIT,
            workQueue,
            threadFactory
        )
    }
}