package com.example.rhizome.app.domain.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.service.SelectRecipe;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.entity.RecipeGenreEntity;
import com.example.rhizome.app.infra.repository.MyRecipeRepository;
import com.example.rhizome.app.infra.repository.RecipeRepository;

@Service
@Transactional

public class SelectRecipeImpl implements SelectRecipe {
	RecipeRepository recipeRepository;

	public List<Recipe> selectAll() {
		return RecipeEntity.createRecipeList(recipeRepository.findAll());
	}

	@Autowired
	MyRecipeRepository myrecipeRepository;

	@Override
	public List<RecipeGenreEntity> getRecommendedRecipes(int recommendCount) {
		// 現在の日付を取得
		LocalDate currentDate = LocalDate.now();
		// LocalDateからint型の日付を取得する方法
		int year = currentDate.getYear();
		int month = currentDate.getMonthValue();
		int day = currentDate.getDayOfMonth();
		// 日付をint型で表現する場合（yyyymmdd形式の整数になる）
		int dateInt = year * 10000 + month * 100 + day;
		System.out.println("現在の日付（int型）: " + dateInt);

		int recordCount = (int) myrecipeRepository.count();
		List<Integer> recommendedRecipeIds = generateRandomRecipeIds(recommendCount, recordCount, dateInt, month, day,
				year);
		return fetchRecipes(recommendedRecipeIds, recommendCount, recordCount);
	}

	private List<Integer> generateRandomRecipeIds(int recommendCount, int recordCount, int dateInt, int month, int year,
			int day) {
		List<Integer> recommendedRecipeIds = new ArrayList<>();

		int randomNumber1 = (dateInt % recordCount) + 1;
		if (randomNumber1 <= 0) {
			int addNumber1 = randomNumber1 + randomNumber1 * 2;
			recommendedRecipeIds.add(addNumber1);
			System.out.println("add" + addNumber1);

		}
		if (randomNumber1 > recordCount) {
			int addNumber1 = ((dateInt - recordCount + recommendCount) % recordCount) + 1;
			recommendedRecipeIds.add(addNumber1);
			System.out.println("add" + addNumber1);
		}
		System.out.println("1" + randomNumber1);
		recommendedRecipeIds.add(randomNumber1);

		int randomNumber2 = (dateInt % recordCount + day) + 1;
		if (randomNumber2 <= 0) {
			int addNumber2 = randomNumber2 + randomNumber2 * 2;
			recommendedRecipeIds.add(addNumber2);
		}
		if (randomNumber2 > recordCount) {
			int addNumber2 = ((dateInt - recordCount - recommendCount + day) % recordCount) + 1;
			recommendedRecipeIds.add(addNumber2);
			System.out.println("add2" + addNumber2);
		}
		System.out.println("2" + randomNumber2);
		recommendedRecipeIds.add(randomNumber2);

		int randomNumber3 = (dateInt % recordCount + month) + 1;
		if (randomNumber3 <= 0) {
			int addNumber3 = randomNumber3 + randomNumber3 * 2;
			recommendedRecipeIds.add(addNumber3);
		}
		if (randomNumber3 > recordCount) {
			int addNumber3 = ((dateInt - recordCount - recommendCount + month) % recordCount) + 1;
			recommendedRecipeIds.add(addNumber3);
			System.out.println("add3" + addNumber3);
		}
		System.out.println("3" + randomNumber3);

		recommendedRecipeIds.add(randomNumber3);

		int randomNumber4 = (dateInt % recordCount - recordCount + year % day) + 1;
		if (randomNumber4 <= 0) {
			int addNumber4 = randomNumber4 * (-1);
			recommendedRecipeIds.add(addNumber4);
		}
		if (randomNumber4 > recordCount) {
			int addNumber4 = ((dateInt - recordCount * recommendCount + 4) % recordCount) + 1;
			recommendedRecipeIds.add(addNumber4);
			System.out.println("add4" + addNumber4);
		}
		System.out.println("4" + randomNumber4);
		recommendedRecipeIds.add(randomNumber4);
		return recommendedRecipeIds;

	}

	private List<RecipeGenreEntity> fetchRecipes(List<Integer> recipeIds, int recommendCount, int recordCount) {
		List<RecipeGenreEntity> recommendedRecipes = new ArrayList<>();
		List<Integer> foundRecipeIds = new ArrayList<>();

		int countDelFlg0 = 0; // delFlgが0のレシピの個数を格納する変数

		for (Integer recipeId : recipeIds) {
			countDelFlg0 = 0;
			RecipeGenreEntity recipegenreEntity = myrecipeRepository.findById(recipeId).orElse(null);
			if (recipegenreEntity != null) {
				if (recipegenreEntity.getDelFlg() == 0) {
					countDelFlg0++; // delFlgが0の場合、カウントを増やす
					System.out.println("delFlgが0のレシピの個数: " + countDelFlg0);
				} else if (recipegenreEntity.getDelFlg() == 1) {
					recommendedRecipes.add(recipegenreEntity);
					foundRecipeIds.add(recipeId);
				}
			}
		}
		int attempts = 0; // 最大試行回数を設定
		int maxAttempts = recordCount - countDelFlg0; // 試行回数の上限を設定
		for (int i = 0; recommendedRecipes.size() < recommendCount && attempts < maxAttempts; i++) {
			// ランダムなレシピIDを生成
			int randomRecipeId = (int) (countDelFlg0 * recommendCount) + i;

			// レシピがすでに取得済みでないことを確認
			if (!foundRecipeIds.contains(randomRecipeId)) {
				RecipeGenreEntity recipeEntity = myrecipeRepository.findById(randomRecipeId).orElse(null);
				if (recipeEntity != null && recipeEntity.getDelFlg() == 1) {
					recommendedRecipes.add(recipeEntity);
					foundRecipeIds.add(randomRecipeId);
				}
			}
			attempts++; // 試行回数を増やす
		}

		return recommendedRecipes;
	}

	public List<RecipeGenreEntity> selectMyRecipes(int userId, int delFlg) {
		//		List<RecipeGenreEntity> recommendedRecipes = new ArrayList<>();
		//		for (Integer userId : userIds) {
		//			RecipeGenreEntity recipegenreEntity = recipeRepository.findById(userId).orElse(null);
		//			if (recipegenreEntity != null) {
		//				recommendedRecipes.add(recipegenreEntity);
		//
		//			}
		//		}
		List<RecipeGenreEntity> recipes = myrecipeRepository.findAllByIdUserAndDelFlg(userId, delFlg);

		//		return recommendedRecipes;
		return recipes;
	}

	public Integer countRecipesByIdUser(int userId) {
		return myrecipeRepository.countByIdUser(userId);
	}
	
	public Integer countRecipesByIdUserAndDelFlg(int userId ,int delFlg) {
		return myrecipeRepository.countByIdUserAndDelFlg(userId, delFlg);
	}

}