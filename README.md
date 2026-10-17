# Spring Boot OpenAI Tool Calling Agent Demo

A small Spring Boot demo showing how an LLM can select and execute Java tools through Spring AI.

## Features

- OpenAI integration with Spring AI
- Tool selection based on natural-language requests
- Order status and delivery-date lookup
- Order cancellation with a status guard
- In-memory conversation context using `conversationId`
- Tool execution logging and structured API errors

## Tech Stack

- Java 21
- Spring Boot 4
- Spring AI 2
- OpenAI
- Maven

## Run

Create `.env` from the example file and add your OpenAI API key:

```properties
OPENAI_API_KEY=your-api-key
```

Start the application:

```bash
./mvnw spring-boot:run
```

The API runs at `http://localhost:8095`.

## Try It

```http
POST /api/agent/chat
Content-Type: application/json
```

```json
{
  "conversationId": "demo-1",
  "message": "What is the status of order 1002?"
}
```

Follow-up requests can reuse the same `conversationId`:

```json
{
  "conversationId": "demo-1",
  "message": "Cancel it."
}
```

Demo order IDs: `1001`, `1002`, and `1003`.

> All order data and chat memory are stored in memory and reset when the application restarts.
