package com.noom.interview.fullstack.sleep.exception

class SleepLogInvalidTimeException: RuntimeException() {
    override val message: String = "The startSleepAt needs to be before the wokeUpAt"
}
