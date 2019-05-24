// tag::all[]
// tag::allButValidation[]
package tacos;
import java.util.Date;
import java.util.List;
// end::allButValidation[]
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
// tag::allButValidation[]
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@Entity
@RestResource(rel="tacos", path="tacos")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // end::allButValidation[]
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    // tag::allButValidation[]
    private String name;
    // end::allButValidation[]
    @Size(min=1, message="You must choose at least 1 ingredient")
    // tag::allButValidation[]
    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    private Date createdAt;

    @PrePersist
    void createAt(){
        this.createdAt=new Date();
    }

}
//end::allButValidation[]
//tag::end[]