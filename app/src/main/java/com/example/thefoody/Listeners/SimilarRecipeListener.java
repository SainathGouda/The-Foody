package com.example.thefoody.Listeners;

import com.example.thefoody.Models.SimilarRecipeResponse;

import java.util.List;

public interface SimilarRecipeListener {

    void didFetch(List<SimilarRecipeResponse> response, String message);
    void didError(String message);
}
