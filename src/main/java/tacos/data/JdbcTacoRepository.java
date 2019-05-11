package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Taco save(Taco taco) {
        long tacoId=saveTacoInfo(taco);
        taco.setId(tacoId);
        for(Ingredient ingredient : taco.getIngredients()){
            saveIngredientToTaco(ingredient,tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
//        PreparedStatementCreator preparedStatementCreator=
//                new PreparedStatementCreatorFactory(
//                        "insert into Taco (name,createdAt) values(?,?)",
//                        Types.VARCHAR,Types.TIMESTAMP).newPreparedStatementCreator(
//                        Arrays.asList(
//                                taco.getName(),
//                                new Timestamp(taco.getCreatedAt().getTime())
//                ));
        PreparedStatementCreatorFactory preparedStatementCreatorFactory=
                new PreparedStatementCreatorFactory(
                        "insert into Taco (name,createdAt) values(?,?)",
                        Types.VARCHAR,Types.TIMESTAMP);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        preparedStatementCreatorFactory.setGeneratedKeysColumnNames("id");
        PreparedStatementCreator preparedStatementCreator=
                preparedStatementCreatorFactory.newPreparedStatementCreator(
                        Arrays.asList(taco.getName(),
                                new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder=new GeneratedKeyHolder();

        jdbcTemplate.update(preparedStatementCreator,keyHolder);
        return keyHolder.getKey().longValue();
        //return 10L;
    }

    private void saveIngredientToTaco(Ingredient ingredient,long tacoId){
        jdbcTemplate.update("insert into taco_ingredients(taco, ingredient) " +
                "values (?,?)",
                tacoId, ingredient.getId());
    }

}
