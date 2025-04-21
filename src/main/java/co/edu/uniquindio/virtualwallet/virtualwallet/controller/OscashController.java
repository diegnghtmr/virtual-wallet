package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OscashController extends CoreController {
    public OscashController() {
        super();
    }

    public List<String> getPercentageQuality() {
        return modelFactory.getCalifications();
    }

    public void addVotedUser(String selectedRating) {
        modelFactory.addVotedUser(selectedRating);
    }

    public String getSavingsSuggestions(String id) {
        List<TransactionDto> transactions = modelFactory.getTransactionsByUser(id);

        if (transactions.isEmpty()) {
            return "Actualmente no tienes transacciones registradas.";
        }

        double totalIncome = 0.0;
        double totalExpenses = 0.0;

        // Calcular ingresos y gastos totales
        for (TransactionDto transaction : transactions) {
            double amount = transaction.amount();
            if (amount > 0) {
                totalIncome += amount;
            } else {
                totalExpenses += Math.abs(amount);
            }
        }

        // Calcular porcentaje de ahorro recomendado (por ejemplo, 15% de los ingresos)
        double recommendedSavings = totalIncome * 0.15;

        // Determinar capacidad de ahorro real
        double potentialSavings = totalIncome - totalExpenses;

        if (potentialSavings <= 0) {
            return "Actualmente no tienes excedentes para ahorrar.";
        } else {
            StringBuilder suggestions = new StringBuilder();
            suggestions.append("Se recomienda destinar $")
                    .append(String.format("%.2f", recommendedSavings))
                    .append(" de tus ingresos mensuales al ahorro.\n");
            suggestions.append("Opciones para maximizar tus ahorros:\n");
            suggestions.append("- Abrir una cuenta de ahorros con intereses\n");
            suggestions.append("- Invertir en planes de inversión de bajo riesgo\n");
            suggestions.append("- Configurar aportes automáticos a un fondo de emergencia\n");
            return suggestions.toString();
        }
    }

    public String getInvestmentRecommendations(String id) {
        List<TransactionDto> transactions = modelFactory.getTransactionsByUser(id);
        double totalIncome = 0.0;
        double totalExpenses = 0.0;

        // Calcular ingresos y gastos totales
        for (TransactionDto transaction : transactions) {
            double amount = transaction.amount();
            if (amount > 0) {
                totalIncome += amount;
            } else {
                totalExpenses += Math.abs(amount);
            }
        }

        double surplus = totalIncome - totalExpenses;

        if (surplus <= 0) {
            return "No tienes excedentes para invertir en este momento.";
        } else {
            // Determinar perfil de riesgo (simplificado)
            String riskProfile = modelFactory.getUserRiskProfile(id);

            StringBuilder recommendations = new StringBuilder();
            recommendations.append("Tienes un excedente de $")
                    .append(String.format("%.2f", surplus))
                    .append(". Según tu perfil de riesgo ")
                    .append(riskProfile)
                    .append(", te recomendamos:\n");

            switch (riskProfile) {
                case "Conservador":
                    recommendations.append("- Bonos gubernamentales\n");
                    recommendations.append("- Depósitos a plazo fijo\n");
                    break;
                case "Moderado":
                    recommendations.append("- Fondos de inversión diversificados\n");
                    recommendations.append("- Bienes raíces\n");
                    break;
                case "Agresivo":
                    recommendations.append("- Acciones en bolsa\n");
                    recommendations.append("- Inversiones en startups\n");
                    break;
                default:
                    recommendations.append("- Consultar con un asesor financiero\n");
            }

            recommendations.append("Diversifica tus inversiones para optimizar rendimientos.");

            return recommendations.toString();
        }
    }

    public String getCommonExpenses(String id) {
        List<TransactionDto> transactions = modelFactory.getTransactionsByUser(id);

        if (transactions.isEmpty()) {
            return "Actualmente no tienes transacciones registradas.";
        }

        Map<String, Double> categorySpending = new HashMap<>();

        // Calculate total spending per category
        for (TransactionDto transaction : transactions) {
            String category = transaction.category().name();
            double amount = transaction.amount();

            categorySpending.put(category, categorySpending.getOrDefault(category, 0.0) + amount);
        }

        // Sort categories by spending amount
        List<Map.Entry<String, Double>> sortedCategories = new ArrayList<>(categorySpending.entrySet());
        sortedCategories.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // Generate suggestions
        StringBuilder suggestions = new StringBuilder();
        suggestions.append("Tus gastos más comunes son:\n");
        int maxSuggestions = 3;
        for (int i = 0; i < Math.min(maxSuggestions, sortedCategories.size()); i++) {
            Map.Entry<String, Double> entry = sortedCategories.get(i);
            suggestions.append("- ").append(entry.getKey())
                    .append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
        }

        return suggestions.toString();
    }
}
