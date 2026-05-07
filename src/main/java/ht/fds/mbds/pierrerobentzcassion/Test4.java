package ht.fds.mbds.pierrerobentzcassion;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;
import java.time.Duration;

public class Test4 {
    public static void main(String[] args) {
        String cle= System.getenv("GEMINI_KEY");

        // 1. initialisation du modele d'embeddings

        EmbeddingModel modele = GoogleAiEmbeddingModel.builder()
                .apiKey(cle)
                .modelName("gemini-embedding-2")
                .outputDimensionality(300)
                .timeout(Duration.ofMillis(100000))
                .taskType(GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY)
                .build();

        // 2. Plusieurs couples de phrases pour comparaison
        String[][] couples =  {
                {"J'aime les pommes", "J'adore les pommes"},
                {"J'aime les pommes", "Je déteste les pommes"},
                {"Le chat est sur le toit", "Le chat est sur le toit"},
                {"Le chat est sur le toit", "Le chien est dans la maison"},
                {"La météo est belle aujourd'hui", "Il fait beau aujourd'hui"},
                {"La météo est belle aujourd'hui", "Il pleut des cordes aujourd'hui"}
        };

        // 3.calculer et afficher la similarité pour chaque couple
        for (String[] couple : couples) {
            String phrase1 = couple[0];
            String phrase2 = couple[1];
            Embedding emb1 = modele.embed(TextSegment.from(phrase1)).content();
            Embedding emb2 = modele.embed(TextSegment.from(phrase2)).content();

            double similarite = CosineSimilarity.between(emb1, emb2);

            System.out.printf("Similarité entre \"%s\" et \"%s\" : %.4f%n", phrase1, phrase2, similarite);
        }

    }
}
