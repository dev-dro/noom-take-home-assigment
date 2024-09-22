package com.noom.interview.fullstack.sleep.exception

class FieldValueInvalidException(field: String): RuntimeException("Invalid value for field $field")