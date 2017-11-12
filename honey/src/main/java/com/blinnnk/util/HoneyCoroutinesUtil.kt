package com.blinnnk.util

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.experimental.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

val uiThread = HoneyHandlerContext(Handler(Looper.getMainLooper()), "UI")

fun <T> coroutinesTask(doThings: () -> T, then: (T) -> Unit) {
  val task = async(CommonPool, CoroutineStart.LAZY) { doThings() }
  launch(uiThread) { then(task.await()) }
}

/**
 * Implements [CoroutineDispatcher] on top of an arbitrary Android [Handler].
 * @param handler a handler.
 * @param name an optional name for debugging.
 */
class HoneyHandlerContext(
  private val handler: Handler,
  private val name: String? = null
) : CoroutineDispatcher(), Delay {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    handler.post(block)
  }

  override fun scheduleResumeAfterDelay(time: Long, unit: TimeUnit, continuation: CancellableContinuation<Unit>) {
    handler.postDelayed({
      with(continuation) { resumeUndispatched(Unit) }
    }, unit.toMillis(time))
  }

  override fun invokeOnTimeout(time: Long, unit: TimeUnit, block: Runnable): DisposableHandle {
    handler.postDelayed(block, unit.toMillis(time))
    return object : DisposableHandle {
      override fun dispose() {
        handler.removeCallbacks(block)
      }
    }
  }

  override fun toString() = name ?: handler.toString()
  override fun equals(other: Any?): Boolean = other is HoneyHandlerContext && other.handler === handler
  override fun hashCode(): Int = System.identityHashCode(handler)
}