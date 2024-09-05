package com.gkemayo.library.category;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;



@RestController
@RequestMapping("/rest/category/api")
@Tag(description = "Book Category Rest Controller", name = "Category")
public class CategoryRestController {
	
	@Autowired
	private CategoryServiceImpl categoryService;
	
	@GetMapping("/allCategories")
	@Operation(summary ="List all book categories of the Library")
	@ApiResponses(value = {
			@ApiResponse(responseCode  = "200", description  = "Ok: successfully listed"),
			@ApiResponse(responseCode  = "204", description  = "No Content: no result founded"),
	})
	public ResponseEntity<List<CategoryDTO>> getAllBookCategories(){
		List<Category> categories = categoryService.getAllCategories();
		if(!CollectionUtils.isEmpty(categories)) {
			//on retire tous les élts null que peut contenir cette liste
			categories.removeAll(Collections.singleton(null));
			List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
				return mapCategoryToCategoryDTO(category);
			}).collect(Collectors.toList());
			return new ResponseEntity<List<CategoryDTO>>(categoryDTOs, HttpStatus.OK);
		}
		return new ResponseEntity<List<CategoryDTO>>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Transforme un Category en CategoryDTO
	 * 
	 * @param book
	 * @return
	 */
	private CategoryDTO mapCategoryToCategoryDTO(Category category) {
		ModelMapper mapper = new ModelMapper();
		CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);
		return categoryDTO;
	}

}
