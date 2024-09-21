package co.edu.uniquindio.virtualwallet.virtualwallet.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder

public class Category {
    private int id;
    private String name;
    private String description;

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}