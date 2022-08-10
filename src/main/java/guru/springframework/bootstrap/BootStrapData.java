package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitToMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class BootStrapData implements ApplicationListener<ContextRefreshedEvent> {
  private final RecipeRepository recipeRepository;
  private final UnitToMeasureRepository uomRepository;
  private final IngredientRepository ingredientRepository;
  private final CategoryRepository categoryRepository;

  public BootStrapData(RecipeRepository recipeRepository, UnitToMeasureRepository uomRepository, IngredientRepository ingredientRepository, CategoryRepository categoryRepository) {
    this.recipeRepository = recipeRepository;
    this.uomRepository = uomRepository;
    this.ingredientRepository = ingredientRepository;
    this.categoryRepository = categoryRepository;
  }


  private List<Recipe> getRecipes() {
    List<Recipe> recipes = new ArrayList<>(1);
    // Unit Of Measure
      Optional<UnitOfMeasure> amountOptional = uomRepository.findByDescription("Amount");
      if(!amountOptional.isPresent()) {
        throw new RuntimeException("Expected UOM not found");
      }

      // get Optionals
      UnitOfMeasure amount = amountOptional.get();


      //Ingredient
      Ingredient mainIngredient = new Ingredient();
      mainIngredient.setDescription("Avocados");
      mainIngredient.setAmount(new BigDecimal(2));
      mainIngredient.setUnitOfMeasure(amount);

      // Category
      Optional<Category> mexicanOptional = categoryRepository.findByDescription("Mexican");
      if(!mexicanOptional.isPresent()) {
        throw new RuntimeException("Expected Category not found");
      }
      Category mexican = mexicanOptional.get();

      //Notes
      Notes guacNotes = new Notes();
      guacNotes.setRecipeNotes("BLABLABLABLABL");



      Recipe perfectGuacamole = new Recipe();
      guacNotes.setRecipe(perfectGuacamole);
      mainIngredient.setRecipe(perfectGuacamole);
      perfectGuacamole.setDescription("Perfect quacamole");
      perfectGuacamole.setPrepTime(20);
      perfectGuacamole.setCookTime(10);
      perfectGuacamole.setNotes(guacNotes);
      perfectGuacamole.setDifficulty(Difficulty.EASY);
      perfectGuacamole.getIngredients().add(mainIngredient);
      perfectGuacamole.getCategories().add(mexican);
      recipes.add(perfectGuacamole);
      System.out.println("::::RECIPE CREATED"+ perfectGuacamole.getDescription());
      return recipes;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    recipeRepository.saveAll(getRecipes());
  }
}
