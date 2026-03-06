package com.devarena.model;

import jakarta.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String buggyCode;

    @Column(columnDefinition = "TEXT")
    private String correctCode;

    @Column(nullable = false)
    private String bugDescription;

    @Column(nullable = false)
    private String category; // e.g. "NullPointer", "Logic", "Syntax"

    @Column(nullable = false)
    private String difficulty; // "Easy", "Medium", "Hard"

    @Column(nullable = false)
    private String hint;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBuggyCode() { return buggyCode; }
    public void setBuggyCode(String buggyCode) { this.buggyCode = buggyCode; }

    public String getCorrectCode() { return correctCode; }
    public void setCorrectCode(String correctCode) { this.correctCode = correctCode; }

    public String getBugDescription() { return bugDescription; }
    public void setBugDescription(String bugDescription) { this.bugDescription = bugDescription; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getHint() { return hint; }
    public void setHint(String hint) { this.hint = hint; }
}