package javaproject.com.expensetracker.controller;

import javaproject.com.expensetracker.entities.*;
import javaproject.com.expensetracker.repository.*;

import javaproject.com.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.security.Principal;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;
    // Get all expenses
    @GetMapping("/getAll")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    // Get a single expense by ID
    @GetMapping("/get/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    // Add a new expense
    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getExpensesByUserId(@PathVariable Long userId, Principal principal) {
        // Get the authenticated username
        String loggedInUsername = principal.getName();

        // Find the user by username
        User user = userRepository.findByUsername(loggedInUsername);
                

        // Check if the user is an admin
        boolean isAdmin = user.getRole().equalsIgnoreCase("ADMIN");

        // Allow access if the user is an admin or accessing their own expenses
        if (!isAdmin && !user.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to access other users' expenses!");
        }

        // Fetch and return the expenses
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }


    // Update an existing expense
    @PutMapping("/update/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        return expenseService.updateExpense(id, updatedExpense);
    }

    // Delete an expense
    @DeleteMapping("/delete/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
