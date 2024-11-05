//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.netty;

import com.corundumstudio.socketio.SocketIOClient;
import com.grean.station.domain.request.RtdCount;
import com.grean.station.domain.request.RtdData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSender implements CommandLineRunner {
    static final Logger logger = LoggerFactory.getLogger(WebSocketSender.class);
    private List<SocketIOClient> socketIOClients = new ArrayList();

    public WebSocketSender() {
    }

    public void run(String... strings) throws Exception {
    }

    public void updateInfo(List<RtdData> rtdDataList) {
        Iterator var2 = this.socketIOClients.iterator();

        while(var2.hasNext()) {
            SocketIOClient client = (SocketIOClient)var2.next();
            client.sendEvent("rtdevent", new Object[]{rtdDataList});
        }

    }

    public void updateCount(RtdCount rtdCount) {
        Iterator var2 = this.socketIOClients.iterator();

        while(var2.hasNext()) {
            SocketIOClient client = (SocketIOClient)var2.next();
            client.sendEvent("countevent", new Object[]{rtdCount});
        }

    }

    public void clientConnected(SocketIOClient socketIOClient) {
        Iterator var2 = this.socketIOClients.iterator();

        SocketIOClient client;
        do {
            if (!var2.hasNext()) {
                this.socketIOClients.add(socketIOClient);
                logger.info("clientConnected");
                logger.info(this.socketIOClients.toString());
                return;
            }

            client = (SocketIOClient)var2.next();
        } while(!client.getSessionId().equals(socketIOClient.getSessionId()));

    }

    public void clientDisconnected(SocketIOClient socketIOClient) {
        this.socketIOClients = (List)this.socketIOClients.stream().filter((client) -> {
            return !client.getSessionId().equals(socketIOClient.getSessionId());
        }).collect(Collectors.toList());
        logger.info("clientDisconnected");
        logger.info(this.socketIOClients.toString());
    }
}
