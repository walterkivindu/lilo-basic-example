package com.example.lilogateway.config;

import io.fria.lilo.GraphQLRequest;
import io.fria.lilo.Lilo;
import io.fria.lilo.RemoteSchemaSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LiloGatewayController {
    private final Lilo lilo;

    public LiloGatewayController() {
        this.lilo = Lilo.
                builder()
                .addSource(RemoteSchemaSource.create("server1", "http://localhost:8081/graphql"))
                .addSource(RemoteSchemaSource.create("server2", "http://localhost:8082/graphql"))
                .build();
    }

    @ResponseBody
    @PostMapping("/graphql")
    public Map<String, Object> stitch(@RequestBody final GraphQLRequest request) {
        return this.lilo.stitch(request.toExecutionInput()).toSpecification();
    }
}
