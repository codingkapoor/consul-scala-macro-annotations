package com.codingkapoor.myservice

import java.security.MessageDigest

object Util {
  def md5(s: String): String = MessageDigest.getInstance("MD5").digest(s.getBytes).map(0xFF & _).map {
    "%02x".format(_)
  }.foldLeft("") {
    _ + _
  }
}
