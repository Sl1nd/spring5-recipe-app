package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImplementation implements RecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCmdToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCmd;

  public RecipeServiceImplementation(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCmdToRecipe, RecipeToRecipeCommand recipeToRecipeCmd) {
    this.recipeRepository = recipeRepository;
    this.recipeCmdToRecipe = recipeCmdToRecipe;
    this.recipeToRecipeCmd = recipeToRecipeCmd;
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

  @Override
  @Transactional
  public RecipeCommand saveRecipeCommand(RecipeCommand recipeCmd) {
    Recipe detachedRecipe = recipeCmdToRecipe.convert(recipeCmd);
    Recipe savedRecipe = recipeRepository.save(detachedRecipe);
    return recipeToRecipeCmd.convert(savedRecipe);
  }

  @Override
  public RecipeCommand findCommandById(Long id) {

    return recipeToRecipeCmd.convert(findById(id));
  }
}
