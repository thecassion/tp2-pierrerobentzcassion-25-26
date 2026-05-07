package ht.fds.mbds.pierrerobentzcassion;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import ht.fds.mbds.pierrerobentzcassion.outil.meteo.MeteoTool;

import java.util.Scanner;


public class Test7 {

    // Assistant conversationnel
    interface Assistant {
        // Prend un message de l'utilisateur et retourne une réponse du LLM.
        @SystemMessage("""
        Tu es un assistant général. Tu peux répondre à toutes sortes de questions.
        Quand on te demande s'il faut prendre un parapluie ou des informations
        sur la pluie pour une ville, utilise les outils mis à ta disposition
        pour obtenir les coordonnées et les prévisions météo.
        Si une ville n'existe pas, indique-le clairement à l'utilisateur.
        """)
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        String llmKey = System.getenv("GEMINI_KEY");
        if (llmKey == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        // Mettre une température qui ne dépasse pas 0,3.
        // Le RAG sert à mieux contrôler l'exactitude des informations données par le LLM
        // et il est donc logique de diminuer la température.
        ChatModel model = GoogleAiGeminiChatModel.builder() .apiKey(llmKey)
                .modelName("gemini-2.5-pro")
                .temperature(0.3)
                .build();

        // Création de l'assistant conversationnel, avec une mémoire.
        // L'implémentation de Assistant est faite par LangChain4j.
        // L'assistant gardera en mémoire les 20 derniers messages.
        // La base vectorielle en mémoire est utilisée pour retrouver les embeddings.
        Assistant assistant =
                AiServices.builder(Assistant.class)
                        .chatModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                        .tools(new MeteoTool()) // Ajout de l'outil
                        .build();

        conversationAvec(assistant);
    }


    private static void conversationAvec(Assistant assistant) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("==================================================");
                System.out.println("Posez votre question : ");
                String question = scanner.nextLine();
                if (question.isBlank()) {
                    continue;
                }
                System.out.println("==================================================");
                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }
                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
                System.out.println("==================================================");
            }
        }
    }

}
