package dev.wren.overhauledchat.util

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

fun logger(name: String): Logger = LogManager.getLogger(name)
