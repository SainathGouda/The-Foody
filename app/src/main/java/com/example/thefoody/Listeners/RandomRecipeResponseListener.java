package com.example.thefoody.Listeners;

import com.example.thefoody.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response ,String message);
    void didError(String message);
}
