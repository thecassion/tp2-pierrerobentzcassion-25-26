package ht.fds.mbds.pierrerobentzcassion;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.output.TokenUsage;

public class Test2 {

    // Prix par million de tokens (en dollars)
    private static final double PRIX_PAR_MILLION_TOKENS_ENTREE = 0.30;
    private static final double PRIX_PAR_MILLION_TOKENS_SORTIE = 2.50;

    public static void main(String[] args) {

        // --- 1. Initialisation du modèle ---
        String cleApi = System.getenv("GEMINI_KEY");

        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cleApi)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();

        // --- 2. Envoi de la requête ---
        ChatRequest requete = ChatRequest.builder()
                .messages(UserMessage.from("Bonjour je m'appelle Pierre Robentz Cassion"))
                .build();

        ChatResponse reponse = modele.chat(requete);

        // --- 3. Affichage de la réponse ---
        System.out.println("Réponse du modèle :");
        System.out.println(reponse.aiMessage().text());

        // --- 4. Calcul et affichage des coûts ---
        TokenUsage usage = reponse.tokenUsage();
        int tokensEntree = usage.inputTokenCount();
        int tokensSortie = usage.outputTokenCount();
        int tokensTotal  = usage.totalTokenCount();

        double coutEntree = (tokensEntree * PRIX_PAR_MILLION_TOKENS_ENTREE) / 1_000_000;
        double coutSortie = (tokensSortie * PRIX_PAR_MILLION_TOKENS_SORTIE) / 1_000_000;
        double coutTotal  = coutEntree + coutSortie;

        System.out.println("\n--- Utilisation des tokens ---");
        System.out.println("Tokens d'entrée  : " + tokensEntree);
        System.out.println("Tokens de sortie : " + tokensSortie);
        System.out.println("Tokens total     : " + tokensTotal);

        System.out.println("\n--- Coût estimé (USD) ---");
        System.out.printf("Coût entrée  : $%.8f%n", coutEntree);
        System.out.printf("Coût sortie  : $%.8f%n", coutSortie);
        System.out.printf("Coût total   : $%.8f%n", coutTotal);
        System.out.printf("Requêtes similaires pour 1$ : %.0f%n", 1.0 / coutTotal);
    }
}
