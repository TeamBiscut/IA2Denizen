package net.biscut.ia2denizen.utils;

public class BridgeLoadException extends RuntimeException {

    private static final long serialVersionUID = 1159105944857392268L;
    public String message;

    public BridgeLoadException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}