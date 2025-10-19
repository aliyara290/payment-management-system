package com.paysystem.service;

import com.paysystem.utils.Crud;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsService {

    // Agent Statistics
    public Map<String, Object> getAgentStatistics(String agentEmail) {
        Map<String, Object> stats = new HashMap<>();

        try {
            // Get agent ID
            List<Map<String, String>> agentResult = Crud.readByCondition("users", "email", agentEmail);
            if (agentResult.isEmpty()) {
                System.out.println("Agent not found");
                return stats;
            }

            int agentId = Integer.parseInt(agentResult.get(0).get("id"));

            // Get all payments for this agent
            List<Map<String, String>> payments = Crud.readByCondition("payments", "agent_id", agentId);

            double totalAnnualSalary = 0;
            int bonusCount = 0;
            int primeCount = 0;
            int compensationCount = 0;

            for (Map<String, String> payment : payments) {
                String type = payment.get("type").toLowerCase();
                double amount = Double.parseDouble(payment.get("amount"));

                if ("salary".equals(type)) {
                    totalAnnualSalary += amount;
                } else if ("bonus".equals(type)) {
                    bonusCount++;
                } else if ("prime".equals(type)) {
                    primeCount++;
                } else if ("compensation".equals(type)) {
                    compensationCount++;
                }
            }

            stats.put("agent_email", agentEmail);
            stats.put("total_annual_salary", totalAnnualSalary);
            stats.put("bonus_count", bonusCount);
            stats.put("prime_count", primeCount);
            stats.put("compensation_count", compensationCount);
            stats.put("total_payments", payments.size());

        } catch (Exception e) {
            System.out.println("Error calculating agent statistics: " + e.getMessage());
        }

        return stats;
    }

    // Department Statistics
    public Map<String, Object> getDepartmentStatistics(String departmentName) {
        Map<String, Object> stats = new HashMap<>();

        try {
            // Get department ID
            List<Map<String, String>> deptResult = Crud.readByCondition("departments", "name", departmentName);
            if (deptResult.isEmpty()) {
                System.out.println("Department not found");
                return stats;
            }

            int departmentId = Integer.parseInt(deptResult.get(0).get("id"));

            // Get all agents in this department
            List<Map<String, String>> agents = Crud.readByCondition("users", "department_id", departmentId);

            if (agents.isEmpty()) {
                stats.put("message", "No agents found in this department");
                return stats;
            }

            double totalDepartmentPayments = 0;
            List<Map<String, Object>> agentRankings = new ArrayList<>();

            for (Map<String, String> agent : agents) {
                int agentId = Integer.parseInt(agent.get("id"));
                String agentName = agent.get("first_name") + " " + agent.get("last_name");
                String agentEmail = agent.get("email");

                // Get all payments for this agent
                List<Map<String, String>> payments = Crud.readByCondition("payments", "agent_id", agentId);

                List<Map <String, String>> topTwo = payments.stream().sorted().limit(2).toList();


                double agentTotalPayments = 0;
                for (Map<String, String> payment : payments) {
                    agentTotalPayments += Double.parseDouble(payment.get("amount"));
                }

                totalDepartmentPayments += agentTotalPayments;

                Map<String, Object> agentRanking = new HashMap<>();
                agentRanking.put("agent_name", agentName);
                agentRanking.put("agent_email", agentEmail);
                agentRanking.put("total_received", agentTotalPayments);
                agentRanking.put("agent_rankings", agentRankings);
                agentRanking.put("top 2: ", topTwo);
                agentRankings.add(agentRanking);
            }

            // Sort agents by total received (descending)
            agentRankings.sort((a, b) ->
                    Double.compare((Double) b.get("total_received"), (Double) a.get("total_received"))
            );

            double averageSalary = agents.isEmpty() ? 0 : totalDepartmentPayments / agents.size();

            stats.put("department_name", departmentName);
            stats.put("total_department_payments", totalDepartmentPayments);
            stats.put("average_agent_salary", averageSalary);
            stats.put("total_agents", agents.size());


        } catch (Exception e) {
            System.out.println("Error calculating department statistics: " + e.getMessage());
        }

        return stats;
    }

    // Display Agent Statistics
    public void displayAgentStatistics(Map<String, Object> stats) {
        if (stats.isEmpty()) {
            System.out.println("\nNo statistics available.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("              AGENT STATISTICS");
        System.out.println("=".repeat(60));
        System.out.println("Agent Email: " + stats.get("agent_email"));
        System.out.println("Total Annual Salary: " + stats.get("total_annual_salary") + " MAD");
        System.out.println("Number of Bonuses: " + stats.get("bonus_count"));
        System.out.println("Number of Primes: " + stats.get("prime_count"));
        System.out.println("Number of Compensations: " + stats.get("compensation_count"));
        System.out.println("Total Payments Received: " + stats.get("total_payments"));
        System.out.println("=".repeat(60));
    }

    // Display Department Statistics
    public void displayDepartmentStatistics(Map<String, Object> stats) {
        if (stats.isEmpty()) {
            System.out.println("\nNo statistics available.");
            return;
        }

        if (stats.containsKey("message")) {
            System.out.println("\n" + stats.get("message"));
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("                    DEPARTMENT STATISTICS");
        System.out.println("=".repeat(80));
        System.out.println("Department: " + stats.get("department_name"));
        System.out.println("Total Department Payments: " + stats.get("total_department_payments") + " MAD");
        System.out.println("Average Agent Salary: " +
                String.format("%.2f", stats.get("average_agent_salary")) + " MAD");
        System.out.println("Total Agents: " + stats.get("total_agents"));

        System.out.println("\n" + "-".repeat(80));
        System.out.println("AGENT RANKINGS (Highest to Lowest)");
        System.out.println("-".repeat(80));
        System.out.printf("%-5s | %-30s | %-25s | %-15s%n",
                "Rank", "Name", "Email", "Total Received");
        System.out.println("-".repeat(80));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rankings = (List<Map<String, Object>>) stats.get("agent_rankings");

        int rank = 1;
        for (Map<String, Object> agent : rankings) {
            System.out.printf("%-5d | %-30s | %-25s | %-15.2f MAD%n",
                    rank++,
                    agent.get("agent_name"),
                    agent.get("agent_email"),
                    agent.get("total_received"));
        }
        System.out.println("=".repeat(80));
    }

     public void bestAgentPayment (String departmentName) {
        if(departmentName.isEmpty()){
            System.out.println("Department name is empty");
        }

        String sql = "SELECT * FROM users JOIN department d ";
        String departmentID = Crud.readByCondition("departments", "name", departmentName).getFirst().get("id");

     }
}