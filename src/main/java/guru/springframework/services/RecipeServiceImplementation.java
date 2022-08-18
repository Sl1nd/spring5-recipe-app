package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImplementation implements RecipeService {
  private final RecipeRepository recipeRepository;

  public RecipeServiceImplementation(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }


  @Override
  public Set<Recipe> getRecipes() {
    Set<Recipe> recipeSet = new HashSet<>();
    recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
    return recipeSet;
  }

  @Override
  public Recipe findById(Long ID) {
    Optional<Recipe> recipe = recipeRepository.findById(ID);
    if(!recipe.isPresent()) {
      throw new RuntimeException("Recipe Not Found");
    }
    return recipe.get();
  }
}
