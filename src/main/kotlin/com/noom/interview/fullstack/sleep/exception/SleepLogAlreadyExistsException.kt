package com.noom.interview.fullstack.sleep.exception

class SleepLogAlreadyExistsException : RuntimeException() {
    override val message: String = "Sleep log already exists for this date"
}