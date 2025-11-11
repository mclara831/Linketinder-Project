package org.acelerazg.utils

class Utils {
    static String generateUUID() {
        return UUID.randomUUID().toString()
    }

    static String toJson(int statusCode, String msg) {
        return [status: statusCode, message: msg]
    }
}
