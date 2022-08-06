package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitToMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {
  private final CategoryRepository categoryRepository;
  private final UnitToMeasureRepository unitOfMeasureRepo;

  public IndexController(CategoryRepository categoryRepository, UnitToMeasureRepository unitOfMeasureRepo) {
    this.categoryRepository = categoryRepository;
    this.unitOfMeasureRepo = unitOfMeasureRepo;
  }

  @RequestMapping({"", "/", "/index"})
  public String getIndexPage(){
    Optional<Category> category = categoryRepository.findByDescription("Mexican");
    Optional<UnitOfMeasure> uom = unitOfMeasureRepo.findByDescription("Teaspoon");
    System.out.println("CAT ID is:" + category.get().getId());
    System.out.println("Uom ID is:"+ uom.get().getId());
    return "index";
  }
}
