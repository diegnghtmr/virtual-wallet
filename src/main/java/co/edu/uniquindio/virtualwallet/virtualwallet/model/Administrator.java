package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Administrator extends Person{
    public Administrator(){
        super();
    }
}