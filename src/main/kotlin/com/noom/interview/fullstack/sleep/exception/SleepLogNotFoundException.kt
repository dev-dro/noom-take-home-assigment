package com.noom.interview.fullstack.sleep.exception

class SleepLogNotFoundException : RuntimeException() {
    override val message: String = "No sleep log found"
}