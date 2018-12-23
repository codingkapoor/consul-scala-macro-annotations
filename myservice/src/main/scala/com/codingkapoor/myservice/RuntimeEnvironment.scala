package com.codingkapoor.myservice

import java.io.File
import com.typesafe.config.ConfigFactory
import scala.sys.SystemProperties

object RuntimeEnvironment {
  private lazy val sp = new SystemProperties()

  private lazy val application_conf = getConfDir + File.separator + "application.conf"
  lazy val appConfig = ConfigFactory.parseFile(new File(application_conf))

  def getConfDir = {
    val vmParm = "myservice.conf"
    val r = sp(vmParm)

    if (r != null && !r.trim.isEmpty) r.trim else throw new RuntimeException(s"-D$vmParm was not provided.")
  }

  def getHomeDir = {
    val vmParm = "myservice.home"
    val r = sp(vmParm)

    if (r != null && !r.trim.isEmpty) r.trim else throw new RuntimeException(s"-D$vmParm was not provided.")
  }
}
