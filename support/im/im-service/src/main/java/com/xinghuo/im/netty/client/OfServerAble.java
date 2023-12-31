package com.xinghuo.im.netty.client;

import com.xinghuo.im.netty.server.ServerAsClientAble;

public abstract class OfServerAble {
    private ServerAsClientAble ofServer;

    public OfServerAble(ServerAsClientAble ofServer) {
        this.ofServer = ofServer;
    }

    public ServerAsClientAble getOfServer() {
        return ofServer;
    }

    public void setOfServer(ServerAsClientAble ofServer) {
        this.ofServer = ofServer;
    }

}
