package ht.fds.mbds.pierrerobentzcassion;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {
    public static void main(String[] args) {
        String cle= System.getenv("GEMINI_KEY");
        // Création du modèle avec un builder
        ChatModel modele = GoogleAiGeminiChatModel.builder() .apiKey(cle)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();
        // Pose une question au modèle
        String reponse= modele.chat("Bonjour je m'appelle Pierre Robentz Cassion");
        // Affiche la réponse du modèle (hello)
        System.out.println(reponse);

        String response2 = modele.chat("Quel est mon nom");

        System.out.println(response2);
    }
}
