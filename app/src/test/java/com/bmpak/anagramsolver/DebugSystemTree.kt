package com.bmpak.anagramsolver

import timber.log.Timber.Tree
import java.io.PrintWriter
import java.io.StringWriter

object DebugSystemTree : Tree() {
  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    val safeTag = if (tag != null) "$tag :" else ""
    if (t != null) {
      System.err.println("$safeTag ${getStackTraceString(t)}")
      return
    }


    System.out.println("$safeTag $message")
  }

  private fun getStackTraceString(t: Throwable): String {
    val sw = StringWriter(256)
    val pw = PrintWriter(sw, false)
    t.printStackTrace(pw)
    pw.flush()
    return sw.toString()
  }
}