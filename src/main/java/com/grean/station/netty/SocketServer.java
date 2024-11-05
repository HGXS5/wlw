//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.netty;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.grean.station.domain.request.RtdData;
import com.grean.station.service.query.RealTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SocketServer {
    static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    @Value("${socket.port}")
    private Integer port;
    @Autowired
    WebSocketSender socketSender;
    @Autowired
    RealTimeService realTimeService;

    public SocketServer() {
    }

    public void doStart() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(this.port);
        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("rtdEvent", RtdData.class, new DataListener<RtdData>() {
            public void onData(SocketIOClient socketIOClient, RtdData rtdData, AckRequest ackRequest) throws Exception {
                server.getBroadcastOperations().sendEvent("rtdevent", new Object[]{rtdData});
            }
        });
        server.addEventListener("countEvent", Integer.class, new DataListener<Integer>() {
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                server.getBroadcastOperations().sendEvent("countevent", new Object[]{integer});
            }
        });
        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                SocketServer.logger.info("...........clientConnected...........");
                SocketServer.this.socketSender.clientConnected(socketIOClient);
                SocketServer.this.socketSender.updateInfo(SocketServer.this.realTimeService.getClientRtdData());
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient socketIOClient) {
                SocketServer.logger.info("...........clientDisconnected...........");
                SocketServer.this.socketSender.clientDisconnected(socketIOClient);
            }
        });
        server.start();
    }
}
