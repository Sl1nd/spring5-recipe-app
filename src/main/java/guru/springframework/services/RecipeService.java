package guru.springframework.services;

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;

import java.util.Set;

public interface RecipeService  {
  Set<Recipe> getRecipes();

  Recipe findById(Long ID);
}
