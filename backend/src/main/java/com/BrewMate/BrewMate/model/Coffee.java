package com.BrewMate.BrewMate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


// Marks this class as a database entity
@Entity
@Table(name = "coffees") // Creates a table named 'coffees'
public class Coffee {

    // Unique identifier for each coffee
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    // Coffee name (required)
    @Column(nullable = false)
    private String name;

    // Ingredient proportions (as percentages)
    private int espressoRatio;
    private int milkRatio;
    private int foamRatio;
    private int waterRatio;

    // OPTIONAL See snapchat for more info
    // private String guideImage;

    // Coffee origin/type (required)
    @Column(nullable = false)
    private String brewingStyle;

    // Path to an image of the coffee
    @Column
    private String imagePath;

    // Gets the path to the coffee image
    public String getImagePath() {
        return imagePath;
    }

    // Sets the path to the coffee image
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Empty constructor required by JPA
    public Coffee() {}

    // Constructor to create a new coffee
    public Coffee(String name, int espressoRatio, int milkRatio, int foamRatio, int waterRatio) {
        this.name = name;
        this.espressoRatio = espressoRatio;
        this.milkRatio = milkRatio;
        this.foamRatio = foamRatio;
        this.waterRatio = waterRatio;
    }

    // Getters and setters
    // Gets the coffee's ID
    public Long getId() {
        return id;
    }

    // Gets the coffee's name
    public String getName() {
        return name;
    }

    // Sets the coffee's name
    public void setName(String name) {
        this.name = name;
    }

    // Gets the espresso percentage
    public int getEspressoRatio() {
        return espressoRatio;
    }

    // Sets the espresso percentage
    public void setEspressoRatio(int espressoRatio) {
        this.espressoRatio = espressoRatio;
    }

    // Gets the milk percentage
    public int getMilkRatio() {
        return milkRatio;
    }

    // Sets the milk percentage
    public void setMilkRatio(int milkRatio) {
        this.milkRatio = milkRatio;
    }

    // Gets the foam percentage
    public int getFoamRatio() {
        return foamRatio;
    }

    // Sets the foam percentage
    public void setFoamRatio(int foamRatio) {
        this.foamRatio = foamRatio;
    }

    // Gets the water percentage
    public int getWaterRatio() {
        return waterRatio;
    }

    // Sets the water percentage
    public void setWaterRatio(int waterRatio) {
        this.waterRatio = waterRatio;
    }

    // Gets the coffee's style (Italian, American, etc)
    public String getBrewingStyle(){
        return brewingStyle;
    }

    // Sets the coffee's style (Italian, American, etc)
    public void setBrewingStyle(String brewingStyle) {
        this.brewingStyle = brewingStyle;
    }

    // OPTIONAL See snapchat for more info
   /*  public String getGuideImage() {
        return guideImage;
    } */

    // OPTIONAL See snapchat for more info
    /* public void setGuideImage(String guideImage) {
        this.guideImage = guideImage;
    } */
}