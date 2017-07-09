package com.huawei.live

import spock.lang.Specification

class RuleTest extends Specification {
    def "demo test"() {
        expect:
        Math.sqrt(n) == expect
        where:
        n | expect
        4 | 2
        9 | 3
    }
}
