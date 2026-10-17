package com.example.toolcallingagent.controller;

import com.example.toolcallingagent.dto.ChatRequest;
import com.example.toolcallingagent.dto.ChatResponse;
import com.example.toolcallingagent.service.AgentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
        return new ChatResponse(agentService.chat(request.conversationId(), request.message()));
    }
}
