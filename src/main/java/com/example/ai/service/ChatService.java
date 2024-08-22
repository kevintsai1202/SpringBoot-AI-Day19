package com.example.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {
	private final ChatClient chatClient;
	private final ChatMemory chatMemory = new InMemoryChatMemory();
	
	public String chat(String chatId, String userMessage) {	   
		log.info("Chat ID:{} User Message:{}",chatId,userMessage);
		String assistantMessage = this.chatClient.prompt()
		.advisors(new MessageChatMemoryAdvisor(chatMemory, chatId, 30))
		.user(userMessage)
        .call().content();
		log.info("Chat ID:{} Assistant Message:{}",chatId,assistantMessage);
		return assistantMessage;
	}
}
