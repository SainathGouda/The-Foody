package com.example.thefoody.Listeners;

import com.example.thefoody.Models.RecipesDetailedResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipesDetailedResponse response, String message);
    void didError(String message);
}
