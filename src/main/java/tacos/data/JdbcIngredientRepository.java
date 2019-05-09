package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
@Repository
public class JdbcIngredientRepository
        implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(
                "select id,name,type from ingredient"
                ,this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return (Ingredient) jdbcTemplate.queryForObject("select id,name,type from ingredient where id=?"
                ,this::mapRowToIngredient,id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs,int rowNum)throws SQLException {
        return new Ingredient(
                rs.getString("id")
                ,rs.getString("name")
                ,Ingredient.Type.valueOf(rs.getString("type")));
    }
}
