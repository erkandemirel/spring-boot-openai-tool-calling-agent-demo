package com.example.toolcallingagent.service;

import com.example.toolcallingagent.tool.OrderTools;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private final ChatClient chatClient;

    public AgentService(ChatClient.Builder chatClientBuilder, OrderTools orderTools, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        You are a helpful order support assistant.
                        Use the available tools when order data is required.
                        Call a state-changing tool only when the user explicitly requests that action.
                        """)
                .defaultTools(orderTools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String chat(String conversationId, String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
