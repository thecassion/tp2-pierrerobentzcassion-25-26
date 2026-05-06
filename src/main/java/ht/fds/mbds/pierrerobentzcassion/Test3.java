package ht.fds.mbds.pierrerobentzcassion;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

public class Test3 {
    public static void main(String[] args) {
        String cle= System.getenv("GEMINI_KEY");
        // Création du modèle avec un builder
        ChatModel modele = GoogleAiGeminiChatModel.builder() .apiKey(cle)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();
        // Creation du PromptTemplate : Prompt avec variable
        PromptTemplate template = PromptTemplate.from("Traduis le texte suivant en Anglais : {{text}}");

        // Generation d'un prompt concret en remplissant la variable text

        String textToTransalte = "Bonjour Je m'appelle Pierre Robentz Cassion et je vis en Haiti";
        Prompt prompt = template.apply(Map.of("text", textToTransalte));

        // Envoie du prompt au modele et affichage
        String reponse = modele.chat(prompt.text());
        System.out.println("Texte à traduire : " + textToTransalte);
        System.out.println("Traduction : " + reponse);

    }
}
