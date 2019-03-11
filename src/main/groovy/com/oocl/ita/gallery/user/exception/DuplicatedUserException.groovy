package com.oocl.ita.gallery.user.exception

class DuplicatedUserException extends Exception {
    DuplicatedUserException(String s) {
        super(s)
    }
    @Override
    String toString() {
        return this.message
    }
}